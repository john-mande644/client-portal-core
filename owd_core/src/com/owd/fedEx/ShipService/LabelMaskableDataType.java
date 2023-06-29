/*
 * LabelMaskableDataType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;


/**
 * Names for data elements / areas which may be suppressed from printing
 * on labels.
 */
public class LabelMaskableDataType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected LabelMaskableDataType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _CUSTOMS_VALUE = "CUSTOMS_VALUE";
    public static final java.lang.String _DUTIES_AND_TAXES_PAYOR_ACCOUNT_NUMBER = "DUTIES_AND_TAXES_PAYOR_ACCOUNT_NUMBER";
    public static final java.lang.String _SECONDARY_BARCODE = "SECONDARY_BARCODE";
    public static final java.lang.String _SHIPPER_ACCOUNT_NUMBER = "SHIPPER_ACCOUNT_NUMBER";
    public static final java.lang.String _TERMS_AND_CONDITIONS = "TERMS_AND_CONDITIONS";
    public static final java.lang.String _TRANSPORTATION_CHARGES_PAYOR_ACCOUNT_NUMBER = "TRANSPORTATION_CHARGES_PAYOR_ACCOUNT_NUMBER";
    public static final LabelMaskableDataType CUSTOMS_VALUE = new LabelMaskableDataType(_CUSTOMS_VALUE);
    public static final LabelMaskableDataType DUTIES_AND_TAXES_PAYOR_ACCOUNT_NUMBER = new LabelMaskableDataType(_DUTIES_AND_TAXES_PAYOR_ACCOUNT_NUMBER);
    public static final LabelMaskableDataType SECONDARY_BARCODE = new LabelMaskableDataType(_SECONDARY_BARCODE);
    public static final LabelMaskableDataType SHIPPER_ACCOUNT_NUMBER = new LabelMaskableDataType(_SHIPPER_ACCOUNT_NUMBER);
    public static final LabelMaskableDataType TERMS_AND_CONDITIONS = new LabelMaskableDataType(_TERMS_AND_CONDITIONS);
    public static final LabelMaskableDataType TRANSPORTATION_CHARGES_PAYOR_ACCOUNT_NUMBER = new LabelMaskableDataType(_TRANSPORTATION_CHARGES_PAYOR_ACCOUNT_NUMBER);
    public java.lang.String getValue() { return _value_;}
    public static LabelMaskableDataType fromValue(java.lang.String value)
          throws java.lang.RuntimeException {
        LabelMaskableDataType enumX = (LabelMaskableDataType)
            _table_.get(value);
        if (enumX==null) throw new java.lang.RuntimeException(getEnumErrorMsg(value));
        return enumX;
    }
    public static LabelMaskableDataType fromString(java.lang.String value)
          throws java.lang.RuntimeException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static java.lang.String getEnumErrorMsg(Object value) 
	{ 
		return "'" + value + "' is not a valid LabelMaskableDataType value."; 
	}
}
