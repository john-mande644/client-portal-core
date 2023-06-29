package com.owd.dc.picking.beans;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Oct 21, 2005
 * Time: 3:01:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class pickStatusBean {
    private String orderNum;
    private int currentItem;
    private int numberOfItems;
    private int currentPicked;
    private int currentTotal;
    private String sku;
    private String license;

    public String getLicense() {
        return license;
    }


    public void setLicense(String license) {
        this.license = license;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public int getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(int currentItem) {
        this.currentItem = currentItem;
    }

    public int getNumberOfItems() {
        return numberOfItems;
    }

    public void setNumberOfItems(int numberOfItems) {
        this.numberOfItems = numberOfItems;
    }

    public int getCurrentPicked() {
        return currentPicked;
    }

    public void setCurrentPicked(int currentPicked) {
        this.currentPicked = currentPicked;
    }

    public int getCurrentTotal() {
        return currentTotal;
    }

    public void setCurrentTotal(int currentTotal) {
        this.currentTotal = currentTotal;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }
}
