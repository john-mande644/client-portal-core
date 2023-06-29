package com.owd.web.internal.warehouse.admin.adjustments;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.hibernate.HibernateSession;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Apr 14, 2006
 * Time: 3:28:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class AdjustmentUtilities {
private final static Logger log =  LogManager.getLogger();

    public static List getClientList() throws Exception {

        List clientList = new ArrayList();
        String query = "SELECT     client_id, company_name\n" +
                "FROM         owd_client\n" +
                "WHERE     (is_active = 1)\n" +
                "ORDER BY company_name";

        ResultSet rs = HibernateSession.getResultSet(HibernateSession.currentSession(), query);
        int i = 0;
        while (rs.next()) {
            selectList btn = new selectList();
            btn.setAction(rs.getString(1));
            btn.setDisplay(rs.getString(2));
            clientList.add(i, btn);
            i++;
            //   //log.debug(rs.getString(1));
        }

        return clientList;
    }

    public static String lookupField(String field) {
        String value = (String) getFeilds().get(field);


        return value;


    }

    static Map fields = null;

    public static Map getFeilds() {
        Map m = fields;
        if (m == null) {
            m = new TreeMap();
            //log.debug("setting fields");
            m.put("Receive Id", "receive_id");
            m.put("Transaction", "transaction_num");
            m.put("Inventory Id", "inventory_id");
            m.put("Inventory Num", "inventory_num");
            m.put("User", "receive_user");
        }
        return m;
    }

}
