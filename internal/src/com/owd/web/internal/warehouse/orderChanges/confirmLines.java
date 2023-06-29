package com.owd.web.internal.warehouse.orderChanges;

/**
 * Created by danny on 10/7/2019.
 */
public class confirmLines {


    private String inventoryNum;
    private String description;
    private String quantityActual;
    private String reason;
    private String lineId;
    private String removeQty;

    public String getInventoryNum() {

        return inventoryNum;
    }

    public void setInventoryNum(String inventoryNum) {
        this.inventoryNum = inventoryNum;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getQuantityActual() {
        return quantityActual;
    }

    public void setQuantityActual(String quantityActual) {
        this.quantityActual = quantityActual;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    public String getRemoveQty() {
        return removeQty;
    }

    public void setRemoveQty(String removeQty) {
        this.removeQty = removeQty;
    }
}
