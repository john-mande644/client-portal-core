package com.owd.dc.warehouse.vendorCompliance.labelsMaker.packageLabels.spscommerce;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SPSCommerceProductId {

    // PartNumberQualifiers
    public final static String PartNumberQualifier_StockKeepingUnit = "SK";
    public final static String PartNumberQualifier_BuyersStyleNumber = "IT";
    public final static String PartNumberQualifier_VendorsStyleNumber = "VA";
    public final static String PartNumberQualifier_ManufacturersPartNumber = "MG";
    public final static String PartNumberQualifier_ModelNumber = "MN";

    @SerializedName("PartNumberQual")
    @Expose
    public String partNumberQualifier;

    @SerializedName("PartNumber")
    @Expose
    public String partNumber;

}
