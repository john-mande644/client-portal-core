/*
 * DropoffType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;

public class DropoffType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected DropoffType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _BUSINESS_SERVICE_CENTER = "BUSINESS_SERVICE_CENTER";
    public static final java.lang.String _DROP_BOX = "DROP_BOX";
    public static final java.lang.String _REGULAR_PICKUP = "REGULAR_PICKUP";
    public static final java.lang.String _REQUEST_COURIER = "REQUEST_COURIER";
    public static final java.lang.String _STATION = "STATION";
    public static final DropoffType BUSINESS_SERVICE_CENTER = new DropoffType(_BUSINESS_SERVICE_CENTER);
    public static final DropoffType DROP_BOX = new DropoffType(_DROP_BOX);
    public static final DropoffType REGULAR_PICKUP = new DropoffType(_REGULAR_PICKUP);
    public static final DropoffType REQUEST_COURIER = new DropoffType(_REQUEST_COURIER);
    public static final DropoffType STATION = new DropoffType(_STATION);
    public java.lang.String getValue() { return _value_;}
    public static DropoffType fromValue(java.lang.String value)
          throws java.lang.RuntimeException {
        DropoffType enumX = (DropoffType)
            _table_.get(value);
        if (enumX==null) throw new java.lang.RuntimeException(getEnumErrorMsg(value));
        return enumX;
    }
    public static DropoffType fromString(java.lang.String value)
          throws java.lang.RuntimeException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static java.lang.String getEnumErrorMsg(Object value) 
	{ 
		return "'" + value + "' is not a valid DropoffType value."; 
	}
}
