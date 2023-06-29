package com.owd.jobs.clients;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.Mailer;
import com.owd.core.OWDUtilities;
import com.owd.core.managers.ConnectionManager;
import com.owd.jobs.OWDStatefulJob;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: May 6, 2007
 * Time: 2:13:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class PhionDailyWholeOrdersReport   extends OWDStatefulJob {
private final static Logger log =  LogManager.getLogger();



        public void internalExecute() {

PhionDailyWholeOrdersReport.doExport(OWDUtilities.getSQLDateForToday()+" 00:00:00");
       }


    public static void main(String[] args)
    {
        doExport("2013-10-14 00:00:00");
       // doExport("2003-09-19 00:00:00");
    }


    //String is the day *after* the report should be for, e.g., if you want a report for yesterday, use today's date.
    public static void doExport(String sqlDate)
    {


        java.sql.Connection cxn = null;
        java.sql.PreparedStatement stmt = null;
        java.sql.ResultSet rs = null;

        StringBuffer result = new StringBuffer();
        StringBuffer header = new StringBuffer();

        java.text.DecimalFormat moneyFormatter = new java.text.DecimalFormat("#,###,##0.00");
        String reportDate = "No Date";

        float total = 0.00f;

        String shippedWSOrdersSQL = "select bill_company_name,bill_first_name+' '+bill_last_name,bill_address_one,bill_address_two,bill_city,bill_state,"
+" bill_zip,bill_phone_num,ship_company_name,ship_first_name+' '+ship_last_name,ship_address_one,ship_address_two,"
+" ship_city,ship_state,ship_zip,order_num,o.created_date,ship_date,carr_service,"
+" cc_type,ss_cod,salesperson,po_num,inventory_num,l.description,quantity_actual,price,total_price,"
+" ship_handling_fee,order_total,dateadd(day,-1,?)"
+" from owd_order o join owd_line_item l on l.order_fkey=order_id"
+" join owd_order_ship_info s on s.order_fkey=order_id"
+" join owd_order_track t on t.order_fkey=order_id and t.is_void=0 and"
+" t.line_index=1 and isnull(o.is_shipping,0)=0 "
+" where o.is_void=0 and o.client_fkey=179"
+" and ship_date >= dateadd(day,-1,?) and ship_date < ?"
+" and salesperson <> 'MONSTERCOMMERCE'";

        try
        {

            cxn = ConnectionManager.getConnection();

            stmt = cxn.prepareStatement(shippedWSOrdersSQL);
            stmt.setString(1,sqlDate);
            stmt.setString(2,sqlDate);
            stmt.setString(3,sqlDate);
            stmt.executeQuery();

            rs = stmt.getResultSet();

            while(rs.next())
            {
                result.append("\""+rs.getString(1)+"\",");
                result.append("\""+rs.getString(2)+"\",");
                result.append("\""+rs.getString(3)+"\",");
                result.append("\""+rs.getString(4)+"\",");
                result.append("\""+rs.getString(5)+"\",");
                result.append("\""+rs.getString(6)+"\",");
                result.append("\""+rs.getString(7)+"\",");
                result.append("\""+rs.getString(8)+"\",");
                result.append("\""+rs.getString(9)+"\",");
                result.append("\""+rs.getString(10)+"\",");
                result.append("\""+rs.getString(11)+"\",");
                result.append("\""+rs.getString(12)+"\",");
                result.append("\""+rs.getString(13)+"\",");
                result.append("\""+rs.getString(14)+"\",");
                result.append("\""+rs.getString(15)+"\",");
                result.append("\""+rs.getString(16)+"\",");
                result.append("\""+OWDUtilities.getSlashDateFromSQLDate(rs.getString(17))+"\",");
                result.append("\""+OWDUtilities.getSlashDateFromSQLDate(rs.getString(18))+"\",");

                result.append("\""+rs.getString(19)+"\",");//carr_service
                String terms = "";

                if("CK".equals(rs.getString(20)))
                {

                        terms="PO";

                }else
                {
                    terms = "CC";
                }
                //result.append(rs.getString(20)+","); //cc_type
                //result.append(rs.getString(21)+","); //ss_cod
                result.append(terms+",");
                result.append("\""+rs.getString(22)+"\",");//salesperson
                if(terms.equals("PO"))
                    result.append("\""+rs.getString(23)+"\",");//po_num
                else
                    result.append("\"\",");

                result.append("\""+rs.getString(24)+"\",");
                result.append("\""+rs.getString(25)+"\",");
                result.append("\""+rs.getString(26)+"\",");
                result.append("\""+moneyFormatter.format(rs.getFloat(27))+"\",");
                result.append("\""+moneyFormatter.format(rs.getFloat(28))+"\",");
                result.append("\""+moneyFormatter.format(rs.getFloat(29))+"\",");
                result.append("\""+moneyFormatter.format(rs.getFloat(30))+"\"\r\n");


                reportDate = OWDUtilities.getSlashDateFromSQLDate(rs.getString(31));
            }

            rs.close();
            stmt.close();
        if(!("No Date".equals(reportDate)))
        {
        header.append("PhIon Wholesale Orders Shipped Report for "+reportDate+"\r\n\r\n");
        header.append("BILL_COMPANY,BILL_NAME,BILL_ADDRESS_1,BILL_ADDRESS_2,BILL_CITY,BILL_STATE,BILL_ZIP,BILL_PHONE,SHIP_COMAPNY,SHIP_NAME,SHIP_ADDRESS_1,SHIP_ADDRESS_2,SHIP_CITY,SHIP_STATE,SHIP_ZIP,ORDER_NUMBER,ORDER_DATE,SHIP_DATE,SHIP_VIA,TERMS,SALES_REP,PO_NUMBER,SKU,SKU_DESC,SKU_QTY,SKU_UNIT_PRICE,LINE_TOTAL,SHIP_FEES,ORDER_TOTAL\r\n");
        header.append(result);
    //	Mailer.sendMailWithAttachment("Phion Daily Wholesale Orders Shipped Report "+reportDate, "Report for "+reportDate+" attached", "noop@owd.com",  header.toString().getBytes(), reportDate.replace('/','_')+"_WSShippedRpt.csv", "text/plain");
        Mailer.sendMailWithAttachment("Phion Daily Wholesale Orders Shipped Report "+reportDate, "Report for "+reportDate+" attached", "gerry.kuse@phionbalance.com",  header.toString().getBytes(), reportDate.replace('/','_')+"_WSShippedRpt.csv", "text/plain");
            Mailer.sendMailWithAttachment("Phion Daily Wholesale Orders Shipped Report "+reportDate, "Report for "+reportDate+" attached", "altsalesmgt@gmail.com",  header.toString().getBytes(), reportDate.replace('/','_')+"_WSShippedRpt.csv", "text/plain");

        }

        }catch(Throwable th)
        {
            th.printStackTrace();
        }finally
        {
            try{rs.close();}catch(Exception ex){}
            try{stmt.close();}catch(Exception ex){}
            try{cxn.close();}catch(Exception ex){}

        }

    }

}
