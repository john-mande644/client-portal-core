package com.owd.dc.inventorycount;

import com.opensymphony.xwork2.ActionSupport;
import com.owd.dc.inventory.upcBarcodeUtilities;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdInventory;
import com.owd.hibernate.generated.WInvRequest;

/**
 * Created by IntelliJ IDEA.
 * User: danny
 * Date: Jul 27, 2010
 * Time: 3:35:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class wInventoryAction  extends ActionSupport {
    private String location;
    private String currentCount;
    private String inventoryId;
    private String qty;
    private int clientFkey;
    private String invName;

      public String execute(){


               location = "";
        
          inventoryId = "" ;
          qty = "";
          invName="";

          
          return "success";
      }

    public String verify(){
        try{
         System.out.println("doing verify");
         if (!location.startsWith("/")) throw new Exception("Not a valid location");

         WInvRequest wi = (WInvRequest) HibernateSession.currentSession().load(WInvRequest.class, new Integer(currentCount));
          clientFkey = wi.getClientFkey();

            String sku = upcBarcodeUtilities.getSku(inventoryId, clientFkey);
            if (sku.equals("Multi")) throw new Exception("Multiple barcode found, please contact IT to use this function");
            if (sku.equals("NA")){
                sku = inventoryId;
            }
            //check that id is for client
            OwdInventory inv = (OwdInventory) HibernateSession.currentSession().load(OwdInventory.class, new Integer(sku));
            if (inv.getOwdClient().getClientId() != clientFkey){
                throw new Exception("Item does not match current client");

            }
            invName = inv.getInventoryNum(); 
             inventoryId = sku;
        } catch(Exception e){
            addActionError(e.getMessage());
            return "error";
        }


        return "success";
    }
   public String add(){
       try{
       String countFkey = wInventoryUtilities.getFkeyForCountLocation(HibernateSession.currentSession(),location,currentCount,inventoryId);
        wCountDTO wcount = new wCountDTO();
           wcount.setInvLocFkey(Integer.parseInt(countFkey));
           wcount.setInventoryId(inventoryId);
           wcount.setLocation(location);
           wcount.setQuanity(qty);
           wcount.setUPC("");
           wcount.setISBN("");

           wInventoryUtilities.insertCount(wcount,"Addin for misplaced");



           addActionMessage("Added "+qty +"of "+invName);
           location = "";

                 inventoryId = "" ;
                 qty = "";
                 invName="";
           
       }catch (Exception e){
           addActionError(e.getMessage());
       }


       return "success";
   }
    public String getInvName() {
        return invName;
    }

    public void setInvName(String invName) {
        this.invName = invName;
    }

    public int getClientFkey() {
        return clientFkey;
    }

    public void setClientFkey(int clientFkey) {
        this.clientFkey = clientFkey;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCurrentCount() {
        return currentCount;
    }

    public void setCurrentCount(String currentCount) {
        this.currentCount = currentCount;
    }

    public String getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(String inventoryId) {
        this.inventoryId = inventoryId;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }
}
