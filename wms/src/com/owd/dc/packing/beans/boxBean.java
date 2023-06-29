package com.owd.dc.packing.beans;

/**
 * Created with IntelliJ IDEA.
 * User: danny
 * Date: 11/28/14
 * Time: 5:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class boxBean {

    private String id;
    private String name;
    private String description;
    private String barcode;

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
