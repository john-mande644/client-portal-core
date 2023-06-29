package com.owd.dc.warehouse.packSlipRelease;

import com.opensymphony.xwork2.ActionSupport;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdClient;
import org.apache.struts2.interceptor.SessionAware;
import org.hibernate.Query;
import org.hibernate.Session;

import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: danny
 * Date: 6/25/13
 * Time: 2:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class AdvancedAction extends ActionSupport implements SessionAware {
    private String facility;
    private List<groupPrintBean> clientList;
    private String clientId;

    private Map sessionAttributeMap;
    private List<clientDetailsBean> details;
    private boolean hasGroup;
    private boolean sortRack;
    private Map<String,String> singlePrinters;
    private List<String> skusInPrintQueue;
    private String clientName;
    private String oldOnesOnly= "no";
    private List<detailedReprintBean> detailedReprintData;
    private List<String> clients;
    private List<String> slas;
    private List<String> shipMethods;
    private List<String> groupNames;
    private List<String> units;
    private List<String> aisles;
    private List<String> boxTypes;
    private List<String> zones;
    private String printer;
    private String orderIds;
    private InputStream inputStream;
    public InputStream getInputStream() {
        return inputStream;
    }
    SimpleDateFormat dff = new SimpleDateFormat("yyyy-M-d");

    public void setSession(Map map) {
        System.out.println("setting PackSlipRelease session map:"+map);
        sessionAttributeMap = map;
        if(sessionAttributeMap.get("owd_current_location")==null)
        {
            sessionAttributeMap.put("owd_current_location",sessionAttributeMap.get("owd_default_location"));
        }
    }

    private void setSkuList(String clientId, String facility, Session sess) throws Exception{
         String sql = "SELECT DISTINCT\n" +
                 "    dbo.owd_line_item.inventory_num\n" +
                 "FROM\n" +
                 "    dbo.owd_order\n" +
                 "INNER JOIN dbo.owd_order_ship_info\n" +
                 "ON\n" +
                 "    (\n" +
                 "        dbo.owd_order.order_id = dbo.owd_order_ship_info.order_fkey\n" +
                 "    )\n" +
                 "INNER JOIN dbo.owd_print_queue3\n" +
                 "ON\n" +
                 "    (\n" +
                 "        dbo.owd_order.order_id = dbo.owd_print_queue3.order_id\n" +
                 "    )\n" +
                 "INNER JOIN dbo.owd_line_item\n" +
                 "ON\n" +
                 "    (\n" +
                 "        dbo.owd_order.order_id = dbo.owd_line_item.order_fkey\n" +
                 "    )\n" +
                 "WHERE\n" +
                 "    dbo.owd_line_item.is_parent = 0\n" +
                 "AND dbo.owd_print_queue3.client_id = :clientId\n" +

                 "AND dbo.owd_order.facility_code = :facility\n" +
                 "AND dbo.owd_print_queue3.isVerified = 1 AND (owd_print_queue3.fileCreated = 1) AND (owd_print_queue3.fileDownloaded = 1) AND (owd_print_queue3.doNotShowInQueue = 0);";
        String othersql = "SELECT DISTINCT\n" +
                "    dbo.owd_line_item.inventory_num\n" +
                "FROM\n" +
                "    dbo.owd_order\n" +
                "INNER JOIN dbo.owd_order_ship_info\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.owd_order.order_id = dbo.owd_order_ship_info.order_fkey\n" +
                "    )\n" +
                "INNER JOIN dbo.owd_print_queue3\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.owd_order.order_id = dbo.owd_print_queue3.order_id\n" +
                "    )\n" +
                "INNER JOIN dbo.owd_line_item\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.owd_order.order_id = dbo.owd_line_item.order_fkey\n" +
                "    )\n" +
                "WHERE\n" +
                "    dbo.owd_line_item.is_parent = 0\n" +

                "AND dbo.owd_order.facility_code = :facility\n" +
                "AND dbo.owd_print_queue3.isVerified = 1 AND (owd_print_queue3.fileCreated = 1) AND (owd_print_queue3.fileDownloaded = 1) AND (owd_print_queue3.doNotShowInQueue = 0);";
      Query q;
        if(clientId.equals("red")||clientId.equals("blue")||clientId.equalsIgnoreCase("business")||clientId.equalsIgnoreCase("prsnl")||clientId.equalsIgnoreCase("gcard")){
            q = sess.createSQLQuery(othersql);
            q.setParameter("facility",facility);
        } else{
            q = sess.createSQLQuery(sql);
            q.setParameter("facility",facility);
            q.setParameter("clientId",clientId);
        }


        List results = q.list();
        skusInPrintQueue = new ArrayList<String>();
        if (results.size()>0){
            for (Object row : results){
            skusInPrintQueue.add(row.toString());

            }

        }
    }
    public String advancedPrint(){

        System.out.println("doing client details");
            System.out.println(clientId);
        System.out.println(sessionAttributeMap.get("owd_current_location").toString());
        System.out.println(clientName);

        try{
            setSkuList(clientId,facility,HibernateSession.currentSession());
          //  details = packSlipUtilities.getDetails(clientId,sessionAttributeMap.get("owd_current_location").toString());
            //hasGroup = packSlipUtilities.clientHasGroupItemsToPrint(clientId,HibernateSession.currentSession(),sessionAttributeMap.get("owd_current_location").toString());
            detailedReprintData = new ArrayList<detailedReprintBean>();

            slas = new ArrayList<String>();
            shipMethods = new ArrayList<String>();
            groupNames = new ArrayList<String>();
            units = new ArrayList<String>();
            clients = new ArrayList<>();
            aisles = new ArrayList<>();
            zones = new ArrayList<>();
            boxTypes = new ArrayList<>();

            System.out.println("going to get the info");
            packSlipUtilities.getOrdersToPrintInfo(facility,clientId,detailedReprintData,slas,shipMethods,groupNames,units,clients,aisles,zones,boxTypes);
        }catch (Exception e){
            e.printStackTrace();
            addActionError(e.getMessage());
        }

        return "success";
    }
    public String doAdvancedPrint(){
        try{
            System.out.println(printer);
            System.out.println(orderIds);
            System.out.println(sortRack);
            if (printer.length()<3) throw new Exception("Invalid Printer was selected");
            packSlipUtilities.printOrdersFromQueueViaId(printer,orderIds,sortRack);
            inputStream = new StringBufferInputStream("WE just printed them to "+printer);
            // fillClientList();
        }catch(Exception e){
            inputStream = new StringBufferInputStream(e.getMessage());
            e.printStackTrace();
        }
        return "success";
    }
    public String groupDetailPrint(){
                     try{
                         System.out.println(printer);
                         System.out.println(orderIds);
                         if (printer.length()<3) throw new Exception("Invalid Printer was selected");
                         packSlipUtilities.reprintOrdersToPrinterViaId(printer,orderIds);
                          inputStream = new StringBufferInputStream("WE just printed them to "+printer);
                        // fillClientList();
                     }catch(Exception e){
                         inputStream = new StringBufferInputStream(e.getMessage());
                         e.printStackTrace();
                     }
        return "success";
    }
    public String doGroupReprint(){
         try{
         System.out.println("This is the Client Id that would be reprinted : " + clientId);
        System.out.println("For Facility" + facility);
             reprintNonPickedForClient(oldOnesOnly);
             System.out.println("hey all here is the value of the oldones" +oldOnesOnly);
        fillClientList();
         }catch (Exception e){
             e.printStackTrace();
             addActionError(e.getMessage());
         }
        return "success";
    }

    public String groupReprint(){
        try{
            System.out.println("helloo");


             fillClientList();

        } catch(Exception e){
            addActionError(e.getMessage());
             e.printStackTrace();

        }




        return "success";
    }
    private void reprintNonPickedForClient(String oldOnesOnly) throws Exception{
        String sql = "WITH ids (print_id,ooId)\n" +
                "as\n" +
                "(\n" +
                "SELECT \n" +
                "    Max(dbo.zzPrintedSlips.print_queue_id),\n" +
                "zzPrintedSlips.order_id\n" +
                "FROM\n" +
                "    dbo.zzPrintedSlips\n" +
                "INNER JOIN dbo.owd_order\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.zzPrintedSlips.order_id = dbo.owd_order.order_id\n" +
                "    )\n" +
                "WHERE\n" +
                "    dbo.zzPrintedSlips.client_id = :clientId\n" +
                "AND dbo.owd_order.order_status = 'At Warehouse' and pick_by is null and facility_code=:facility\n" +
                "group By zzPrintedSlips.order_id\n" +
                ")\n" +
                "insert into owd_print_queue_sl (order_id, client_id, printer_name,pdf_binary,created) \n" +
                " select order_id, client_id,printer_name,pdf_binary,getDate() from zzPrintedSlips, w_order_attributes where print_queue_id in (\n" +
                "select print_id from ids) and order_id = order_fkey\n" +
                "order by fingerprint\n";
        String oldsql = "WITH ids (print_id,ooId)\n" +
                "as\n" +
                "(\n" +
                "SELECT \n" +
                "    Max(dbo.zzPrintedSlips.print_queue_id),\n" +
                "zzPrintedSlips.order_id\n" +
                "FROM\n" +
                "    dbo.zzPrintedSlips\n" +
                "INNER JOIN dbo.owd_order\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.zzPrintedSlips.order_id = dbo.owd_order.order_id\n" +
                "    )\n" +
                "WHERE\n" +
                "    dbo.zzPrintedSlips.client_id = :clientId\n" +
                "AND dbo.owd_order.order_status = 'At Warehouse' and pick_by is null and facility_code=:facility\n" +
                "group By zzPrintedSlips.order_id\n" +
                "Having  MIN(deleted_date)<:theDate \n" +
                ")\n" +
                "insert into owd_print_queue_sl (order_id, client_id, printer_name,pdf_binary,created) \n" +
                " select order_id, client_id,printer_name,pdf_binary,getDate() from zzPrintedSlips, w_order_attributes where print_queue_id in (\n" +
                "select print_id from ids) and order_id = order_fkey\n" +
                "order by fingerprint\n";

       Query q;

        if(oldOnesOnly.equalsIgnoreCase("yes")){
            q = HibernateSession.currentSession().createSQLQuery(oldsql);
            q.setParameter("facility",facility);
            q.setParameter("clientId",clientId);
            q.setParameter("theDate",dff.format(Calendar.getInstance().getTime()));
        } else{
        q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("facility",facility);
        q.setParameter("clientId",clientId);
        }
        q.executeUpdate();
        HibUtils.commit(HibernateSession.currentSession());

    }
   private void fillClientList() throws Exception{
       String oldones =   "WITH ids (print_id,ooId,client)\n" +
               "as\n" +
               "(\n" +
               "SELECT \n" +
               "    Max(dbo.zzPrintedSlips.print_queue_id),\n" +
               "zzPrintedSlips.order_id, client_id\n" +
               "FROM\n" +
               "    dbo.zzPrintedSlips\n" +
               "INNER JOIN dbo.owd_order\n" +
               "ON\n" +
               "    (\n" +
               "        dbo.zzPrintedSlips.order_id = dbo.owd_order.order_id\n" +
               "    )\n" +
               "WHERE\n" +
               "   \n" +
               "dbo.owd_order.order_status = 'At Warehouse' and pick_status = 0 and facility_code = :facility\n" +
               "group By zzPrintedSlips.order_id,client_id \n" +
               "Having  MIN(deleted_date)<:theDate \n" +
               ")\n" +
               "select client,company_name, COUNT(ooId) as count from ids,owd_client where client = client_id group by client,company_name\n" +
               "order by count DESC";

       String sql =  "WITH ids (print_id,ooId,client)\n" +
               "as\n" +
               "(\n" +
               "SELECT \n" +
               "    Max(dbo.zzPrintedSlips.print_queue_id),\n" +
               "zzPrintedSlips.order_id, client_id\n" +
               "FROM\n" +
               "    dbo.zzPrintedSlips\n" +
               "INNER JOIN dbo.owd_order\n" +
               "ON\n" +
               "    (\n" +
               "        dbo.zzPrintedSlips.order_id = dbo.owd_order.order_id\n" +
               "    )\n" +
               "WHERE\n" +
               "   \n" +
               "dbo.owd_order.order_status = 'At Warehouse' and pick_status = 0 and facility_code = :facility\n" +
               "group By zzPrintedSlips.order_id,client_id\n" +
               ")\n" +
               "select client,company_name, COUNT(ooId) as count from ids,owd_client where client = client_id group by client,company_name\n" +
               "order by count DESC";
       //Get the old ones first and add to map
       Query qq = HibernateSession.currentSession().createSQLQuery(oldones);
       qq.setParameter("facility",facility);
       qq.setParameter("theDate",dff.format(Calendar.getInstance().getTime()));
       List oldResults = qq.list();
       Map<String,String> oldCount = new HashMap<String, String>();
       if(oldResults.size()>0){
           for(Object row: oldResults){
               Object[] data = (Object[]) row;
               oldCount.put(data[0].toString(),data[2].toString());
               oldCount.put(data[0].toString(),data[2].toString());
           }
       }

       Query q = HibernateSession.currentSession().createSQLQuery(sql);
       System.out.println("???????????????????????????????????????????????");
       q.setParameter("facility",facility);
       List results = q.list();
       System.out.println(results.size());
       if(results.size()>0){
           clientList = new ArrayList<groupPrintBean>();
           for(Object row : results){
               Object[] data = (Object[]) row;
               groupPrintBean gpb = new groupPrintBean();
               gpb.setClientId(data[0].toString());
               gpb.setClientName(data[1].toString());
               gpb.setCount(data[2].toString());
               if(oldCount.containsKey(gpb.getClientId())){
                   gpb.setOldCount(oldCount.get(gpb.getClientId()));
               } else{
                   gpb.setOldCount("0");
               }
               clientList.add(gpb);

           }


       } else{
           addActionMessage("Nothing left to do for " + facility);
       }
       detailedReprintData = new ArrayList<detailedReprintBean>();
       clients = new ArrayList<String>();
       slas = new ArrayList<String>();
       shipMethods = new ArrayList<String>();
       groupNames = new ArrayList<String>();
       packSlipUtilities.getUnpickedPrintedOrderInfo(facility,detailedReprintData,slas,clients,shipMethods,groupNames);

   }

    public String getFacility() {
        return facility;
    }

    public void setFacility(String facility) {
        this.facility = facility;
    }

    public List<groupPrintBean> getClientList() {
        return clientList;
    }

    public void setClientList(List<groupPrintBean> clientList) {
        this.clientList = clientList;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Map getSessionAttributeMap() {
        return sessionAttributeMap;
    }

    public void setSessionAttributeMap(Map sessionAttributeMap) {
        this.sessionAttributeMap = sessionAttributeMap;
    }

    public List<clientDetailsBean> getDetails() {
        return details;
    }

    public void setDetails(List<clientDetailsBean> details) {
        this.details = details;
    }

    public boolean isHasGroup() {
        return hasGroup;
    }

    public void setHasGroup(boolean hasGroup) {
        this.hasGroup = hasGroup;
    }
    public Map<String, String> getSinglePrinters() {
        if(null == singlePrinters) singlePrinters = packSlipUtilities.getTodayPrinters(sessionAttributeMap.get("owd_current_location")+"");
        return singlePrinters;
    }



    public List<String> getSkusInPrintQueue() {
        return skusInPrintQueue;
    }

    public void setSkusInPrintQueue(List<String> skusInPrintQueue) {
        this.skusInPrintQueue = skusInPrintQueue;
    }

    public void setSinglePrinters(Map<String, String> singlePrinters) {
        this.singlePrinters = singlePrinters;
    }

    public String getOldOnesOnly() {
        return oldOnesOnly;
    }

    public void setOldOnesOnly(String oldOnesOnly) {
        this.oldOnesOnly = oldOnesOnly;
    }

    public String getClientName() {
        if(null == clientName){
            try{
                if(clientId.equals("red")){
                    clientName="all Red ship methods";
                }  else if(clientId.equals("blue")){
                    clientName="all other ship methods";
                } else if(clientId.equals("business")) {
                    clientName = "Business Orders";
                } else if (clientId.equals("prsnl")) {
                    clientName = "all personlization orders";
                } else if (clientId.equals("gcard")) {
                    clientName = "all greeting card orders";
                } else {
                OwdClient client = (OwdClient)HibernateSession.currentSession().load(OwdClient.class,Integer.parseInt(clientId));
                      clientName = client.getCompanyName();
                }
            } catch(Exception e){
                e.printStackTrace();
            }
        }
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public List<detailedReprintBean> getDetailedReprintData() {
        return detailedReprintData;
    }

    public void setDetailedReprintData(List<detailedReprintBean> detailedReprintData) {
        this.detailedReprintData = detailedReprintData;
    }

    public List<String> getClients() {
        return clients;
    }

    public void setClients(List<String> clients) {
        this.clients = clients;
    }

    public List<String> getSlas() {
        return slas;
    }

    public void setSlas(List<String> slas) {
        this.slas = slas;
    }

    public List<String> getGroupNames() {
        return groupNames;
    }

    public void setGroupNames(List<String> groupNames) {
        this.groupNames = groupNames;
    }

    public List<String> getUnits() {
        return units;
    }

    public void setUnits(List<String> units) {
        this.units = units;
    }

    public List<String> getShipMethods() {

        return shipMethods;
    }

    public void setShipMethods(List<String> shipMethods) {
        this.shipMethods = shipMethods;
    }

    public String getPrinter() {
        return printer;
    }

    public void setPrinter(String printer) {
        this.printer = printer;
    }

    public String getOrderIds() {
        return orderIds;
    }

    public void setOrderIds(String orderIds) {
        this.orderIds = orderIds;
    }

    public boolean isSortRack() {
        return sortRack;
    }

    public void setSortRack(boolean sortRack) {
        this.sortRack = sortRack;
    }

    public List<String> getZones() {
        return zones;
    }

    public void setZones(List<String> zones) {
        this.zones = zones;
    }

    public List<String> getAisles() {
        return aisles;
    }

    public void setAisles(List<String> aisles) {
        this.aisles = aisles;
    }

    public List<String> getBoxTypes() {
        return boxTypes;
    }

    public void setBoxTypes(List<String> boxTypes) {
        this.boxTypes = boxTypes;
    }
}
