package com.owd.dc.inventory.beans;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Dec 30, 2005
 * Time: 3:50:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class invBean {
    private String inventoryId;
    private String inventoryNum;
    private String Description;
    private String qtyOnHand;
    private Boolean hasBarcode = false;
    private String upcCode;
    private String notes;

    public Boolean getHasBarcode() {
        return hasBarcode;
    }

    public void setHasBarcode(Boolean hasBarcode) {
        this.hasBarcode = hasBarcode;
    }

    public String getQtyOnHand() {
        return qtyOnHand;
    }

    public void setQtyOnHand(String qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
    }

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

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getUpcCode() {
        return upcCode;
    }

    public void setUpcCode(String upcCode) {
        this.upcCode = upcCode;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
