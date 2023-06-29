/*
 * RadioactivityUnitOfMeasure.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;

public class RadioactivityUnitOfMeasure implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected RadioactivityUnitOfMeasure(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _BQ = "BQ";
    public static final java.lang.String _GBQ = "GBQ";
    public static final java.lang.String _KBQ = "KBQ";
    public static final java.lang.String _MBQ = "MBQ";
    public static final java.lang.String _PBQ = "PBQ";
    public static final java.lang.String _TBQ = "TBQ";
    public static final RadioactivityUnitOfMeasure BQ = new RadioactivityUnitOfMeasure(_BQ);
    public static final RadioactivityUnitOfMeasure GBQ = new RadioactivityUnitOfMeasure(_GBQ);
    public static final RadioactivityUnitOfMeasure KBQ = new RadioactivityUnitOfMeasure(_KBQ);
    public static final RadioactivityUnitOfMeasure MBQ = new RadioactivityUnitOfMeasure(_MBQ);
    public static final RadioactivityUnitOfMeasure PBQ = new RadioactivityUnitOfMeasure(_PBQ);
    public static final RadioactivityUnitOfMeasure TBQ = new RadioactivityUnitOfMeasure(_TBQ);
    public java.lang.String getValue() { return _value_;}
    public static RadioactivityUnitOfMeasure fromValue(java.lang.String value)
          throws java.lang.RuntimeException {
        RadioactivityUnitOfMeasure enumX = (RadioactivityUnitOfMeasure)
            _table_.get(value);
        if (enumX==null) throw new java.lang.RuntimeException(getEnumErrorMsg(value));
        return enumX;
    }
    public static RadioactivityUnitOfMeasure fromString(java.lang.String value)
          throws java.lang.RuntimeException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static java.lang.String getEnumErrorMsg(Object value) 
	{ 
		return "'" + value + "' is not a valid RadioactivityUnitOfMeasure value."; 
	}
}
