package com.owd.dc.warehouse.vendorCompliance.labelsMaker.packageLabels.spscommerce;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SPSCommerceZPLData {
    @SerializedName("zplData")
    @Expose
    public List<String> zplData;

    public String toJson()
    {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static SPSCommerceZPLData fromJson(String json)
    {
        Gson gson = new Gson();
        return gson.fromJson(json, SPSCommerceZPLData.class);
    }
}
