package com.owd.dc.warehouse.vendorCompliance.labelsMaker.packageLabels.spscommerce;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SPSCommerceReference {

    public final static String ReferenceQualifier_MerchandiseTypeCode = "MR";
    public final static String ReferenceQualifier_TrackingNumber = "2I";
    public final static String ReferenceQualifier_InvoiceNumber = "INV";
    public final static String ReferenceQualifier_AssociatedPurchaseOrderNumbers = "AN";
    public final static String ReferenceQualifier_TransactionReferenceNumber = "TN";
    public final static String ReferenceQualifier_PromotionDealNumber = "PD";
    public final static String ReferenceQualifier_OrderOriginationCode = "AGL";
    public final static String ReferenceQualifier_PrePackOrGroupingCode = "PPGP";
    public final static String ReferenceQualifier_AppointmentNumber = "AO";
    public final static String ReferenceQualifier_LoadPlanningNumber = "LO";
    public final static String ReferenceQualifier_ProcessHandlingCode = "PHC";
    public final static String ReferenceQualifier_InternalCustomerNumber = "IT";
    public final static String ReferenceQualifier_LotNumber = "LT";
    public final static String ReferenceQualifier_SourceDocumentRevisionNumber = "SDR";
    public final static String ReferenceQualifier_SerialNumber = "SE";
    public final static String ReferenceQualifier_ThirdPartyReferenceNumber = "GK";
    public final static String ReferenceQualifier_ThirdPartyFreightAccountNumber = "TPF";

    public final static String ReferenceQualifier_BuyerIdentification = "YD";
    public final static String ReferenceQualifier_WarehouseBinLocationNumber = "WBN";
    public final static String ReferenceQualifier_BatchNumber = "BT";
    public final static String ReferenceQualifier_GovernmentBillOfLading = "GL";
    public final static String ReferenceQualifier_DeliveryNoteNumber = "DNN";
    public final static String ReferenceQualifier_GovernmentPriorityNumber = "GP";
    public final static String ReferenceQualifier_ProjectCode = "P4";

    @SerializedName("ReferenceQual")
    @Expose
    public String referenceQualifier;

    @SerializedName("ReferenceID")
    @Expose
    public String referenceId;

    @SerializedName("Description")
    @Expose
    public String description;
}
