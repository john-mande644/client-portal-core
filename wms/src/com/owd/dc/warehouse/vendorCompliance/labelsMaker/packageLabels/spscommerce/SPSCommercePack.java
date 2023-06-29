package com.owd.dc.warehouse.vendorCompliance.labelsMaker.packageLabels.spscommerce;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SPSCommercePack {

    // PackLevelTypes
    public final static String PackLevelType_Pack = "P";
    public final static String PackLevelType_TarePallet = "T";

    @SerializedName("PackLevelType")
    @Expose
    public String packLevelType;

    @SerializedName("ShippingSerialID")
    @Expose
    public String shippingSerialId;

    @SerializedName("Address")
    @Expose
    public List<SPSCommerceAddress> addresses;

    @SerializedName("Item")
    @Expose
    public List<SPSCommerceItem> items;

    @SerializedName("PhysicalDetails")
    @Expose
    public List<SPSCommercePhysicalDetails> physicalDetails;

    @SerializedName("MarksAndNumbersCollection")
    @Expose
    public List<SPSCommerceMarksAndNumbersCollection> marksAndNumbersCollections;

    @SerializedName("Notes")
    @Expose
    public List<SPSCommerceNote> notes;

    @SerializedName("CarrierPackageID")
    @Expose
    public String carrierPackageId;

    @SerializedName("Pack")
    @Expose
    public List<SPSCommercePack> packs;

}
