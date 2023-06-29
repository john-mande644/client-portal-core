/**
 * Customer.java holds customer information for table owd_customer
 */

package com.owd.core.business.order;

import com.owd.core.managers.ConnectionManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Customer {
private final static Logger log =  LogManager.getLogger();

    public static final String kcustomer_id = "order_num";//1
    public static final String kcustomer_num = "customer_num";//2
    public static final String kvendor_no = "vendor_no";//3
    public static final String kbill_last_name = "bill_last_name";//4
    public static final String kbill_first_name = "bill_first_name";//5
    public static final String kbill_address_one = "bill_address_one";//6
    public static final String kbill_address_two = "bill_address_two";//7
    public static final String kbill_city = "bill_city";//8
    public static final String kbill_state = "bill_state";//9
    public static final String kbill_zip = "bill_zip";//10
    public static final String kbill_country = "bill_country";//11
    public static final String kbill_company_name = "bill_company_name";//12
    public static final String kbill_title = "bill_title";//13
    public static final String kbill_phone_num = "bill_phone_num";//14
    public static final String kbill_fax_num = "bill_fax_num";//15
    public static final String kbill_email_address = "bill_email_address";//16
    public static final String kship_last_name = "ship_last_name";//17
    public static final String kship_first_name = "ship_first_name";//18
    public static final String kship_address_one = "ship_address_one";//19
    public static final String kship_address_two = "ship_address_two";//20
    public static final String kship_city = "ship_city";//21
    public static final String kship_state = "ship_state";//22
    public static final String kship_zip = "ship_zip";//23
    public static final String kship_country = "ship_country";//24
    public static final String kship_company_name = "ship_company";//25
    public static final String kship_title = "ship_title";//26
    public static final String kship_phone_num = "ship_phone_num";//27
    public static final String kship_fax_num = "ship_fax_num";//28
    public static final String kship_email_address = "ship_email_address";//29
    public static final String kcreated_date = "careated_date";//30
    public static final String kcreated_by = "created_by";//31
    public static final String kmodified_date = "modified_date";//32
    public static final String kmodified_by = "modified_by";//33
    public static final String krow_is_locked = "row_is_locked";//34
    //owd_customer field names that are used
    public static final String fieldnames[] = {
        kbill_last_name, kbill_first_name, kbill_address_one, kbill_address_two,
        kbill_city, kbill_state, kbill_zip, kbill_country, kbill_company_name,
        kbill_phone_num, kbill_fax_num, kbill_email_address, kcustomer_id};

    public String customer_id;
    public String customer_num;
    public String vendor_no;
    public String bill_last_name;
    public String bill_first_name;
    public String bill_address_one;
    public String bill_address_two;
    public String bill_city;
    public String bill_state;
    public String bill_zip;
    public String bill_country;
    public String bill_company_name;
    public String bill_title;
    public String bill_phone_num;
    public String bill_fax_num;
    public String bill_email_address;
    public String ship_last_name;
    public String ship_first_name;
    public String ship_address_one;
    public String ship_address_two;
    public String ship_city;
    public String ship_state;
    public String ship_zip;
    public String ship_country;
    public String ship_company_name;
    public String ship_title;
    public String ship_phone_num;
    public String ship_fax_num;
    public String ship_email_address;
    public String created_date;
    public String created_by;
    public String modified_date;
    public String modified_by;
    public String row_is_locked;

    public static ArrayList getCustomerBillInfo(String customer_num, String lname, String fname, String client_id) {
        ////log.debug("Customer.getCustomerBillInfo(String,String,String,ArrayList\n");
        ArrayList records = new ArrayList();
        Connection cxn = null;
        //PreparedStatement stmt = null;
        Statement stmt = null;
        ResultSet rs = null;
        String field_name = "*";
        /*
        Iterator ifn = field_names.iterator();
        while(ifn.hasNext())
        {
            if("*".equals(field_name))
                field_name = (String) ifn.next();
            else
                field_name += ","+(String) ifn.next();
        }
        //log.debug("field names: "+field_name);
        */
        for (int i = 0; i < fieldnames.length; i++) {
            if ("*".equals(field_name))
                field_name = fieldnames[i];
            else
                field_name += "," + fieldnames[i];
        }

        try {
            cxn = ConnectionManager.getConnection();
            /*
            stmt = cxn.prepareStatement("select distinct "+ field_name +" from owd_order where order_num like \'?\' and bill_last_name like \'?\' and bill_first_name like \'?\'");

            stmt.setString(1, customer_num);
            stmt.setString(2, lname);
            stmt.setString(3, fname);
            */
            String sqlstmt = "select distinct " + field_name + " from owd_order where order_num like \'" + customer_num + "\' and bill_last_name like \'" + lname + "\' and bill_first_name like \'" + fname + "\' and client_fkey like \'" + client_id + "\'";
            ////log.debug("get customer query: "+sqlstmt+"\n");
            stmt = cxn.createStatement();
            rs = stmt.executeQuery(sqlstmt);
            while (rs.next()) {
                ArrayList record = new ArrayList();
                for (int i = 0; i < fieldnames.length; i++) {
                    record.add(rs.getString(i + 1));
                }
                records.add(record);
            }
        } catch (Exception e) {
            //log.debug("Get Customer Error: " + e + "\n");
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
        return records;
    }

    public static ArrayList getCustomerBillInfo(String client_id) {
        return getCustomerBillInfo("%", "%", "%", client_id);
    }

    public static ArrayList getCustomerBillInfoForID(String cust_num, String client_id) {
        return getCustomerBillInfo(cust_num, "%", "%", client_id);
    }

    public static ArrayList getCustomerBillInfoForName(String lname, String fname, String client_id) {
        ////log.debug("Customer.getCustomerBillInfoForName(String, String, ArrayList)\n");
        return getCustomerBillInfo("%", lname, fname, client_id);
    }
}
