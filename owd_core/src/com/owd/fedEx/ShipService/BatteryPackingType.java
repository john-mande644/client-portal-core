/*
 * BatteryPackingType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;


/**
 * Describes the packing arrangement of a battery or cell with respect
 * to other items within the same package.
 */
public class BatteryPackingType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected BatteryPackingType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _CONTAINED_IN_EQUIPMENT = "CONTAINED_IN_EQUIPMENT";
    public static final java.lang.String _PACKED_WITH_EQUIPMENT = "PACKED_WITH_EQUIPMENT";
    public static final BatteryPackingType CONTAINED_IN_EQUIPMENT = new BatteryPackingType(_CONTAINED_IN_EQUIPMENT);
    public static final BatteryPackingType PACKED_WITH_EQUIPMENT = new BatteryPackingType(_PACKED_WITH_EQUIPMENT);
    public java.lang.String getValue() { return _value_;}
    public static BatteryPackingType fromValue(java.lang.String value)
          throws java.lang.RuntimeException {
        BatteryPackingType enumX = (BatteryPackingType)
            _table_.get(value);
        if (enumX==null) throw new java.lang.RuntimeException(getEnumErrorMsg(value));
        return enumX;
    }
    public static BatteryPackingType fromString(java.lang.String value)
          throws java.lang.RuntimeException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static java.lang.String getEnumErrorMsg(Object value) 
	{ 
		return "'" + value + "' is not a valid BatteryPackingType value."; 
	}
}
