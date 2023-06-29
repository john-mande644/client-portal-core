package com.owd.dc.warehouse.vendorCompliance.labelsMaker.packageLabels.spscommerce;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SPSCommerceCarrierInformation {

    // CarrierTransMethodCodes
    public final static String CarrierTransMethodCodes_FedExGround = "FDEG";

    @SerializedName("CarrierRouting")
    @Expose
    public String carrierRouting;

    @SerializedName("CarrierAlphaCode")
    @Expose
    public String carrierAlphaCode;

    @SerializedName("CarrierTransMethodCode")
    @Expose
    public String carrierTransMethodCode;
}
