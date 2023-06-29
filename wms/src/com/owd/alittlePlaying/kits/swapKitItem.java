package com.owd.alittlePlaying.kits;

import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * Created by IntelliJ IDEA.
 * User: danny
 * Date: Jul 28, 2010
 * Time: 9:47:05 AM
 * To change this template use File | Settings | File Templates.
 */
public class swapKitItem {

    public static void main(String[] args) {
        String clientId = "494";
        try {
            Session sess = HibernateSession.currentSession();
            String KitSku = createkits.getIdfromInventoryNumforClient("AMZUK-FLEX-1M", clientId, sess);
            String oldSku = createkits.getIdfromInventoryNumforClient("AMZUK-FLEX-6M", clientId, sess);
            String newSku = createkits.getIdfromInventoryNumforClient("FLEX-B60", clientId, sess);

            System.out.printf("Kit sku: %s,  Oldsku: %s, new Sku: %s", KitSku, oldSku, newSku);
            swapKit(KitSku, oldSku, newSku, sess);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static void swapKit(String kitsku, String oldsku, String newsku, Session sess) {
        try {
            String sql = "update owd_inventory_required_skus set req_inventory_fkey = :newsku " +
                    "where inventory_fkey = :kitsku and req_inventory_fkey = :oldsku";
            Query q = sess.createSQLQuery(sql);
            q.setString("newsku", newsku);
            q.setString("kitsku", kitsku);
            q.setString("oldsku", oldsku);
            int results = q.executeUpdate();
            System.out.printf("This is how many rows updated %d", results);
            if (results > 0) {
                HibUtils.commit(sess);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
