package com.owd.magento2Api.Models;

public class WebSite {
    private int id;
    private String code;
    private String name;
    private int default_group_id;

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

    public int getDefault_group_id() {
        return default_group_id;
    }

    public void setDefault_group_id(int default_group_id) {
        this.default_group_id = default_group_id;
    }
}
