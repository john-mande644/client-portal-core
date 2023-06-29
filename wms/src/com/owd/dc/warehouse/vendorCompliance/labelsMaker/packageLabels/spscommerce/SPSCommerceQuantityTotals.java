package com.owd.dc.warehouse.vendorCompliance.labelsMaker.packageLabels.spscommerce;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SPSCommerceQuantityTotals {

    // QuantityTotalsQualifiers
    public final static String QuantityTotalsQualifier_SummaryQuantityTotals = "SQT";

    // WeightQualifiers
    public final static String WeightQualifier_GrossWeight = "G";
    public final static String WeightQualifier_ActualNetWeight = "N";

    // WeightUOMs
    public final static String WeightUOM_Kilogram = "KG";


    @SerializedName("QuantityTotalsQualifier")
    @Expose
    public String quantityTotalsQualifier;

    @SerializedName("WeightQualifier")
    @Expose
    public String weightQualifier;

    @SerializedName("Weight")
    @Expose
    public double weight;

    @SerializedName("WeightUOM")
    @Expose
    public String weightUOM;

    @SerializedName("Quantity")
    @Expose
    public double quantity;
}
