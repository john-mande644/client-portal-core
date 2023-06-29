package com.owd.dc.inventory.forms;
import org.apache.struts.action.ActionForm;
import com.owd.dc.inventory.upcBarcodeUtilities;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Apr 8, 2005
 * Time: 3:29:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class skuForm extends ActionForm {
  private String sku;
   private String numlabels ="";
    private String inventoryNum;
    private String picsku;
    private String display;
    private String pickMultiple="";

    public String getPickMultiple() {
        return pickMultiple;
    }

    public void setPickMultiple(String pickMultiple) {
        this.pickMultiple = pickMultiple;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getPicsku() {
        return picsku;
    }

    public void setPicsku(String picsku) {
        this.picsku = picsku;
    }

    public String getInventoryNum() {
        return inventoryNum;
    }

    public void setInventoryNum(String inventoryNum) {
        this.inventoryNum = inventoryNum;
    }

    public String getNumlabels() {
        return numlabels;
    }

    public void setNumlabels(String numlabels) {
        this.numlabels = numlabels;
    }




    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getSku() {
         return sku;
    }

}

