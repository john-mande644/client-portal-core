package com.owd.dc.warehouse.cycleCountSheets;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created with IntelliJ IDEA.
 * User: danny
 * Date: 6/16/14
 * Time: 12:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class cycleCountSheetData {
    private String client;
    private String date;
    private Map<String,cycleCountInv> inventory = new TreeMap<String, cycleCountInv>();


    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Map<String, cycleCountInv> getInventory() {
        return inventory;
    }

    public void setInventory(Map<String, cycleCountInv> inventory) {
        this.inventory = inventory;
    }
}
