/*
 * CodReturnReferenceIndicatorType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;

public class CodReturnReferenceIndicatorType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected CodReturnReferenceIndicatorType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _INVOICE = "INVOICE";
    public static final java.lang.String _PO = "PO";
    public static final java.lang.String _REFERENCE = "REFERENCE";
    public static final java.lang.String _TRACKING = "TRACKING";
    public static final CodReturnReferenceIndicatorType INVOICE = new CodReturnReferenceIndicatorType(_INVOICE);
    public static final CodReturnReferenceIndicatorType PO = new CodReturnReferenceIndicatorType(_PO);
    public static final CodReturnReferenceIndicatorType REFERENCE = new CodReturnReferenceIndicatorType(_REFERENCE);
    public static final CodReturnReferenceIndicatorType TRACKING = new CodReturnReferenceIndicatorType(_TRACKING);
    public java.lang.String getValue() { return _value_;}
    public static CodReturnReferenceIndicatorType fromValue(java.lang.String value)
          throws java.lang.RuntimeException {
        CodReturnReferenceIndicatorType enumX = (CodReturnReferenceIndicatorType)
            _table_.get(value);
        if (enumX==null) throw new java.lang.RuntimeException(getEnumErrorMsg(value));
        return enumX;
    }
    public static CodReturnReferenceIndicatorType fromString(java.lang.String value)
          throws java.lang.RuntimeException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static java.lang.String getEnumErrorMsg(Object value) 
	{ 
		return "'" + value + "' is not a valid CodReturnReferenceIndicatorType value."; 
	}
}
