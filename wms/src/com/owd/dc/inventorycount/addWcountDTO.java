package com.owd.dc.inventorycount;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Dec 28, 2005
 * Time: 3:15:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class addWcountDTO {
      private String inventoryId;
    private String location;
    private int originalScan;
    private int wRequestId;


    public String getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(String inventoryId) {
        this.inventoryId = inventoryId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getOriginalScan() {
        return originalScan;
    }

    public void setOriginalScan(int originalScan) {
        this.originalScan = originalScan;
    }

    public int getwRequestId() {
        return wRequestId;
    }

    public void setwRequestId(int wRequestId) {
        this.wRequestId = wRequestId;
    }
}
