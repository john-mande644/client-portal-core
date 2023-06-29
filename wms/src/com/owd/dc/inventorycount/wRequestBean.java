package com.owd.dc.inventorycount;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Dec 30, 2005
 * Time: 10:25:42 AM
 * To change this template use File | Settings | File Templates.
 */
public class wRequestBean {
    private String id;
    private String client;
    private String description;
    private String location;
    private String inventoryId;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(String inventoryId) {
        this.inventoryId = inventoryId;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
