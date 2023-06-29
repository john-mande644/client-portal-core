package com.owd.dc.inventory.beans;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Apr 8, 2005
 * Time: 3:24:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class skuDTO {
    private String sku;
    private int numlabels;
   private String inventoryNum;
    private String picsku;
    private int pickMultiple;


    public int getPickMultiple() {
        return pickMultiple;
    }

    public void setPickMultiple(int pickMultiple) {
        this.pickMultiple = pickMultiple;
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

    public int getNumlabels() {
        return numlabels;
    }

    public void setNumlabels(int numlabels) {
        this.numlabels = numlabels;
    }


    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getSku() {
        return sku;
    }


}
