package com.owd.core.business.order.distributedOrderManagement.Model;

public class domSku {

    private String inventory_num;
    private long qty;

    public String getInventory_num() {
        return inventory_num;
    }

    public void setInventory_num(String inventory_num) {
        this.inventory_num = inventory_num;
    }

    public long getQty() {
        return qty;
    }

    public void setQty(long qty) {
        this.qty = qty;
    }
}
