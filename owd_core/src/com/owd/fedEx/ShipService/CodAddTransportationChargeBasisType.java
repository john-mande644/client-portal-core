/*
 * CodAddTransportationChargeBasisType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;

public class CodAddTransportationChargeBasisType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected CodAddTransportationChargeBasisType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _COD_SURCHARGE = "COD_SURCHARGE";
    public static final java.lang.String _NET_CHARGE = "NET_CHARGE";
    public static final java.lang.String _NET_FREIGHT = "NET_FREIGHT";
    public static final java.lang.String _TOTAL_CUSTOMER_CHARGE = "TOTAL_CUSTOMER_CHARGE";
    public static final CodAddTransportationChargeBasisType COD_SURCHARGE = new CodAddTransportationChargeBasisType(_COD_SURCHARGE);
    public static final CodAddTransportationChargeBasisType NET_CHARGE = new CodAddTransportationChargeBasisType(_NET_CHARGE);
    public static final CodAddTransportationChargeBasisType NET_FREIGHT = new CodAddTransportationChargeBasisType(_NET_FREIGHT);
    public static final CodAddTransportationChargeBasisType TOTAL_CUSTOMER_CHARGE = new CodAddTransportationChargeBasisType(_TOTAL_CUSTOMER_CHARGE);
    public java.lang.String getValue() { return _value_;}
    public static CodAddTransportationChargeBasisType fromValue(java.lang.String value)
          throws java.lang.RuntimeException {
        CodAddTransportationChargeBasisType enumX = (CodAddTransportationChargeBasisType)
            _table_.get(value);
        if (enumX==null) throw new java.lang.RuntimeException(getEnumErrorMsg(value));
        return enumX;
    }
    public static CodAddTransportationChargeBasisType fromString(java.lang.String value)
          throws java.lang.RuntimeException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static java.lang.String getEnumErrorMsg(Object value) 
	{ 
		return "'" + value + "' is not a valid CodAddTransportationChargeBasisType value."; 
	}
}
