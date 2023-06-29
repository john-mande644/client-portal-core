package com.owd.core.business.order.shippingAdjustments;

import com.owd.core.business.order.Event;
import com.owd.core.business.order.OrderStatus;
import com.owd.core.business.order.beans.shortShipBean;
import com.owd.core.business.order.beans.shortShipResultsBean;
import com.owd.core.business.order.shippingAdjustments.clientInterfaces.fohShortShipService;
import com.owd.core.dbUtilities;
import com.owd.core.managers.FacilitiesManager;
import com.owd.core.managers.InventoryManager;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.*;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.engine.spi.SessionImplementor;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by danny on 9/2/2019.
 */
public class shortShipUtilities {
    private final static Logger log =  LogManager.getLogger();
    public static void main(String[] args){

        try{
            //System.setProperty("com.owd.environment","test");
            shortShipBean ss = new shortShipBean();
            ss.setLineItemId(44350926);
            ss.setQtyToAdjust(1);
            ss.setReason("stockout");
            List<shortShipBean> l = new ArrayList<>();
            l.add(ss);
            processShortShipRequest(l, "DannyTest", 640, 21325578);
          //  makeAdjustments(l,640,19190069,"Danny");
           // generateOrQueuePackingSlip(640,19190069);

        }catch (Exception e){
           e.printStackTrace();
        }


    }



    public static shortShipResultsBean processShortShipRequest(List<shortShipBean> items, String user, Integer clientId, Integer orderId) throws Exception{

        shortShipResultsBean resultsBean = new shortShipResultsBean();
        //Verify we are not caneling more that we have released.
        for(shortShipBean item: items){
            verifyAdjustNotGreaterThanActual(item);
        }

        //Process client requests.

        if(!transmitClientData(items, clientId, orderId)){
            throw new Exception("Unable to transmit changes to client. Please try again later");
        }


            log.debug(items);
       resultsBean.setSuccess(makeAdjustments(items,clientId,orderId,user));

        boolean goodOrder = checkAndPutOrderOnHoldIfNeeded(orderId,user);



        if(goodOrder){
            log.debug("Order Still has items, checking for new packing slip if needed");
            String url = generateOrQueuePackingSlip(clientId,orderId);
            resultsBean.setMessage("Successfully modified order");
            resultsBean.setUrl(url);
        }else{
            resultsBean.setMessage("Order Un-posted because no line items left to ship.");
        }

        HibernateSession.currentSession().getTransaction().commit();


        return resultsBean;
    }
    private static String getNotes(List<shortShipBean> items) throws Exception{
        StringBuilder sb = new StringBuilder();

        sb.append("The following Sku's and Qty's were removed from the order:\r\n");
        for(shortShipBean s:items){
            OwdLineItem item = (OwdLineItem) HibernateSession.currentSession().load(OwdLineItem.class,s.getLineItemId());
            sb.append(item.getInventoryNum());
            sb.append(": ");
            sb.append(s.getQtyToAdjust());
            sb.append("\r\n");

        }

        return sb.toString();
    }

    private static boolean removeDCHold(List<shortShipBean> items, Integer orderId, String user) throws Exception{
        boolean success = false;
        OwdOrder order = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class,orderId);
        OwdOrderShipHold holder = order.getHoldinfo();
        log.debug("Checking for hold and removing");
        if(null!= holder) {
            if (holder.getId() > 0) {
                holder.setClearedBy(user);
                holder.setResNote(getNotes(items));
                holder.setResolutionDate(Calendar.getInstance().getTime());
                holder.setResolutionType("Short Shipped");
                holder.setClearedDate(Calendar.getInstance().getTime());


                holder.setIsOnWhHold(new Integer("0"));
                HibernateSession.currentSession().saveOrUpdate(holder);


            } else {
                success = true;
            }
        }else{
            success = true;
        }

