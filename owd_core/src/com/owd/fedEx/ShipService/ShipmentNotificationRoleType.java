/*
 * ShipmentNotificationRoleType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;

public class ShipmentNotificationRoleType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected ShipmentNotificationRoleType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _BROKER = "BROKER";
    public static final java.lang.String _OTHER = "OTHER";
    public static final java.lang.String _RECIPIENT = "RECIPIENT";
    public static final java.lang.String _SHIPPER = "SHIPPER";
    public static final java.lang.String _THIRD_PARTY = "THIRD_PARTY";
    public static final ShipmentNotificationRoleType BROKER = new ShipmentNotificationRoleType(_BROKER);
    public static final ShipmentNotificationRoleType OTHER = new ShipmentNotificationRoleType(_OTHER);
    public static final ShipmentNotificationRoleType RECIPIENT = new ShipmentNotificationRoleType(_RECIPIENT);
    public static final ShipmentNotificationRoleType SHIPPER = new ShipmentNotificationRoleType(_SHIPPER);
    public static final ShipmentNotificationRoleType THIRD_PARTY = new ShipmentNotificationRoleType(_THIRD_PARTY);
    public java.lang.String getValue() { return _value_;}
    public static ShipmentNotificationRoleType fromValue(java.lang.String value)
          throws java.lang.RuntimeException {
        ShipmentNotificationRoleType enumX = (ShipmentNotificationRoleType)
            _table_.get(value);
        if (enumX==null) throw new java.lang.RuntimeException(getEnumErrorMsg(value));
        return enumX;
    }
    public static ShipmentNotificationRoleType fromString(java.lang.String value)
          throws java.lang.RuntimeException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static java.lang.String getEnumErrorMsg(Object value) 
	{ 
		return "'" + value + "' is not a valid ShipmentNotificationRoleType value."; 
	}
}
