package com.owd.dc.warehouse.packSlipRelease;

import com.opensymphony.xwork2.*;
import com.owd.core.managers.FacilitiesManager;
import com.owd.hibernate.HibernateSession;

import java.util.*;

import org.apache.struts2.interceptor.SessionAware;
import org.hibernate.Session;
import org.apache.struts2.interceptor.ParameterAware;

/**
 * Created by IntelliJ IDEA.
 * User: danny
 * Date: Jan 16, 2009
 * Time: 4:09:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class startAction extends ActionSupport implements SessionAware {
      private Map<String, packSlipClientBean> clients;
    private Map<String, String> currentPrinting;
    private Map<String,String> printers;
    private Map<String,String> singlePrinters;
   private String dateLoaded;
    private boolean autoRelease;
    private String orderNum;
    private String singlePrinter;
   private String clientId;
    private String shipMethod;
    private List<clientDetailsBean> details;
    private String detailsPrinter;
    private String printList;
    private String clientName;
    private String group;
    private boolean hasGroup;
    private Map sessionAttributeMap;
    private String location="DC1";
    private Map<String,String> locations;



    public void setSession(Map map) {
        System.out.println("setting PackSlipRelease session map:"+map);
        sessionAttributeMap = map;
        if(sessionAttributeMap.get("owd_current_location")==null)
        {
            sessionAttributeMap.put("owd_current_location",sessionAttributeMap.get("owd_default_location"));
        }
    }
   
    private  void loadVariables(Session sess){
          currentPrinting = packSlipUtilities.getCurrentPrintQueue(sess,""+sessionAttributeMap.get("owd_current_location"));
              clients = packSlipUtilities.getClientOrderMap(sess,""+sessionAttributeMap.get("owd_current_location"));
             location = sessionAttributeMap.get("owd_current_location")+"";

             setDateLoaded(Calendar.getInstance().getTime().toString());
    }
    public String execute(){
        System.out.println("in pack slip start");
        try{
            
            Session sess = HibernateSession.currentSession();
            loadVariables(sess);

        }  catch (Exception e){
            e.printStackTrace();
            addActionError(e.getMessage());
        } 

        return "success";

    }
    public String doReleaseSlips(){
                 System.out.println("Releasing checked ones");
               System.out.println(clients.size());
        try{
        Session sess = HibernateSession.currentSession();
        for(packSlipClientBean cb: clients.values()){
            System.out.println(cb.isPrint());
           if(cb.isPrint()){
               System.out.println(cb.getPrinter() + "for "+cb.getClientName());

               packSlipUtilities.releaseOrdersForClient(cb.getClientId(),cb.getPrinter(),sess,sessionAttributeMap.get("owd_current_location").toString());

           }

        }


            loadVariables(sess);

         } catch(Exception e){

         }
        return "success";
    }

    public String doSetLocation(){
                 System.out.println("setting current location session attribute to "+ActionContext.getContext().getParameters().get("location"));
                 System.out.println("setting current location session attribute to "+location);

        try{

            sessionAttributeMap.put("owd_current_location", (null==location?"DC1":(location)));
              loadVariables(HibernateSession.currentSession());
         } catch(Exception e){
            e.printStackTrace();
         }
        return "success";
    }

    public String reprintOrder(){
                         try{
            Session sess = HibernateSession.currentSession();
                             System.out.println(orderNum);
                             System.out.println("reprinting");
                             int rows = packSlipUtilities.reprintSingleOrderByOrderNumber(sess,orderNum,singlePrinter);
                             if (rows== 0){
                                 addActionError("Unable to reprint Packslip");
                             }  else{
                                 addActionMessage("Reprinted "+orderNum);
                             }

                             loadVariables(sess);
                             
                         } catch(Exception e){
                             addActionError(e.getMessage());
                         }
        return "success";
    }
     public String doSingleOrderRelease(){
                 System.out.println("Releasing single order");
        
        try{
        Session sess = HibernateSession.currentSession();
           int rows = 0;
           rows =  packSlipUtilities.printSingleOrderByOrderNumber(sess,orderNum,singlePrinter);



            loadVariables(sess);

         } catch(Exception e){

         }
        return "success";
    }

    private Object jsonModel;

    public Object getJsonModel() {
        return jsonModel;
    }

    public void setJsonModel(Object jsonModel) {
        this.jsonModel = jsonModel;
    }

    public String groupPrint(){
       jsonResultBean b = new jsonResultBean();
        try{
            if (null!=detailsPrinter){
           int rows = packSlipUtilities.printGroupOrdersForClient(clientId,packSlipUtilities.getPrintOnePrinterFromName(detailsPrinter,HibernateSession.currentSession()),HibernateSession.currentSession(),sessionAttributeMap.get("owd_current_location").toString());
            }else{
             b.setError("You must specify a printer for group printing");
            }
            setJsonModel(b);
        } catch (Exception e){
           e.printStackTrace();
            b.setError(e.getMessage());
        }

        return Action.SUCCESS;

    }
    public String detailsPrint(){
        
             if(null!= detailsPrinter){
            System.out.println("doing priting first");
           System.out.println(printList);
            
           String[] s = printList.split("v5v");
            String pitem = s[0];
            String pmethod = s[1];
            String ptime = s[2].replace(".0","");
            System.out.println(pitem);
            System.out.println(pmethod);
            System.out.println(ptime);
                 System.out.println(clientId);
               jsonResultBean b = new jsonResultBean();
           try{
            packSlipUtilities.printDetailedSelection(clientId,pitem,pmethod,ptime,detailsPrinter,HibernateSession.currentSession(),sessionAttributeMap.get("owd_current_location").toString());
           }catch (Exception e){
               b.setError(e.getMessage());
           }

                 b.setMessage("Printed Line items :"+pitem +" for method" +pmethod);



                 


                 setJsonModel(b);


        }
        return Action.SUCCESS;
    }
    public String getClientDetails(){
        System.out.println("doing client details");


        try{
            details = packSlipUtilities.getDetails(clientId,sessionAttributeMap.get("owd_current_location").toString());
            hasGroup = packSlipUtilities.clientHasGroupItemsToPrint(clientId,HibernateSession.currentSession(),sessionAttributeMap.get("owd_current_location").toString());
        }catch (Exception e){
            e.printStackTrace();
            addActionError(e.getMessage());
        }

       return "success";
    }
    public Map<String, packSlipClientBean> getClients() {
        return clients;
    }

    public void setClients(Map<String, packSlipClientBean> clients) {
        this.clients = clients;
    }

    public Map<String, String> getCurrentPrinting() {
        return currentPrinting;
    }

    public void setCurrentPrinting(Map<String, String> currentPrinting) {
        this.currentPrinting = currentPrinting;
    }

    public String getDateLoaded() {
        return dateLoaded;
    }

    public void setDateLoaded(String dateLoaded) {
        this.dateLoaded = dateLoaded;
    }

    public boolean isAutoRelease() {
        autoRelease = packSlipUtilities.getAutoRelease();

        return autoRelease;
    }

    public void setAutoRelease(boolean autoRelease) {
        this.autoRelease = autoRelease;
    }

    public Map<String, String> getLocations() {
        if(locations==null)
        {
            locations = new TreeMap<String, String>();
            try{
            List<String> l = FacilitiesManager.getActiveFacilityCodes();
           for(String s : l){
             locations.put(s,s);
           }
            } catch (Exception e){
             e.printStackTrace();
            }
        }
        return locations;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Map getSessionAttributeMap() {
        return sessionAttributeMap;
    }

    public void setSessionAttributeMap(Map sessionAttributeMap) {
        this.sessionAttributeMap = sessionAttributeMap;
    }

    public Map<String, String> getSinglePrinters() {
        if(null == singlePrinters) singlePrinters = packSlipUtilities.getTodayPrinters(sessionAttributeMap.get("owd_current_location")+"");
        return singlePrinters;
    }

    public void setSinglePrinters(Map<String, String> singlePrinters) {
        this.singlePrinters = singlePrinters;
    }

    public Map<String, String> getPrinters() {
        if (null == printers){
            printers = packSlipUtilities.getPrinters(sessionAttributeMap.get("owd_current_location")+"");
            
        }
        return printers;
    }

    public void setPrinters(Map<String, String> printers) {
        this.printers = printers;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getSinglePrinter() {

        return singlePrinter;
    }

    public void setSinglePrinter(String singlePrinter) {
        this.singlePrinter = singlePrinter;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getShipMethod() {
        return shipMethod;
    }

    public void setShipMethod(String shipMethod) {
        this.shipMethod = shipMethod;
    }

    public List<clientDetailsBean> getDetails() {
        return details;
    }

    public void setDetails(List<clientDetailsBean> details) {
        this.details = details;
    }

    public String getDetailsPrinter() {
        return detailsPrinter;
    }

    public void setDetailsPrinter(String detailsPrinter) {
        this.detailsPrinter = detailsPrinter;
    }

    public String getPrintList() {
        return printList;
    }

    public void setPrintList(String printList) {
        this.printList = printList;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public boolean isHasGroup() {
        return hasGroup;
    }

    public void setHasGroup(boolean hasGroup) {
        this.hasGroup = hasGroup;
    }
}

