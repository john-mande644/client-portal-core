/*
 * CodCollectionType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;


/**
 * Identifies the type of funds FedEx should collect upon shipment
 * delivery.
 */
public class CodCollectionType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected CodCollectionType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _ANY = "ANY";
    public static final java.lang.String _CASH = "CASH";
    public static final java.lang.String _COMPANY_CHECK = "COMPANY_CHECK";
    public static final java.lang.String _GUARANTEED_FUNDS = "GUARANTEED_FUNDS";
    public static final java.lang.String _PERSONAL_CHECK = "PERSONAL_CHECK";
    public static final CodCollectionType ANY = new CodCollectionType(_ANY);
    public static final CodCollectionType CASH = new CodCollectionType(_CASH);
    public static final CodCollectionType COMPANY_CHECK = new CodCollectionType(_COMPANY_CHECK);
    public static final CodCollectionType GUARANTEED_FUNDS = new CodCollectionType(_GUARANTEED_FUNDS);
    public static final CodCollectionType PERSONAL_CHECK = new CodCollectionType(_PERSONAL_CHECK);
    public java.lang.String getValue() { return _value_;}
    public static CodCollectionType fromValue(java.lang.String value)
          throws java.lang.RuntimeException {
        CodCollectionType enumX = (CodCollectionType)
            _table_.get(value);
        if (enumX==null) throw new java.lang.RuntimeException(getEnumErrorMsg(value));
        return enumX;
    }
    public static CodCollectionType fromString(java.lang.String value)
          throws java.lang.RuntimeException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static java.lang.String getEnumErrorMsg(Object value) 
	{ 
		return "'" + value + "' is not a valid CodCollectionType value."; 
	}
}
