/*
 * SmartPostAncillaryEndorsementType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;


/**
 * These values are mutually exclusive; at most one of them can be
 * attached to a SmartPost shipment.
 */
public class SmartPostAncillaryEndorsementType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected SmartPostAncillaryEndorsementType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _ADDRESS_CORRECTION = "ADDRESS_CORRECTION";
    public static final java.lang.String _CARRIER_LEAVE_IF_NO_RESPONSE = "CARRIER_LEAVE_IF_NO_RESPONSE";
    public static final java.lang.String _CHANGE_SERVICE = "CHANGE_SERVICE";
    public static final java.lang.String _FORWARDING_SERVICE = "FORWARDING_SERVICE";
    public static final java.lang.String _RETURN_SERVICE = "RETURN_SERVICE";
    public static final SmartPostAncillaryEndorsementType ADDRESS_CORRECTION = new SmartPostAncillaryEndorsementType(_ADDRESS_CORRECTION);
    public static final SmartPostAncillaryEndorsementType CARRIER_LEAVE_IF_NO_RESPONSE = new SmartPostAncillaryEndorsementType(_CARRIER_LEAVE_IF_NO_RESPONSE);
    public static final SmartPostAncillaryEndorsementType CHANGE_SERVICE = new SmartPostAncillaryEndorsementType(_CHANGE_SERVICE);
    public static final SmartPostAncillaryEndorsementType FORWARDING_SERVICE = new SmartPostAncillaryEndorsementType(_FORWARDING_SERVICE);
    public static final SmartPostAncillaryEndorsementType RETURN_SERVICE = new SmartPostAncillaryEndorsementType(_RETURN_SERVICE);
    public java.lang.String getValue() { return _value_;}
    public static SmartPostAncillaryEndorsementType fromValue(java.lang.String value)
          throws java.lang.RuntimeException {
        SmartPostAncillaryEndorsementType enumX = (SmartPostAncillaryEndorsementType)
            _table_.get(value);
        if (enumX==null) throw new java.lang.RuntimeException(getEnumErrorMsg(value));
        return enumX;
    }
    public static SmartPostAncillaryEndorsementType fromString(java.lang.String value)
          throws java.lang.RuntimeException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static java.lang.String getEnumErrorMsg(Object value) 
	{ 
		return "'" + value + "' is not a valid SmartPostAncillaryEndorsementType value."; 
	}
}
