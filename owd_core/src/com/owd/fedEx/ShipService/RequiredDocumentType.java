/*
 * RequiredDocumentType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;


/**
 * This identifies the document types that can be required. This can
 * also indicate when either a COMMERCIAL_INVOICE or a PRO_FORMA_INVOICE
 * is required through the COMMERCIAL_OR_PRO_FORMA_INVOICE option.
 */
public class RequiredDocumentType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected RequiredDocumentType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _AIR_WAYBILL = "AIR_WAYBILL";
    public static final java.lang.String _CERTIFICATE_OF_ORIGIN = "CERTIFICATE_OF_ORIGIN";
    public static final java.lang.String _COMMERCIAL_INVOICE = "COMMERCIAL_INVOICE";
    public static final java.lang.String _COMMERCIAL_OR_PRO_FORMA_INVOICE = "COMMERCIAL_OR_PRO_FORMA_INVOICE";
    public static final java.lang.String _NAFTA_CERTIFICATE_OF_ORIGIN = "NAFTA_CERTIFICATE_OF_ORIGIN";
    public static final java.lang.String _PRO_FORMA_INVOICE = "PRO_FORMA_INVOICE";
    public static final RequiredDocumentType AIR_WAYBILL = new RequiredDocumentType(_AIR_WAYBILL);
    public static final RequiredDocumentType CERTIFICATE_OF_ORIGIN = new RequiredDocumentType(_CERTIFICATE_OF_ORIGIN);
    public static final RequiredDocumentType COMMERCIAL_INVOICE = new RequiredDocumentType(_COMMERCIAL_INVOICE);
    public static final RequiredDocumentType COMMERCIAL_OR_PRO_FORMA_INVOICE = new RequiredDocumentType(_COMMERCIAL_OR_PRO_FORMA_INVOICE);
    public static final RequiredDocumentType NAFTA_CERTIFICATE_OF_ORIGIN = new RequiredDocumentType(_NAFTA_CERTIFICATE_OF_ORIGIN);
    public static final RequiredDocumentType PRO_FORMA_INVOICE = new RequiredDocumentType(_PRO_FORMA_INVOICE);
    public java.lang.String getValue() { return _value_;}
    public static RequiredDocumentType fromValue(java.lang.String value)
          throws java.lang.RuntimeException {
        RequiredDocumentType enumX = (RequiredDocumentType)
            _table_.get(value);
        if (enumX==null) throw new java.lang.RuntimeException(getEnumErrorMsg(value));
        return enumX;
    }
    public static RequiredDocumentType fromString(java.lang.String value)
          throws java.lang.RuntimeException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static java.lang.String getEnumErrorMsg(Object value) 
	{ 
		return "'" + value + "' is not a valid RequiredDocumentType value."; 
	}
}
