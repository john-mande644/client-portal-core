package com.owd.dc.warehouse.vendorCompliance.labelsMaker.packageLabels.spscommerce;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SPSCommerceItem {

    // ShipQuantityUOMs
    public final static String ShipQuantityUOM_Bundle = "BD";
    public final static String ShipQuantityUOM_Bag = "BG";
    public final static String ShipQuantityUOM_Book = "BK";
    public final static String ShipQuantityUOM_Box = "BX";
    public final static String ShipQuantityUOM_Case = "CA";
    public final static String ShipQuantityUOM_Each = "EA";
    public final static String ShipQuantityUOM_Gallon = "GA";
    public final static String ShipQuantityUOM_HalfGallon = "GH";
    public final static String ShipQuantityUOM_Inch = "IN";
    public final static String ShipQuantityUOM_Kilogram = "KG";
    public final static String ShipQuantityUOM_Pound = "LB";
    public final static String ShipQuantityUOM_Pad = "PD";
    public final static String ShipQuantityUOM_Pack = "PH";
    public final static String ShipQuantityUOM_Package = "PK";
    public final static String ShipQuantityUOM_PalletUnitLoad = "PL";
    public final static String ShipQuantityUOM_Roll = "RL";
    public final static String ShipQuantityUOM_Ream = "RM";
    public final static String ShipQuantityUOM_SquareFoot = "SF";
    public final static String ShipQuantityUOM_Sheet = "SH";
    public final static String ShipQuantityUOM_Set = "ST";
    public final static String ShipQuantityUOM_OunceAv = "OZ";

    @SerializedName("BuyerPartNumber")
    @Expose
    public String buyerPartNumber;

    @SerializedName("ConsumerPackageCode")
    @Expose
    public String consumerPackageCode;

    @SerializedName("ShipQty")
    @Expose
    public double shipQuantity;

    @SerializedName("VendorPartNumber")
    @Expose
    public String vendorPartNumber;

    @SerializedName("ProductOrItemDescription")
    @Expose
    public List<SPSCommerceProductOrItemDescription> productOrItemDescriptions;

    @SerializedName("MarksAndNumbersCollection")
    @Expose
    public List<SPSCommerceMarksAndNumbersCollection> marksAndNumbersCollection;

    @SerializedName("References")
    @Expose
    public List<SPSCommerceReference> references;

    @SerializedName("Dates")
    @Expose
    public List<SPSCommerceDate> dates;

    @SerializedName("GTIN")
    @Expose
    public String gtin;

    @SerializedName("ProductSizeDescription")
    @Expose
    public String productSizeDescription;

    @SerializedName("ProductColorDescription")
    @Expose
    public String productColorDescription;

    @SerializedName("Subline")
    @Expose
    public List<SPSCommerceSubline> sublines;

    @SerializedName("ProductID")
    @Expose
    public SPSCommerceProductId productId;

    @SerializedName("ProductMaterialCode")
    @Expose
    public String productMaterialCode;

    @SerializedName("ProductMaterialDescription")
    @Expose
    public String productMaterialDescription;

    @SerializedName("UPCCaseCode")
    @Expose
    public String upcCaseCode;

    @SerializedName("Department")
    @Expose
    public String department;

    @SerializedName("DepartmentDescription")
    @Expose
    public String departmentDescription;

    @SerializedName("Notes")
    @Expose
    public List<SPSCommerceNote> notes;

    @SerializedName("PhysicalDetails")
    @Expose
    public List<SPSCommercePhysicalDetails> physicalDetails;

    @SerializedName("ProductSizeCode")
    @Expose
    public String productSizeCode;

    @SerializedName("ProductColorCode")
    @Expose
    public String productColorCode;

    @SerializedName("EAN")
    @Expose
    public String ean;

    @SerializedName("InternationalStandardBookNumber")
    @Expose
    public String internationalStandardBookNumber;

    @SerializedName("Class")
    @Expose
    public String className;

    @SerializedName("OrderQty")
    @Expose
    public double orderQuantity;

    @SerializedName("ShipQtyUOM")
    @Expose
    public String ShipQuantityUOM;
}
