package com.owd.dc.warehouse.vendorCompliance.labelsMaker.packageLabels.spscommerce;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SPSCommercePhysicalDetails {

    // PackQualifiers
    public final static String PackQualifier_Outer = "OU";
    public final static String PackQualifier_Inner = "IN";

    // WeightQualifiers
    public final static String WeightQualifier_GrossWeight = "G";
    public final static String WeightQualifier_ActualNetWeight = "N";

    // PackUOMs
    public final static String PackUOM_Each = "EA";
    public final static String PackUOM_Pound = "LB";
    public final static String PackUOM_OunceAv = "OZ";
    public final static String PackUOM_Container = "CH";
    public final static String PackUOM_Carton = "CT";
    public final static String PackUOM_Roll = "RL";

    // PackWeightUOMs
    public final static String PackWeightUOM_Pound = "LB";
    public final static String PackWeightUOM_Gram = "GR";
    public final static String PackWeightUOM_Kilogram = "KG";

    // PackingMediums
    public final static String PackingMedium_Carton = "CTN";
    public final static String PackingMedium_Pallet = "PLT";

    @SerializedName("PackQualifier")
    @Expose
    public String packQualifier;

    @SerializedName("WeightQualifier")
    @Expose
    public String weightQualifier;

    @SerializedName("PackWeight")
    @Expose
    public double packWeight;

    @SerializedName("PackValue")
    @Expose
    public double packValue;

    @SerializedName("PackSize")
    @Expose
    public double packSize;

    @SerializedName("PackUOM")
    @Expose
    public String packUOM;

    @SerializedName("PackWeightUOM")
    @Expose
    public String packWeightUOM;

    @SerializedName("PackingMedium")
    @Expose
    public String packingMedium;

    @SerializedName("Description")
    @Expose
    public String description;

    @SerializedName("PackLength")
    @Expose
    public double packLength;


}
