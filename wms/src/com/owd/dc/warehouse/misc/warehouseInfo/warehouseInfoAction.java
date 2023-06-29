package com.owd.dc.warehouse.misc.warehouseInfo;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.owd.WMSUtils;
import com.owd.core.managers.FacilitiesManager;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdClient;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: danny
 * Date: 11/20/14
 * Time: 9:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class warehouseInfoAction extends ActionSupport{
    private String facility;
     private List<slaBreakDownBean> slas;
    private List<slaBreakDownBean> unpickSlas;
    private Map<String,String> clients;
    private List<String> locations;
    private String clientId;
    private String companyName;
    private String today;
    private String caseQty;
    private String palletQty;
    private List<String> groupNames;
    private String groupName="";
    private List<skuInfoBean> skuInfo;


    public String execute(){
        try{
       if(null==facility){
           System.out.println("Setting facility from request");
           facility = WMSUtils.getDefaultFacilityFromContext(ActionContext.getContext());
       }
            if(null==clientId){
                clientId = "489";
            }

        slas = warehouseInfoUtils.loadSlaListForFacility(clientId,facility);
          unpickSlas = warehouseInfoUtils.loadUnpickedSlaListForFacility(clientId,facility);
            clients = warehouseInfoUtils.getClientsWithOrdersAtWarehouse(facility);
           OwdClient client = (OwdClient) HibernateSession.currentSession().load(OwdClient.class,Integer.parseInt(clientId));
            companyName = client.getCompanyName();
        }catch  (Exception e){
            addActionError(e.getMessage());
        }
        return "success";
    }


    public String skuStart(){
        try{
            if(null==facility){
                System.out.println("Setting facility from request");
                facility = WMSUtils.getDefaultFacilityFromContext(ActionContext.getContext());

            }
            clients = warehouseInfoUtils.getClientsWithOrdersAtWarehouse(facility);
            groupNames = warehouseInfoUtils.getGroupNamesWithOrdersAtWarehouse(facility);

        }catch(Exception e){
             addActionError(e.getMessage());
        }
        return "success";
    }

    public String skuLoadInfo(){
        try{
         if(null ==caseQty||caseQty.length()==0){
             caseQty = "1";
         }
         if(null==palletQty||palletQty.length()==0){
             palletQty = "1";
         }
            skuInfo = warehouseInfoUtils.getSkuInfoForClientAtFacility(facility,clientId,groupName,caseQty,palletQty);
        }catch (Exception e){
            addActionError(e.getMessage());
            e.printStackTrace();
        }

        return "success";
    }

    public String getFacility() {
        return facility;
    }

    public void setFacility(String facility) {
        this.facility = facility;
    }

    public List<slaBreakDownBean> getSlas() {
        return slas;
    }

    public void setSlas(List<slaBreakDownBean> slas) {
        this.slas = slas;
    }

    public List<slaBreakDownBean> getUnpickSlas() {
        return unpickSlas;
    }

    public void setUnpickSlas(List<slaBreakDownBean> unpickSlas) {
        this.unpickSlas = unpickSlas;
    }

    public Map<String, String> getClients() {
        return clients;
    }

    public void setClients(Map<String, String> clients) {
        this.clients = clients;
    }

    public List<String> getLocations() {
        try{
          locations = FacilitiesManager.getActiveFacilityCodes();
        }catch(Exception e){
            e.printStackTrace();
        }
        return locations;
    }

    public void setLocations(List<String> locations) {
        this.locations = locations;
    }

    public String getClientId() {
        return clientId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;

    }

    public String getToday() {
        try{
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            today = formatter.format(Calendar.getInstance().getTime());
        } catch(Exception e){

        }
        return today;
    }

    public void setToday(String today) {
        this.today = today;
    }

    public String getCaseQty() {
        return caseQty;
    }

    public void setCaseQty(String caseQty) {
        this.caseQty = caseQty;
    }

    public String getPalletQty() {
        return palletQty;
    }

    public void setPalletQty(String palletQty) {
        this.palletQty = palletQty;
    }

    public List<String> getGroupNames() {
        return groupNames;
    }

    public void setGroupNames(List<String> groupNames) {
        this.groupNames = groupNames;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<skuInfoBean> getSkuInfo() {
        return skuInfo;
    }

    public void setSkuInfo(List<skuInfoBean> skuInfo) {
        this.skuInfo = skuInfo;
    }
}
