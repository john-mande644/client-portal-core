/*
 * RateDiscountType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;

public class RateDiscountType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected RateDiscountType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _BONUS = "BONUS";
    public static final java.lang.String _COUPON = "COUPON";
    public static final java.lang.String _EARNED = "EARNED";
    public static final java.lang.String _INCENTIVE = "INCENTIVE";
    public static final java.lang.String _OTHER = "OTHER";
    public static final java.lang.String _VOLUME = "VOLUME";
    public static final RateDiscountType BONUS = new RateDiscountType(_BONUS);
    public static final RateDiscountType COUPON = new RateDiscountType(_COUPON);
    public static final RateDiscountType EARNED = new RateDiscountType(_EARNED);
    public static final RateDiscountType INCENTIVE = new RateDiscountType(_INCENTIVE);
    public static final RateDiscountType OTHER = new RateDiscountType(_OTHER);
    public static final RateDiscountType VOLUME = new RateDiscountType(_VOLUME);
    public java.lang.String getValue() { return _value_;}
    public static RateDiscountType fromValue(java.lang.String value)
          throws java.lang.RuntimeException {
        RateDiscountType enumX = (RateDiscountType)
            _table_.get(value);
        if (enumX==null) throw new java.lang.RuntimeException(getEnumErrorMsg(value));
        return enumX;
    }
    public static RateDiscountType fromString(java.lang.String value)
          throws java.lang.RuntimeException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static java.lang.String getEnumErrorMsg(Object value) 
	{ 
		return "'" + value + "' is not a valid RateDiscountType value."; 
	}
}
