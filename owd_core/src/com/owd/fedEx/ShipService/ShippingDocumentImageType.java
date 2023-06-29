/*
 * ShippingDocumentImageType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;


/**
 * Specifies the image format used for a shipping document.
 */
public class ShippingDocumentImageType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected ShippingDocumentImageType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _DOC = "DOC";
    public static final java.lang.String _EPL2 = "EPL2";
    public static final java.lang.String _PDF = "PDF";
    public static final java.lang.String _PNG = "PNG";
    public static final java.lang.String _RTF = "RTF";
    public static final java.lang.String _TEXT = "TEXT";
    public static final java.lang.String _ZPLII = "ZPLII";
    public static final ShippingDocumentImageType DOC = new ShippingDocumentImageType(_DOC);
    public static final ShippingDocumentImageType EPL2 = new ShippingDocumentImageType(_EPL2);
    public static final ShippingDocumentImageType PDF = new ShippingDocumentImageType(_PDF);
    public static final ShippingDocumentImageType PNG = new ShippingDocumentImageType(_PNG);
    public static final ShippingDocumentImageType RTF = new ShippingDocumentImageType(_RTF);
    public static final ShippingDocumentImageType TEXT = new ShippingDocumentImageType(_TEXT);
    public static final ShippingDocumentImageType ZPLII = new ShippingDocumentImageType(_ZPLII);
    public java.lang.String getValue() { return _value_;}
    public static ShippingDocumentImageType fromValue(java.lang.String value)
          throws java.lang.RuntimeException {
        ShippingDocumentImageType enumX = (ShippingDocumentImageType)
            _table_.get(value);
        if (enumX==null) throw new java.lang.RuntimeException(getEnumErrorMsg(value));
        return enumX;
    }
    public static ShippingDocumentImageType fromString(java.lang.String value)
          throws java.lang.RuntimeException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static java.lang.String getEnumErrorMsg(Object value) 
	{ 
		return "'" + value + "' is not a valid ShippingDocumentImageType value."; 
	}
}
