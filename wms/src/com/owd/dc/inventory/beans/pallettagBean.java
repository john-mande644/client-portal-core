package com.owd.dc.inventory.beans;

import com.thoughtworks.xstream.XStream;

/**
 * Created with IntelliJ IDEA.
 * User: danny
 * Date: 8/8/12
 * Time: 3:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class pallettagBean {
    String client;
    String inventoryId;
    String inventoryNum;
    String description;
    String qty;


    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getInventoryId() {
        return inventoryId;
    }
    public void setInventoryId(int inventoryId1){
        setInventoryId(inventoryId1+"");
    }
    public void setInventoryId(String inventoryId) {
        this.inventoryId = inventoryId;
    }

    public String getInventoryNum() {
        return inventoryNum;
    }

    public void setInventoryNum(String inventoryNum) {
        this.inventoryNum = inventoryNum;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public void setQty(int qty1){
        setQty(qty1+"");
    }
    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public static XStream getXStream(){
      XStream x = new XStream();
      x.alias("pallettag",pallettagBean.class);

         return x;
       }
}
