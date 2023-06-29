/*
 * RateTypeBasisType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;


/**
 * Select the type of rate from which the element is to be selected.
 */
public class RateTypeBasisType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected RateTypeBasisType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _ACCOUNT = "ACCOUNT";
    public static final java.lang.String _LIST = "LIST";
    public static final RateTypeBasisType ACCOUNT = new RateTypeBasisType(_ACCOUNT);
    public static final RateTypeBasisType LIST = new RateTypeBasisType(_LIST);
    public java.lang.String getValue() { return _value_;}
    public static RateTypeBasisType fromValue(java.lang.String value)
          throws java.lang.RuntimeException {
        RateTypeBasisType enumX = (RateTypeBasisType)
            _table_.get(value);
        if (enumX==null) throw new java.lang.RuntimeException(getEnumErrorMsg(value));
        return enumX;
    }
    public static RateTypeBasisType fromString(java.lang.String value)
          throws java.lang.RuntimeException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static java.lang.String getEnumErrorMsg(Object value) 
	{ 
		return "'" + value + "' is not a valid RateTypeBasisType value."; 
	}
}
