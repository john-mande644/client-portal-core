/*
 * EdtRequestType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;


/**
 * Specifies the types of Estimated Duties and Taxes to be included
 * in a rate quotation for an international shipment.
 */
public class EdtRequestType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected EdtRequestType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _ALL = "ALL";
    public static final java.lang.String _NONE = "NONE";
    public static final EdtRequestType ALL = new EdtRequestType(_ALL);
    public static final EdtRequestType NONE = new EdtRequestType(_NONE);
    public java.lang.String getValue() { return _value_;}
    public static EdtRequestType fromValue(java.lang.String value)
          throws java.lang.RuntimeException {
        EdtRequestType enumX = (EdtRequestType)
            _table_.get(value);
        if (enumX==null) throw new java.lang.RuntimeException(getEnumErrorMsg(value));
        return enumX;
    }
    public static EdtRequestType fromString(java.lang.String value)
          throws java.lang.RuntimeException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static java.lang.String getEnumErrorMsg(Object value) 
	{ 
		return "'" + value + "' is not a valid EdtRequestType value."; 
	}
}
