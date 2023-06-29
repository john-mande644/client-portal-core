package com.owd.dc.warehouse.orderInfo;

/**
 * Created with IntelliJ IDEA.
 * User: danny
 * Date: 8/26/14
 * Time: 2:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class pickErrorBean {
    private String packer;
    private String picker;
    private String inventory_id;
    private String inventory_num;
    private String qty;
    private String reported_time;

    public String getPacker() {
        return packer;
    }

    public void setPacker(String packer) {
        this.packer = packer;
    }

    public String getPicker() {
        return picker;
    }

    public void setPicker(String picker) {
        this.picker = picker;
    }

    public String getInventory_id() {
        return inventory_id;
    }

    public void setInventory_id(String inventory_id) {
        this.inventory_id = inventory_id;
    }

    public String getInventory_num() {
        return inventory_num;
    }

    public void setInventory_num(String inventory_num) {
        this.inventory_num = inventory_num;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getReported_time() {
        return reported_time;
    }

    public void setReported_time(String reported_time) {
        this.reported_time = reported_time;
    }
}
