/*
 * TinType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;

public class TinType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected TinType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _BUSINESS_NATIONAL = "BUSINESS_NATIONAL";
    public static final java.lang.String _BUSINESS_STATE = "BUSINESS_STATE";
    public static final java.lang.String _BUSINESS_UNION = "BUSINESS_UNION";
    public static final java.lang.String _PERSONAL_NATIONAL = "PERSONAL_NATIONAL";
    public static final java.lang.String _PERSONAL_STATE = "PERSONAL_STATE";
    public static final TinType BUSINESS_NATIONAL = new TinType(_BUSINESS_NATIONAL);
    public static final TinType BUSINESS_STATE = new TinType(_BUSINESS_STATE);
    public static final TinType BUSINESS_UNION = new TinType(_BUSINESS_UNION);
    public static final TinType PERSONAL_NATIONAL = new TinType(_PERSONAL_NATIONAL);
    public static final TinType PERSONAL_STATE = new TinType(_PERSONAL_STATE);
    public java.lang.String getValue() { return _value_;}
    public static TinType fromValue(java.lang.String value)
          throws java.lang.RuntimeException {
        TinType enumX = (TinType)
            _table_.get(value);
        if (enumX==null) throw new java.lang.RuntimeException(getEnumErrorMsg(value));
        return enumX;
    }
    public static TinType fromString(java.lang.String value)
          throws java.lang.RuntimeException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static java.lang.String getEnumErrorMsg(Object value) 
	{ 
		return "'" + value + "' is not a valid TinType value."; 
	}
}
