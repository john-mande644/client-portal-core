/*
 * LabelFormatType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;

public class LabelFormatType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected LabelFormatType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _COMMON2D = "COMMON2D";
    public static final java.lang.String _LABEL_DATA_ONLY = "LABEL_DATA_ONLY";
    public static final LabelFormatType COMMON2D = new LabelFormatType(_COMMON2D);
    public static final LabelFormatType LABEL_DATA_ONLY = new LabelFormatType(_LABEL_DATA_ONLY);
    public java.lang.String getValue() { return _value_;}
    public static LabelFormatType fromValue(java.lang.String value)
          throws java.lang.RuntimeException {
        LabelFormatType enumX = (LabelFormatType)
            _table_.get(value);
        if (enumX==null) throw new java.lang.RuntimeException(getEnumErrorMsg(value));
        return enumX;
    }
    public static LabelFormatType fromString(java.lang.String value)
          throws java.lang.RuntimeException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static java.lang.String getEnumErrorMsg(Object value) 
	{ 
		return "'" + value + "' is not a valid LabelFormatType value."; 
	}
}
