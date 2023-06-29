/*
 * TaxType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;

public class TaxType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected TaxType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _EXPORT = "EXPORT";
    public static final java.lang.String _GST = "GST";
    public static final java.lang.String _HST = "HST";
    public static final java.lang.String _INTRACOUNTRY = "INTRACOUNTRY";
    public static final java.lang.String _OTHER = "OTHER";
    public static final java.lang.String _PST = "PST";
    public static final java.lang.String _VAT = "VAT";
    public static final TaxType EXPORT = new TaxType(_EXPORT);
    public static final TaxType GST = new TaxType(_GST);
    public static final TaxType HST = new TaxType(_HST);
    public static final TaxType INTRACOUNTRY = new TaxType(_INTRACOUNTRY);
    public static final TaxType OTHER = new TaxType(_OTHER);
    public static final TaxType PST = new TaxType(_PST);
    public static final TaxType VAT = new TaxType(_VAT);
    public java.lang.String getValue() { return _value_;}
    public static TaxType fromValue(java.lang.String value)
          throws java.lang.RuntimeException {
        TaxType enumX = (TaxType)
            _table_.get(value);
        if (enumX==null) throw new java.lang.RuntimeException(getEnumErrorMsg(value));
        return enumX;
    }
    public static TaxType fromString(java.lang.String value)
          throws java.lang.RuntimeException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static java.lang.String getEnumErrorMsg(Object value) 
	{ 
		return "'" + value + "' is not a valid TaxType value."; 
	}
}
