package com.owd.core.business.order.shippingAdjustments.clientInterfaces.dataBeans;

import java.util.List;

/**
 * Created by danny on 10/8/2019.
 */
public class FOHShortShipData {
    private String order_reference;
    private String idempotency_key;
    private List<FOHLine> line_items;

    public String getOrder_reference() {
        return order_reference;
    }

    public void setOrder_reference(String order_reference) {
        this.order_reference = order_reference;
    }

    public String getIdempotency_key() {
        return idempotency_key;
    }

    public void setIdempotency_key(String idempotency_key) {
        this.idempotency_key = idempotency_key;
    }

    public List<FOHLine> getLine_items() {
        return line_items;
    }

    public void setLine_items(List<FOHLine> line_items) {
        this.line_items = line_items;
    }
}
