package com.owd.jobs.clients;

import com.owd.core.Mailer;
import com.owd.hibernate.HibernateSession;
import com.owd.jobs.OWDStatefulJob;
import org.apache.commons.net.ftp.FTPClient;
import org.hibernate.Query;

import java.io.ByteArrayInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by danny on 4/17/2017.
 */
public class GildanWeeklyFtpFilesJob extends OWDStatefulJob {
    public DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    public DateFormat fileName = new SimpleDateFormat("yyMMdd");
    public Date firstDatePastPeriod;
    public Date firstDateOfPeriod;

    public static void main(String[] args){


        run();

    }

    public void internalExecute(){
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR ,0) ;
        today.set(Calendar.HOUR_OF_DAY ,0)  ;
        today.set(Calendar.MINUTE ,0)  ;
        today.set(Calendar.SECOND ,0)    ;
        today.set(Calendar.MILLISECOND ,0)    ;

        while(today.get(Calendar.DAY_OF_WEEK) != Calendar.FRIDAY)
        {
            today.add(Calendar.DATE, -1);
        }
        today.add(Calendar.DATE, 1);
        firstDatePastPeriod = today.getTime();
        today.add(Calendar.DATE, -1);
        while(today.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY)
        {
            today.add(Calendar.DATE, -1);
        }
        firstDateOfPeriod = today.getTime();
        System.out.println(df.format(firstDateOfPeriod));
        System.out.println(df.format(firstDatePastPeriod));
        try {
            pullReportAndFTPIt(jcPenneySQL, "JC", "JCPenney");
         //   pullReportAndFTPIt(walmartSql,"WM","Walmart");
           pullReportAndFTPIt(gildanOnlineSql,"GO","Gildan Online");

        }catch (Exception e){
            e.printStackTrace();
        }

    }


    private void pullReportAndFTPIt(String sql,String fileIdentifier, String subjectName) throws Exception  {
        try{
            StringBuilder sb = new StringBuilder();

            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            q.setParameter("first",df.format(firstDateOfPeriod));
            q.setParameter("last",df.format(firstDatePastPeriod));
            List results = q.list();

            if(results.size()>0){
                sb.append("Branch,Customer Number,SKU,Shipped Units,Unit Cost,Extended Total Cost,Default Retail Unit Price,Net Sales,Returned Units,Restocked Units,Restocked Units Extended Cost\r\n");

                for(Object row : results){
                    Object[] data = (Object[]) row;
                    sb.append(data[0]);
                    sb.append(",");
                    sb.append(data[1]);
                    sb.append(",");
                    sb.append(data[2]);
                    sb.append(",");
                    sb.append(data[3]);
                    sb.append(",");
                    sb.append(data[4]);
                    sb.append(",");
                    sb.append(data[5]);
                    sb.append(",");
                    sb.append(data[6]);
                    sb.append(",");
                    sb.append(data[7]);
                    sb.append(",");
                    sb.append(data[8]);
                    sb.append(",");
                    sb.append(data[9]);
                    sb.append(",");
                    sb.append(data[10]);

                    sb.append("\r\n");

                }
                FTPClient client = new FTPClient();
                String filename = fileIdentifier+fileName.format(firstDatePastPeriod)+".csv";
                try{
                    client.connect("ftp.gildan.com");

                    client.login("63677","OWd@2017");
                    client.enterLocalPassiveMode();
                    client.changeWorkingDirectory("/fromvan");

                   System.out.println(client.storeFile(filename, new ByteArrayInputStream(sb.toString().getBytes())));
                    System.out.println(client.getReplyCode());
                    System.out.println(client.getReplyString());
                    client.logout();

                }catch (Exception e){
                    e.printStackTrace();
                }
                Mailer.sendMailWithAttachment("Weekly FTP file for "+subjectName, "Attached is the file", "acardillo" +
                        "@gildan.com",new Object[]{"mhust@gildan.com","dpriebe@gildan.com","mastorino@gildan.com",
                         "llegg@gildan.com"}, sb.toString().getBytes(), filename, "text/csv");






            }




        }catch(Exception e){
            e.printStackTrace();

        }




    }





  public String jcPenneySQL = "SELECT 'RDC' as \"Branch\", '171436' as \"Customer Number\", \n" +
          "\n" +
          "                    i.inventory_num                AS 'SKU',\n" +
          "                    ISNULL(SUM(quantity_actual),0) AS 'Shipped Units',\n" +
          "                    ISNULL(supp_cost,0.0000)       AS 'Unit Cost',\n" +
          "                    ISNULL(supp_cost*SUM(quantity_actual),0.0000) 'Extended Total Cost',\n" +
          "                    ISNULL(i.price,0.00)            AS 'Default Retail Unit Price',\n" +
          "                        ISNULL(SUM(quantity_actual*l.price),0.00) AS 'Net Sales',\n" +
          "                    ISNULL(ret,0)                   AS 'Returned Units',\n" +
          "                    ISNULL(rts,0)                   AS 'Restocked Units',\n" +
          "                    ISNULL(rts*supp_cost,0.0000)    AS 'Restocked Units Extended Cost'\n" +
          "                FROM\n" +
          "                    owd_inventory i (NOLOCK)\n" +
          "                LEFT OUTER JOIN\n" +
          "                   (\n" +
          "                        SELECT\n" +
          "                            SUM([Qty Restocked]) AS rts,\n" +
          "                            SUM([Qty Received])  AS ret,\n" +
          "                            [Inventory ID]       AS inventory_id\n" +
          "                        FROM\n" +
          "                            OWD_CLIENT_REPORTS.dbo.vw_returndata join owd_order oo on oo.order_id=[Order Id] and order_type like '%jcpenney%'\n" +
          "                        WHERE\n" +
          "                            [RETURN DATE] >=:first\n" +
          "                        AND [RETURN DATE]<:last\n" +
          "                        GROUP BY\n" +
          "                            [Inventory ID]\n" +
          "                    ) AS returnees\n" +
          "                ON\n" +
          "                    returnees.inventory_id=i.inventory_id\n" +
          "                LEFT OUTER JOIN owd_line_item l (NOLOCK)\n" +
          "                JOIN owd_order (NOLOCK)\n" +
          "                ON\n" +
          "                    shipped_on >= :first\n" +
          "                AND shipped_on <= :last\n" +
          "                AND order_id=order_fkey\n" +
          "                ON\n" +
          "                    i.inventory_id=l.inventory_id\n" +
          "                WHERE\n" +
          "                    i.client_fkey in ('471') and i.inventory_num not in ('PPS','MAIL') and order_type like '%jcpenney%'\n" +
          "                GROUP BY\n" +
          "                    i.inventory_num,\n" +
          "                    i.price,\n" +
          "                    supp_cost,\n" +
          "                    ret,\n" +
          "                    rts";
    public String walmartSql = "SELECT 'RDC' as \"Branch\", '108875' as \"Customer Number\", \n" +
            "\n" +
            "                    i.inventory_num                AS 'SKU',\n" +
            "                    ISNULL(SUM(quantity_actual),0) AS 'Shipped Units',\n" +
            "                    ISNULL(supp_cost,0.0000)       AS 'Unit Cost',\n" +
            "                    ISNULL(supp_cost*SUM(quantity_actual),0.0000) 'Extended Total Cost',\n" +
            "                    ISNULL(i.price,0.00)            AS 'Default Retail Unit Price',\n" +
            "                        ISNULL(SUM(quantity_actual*l.price),0.00) AS 'Net Sales',\n" +
            "                    ISNULL(ret,0)                   AS 'Returned Units',\n" +
            "                    ISNULL(rts,0)                   AS 'Restocked Units',\n" +
            "                    ISNULL(rts*supp_cost,0.0000)    AS 'Restocked Units Extended Cost'\n" +
            "                FROM\n" +
            "                    owd_inventory i (NOLOCK)\n" +
            "                LEFT OUTER JOIN\n" +
            "                   (\n" +
            "                        SELECT\n" +
            "                            SUM([Qty Restocked]) AS rts,\n" +
            "                            SUM([Qty Received])  AS ret,\n" +
            "                            [Inventory ID]       AS inventory_id\n" +
            "                        FROM\n" +
            "                            OWD_CLIENT_REPORTS.dbo.vw_returndata join owd_order oo on oo.order_id=[Order Id] and order_type like '%walmart%'\n" +
            "                        WHERE\n" +
            "                            [RETURN DATE] >=:first\n" +
            "                        AND [RETURN DATE]<:last\n" +
            "                        GROUP BY\n" +
            "                            [Inventory ID]\n" +
            "                    ) AS returnees\n" +
            "                ON\n" +
            "                    returnees.inventory_id=i.inventory_id\n" +
            "                LEFT OUTER JOIN owd_line_item l (NOLOCK)\n" +
            "                JOIN owd_order (NOLOCK)\n" +
            "                ON\n" +
            "                    shipped_on >= :first\n" +
            "                AND shipped_on <= :last\n" +
            "                AND order_id=order_fkey\n" +
            "                ON\n" +
            "                    i.inventory_id=l.inventory_id\n" +
            "                WHERE\n" +
            "                    i.client_fkey in ('471') and i.inventory_num not in ('PPS','MAIL') and order_type like '%walmart%'\n" +
            "                GROUP BY\n" +
            "                    i.inventory_num,\n" +
            "                    i.price,\n" +
            "                    supp_cost,\n" +
            "                    ret,\n" +
            "                    rts";
    public String gildanOnlineSql = "SELECT 'RDC' as \"Branch\", '183559' as \"Customer Number\", \n" +
            "\n" +
            "                    i.inventory_num                AS 'SKU',\n" +
            "                    ISNULL(SUM(quantity_actual),0) AS 'Shipped Units',\n" +
            "                    ISNULL(supp_cost,0.0000)       AS 'Unit Cost',\n" +
            "                    ISNULL(supp_cost*SUM(quantity_actual),0.0000) 'Extended Total Cost',\n" +
            "                    ISNULL(i.price,0.00)            AS 'Default Retail Unit Price',\n" +
            "                        ISNULL(SUM(quantity_actual*l.price),0.00) AS 'Net Sales',\n" +
            "                    ISNULL(ret,0)                   AS 'Returned Units',\n" +
            "                    ISNULL(rts,0)                   AS 'Restocked Units',\n" +
            "                    ISNULL(rts*supp_cost,0.0000)    AS 'Restocked Units Extended Cost'\n" +
            "                FROM\n" +
            "                    owd_inventory i (NOLOCK)\n" +
            "                LEFT OUTER JOIN\n" +
            "                   (\n" +
            "                        SELECT\n" +
            "                            SUM([Qty Restocked]) AS rts,\n" +
            "                            SUM([Qty Received])  AS ret,\n" +
            "                            [Inventory ID]       AS inventory_id\n" +
            "                        FROM\n" +
            "                            OWD_CLIENT_REPORTS.dbo.vw_returndata join owd_order oo on oo.order_id=[Order Id] and  (order_type like '%magento%' or order_type like '%bigcommerce%' or order_type like '%Gildan.com%')\n" +
            "                        WHERE\n" +
            "                            [RETURN DATE] >=:first\n" +
            "                        AND [RETURN DATE]<:last\n" +
            "                        GROUP BY\n" +
            "                            [Inventory ID]\n" +
            "                    ) AS returnees\n" +
            "                ON\n" +
            "                    returnees.inventory_id=i.inventory_id\n" +
            "                LEFT OUTER JOIN owd_line_item l (NOLOCK)\n" +
            "                JOIN owd_order (NOLOCK)\n" +
            "                ON\n" +
            "                    shipped_on >=:first\n" +
            "                AND shipped_on <=:last\n" +
            "                AND order_id=order_fkey\n" +
            "                ON\n" +
            "                    i.inventory_id=l.inventory_id\n" +
            "                WHERE\n" +
            "                    i.client_fkey in ('471') and i.inventory_num not in ('PPS','MAIL') and (order_type like '%magento%' or order_type like '%bigcommerce%' or order_type like '%Gildan.com%' )\n" +
            "                GROUP BY\n" +
            "                    i.inventory_num,\n" +
            "                    i.price,\n" +
            "                    supp_cost,\n" +
            "                    ret,\n" +
            "                    rts";
}
