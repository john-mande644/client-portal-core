package com.owd.dc.warehouse.orderInfo;

/**
 * Created by IntelliJ IDEA.
 * User: danny
 * Date: Jan 17, 2011
 * Time: 2:59:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class pickItemsBean {
   private String firstScan;
    private String lastScan;
    private String unitsPicked;
    private String replenishTime;
  private String inventoryId;
    private String inventoryNum;



    public String getFirstScan() {
        return firstScan;
    }

    public void setFirstScan(String firstScan) {
        this.firstScan = firstScan;
    }

    public String getLastScan() {
        return lastScan;
    }

    public void setLastScan(String lastScan) {
        this.lastScan = lastScan;
    }

    public String getUnitsPicked() {
        return unitsPicked;
    }

    public void setUnitsPicked(String unitsPicked) {
        this.unitsPicked = unitsPicked;
    }

    public String getReplenishTime() {
        return replenishTime;
    }

    public void setReplenishTime(String replenishTime) {
        this.replenishTime = replenishTime;
    }

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
}
