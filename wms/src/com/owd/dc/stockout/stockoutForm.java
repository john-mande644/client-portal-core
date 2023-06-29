package com.owd.dc.stockout;

import org.apache.struts.action.ActionForm;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Jan 23, 2006
 * Time: 9:28:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class stockoutForm extends ActionForm {
    private String sLocation;
    private String sInventoryId;
    private String location;
    private String InventoryId;
    private String clientFkey;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClientFkey() {
        return clientFkey;
    }

    public void setClientFkey(String clientFkey) {
        this.clientFkey = clientFkey;
    }

    public String getsLocation() {
        return sLocation;
    }

    public void setsLocation(String sLocation) {
        this.sLocation = sLocation;
    }

    public String getsInventoryId() {
        return sInventoryId;
    }

    public void setsInventoryId(String sInventoryId) {
        this.sInventoryId = sInventoryId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getInventoryId() {
        return InventoryId;
    }

    public void setInventoryId(String inventoryId) {
        InventoryId = inventoryId;
    }


}
