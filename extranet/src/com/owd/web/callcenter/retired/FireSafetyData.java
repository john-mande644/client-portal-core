package com.owd.web.callcenter.retired;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.managers.ConnectionManager;

import java.sql.ResultSet;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Sep 23, 2003
 * Time: 12:17:23 PM
 * To change this template use Options | File Templates.
 */
public class FireSafetyData {
private final static Logger log =  LogManager.getLogger();
    public String zip = "";
    public String first_name = "";
    public String last_name = "";
    public String phone = "";
    public String marketing_source = "";
    public String address_1 = "";
    public String address_2 = "";
    public String city = "";
    public String state = "";
    public String home_phone = "";
    public String door_count = "";
    public String fire_system_present = "";
    public String time_in_home = "";
    public String own_home = "";
    public String contact_time = "";
    public String contact_phone = "";
    public String cell_phone = "";

    public String comments = "";

    public FireSafetyData() {


    }


    public void saveOrderData() throws Exception {

        java.sql.Connection cxn = null;
        java.sql.PreparedStatement stmt = null;
        ResultSet rs = null;

        try {

            cxn = ConnectionManager.getConnection();

            stmt = cxn.prepareStatement("insert into proalertfs_orders "
                    + "(zip,first_name,last_name,phone,"
                    + "marketing_source,address_1,address_2,city,state,"
                    + "home_phone,door_count,fire_system_present,time_in_home,"
                    + "own_home,contact_time,contact_phone,cell_phone,comments)"
                    + " VALUES "
                    + "(?,?,?,?,?,"
                    + "?,?,?,?,?,"
                    + "?,?,?,?,?,"
                    + "?,?,?)");
            int currIndex = 1;
            stmt.setString(currIndex, zip);
            currIndex++;
            stmt.setString(currIndex, first_name);
            currIndex++;
            stmt.setString(currIndex, last_name);
            currIndex++;
            stmt.setString(currIndex, phone);
            currIndex++;
            stmt.setString(currIndex, marketing_source);
            currIndex++;
            stmt.setString(currIndex, address_1);
            currIndex++;
            stmt.setString(currIndex, address_2);
            currIndex++;
            stmt.setString(currIndex, city);
            currIndex++;
            stmt.setString(currIndex, state);
            currIndex++;
            stmt.setString(currIndex, home_phone);
            currIndex++;
            stmt.setString(currIndex, door_count);
            currIndex++;
            stmt.setString(currIndex, fire_system_present);
            currIndex++;
            stmt.setString(currIndex, time_in_home);
            currIndex++;
            stmt.setString(currIndex, own_home);
            currIndex++;
            stmt.setString(currIndex, contact_time);
            currIndex++;
            stmt.setString(currIndex, contact_phone);
            currIndex++;
            stmt.setString(currIndex, cell_phone);
            currIndex++;
            stmt.setString(currIndex, comments);
            currIndex++;
            stmt.executeUpdate();

            stmt.close();
            cxn.commit();
            cxn.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
            }

            try {
                stmt.close();
            } catch (Exception e) {
            }
            try {
                cxn.close();
            } catch (Exception e) {
            }
        }


    }


}
