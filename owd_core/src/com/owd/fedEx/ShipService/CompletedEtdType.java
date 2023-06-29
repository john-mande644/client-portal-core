/*
 * CompletedEtdType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;

public class CompletedEtdType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected CompletedEtdType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _ELECTRONIC_DOCUMENTS_ONLY = "ELECTRONIC_DOCUMENTS_ONLY";
    public static final java.lang.String _ELECTRONIC_DOCUMENTS_WITH_ORIGINALS = "ELECTRONIC_DOCUMENTS_WITH_ORIGINALS";
    public static final CompletedEtdType ELECTRONIC_DOCUMENTS_ONLY = new CompletedEtdType(_ELECTRONIC_DOCUMENTS_ONLY);
    public static final CompletedEtdType ELECTRONIC_DOCUMENTS_WITH_ORIGINALS = new CompletedEtdType(_ELECTRONIC_DOCUMENTS_WITH_ORIGINALS);
    public java.lang.String getValue() { return _value_;}
    public static CompletedEtdType fromValue(java.lang.String value)
          throws java.lang.RuntimeException {
        CompletedEtdType enumX = (CompletedEtdType)
            _table_.get(value);
        if (enumX==null) throw new java.lang.RuntimeException(getEnumErrorMsg(value));
        return enumX;
    }
    public static CompletedEtdType fromString(java.lang.String value)
          throws java.lang.RuntimeException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static java.lang.String getEnumErrorMsg(Object value) 
	{ 
		return "'" + value + "' is not a valid CompletedEtdType value."; 
	}
}
