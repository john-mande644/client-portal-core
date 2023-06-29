package com.owd.dc.inventorycount;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Dec 23, 2005
 * Time: 2:06:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class inventoryCountBean {
    private String inventoryId;
    private String inventoryNum;
    private String quanity;

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

    public String getQuanity() {
        return quanity;
    }

    public void setQuanity(String quanity) {
        this.quanity = quanity;
    }
}
