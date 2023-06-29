package com.owd.core.business.order.shippingAdjustments.clientInterfaces.dataBeans;

import java.math.BigDecimal;

/**
 * Created by danny on 10/8/2019.
 */
public class FOHLine {
    private Integer line_number;
    private Integer quantity;

    private String sku;
    private String description;
    private BigDecimal declared_value;
    private String customs_desc;

    public Integer getLine_number() {
        return line_number;
    }

    public void setLine_number(Integer line_number) {
        this.line_number = line_number;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getDeclared_value() {
        return declared_value;
    }

    public void setDeclared_value(BigDecimal declared_value) {
        this.declared_value = declared_value;
    }

    public String getCustoms_desc() {
        return customs_desc;
    }

    public void setCustoms_desc(String customs_desc) {
        this.customs_desc = customs_desc;
    }
}
