package com.owd.dc.picking.forms;

import org.apache.struts.action.ActionForm;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Oct 26, 2005
 * Time: 12:49:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class replenishForm extends ActionForm {

    private String pickItemId;
    private String inventoryId;
    private String startTime;
    private String pickStatusId;
    private String location;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPickStatusId() {
        return pickStatusId;
    }

    public void setPickStatusId(String pickStatusId) {
        this.pickStatusId = pickStatusId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getPickItemId() {
        return pickItemId;
    }

    public void setPickItemId(String pickItemId) {
        this.pickItemId = pickItemId;
    }

    public String getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(String inventoryId) {
        this.inventoryId = inventoryId;
    }

}
