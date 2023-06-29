/*
 * PageQuadrantType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;

public class PageQuadrantType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected PageQuadrantType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _BOTTOM_LEFT = "BOTTOM_LEFT";
    public static final java.lang.String _BOTTOM_RIGHT = "BOTTOM_RIGHT";
    public static final java.lang.String _TOP_LEFT = "TOP_LEFT";
    public static final java.lang.String _TOP_RIGHT = "TOP_RIGHT";
    public static final PageQuadrantType BOTTOM_LEFT = new PageQuadrantType(_BOTTOM_LEFT);
    public static final PageQuadrantType BOTTOM_RIGHT = new PageQuadrantType(_BOTTOM_RIGHT);
    public static final PageQuadrantType TOP_LEFT = new PageQuadrantType(_TOP_LEFT);
    public static final PageQuadrantType TOP_RIGHT = new PageQuadrantType(_TOP_RIGHT);
    public java.lang.String getValue() { return _value_;}
    public static PageQuadrantType fromValue(java.lang.String value)
          throws java.lang.RuntimeException {
        PageQuadrantType enumX = (PageQuadrantType)
            _table_.get(value);
        if (enumX==null) throw new java.lang.RuntimeException(getEnumErrorMsg(value));
        return enumX;
    }
    public static PageQuadrantType fromString(java.lang.String value)
          throws java.lang.RuntimeException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static java.lang.String getEnumErrorMsg(Object value) 
	{ 
		return "'" + value + "' is not a valid PageQuadrantType value."; 
	}
}
