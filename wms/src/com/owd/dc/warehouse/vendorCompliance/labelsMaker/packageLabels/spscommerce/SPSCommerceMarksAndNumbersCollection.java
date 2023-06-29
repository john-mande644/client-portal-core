package com.owd.dc.warehouse.vendorCompliance.labelsMaker.packageLabels.spscommerce;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SPSCommerceMarksAndNumbersCollection {

    // MarksAndNumbersQualifiers
    public final static String MarksAndNumbersQualifier_ShipperAssignedCaseNumber = "CA";
    public final static String MarksAndNumbersQualifier_UPCShippingContainerCode = "UC";

    @SerializedName("MarksAndNumbersQualifier1")
    @Expose
    public String marksAndNumbersQualifier1;

    @SerializedName("MarksAndNumbers1")
    @Expose
    public String marksAndNumbers1;
}
