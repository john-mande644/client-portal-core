package com.owd.web.internal.zplutils;

import com.owd.hibernate.HibernateSession;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DBUtils {
    /**
     * Retrieves a list of locations to be displayed in a drop-down
     *
     * @return
     */
    public static List<ShipLocation> getLocations() {
        try {
            ResultSet r = HibernateSession.getResultSet("SELECT * FROM ship_locations");
            ArrayList<ShipLocation> options = new ArrayList<>();
            while (r.next()) {
                String str = String.format("%s %s %s, %s, %s, %s", r.getString(6), r.getString(2), r.getString(3), r.getString(4), r.getString(5), r.getString(7));
                options.add(new ShipLocation(str, r.getInt(1)));
            }

            return options;
        } catch (Exception e) {
            System.out.println(e);
        }
        return new ArrayList<>();
    }

    /**
     * Retrieves a list of vendors to be displayed in a drop-down
     *
     * @return
     */
    public static List<Vendor> getVendors() {
        try {
            ResultSet r = HibernateSession.getResultSet("SELECT * FROM vendors");
            ArrayList<Vendor> options = new ArrayList<>();
            while (r.next()) {
                String str = String.format("%s (%s)", r.getString(2), r.getString(3));
                options.add(new Vendor(str, r.getInt(1)));
            }

            return options;
        } catch (Exception e) {
            System.out.println(e);
        }
        return new ArrayList<>();
    }

    public static String getSSCC() throws Exception {
        // generate the sscc ID
        String ssccQuery = "DECLARE @ssccId VARCHAR(100);" +
                "EXECUTE [dbo].[usp_generatecheckdigitfor_SSCC] @ssccId OUTPUT;" +
                "SELECT @ssccId;";

        ResultSet r = HibernateSession.getResultSet(ssccQuery);
        r.next();
        return r.getString(1);
    }
}
