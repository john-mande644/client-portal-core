/*
 * CustomerReferenceType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;

public class CustomerReferenceType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected CustomerReferenceType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _CUSTOMER_REFERENCE = "CUSTOMER_REFERENCE";
    public static final java.lang.String _DEPARTMENT_NUMBER = "DEPARTMENT_NUMBER";
    public static final java.lang.String _INTRACOUNTRY_REGULATORY_REFERENCE = "INTRACOUNTRY_REGULATORY_REFERENCE";
    public static final java.lang.String _INVOICE_NUMBER = "INVOICE_NUMBER";
    public static final java.lang.String _P_O_NUMBER = "P_O_NUMBER";
    public static final java.lang.String _RMA_ASSOCIATION = "RMA_ASSOCIATION";
    public static final java.lang.String _SHIPMENT_INTEGRITY = "SHIPMENT_INTEGRITY";
    public static final CustomerReferenceType CUSTOMER_REFERENCE = new CustomerReferenceType(_CUSTOMER_REFERENCE);
    public static final CustomerReferenceType DEPARTMENT_NUMBER = new CustomerReferenceType(_DEPARTMENT_NUMBER);
    public static final CustomerReferenceType INTRACOUNTRY_REGULATORY_REFERENCE = new CustomerReferenceType(_INTRACOUNTRY_REGULATORY_REFERENCE);
    public static final CustomerReferenceType INVOICE_NUMBER = new CustomerReferenceType(_INVOICE_NUMBER);
    public static final CustomerReferenceType P_O_NUMBER = new CustomerReferenceType(_P_O_NUMBER);
    public static final CustomerReferenceType RMA_ASSOCIATION = new CustomerReferenceType(_RMA_ASSOCIATION);
    public static final CustomerReferenceType SHIPMENT_INTEGRITY = new CustomerReferenceType(_SHIPMENT_INTEGRITY);
    public java.lang.String getValue() { return _value_;}
    public static CustomerReferenceType fromValue(java.lang.String value)
          throws java.lang.RuntimeException {
        CustomerReferenceType enumX = (CustomerReferenceType)
            _table_.get(value);
        if (enumX==null) throw new java.lang.RuntimeException(getEnumErrorMsg(value));
        return enumX;
    }
    public static CustomerReferenceType fromString(java.lang.String value)
          throws java.lang.RuntimeException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static java.lang.String getEnumErrorMsg(Object value) 
	{ 
		return "'" + value + "' is not a valid CustomerReferenceType value."; 
	}
}
