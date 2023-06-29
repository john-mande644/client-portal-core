package com.owd.core;


public class IIFExporter {

//test for AV
    public static void main(String[] args) {

        java.sql.Connection cxn = null;
        java.sql.PreparedStatement stmt = null;
        java.sql.ResultSet rs = null;

        StringBuffer result = new StringBuffer();
        StringBuffer header = new StringBuffer();

        java.text.DecimalFormat moneyFormatter = new java.text.DecimalFormat("#,###,##0.00");

        float total = 0.00f;

        String salesSQL = "select "
                + "\'SPL\',\'\',\'CASH SALE\',CONVERT(varchar,ship_date,101),\'Sales\',\'\',\'\',-1*(sum(quantity_actual)),price,inventory_num,max(description),(-1*sum(quantity_actual)*price) "
                + "from owd_line_item l join owd_order o on order_id = l.order_fkey and client_fkey=117 and o.is_void = 0 "
                + "join owd_order_track t on ship_date = ? and t.is_void = 0 and line_index = 1 and t.order_fkey = l.order_fkey "
                + "where price>0 "
                + "group by ship_date,inventory_num,price";

        try {

            cxn = com.owd.core.managers.ConnectionManager.getConnection();

            stmt = cxn.prepareStatement(salesSQL);
            stmt.setString(1, "2003-4-21 00:00:00");

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

            cxn.rollback();

            header.append("!TRNS\tTOPRINT\tTRNSTYPE\tDATE\tACCNT\tNAME\tCLASS\tNAMEISTAXABLE\t\t\tMEMO\tAMOUNT\r\n");
            header.append("!SPL\tSPLID\tTRNSTYPE\tDATE\tACCNT\tNAME\tCLASS\tQNTY\tPRICE\tINVITEM\tMEMO\tAMOUNT\r\n");
            header.append("!ENDTRNS\t\t\t\t\t\t\t\t\t\t\t\r\n");
            header.append("TRNS\tN\tCASH SALE\t4/21/2003\tSales Clearing\tOWD Daily Sales\t\tN\t\t\tDaily Sales Import\t" + total + "\r\n");
            header.append(result);
            header.append("ENDTRNS\t\t\t\t\t\t\t\t\t\t\t\r\n");
            Mailer.sendMailWithAttachment("IIF Sales Report 2/21/2003", "Report for 2/21/2003 attached", "owditadmin@owd.com", header.toString().getBytes(), "report", "application/x-qw");
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
