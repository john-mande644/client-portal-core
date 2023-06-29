package com.owd.magento2Api.Models;

public class InvontoryStock {
    private int item_id;
    private int product_id;
    private int stock_id;
    private long qty;

    public int getItem_id() {
        return item_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public int getStock_id() {
        return stock_id;
    }

    public long getQty() {
        return qty;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public void setStock_id(int stock_id) {
        this.stock_id = stock_id;
    }

    public void setQty(long qty) {
        this.qty = qty;
    }
}
