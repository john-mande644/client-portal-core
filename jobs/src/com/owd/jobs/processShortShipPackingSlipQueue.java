package com.owd.jobs;

import com.owd.core.business.order.shippingAdjustments.clientInterfaces.fohShortShipService;
import com.owd.core.managers.ScanManager;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdOrder;
import com.owd.hibernate.generated.ScanOrder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Query;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Created by danny on 2/5/2020.
 */
public class processShortShipPackingSlipQueue extends OWDStatefulJob{
    private final static Logger log = LogManager.getLogger();

    public static void main(String[] args){
        run();
    }

    public void internalExecute(){

        try{
        String sql = "select order_id, client_id,printQueueId from owd_print_queue_shortShip where processed = 0";
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            List l = q.list();
            log.debug("Found " + l.size() + " to process packing slips for Short Ship");
            for(Object result:l){
                Object[] data = (Object[]) result;
                generateOrQueuePackingSlip(Integer.parseInt(data[0].toString()),data[2]);
            }



        }catch (Exception e){

            e.printStackTrace();

        }






    }

    private static void generateOrQueuePackingSlip(Integer orderId,Object printQueueId) throws Exception{
        String url = "";

        //If null printQueueID insert into print queue
        if(null == printQueueId){
            System.out.println("no ID, so inserting record:" +orderId);
            insertIntoPrintQueue(orderId);

        }else{
            //If printQueueID is not null check if processed fully and delete if needed. and Mark Processed.
            System.out.println("Checking for packingSlipGeneration: " + printQueueId);
            checkForCreatedAndRemoveFromPrintQueue(printQueueId.toString(),orderId);
        }





    }

    private static void checkForCreatedAndRemoveFromPrintQueue(String printQueueId,Integer orderId){
        try{
            String sql = "select order_id from owd_print_queue3 where fileDownloaded = 1 and print_queue_id = :printQueueId";
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            q.setParameter("printQueueId",printQueueId);
            List l = q.list();
            if(l.size()>0){
                //This returned results so they have been created. Set flag and remove printqueue record
                Query qq = HibernateSession.currentSession().createSQLQuery("update owd_print_queue_shortShip set processed = 1 where printQueueId = :printQueueId ");
                qq.setParameter("printQueueId",printQueueId);
                qq.executeUpdate();

                qq = HibernateSession.currentSession().createSQLQuery("delete from owd_print_queue3 where print_queue_id = :printQueueId");
                qq.setParameter("printQueueId",printQueueId);
                qq.executeUpdate();

                Query qAVS = HibernateSession.currentSession().createSQLQuery("update owd_order_ship_info set avs_overide = 0 where order_Fkey = :orderFkey");
                qAVS.setParameter("orderFkey",orderId);
                qAVS.executeUpdate();

                OwdOrder order = (OwdOrder)HibernateSession.currentSession().load(OwdOrder.class,orderId);

                //get file data and update
                 //get the scans

                Collection scans = order.getScanDocs();
                if(scans !=null) {
                    Iterator it2 = scans.iterator();
                    if (it2.hasNext()) {
                        ScanOrder so = (ScanOrder) it2.next();
                        while (so.getType().equals("RETURN_LABEL")) {
                            if (it2.hasNext()) {
                                so = (ScanOrder) it2.next();
                            }
                        }
                        System.out.println("getting file" + so.getName());
                        byte[] fileBytes = ScanManager.getScanFromOwdOrder(order, so.getName());
                        Query qqq = HibernateSession.currentSession().createSQLQuery("update owd_print_queue_shortShip " +
                                "set pdf_data = :pdf where printQueueId = :printQueueId ");
                        qqq.setParameter("printQueueId",printQueueId);
                        qqq.setBinary("pdf",fileBytes);
                        qqq.executeUpdate();

                    }
                }


                HibUtils.commit(HibernateSession.currentSession());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void insertIntoPrintQueue(Integer orderId){
        try{

            Query q = HibernateSession.currentSession().createSQLQuery("execute insertShortShipOrderIntoPrintQueue :orderId");
            q.setParameter("orderId",orderId);

            List l = q.list();
            String printId = l.get(0).toString();
            Query qq = HibernateSession.currentSession().createSQLQuery("update owd_print_queue_shortShip  set printQueueId = :printId where printQueueId is null and order_id = :orderId ");
            qq.setParameter("orderId",orderId);
            qq.setParameter("printId",printId);
            qq.executeUpdate();

            Query qAVS = HibernateSession.currentSession().createSQLQuery("update owd_order_ship_info set avs_overide = 1 where order_Fkey = :orderFkey");
            qAVS.setParameter("orderFkey",orderId);
            qAVS.executeUpdate();
            HibUtils.commit(HibernateSession.currentSession());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
