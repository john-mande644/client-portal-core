package com.owd.dc.picking.forms;

import org.apache.struts.action.ActionForm;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Dec 21, 2005
 * Time: 11:17:49 AM
 * To change this template use File | Settings | File Templates.
 */
public class holdForm extends ActionForm {
    public String inventoryId;
    public String inventoryNum;
    public String locations;
    public String onHand;
    public String user;
    public String orderNum;
    public String pickItemId;
    public String replenishStart;
    public String holdReason;
    public String holdNotes;

    public String getReplenishStart() {
        return replenishStart;
    }

    public void setReplenishStart(String replenishStart) {
        this.replenishStart = replenishStart;
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

    public String getInventoryNum() {
        return inventoryNum;
    }

    public void setInventoryNum(String inventoryNum) {
        this.inventoryNum = inventoryNum;
    }

    public String getLocations() {
        return locations;
    }

    public void setLocations(String locations) {
        this.locations = locations;
    }

    public String getOnHand() {
        return onHand;
    }

    public void setOnHand(String onHand) {
        this.onHand = onHand;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getHoldReason() {
        return holdReason;
    }

    public void setHoldReason(String holdReason) {
        this.holdReason = holdReason;
    }

    public String getHoldNotes() {
        return holdNotes;
    }

    public void setHoldNotes(String holdNotes) {
        this.holdNotes = holdNotes;
    }
}
