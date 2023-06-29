package com.owd.dc.inventorycount;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Jan 18, 2006
 * Time: 8:38:07 AM
 * To change this template use File | Settings | File Templates.
 */
public class editWCountDTO {
     private String inventoryId;
    private int quanity;
    private Integer wCountId;
    private String location;
    private String requestId;
    private String oldQuanity;


    public String getOldQuanity() {
        return oldQuanity;
    }

    public void setOldQuanity(String oldQuanity) {
        this.oldQuanity = oldQuanity;
    }

    public String getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(String inventoryId) {
        this.inventoryId = inventoryId;
    }

    public int getQuanity() {
        return quanity;
    }

    public void setQuanity(int quanity) {
        this.quanity = quanity;
    }

    public Integer getwCountId() {
        return wCountId;
    }

    public void setwCountId(Integer wCountId) {
        this.wCountId = wCountId;
    }

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

}
