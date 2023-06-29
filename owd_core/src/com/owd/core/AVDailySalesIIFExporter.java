package com.owd.core;

import com.owd.core.managers.ConnectionManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class AVDailySalesIIFExporter {
private final static Logger log =  LogManager.getLogger();

    public static void main(String[] args) {
        doExport("2003-05-26 00:00:00");
        doExport("2003-05-27 00:00:00");
        doExport("2003-05-30 00:00:00");
        doExport("2003-05-31 00:00:00");
        doExport("2003-06-03 00:00:00");
        doExport("2003-06-04 00:00:00");
    }


    //String is the day *after* the report should be for, e.g., if you want a report for yesterday, use today's date.
    public static void doExport(String sqlDate) {


        java.sql.Connection cxn = null;
        java.sql.PreparedStatement stmt = null;
        java.sql.ResultSet rs = null;

        StringBuffer result = new StringBuffer();
        StringBuffer header = new StringBuffer();

        java.text.DecimalFormat moneyFormatter = new java.text.DecimalFormat("#,###,##0.00");
        String reportDate = "No Date";
        float total = 0.00f;

        String salesSQL = "select "
                + "\'SPL\',\'\',\'CASH SALE\',CONVERT(varchar,post_date,101),\'4010\',\'\',\'\',-1*(sum(quantity_actual)),price,inventory_num,max(description),(-1*sum(quantity_actual)*price) "
                + "from owd_line_item l join owd_order o on post_date >= dateadd(day,-1,?) and post_date < ? and order_id = l.order_fkey and client_fkey=117 and o.is_void = 0 "
                + "where price>0 and quantity_actual>0 "
                + "group by CONVERT(varchar,post_date,101),inventory_num,price";

        String voidedSalesSQL = "select "
                + "\'SPL\',\'\',\'CASH SALE\',CONVERT(varchar,post_date,101),\'4010\',\'\',\'\',(sum(quantity_actual)),price,inventory_num,max(description),(sum(quantity_actual)*price) "
                + "from owd_line_item l join owd_order o on post_date >= dateadd(day,-1,?) and post_date < ? and order_id = l.order_fkey and client_fkey=117 and o.is_void = 1"
                + "where price>0 and quantity_actual>0 "
                + "group by CONVERT(varchar,post_date,101),inventory_num,price";

        try {

            cxn = ConnectionManager.getConnection();

            stmt = cxn.prepareStatement(salesSQL);
            stmt.setString(1, sqlDate);
            stmt.setString(2, sqlDate);

            stmt.executeQuery();

            rs = stmt.getResultSet();

            while (rs.next()) {
                reportDate = rs.getString(4);

                result.append(rs.getString(1) + "\t");
                result.append(rs.getString(2) + "\t");
                result.append(rs.getString(3) + "\t");
                result.append(rs.getString(4) + "\t");
                result.append(rs.getString(5) + "\t");
                result.append(rs.getString(6) + "\t");
                result.append(rs.getString(7) + "\t");
                result.append(rs.getString(8) + "\t");
                result.append(moneyFormatter.format(rs.getFloat(9)) + "\t");
                result.append(rs.getString(10) + "\t");
                result.append(rs.getString(11) + "\t");
                result.append(moneyFormatter.format(rs.getFloat(12)) + "\r\n");

                total += (rs.getFloat(12) * -1);
            }

            rs.close();
            stmt.close();
            cxn.rollback();

            stmt = cxn.prepareStatement(voidedSalesSQL);
            stmt.setString(1, sqlDate);
            stmt.setString(2, sqlDate);

            stmt.executeQuery();

            rs = stmt.getResultSet();

            while (rs.next()) {
                result.append(rs.getString(1) + "\t");
                result.append(rs.getString(2) + "\t");
                result.append(rs.getString(3) + "\t");
                result.append(rs.getString(4) + "\t");
                result.append(rs.getString(5) + "\t");
                result.append(rs.getString(6) + "\t");
                result.append(rs.getString(7) + "\t");
                result.append(rs.getString(8) + "\t");
                result.append(moneyFormatter.format(rs.getFloat(9)) + "\t");
                result.append(rs.getString(10) + "\t");
                result.append(rs.getString(11) + "\t");
                result.append(moneyFormatter.format(rs.getFloat(12)) + "\r\n");

                total += (rs.getFloat(12) * -1);
            }

            header.append("!TRNS\tTOPRINT\tTRNSTYPE\tDATE\tACCNT\tNAME\tCLASS\tNAMEISTAXABLE\t\t\tMEMO\tAMOUNT\r\n");
            header.append("!SPL\tSPLID\tTRNSTYPE\tDATE\tACCNT\tNAME\tCLASS\tQNTY\tPRICE\tINVITEM\tMEMO\tAMOUNT\r\n");
            header.append("!ENDTRNS\t\t\t\t\t\t\t\t\t\t\t\r\n");
            header.append("TRNS\tN\tCASH SALE\t" + reportDate + "\t1225\tOWD Daily Sales\t\tN\t\t\tDaily Sales Import\t" + moneyFormatter.format(total) + "\r\n");
            header.append(result);
            header.append("ENDTRNS\t\t\t\t\t\t\t\t\t\t\t\r\n");
            Mailer.sendMailWithAttachment("IIF Sales Report " + reportDate, "Report for " + reportDate + " attached", "activevideos@mangoldcpa.com", header.toString().getBytes(), OWDUtilities.getYYYYMMDDFromSQLDate(sqlDate) + "SalesReport.iif", "application/x-qw");
            //Mailer.sendMailWithAttachment("IIF Sales Report "+reportDate, "Report for "+reportDate+" attached", "dw@activevideos.com",  header.toString().getBytes(), OWDUtilities.getYYYYMMDDFromSQLDate(sqlDate)+"SalesReport.iif", "application/x-qw");


        } catch (Throwable th) {
            th.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception ex) {
            }
            try {
                stmt.close();
            } catch (Exception ex) {
            }
            try {
                cxn.close();
            } catch (Exception ex) {
            }

        }

    }

}
