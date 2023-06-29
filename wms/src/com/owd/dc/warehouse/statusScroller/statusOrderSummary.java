package com.owd.dc.warehouse.statusScroller;

/**
 * Created with IntelliJ IDEA.
 * User: danny
 * Date: 5/16/14
 * Time: 9:44 AM
 * To change this template use File | Settings | File Templates.
 */
public class statusOrderSummary {
    private String itemGroup1;
    private String itemGroup2;
    private int orders;
    private int shippedOrders;
    private int ordersLeftToShip;
    private int packedOrders;
    private int pickedOrders;
    private int unpickedOrders;
    private int packedUnshippedOrders;

    public String getItemGroup1() {
        return itemGroup1;
    }

    public void setItemGroup1(String itemGroup1) {
        this.itemGroup1 = itemGroup1;
    }

    public String getItemGroup2() {
        return itemGroup2;
    }

    public void setItemGroup2(String itemGroup2) {
        this.itemGroup2 = itemGroup2;
    }

    public int getOrders() {
        return orders;
    }

    public void setOrders(int orders) {
        this.orders = orders;
    }

    public int getShippedOrders() {
        return shippedOrders;
    }

    public void setShippedOrders(int shippedOrders) {
        this.shippedOrders = shippedOrders;
    }

    public int getOrdersLeftToShip() {
        return ordersLeftToShip;
    }

    public void setOrdersLeftToShip(int ordersLeftToShip) {
        this.ordersLeftToShip = ordersLeftToShip;
    }

    public int getPackedOrders() {
        return packedOrders;
    }

    public void setPackedOrders(int packedOrders) {
        this.packedOrders = packedOrders;
    }

    public int getPickedOrders() {
        return pickedOrders;
    }

    public void setPickedOrders(int pickedOrders) {
        this.pickedOrders = pickedOrders;
    }

    public int getUnpickedOrders() {
        return unpickedOrders;
    }

    public void setUnpickedOrders(int unpickedOrders) {
        this.unpickedOrders = unpickedOrders;
    }

    public int getPackedUnshippedOrders() {
        return packedUnshippedOrders;
    }

    public void setPackedUnshippedOrders(int packedUnshippedOrders) {
        this.packedUnshippedOrders = packedUnshippedOrders;
    }
}
