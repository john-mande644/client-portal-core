package com.owd.magento2Api.Models;

public class Product {
    private int id;
    private String sku;
    private String name;
    private int status;
    private ProductExtentionAttribute extension_attributes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ProductExtentionAttribute getExtension_attributes() {
        return extension_attributes;
    }

    public void setExtension_attributes(ProductExtentionAttribute extension_attributes) {
        this.extension_attributes = extension_attributes;
    }
}
