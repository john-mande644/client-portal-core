/*
 * NotificationEventType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;

public class NotificationEventType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected NotificationEventType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _ON_DELIVERY = "ON_DELIVERY";
    public static final java.lang.String _ON_ESTIMATED_DELIVERY = "ON_ESTIMATED_DELIVERY";
    public static final java.lang.String _ON_EXCEPTION = "ON_EXCEPTION";
    public static final java.lang.String _ON_SHIPMENT = "ON_SHIPMENT";
    public static final java.lang.String _ON_TENDER = "ON_TENDER";
    public static final NotificationEventType ON_DELIVERY = new NotificationEventType(_ON_DELIVERY);
    public static final NotificationEventType ON_ESTIMATED_DELIVERY = new NotificationEventType(_ON_ESTIMATED_DELIVERY);
    public static final NotificationEventType ON_EXCEPTION = new NotificationEventType(_ON_EXCEPTION);
    public static final NotificationEventType ON_SHIPMENT = new NotificationEventType(_ON_SHIPMENT);
    public static final NotificationEventType ON_TENDER = new NotificationEventType(_ON_TENDER);
    public java.lang.String getValue() { return _value_;}
    public static NotificationEventType fromValue(java.lang.String value)
          throws java.lang.RuntimeException {
        NotificationEventType enumX = (NotificationEventType)
            _table_.get(value);
        if (enumX==null) throw new java.lang.RuntimeException(getEnumErrorMsg(value));
        return enumX;
    }
    public static NotificationEventType fromString(java.lang.String value)
          throws java.lang.RuntimeException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static java.lang.String getEnumErrorMsg(Object value) 
	{ 
		return "'" + value + "' is not a valid NotificationEventType value."; 
	}
}
