/*
 * InternationalDocumentContentType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;

public class InternationalDocumentContentType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected InternationalDocumentContentType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _DERIVED = "DERIVED";
    public static final java.lang.String _DOCUMENTS_ONLY = "DOCUMENTS_ONLY";
    public static final java.lang.String _NON_DOCUMENTS = "NON_DOCUMENTS";
    public static final InternationalDocumentContentType DERIVED = new InternationalDocumentContentType(_DERIVED);
    public static final InternationalDocumentContentType DOCUMENTS_ONLY = new InternationalDocumentContentType(_DOCUMENTS_ONLY);
    public static final InternationalDocumentContentType NON_DOCUMENTS = new InternationalDocumentContentType(_NON_DOCUMENTS);
    public java.lang.String getValue() { return _value_;}
    public static InternationalDocumentContentType fromValue(java.lang.String value)
          throws java.lang.RuntimeException {
        InternationalDocumentContentType enumX = (InternationalDocumentContentType)
            _table_.get(value);
        if (enumX==null) throw new java.lang.RuntimeException(getEnumErrorMsg(value));
        return enumX;
    }
    public static InternationalDocumentContentType fromString(java.lang.String value)
          throws java.lang.RuntimeException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static java.lang.String getEnumErrorMsg(Object value) 
	{ 
		return "'" + value + "' is not a valid InternationalDocumentContentType value."; 
	}
}
