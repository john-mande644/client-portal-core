package com.owd.dc.packing.printserver;

import com.owd.core.Mailer;
import com.owd.core.managers.ScanManager;
import com.owd.dc.packing.printserver.beans.facilities;
import com.owd.dc.packing.printserver.beans.printRequest;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdOrder;
import com.owd.hibernate.generated.ScanOrder;
import org.hibernate.Query;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: danny
 * Date: 4/14/14
 * Time: 9:02 AM
 * To change this template use File | Settings | File Templates.
 */
public class printserverUtils {
         public static void main(String[] args){
             try{
                //System.out.println(getTotalInQueueFacility("DC1"));
              //  printRequest pr = loadPrintRequest("DC1","DC1COLOR","50");
               // System.out.println(getFileOrderID("7273170"));
                 /*updateProcessTrackingTime("printCheck","DC1");
                 updateProcessTrackingTime("printCheck","DC6");
                 updateProcessTrackingTime("printCheck","DC7");*/
                System.out.println(getOrdersToPrint("DC1","DC1COLOR1","50"));

              //  printRequest pr = getFacilities();
               /*  System.out.println(pr.getSummary().getPrintQueueTotal());
                System.out.println(pr.getSummary().getPrintDetails().get(0).getPrinterName());
                 XStream x = new XStream();
                 x.alias("detailedSummary",printRequest.detailedPrintSummary.class);
                x.alias("printOrder",printRequest.printRequestOrders.class) ;
                 x.alias("printRequest",printRequest.class);
                 x.alias("location",String.class);
                 System.out.println(x.toXML(pr));*/
                 //reportPackSlipPrinted("5816460");

             }catch(Exception e){
                 e.printStackTrace();
             }
         }
     public static printRequest getFacilities() throws Exception {
         printRequest pr = new printRequest();
           pr.setFacilityList(loadAllFacilities());


         return pr;
     }
    public static void updateProcessTrackingTime(String process, String facility){

        try{
          String sql = "select id from app_data where project = 'wms' and description ='printserver' and variable = :process and value=:facility";
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            q.setParameter("process",process);
            q.setParameter("facility",facility);
            List ids = q.list();
            if(ids.size()>0){
                System.out.println("WE have found one update it");
                String update = "update app_data set display = getDate() where id = :id";
                Query qupdate = HibernateSession.currentSession().createSQLQuery(update);
                qupdate.setParameter("id",ids.get(0).toString());
                qupdate.executeUpdate();
            } else{
                System.out.println("not found insert one");
               String insert = "INSERT\n" +
                       "INTO\n" +
                       "    dbo.app_data (project, description, VARIABLE,value,display) values ('wms','printserver',:process,:facility,getDate())";
               Query qinsert = HibernateSession.currentSession().createSQLQuery(insert);
                qinsert.setParameter("process",process);
                qinsert.setParameter("facility",facility);
                qinsert.executeUpdate();
            }
            HibUtils.commit(HibernateSession.currentSession());

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Error updating process silly isnt it");
            System.out.println(process);
            System.out.println(facility);
        }


    }
    public static printRequest loadPrintRequest(String facility,String printer,String qty) throws Exception{
        updateProcessTrackingTime("printCheck",facility);
        System.out.println("in load Print Request");
    printRequest pr = new printRequest();
        System.out.println("1");
        printRequest.printSummary ps = new printRequest.printSummary();
        System.out.println("2");
       ps.setPrintQueueTotal(getTotalInQueueFacility(facility));
        System.out.println("3");
       ps.setPrintDetails(getDetailedSummary(facility));
        System.out.println("4");
        pr.setSummary(ps);
        System.out.println("5");
        pr.setOrdersToPrint(getOrdersToPrint(facility,printer,qty));


      return pr;
    }
    public static void reportPackSlipPrinted(String printID) throws Exception {
        String sql = "delete from owd_print_queue_sl where print_queue_id = :printID";
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("printID",printID);

        if(q.executeUpdate()<1){
            throw new Exception("Unable to remove the packslip print entry");
        }
        HibUtils.commit(HibernateSession.currentSession());



    }
    public static byte[] getFileOrderID(String orderID) throws Exception{
        OwdOrder order = (OwdOrder) HibernateSession.currentSession().load((OwdOrder.class),Integer.parseInt(orderID));
        //get the scans

        Collection scans = order.getScanDocs();
        if(scans !=null){
            Iterator it2 = scans.iterator();
            if(it2.hasNext()){
                ScanOrder so = (ScanOrder) it2.next();
              System.out.println("getting file" + so.getName());

                byte[] fileBytes = ScanManager.getScanFromOwdOrder(order, so.getName());

                return fileBytes;

            }

            throw new Exception("No scans were found");
        }else{

            throw new Exception("No scans were found");
        }


    }
    private static List<printRequest.printRequestOrders> getOrdersToPrint(String facility, String printerName, String qty) throws Exception{
        List<printRequest.printRequestOrders> pro= new ArrayList<printRequest.printRequestOrders>();
                   String sql = "SELECT top 50\n" +
                           "    dbo.owd_print_queue_sl.print_queue_id,\n" +
                           "    dbo.owd_print_queue_sl.printer_name,\n" +
                           "    dbo.owd_print_queue_sl.order_id\n" +
                           "FROM\n" +
                           "    dbo.owd_print_queue_sl\n" +
                           "INNER JOIN dbo.owd_order\n" +
                           "ON\n" +
                           "    (\n" +
                           "        dbo.owd_print_queue_sl.order_id = dbo.owd_order.order_id\n" +
                           "    )\n" +
                           "WHERE\n" +
                           "    dbo.owd_order.facility_code = :facility\n" +
                           "AND dbo.owd_print_queue_sl.printer_name = :printerName \n" +
                           "order by print_queue_id\n" +
                           ";";
                  Query q = HibernateSession.currentSession().createSQLQuery(sql);
                 // q.setInteger("qty",Integer.parseInt(qty));
                   q.setParameter("facility",facility);
        q.setParameter("printerName",printerName);
           List results = q.list();
        for(Object row:results){
            Object[] data = (Object[]) row;
            printRequest.printRequestOrders porder = new printRequest.printRequestOrders();
            porder.setQueueId(data[0].toString());
            porder.setPrinterName(data[1].toString());
            porder.setOrderId(data[2].toString());
            System.out.println("Set info going to load OWD order object");
            OwdOrder order = (OwdOrder)HibernateSession.currentSession().load(OwdOrder.class,Integer.parseInt(data[2].toString()));
            System.out.println("Going to do the scandocs");
            boolean add = true;
            if(order.getScanDocs().size()>0){

                Iterator it2 = order.getScanDocs().iterator();
                if(it2.hasNext()) {
                    ScanOrder so = (ScanOrder) it2.next();
                    while (so.getType().equals("RETURN_LABEL")) {
                        if (it2.hasNext()) {
                            so = (ScanOrder) it2.next();
                        } else {
                            System.out.println("Delete and recreate: " + porder.getOrderId());
                            deleteFromLivePrintQueueAddToReCreate(porder.getOrderId());
                            //set add to false, so we don't add this to results and try to print something we don't want
                            add = false;
                            //break out of loop to prevent infinite loop
                            break;
                        }
                    }
                    if(add) {
                        porder.setFileName(so.getName());
                    }
                }

            if(add) {
                pro.add(porder);
            }
            }else {
               // throw new Exception("No file has been found for this order, it needs to be reset: " + porder.getOrderId());
               deleteFromLivePrintQueueAddToReCreate(porder.getOrderId());
            }

        }
        System.out.println("Done loop");
        return pro;
    }
    private static void  deleteFromLivePrintQueueAddToReCreate(String orderId){
        try{
           // Mailer.sendMail("Reseting packginslip",orderId,"dnickels@owd.com","printserver@owd.com");
            System.out.println(orderId);
            String sqlDelete = "delete from owd_print_queue_sl where order_id = :orderId";
            Query q = HibernateSession.currentSession().createSQLQuery(sqlDelete);
            q.setParameter("orderId",orderId);
            int i = q.executeUpdate();
            System.out.println(i);

            if(i>0){
                System.out.println("We are going to re-add to printqueue");
                String sql = "insert into owd_print_queue3 (order_id, client_id, printer_name, sla, pdf_binary) select top 1 order_id, client_id, printer_name, dbo.udf_getsladate(order_id), pdf_binary from zzPrintedSlips where order_id = :orderId order by deleted_date DESC";
                Query qq = HibernateSession.currentSession().createSQLQuery(sql);
                System.out.println("Created query");
                qq.setParameter("orderId",orderId);
                qq.executeUpdate();
                System.out.println("Query complete");
                HibUtils.commit(HibernateSession.currentSession());
                System.out.println("Query committed");
            }



        } catch (Exception e){
            e.printStackTrace();
        }
    }
    private static List<printRequest.detailedPrintSummary> getDetailedSummary(String facility) throws Exception{
        List<printRequest.detailedPrintSummary>   dps = new ArrayList<printRequest.detailedPrintSummary>();

        String sql = "SELECT\n" +
                "    dbo.owd_print_queue_sl.printer_name,\n" +
                "    COUNT(dbo.owd_print_queue_sl.print_queue_id)\n" +
                "FROM\n" +
                "    dbo.owd_print_queue_sl\n" +
                "INNER JOIN dbo.owd_order\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.owd_print_queue_sl.order_id = dbo.owd_order.order_id\n" +
                "    )\n" +
                "WHERE\n" +
                "    dbo.owd_order.facility_code = :facility\n" +
                "GROUP BY\n" +
                "    dbo.owd_print_queue_sl.printer_name ;";
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("facility",facility);
        List results = q.list();
        for(Object row : results){
            Object[] data = (Object []) row;
            printRequest.detailedPrintSummary sum = new printRequest.detailedPrintSummary();
            sum.setPrinterName(data[0].toString());
            sum.setQty(data[1].toString());
            dps.add(sum);
        }


       return dps;
    }
    private static String getTotalInQueueFacility(String facility) throws Exception{
        //get the total order that have been released to print for the facility
        String sql = "SELECT\n" +
                "    COUNT(dbo.owd_print_queue_sl.print_queue_id)\n" +
                "FROM\n" +
                "    dbo.owd_print_queue_sl\n" +
                "INNER JOIN dbo.owd_order\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.owd_print_queue_sl.order_id = dbo.owd_order.order_id\n" +
                "    )\n" +
                "WHERE\n" +
                "    dbo.owd_order.facility_code = :facility ;";
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("facility",facility);

        return q.list().get(0).toString();



    }
    private static facilities loadAllFacilities() throws Exception{
        facilities f = new facilities();
                 String sql = "select loc_code from owd_facilities where is_active = 1";
Query q = HibernateSession.currentSession().createSQLQuery(sql);
        List results = q.list();
        List<String> locs = new ArrayList<String>();
        for (Object row: results){
            locs.add(row.toString());
        }
                                     f.setOWDFacilities(locs);
        return f;
    }



}
