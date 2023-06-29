package com.owd.dc.locations.frontLocation;

import com.opensymphony.xwork2.ActionSupport;
import com.owd.dc.inventory.upcBarcodeUtilities;
import com.owd.hibernate.HibernateSession;
import org.hibernate.Session;

import java.util.HashMap;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: danny
 * Date: Jan 19, 2010
 * Time: 4:17:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class checkFrontAction extends ActionSupport {

   private String barcode;
   private String frontLocation;
    private String newLocation;
    private List<String> otherLocations;
    private String sku;
    private String description;
    private boolean goodfront;

     public void fillInfo(Session sess,String msku) throws Exception{
          frontLocationBean b = new frontLocationBean();
            b = frontUtils.loadSkuInfo(sess,msku);
            frontLocation = b.getFrontLocation();
            sku = b.getSku();
            description = b.getDescription();
            otherLocations = b.getOtherLocations();
           goodfront = b.isFrontInList();
     }
    public String execute(){
        System.out.println("in Front Start");
        try{
         String msku = upcBarcodeUtilities.getSku(barcode, 0);

            if (msku.equals("Multi")) {
                System.out.println("in Multi");
                HashMap multilist = upcBarcodeUtilities.getMultiBarcodeList(barcode, 0);

               // request.setAttribute("skus", multilist);
              //  request.setAttribute("var", "barcode");
             //   request.setAttribute("url", "checkFront.action");
             //   return "multi";
                throw new Exception("mulit sku now supported right now");
            }
           fillInfo(HibernateSession.currentSession(),msku);
           barcode=msku;
            
        }  catch (Exception e){
            e.printStackTrace();
            addActionError(e.getMessage());
        }

        return "success";

    }
   public String saveChange() {
       try{
       System.out.println(sku);
         frontUtils.setNewFront(HibernateSession.currentSession(),barcode,newLocation);  

       addActionMessage("Changed First Location to " + newLocation);
        return "success";
       }catch (Exception e){
           addActionError(e.getMessage());
           return "error";
       }
    }
    public String removeLocation(){

        try{
           frontUtils.removeLocation(HibernateSession.currentSession(),barcode,newLocation);
           fillInfo(HibernateSession.currentSession(),barcode);
            
           addActionMessage("Removed "+ newLocation);
        } catch(Exception e){
            addActionError(e.getMessage());
            return "error";
        }



        return "success";
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getFrontLocation() {
        return frontLocation;
    }

    public void setFrontLocation(String frontLocation) {
        this.frontLocation = frontLocation;
    }

    public String getNewLocation() {
        return newLocation;
    }

    public void setNewLocation(String newLocation) {
        this.newLocation = newLocation;
    }

    public List<String> getOtherLocations() {
        return otherLocations;
    }

    public void setOtherLocations(List<String> otherLocations) {
        this.otherLocations = otherLocations;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isGoodfront() {
        return goodfront;
    }

    public void setGoodfront(boolean goodfront) {
        this.goodfront = goodfront;
    }
}
