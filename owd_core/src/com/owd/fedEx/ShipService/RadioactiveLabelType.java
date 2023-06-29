/*
 * RadioactiveLabelType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;

public class RadioactiveLabelType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected RadioactiveLabelType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _III_YELLOW = "III_YELLOW";
    public static final java.lang.String _II_YELLOW = "II_YELLOW";
    public static final java.lang.String _I_WHITE = "I_WHITE";
    public static final RadioactiveLabelType III_YELLOW = new RadioactiveLabelType(_III_YELLOW);
    public static final RadioactiveLabelType II_YELLOW = new RadioactiveLabelType(_II_YELLOW);
    public static final RadioactiveLabelType I_WHITE = new RadioactiveLabelType(_I_WHITE);
    public java.lang.String getValue() { return _value_;}
    public static RadioactiveLabelType fromValue(java.lang.String value)
          throws java.lang.RuntimeException {
        RadioactiveLabelType enumX = (RadioactiveLabelType)
            _table_.get(value);
        if (enumX==null) throw new java.lang.RuntimeException(getEnumErrorMsg(value));
        return enumX;
    }
    public static RadioactiveLabelType fromString(java.lang.String value)
          throws java.lang.RuntimeException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static java.lang.String getEnumErrorMsg(Object value) 
	{ 
		return "'" + value + "' is not a valid RadioactiveLabelType value."; 
	}
}
