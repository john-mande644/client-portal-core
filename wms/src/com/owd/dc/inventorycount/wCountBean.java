package com.owd.dc.inventorycount;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Jan 17, 2006
 * Time: 11:08:06 AM
 * To change this template use File | Settings | File Templates.
 */
public class wCountBean {
    private String inventoryId;
    private String quanity;
    private String wCountId;
    private String location;
    private String requestId;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(String inventoryId) {
        this.inventoryId = inventoryId;
    }

    public String getQuanity() {
        return quanity;
    }

    public void setQuanity(String quanity) {
        this.quanity = quanity;
    }

    public String getwCountId() {
        return wCountId;
    }

    public void setwCountId(String wCountId) {
        this.wCountId = wCountId;
    }
}
