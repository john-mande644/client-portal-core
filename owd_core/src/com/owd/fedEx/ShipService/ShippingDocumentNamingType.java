/*
 * ShippingDocumentNamingType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;


/**
 * Identifies the convention by which file names are constructed for
 * STORED or DEFERRED documents.
 */
public class ShippingDocumentNamingType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected ShippingDocumentNamingType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _FAST = "FAST";
    public static final java.lang.String _LEGACY_FXRS = "LEGACY_FXRS";
    public static final ShippingDocumentNamingType FAST = new ShippingDocumentNamingType(_FAST);
    public static final ShippingDocumentNamingType LEGACY_FXRS = new ShippingDocumentNamingType(_LEGACY_FXRS);
    public java.lang.String getValue() { return _value_;}
    public static ShippingDocumentNamingType fromValue(java.lang.String value)
          throws java.lang.RuntimeException {
        ShippingDocumentNamingType enumX = (ShippingDocumentNamingType)
            _table_.get(value);
        if (enumX==null) throw new java.lang.RuntimeException(getEnumErrorMsg(value));
        return enumX;
    }
    public static ShippingDocumentNamingType fromString(java.lang.String value)
          throws java.lang.RuntimeException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static java.lang.String getEnumErrorMsg(Object value) 
	{ 
		return "'" + value + "' is not a valid ShippingDocumentNamingType value."; 
	}
}
