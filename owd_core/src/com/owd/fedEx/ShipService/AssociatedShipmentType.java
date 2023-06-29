/*
 * AssociatedShipmentType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;

public class AssociatedShipmentType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected AssociatedShipmentType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _COD_AND_DELIVERY_ON_INVOICE_ACCEPTANCE_RETURN = "COD_AND_DELIVERY_ON_INVOICE_ACCEPTANCE_RETURN";
    public static final java.lang.String _COD_RETURN = "COD_RETURN";
    public static final java.lang.String _DELIVERY_ON_INVOICE_ACCEPTANCE_RETURN = "DELIVERY_ON_INVOICE_ACCEPTANCE_RETURN";
    public static final AssociatedShipmentType COD_AND_DELIVERY_ON_INVOICE_ACCEPTANCE_RETURN = new AssociatedShipmentType(_COD_AND_DELIVERY_ON_INVOICE_ACCEPTANCE_RETURN);
    public static final AssociatedShipmentType COD_RETURN = new AssociatedShipmentType(_COD_RETURN);
    public static final AssociatedShipmentType DELIVERY_ON_INVOICE_ACCEPTANCE_RETURN = new AssociatedShipmentType(_DELIVERY_ON_INVOICE_ACCEPTANCE_RETURN);
    public java.lang.String getValue() { return _value_;}
    public static AssociatedShipmentType fromValue(java.lang.String value)
          throws java.lang.RuntimeException {
        AssociatedShipmentType enumX = (AssociatedShipmentType)
            _table_.get(value);
        if (enumX==null) throw new java.lang.RuntimeException(getEnumErrorMsg(value));
        return enumX;
    }
    public static AssociatedShipmentType fromString(java.lang.String value)
          throws java.lang.RuntimeException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static java.lang.String getEnumErrorMsg(Object value) 
	{ 
		return "'" + value + "' is not a valid AssociatedShipmentType value."; 
	}
}
