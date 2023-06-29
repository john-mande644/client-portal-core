package com.owd.dc.warehouse.misc.beans;

/**
 * Created with IntelliJ IDEA.
 * User: danny
 * Date: 2/7/13
 * Time: 8:44 AM
 * To change this template use File | Settings | File Templates.
 */
public class noScanReportBean {
    private String inventoryId;
    private String inventoryNum;
    private String description;
    private Boolean noScan;

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

    public Boolean getNoScan() {
        return noScan;
    }

    public void setNoScan(Boolean noScan) {
        this.noScan = noScan;
    }
}
