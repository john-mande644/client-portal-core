package com.owd.magento2Api.Models;

public class Stock {
    private int stock_id;
    private String name;
    private StockExtentionAttributes extension_attributes;

    public int getStock_id() {
        return stock_id;
    }

    public void setStock_id(int stock_id) {
        this.stock_id = stock_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StockExtentionAttributes getExtension_attributes() {
        return extension_attributes;
    }

    public void setExtension_attributes(StockExtentionAttributes extension_attributes) {
        this.extension_attributes = extension_attributes;
    }
}