package com.owd.magento2Api.Models;

public class Store {
    private int id;
    private String code;
    private String name;
    private int website_id;
    private int store_group_id;
    private int is_active;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWebsite_id() {
        return website_id;
    }

    public void setWebsite_id(int website_id) {
        this.website_id = website_id;
    }

    public int getStore_group_id() {
        return store_group_id;
    }

    public void setStore_group_id(int store_group_id) {
        this.store_group_id = store_group_id;
    }

    public int getIs_active() {
        return is_active;
    }

    public void setIs_active(int is_active) {
        this.is_active = is_active;
    }
}
