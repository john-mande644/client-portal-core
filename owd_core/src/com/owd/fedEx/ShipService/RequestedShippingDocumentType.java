/*
 * RequestedShippingDocumentType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;

public class RequestedShippingDocumentType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected RequestedShippingDocumentType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _CERTIFICATE_OF_ORIGIN = "CERTIFICATE_OF_ORIGIN";
    public static final java.lang.String _COMMERCIAL_INVOICE = "COMMERCIAL_INVOICE";
    public static final java.lang.String _CUSTOMER_SPECIFIED_LABELS = "CUSTOMER_SPECIFIED_LABELS";
    public static final java.lang.String _CUSTOM_PACKAGE_DOCUMENT = "CUSTOM_PACKAGE_DOCUMENT";
    public static final java.lang.String _CUSTOM_SHIPMENT_DOCUMENT = "CUSTOM_SHIPMENT_DOCUMENT";
    public static final java.lang.String _DANGEROUS_GOODS_SHIPPERS_DECLARATION = "DANGEROUS_GOODS_SHIPPERS_DECLARATION";
    public static final java.lang.String _EXPORT_DECLARATION = "EXPORT_DECLARATION";
    public static final java.lang.String _FEDEX_FREIGHT_STRAIGHT_BILL_OF_LADING = "FEDEX_FREIGHT_STRAIGHT_BILL_OF_LADING";
    public static final java.lang.String _GENERAL_AGENCY_AGREEMENT = "GENERAL_AGENCY_AGREEMENT";
    public static final java.lang.String _LABEL = "LABEL";
    public static final java.lang.String _NAFTA_CERTIFICATE_OF_ORIGIN = "NAFTA_CERTIFICATE_OF_ORIGIN";
    public static final java.lang.String _OP_900 = "OP_900";
    public static final java.lang.String _PRO_FORMA_INVOICE = "PRO_FORMA_INVOICE";
    public static final java.lang.String _RETURN_INSTRUCTIONS = "RETURN_INSTRUCTIONS";
    public static final java.lang.String _VICS_BILL_OF_LADING = "VICS_BILL_OF_LADING";
    public static final RequestedShippingDocumentType CERTIFICATE_OF_ORIGIN = new RequestedShippingDocumentType(_CERTIFICATE_OF_ORIGIN);
    public static final RequestedShippingDocumentType COMMERCIAL_INVOICE = new RequestedShippingDocumentType(_COMMERCIAL_INVOICE);
    public static final RequestedShippingDocumentType CUSTOMER_SPECIFIED_LABELS = new RequestedShippingDocumentType(_CUSTOMER_SPECIFIED_LABELS);
    public static final RequestedShippingDocumentType CUSTOM_PACKAGE_DOCUMENT = new RequestedShippingDocumentType(_CUSTOM_PACKAGE_DOCUMENT);
    public static final RequestedShippingDocumentType CUSTOM_SHIPMENT_DOCUMENT = new RequestedShippingDocumentType(_CUSTOM_SHIPMENT_DOCUMENT);
    public static final RequestedShippingDocumentType DANGEROUS_GOODS_SHIPPERS_DECLARATION = new RequestedShippingDocumentType(_DANGEROUS_GOODS_SHIPPERS_DECLARATION);
    public static final RequestedShippingDocumentType EXPORT_DECLARATION = new RequestedShippingDocumentType(_EXPORT_DECLARATION);
    public static final RequestedShippingDocumentType FEDEX_FREIGHT_STRAIGHT_BILL_OF_LADING = new RequestedShippingDocumentType(_FEDEX_FREIGHT_STRAIGHT_BILL_OF_LADING);
    public static final RequestedShippingDocumentType GENERAL_AGENCY_AGREEMENT = new RequestedShippingDocumentType(_GENERAL_AGENCY_AGREEMENT);
    public static final RequestedShippingDocumentType LABEL = new RequestedShippingDocumentType(_LABEL);
    public static final RequestedShippingDocumentType NAFTA_CERTIFICATE_OF_ORIGIN = new RequestedShippingDocumentType(_NAFTA_CERTIFICATE_OF_ORIGIN);
    public static final RequestedShippingDocumentType OP_900 = new RequestedShippingDocumentType(_OP_900);
    public static final RequestedShippingDocumentType PRO_FORMA_INVOICE = new RequestedShippingDocumentType(_PRO_FORMA_INVOICE);
    public static final RequestedShippingDocumentType RETURN_INSTRUCTIONS = new RequestedShippingDocumentType(_RETURN_INSTRUCTIONS);
    public static final RequestedShippingDocumentType VICS_BILL_OF_LADING = new RequestedShippingDocumentType(_VICS_BILL_OF_LADING);
    public java.lang.String getValue() { return _value_;}
    public static RequestedShippingDocumentType fromValue(java.lang.String value)
          throws java.lang.RuntimeException {
        RequestedShippingDocumentType enumX = (RequestedShippingDocumentType)
            _table_.get(value);
        if (enumX==null) throw new java.lang.RuntimeException(getEnumErrorMsg(value));
        return enumX;
    }
    public static RequestedShippingDocumentType fromString(java.lang.String value)
          throws java.lang.RuntimeException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static java.lang.String getEnumErrorMsg(Object value) 
	{ 
		return "'" + value + "' is not a valid RequestedShippingDocumentType value."; 
	}
}
