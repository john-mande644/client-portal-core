/*
 * LabelStockType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;

public class LabelStockType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected LabelStockType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _value1 = "PAPER_4X6";
    public static final java.lang.String _value2 = "PAPER_4X6.75";
    public static final java.lang.String _value3 = "PAPER_4X8";
    public static final java.lang.String _value4 = "PAPER_4X9";
    public static final java.lang.String _value5 = "PAPER_7X4.75";
    public static final java.lang.String _value6 = "PAPER_8.5X11_BOTTOM_HALF_LABEL";
    public static final java.lang.String _value7 = "PAPER_8.5X11_TOP_HALF_LABEL";
    public static final java.lang.String _value8 = "PAPER_LETTER";
    public static final java.lang.String _value9 = "STOCK_4X6";
    public static final java.lang.String _value10 = "STOCK_4X6.75";
    public static final java.lang.String _value11 = "STOCK_4X6.75_LEADING_DOC_TAB";
    public static final java.lang.String _value12 = "STOCK_4X6.75_TRAILING_DOC_TAB";
    public static final java.lang.String _value13 = "STOCK_4X8";
    public static final java.lang.String _value14 = "STOCK_4X9";
    public static final java.lang.String _value15 = "STOCK_4X9_LEADING_DOC_TAB";
    public static final java.lang.String _value16 = "STOCK_4X9_TRAILING_DOC_TAB";
    public static final LabelStockType value1 = new LabelStockType(_value1);
    public static final LabelStockType value2 = new LabelStockType(_value2);
    public static final LabelStockType value3 = new LabelStockType(_value3);
    public static final LabelStockType value4 = new LabelStockType(_value4);
    public static final LabelStockType value5 = new LabelStockType(_value5);
    public static final LabelStockType value6 = new LabelStockType(_value6);
    public static final LabelStockType value7 = new LabelStockType(_value7);
    public static final LabelStockType value8 = new LabelStockType(_value8);
    public static final LabelStockType value9 = new LabelStockType(_value9);
    public static final LabelStockType value10 = new LabelStockType(_value10);
    public static final LabelStockType value11 = new LabelStockType(_value11);
    public static final LabelStockType value12 = new LabelStockType(_value12);
    public static final LabelStockType value13 = new LabelStockType(_value13);
    public static final LabelStockType value14 = new LabelStockType(_value14);
    public static final LabelStockType value15 = new LabelStockType(_value15);
    public static final LabelStockType value16 = new LabelStockType(_value16);
    public java.lang.String getValue() { return _value_;}
    public static LabelStockType fromValue(java.lang.String value)
          throws java.lang.RuntimeException {
        LabelStockType enumX = (LabelStockType)
            _table_.get(value);
        if (enumX==null) throw new java.lang.RuntimeException(getEnumErrorMsg(value));
        return enumX;
    }
    public static LabelStockType fromString(java.lang.String value)
          throws java.lang.RuntimeException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static java.lang.String getEnumErrorMsg(Object value) 
	{ 
		return "'" + value + "' is not a valid LabelStockType value."; 
	}
}
