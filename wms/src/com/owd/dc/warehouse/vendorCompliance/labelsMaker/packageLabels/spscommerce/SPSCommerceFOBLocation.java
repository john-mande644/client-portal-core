package com.owd.dc.warehouse.vendorCompliance.labelsMaker.packageLabels.spscommerce;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SPSCommerceFOBLocation {

    // FOBLocationQualifiers
    public final static String FOBLocationQualifier_PortOfLoading = "KL";
    public final static String FOBLocationQualifier_PortOfEntry = "PE";


    @SerializedName("FOBLocationQualifier")
    @Expose
    public String fobLocationQualifier;

    @SerializedName("FOBLocationDescription")
    @Expose
    public String fobLocationDescription;
}
