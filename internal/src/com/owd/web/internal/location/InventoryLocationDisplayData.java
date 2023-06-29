package com.owd.web.internal.location;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Mar 15, 2005
 * Time: 11:32:01 AM
 * To change this template use File | Settings | File Templates.
 */
public class InventoryLocationDisplayData {

    int qty = 0;
    String location = "";

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocDesc() {
        return locDesc;
    }

    public void setLocDesc(String locDesc) {
        this.locDesc = locDesc;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getSkuDesc() {
        return skuDesc;
    }

    public void setSkuDesc(String skuDesc) {
        this.skuDesc = skuDesc;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    String locDesc = "";
    String sku = "";
    String skuDesc = "";
    String client = "";


    public InventoryLocationDisplayData(String loc, String ldesc, String skuname, String sdesc, String cname, int quant) {
        qty = quant;
        location = loc;
        locDesc = ldesc;
        sku = skuname;
        skuDesc = sdesc;
        client = cname;
    }
}
