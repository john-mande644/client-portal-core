/*
 * SmartPostIndiciaType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;

public class SmartPostIndiciaType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected SmartPostIndiciaType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _MEDIA_MAIL = "MEDIA_MAIL";
    public static final java.lang.String _PARCEL_RETURN = "PARCEL_RETURN";
    public static final java.lang.String _PARCEL_SELECT = "PARCEL_SELECT";
    public static final java.lang.String _PRESORTED_BOUND_PRINTED_MATTER = "PRESORTED_BOUND_PRINTED_MATTER";
    public static final java.lang.String _PRESORTED_STANDARD = "PRESORTED_STANDARD";
    public static final SmartPostIndiciaType MEDIA_MAIL = new SmartPostIndiciaType(_MEDIA_MAIL);
    public static final SmartPostIndiciaType PARCEL_RETURN = new SmartPostIndiciaType(_PARCEL_RETURN);
    public static final SmartPostIndiciaType PARCEL_SELECT = new SmartPostIndiciaType(_PARCEL_SELECT);
    public static final SmartPostIndiciaType PRESORTED_BOUND_PRINTED_MATTER = new SmartPostIndiciaType(_PRESORTED_BOUND_PRINTED_MATTER);
    public static final SmartPostIndiciaType PRESORTED_STANDARD = new SmartPostIndiciaType(_PRESORTED_STANDARD);
    public java.lang.String getValue() { return _value_;}
    public static SmartPostIndiciaType fromValue(java.lang.String value)
          throws java.lang.RuntimeException {
        SmartPostIndiciaType enumX = (SmartPostIndiciaType)
            _table_.get(value);
        if (enumX==null) throw new java.lang.RuntimeException(getEnumErrorMsg(value));
        return enumX;
    }
    public static SmartPostIndiciaType fromString(java.lang.String value)
          throws java.lang.RuntimeException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static java.lang.String getEnumErrorMsg(Object value) 
	{ 
		return "'" + value + "' is not a valid SmartPostIndiciaType value."; 
	}
}
