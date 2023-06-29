/*
 * LabelOrderType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;


/**
 * Specifies the order in which the labels will be returned
 */
public class LabelOrderType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected LabelOrderType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _SHIPPING_LABEL_FIRST = "SHIPPING_LABEL_FIRST";
    public static final java.lang.String _SHIPPING_LABEL_LAST = "SHIPPING_LABEL_LAST";
    public static final LabelOrderType SHIPPING_LABEL_FIRST = new LabelOrderType(_SHIPPING_LABEL_FIRST);
    public static final LabelOrderType SHIPPING_LABEL_LAST = new LabelOrderType(_SHIPPING_LABEL_LAST);
    public java.lang.String getValue() { return _value_;}
    public static LabelOrderType fromValue(java.lang.String value)
          throws java.lang.RuntimeException {
        LabelOrderType enumX = (LabelOrderType)
            _table_.get(value);
        if (enumX==null) throw new java.lang.RuntimeException(getEnumErrorMsg(value));
        return enumX;
    }
    public static LabelOrderType fromString(java.lang.String value)
          throws java.lang.RuntimeException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static java.lang.String getEnumErrorMsg(Object value) 
	{ 
		return "'" + value + "' is not a valid LabelOrderType value."; 
	}
}
