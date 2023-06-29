package com.owd.dc.manifest.ExternalApis.deliverr.classes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

public class DeliverrRequest {

    @SerializedName("length")
    @Expose
    private Double length;
    @SerializedName("width")
    @Expose
    private Double width;
    @SerializedName("height")
    @Expose
    private Double height;
    @SerializedName("weight")
    @Expose
    private Double weight;
    @SerializedName("items")
    @Expose
    private HashMap items;

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public HashMap getItems() {
        return items;
    }

    public void setItems(HashMap items) {
        this.items = items;
    }

}
