package com.owd.core.business.order.beans;

/**
 * Created by danny on 11/16/2016.
 */
public class lineItemExemptions {


    private String inventory_num;
    private String vendor_sku;
    private String exemptionCode;
    private String exemptionValue;
    private String qty;
    private String order_fkey;
    private String merchant_line_number;
    private String id;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrder_fkey() {
        return order_fkey;
    }

    public void setOrder_fkey(String order_fkey) {
        this.order_fkey = order_fkey;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getInventory_num() {
        return inventory_num;
    }

    public void setInventory_num(String inventory_num) {
        this.inventory_num = inventory_num;
    }

    public String getVendor_sku() {
        return vendor_sku;
    }

    public void setVendor_sku(String vendor_sku) {
        this.vendor_sku = vendor_sku;
    }

    public String getExemptionCode() {
        return exemptionCode;
    }

    public void setExemptionCode(String exemptionCode) {
        this.exemptionCode = exemptionCode;
    }

    public String getExemptionValue() {
        return exemptionValue;
    }

    public void setExemptionValue(String exemptionValue) {
        this.exemptionValue = exemptionValue;
    }

    public String getMerchant_line_number() {
        return merchant_line_number;
    }

    public void setMerchant_line_number(String merchant_line_number) {
        this.merchant_line_number = merchant_line_number;
    }
}
