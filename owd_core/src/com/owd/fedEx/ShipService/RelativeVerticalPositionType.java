/*
 * RelativeVerticalPositionType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;


/**
 * Describes the vertical position of an item relative to another
 * item.
 */
public class RelativeVerticalPositionType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected RelativeVerticalPositionType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _ABOVE = "ABOVE";
    public static final java.lang.String _BELOW = "BELOW";
    public static final RelativeVerticalPositionType ABOVE = new RelativeVerticalPositionType(_ABOVE);
    public static final RelativeVerticalPositionType BELOW = new RelativeVerticalPositionType(_BELOW);
    public java.lang.String getValue() { return _value_;}
    public static RelativeVerticalPositionType fromValue(java.lang.String value)
          throws java.lang.RuntimeException {
        RelativeVerticalPositionType enumX = (RelativeVerticalPositionType)
            _table_.get(value);
        if (enumX==null) throw new java.lang.RuntimeException(getEnumErrorMsg(value));
        return enumX;
    }
    public static RelativeVerticalPositionType fromString(java.lang.String value)
          throws java.lang.RuntimeException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static java.lang.String getEnumErrorMsg(Object value) 
	{ 
		return "'" + value + "' is not a valid RelativeVerticalPositionType value."; 
	}
}
