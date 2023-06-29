package com.owd.dc.warehouse.misc.warehouseInfo;

/**
 * Created with IntelliJ IDEA.
 * User: danny
 * Date: 11/25/14
 * Time: 3:52 PM
 * To change this template use File | Settings | File Templates.
 */
public class skuInfoBean {
    private String inventoryId;
    private String inventoryNum;
    private String description;
    private String units;
    private String orders;
    private String pallets;
    private String cases;


    public String getInventoryId() {
        return inventoryId;
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

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public String getOrders() {
        return orders;
    }

    public void setOrders(String orders) {
        this.orders = orders;
    }

    public String getPallets() {
        return pallets;
    }

    public void setPallets(String pallets) {
        this.pallets = pallets;
    }

    public String getCases() {
        return cases;
    }

    public void setCases(String cases) {
        this.cases = cases;
    }
}
