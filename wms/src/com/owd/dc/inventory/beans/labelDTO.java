package com.owd.dc.inventory.beans;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Oct 20, 2005
 * Time: 11:01:40 AM
 * To change this template use File | Settings | File Templates.
 */
public class labelDTO {
    private int sku;
    private int numlabels;
    private String action;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public int getSku() {
        return sku;
    }

    public void setSku(int sku) {
        this.sku = sku;
    }

    public int getNumlabels() {
        return numlabels;
    }

    public void setNumlabels(int numlabels) {
        this.numlabels = numlabels;
    }


}
