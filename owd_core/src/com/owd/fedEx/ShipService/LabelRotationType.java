/*
 * LabelRotationType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;


/**
 * Relative to normal orientation for the printer.
 */
public class LabelRotationType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected LabelRotationType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _LEFT = "LEFT";
    public static final java.lang.String _NONE = "NONE";
    public static final java.lang.String _RIGHT = "RIGHT";
    public static final java.lang.String _UPSIDE_DOWN = "UPSIDE_DOWN";
    public static final LabelRotationType LEFT = new LabelRotationType(_LEFT);
    public static final LabelRotationType NONE = new LabelRotationType(_NONE);
    public static final LabelRotationType RIGHT = new LabelRotationType(_RIGHT);
    public static final LabelRotationType UPSIDE_DOWN = new LabelRotationType(_UPSIDE_DOWN);
    public java.lang.String getValue() { return _value_;}
    public static LabelRotationType fromValue(java.lang.String value)
          throws java.lang.RuntimeException {
        LabelRotationType enumX = (LabelRotationType)
            _table_.get(value);
        if (enumX==null) throw new java.lang.RuntimeException(getEnumErrorMsg(value));
        return enumX;
    }
    public static LabelRotationType fromString(java.lang.String value)
          throws java.lang.RuntimeException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static java.lang.String getEnumErrorMsg(Object value) 
	{ 
		return "'" + value + "' is not a valid LabelRotationType value."; 
	}
}
