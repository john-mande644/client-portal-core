/*
 * RateElementBasisType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;


/**
 * Selects the value from a set of rate data to which the percentage
 * is applied.
 */
public class RateElementBasisType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected RateElementBasisType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _BASE_CHARGE = "BASE_CHARGE";
    public static final java.lang.String _NET_CHARGE = "NET_CHARGE";
    public static final java.lang.String _NET_CHARGE_EXCLUDING_TAXES = "NET_CHARGE_EXCLUDING_TAXES";
    public static final java.lang.String _NET_FREIGHT = "NET_FREIGHT";
    public static final RateElementBasisType BASE_CHARGE = new RateElementBasisType(_BASE_CHARGE);
    public static final RateElementBasisType NET_CHARGE = new RateElementBasisType(_NET_CHARGE);
    public static final RateElementBasisType NET_CHARGE_EXCLUDING_TAXES = new RateElementBasisType(_NET_CHARGE_EXCLUDING_TAXES);
    public static final RateElementBasisType NET_FREIGHT = new RateElementBasisType(_NET_FREIGHT);
    public java.lang.String getValue() { return _value_;}
    public static RateElementBasisType fromValue(java.lang.String value)
          throws java.lang.RuntimeException {
        RateElementBasisType enumX = (RateElementBasisType)
            _table_.get(value);
        if (enumX==null) throw new java.lang.RuntimeException(getEnumErrorMsg(value));
        return enumX;
    }
    public static RateElementBasisType fromString(java.lang.String value)
          throws java.lang.RuntimeException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static java.lang.String getEnumErrorMsg(Object value) 
	{ 
		return "'" + value + "' is not a valid RateElementBasisType value."; 
	}
}
