package com.owd.web.internal.zplutils;

public class Vendor {
    String name;
    int id;

    public Vendor(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
