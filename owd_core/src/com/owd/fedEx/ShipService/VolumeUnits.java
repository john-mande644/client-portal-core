/*
 * VolumeUnits.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;


/**
 * Units of three-dimensional volume/cubic measure.
 */
public class VolumeUnits implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected VolumeUnits(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _CUBIC_FT = "CUBIC_FT";
    public static final java.lang.String _CUBIC_M = "CUBIC_M";
    public static final VolumeUnits CUBIC_FT = new VolumeUnits(_CUBIC_FT);
    public static final VolumeUnits CUBIC_M = new VolumeUnits(_CUBIC_M);
    public java.lang.String getValue() { return _value_;}
    public static VolumeUnits fromValue(java.lang.String value)
          throws java.lang.RuntimeException {
        VolumeUnits enumX = (VolumeUnits)
            _table_.get(value);
        if (enumX==null) throw new java.lang.RuntimeException(getEnumErrorMsg(value));
        return enumX;
    }
    public static VolumeUnits fromString(java.lang.String value)
          throws java.lang.RuntimeException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static java.lang.String getEnumErrorMsg(Object value) 
	{ 
		return "'" + value + "' is not a valid VolumeUnits value."; 
	}
}
