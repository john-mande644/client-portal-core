package com.owd.core.managers;


public class SalesTaxManager {


    public static float getSalesTaxPct(String zip, String city, String clientID) {
        float pct = -1f;
        float highpct = 0.00f;
        java.sql.Connection cxn = null;
        java.sql.PreparedStatement stmt = null;
        java.sql.ResultSet rs = null;

        try {
            cxn = ConnectionManager.getConnection();
            stmt = cxn.prepareStatement("select combined_sales_tax,city_name from TAXSALES where zip5 = ?");
            stmt.setString(1, zip.trim().substring(0, 5));
            String cityname = city.trim().toUpperCase();
            stmt.executeQuery();
            rs = stmt.getResultSet();
            while (rs.next()) {
                if (cityname.equals(rs.getString(2).trim())) {
                    pct = rs.getFloat(1);
                } else {
                    if (rs.getFloat(1) > highpct)
                        highpct = rs.getFloat(1);
                }
            }
            if (pct == -1f) pct = highpct;

        } catch (Exception ex) {

            ex.printStackTrace();

        } finally {

            try {
                rs.close();
            } catch (Exception exc) {
            }
            try {
                stmt.close();
            } catch (Exception exc) {
            }
            try {
                cxn.close();
            } catch (Exception exc) {
            }


        }


        return pct;

    }


    public static void main(String[] args) {
        //log.debug("57601w=" + SalesTaxManager.getSalesTaxPct("57601", "Mobridgew", "117"));
        //log.debug("57601=" + SalesTaxManager.getSalesTaxPct("57601", "Mobridge", "117"));
        //log.debug("57601=" + SalesTaxManager.getSalesTaxPct("57601", "Promise", "117"));

    }
}
