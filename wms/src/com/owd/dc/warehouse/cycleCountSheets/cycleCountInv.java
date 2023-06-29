package com.owd.dc.warehouse.cycleCountSheets;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: danny
 * Date: 6/16/14
 * Time: 12:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class cycleCountInv {
    private String id;
    private String invNumber;
    private String desc;
    private String qty;
    private String supplier;
    private List<String> location = new ArrayList<String>();
    private String upc;
    private String isbn;
    private Map<String,Integer> lots;


    public Map<String, Integer> getLots() {
        return lots;
    }

    public void setLots(Map<String, Integer> lots) {
        this.lots = lots;
    }

    public String getUpc() {
        return upc;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInvNumber() {
        return invNumber;
    }

    public void setInvNumber(String invNumber) {
        this.invNumber = invNumber;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public List<String> getLocation() {
        return location;
    }

    public void setLocation(List<String> location) {
        this.location = location;
    }
}
