package com.owd.dc.warehouse.vendorCompliance.labelsMaker.packageLabels.spscommerce;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SPSCommerceAccessToken {
    @SerializedName("access_token")
    @Expose
    public  String accessToken;

    @SerializedName("expires_in")
    @Expose
    public int expiresIn;

    @SerializedName("token_type")
    @Expose
    public String tokenType;

    public String toJson()
    {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static SPSCommerceAccessToken fromJson(String json)
    {
        Gson gson = new Gson();
        return gson.fromJson(json, SPSCommerceAccessToken.class);
    }
}
