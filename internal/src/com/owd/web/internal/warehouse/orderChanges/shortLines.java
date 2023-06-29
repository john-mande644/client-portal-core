package com.owd.web.internal.warehouse.orderChanges;

/**
 * Created by danny on 10/2/2019.
 */
public class shortLines {

    private String lineId;
    private String qty;
    private String reason;

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

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }
}
