package com.owd.dc.warehouse.vendorCompliance.labelsMaker.packageLabels.spscommerce;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SPSCommerceGetAccessTokenRequest {
    @SerializedName("grant_type")
    @Expose
    public  String grantType = "client_credentials";

    @SerializedName("client_id")
    @Expose
    public String clientId;

    @SerializedName("client_secret")
    @Expose
    public String clientSecret;

    @SerializedName("audience")
    @Expose
    public String audience = "api://api.spscommerce.com/";

    public String toJson()
    {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static SPSCommerceGetAccessTokenRequest fromJson(String json)
    {
        Gson gson = new Gson();
        return gson.fromJson(json, SPSCommerceGetAccessTokenRequest.class);
    }

}
