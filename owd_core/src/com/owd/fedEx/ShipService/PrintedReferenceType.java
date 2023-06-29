/*
 * PrintedReferenceType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;


/**
 * Identifies a particular reference identifier printed on a Freight
 * bill of lading.
 */
public class PrintedReferenceType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected PrintedReferenceType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _BILL_OF_LADING = "BILL_OF_LADING";
    public static final java.lang.String _CONSIGNEE_ID_NUMBER = "CONSIGNEE_ID_NUMBER";
    public static final java.lang.String _SHIPPER_ID_NUMBER = "SHIPPER_ID_NUMBER";
    public static final PrintedReferenceType BILL_OF_LADING = new PrintedReferenceType(_BILL_OF_LADING);
    public static final PrintedReferenceType CONSIGNEE_ID_NUMBER = new PrintedReferenceType(_CONSIGNEE_ID_NUMBER);
    public static final PrintedReferenceType SHIPPER_ID_NUMBER = new PrintedReferenceType(_SHIPPER_ID_NUMBER);
    public java.lang.String getValue() { return _value_;}
    public static PrintedReferenceType fromValue(java.lang.String value)
          throws java.lang.RuntimeException {
        PrintedReferenceType enumX = (PrintedReferenceType)
            _table_.get(value);
        if (enumX==null) throw new java.lang.RuntimeException(getEnumErrorMsg(value));
        return enumX;
    }
    public static PrintedReferenceType fromString(java.lang.String value)
          throws java.lang.RuntimeException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static java.lang.String getEnumErrorMsg(Object value) 
	{ 
		return "'" + value + "' is not a valid PrintedReferenceType value."; 
	}
}
