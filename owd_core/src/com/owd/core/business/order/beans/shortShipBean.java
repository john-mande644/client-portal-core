package com.owd.core.business.order.beans;

/**
 * Created by danny on 9/2/2019.
 */
public class shortShipBean {

    private Integer lineItemId;
    private Integer qtyToAdjust;
    private String reason;

    public Integer getLineItemId() {
        return lineItemId;
    }

    public void setLineItemId(Integer lineItemId) {
        this.lineItemId = lineItemId;
    }

    public Integer getQtyToAdjust() {
        return qtyToAdjust;
    }

    public void setQtyToAdjust(Integer qtyToAdjust) {
        this.qtyToAdjust = qtyToAdjust;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
