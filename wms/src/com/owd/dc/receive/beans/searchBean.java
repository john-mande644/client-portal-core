package com.owd.dc.receive.beans;

/**
 * Created by danny on 3/18/2017.
 */
public class searchBean {

    private String inventoryFkey;
    private String inventory_num;
    private String title = "";
    private String description = "";
    private String expected;


    public String getInventoryFkey() {
        return inventoryFkey;
    }

    public void setInventoryFkey(String inventoryFkey) {
        this.inventoryFkey = inventoryFkey;
    }

    public String getInventory_num() {
        return inventory_num;
    }

    public void setInventory_num(String inventory_num) {
        this.inventory_num = inventory_num;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExpected() {
        return expected;
    }

    public void setExpected(String expected) {
        this.expected = expected;
    }
}
