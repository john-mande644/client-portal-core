package com.owd.dc.packing.beans;

/**
 * Created by IntelliJ IDEA.
 * User: danny
 * Date: Dec 16, 2010
 * Time: 2:42:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class PrintableData {
  private  String companyName;
    private String inventoryNum;
    private String stock;
    private String type;
    private String data;


    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getInventoryNum() {
        return inventoryNum;
    }

    public void setInventoryNum(String inventoryNum) {
        this.inventoryNum = inventoryNum;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
