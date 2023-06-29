package com.owd.alittlePlaying.kits;

import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * Created by IntelliJ IDEA.
 * User: danny
 * Date: Oct 18, 2010
 * Time: 10:34:46 AM
 * To change this template use File | Settings | File Templates.
 */
public class addToKit {


    public static void main(String[] args) {
        String clientId = "494";

       // String kitSku = "MD-4B-FE";
        String addonSku = "INS-001-MD3";
        insertNewItem("AMZCA-BP-3B", addonSku, "1", clientId);
      /*  insertNewItem("JULY-MD-3B-BE", addonSku, "1", clientId);
        insertNewItem("JULY-MD-6B-BE", addonSku, "1", clientId);
        insertNewItem("MD-10B", addonSku, "1", clientId);
        insertNewItem("MD-12B", addonSku, "1", clientId);
        insertNewItem("MD-1B-BE", addonSku, "1", clientId);
        insertNewItem("MD-1B-FE", addonSku, "1", clientId);
        insertNewItem("MD-2B-BE", addonSku, "1", clientId);
        insertNewItem("MD-2B-FE", addonSku, "1", clientId);
        insertNewItem("MD303-2M", addonSku, "1", clientId);
        insertNewItem("MD303-6M", addonSku, "1", clientId);
        insertNewItem("MD305-1M", addonSku, "1", clientId);
        insertNewItem("MD305-2M", addonSku, "1", clientId);
        insertNewItem("MD305-4M", addonSku, "1", clientId);
        insertNewItem("MD306-4M", addonSku, "1", clientId);
        insertNewItem("MD-3B-UP", addonSku, "1", clientId);
        insertNewItem("MD-4B-BE", addonSku, "1", clientId);
        insertNewItem("MD-4B-FE", addonSku, "1", clientId);
        insertNewItem("MD-4BS-BE", addonSku, "1", clientId);
        insertNewItem("MD-4BS-FE", addonSku, "1", clientId);
        insertNewItem("MD-5B", addonSku, "1", clientId);
        insertNewItem("MD-6B-UP", addonSku, "1", clientId);*/


    }


    public static void insertNewItem(String Mainsku, String addSku, String qty, String clientId) {
        try {
            Session sess = HibernateSession.currentSession();
            String kitSku = createkits.getIdfromInventoryNumforClient(Mainsku, clientId, sess);
            String addOnSku = createkits.getIdfromInventoryNumforClient(addSku, clientId, sess);

            String sql = "insert into owd_inventory_required_skus ( inventory_fkey, req_inventory_fkey, req_inventory_quant) values (\n" +
                    ":kitSku, :addOnSku, :qty)";

            Query q = sess.createSQLQuery(sql);
            q.setString("kitSku", kitSku);
            q.setString("addOnSku", addOnSku);
            q.setString("qty", qty);

            int resutls = q.executeUpdate();
            if (resutls > 0) {
                HibUtils.commit(sess);
                System.out.println("Yeah it worked");
            }

        } catch (Exception e) {
            System.out.println("Fail");
            e.printStackTrace();
        }


    }
}
