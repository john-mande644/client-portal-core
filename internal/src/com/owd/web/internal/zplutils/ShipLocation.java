package com.owd.web.internal.zplutils;

public class ShipLocation {
    public String display;
    public int index;

    public ShipLocation(String display, int index) {
        this.display = display;
        this.index = index;
    }

    public String getDisplay() {
        return display;
    }

    public int getIndex() {
        return index;
    }
}
