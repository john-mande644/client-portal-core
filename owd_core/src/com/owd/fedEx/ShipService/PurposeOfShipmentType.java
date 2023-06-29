/*
 * PurposeOfShipmentType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;

public class PurposeOfShipmentType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected PurposeOfShipmentType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _GIFT = "GIFT";
    public static final java.lang.String _NOT_SOLD = "NOT_SOLD";
    public static final java.lang.String _PERSONAL_EFFECTS = "PERSONAL_EFFECTS";
    public static final java.lang.String _REPAIR_AND_RETURN = "REPAIR_AND_RETURN";
    public static final java.lang.String _SAMPLE = "SAMPLE";
    public static final java.lang.String _SOLD = "SOLD";
    public static final PurposeOfShipmentType GIFT = new PurposeOfShipmentType(_GIFT);
    public static final PurposeOfShipmentType NOT_SOLD = new PurposeOfShipmentType(_NOT_SOLD);
    public static final PurposeOfShipmentType PERSONAL_EFFECTS = new PurposeOfShipmentType(_PERSONAL_EFFECTS);
    public static final PurposeOfShipmentType REPAIR_AND_RETURN = new PurposeOfShipmentType(_REPAIR_AND_RETURN);
    public static final PurposeOfShipmentType SAMPLE = new PurposeOfShipmentType(_SAMPLE);
    public static final PurposeOfShipmentType SOLD = new PurposeOfShipmentType(_SOLD);
    public java.lang.String getValue() { return _value_;}
    public static PurposeOfShipmentType fromValue(java.lang.String value)
          throws java.lang.RuntimeException {
        PurposeOfShipmentType enumX = (PurposeOfShipmentType)
            _table_.get(value);
        if (enumX==null) throw new java.lang.RuntimeException(getEnumErrorMsg(value));
        return enumX;
    }
    public static PurposeOfShipmentType fromString(java.lang.String value)
          throws java.lang.RuntimeException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static java.lang.String getEnumErrorMsg(Object value) 
	{ 
		return "'" + value + "' is not a valid PurposeOfShipmentType value."; 
	}
}
