/*
 * ReturnType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;

public class ReturnType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected ReturnType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _FEDEX_TAG = "FEDEX_TAG";
    public static final java.lang.String _PENDING = "PENDING";
    public static final java.lang.String _PRINT_RETURN_LABEL = "PRINT_RETURN_LABEL";
    public static final ReturnType FEDEX_TAG = new ReturnType(_FEDEX_TAG);
    public static final ReturnType PENDING = new ReturnType(_PENDING);
    public static final ReturnType PRINT_RETURN_LABEL = new ReturnType(_PRINT_RETURN_LABEL);
    public java.lang.String getValue() { return _value_;}
    public static ReturnType fromValue(java.lang.String value)
          throws java.lang.RuntimeException {
        ReturnType enumX = (ReturnType)
            _table_.get(value);
        if (enumX==null) throw new java.lang.RuntimeException(getEnumErrorMsg(value));
        return enumX;
    }
    public static ReturnType fromString(java.lang.String value)
          throws java.lang.RuntimeException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static java.lang.String getEnumErrorMsg(Object value) 
	{ 
		return "'" + value + "' is not a valid ReturnType value."; 
	}
}
