/*
 * CustomerImageUsageType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;

public class CustomerImageUsageType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected CustomerImageUsageType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _LETTER_HEAD = "LETTER_HEAD";
    public static final java.lang.String _SIGNATURE = "SIGNATURE";
    public static final CustomerImageUsageType LETTER_HEAD = new CustomerImageUsageType(_LETTER_HEAD);
    public static final CustomerImageUsageType SIGNATURE = new CustomerImageUsageType(_SIGNATURE);
    public java.lang.String getValue() { return _value_;}
    public static CustomerImageUsageType fromValue(java.lang.String value)
          throws java.lang.RuntimeException {
        CustomerImageUsageType enumX = (CustomerImageUsageType)
            _table_.get(value);
        if (enumX==null) throw new java.lang.RuntimeException(getEnumErrorMsg(value));
        return enumX;
    }
    public static CustomerImageUsageType fromString(java.lang.String value)
          throws java.lang.RuntimeException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static java.lang.String getEnumErrorMsg(Object value) 
	{ 
		return "'" + value + "' is not a valid CustomerImageUsageType value."; 
	}
}
