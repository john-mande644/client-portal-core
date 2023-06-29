/*
 * HazardousCommodityQuantityType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;


/**
 * Specifies the measure of quantity to be validated against a prescribed
 * limit.
 */
public class HazardousCommodityQuantityType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected HazardousCommodityQuantityType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _GROSS = "GROSS";
    public static final java.lang.String _NET = "NET";
    public static final HazardousCommodityQuantityType GROSS = new HazardousCommodityQuantityType(_GROSS);
    public static final HazardousCommodityQuantityType NET = new HazardousCommodityQuantityType(_NET);
    public java.lang.String getValue() { return _value_;}
    public static HazardousCommodityQuantityType fromValue(java.lang.String value)
          throws java.lang.RuntimeException {
        HazardousCommodityQuantityType enumX = (HazardousCommodityQuantityType)
            _table_.get(value);
        if (enumX==null) throw new java.lang.RuntimeException(getEnumErrorMsg(value));
        return enumX;
    }
    public static HazardousCommodityQuantityType fromString(java.lang.String value)
          throws java.lang.RuntimeException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static java.lang.String getEnumErrorMsg(Object value) 
	{ 
		return "'" + value + "' is not a valid HazardousCommodityQuantityType value."; 
	}
}
