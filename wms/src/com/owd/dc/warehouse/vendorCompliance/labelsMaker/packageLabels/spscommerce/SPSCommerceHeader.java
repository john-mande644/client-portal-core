package com.owd.dc.warehouse.vendorCompliance.labelsMaker.packageLabels.spscommerce;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SPSCommerceHeader {
    @SerializedName("PurchaseOrderNumber")
    @Expose
    public String purchaseOrderNumber;

    @SerializedName("BillOfLadingNumber")
    @Expose
    public String billOfLadingNumber;

    @SerializedName("Address")
    @Expose
    public List<SPSCommerceAddress> addresses;

    @SerializedName("CarrierInformation")
    @Expose
    public List<SPSCommerceCarrierInformation> carrierInformations;

    @SerializedName("CarrierProNumber")
    @Expose
    public String carrierProNumber;

    @SerializedName("ShipDate")
    @Expose
    public String shipDate;

    @SerializedName("Dates")
    @Expose
    public List<SPSCommerceDate> dates;

    @SerializedName("Department")
    @Expose
    public String department;

    @SerializedName("References")
    @Expose
    public List<SPSCommerceReference> references;

    @SerializedName("TradingPartnerId")
    @Expose
    public String tradingPartnerId;

    @SerializedName("Vendor")
    @Expose
    public String vendor;

    @SerializedName("ShipmentIdentification")
    @Expose
    public double shipmentIdentification;

    @SerializedName("QuantityTotals")
    @Expose
    public List<SPSCommerceQuantityTotals> quantityTotals;

    @SerializedName("CustomerOrderNumber")
    @Expose
    public String customerOrderNumber;

    @SerializedName("Division")
    @Expose
    public String division;

    @SerializedName("Notes")
    @Expose
    public List<SPSCommerceNote> notes;

    @SerializedName("PurchaseOrderDate")
    @Expose
    public String purchaseOrderDate;

    @SerializedName("CustomerAccountNumber")
    @Expose
    public String customerAccountNumber;

    @SerializedName("Contacts")
    @Expose
    public List<SPSCommerceContact> contacts;

    @SerializedName("AppointmentNumber")
    @Expose
    public String appointmentNumber;

    @SerializedName("FOBRelatedInstructions")
    @Expose
    public List<SPSCommerceFOBLocation> fobRelatedInstructions;



}
