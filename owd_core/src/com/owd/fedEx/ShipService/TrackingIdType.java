/*
 * TrackingIdType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;

public class TrackingIdType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected TrackingIdType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _EXPRESS = "EXPRESS";
    public static final java.lang.String _FEDEX = "FEDEX";
    public static final java.lang.String _FREIGHT = "FREIGHT";
    public static final java.lang.String _GROUND = "GROUND";
    public static final java.lang.String _USPS = "USPS";
    public static final TrackingIdType EXPRESS = new TrackingIdType(_EXPRESS);
    public static final TrackingIdType FEDEX = new TrackingIdType(_FEDEX);
    public static final TrackingIdType FREIGHT = new TrackingIdType(_FREIGHT);
    public static final TrackingIdType GROUND = new TrackingIdType(_GROUND);
    public static final TrackingIdType USPS = new TrackingIdType(_USPS);
    public java.lang.String getValue() { return _value_;}
    public static TrackingIdType fromValue(java.lang.String value)
          throws java.lang.RuntimeException {
        TrackingIdType enumX = (TrackingIdType)
            _table_.get(value);
        if (enumX==null) throw new java.lang.RuntimeException(getEnumErrorMsg(value));
        return enumX;
    }
    public static TrackingIdType fromString(java.lang.String value)
          throws java.lang.RuntimeException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static java.lang.String getEnumErrorMsg(Object value) 
	{ 
		return "'" + value + "' is not a valid TrackingIdType value."; 
	}
}
