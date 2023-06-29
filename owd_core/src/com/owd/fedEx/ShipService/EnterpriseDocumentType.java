/*
 * EnterpriseDocumentType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;


/**
 * This identifies some of the document types recognized by Enterprise
 * Document Management Service.
 */
public class EnterpriseDocumentType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected EnterpriseDocumentType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _AIR_WAYBILL = "AIR_WAYBILL";
    public static final java.lang.String _CERTIFICATE_OF_ORIGIN = "CERTIFICATE_OF_ORIGIN";
    public static final java.lang.String _COMMERCIAL_INVOICE = "COMMERCIAL_INVOICE";
    public static final java.lang.String _NAFTA_CERTIFICATE_OF_ORIGIN = "NAFTA_CERTIFICATE_OF_ORIGIN";
    public static final java.lang.String _PRO_FORMA_INVOICE = "PRO_FORMA_INVOICE";
    public static final EnterpriseDocumentType AIR_WAYBILL = new EnterpriseDocumentType(_AIR_WAYBILL);
    public static final EnterpriseDocumentType CERTIFICATE_OF_ORIGIN = new EnterpriseDocumentType(_CERTIFICATE_OF_ORIGIN);
    public static final EnterpriseDocumentType COMMERCIAL_INVOICE = new EnterpriseDocumentType(_COMMERCIAL_INVOICE);
    public static final EnterpriseDocumentType NAFTA_CERTIFICATE_OF_ORIGIN = new EnterpriseDocumentType(_NAFTA_CERTIFICATE_OF_ORIGIN);
    public static final EnterpriseDocumentType PRO_FORMA_INVOICE = new EnterpriseDocumentType(_PRO_FORMA_INVOICE);
    public java.lang.String getValue() { return _value_;}
    public static EnterpriseDocumentType fromValue(java.lang.String value)
          throws java.lang.RuntimeException {
        EnterpriseDocumentType enumX = (EnterpriseDocumentType)
            _table_.get(value);
        if (enumX==null) throw new java.lang.RuntimeException(getEnumErrorMsg(value));
        return enumX;
    }
    public static EnterpriseDocumentType fromString(java.lang.String value)
          throws java.lang.RuntimeException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static java.lang.String getEnumErrorMsg(Object value) 
	{ 
		return "'" + value + "' is not a valid EnterpriseDocumentType value."; 
	}
}
