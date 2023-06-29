/*
 * ReturnedRateType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;


/**
 * The "PAYOR..." rates are expressed in the currency identified in
 * the payor's rate table(s). The "RATED..." rates are expressed in the
 * currency of the origin country. Former "...COUNTER..." values have
 * become "...RETAIL..." values, except for PAYOR_COUNTER and RATED_COUNTER,
 * which have been removed.
 */
public class ReturnedRateType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected ReturnedRateType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _INCENTIVE = "INCENTIVE";
    public static final java.lang.String _NEGOTIATED = "NEGOTIATED";
    public static final java.lang.String _PAYOR_ACCOUNT_PACKAGE = "PAYOR_ACCOUNT_PACKAGE";
    public static final java.lang.String _PAYOR_ACCOUNT_SHIPMENT = "PAYOR_ACCOUNT_SHIPMENT";
    public static final java.lang.String _PAYOR_LIST_PACKAGE = "PAYOR_LIST_PACKAGE";
    public static final java.lang.String _PAYOR_LIST_SHIPMENT = "PAYOR_LIST_SHIPMENT";
    public static final java.lang.String _PREFERRED_ACCOUNT_PACKAGE = "PREFERRED_ACCOUNT_PACKAGE";
    public static final java.lang.String _PREFERRED_ACCOUNT_SHIPMENT = "PREFERRED_ACCOUNT_SHIPMENT";
    public static final java.lang.String _PREFERRED_LIST_PACKAGE = "PREFERRED_LIST_PACKAGE";
    public static final java.lang.String _PREFERRED_LIST_SHIPMENT = "PREFERRED_LIST_SHIPMENT";
    public static final java.lang.String _PREFERRED_NEGOTIATED = "PREFERRED_NEGOTIATED";
    public static final ReturnedRateType INCENTIVE = new ReturnedRateType(_INCENTIVE);
    public static final ReturnedRateType NEGOTIATED = new ReturnedRateType(_NEGOTIATED);
    public static final ReturnedRateType PAYOR_ACCOUNT_PACKAGE = new ReturnedRateType(_PAYOR_ACCOUNT_PACKAGE);
    public static final ReturnedRateType PAYOR_ACCOUNT_SHIPMENT = new ReturnedRateType(_PAYOR_ACCOUNT_SHIPMENT);
    public static final ReturnedRateType PAYOR_LIST_PACKAGE = new ReturnedRateType(_PAYOR_LIST_PACKAGE);
    public static final ReturnedRateType PAYOR_LIST_SHIPMENT = new ReturnedRateType(_PAYOR_LIST_SHIPMENT);
    public static final ReturnedRateType PREFERRED_ACCOUNT_PACKAGE = new ReturnedRateType(_PREFERRED_ACCOUNT_PACKAGE);
    public static final ReturnedRateType PREFERRED_ACCOUNT_SHIPMENT = new ReturnedRateType(_PREFERRED_ACCOUNT_SHIPMENT);
    public static final ReturnedRateType PREFERRED_LIST_PACKAGE = new ReturnedRateType(_PREFERRED_LIST_PACKAGE);
    public static final ReturnedRateType PREFERRED_LIST_SHIPMENT = new ReturnedRateType(_PREFERRED_LIST_SHIPMENT);
    public static final ReturnedRateType PREFERRED_NEGOTIATED = new ReturnedRateType(_PREFERRED_NEGOTIATED);
    public java.lang.String getValue() { return _value_;}
    public static ReturnedRateType fromValue(java.lang.String value)
          throws java.lang.RuntimeException {
        ReturnedRateType enumX = (ReturnedRateType)
            _table_.get(value);
        if (enumX==null) throw new java.lang.RuntimeException(getEnumErrorMsg(value));
        return enumX;
    }
    public static ReturnedRateType fromString(java.lang.String value)
          throws java.lang.RuntimeException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static java.lang.String getEnumErrorMsg(Object value) 
	{ 
		return "'" + value + "' is not a valid ReturnedRateType value."; 
	}
}
