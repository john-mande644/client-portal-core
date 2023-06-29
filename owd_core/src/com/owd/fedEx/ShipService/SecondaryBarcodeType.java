/*
 * SecondaryBarcodeType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;

public class SecondaryBarcodeType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected SecondaryBarcodeType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _COMMON_2D = "COMMON_2D";
    public static final java.lang.String _NONE = "NONE";
    public static final java.lang.String _SSCC_18 = "SSCC_18";
    public static final java.lang.String _USPS = "USPS";
    public static final SecondaryBarcodeType COMMON_2D = new SecondaryBarcodeType(_COMMON_2D);
    public static final SecondaryBarcodeType NONE = new SecondaryBarcodeType(_NONE);
    public static final SecondaryBarcodeType SSCC_18 = new SecondaryBarcodeType(_SSCC_18);
    public static final SecondaryBarcodeType USPS = new SecondaryBarcodeType(_USPS);
    public java.lang.String getValue() { return _value_;}
    public static SecondaryBarcodeType fromValue(java.lang.String value)
          throws java.lang.RuntimeException {
        SecondaryBarcodeType enumX = (SecondaryBarcodeType)
            _table_.get(value);
        if (enumX==null) throw new java.lang.RuntimeException(getEnumErrorMsg(value));
        return enumX;
    }
    public static SecondaryBarcodeType fromString(java.lang.String value)
          throws java.lang.RuntimeException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static java.lang.String getEnumErrorMsg(Object value) 
	{ 
		return "'" + value + "' is not a valid SecondaryBarcodeType value."; 
	}
}
