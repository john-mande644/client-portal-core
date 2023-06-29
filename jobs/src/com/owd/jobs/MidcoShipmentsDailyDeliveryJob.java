package com.owd.jobs;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.OWDUtilities;
import com.owd.core.ftp.FTPManager;
import com.owd.core.managers.ConnectionManager;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: May 6, 2007
 * Time: 2:13:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class
        MidcoShipmentsDailyDeliveryJob extends OWDStatefulJob {
    private final static Logger log =  LogManager.getLogger();

    static                 SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");

         static String salesDataSQL = "select distinct\n" +
                         "order_refnum as 'MidcoOrderID',\n" +
                         "case when charindex('|',whse_notes)>0 \n" +
                         "then RTRIM(LTRIM(substring(whse_notes,CHARINDEX(':',whse_notes)+1,charindex('|',whse_notes)-CHARINDEX(':',whse_notes)-1)) )\n" +
                         "else '' end  as 'Account',\n" +
                         "case when charindex('|',whse_notes)>0 \n" +
                         "then RTRIM(LTRIM(substring(substring(whse_notes,charindex('|',whse_notes)+1,999),CHARINDEX(':',substring(whse_notes,charindex('|',whse_notes)+1,999))+1,999)) )\n" +
                         "else '' end  as 'Region',\n" +
                         "serial  as 'Serial',\n" +
                         "ua  as 'UnitAddress',\n" +
                         "case when charindex(',',o.tracking_nums)>0 then substring(o.tracking_nums,1,charindex(',',o.tracking_nums)-1) else o.tracking_nums end as 'Tracking',inventory_num as 'Item'," +
                 "CONVERT(VARCHAR(8), o.shipped_on, 112) as 'ShippingDate',order_num as 'OwdOrderId',carr_service_ref_num as 'ShipType'\n" +
                         "from owd_order o join owd_order_ship_info s on s.order_fkey=order_id\n" +
                         "join owd_line_item l \n" +
                         "left outer join \n" +
                         "(select line_fkey,(substring(serial_number,1,charindex('|',serial_number)-1)) as gid, RTRIM(LTRIM((substring(serial_number,charindex(':',serial_number)+1,999)))) as serial\n" +
                         "from owd_line_serial_link join owd_inventory_serial on serial_fkey = owd_inventory_serial.id where patindex('%|Serial%',serial_number)>0) as serialdata\n" +
                         " on serialdata.line_fkey=line_item_id \n" +
                         "left outer join \n" +
                         "(select line_fkey,(substring(serial_number,1,charindex('|',serial_number)-1)) as gid, RTRIM(LTRIM((substring(serial_number,charindex(':',serial_number)+1,999)))) as ua\n" +
                         "from owd_line_serial_link join owd_inventory_serial on serial_fkey = owd_inventory_serial.id where patindex('%|UA%',serial_number)>0) as uadata \n" +
                         " on uadata.line_fkey=line_item_id \n" +
                         " on order_id=l.order_fkey and inventory_id in (131755)\n" +
                         "where o.created_date>='2010-7-1'\n" +
                         "and (uadata.gid=null or serialdata.gid=uadata.gid)\n" +
                         "and client_fkey=460 and "+
                       "o.shipped_on=? order by order_refnum";
      static String  HDDTASalesDataSql= "SELECT DISTINCT\n" +
              "    order_refnum AS 'MidcoOrderID',\n" +
              "    CASE\n" +
              "        WHEN charindex('|',whse_notes)>0\n" +
              "        THEN RTRIM(LTRIM(substring(whse_notes,CHARINDEX(':',whse_notes)+1,charindex('|',whse_notes)-CHARINDEX(':',whse_notes)-1)) )\n" +
              "        ELSE ''\n" +
              "    END AS 'Account',\n" +
              "    CASE\n" +
              "        WHEN charindex('|',whse_notes)>0\n" +
              "        THEN RTRIM(LTRIM(substring(substring(whse_notes,charindex('|',whse_notes)+1,999),CHARINDEX(':',substring(whse_notes,charindex('|',whse_notes)+1,999))+1,999)) )\n" +
              "        ELSE ''\n" +
              "    END    AS 'Region',\n" +
              "    serial AS 'Serial',\n" +
              "    CASE\n" +
              "        WHEN charindex(',',o.tracking_nums)>0\n" +
              "        THEN substring(o.tracking_nums,1,charindex(',',o.tracking_nums)-1)\n" +
              "        ELSE o.tracking_nums\n" +
              "    END                                    AS 'Tracking',\n" +
              "    inventory_num                          AS 'Item',\n" +
              "    CONVERT(VARCHAR(8), o.shipped_on, 112) AS 'ShippingDate',\n" +
              "    order_num                              AS 'OwdOrderId',\n" +
              "    carr_service_ref_num                   AS 'ShipType'\n" +
              "FROM\n" +
              "    owd_order o\n" +
              "JOIN owd_order_ship_info s\n" +
              "ON\n" +
              "    s.order_fkey=order_id\n" +
              "JOIN owd_line_item l\n" +
              "LEFT OUTER JOIN\n" +
              "    (\n" +
              "        SELECT\n" +
              "            line_fkey,\n" +
              "            (substring(serial_number,1,charindex('|',serial_number)-1))                 AS gid,\n" +
              "            RTRIM(LTRIM((substring(serial_number,charindex(':',serial_number)+1,999)))) AS serial\n" +
              "        FROM\n" +
              "            owd_line_serial_link\n" +
              "        JOIN owd_inventory_serial\n" +
              "        ON\n" +
              "            serial_fkey = owd_inventory_serial.id\n" +
              "        WHERE\n" +
              "            patindex('%|Serial%',serial_number)>0\n" +
              "    ) AS serialdata\n" +
              "ON\n" +
              "    serialdata.line_fkey=line_item_id\n" +
              "ON\n" +
              "    order_id=l.order_fkey\n" +
              "AND inventory_id IN (188449)\n" +
              "WHERE\n" +
              "    o.created_date>='2010-7-1'\n" +
              "AND client_fkey=460\n" +
              "AND o.shipped_on=?\n" +
              "ORDER BY\n" +
              "    order_refnum";
      static String modemSalesDataSQL = "select distinct\n" +
                         "order_refnum as 'MidcoOrderID',\n" +
                         "case when charindex('|',whse_notes)>0 \n" +
                         "then RTRIM(LTRIM(substring(whse_notes,CHARINDEX(':',whse_notes)+1,charindex('|',whse_notes)-CHARINDEX(':',whse_notes)-1)) )\n" +
                         "else '' end  as 'Account',\n" +
                         "case when charindex('|',whse_notes)>0 \n" +
                         "then RTRIM(LTRIM(substring(substring(whse_notes,charindex('|',whse_notes)+1,999),CHARINDEX(':',substring(whse_notes,charindex('|',whse_notes)+1,999))+1,999)) )\n" +
                         "else '' end  as 'Region',\n" +
                         "serial  as 'Serial',\n" +
                         "ua  as 'UnitAddress',\n" +
                         "case when charindex(',',o.tracking_nums)>0 then substring(o.tracking_nums,1,charindex(',',o.tracking_nums)-1) else o.tracking_nums end as 'Tracking',inventory_num as 'Item',CONVERT(VARCHAR(8), o.shipped_on, 112) as 'ShippingDate'," +
              "order_num as 'OwdOrderId',carr_service_ref_num as 'ShipType', o.shipped_cost as 'ShipCost'\n" +
                         "from owd_order o join owd_order_ship_info s on s.order_fkey=order_id\n" +
                         "join owd_line_item l \n" +
                         "left outer join \n" +
                         "(select line_fkey,(substring(serial_number,1,charindex('|',serial_number)-1)) as gid, RTRIM(LTRIM((substring(serial_number,charindex(':',serial_number)+1,999)))) as serial\n" +
                         "from owd_line_serial_link join owd_inventory_serial on serial_fkey = owd_inventory_serial.id where patindex('%|Serial%',serial_number)>0) as serialdata\n" +
                         " on serialdata.line_fkey=line_item_id \n" +
                         "left outer join \n" +
                         "(select line_fkey,(substring(serial_number,1,charindex('|',serial_number)-1)) as gid, RTRIM(LTRIM((substring(serial_number,charindex(':',serial_number)+1,999)))) as ua\n" +
                         "from owd_line_serial_link join owd_inventory_serial on serial_fkey = owd_inventory_serial.id where patindex('%|MAC%',serial_number)>0) as uadata \n" +
                         " on uadata.line_fkey=line_item_id \n" +
                         " on order_id=l.order_fkey and inventory_id in (136742,136741)\n" +
                         "where o.created_date>='2010-7-1'\n" +
                         "and (uadata.gid=null or serialdata.gid=uadata.gid)\n" +
                         "and client_fkey=460 and "+
                       "o.shipped_on=? order by order_refnum";

        public void internalExecute() {

            try
            {


                log.debug("" + salesDataSQL);
                Calendar cal = Calendar.getInstance();

                try
                {
                 putDataFile(formatter.format(cal.getTime()),getReportData(salesDataSQL,cal.getTime()));
                }catch(Exception ex)
                {
                    ex.printStackTrace();
                }

                 putModemDataFile(formatter.format(cal.getTime()), getReportData(modemSalesDataSQL, cal.getTime()));
                putHDDTADataFile(formatter.format(cal.getTime()),getReportData(HDDTASalesDataSql,cal.getTime()));

            }catch(Throwable th)
        {
            th.printStackTrace();
        }

       }

    public static void main(String[] args)  throws Exception
    {
     //  run();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE,-1);
        log.debug(formatter.format(cal.getTime()));
       // putDataFile(formatter.format(cal.getTime()),getReportData(salesDataSQL,cal.getTime()));
               //  putModemDataFile(formatter.format(cal.getTime()),getReportData(modemSalesDataSQL,cal.getTime()));
        putHDDTADataFile(formatter.format(cal.getTime()),getReportData(HDDTASalesDataSql,cal.getTime()));
    }

    static public String putDataFile(String providedFilenamePrefix, String data) throws Exception {

              //String is the day the report should be for.
          //    GregorianCalendar calendar = new GregorianCalendar();
         //  calendar.add(Calendar.DATE,-5);
              // calendar.add(Calendar.DATE,-2);
              SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
              String filename = "";

//send to ftp site


              filename = providedFilenamePrefix + "-1";
              log.debug("generated file name:" + filename);
              int currIndex = 1;

              FTPManager ftp = new FTPManager("owd.com", "midco", "midftp77");
              ftp.setReadDirectory("/");
              ftp.setWriteDirectory("/");
              Vector files = ftp.getFileNames();
              log.debug("ftp found " + files.size() + " files!");


              ftp.writeFile(filename+".csv", data.getBytes());

              log.debug("file:" + filename);


              return filename;

          }

    static public String putModemDataFile(String providedFilenamePrefix, String data) throws Exception {

           //String is the day the report should be for.
      //     GregorianCalendar calendar = new GregorianCalendar();
      //  calendar.add(Calendar.DATE,-5);
           // calendar.add(Calendar.DATE,-2);
           SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
           String filename = "";

//send to ftp site


           filename = providedFilenamePrefix + "-1";
           log.debug("generated file name:" + filename);
           int currIndex = 1;

           FTPManager ftp = new FTPManager("owd.com", "midco", "midftp77");
           ftp.setReadDirectory("/modems");
           ftp.setWriteDirectory("/modems");
           Vector files = ftp.getFileNames();
           log.debug("ftp found " + files.size() + " files!");


           ftp.writeFile(filename+".csv", data.getBytes());

           log.debug("file:" + filename);


           return filename;

       }
    static public String putHDDTADataFile(String providedFilenamePrefix, String data) throws Exception {

               //String is the day the report should be for.
          //     GregorianCalendar calendar = new GregorianCalendar();
          //  calendar.add(Calendar.DATE,-5);
               // calendar.add(Calendar.DATE,-2);
               SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
               String filename = "";

    //send to ftp site


               filename = providedFilenamePrefix + "-1";
               log.debug("generated file name:" + filename);
               int currIndex = 1;

               FTPManager ftp = new FTPManager("owd.com", "midco", "midftp77");
               ftp.setReadDirectory("/hddta");
               ftp.setWriteDirectory("/hddta");
               Vector files = ftp.getFileNames();
               log.debug("ftp found " + files.size() + " files!");


               ftp.writeFile(filename+".csv", data.getBytes());

               log.debug("file:" + filename);


               return filename;

           }
       static public String getReportData(String sql, Date currDate)
       {


        java.sql.Connection cxn = null;
        java.sql.PreparedStatement stmt = null;
        java.sql.ResultSet rs = null;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        DecimalFormat df = new DecimalFormat("##,###.00");
        StringBuffer result = new StringBuffer();
             try
        {

            cxn = ConnectionManager.getConnection();

            stmt = cxn.prepareStatement(sql);
            stmt.setString(1,sdf.format(currDate.getTime()));
            stmt.executeQuery();

            rs = stmt.getResultSet();

              int c = rs.getMetaData().getColumnCount();
            for(int i=1;i<=c;i++)
                {
                   result.append((i==1?"\"":",\"")+rs.getMetaData().getColumnLabel(i)+"\"");
                }
                result.append("\r\n");

            while(rs.next())
            {
                log.debug("got data row");
                for (int i = 1; i <= c; i++) {

                    String type = rs.getMetaData().getColumnTypeName(i);
                    if (type.contains("datetime")) {
                        result.append((i == 1 ? "\"" : ",\"") + sdf.format(rs.getDate(i)) + "\"");
                    } else if (type.contains("numeric") || type.contains("money") || type.contains("float") || type.contains("decimal") || type.contains("real")) {
                        result.append((i == 1 ? "\"" : ",\"") + df.format(OWDUtilities.roundFloat(rs.getFloat(i))) + "\"");
                    } else {
                        result.append((i == 1 ? "\"" : ",\"") + rs.getString(i) + "\"");
                    }
                }
                result.append("\r\n");

            }

            rs.close();
            stmt.close();

        }catch(Throwable th)
        {
            th.printStackTrace();
        }finally
        {
            try{rs.close();}catch(Exception ex){}
            try{stmt.close();}catch(Exception ex){}
            try{cxn.close();}catch(Exception ex){}

        }

           return result.toString();
       }
}
