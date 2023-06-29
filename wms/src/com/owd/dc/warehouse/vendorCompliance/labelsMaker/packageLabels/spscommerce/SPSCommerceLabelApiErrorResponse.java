package com.owd.dc.warehouse.vendorCompliance.labelsMaker.packageLabels.spscommerce;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SPSCommerceLabelApiErrorResponse {

    @SerializedName("status")
    @Expose
    public String status;

    @SerializedName("error")
    @Expose
    public SPSCommerceLabelApiError error;

    public String toJson()
    {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static SPSCommerceLabelApiErrorResponse fromJson(String json)
    {
        Gson gson = new Gson();
        return gson.fromJson(json, SPSCommerceLabelApiErrorResponse.class);
    }
}
