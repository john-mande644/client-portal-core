package com.owd.dc.inventory;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Sep 29, 2005
 * Time: 4:12:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class multiSkuForm {
    private String inventoryId;
    private String inventoryNum;
    private String clientName;
    private String description;
    private String clientFkey;

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

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getClientFkey() {
        return clientFkey;
    }

    public void setClientFkey(String clientFkey) {
        this.clientFkey = clientFkey;
    }
}
