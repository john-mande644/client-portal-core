package com.owd.dc.warehouse.vendorCompliance.labelsMaker.packageLabels.spscommerce;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SPSCommerceLabelApiError {

    @SerializedName("error")
    @Expose
    public String error;

    @SerializedName("errorDescription")
    @Expose
    public String errorDescription;
}
