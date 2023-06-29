/*
 * SmartPostShipmentProcessingOptionType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;

public class SmartPostShipmentProcessingOptionType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected SmartPostShipmentProcessingOptionType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _GROUND_TRACKING_NUMBER_REQUESTED = "GROUND_TRACKING_NUMBER_REQUESTED";
    public static final SmartPostShipmentProcessingOptionType GROUND_TRACKING_NUMBER_REQUESTED = new SmartPostShipmentProcessingOptionType(_GROUND_TRACKING_NUMBER_REQUESTED);
    public java.lang.String getValue() { return _value_;}
    public static SmartPostShipmentProcessingOptionType fromValue(java.lang.String value)
          throws java.lang.RuntimeException {
        SmartPostShipmentProcessingOptionType enumX = (SmartPostShipmentProcessingOptionType)
            _table_.get(value);
        if (enumX==null) throw new java.lang.RuntimeException(getEnumErrorMsg(value));
        return enumX;
    }
    public static SmartPostShipmentProcessingOptionType fromString(java.lang.String value)
          throws java.lang.RuntimeException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static java.lang.String getEnumErrorMsg(Object value) 
	{ 
		return "'" + value + "' is not a valid SmartPostShipmentProcessingOptionType value."; 
	}
}
