/*
 * DangerousGoodsAccessibilityType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;

public class DangerousGoodsAccessibilityType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected DangerousGoodsAccessibilityType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _ACCESSIBLE = "ACCESSIBLE";
    public static final java.lang.String _INACCESSIBLE = "INACCESSIBLE";
    public static final DangerousGoodsAccessibilityType ACCESSIBLE = new DangerousGoodsAccessibilityType(_ACCESSIBLE);
    public static final DangerousGoodsAccessibilityType INACCESSIBLE = new DangerousGoodsAccessibilityType(_INACCESSIBLE);
    public java.lang.String getValue() { return _value_;}
    public static DangerousGoodsAccessibilityType fromValue(java.lang.String value)
          throws java.lang.RuntimeException {
        DangerousGoodsAccessibilityType enumX = (DangerousGoodsAccessibilityType)
            _table_.get(value);
        if (enumX==null) throw new java.lang.RuntimeException(getEnumErrorMsg(value));
        return enumX;
    }
    public static DangerousGoodsAccessibilityType fromString(java.lang.String value)
          throws java.lang.RuntimeException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static java.lang.String getEnumErrorMsg(Object value) 
	{ 
		return "'" + value + "' is not a valid DangerousGoodsAccessibilityType value."; 
	}
}
