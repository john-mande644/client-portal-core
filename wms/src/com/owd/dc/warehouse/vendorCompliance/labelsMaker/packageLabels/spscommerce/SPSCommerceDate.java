package com.owd.dc.warehouse.vendorCompliance.labelsMaker.packageLabels.spscommerce;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SPSCommerceDate {

    // DateTimeQualifiers
    public final static String DateTimeQualifier_Production = "405";
    public final static String DateTimeQualifier_Discontinued = "036";
    public final static String DateTimeQualifier_PromotionStart = "015";
    public final static String DateTimeQualifier_RequestedShip = "010";
    public final static String DateTimeQualifier_EstimatedDelivery = "017";
    public final static String DateTimeQualifier_ActualShip = "011";

    @SerializedName("DateTimeQualifier")
    @Expose
    public String dateTimeQualifier;

    @SerializedName("Date")
    @Expose
    public String date;

}
