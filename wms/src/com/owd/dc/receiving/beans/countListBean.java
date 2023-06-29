package com.owd.dc.receiving.beans;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Apr 11, 2006
 * Time: 8:17:33 AM
 * To change this template use File | Settings | File Templates.
 */
public class countListBean {
    private String sku;
    private String count;
    private String damage;


    public String getDamage() {
        return damage;
    }

    public void setDamage(String damage) {
        this.damage = damage;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }
}
