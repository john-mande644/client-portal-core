package com.owd.dc.warehouse.supplyTracking;

/**
 * Created with IntelliJ IDEA.
 * User: danny
 * Date: 9/18/12
 * Time: 2:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class JSONcheckIdBean {
    private String error ="";
    private String invId;
    private String inventoryNum;
    private String description;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getInvId() {
        return invId;
    }

    public void setInvId(String invId) {
        this.invId = invId;
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
}
