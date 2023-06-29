package com.owd.dc.warehouse.vendorCompliance.labelsMaker.packageLabels.spscommerce;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SPSCommerceAddress {

    // AddressTypeCodes
    public final static String AddressTypeCode_ShipFrom = "SF";
    public final static String AddressTypeCode_ShipTo = "ST";
    public final static String AddressTypeCode_Vendor = "VN";
    public final static String AddressTypeCode_CountryOfOrigin = "CT";
    public final static String AddressTypeCode_BillToParty = "BT";
    public final static String AddressTypeCode_Consolidator = "CS";
    public final static String AddressTypeCode_DeliveryAddress = "DA";
    public final static String AddressTypeCode_ManufacturerOfGoods = "MF";
    public final static String AddressTypeCode_MarkForParty = "Z7";
    public final static String AddressTypeCode_Warehouse = "WH";
    public final static String AddressTypeCode_OrderedBy = "OB";

    // LocationCodeQualifiers
    public final static String LocationCodeQualifier_DunsNumber = "1";
    public final static String LocationCodeQualifier_DunsPlus4Number = "9";
    public final static String LocationCodeQualifier_SellerLocationNumber = "91";
    public final static String LocationCodeQualifier_BuyerLocationNumber = "92";
    public final static String LocationCodeQualifier_GlobalLocationNumber = "UL";


    @SerializedName("AddressTypeCode")
    @Expose
    public String addressTypeCode;

    @SerializedName("AddressName")
    @Expose
    public String addressName;

    @SerializedName("Address1")
    @Expose
    public String address1;

    @SerializedName("Address2")
    @Expose
    public String address2;

    @SerializedName("City")
    @Expose
    public String city;

    @SerializedName("PostalCode")
    @Expose
    public String postalCode;

    @SerializedName("LocationCodeQualifier")
    @Expose
    public String locationCodeQualifier;

    @SerializedName("AddressLocationNumber")
    @Expose
    public String addressLocationNumber;

    @SerializedName("State")
    @Expose
    public String state;

    @SerializedName("Country")
    @Expose
    public String country;

    @SerializedName("AddressAlternateName")
    @Expose
    public String addressAlternateName;

    @SerializedName("AddressAlternateName2")
    @Expose
    public String addressAlternateName2;

    @SerializedName("Address3")
    @Expose
    public String address3;

    @SerializedName("Address4")
    @Expose
    public String address4;

    @SerializedName("Contacts")
    @Expose
    public List<SPSCommerceContact> contacts;




}
