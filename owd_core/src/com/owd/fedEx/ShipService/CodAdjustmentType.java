/*
 * CodAdjustmentType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;


/**
 * Specifies the type of adjustment was performed to the COD collection
 * amount during rating.
 */
public class CodAdjustmentType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected CodAdjustmentType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _CHARGES_ADDED = "CHARGES_ADDED";
    public static final java.lang.String _NONE = "NONE";
    public static final CodAdjustmentType CHARGES_ADDED = new CodAdjustmentType(_CHARGES_ADDED);
    public static final CodAdjustmentType NONE = new CodAdjustmentType(_NONE);
    public java.lang.String getValue() { return _value_;}
    public static CodAdjustmentType fromValue(java.lang.String value)
          throws java.lang.RuntimeException {
        CodAdjustmentType enumX = (CodAdjustmentType)
            _table_.get(value);
        if (enumX==null) throw new java.lang.RuntimeException(getEnumErrorMsg(value));
        return enumX;
    }
    public static CodAdjustmentType fromString(java.lang.String value)
          throws java.lang.RuntimeException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static java.lang.String getEnumErrorMsg(Object value) 
	{ 
		return "'" + value + "' is not a valid CodAdjustmentType value."; 
	}
}
