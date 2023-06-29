package com.owd.core.business.autoship;

/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: Mar 27, 2008
 * Time: 2:19:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class autoShipItem {

    private String sku;
    private int qty;
    private Float price;
    private String description;


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }


    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }
}
