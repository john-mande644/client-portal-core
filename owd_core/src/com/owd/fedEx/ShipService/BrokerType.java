/*
 * BrokerType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;

public class BrokerType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected BrokerType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _EXPORT = "EXPORT";
    public static final java.lang.String _IMPORT = "IMPORT";
    public static final BrokerType EXPORT = new BrokerType(_EXPORT);
    public static final BrokerType IMPORT = new BrokerType(_IMPORT);
    public java.lang.String getValue() { return _value_;}
    public static BrokerType fromValue(java.lang.String value)
          throws java.lang.RuntimeException {
        BrokerType enumX = (BrokerType)
            _table_.get(value);
        if (enumX==null) throw new java.lang.RuntimeException(getEnumErrorMsg(value));
        return enumX;
    }
    public static BrokerType fromString(java.lang.String value)
          throws java.lang.RuntimeException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static java.lang.String getEnumErrorMsg(Object value) 
	{ 
		return "'" + value + "' is not a valid BrokerType value."; 
	}
}
