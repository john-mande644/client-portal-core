package com.owd.dc.inventorycount;

import org.apache.struts.action.ActionForm;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Dec 28, 2005
 * Time: 3:06:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class addWcountForm extends ActionForm {
    private String inventoryId;
    private String location;
    private String originalScan ="";
    private String wRequestId ="";



    
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

    public String getOriginalScan() {
        return originalScan;
    }

    public void setOriginalScan(String originalScan) {
        this.originalScan = originalScan;
    }

    public String getwRequestId() {
        return wRequestId;
    }

    public void setwRequestId(String wRequestId) {
        this.wRequestId = wRequestId;
    }
}
