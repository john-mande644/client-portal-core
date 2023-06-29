/*
 * FreightOnValueType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;


/**
 * Identifies responsibilities with respect to loss, damage, etc.
 */
public class FreightOnValueType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected FreightOnValueType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _CARRIER_RISK = "CARRIER_RISK";
    public static final java.lang.String _OWN_RISK = "OWN_RISK";
    public static final FreightOnValueType CARRIER_RISK = new FreightOnValueType(_CARRIER_RISK);
    public static final FreightOnValueType OWN_RISK = new FreightOnValueType(_OWN_RISK);
    public java.lang.String getValue() { return _value_;}
    public static FreightOnValueType fromValue(java.lang.String value)
          throws java.lang.RuntimeException {
        FreightOnValueType enumX = (FreightOnValueType)
            _table_.get(value);
        if (enumX==null) throw new java.lang.RuntimeException(getEnumErrorMsg(value));
        return enumX;
    }
    public static FreightOnValueType fromString(java.lang.String value)
          throws java.lang.RuntimeException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static java.lang.String getEnumErrorMsg(Object value) 
	{ 
		return "'" + value + "' is not a valid FreightOnValueType value."; 
	}
}
