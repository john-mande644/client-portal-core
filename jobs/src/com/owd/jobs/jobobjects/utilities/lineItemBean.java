package com.owd.jobs.jobobjects.utilities;

/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: Apr 23, 2007
 * Time: 10:39:49 AM
 * To change this template use File | Settings | File Templates.
 */
public class lineItemBean {
     private String quanity;
    private String inventory_num;
    private String desc;
    private String price;
    private String subtotal;
    private String itemToUnmap;


    public String toString() {
        return "lineItemBean{" +
                "quanity='" + quanity + '\'' +
                ", inventory_num='" + inventory_num + '\'' +
                ", desc='" + desc + '\'' +
                ", price='" + price + '\'' +
                ", subtotal='" + subtotal + '\'' +
                ", itemToUnmap='" + itemToUnmap + '\'' +
                '}';
    }

    public String getItemToUnmap() {
        return itemToUnmap;
    }

    public void setItemToUnmap(String itemToUnmap) {
        this.itemToUnmap = itemToUnmap;
    }

    public String getQuanity() {
        return quanity;
    }

    public void setQuanity(String quanity) {
        this.quanity = quanity;
    }

    public String getInventory_num() {
        return inventory_num;
    }

    public void setInventory_num(String inventory_num) {
        this.inventory_num = inventory_num;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }
}
