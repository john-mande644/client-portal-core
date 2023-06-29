package com.owd.alittlePlaying.kits;

import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: danny
 * Date: 3/26/13
 * Time: 9:28 AM
 * To change this template use File | Settings | File Templates.
 */
public class removeItemFromKit {
    public static String getIdfromInventoryNumforClient(String inventoryNum, String clientId, Session sess) throws Exception {
        String id = "";
        String sql = "select inventory_id from owd_inventory where inventory_num = :num and client_fkey = :client";
        Query q = sess.createSQLQuery(sql);
        q.setString("num", inventoryNum.trim());
        q.setString("client", clientId);
        List result = q.list();
        if (result.size() > 0) {
            id = result.get(0).toString();
        } else {
            throw new Exception("NO good lookup for: " + inventoryNum);
        }


        return id;
    }

    public static void main(String[] args){
             String clientId = "494";
        try{
        Session sess = HibernateSession.currentSession();
        String main = getIdfromInventoryNumforClient("WELL-BK",clientId,sess);

           List<String> items = new ArrayList<String>();
            items.add("MD-4B-FE");
           // items.add("MD304-3AUA");
          /*  items.add("MD301-4AU");
           // items.add("MD303-7AUA");
            items.add("MD303-3AU");
            items.add("MD303-1AUA");
            items.add("MD302-3AU");
            items.add("MD302-1AUA");
            items.add("MD304-6AU");
            items.add("MD304-3AUA");
            items.add("MD303-7AU");
            items.add("MD303-3AUMA");
            items.add("MD303-1AU");
            items.add("MD302-3AU");
            items.add("MD302-1AUA");
            items.add("MD307-AU");
            items.add("MD303-AU A");
            items.add("MD301-1AU");*/


            for (String item:items){
            try{
             boolean done =   remove(main,getIdfromInventoryNumforClient(item,clientId,sess),sess);
           System.out.println(item +" : " + done);
            }catch(Exception e){
                e.printStackTrace();
            }
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static boolean remove(String main, String item, Session sess)throws Exception{
        boolean worked = false;
             String sql = "delete from owd_inventory_required_skus where inventory_fkey = :item and req_inventory_fkey = :main";
        Query q = sess.createSQLQuery(sql);
        q.setParameter("main",main);
        q.setParameter("item",item);
        int i = q.executeUpdate();

        if (i>=1){
            System.out.println("it's good");
            HibUtils.commit(sess);
            worked = true;
        }


        return worked;
    }
}
