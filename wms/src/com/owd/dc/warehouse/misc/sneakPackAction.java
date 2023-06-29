package com.owd.dc.warehouse.misc;

import com.opensymphony.xwork2.ActionSupport;
import com.owd.dc.inventorycount.wInventoryUtilities;
import com.owd.dc.packing.beans.boxBean;
import com.owd.dc.packing.boxUtil;
import com.owd.dc.warehouse.misc.Utils.sneakPackUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.dc.warehouse.misc.beans.sneakPackItemsBean;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created with IntelliJ IDEA.
 * User: danny
 * Date: 12/5/13
 * Time: 9:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class sneakPackAction extends ActionSupport implements ServletResponseAware, ServletRequestAware, SessionAware {
      private String barcodeScan;
    private String pickerId;
    private String itemScan;
   private List<sneakPackItemsBean> items;
    private String boxId;
    private String orderFkey;
    private String boxName;
    private String printerName = "";
    private String printerUrl="";
    private Map<String,String> printerMap;
    private List<boxBean> boxes;
    private Map session;
    private String shipMethod;



    public String execute(){

        HttpServletRequest request = ServletActionContext.getRequest();
       pickerId = wInventoryUtilities.getIdFromCookies(request)+"";

        try{
            loadPrinterFromCookie();
            System.out.printf("This is the id we have : %s%n",pickerId);
             if(sneakPackUtils.checkPackingOrder(pickerId)){
                 items = sneakPackUtils.loadPackItemsBean(pickerId);
                 boxName= sneakPackUtils.getBoxNameByPickerId(pickerId);

                     return "packing";


             }
        } catch (Exception e){
            addActionError(e.getMessage());
        }

        return "success";
    }

    public String loadOrder(){
        try{
            if(printerName.length()==0) throw new Exception("Please setup printer");
            Map sneakPackBoxMap= new TreeMap();
            if(session.containsKey("sneakPackBoxMap")){
                sneakPackBoxMap = (Map) session.get("sneakPackBoxMap");

            }
          String loadPack = sneakPackUtils.loadOrderIntoDatabase("*"+barcodeScan+"*",pickerId, HibernateSession.currentSession(),sneakPackBoxMap);
            if(loadPack.equals("boxes")){
                boxes = boxUtil.getBoxBeanListForOrderId(sneakPackUtils.getOrderFkeyViaPickerId(pickerId));
                shipMethod = sneakPackUtils.getShipMethod(pickerId);
                return loadPack;
            }
            if(loadPack.equals("success")){
                //load bean and contintue
                items = sneakPackUtils.loadPackItemsBean(pickerId);
                boxName= sneakPackUtils.getBoxNameByPickerId(pickerId);
            } else{
                if(loadPack.contains("Already packing")){
                    return "success";
                }
                addActionError(loadPack);
                return "error";
            }
        }catch(Exception e){
            if(e.getMessage().contains("Already packing")){
                return "success";
            }
            addActionError(e.getMessage());
            return "error";
        }
        return "success";
    }
    public String clearBoxes(){
        Map sneakPackBoxMap= new TreeMap();
        session.put("sneakPackBoxMap",sneakPackBoxMap);
        HttpServletRequest request = ServletActionContext.getRequest();
        pickerId = wInventoryUtilities.getIdFromCookies(request)+"";

        try{
            loadPrinterFromCookie();
            System.out.printf("This is the id we have : %s%n",pickerId);
            if(sneakPackUtils.checkPackingOrder(pickerId)){
                items = sneakPackUtils.loadPackItemsBean(pickerId);
                boxName= sneakPackUtils.getBoxNameByPickerId(pickerId);

                return "packing";


            }
        } catch (Exception e){
            addActionError(e.getMessage());
        }
        return "success";
    }
    public String selectBox(){
             if(boxId.length()>0){
                 try{
                  if(sneakPackUtils.updateBox(boxId,pickerId,HibernateSession.currentSession())){
                      items = sneakPackUtils.loadPackItemsBean(pickerId);
                      boxName= sneakPackUtils.getBoxNameByPickerId(pickerId);

                      Map sneakPackBoxMap= new TreeMap();
                      if(session.containsKey("sneakPackBoxMap")){
                          sneakPackBoxMap = (Map) session.get("sneakPackBoxMap");

                      }
                      sneakPackBoxMap.put(sneakPackUtils.getFingerprintByPackerId(pickerId),boxId);
                      session.put("sneakPackBoxMap",sneakPackBoxMap);
                  } else{
                      addActionError("Well it didn't quite work");
                      return "error";
                  }
                 } catch (Exception e){
                     addActionError(e.getMessage());
                     return "error";
                 }
             }

        return "success";
    }
    public String scanItem(){
        System.out.println("hi");
                 if(itemScan.length()>0){
                   try{
                       System.out.println("hi1");
                       sneakPackUtils.packItem(itemScan.trim(), pickerId, HibernateSession.currentSession());
                       System.out.println("hi2");
                       boolean done = sneakPackUtils.checkDonePacking(pickerId);
                       System.out.println("hi3");
                       if(done){
                        sneakPackUtils.packTheOrder(pickerId,HibernateSession.currentSession());
                          if(printerUrl.length()==0){
                              loadPrinterFromCookie();
                          }
                        sneakPackUtils.printPackageLabel(pickerId,printerUrl);
                        sneakPackUtils.removePackedData(pickerId);

                           return "done";
                       }
                       System.out.println("hi4");

                   } catch(Exception e){
                      addActionError(e.getMessage());

                   }
                 }else{
                     addActionError("Please scan something usefull :)");
                 }
                try{
                    items = sneakPackUtils.loadPackItemsBean(pickerId);
                } catch (Exception ex){
                     addActionError("plllllllllllllltttttt");
                }

        return "success";
    }
    public String cancelPack(){
        try{
            loadPrinterFromCookie();
              sneakPackUtils.removePackedData(pickerId);

        }catch(Exception e){
            try{
            items = sneakPackUtils.loadPackItemsBean(pickerId);
            }catch(Exception ee){

            }
            addActionError(e.getMessage());
                return "error";
        }
        return "success";
    }
   public String savePrinter(){
                    try{
                        printerMap = printUtils.fillPrinterMap();
                        if(printerMap.containsKey(printerName)){
                           Cookie name = new Cookie("TABprinterName",printerName);
                            Cookie url = new Cookie("TABprinterUrl",printerMap.get(printerName));
                            servletResponse.addCookie(name);
                            servletResponse.addCookie(url);
                             printerUrl = printerMap.get(printerName);
                        }
                    } catch (Exception e){
                        addActionError(e.getMessage());
                        e.printStackTrace();
                        return "error";
                    }

       return "success";
   }
    public String setupPrinter(){
        try{
           loadPrinterFromCookie();
            printerMap = printUtils.fillPrinterMap();




        } catch (Exception e){
            addActionError(e.getMessage());
            return "error";
        }
         return "success";
    }
    private void loadPrinterFromCookie() throws Exception{
          for(Cookie c: servletRequest.getCookies()){
              if(c.getName().equals("TABprinterName")) printerName = c.getValue();
              if(c.getName().equals("TABprinterUrl")) printerUrl = c.getValue();
          }





    }



    protected HttpServletResponse servletResponse;

    public void setServletResponse(HttpServletResponse servletResponse) {
        this.servletResponse = servletResponse;
    }

    protected HttpServletRequest servletRequest;

    public void setServletRequest(HttpServletRequest servletRequest) {
        this.servletRequest = servletRequest;
    }
    public String getBarcodeScan() {
        return barcodeScan;
    }

    public void setBarcodeScan(String barcodeScan) {
        this.barcodeScan = barcodeScan;
    }

    public String getPickerId() {
        return pickerId;
    }

    public void setPickerId(String pickerId) {
        this.pickerId = pickerId;
    }

    public List<sneakPackItemsBean> getItems() {
        return items;
    }

    public void setItems(List<sneakPackItemsBean> items) {
        this.items = items;
    }

    public String getItemScan() {
        return itemScan;
    }

    public void setItemScan(String itemScan) {
        this.itemScan = itemScan;
    }

    public String getBoxId() {
        return boxId;
    }

    public void setBoxId(String boxId) {
        this.boxId = boxId;
    }

    public String getBoxName() {
        return boxName;
    }

    public void setBoxName(String boxName) {
        this.boxName = boxName;
    }

    public String getPrinterName() {
        return printerName;
    }

    public void setPrinterName(String printerName) {
        this.printerName = printerName;
    }

    public String getPrinterUrl() {
        return printerUrl;
    }

    public void setPrinterUrl(String printerUrl) {
        this.printerUrl = printerUrl;
    }

    public Map<String, String> getPrinterMap() {
        return printerMap;
    }

    public void setPrinterMap(Map<String, String> printerMap) {
        this.printerMap = printerMap;
    }

    public List<boxBean> getBoxes() {
        return boxes;
    }

    public void setBoxes(List<boxBean> boxes) {
        this.boxes = boxes;
    }

    public Map getSession() {
        return session;
    }

    public void setSession(Map session) {
        this.session = session;
    }

    public String getShipMethod() {
        return shipMethod;
    }

    public void setShipMethod(String shipMethod) {
        this.shipMethod = shipMethod;
    }
}
