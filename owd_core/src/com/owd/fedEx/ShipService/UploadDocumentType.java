/*
 * UploadDocumentType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;

public class UploadDocumentType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected UploadDocumentType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _CERTIFICATE_OF_ORIGIN = "CERTIFICATE_OF_ORIGIN";
    public static final java.lang.String _COMMERCIAL_INVOICE = "COMMERCIAL_INVOICE";
    public static final java.lang.String _ETD_LABEL = "ETD_LABEL";
    public static final java.lang.String _NAFTA_CERTIFICATE_OF_ORIGIN = "NAFTA_CERTIFICATE_OF_ORIGIN";
    public static final java.lang.String _NET_RATE_SHEET = "NET_RATE_SHEET";
    public static final java.lang.String _OTHER = "OTHER";
    public static final java.lang.String _PRO_FORMA_INVOICE = "PRO_FORMA_INVOICE";
    public static final UploadDocumentType CERTIFICATE_OF_ORIGIN = new UploadDocumentType(_CERTIFICATE_OF_ORIGIN);
    public static final UploadDocumentType COMMERCIAL_INVOICE = new UploadDocumentType(_COMMERCIAL_INVOICE);
    public static final UploadDocumentType ETD_LABEL = new UploadDocumentType(_ETD_LABEL);
    public static final UploadDocumentType NAFTA_CERTIFICATE_OF_ORIGIN = new UploadDocumentType(_NAFTA_CERTIFICATE_OF_ORIGIN);
    public static final UploadDocumentType NET_RATE_SHEET = new UploadDocumentType(_NET_RATE_SHEET);
    public static final UploadDocumentType OTHER = new UploadDocumentType(_OTHER);
    public static final UploadDocumentType PRO_FORMA_INVOICE = new UploadDocumentType(_PRO_FORMA_INVOICE);
    public java.lang.String getValue() { return _value_;}
    public static UploadDocumentType fromValue(java.lang.String value)
          throws java.lang.RuntimeException {
        UploadDocumentType enumX = (UploadDocumentType)
            _table_.get(value);
        if (enumX==null) throw new java.lang.RuntimeException(getEnumErrorMsg(value));
        return enumX;
    }
    public static UploadDocumentType fromString(java.lang.String value)
          throws java.lang.RuntimeException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static java.lang.String getEnumErrorMsg(Object value) 
	{ 
		return "'" + value + "' is not a valid UploadDocumentType value."; 
	}
}
