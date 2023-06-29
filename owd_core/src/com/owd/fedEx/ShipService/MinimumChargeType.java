/*
 * MinimumChargeType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;

public class MinimumChargeType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected MinimumChargeType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _CUSTOMER = "CUSTOMER";
    public static final java.lang.String _CUSTOMER_FREIGHT_WEIGHT = "CUSTOMER_FREIGHT_WEIGHT";
    public static final java.lang.String _EARNED_DISCOUNT = "EARNED_DISCOUNT";
    public static final java.lang.String _MIXED = "MIXED";
    public static final java.lang.String _RATE_SCALE = "RATE_SCALE";
    public static final MinimumChargeType CUSTOMER = new MinimumChargeType(_CUSTOMER);
    public static final MinimumChargeType CUSTOMER_FREIGHT_WEIGHT = new MinimumChargeType(_CUSTOMER_FREIGHT_WEIGHT);
    public static final MinimumChargeType EARNED_DISCOUNT = new MinimumChargeType(_EARNED_DISCOUNT);
    public static final MinimumChargeType MIXED = new MinimumChargeType(_MIXED);
    public static final MinimumChargeType RATE_SCALE = new MinimumChargeType(_RATE_SCALE);
    public java.lang.String getValue() { return _value_;}
    public static MinimumChargeType fromValue(java.lang.String value)
          throws java.lang.RuntimeException {
        MinimumChargeType enumX = (MinimumChargeType)
            _table_.get(value);
        if (enumX==null) throw new java.lang.RuntimeException(getEnumErrorMsg(value));
        return enumX;
    }
    public static MinimumChargeType fromString(java.lang.String value)
          throws java.lang.RuntimeException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static java.lang.String getEnumErrorMsg(Object value) 
	{ 
		return "'" + value + "' is not a valid MinimumChargeType value."; 
	}
}
