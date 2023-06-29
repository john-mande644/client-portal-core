/*
 * AccessorRoleType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;


/**
 * Specifies the role that identifies the permissions the accessor
 * of the pending shipment.
 */
public class AccessorRoleType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected AccessorRoleType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _SHIPMENT_COMPLETOR = "SHIPMENT_COMPLETOR";
    public static final java.lang.String _SHIPMENT_INITIATOR = "SHIPMENT_INITIATOR";
    public static final AccessorRoleType SHIPMENT_COMPLETOR = new AccessorRoleType(_SHIPMENT_COMPLETOR);
    public static final AccessorRoleType SHIPMENT_INITIATOR = new AccessorRoleType(_SHIPMENT_INITIATOR);
    public java.lang.String getValue() { return _value_;}
    public static AccessorRoleType fromValue(java.lang.String value)
          throws java.lang.RuntimeException {
        AccessorRoleType enumX = (AccessorRoleType)
            _table_.get(value);
        if (enumX==null) throw new java.lang.RuntimeException(getEnumErrorMsg(value));
        return enumX;
    }
    public static AccessorRoleType fromString(java.lang.String value)
          throws java.lang.RuntimeException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static java.lang.String getEnumErrorMsg(Object value) 
	{ 
		return "'" + value + "' is not a valid AccessorRoleType value."; 
	}
}
