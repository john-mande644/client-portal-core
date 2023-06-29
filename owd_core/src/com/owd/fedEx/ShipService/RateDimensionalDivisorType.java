/*
 * RateDimensionalDivisorType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;


/**
 * Indicates the reason that a dim divisor value was chose.
 */
public class RateDimensionalDivisorType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected RateDimensionalDivisorType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _COUNTRY = "COUNTRY";
    public static final java.lang.String _CUSTOMER = "CUSTOMER";
    public static final java.lang.String _OTHER = "OTHER";
    public static final java.lang.String _PRODUCT = "PRODUCT";
    public static final java.lang.String _WAIVED = "WAIVED";
    public static final RateDimensionalDivisorType COUNTRY = new RateDimensionalDivisorType(_COUNTRY);
    public static final RateDimensionalDivisorType CUSTOMER = new RateDimensionalDivisorType(_CUSTOMER);
    public static final RateDimensionalDivisorType OTHER = new RateDimensionalDivisorType(_OTHER);
    public static final RateDimensionalDivisorType PRODUCT = new RateDimensionalDivisorType(_PRODUCT);
    public static final RateDimensionalDivisorType WAIVED = new RateDimensionalDivisorType(_WAIVED);
    public java.lang.String getValue() { return _value_;}
    public static RateDimensionalDivisorType fromValue(java.lang.String value)
          throws java.lang.RuntimeException {
        RateDimensionalDivisorType enumX = (RateDimensionalDivisorType)
            _table_.get(value);
        if (enumX==null) throw new java.lang.RuntimeException(getEnumErrorMsg(value));
        return enumX;
    }
    public static RateDimensionalDivisorType fromString(java.lang.String value)
          throws java.lang.RuntimeException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static java.lang.String getEnumErrorMsg(Object value) 
	{ 
		return "'" + value + "' is not a valid RateDimensionalDivisorType value."; 
	}
}
