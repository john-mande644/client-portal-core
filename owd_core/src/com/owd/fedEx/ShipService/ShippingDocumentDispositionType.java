/*
 * ShippingDocumentDispositionType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;


/**
 * Specifies how to return a shipping document to the caller.
 */
public class ShippingDocumentDispositionType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected ShippingDocumentDispositionType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _DEFERRED_STORED = "DEFERRED_STORED";
    public static final java.lang.String _EMAILED = "EMAILED";
    public static final java.lang.String _QUEUED = "QUEUED";
    public static final java.lang.String _RETURNED = "RETURNED";
    public static final java.lang.String _STORED = "STORED";
    public static final ShippingDocumentDispositionType DEFERRED_STORED = new ShippingDocumentDispositionType(_DEFERRED_STORED);
    public static final ShippingDocumentDispositionType EMAILED = new ShippingDocumentDispositionType(_EMAILED);
    public static final ShippingDocumentDispositionType QUEUED = new ShippingDocumentDispositionType(_QUEUED);
    public static final ShippingDocumentDispositionType RETURNED = new ShippingDocumentDispositionType(_RETURNED);
    public static final ShippingDocumentDispositionType STORED = new ShippingDocumentDispositionType(_STORED);
    public java.lang.String getValue() { return _value_;}
    public static ShippingDocumentDispositionType fromValue(java.lang.String value)
          throws java.lang.RuntimeException {
        ShippingDocumentDispositionType enumX = (ShippingDocumentDispositionType)
            _table_.get(value);
        if (enumX==null) throw new java.lang.RuntimeException(getEnumErrorMsg(value));
        return enumX;
    }
    public static ShippingDocumentDispositionType fromString(java.lang.String value)
          throws java.lang.RuntimeException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static java.lang.String getEnumErrorMsg(Object value) 
	{ 
		return "'" + value + "' is not a valid ShippingDocumentDispositionType value."; 
	}
}
