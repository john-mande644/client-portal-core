package com.owd.dc.warehouse.vendorCompliance.labelsMaker.packageLabels.spscommerce;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SPSCommerceSubline {

    @SerializedName("VendorPartNumber")
    @Expose
    public String vendorPartNumber;

    @SerializedName("ProductSizeDescription")
    @Expose
    public String productSizeDescription;

    @SerializedName("ProductColorDescription")
    @Expose
    public String productColorDescription;

    @SerializedName("QtyPer")
    @Expose
    public double quantityPer;

    @SerializedName("ProductOrItemDescription")
    @Expose
    public List<SPSCommerceProductOrItemDescription> productOrItemDescriptions;

}
