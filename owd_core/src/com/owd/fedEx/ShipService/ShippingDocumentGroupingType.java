/*
 * ShippingDocumentGroupingType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;


/**
 * Specifies how to organize all shipping documents of the same type.
 */
public class ShippingDocumentGroupingType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected ShippingDocumentGroupingType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _CONSOLIDATED_BY_DOCUMENT_TYPE = "CONSOLIDATED_BY_DOCUMENT_TYPE";
    public static final java.lang.String _INDIVIDUAL = "INDIVIDUAL";
    public static final ShippingDocumentGroupingType CONSOLIDATED_BY_DOCUMENT_TYPE = new ShippingDocumentGroupingType(_CONSOLIDATED_BY_DOCUMENT_TYPE);
    public static final ShippingDocumentGroupingType INDIVIDUAL = new ShippingDocumentGroupingType(_INDIVIDUAL);
    public java.lang.String getValue() { return _value_;}
    public static ShippingDocumentGroupingType fromValue(java.lang.String value)
          throws java.lang.RuntimeException {
        ShippingDocumentGroupingType enumX = (ShippingDocumentGroupingType)
            _table_.get(value);
        if (enumX==null) throw new java.lang.RuntimeException(getEnumErrorMsg(value));
        return enumX;
    }
    public static ShippingDocumentGroupingType fromString(java.lang.String value)
          throws java.lang.RuntimeException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static java.lang.String getEnumErrorMsg(Object value) 
	{ 
		return "'" + value + "' is not a valid ShippingDocumentGroupingType value."; 
	}
}
