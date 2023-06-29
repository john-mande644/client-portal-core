package com.owd.dc.warehouse.vendorCompliance.labelsMaker.packageLabels.spscommerce;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SPSCommerceGetShippingLabelRequest {

    @SerializedName("Header")
    @Expose
    public SPSCommerceHeader header;

    @SerializedName("Pack")
    @Expose
    public List<SPSCommercePack> packs;

    public String toJson()
    {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static SPSCommerceGetShippingLabelRequest fromJson(String json)
    {
        Gson gson = new Gson();
        return gson.fromJson(json, SPSCommerceGetShippingLabelRequest.class);
    }
}
