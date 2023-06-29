package com.owd.dc.warehouse.personalized;

import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.generated.WarehouseStickerRelease;
import com.owd.hibernate.generated.OwdOrder;
import com.owd.core.business.order.OrderStatus;
import com.owd.core.business.order.OrderUtilities;
import com.owd.core.managers.ConnectionManager;

import java.util.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

import org.hibernate.Session;
import org.hibernate.Query;

/**
 * Created by IntelliJ IDEA.
 * User: danny
 * Date: Nov 6, 2008
 * Time: 4:23:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class BORStickerReceipt {


    /**
     *
     * @param sess      HibernateSession to use
     * @param orderRef  Client order Reference the sicker is for
     * @param stickerRef The sku for the skicker on the order
     * @param skuRef     The sku that the sticker is for
     * @param clientID   The client Id for the order
     * @throws Exception   Errors for not valid orders and sku's
     * @return String Returns the info of what happend to be added to diplay list
     */

    public static String addScannedOrderToConfirmedList(Session sess, String orderRef, String stickerRef, String skuRef, String clientID) throws Exception {
         System.out.println(clientID);
          String info="";
        //look up order
        //check line item SKUs and quantities
        //check order status
        boolean newOrder = true;

       //System.out.println("Searching for ref " + orderRef);




       System.out.println("4");

        long time = Calendar.getInstance().getTimeInMillis();
               String sql = " from OwdOrder as o left join o.lineitems as items with items.inventoryNum = '"+skuRef +"'"+
                       "where o.postDate is null and o.clientFkey = "+ clientID +" and o.isFutureShip=0" +
                       "and o.isVoid = 0  and o.orderStatus = 'Backorder (Active)' and o.orderRefnum like '%"+orderRef+"%' order by o.actualOrderDate";
               Query findOrder = sess.createQuery(sql);
      /*  System.out.println("22");
                findOrder.setParameter("skuRef",skuRef);
        System.out.println("33");
        findOrder.setParameter("clientID",Integer.valueOf(clientID));
        System.out.println("44");
        findOrder.setParameter("orderRef", orderRef);
        System.out.println("55");*/
               List l = findOrder.list();
   


           if (l.size()==0) {
               String statussql = "select order_status from owd_order  (NOLOCK) where order_refnum = '"+orderRef+"' and is_void = 0 and client_fkey  = "+clientID;
           //  System.out.println(statussql);
               Query q = sess.createSQLQuery(statussql);
               List res = q.list();
            //             System.out.println(res.size());
               String s = "";
               for(Object a :  res.toArray()){
                   s = s + a.toString()+" ";

               }


               throw new Exception("Error looking up order for reference " + orderRef + " - review with AM!  Status is: "+s);
           }

              System.out.println(Calendar.getInstance().getTimeInMillis() - time);
              System.out.println(l.get(0));
               Object[] row = (Object[]) l.get(0);
             
               OwdOrder order = (OwdOrder) row[0];
               System.out.println(order.getOrderId());
               

           /* String orderFinderSQL = "select distinct order_id from owd_order (NOLOCK) " +
                "join owd_line_item l (NOLOCK) on order_id=order_fkey and inventory_num= :skuRef where post_date is null and " +
                "is_void=0 and is_backorder=1 and client_fkey= :clientID and is_future_ship=0 and order_refnum = :orderRef ";
            Query findOrder = sess.createSQLQuery(orderFinderSQL);
        System.out.println("5");
        findOrder.setParameter("skuRef",skuRef);
        findOrder.setParameter("clientID",clientID);
        findOrder.setParameter("orderRef", orderRef);
          System.out.println("6");

          List result = findOrder.list();
           System.out.println("7");

          System.out.println(Calendar.getInstance().getTimeInMillis() - time+ " time to done");*/


        /*while (rs.next()) {
            System.out.println("got order id " + rs.getString(1));
            osVector = OrderStatus.getOrderStatusByKey(OrderStatus.kByOrderID, rs.getString(1), clientID, false);


        }*/
        System.out.println("Found order");
        
      /*  if (result.size() != 1) throw new Exception("Error looking up order for reference " + orderRef + " - review with AM!");

        Object res = (Object) result.get(0);

        System.out.println(res.toString());*/
          Integer orderId = Integer.valueOf(order.getOrderId());
       // Vector osVector =  OrderStatus.getOrderStatusByKey(OrderStatus.kByOrderID, res.toString(), clientID, false);
        //add to list of confirmed orders
       // OrderStatus os = (OrderStatus) osVector.elementAt(0);
        /* String statusSQL = "select order_id, order_num, order_status,is_backorder, count(line_item_id), shipped_on from owd_order(NOLOCK), owd_line_item(NOLOCK) \n" +
                 "where order_id = order_fkey and order_id = :orderId and is_void = 0\n" +
                 " group by order_id, order_num, order_status, is_backorder, shipped_on";
         Query status = sess.createSQLQuery(statusSQL);
        status.setParameter("orderId",orderId);


        List stats = status.list();*/
        int lines = 0;

        Integer owdRefNum;
        String orderStatus = "??????";


           owdRefNum = Integer.valueOf(order.getOrderNum());
            lines = order.getLineitems().size();
            System.out.println(order.getBackorderLevel());

                orderStatus = order.getOrderStatus();
           


            if(lines<3) throw new Exception("Insufficient lines in order to fulfill kit - review and edit order!");



       boolean scansOK = false;
       String lookupExistingSql = " from WarehouseStickerRelease where clientId = :clientID and orderRefNum = :orderRef and stickerRef = :stickerRef and done = 0 ";

       Query exestingEntry = sess.createQuery(lookupExistingSql);
        exestingEntry.setParameter("clientID",Integer.valueOf(clientID));
        System.out.println("2");
         exestingEntry.setParameter("orderRef",orderRef);
        System.out.println("9");
          exestingEntry.setParameter("stickerRef",Integer.valueOf(stickerRef));
        System.out.println("10");
        List resExisting = exestingEntry.list();
        System.out.println("Done lookeing for existing");
        WarehouseStickerRelease stick ;
        if(resExisting.size()>0){
                                   System.out.println("doing existing");
            
           // String exestingId = getFirstElement(resExisting);
           // String updateExestingSql = "update warehouse_sticker_release set qty = qty + 1 where id = :id";
            stick = (WarehouseStickerRelease) resExisting.get(0);
            System.out.println("loaded");
          stick.setQty(stick.getQty()+1);
            System.out.println("Added qty");
            sess.saveOrUpdate(stick);
            System.out.println("saved");

        } else{
            System.out.println("Trying new");
            stick = new WarehouseStickerRelease();
            stick.setClientId(Integer.valueOf(clientID));
            stick.setOrderId(orderId);
            stick.setOrderNum(owdRefNum);
            stick.setOrderRefNum(orderRef);
            stick.setQty(1);
            stick.setStatus(orderStatus);
            stick.setStickerRef(Integer.parseInt(stickerRef));
            stick.setDone(0);
           // stick.setInfo("12345678901234567890123456789012345678907777777777777777777777777777777777777777777771234567890");
            stick.setInfo("");
             sess.save(stick);




        }

        System.out.println("stickerref="+stickerRef);
        System.out.println("skuRef="+skuRef);


            long stickerCount = stick.getQty();
        System.out.println(stickerCount);

       // System.out.println("map value = "+((Integer)borOrder.getItemMap().get(stickerRef)).intValue());

            if(stickerCount>0 ) scansOK=true;




        if (scansOK) {
            System.out.println("scan's ok");

            String s = "Ready to post  "+orderStatus+
                               ":"+  orderRef+ ":" +owdRefNum;





            stick.setInfo(s);

              sess.saveOrUpdate(stick);
            System.out.println();
            info = s;

            System.out.println(s);
        } else {
            System.out.println("bad scans");

            String s = "Error order will not be posted  "+orderStatus+
                               ":"+  orderRef+ ":" +owdRefNum;





            stick.setInfo(s);
              sess.saveOrUpdate(stick);

            throw new Exception("Order " + orderRef + " not recognized as valid personalized sticker order. Unit counts may not be correct in order. Please review with AM!");

        }
       System.out.println("returning");
      return  info;


    }

   private static String  getFirstElement(List<Object> l){
       Object[] o = (Object[])l.get(0);
       return o[0].toString();

   }
    public static String setDoneFlag(String orderId, Session sess){
        String sql = "update warehouse_sticker_release set done = 2 where order_id = :orderId";
        Query setdone = sess.createSQLQuery(sql);
        setdone.setParameter("orderId",orderId);
        int rows = setdone.executeUpdate();
        if (rows>0) return "success";
        return "error";
        
    }
    public static String releaseConfirmedOrders(String clientId, String orderId, Map items, Session sess,String groupId) throws Exception {
         //todo
          System.out.println("Client id: "+clientId);
        //for each order
        String clearItems = "update owd_inventory_oh set qty_on_hand=0 from owd_inventory_oh join owd_inventory on" +
                                             " inventory_id=inventory_fkey and is_scan_for_release=1 and client_fkey = :clientId";


        Query clearScanForRelease =  sess.createSQLQuery(clearItems);


           /* PreparedStatement pstmtClear = null;
        pstmtClear = cxn.prepareStatement("update owd_inventory_oh set qty_on_hand=0 from owd_inventory_oh join owd_inventory on" +
                                             " inventory_id=inventory_fkey and is_scan_for_release=1");
*/
        try
        {
                clearScanForRelease.setParameter("clientId",clientId);
                 int rows = clearScanForRelease.executeUpdate();
            System.out.println("rows updated: "+ rows);
            if (rows>0){
            HibUtils.commit(sess);
            } else{
                throw new Exception("Unable to clear Scan for Release Inventory");
            }

        }   catch(Exception ex)
        {
            HibUtils.rollback(sess);
           throw new Exception(ex.getMessage());
            } finally {


            }
          Connection cxn = ConnectionManager.getConnection();


            PreparedStatement pstmtAdd = null;
          
            //PreparedStatement pstmtZero = null;

        Vector osVector =  OrderStatus.getOrderStatusByKey(OrderStatus.kByOrderID, orderId, clientId, false);
        //add to list of confirmed orders
        if (osVector.size()<1) throw new Exception("error looking up order status");
        
        OrderStatus status = (OrderStatus) osVector.elementAt(0);

            System.out.println("Attempting release of order " + status.orderReference);
            try {
                 if(status.post_date != null && !status.post_date.toString().equals("1900-01-01")) throw new Exception("Order already posted!");
                if(status.is_void) throw new Exception("Order voided!");

                //partial-ship release it
                              /* pstmtZero = cxn.prepareStatement("update owd_inventory_oh set qty_on_hand=0 from owd_inventory_oh join owd_inventory on" +
                                       "inventory_id=inventory_fkey and client_fkey=? where inventory_num=?");*/
                               pstmtAdd = cxn.prepareStatement("update owd_inventory_oh " +
                                     "set qty_on_hand = ? from owd_inventory_oh join owd_inventory on" +
                                       " inventory_id=inventory_fkey and client_fkey=? where inventory_num=?");



                 Iterator it = items.keySet().iterator();
                while(it.hasNext())
                {
                    String sku = (String) it.next();
                    System.out.println("Adding inventory to item "+sku);
                    pstmtAdd.setInt(1,Integer.valueOf(items.get(sku).toString()));
                    pstmtAdd.setInt(2,new Integer(clientId));
                    pstmtAdd.setString(3,sku);
                    pstmtAdd.execute();
                }
                HibUtils.commit(sess);
                //look up order
                //move inventory

               //  OrderUtilities.updateLineItemsForAvailability(cxn,status.items, OrderXMLDoc.kPartialShip,true,true);

                System.out.println("shipping order");

             //todo uncomment
            String backorder_ref = OrderUtilities.shipExistingOrder(cxn, status, groupId);     //commits transaction
            if(backorder_ref==null) {backorder_ref = "";}


                cxn.commit();
                cxn.close();
                

                return ("Order ("+status.OWDorderReference+"/"+status.orderReference+") Released!"+(backorder_ref.length()>0?"; Backorder created as reference "+backorder_ref:""));

            } catch (Exception ex) {
                try {
                    cxn.rollback();
                } catch (Exception exa) {
                }
                try {
                    pstmtAdd.close();
                } catch (Exception exa) {
                }
                try {
                //    pstmtZero.close();
                } catch (Exception exa) {
                }
                try {
                   cxn.close();
                } catch (Exception exa) {
                }
              return ("Error releasing order: " + ex.getMessage());
            } finally {
                try {
                    pstmtAdd.close();
                } catch (Exception exa) {
                }
                try {
                  //  pstmtZero.close();
                } catch (Exception exa) {
                }
                try {
                   cxn.close();
                } catch (Exception exa) {
                }
            }




   }

       public static void main(String[] args){
           try{
               Session sess = HibernateSession.currentSession();
        System.out.println(addScannedOrderToConfirmedList(sess,"639831","23656","23654","160"));
           System.out.println("La ti da ti da");






               HibUtils.commit(HibernateSession.currentSession());

              /* Map<String,borOrderInfo> m = loadOrderInfo("160",sess);
               for(borOrderInfo k: m.values()){
                System.out.println(k.getOrderId());
                   System.out.println(k.getItems().size());
                   System.out.println("SSSSSSSSS");
               }*/
           } catch (Exception e){
               e.printStackTrace();
           }

       }

    public static Map loadOrderInfo(String clientId, Session sess){
        Map<String,borOrderInfo> l = new LinkedHashMap<String,borOrderInfo>();
        String lookupStickersSQL = "select order_id, order_num, sticker_ref, qty,info,order_ref_num from warehouse_sticker_release  (NOLOCK) where client_id = :clientId and done = 0 order By id";
        Query lookup = sess.createSQLQuery(lookupStickersSQL);
        lookup.setParameter("clientId",clientId);
        List stickers = lookup.list();
         for (int i = 0; i<stickers.size();i++) {
             Object[] row = (Object[]) stickers.get(i);
             System.out.println(row[0]);
             if(l.containsKey(row[0].toString())){
               l.get(row[0].toString()).getItems().put(row[2].toString(),row[3].toString());
             }else{
                 System.out.println("elseing");
               borOrderInfo b = new borOrderInfo();
                b.setClientId(clientId);
                 b.setInfo(row[4].toString());
                 b.setOrderRefnum(row[5].toString());
                 b.setOrderId(row[0].toString());
                 b.setOrderNum(row[1].toString());
                 Map m = new TreeMap();
                 m.put(row[2].toString(), row[3].toString());
                 b.setItems(m);
                 l.put(b.getOrderId(),b);
             }

         }


        


        return l;
    }

}