package com.owd.alittlePlaying;

import com.owd.core.business.order.Event;
import com.owd.core.business.order.OrderStatus;
import com.owd.core.business.order.OrderUtilities;
import com.owd.core.managers.ConnectionManager;
import com.owd.core.managers.FacilitiesManager;
import com.owd.core.xml.OrderXMLDoc;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import org.hibernate.Query;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by danny on 6/13/2016.
 */
public class OrderPlaying {


    public static void putOrderOnHold(String orderId){
        try{

            Connection cxn = ConnectionManager.getConnection();

            String esql = "update owd_order set  is_future_ship = 1 , post_date = null where order_id = " + orderId;
            Statement stmt = cxn.createStatement();

            int rowsUpdated = stmt.executeUpdate(esql);

            if (rowsUpdated < 1){
                stmt.close();
                cxn.close();
                throw new Exception("Order not updated; could not set on hold");
            }


            cxn.commit();
            Event.addOrderEvent(new Integer(orderId).intValue(), Event.kEventTypeHandling, "Order set on hold by " + "client Reqeust", "Client Request");

            stmt.close();
            cxn.close();
        }catch(Exception e){
            e.printStackTrace();
        }


    }
    public static void removeHoldBackorder(String orderId){

        try{
            Connection cxn = ConnectionManager.getConnection();

            String esql = "update owd_order set  is_future_ship = 0, is_backorder=1, backorder_order_num = \"\" where order_id = " + orderId;
            Statement stmt = cxn.createStatement();

            int rowsUpdated = stmt.executeUpdate(esql);

            if (rowsUpdated < 1)
                throw new Exception("Order not updated; could not release hold");

            cxn.commit();
            Event.addOrderEvent(new Integer(orderId).intValue(), Event.kEventTypeHandling, "Order hold removed by " + "Batch Client Request", "system");
            stmt.close();
            cxn.close();
        }catch (Exception e){


            e.printStackTrace();
        }




    }

    static List<Integer> bad = new ArrayList<>();

    public static void main(String[] args){
       /* System.setProperty("com.owd.environment","test");


        try {
            PackingManager.packAndShip(15195716,true, 4, 0.00, "12345678918", false);
           // releaseOrder(15195716+"");
          //  ManifestingManager.getSSCCBarcode();

        }catch (Exception e){

        }*/



       /*  List<String> l = new ArrayList<String>();

       l.add("16381543");
        l.add("16381588");
        l.add("16381595");
        l.add("16381606");
        l.add("16381630");
        l.add("16381648");
        l.add("16381689");
        l.add("16381699");
        l.add("16381750");
        l.add("16381776");
        l.add("16381803");
        l.add("16381809");
        l.add("16381812");
        l.add("16381824");
        l.add("16381830");
        l.add("16381856");
        l.add("16381857");
        l.add("16381907");
        l.add("16381924");
        l.add("16381943");
        l.add("16382023");
        l.add("16382027");
        l.add("16382073");



        for(String id:l){
            try {
              //  resetShippedToWarehouse.resetOrder(id);
               unpostSetBack(id);
            }catch (Exception e){
                e.printStackTrace();
            }
            //releaseOrder(id);
        }*/

//        ==================================
        // 1209795
        List<String> l = new ArrayList<String>();
        /*l.add("24154365");
        l.add("24154366");
        l.add("24154367");
        l.add("24154368");*/

        for(String i:l){
            removeHoldBackorder(i);
        }

        System.out.println("Bad:::");
        for(Integer i: bad){
            System.out.println(i);
        }
    }

    public static void unpostSetBack(String orderId){
        try{
            Query q = HibernateSession.currentSession().createSQLQuery("execute dbo.unpost_order_setback :orderId,1");
            q.setParameter("orderId",orderId);
            q.executeUpdate();
            HibUtils.commit(HibernateSession.currentSession());
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public static boolean releaseOrder(String orderId){
        boolean success = false;
        Connection cxn = null;
        try{
            cxn = ConnectionManager.getConnection();
            System.out.println("hellO");
            OrderStatus status = new OrderStatus(orderId);
            System.out.println(status.getLocation());
            OrderUtilities.updateLineItemsForAvailability(cxn,status.items, OrderXMLDoc.kHoldOrder,false,true, FacilitiesManager.getFacilityForCode(status.getLocation()).getId());

            OrderUtilities.shipExistingOrder(cxn,status);
            success = true;
        }   catch (Exception e){
            e.printStackTrace();
        }   finally {
            try {
                cxn.close();
            }catch (Exception e){

            }
        }

        return success;
    }





    public static boolean releaseOrder(String orderId, String clientId,int facility){
        boolean success = false;
        Connection cxn = null;
        try{
            cxn = ConnectionManager.getConnection();
            System.out.println("hellO");
            OrderStatus status = new OrderStatus(orderId,clientId);
            OrderUtilities.updateLineItemsForAvailability(cxn,status.items, OrderXMLDoc.kPartialShip,false,true,facility);

            OrderUtilities.shipExistingOrder(cxn,status);
            success = true;
        }   catch (Exception e){
            e.printStackTrace();
        }   finally {
            try {
                cxn.close();
            }catch (Exception e){

            }
        }

        return success;
    }

}
