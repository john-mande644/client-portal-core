/*
 * PackageSpecialServiceType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;

public class PackageSpecialServiceType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected PackageSpecialServiceType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _ALCOHOL = "ALCOHOL";
    public static final java.lang.String _APPOINTMENT_DELIVERY = "APPOINTMENT_DELIVERY";
    public static final java.lang.String _BATTERY = "BATTERY";
    public static final java.lang.String _COD = "COD";
    public static final java.lang.String _DANGEROUS_GOODS = "DANGEROUS_GOODS";
    public static final java.lang.String _DRY_ICE = "DRY_ICE";
    public static final java.lang.String _NON_STANDARD_CONTAINER = "NON_STANDARD_CONTAINER";
    public static final java.lang.String _PRIORITY_ALERT = "PRIORITY_ALERT";
    public static final java.lang.String _SIGNATURE_OPTION = "SIGNATURE_OPTION";
    public static final PackageSpecialServiceType ALCOHOL = new PackageSpecialServiceType(_ALCOHOL);
    public static final PackageSpecialServiceType APPOINTMENT_DELIVERY = new PackageSpecialServiceType(_APPOINTMENT_DELIVERY);
    public static final PackageSpecialServiceType BATTERY = new PackageSpecialServiceType(_BATTERY);
    public static final PackageSpecialServiceType COD = new PackageSpecialServiceType(_COD);
    public static final PackageSpecialServiceType DANGEROUS_GOODS = new PackageSpecialServiceType(_DANGEROUS_GOODS);
    public static final PackageSpecialServiceType DRY_ICE = new PackageSpecialServiceType(_DRY_ICE);
    public static final PackageSpecialServiceType NON_STANDARD_CONTAINER = new PackageSpecialServiceType(_NON_STANDARD_CONTAINER);
    public static final PackageSpecialServiceType PRIORITY_ALERT = new PackageSpecialServiceType(_PRIORITY_ALERT);
    public static final PackageSpecialServiceType SIGNATURE_OPTION = new PackageSpecialServiceType(_SIGNATURE_OPTION);
    public java.lang.String getValue() { return _value_;}
    public static PackageSpecialServiceType fromValue(java.lang.String value)
          throws java.lang.RuntimeException {
        PackageSpecialServiceType enumX = (PackageSpecialServiceType)
            _table_.get(value);
        if (enumX==null) throw new java.lang.RuntimeException(getEnumErrorMsg(value));
        return enumX;
    }
    public static PackageSpecialServiceType fromString(java.lang.String value)
          throws java.lang.RuntimeException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static java.lang.String getEnumErrorMsg(Object value) 
	{ 
		return "'" + value + "' is not a valid PackageSpecialServiceType value."; 
	}
}
