package com.owd.dc.inventory.beans;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Apr 10, 2005
 * Time: 3:41:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class locationDTO {
    private String location;
    private String sku;
    private int remember;
    private String rememberClientName;
    private String clientFkey;
    private String newParentId;
    private String primary;
    private String notes;
    private String lotValue="";

    public String getLotValue() {
        return lotValue;
    }

    public void setLotValue(String lotValue) {
        this.lotValue = lotValue;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getPrimary() {
        return primary;
    }

    public void setPrimary(String primary) {
        this.primary = primary;
    }

    public String getNewParentId() {
        return newParentId;
    }

    public void setNewParentId(String newParentId) {
        this.newParentId = newParentId;
    }

    public String getClientFkey() {
        return clientFkey;
    }

    public void setClientFkey(String clientFkey) {
        this.clientFkey = clientFkey;
    }

    public void setlocation(String location) {
        this.location = location;
    }
    public void setsku(String sku) {
        this.sku = sku;
    }
    public String getlocation() {
        return location;
    }
    public String getsku() {
        return sku;
    }

    public int getRemember() {
        return remember;
    }

    public void setRemember(int remember) {
        this.remember = remember;
    }

    public String getRememberClientName() {
        return rememberClientName;
    }

    public void setRememberClientName(String rememberClientName) {
        this.rememberClientName = rememberClientName;
    }
}