        return  success;
    }

    private static boolean makeAdjustments(List<shortShipBean> items, Integer clientId, Integer orderId, String user) throws Exception{

        boolean success = false;
        StringBuilder notes = new StringBuilder();
        Map<OwdLineItem, Integer> stockouts = new HashMap<>();
        try{
             // HibernateSession.currentSession().getTransaction().begin();

            //Enter exemptions first, track stockouts to process
            for(shortShipBean item: items){
                OwdLineItem li = (OwdLineItem) HibernateSession.currentSession().load(OwdLineItem.class,item.getLineItemId());
                li.setQuantityActual(li.getQuantityActual()-item.getQtyToAdjust());
                li.setQuantityRequest(li.getQuantityRequest()-item.getQtyToAdjust());
                HibernateSession.currentSession().saveOrUpdate(li);
                OwdLineItemExemptions lie = new OwdLineItemExemptions();
                lie.setOrderFkey(orderId);
                lie.setInventoryNum(li.getInventoryNum());
                lie.setVendorSku("");
                lie.setExemptionCode("quantity");
                lie.setExemptionValue(item.getReason());
                lie.setQty(item.getQtyToAdjust());
                lie.setMerchantLineNumber(li.getCustRefnum());
                HibernateSession.currentSession().saveOrUpdate(lie);
                notes.append("Removed qty ");
                notes.append(item.getQtyToAdjust());
                notes.append(" for Sku: ");
                notes.append(li.getInventoryNum());
                notes.append("\n");

                if(item.getReason().equalsIgnoreCase("stockout")){
                    stockouts.put(li,item.getQtyToAdjust());
                }

            }
            HibUtils.commit(HibernateSession.currentSession());

            if(stockouts.size()>0){
              processStockouts(stockouts,user,clientId,orderId);
            }
            removeDCHold(items,orderId,user);
            HibUtils.commit(HibernateSession.currentSession());
           //

            success = true;

        }catch (Exception e){

            try{
               HibernateSession.currentSession().getTransaction().rollback();
            }catch (Exception ex){

            }
            throw new Exception("Unable to process Short Ship, please evaluate and try again: "+ e.getMessage());
        }


        return success;
    }

    private static boolean processStockouts(Map<OwdLineItem,Integer> stockouts, String user, Integer clientId, Integer orderId) throws Exception{
        boolean success = false;

        try{
            OwdOrder order = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class,orderId);
            OwdReceive rcv = new OwdReceive();
            Calendar cal = Calendar.getInstance();
            rcv.setCarrier("UPS Ground");
            rcv.setCreatedBy("Inventory Count");
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-DD HH:MM:ss");
            rcv.setCreatedDate(cal.getTime());
            rcv.setDriver("");
            rcv.setIsVoid(false);
            rcv.setNotes("ShortShip");
            rcv.setOwdClient((OwdClient) HibernateSession.currentSession().load(OwdClient.class, clientId));
            rcv.setReceiveDate(cal.getTime());
            rcv.setReceiveUser(user);
            rcv.setRefNum("ShortShip:" + order.getOrderRefnum());
            rcv.setTimeIn(cal.getTime());
            rcv.setTimeOut(cal.getTime());
            rcv.setType("Adjustment");
            rcv.setFacilityCode(order.getFacilityCode());
            HibernateSession.currentSession().save(rcv);
            for(OwdLineItem li:stockouts.keySet()){
                OwdInventory inv = InventoryManager.getOwdInventoryForClientAndSku(clientId+"", li.getInventoryNum());


                OwdReceiveItem ri = new OwdReceiveItem();

                ri.setCreatedBy(user);
                ri.setCreatedDate(cal.getTime());
                ri.setDescription(inv.getDescription());
                ri.setInventoryNum(inv.getInventoryNum());
                ri.setItemStatus("New");
                ri.setOwdInventory(inv);
                ri.setIsVoid(0);
                ri.setQuantity(stockouts.get(li)*-1);
                ri.setOwdReceive(rcv);
                HibernateSession.currentSession().save(ri);


                System.out.println("Doing: " + li.getInventoryNum());

                OwdInventoryHistory ohh = new OwdInventoryHistory();
                ohh.setInventoryFkey(inv.getInventoryId());
                ohh.setReceiveItemFkey(ri.getReceiveItemId());
                ohh.setQtyChange(stockouts.get(li)*-1);
                ohh.setNote("order.ShortShip");
                ohh.setFacility(FacilitiesManager.getFacilityForCode(order.getFacilityCode()));
                HibernateSession.currentSession().save(ohh);

                HibernateSession.currentSession().evict(li);

            }
            rcv.setTransactionNum("OWDADJ-"+ dbUtilities.getNextID(((SessionImplementor) HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection(), "Receive"));

            HibernateSession.currentSession().save(rcv);



        }catch (Exception e){
            e.printStackTrace();
            throw new Exception("Unable to post adjustments, please retry: "+e.getMessage());
        }


        return success;
    }

    private static String generateOrQueuePackingSlip(Integer clientId, Integer orderId) throws Exception{
        String url = "";

        // Add everything to print queue for now for generation.
        if(insertToShortShipPrintQueue(clientId,orderId)){
            return "";
        }else{
            throw new Exception("Error inserting order for packing slip regeneration, please contact IT");
        }
      /*  switch ( clientId){
            case 640:
                return fohShortShipService.processPackingSlip(orderId);
            default:
                return url;
        }*/
        //return url;
    }

    private static boolean insertToShortShipPrintQueue(Integer clientId, Integer orderId) throws Exception{
        boolean success = false;
        OwdOrder order = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class, orderId);
        String sql = "INSERT\n" +
                "INTO\n" +
                "    owd_print_queue_shortShip\n" +
                "    (\n" +
                "        order_id,\n" +
                "        client_id,\n" +
                "        created,\n" +

                "        facility\n" +
                "    )\n" +
                "    VALUES\n" +
                "    (\n" +
                "       \n" +
                "        :orderId,\n" +
                "        :clientId,\n" +
                "        getDate(),\n" +

                "        :facility\n" +
                "    );";
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("orderId",orderId);
        q.setParameter("clientId",clientId);
        q.setParameter("facility",order.getFacilityCode());

        int i = q.executeUpdate();
        if(i>0){
            success = true;
            HibUtils.commit(HibernateSession.currentSession());
        }

        return success;
    }

    private static boolean transmitClientData(List<shortShipBean> items, Integer clientId, Integer orderId) throws Exception{

        switch (clientId){
            case 640:
                return true;
            default:
                return true;
        }


    }




    private static boolean verifyAdjustNotGreaterThanActual(shortShipBean item) throws Exception{
        OwdLineItem li = (OwdLineItem) HibernateSession.currentSession().load(OwdLineItem.class, item.getLineItemId());
        if(item.getQtyToAdjust()<= li.getQuantityActual()){
            return true;
        }
        throw new Exception("Unable to ship less than quantity releases for "+ li.getInventoryNum()+". Tried to short ship " + item.getQtyToAdjust() + " with released qty of "+li.getQuantityActual());


    }

    private static boolean checkAndPutOrderOnHoldIfNeeded(Integer orderId, String user) throws  Exception{
        boolean success = false;
        OrderStatus status = new OrderStatus(orderId+"");

        if(status.getLineCount()>0){
            success = true;
        }else{
            status.unpostOrder();
            Event.addOrderEvent(orderId, Event.kEventTypeHandling, "Order unposted because short ship process removed all line items", user);

        }



        return success;
    }
}
