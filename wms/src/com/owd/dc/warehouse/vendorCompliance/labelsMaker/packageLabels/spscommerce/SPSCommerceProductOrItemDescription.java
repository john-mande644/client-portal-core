package com.owd.dc.warehouse.vendorCompliance.labelsMaker.packageLabels.spscommerce;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SPSCommerceProductOrItemDescription {

    // ProductCharacteristicCodes
    public final static String ProductCharacteristicCode_SizeDescription = "74";
    public final static String ProductCharacteristicCode_ProductDescription = "08";

    @SerializedName("ProductCharacteristicCode")
    @Expose
    public String productCharacteristicCode;

    @SerializedName("ProductDescription")
    @Expose
    public String productDescription;

    @SerializedName("ProductDescriptionCode")
    @Expose
    public String productDescriptionCode;
}
