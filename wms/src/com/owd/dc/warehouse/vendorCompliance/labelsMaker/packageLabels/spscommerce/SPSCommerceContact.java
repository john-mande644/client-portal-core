package com.owd.dc.warehouse.vendorCompliance.labelsMaker.packageLabels.spscommerce;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SPSCommerceContact {

    // ContactTypeCodes
    public final static String ContactTypeCode_ReceivingContact = "RE";
    public final static String ContactTypeCode_PrimaryInformationContact = "IC";

    @SerializedName("PrimaryPhone")
    @Expose
    public String primaryPhone;

    @SerializedName("ContactTypeCode")
    @Expose
    public String contactTypeCode;

    @SerializedName("PrimaryEmail")
    @Expose
    public String primaryEmail;

    @SerializedName("ContactName")
    @Expose
    public String contactName;
}
