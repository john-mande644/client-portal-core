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
public class KenmarkData {
private final static Logger log =  LogManager.getLogger();
    public String zip = "";
    public String first_name = "";
    public String last_name = "";
    public String phone = "";
    public String brand = "";
    public String address_1 = "";
    public String address_2 = "";
    public String city = "";
    public String state = "";
    public String model = "";
    public String problem = "";
    public String parts = "";
    public String notes = "";
    public String csr = "";

    public KenmarkData() {


    }


    public void saveOrderData() throws Exception {

        java.sql.Connection cxn = null;
        java.sql.PreparedStatement stmt = null;
        ResultSet rs = null;

        try {


            cxn = ConnectionManager.getConnection();

            stmt = cxn.prepareStatement("insert into kenmark_data "
                    + "(zip,f_name,l_name,phone,"
                    + "brand,address_1,address_2,city,state,"
                    + "model,problem,parts,notes,"
                    + "csr)"
                    + " VALUES "
                    + "(?,?,?,?,?,"
                    + "?,?,?,?,?,"
                    + "?,?,?,?)");
            int currIndex = 1;
            stmt.setString(currIndex, zip);
            currIndex++;
            stmt.setString(currIndex, first_name);
            currIndex++;
            stmt.setString(currIndex, last_name);
            currIndex++;
            stmt.setString(currIndex, phone);
            currIndex++;
            stmt.setString(currIndex, brand);
            currIndex++;
            stmt.setString(currIndex, address_1);
            currIndex++;
            stmt.setString(currIndex, address_2);
            currIndex++;
            stmt.setString(currIndex, city);
            currIndex++;
            stmt.setString(currIndex, state);
            currIndex++;
            stmt.setString(currIndex, model);
            currIndex++;
            stmt.setString(currIndex, problem);
            currIndex++;
            stmt.setString(currIndex, parts.replace('\r', ' ').replace('\n', ' '));
            currIndex++;
            stmt.setString(currIndex, notes);
            currIndex++;
            stmt.setString(currIndex, csr);
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
