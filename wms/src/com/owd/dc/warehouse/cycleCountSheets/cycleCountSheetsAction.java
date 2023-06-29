package com.owd.dc.warehouse.cycleCountSheets;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: danny
 * Date: 6/16/14
 * Time: 1:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class cycleCountSheetsAction extends ActionSupport{
    private String clientId;
    private cycleCountSheetData data;
    private String facility;
    private Map<String,String> clients;
    private List<String> facilities;
    private Object jsonModel;
    private List<cycleCountInv> skuList;
    private List<String> groupList;
    private String group = "";
    private String inventoryNumList="";
    private String inventoryIdList="";
    private String skusString="";
    private List<String> inventoryNumMulti;
    private String searchString;

   public String searchSku(){
       List<String> l = new ArrayList<String>();
        try{
          l = cycleCountSheetUtils.seachSkus(clientId,facility,searchString);

        }catch (Exception e){
            e.printStackTrace();
          l.add("error");
        }

       setJsonModel(l);

       return Action.SUCCESS;
   }
    public String getSkus(){
         try{
            Integer.parseInt(clientId);
            skuList = cycleCountSheetUtils.getSkus(clientId,facility);
            // skusString = cycleCountSheetUtils.getSkuString(skuList);
             groupList = cycleCountSheetUtils.getGroups(clientId,facility);


         } catch(NumberFormatException nf){
             addActionError("That does not appear to be a good id");
         }
         catch(Exception e){
             e.printStackTrace();
             addActionError(e.getMessage());
         }


        return Action.SUCCESS;
    }
    public String start(){
        try{
        facilities = cycleCountSheetUtils.getFacilites();
        clients = cycleCountSheetUtils.getClients();
        }catch (Exception e){
            addActionError(e.getMessage());
            e.printStackTrace();
        }


        return "success";
    }
    public String load(){

        try{
            System.out.println("This is the multi");
            System.out.println(inventoryNumMulti);
            if(null == inventoryNumMulti){
                inventoryNumMulti = new ArrayList<String>();
            }
            data = cycleCountSheetUtils.loadSheetDataClientDc(clientId,facility,group,inventoryNumList,inventoryIdList,inventoryNumMulti);


        }catch (Exception e){
            e.printStackTrace();
            addActionError(e.getMessage());
            return "error";
        }



        return "success";
    }


    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public cycleCountSheetData getData() {
        return data;
    }

    public void setData(cycleCountSheetData data) {
        this.data = data;
    }

    public String getFacility() {
        return facility;
    }

    public void setFacility(String facility) {
        this.facility = facility;
    }

    public Map<String, String> getClients() {
        return clients;
    }

    public void setClients(Map<String, String> clients) {
        this.clients = clients;
    }

    public List<String> getFacilities() {
        return facilities;
    }

    public void setFacilities(List<String> facilities) {
        this.facilities = facilities;
    }

    public Object getJsonModel() {
        return jsonModel;
    }

    public void setJsonModel(Object jsonModel) {
        this.jsonModel = jsonModel;
    }

    public List<cycleCountInv> getSkuList() {
        return skuList;
    }

    public void setSkuList(List<cycleCountInv> skuList) {
        this.skuList = skuList;
    }

    public List<String> getGroupList() {
        return groupList;
    }

    public void setGroupList(List<String> groupList) {
        this.groupList = groupList;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getInventoryNumList() {
        return inventoryNumList;
    }

    public void setInventoryNumList(String inventoryNumList) {
        this.inventoryNumList = inventoryNumList;
    }

    public String getInventoryIdList() {
        return inventoryIdList;
    }

    public void setInventoryIdList(String inventoryIdList) {
        this.inventoryIdList = inventoryIdList;
    }

    public String getSkusString() {
        return skusString;
    }

    public void setSkusString(String skusString) {
        this.skusString = skusString;
    }

    public List<String> getInventoryNumMulti() {
        return inventoryNumMulti;
    }

    public void setInventoryNumMulti(List<String> inventoryNumMulti) {
        this.inventoryNumMulti = inventoryNumMulti;
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }
}
