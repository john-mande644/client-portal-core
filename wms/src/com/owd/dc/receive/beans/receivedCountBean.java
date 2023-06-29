package com.owd.dc.receive.beans;

/**
 * Created by danny on 7/20/2015.
 */
public class receivedCountBean {
    private Integer inventoryId;
   private Integer qtyReceive;
    private Integer qtyDamaged;
    private String notes;


    public Integer getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Integer inventoryId) {
        this.inventoryId = inventoryId;
    }

    public Integer getQtyReceive() {
        return qtyReceive;
    }

    public void setQtyReceive(Integer qtyReceive) {
        this.qtyReceive = qtyReceive;
    }

    public Integer getQtyDamaged() {
        return qtyDamaged;
    }

    public void setQtyDamaged(Integer qtyDamaged) {
        this.qtyDamaged = qtyDamaged;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

}
