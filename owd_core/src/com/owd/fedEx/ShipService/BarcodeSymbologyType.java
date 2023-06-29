/*
 * BarcodeSymbologyType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;


/**
 * Identification of the type of barcode (symbology) used on FedEx
 * documents and labels.
 */
public class BarcodeSymbologyType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected BarcodeSymbologyType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _CODABAR = "CODABAR";
    public static final java.lang.String _CODE128 = "CODE128";
    public static final java.lang.String _CODE128B = "CODE128B";
    public static final java.lang.String _CODE128C = "CODE128C";
    public static final java.lang.String _CODE128_WIDEBAR = "CODE128_WIDEBAR";
    public static final java.lang.String _CODE39 = "CODE39";
    public static final java.lang.String _CODE93 = "CODE93";
    public static final java.lang.String _I2OF5 = "I2OF5";
    public static final java.lang.String _PDF417 = "PDF417";
    public static final java.lang.String _POSTNET = "POSTNET";
    public static final java.lang.String _QR_CODE = "QR_CODE";
    public static final java.lang.String _UCC128 = "UCC128";
    public static final BarcodeSymbologyType CODABAR = new BarcodeSymbologyType(_CODABAR);
    public static final BarcodeSymbologyType CODE128 = new BarcodeSymbologyType(_CODE128);
    public static final BarcodeSymbologyType CODE128B = new BarcodeSymbologyType(_CODE128B);
    public static final BarcodeSymbologyType CODE128C = new BarcodeSymbologyType(_CODE128C);
    public static final BarcodeSymbologyType CODE128_WIDEBAR = new BarcodeSymbologyType(_CODE128_WIDEBAR);
    public static final BarcodeSymbologyType CODE39 = new BarcodeSymbologyType(_CODE39);
    public static final BarcodeSymbologyType CODE93 = new BarcodeSymbologyType(_CODE93);
    public static final BarcodeSymbologyType I2OF5 = new BarcodeSymbologyType(_I2OF5);
    public static final BarcodeSymbologyType PDF417 = new BarcodeSymbologyType(_PDF417);
    public static final BarcodeSymbologyType POSTNET = new BarcodeSymbologyType(_POSTNET);
    public static final BarcodeSymbologyType QR_CODE = new BarcodeSymbologyType(_QR_CODE);
    public static final BarcodeSymbologyType UCC128 = new BarcodeSymbologyType(_UCC128);
    public java.lang.String getValue() { return _value_;}
    public static BarcodeSymbologyType fromValue(java.lang.String value)
          throws java.lang.RuntimeException {
        BarcodeSymbologyType enumX = (BarcodeSymbologyType)
            _table_.get(value);
        if (enumX==null) throw new java.lang.RuntimeException(getEnumErrorMsg(value));
        return enumX;
    }
    public static BarcodeSymbologyType fromString(java.lang.String value)
          throws java.lang.RuntimeException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static java.lang.String getEnumErrorMsg(Object value) 
	{ 
		return "'" + value + "' is not a valid BarcodeSymbologyType value."; 
	}
}
