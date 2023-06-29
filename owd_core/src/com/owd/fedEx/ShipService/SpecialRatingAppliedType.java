/*
 * SpecialRatingAppliedType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;

public class SpecialRatingAppliedType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected SpecialRatingAppliedType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _FEDEX_ONE_RATE = "FEDEX_ONE_RATE";
    public static final java.lang.String _FIXED_FUEL_SURCHARGE = "FIXED_FUEL_SURCHARGE";
    public static final java.lang.String _IMPORT_PRICING = "IMPORT_PRICING";
    public static final SpecialRatingAppliedType FEDEX_ONE_RATE = new SpecialRatingAppliedType(_FEDEX_ONE_RATE);
    public static final SpecialRatingAppliedType FIXED_FUEL_SURCHARGE = new SpecialRatingAppliedType(_FIXED_FUEL_SURCHARGE);
    public static final SpecialRatingAppliedType IMPORT_PRICING = new SpecialRatingAppliedType(_IMPORT_PRICING);
    public java.lang.String getValue() { return _value_;}
    public static SpecialRatingAppliedType fromValue(java.lang.String value)
          throws java.lang.RuntimeException {
        SpecialRatingAppliedType enumX = (SpecialRatingAppliedType)
            _table_.get(value);
        if (enumX==null) throw new java.lang.RuntimeException(getEnumErrorMsg(value));
        return enumX;
    }
    public static SpecialRatingAppliedType fromString(java.lang.String value)
          throws java.lang.RuntimeException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static java.lang.String getEnumErrorMsg(Object value) 
	{ 
		return "'" + value + "' is not a valid SpecialRatingAppliedType value."; 
	}
}
