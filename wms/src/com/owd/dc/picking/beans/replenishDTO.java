package com.owd.dc.picking.beans;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Oct 26, 2005
 * Time: 1:00:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class replenishDTO {
    private int pickItemId;
    private int inventoryId;
    private String startTime;
    private int pickStatusId;
    private String location;


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getPickStatusId() {
        return pickStatusId;
    }

    public void setPickStatusId(int pickStatusId) {
        this.pickStatusId = pickStatusId;
    }

    public int getPickItemId() {
        return pickItemId;
    }

    public void setPickItemId(int pickItemId) {
        this.pickItemId = pickItemId;
    }

    public int getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(int inventoryId) {
        this.inventoryId = inventoryId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

}
