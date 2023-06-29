/*
 * FreightShipmentRoleType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;


/**
 * Indicates the role of the party submitting the transaction.
 */
public class FreightShipmentRoleType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected FreightShipmentRoleType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _CONSIGNEE = "CONSIGNEE";
    public static final java.lang.String _SHIPPER = "SHIPPER";
    public static final FreightShipmentRoleType CONSIGNEE = new FreightShipmentRoleType(_CONSIGNEE);
    public static final FreightShipmentRoleType SHIPPER = new FreightShipmentRoleType(_SHIPPER);
    public java.lang.String getValue() { return _value_;}
    public static FreightShipmentRoleType fromValue(java.lang.String value)
          throws java.lang.RuntimeException {
        FreightShipmentRoleType enumX = (FreightShipmentRoleType)
            _table_.get(value);
        if (enumX==null) throw new java.lang.RuntimeException(getEnumErrorMsg(value));
        return enumX;
    }
    public static FreightShipmentRoleType fromString(java.lang.String value)
          throws java.lang.RuntimeException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static java.lang.String getEnumErrorMsg(Object value) 
	{ 
		return "'" + value + "' is not a valid FreightShipmentRoleType value."; 
	}
}
