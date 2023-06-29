/*
 * PhysicalFormType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;

public class PhysicalFormType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected PhysicalFormType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _GAS = "GAS";
    public static final java.lang.String _LIQUID = "LIQUID";
    public static final java.lang.String _SOLID = "SOLID";
    public static final java.lang.String _SPECIAL = "SPECIAL";
    public static final PhysicalFormType GAS = new PhysicalFormType(_GAS);
    public static final PhysicalFormType LIQUID = new PhysicalFormType(_LIQUID);
    public static final PhysicalFormType SOLID = new PhysicalFormType(_SOLID);
    public static final PhysicalFormType SPECIAL = new PhysicalFormType(_SPECIAL);
    public java.lang.String getValue() { return _value_;}
    public static PhysicalFormType fromValue(java.lang.String value)
          throws java.lang.RuntimeException {
        PhysicalFormType enumX = (PhysicalFormType)
            _table_.get(value);
        if (enumX==null) throw new java.lang.RuntimeException(getEnumErrorMsg(value));
        return enumX;
    }
    public static PhysicalFormType fromString(java.lang.String value)
          throws java.lang.RuntimeException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static java.lang.String getEnumErrorMsg(Object value) 
	{ 
		return "'" + value + "' is not a valid PhysicalFormType value."; 
	}
}
