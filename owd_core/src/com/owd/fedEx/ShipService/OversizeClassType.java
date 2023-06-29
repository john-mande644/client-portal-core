/*
 * OversizeClassType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;

public class OversizeClassType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected OversizeClassType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _OVERSIZE_1 = "OVERSIZE_1";
    public static final java.lang.String _OVERSIZE_2 = "OVERSIZE_2";
    public static final java.lang.String _OVERSIZE_3 = "OVERSIZE_3";
    public static final OversizeClassType OVERSIZE_1 = new OversizeClassType(_OVERSIZE_1);
    public static final OversizeClassType OVERSIZE_2 = new OversizeClassType(_OVERSIZE_2);
    public static final OversizeClassType OVERSIZE_3 = new OversizeClassType(_OVERSIZE_3);
    public java.lang.String getValue() { return _value_;}
    public static OversizeClassType fromValue(java.lang.String value)
          throws java.lang.RuntimeException {
        OversizeClassType enumX = (OversizeClassType)
            _table_.get(value);
        if (enumX==null) throw new java.lang.RuntimeException(getEnumErrorMsg(value));
        return enumX;
    }
    public static OversizeClassType fromString(java.lang.String value)
          throws java.lang.RuntimeException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static java.lang.String getEnumErrorMsg(Object value) 
	{ 
		return "'" + value + "' is not a valid OversizeClassType value."; 
	}
}
