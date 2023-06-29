/*
 * CustomDeliveryWindowType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;

public class CustomDeliveryWindowType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected CustomDeliveryWindowType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _AFTER = "AFTER";
    public static final java.lang.String _BEFORE = "BEFORE";
    public static final java.lang.String _BETWEEN = "BETWEEN";
    public static final java.lang.String _ON = "ON";
    public static final CustomDeliveryWindowType AFTER = new CustomDeliveryWindowType(_AFTER);
    public static final CustomDeliveryWindowType BEFORE = new CustomDeliveryWindowType(_BEFORE);
    public static final CustomDeliveryWindowType BETWEEN = new CustomDeliveryWindowType(_BETWEEN);
    public static final CustomDeliveryWindowType ON = new CustomDeliveryWindowType(_ON);
    public java.lang.String getValue() { return _value_;}
    public static CustomDeliveryWindowType fromValue(java.lang.String value)
          throws java.lang.RuntimeException {
        CustomDeliveryWindowType enumX = (CustomDeliveryWindowType)
            _table_.get(value);
        if (enumX==null) throw new java.lang.RuntimeException(getEnumErrorMsg(value));
        return enumX;
    }
    public static CustomDeliveryWindowType fromString(java.lang.String value)
          throws java.lang.RuntimeException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static java.lang.String getEnumErrorMsg(Object value) 
	{ 
		return "'" + value + "' is not a valid CustomDeliveryWindowType value."; 
	}
}
