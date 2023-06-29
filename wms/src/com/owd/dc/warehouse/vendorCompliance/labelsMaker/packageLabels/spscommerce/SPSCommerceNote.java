package com.owd.dc.warehouse.vendorCompliance.labelsMaker.packageLabels.spscommerce;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SPSCommerceNote {

    // NoteCodes
    public final static String NoteCode_GeneralNote = "GEN";
    public final static String NoteCode_SpecialInstructions = "SPE";
    public final static String NoteCode_ShippingNote = "SHP";

    @SerializedName("NoteCode")
    @Expose
    public String noteCode;

    @SerializedName("Note")
    @Expose
    public String note;
}
