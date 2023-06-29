package com.owd.alittlePlaying.kits;

import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import org.hibernate.Query;
import org.hibernate.Session;

import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: danny
 * Date: Nov 20, 2009
 * Time: 1:43:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class createkits {
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

    public static void createSingleKit(String clientId, String mainSku, String childSku, String qty) throws Exception {

        Map<String, String> l = new HashMap();
        l.put(getIdfromInventoryNumforClient(childSku, clientId, HibernateSession.currentSession()), qty);
        creatkit(getIdfromInventoryNumforClient(mainSku, clientId, HibernateSession.currentSession()), l);


    }

    public static void main(String[] args) {


        try {


            //createSingleKit("494","SP-6B","SP-B90","6");


            String clientid = "375";
            // System.out.println(getIdfromInventoryNumforClient("0101NT-YND-SSA2",clientid,HibernateSession.currentSession()));
            String mainsku = getIdfromInventoryNumforClient("drop slim plus 2 bottles", clientid, HibernateSession.currentSession());    //1

            Map<String, String> l = new HashMap();


            l.put(getIdfromInventoryNumforClient("Drop Slim PLUS", clientid, HibernateSession.currentSession()), "2");
           // l.put(getIdfromInventoryNumforClient("BSE-ML13", clientid, HibernateSession.currentSession()), "1");
            //l.put(getIdfromInventoryNumforClient("TEA-B60", clientid, HibernateSession.currentSession()), "1");
            // l.put(getIdfromInventoryNumforClient("TEA-B60", clientid, HibernateSession.currentSession()), "1");

            //   l.put(getIdfromInventoryNumforClient("BAY206", clientid, HibernateSession.currentSession()), "1");
//           l.put(getIdfromInventoryNumforClient("BRO-B60", clientid, HibernateSession.currentSession()), "1");
//           l.put(getIdfromInventoryNumforClient("ESF-B30", clientid, HibernateSession.currentSession()), "1");
//            l.put(getIdfromInventoryNumforClient("TEA-B60", clientid, HibernateSession.currentSession()), "1");
//            l.put(getIdfromInventoryNumforClient("NAT-B60", clientid, HibernateSession.currentSession()), "1");

            creatkit(mainsku, l);

//           l.put(getIdfromInventoryNumforClient("INS-001-MD3", clientid, HibernateSession.currentSession()), "1");
//           l.put(getIdfromInventoryNumforClient("Wooden Crates", clientid, HibernateSession.currentSession()), "1");
         /*   l.put(getIdfromInventoryNumforClient("1022LG-RED", clientid, HibernateSession.currentSession()), "1");
            l.put(getIdfromInventoryNumforClient("1031LG-RED",clientid,HibernateSession.currentSession()),"1");
                    l.put(getIdfromInventoryNumforClient("9200TC-BLU",clientid,HibernateSession.currentSession()),"1");
                    l.put(getIdfromInventoryNumforClient("3010TG-BSB",clientid,HibernateSession.currentSession()),"1");*//*
            //  l.put(getIdfromInventoryNumforClient("40-TGScore-1e",clientid,HibernateSession.currentSession()),"1");
            // l.put(getIdfromInventoryNumforClient("2001PB-CW1",clientid,HibernateSession.currentSession()),"1");
            //  l.put(getIdfromInventoryNumforClient("4007PK-HOL",clientid,HibernateSession.currentSession()),"1");
            *//*  l.put(getIdfromInventoryNumforClient("0102NT-BS4A",clientid,HibernateSession.currentSession()),"1");
            l.put(getIdfromInventoryNumforClient("0102NT-BS4B",clientid,HibernateSession.currentSession()),"1");*//*

            // l.put(getIdfromInventoryNumforClient("4007PK-HOL",clientid,HibernateSession.currentSession()),"1");


            creatkit(mainsku, l);
*/
        } catch (Exception e) {
            e.printStackTrace();
        }

        /* String mainsku = "131142 ";    //1

         Map<String,String> l = new HashMap();



l.put("130908","2");





        creatkit(mainsku,l);*/

/*              mainsku="130713";
                 l = new HashMap();
                     l.put("130609","1");
l.put("119309","1");
                      creatkit(mainsku,l);*/
        /*
 mainsku="129949";
   l = new HashMap();
l.put("128861","1");
l.put("128911","1");

        creatkit(mainsku,l);
    mainsku="129950";
   l = new HashMap();
l.put("128861","1");
l.put("128910","1");

        creatkit(mainsku,l);*/
    }

    public static void creatkit(String id, Map<String, String> items) {
        try {
            System.out.println("1");
            PreparedStatement pstat;
            for (String item : items.keySet()) {

                System.out.println("2");

                pstat = HibernateSession.getPreparedStatement("insert into owd_inventory_required_skus ( inventory_fkey, req_inventory_fkey, req_inventory_quant) values (\n" +
                        "" + id + "," + item + "," + items.get(item) + ")");
                System.out.println("3");
                System.out.println(pstat.executeUpdate());

            }

            String sql = "update owd_inventory set is_auto_inventory = 1 where inventory_id = " + id;
            pstat = HibernateSession.getPreparedStatement(sql);
            pstat.executeUpdate();
            HibUtils.commit(HibernateSession.currentSession());
            /* HibernateSession.currentSession().flush();
           HibernateSession.currentSession().connection().commit();
            HibernateSession.currentSession().connection().close();*/
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
