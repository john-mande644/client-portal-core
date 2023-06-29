package com.owd.dc.inventorycount;

import org.apache.struts.action.ActionForm;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Jan 18, 2006
 * Time: 8:36:43 AM
 * To change this template use File | Settings | File Templates.
 */
public class editWCountForm extends ActionForm{
     private String inventoryId;
    private String quanity ="";
    private String wCountId;
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
