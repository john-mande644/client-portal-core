/*
 * FreightRateQuoteType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;


/**
 * Specifies the type of rate quote
 */
public class FreightRateQuoteType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected FreightRateQuoteType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _AUTOMATED = "AUTOMATED";
    public static final java.lang.String _MANUAL = "MANUAL";
    public static final FreightRateQuoteType AUTOMATED = new FreightRateQuoteType(_AUTOMATED);
    public static final FreightRateQuoteType MANUAL = new FreightRateQuoteType(_MANUAL);
    public java.lang.String getValue() { return _value_;}
    public static FreightRateQuoteType fromValue(java.lang.String value)
          throws java.lang.RuntimeException {
        FreightRateQuoteType enumX = (FreightRateQuoteType)
            _table_.get(value);
        if (enumX==null) throw new java.lang.RuntimeException(getEnumErrorMsg(value));
        return enumX;
    }
    public static FreightRateQuoteType fromString(java.lang.String value)
          throws java.lang.RuntimeException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static java.lang.String getEnumErrorMsg(Object value) 
	{ 
		return "'" + value + "' is not a valid FreightRateQuoteType value."; 
	}
}
