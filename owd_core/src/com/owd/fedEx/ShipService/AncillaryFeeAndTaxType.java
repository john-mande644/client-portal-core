/*
 * AncillaryFeeAndTaxType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;

public class AncillaryFeeAndTaxType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected AncillaryFeeAndTaxType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _CLEARANCE_ENTRY_FEE = "CLEARANCE_ENTRY_FEE";
    public static final java.lang.String _GOODS_AND_SERVICES_TAX = "GOODS_AND_SERVICES_TAX";
    public static final java.lang.String _HARMONIZED_SALES_TAX = "HARMONIZED_SALES_TAX";
    public static final java.lang.String _OTHER = "OTHER";
    public static final AncillaryFeeAndTaxType CLEARANCE_ENTRY_FEE = new AncillaryFeeAndTaxType(_CLEARANCE_ENTRY_FEE);
    public static final AncillaryFeeAndTaxType GOODS_AND_SERVICES_TAX = new AncillaryFeeAndTaxType(_GOODS_AND_SERVICES_TAX);
    public static final AncillaryFeeAndTaxType HARMONIZED_SALES_TAX = new AncillaryFeeAndTaxType(_HARMONIZED_SALES_TAX);
    public static final AncillaryFeeAndTaxType OTHER = new AncillaryFeeAndTaxType(_OTHER);
    public java.lang.String getValue() { return _value_;}
    public static AncillaryFeeAndTaxType fromValue(java.lang.String value)
          throws java.lang.RuntimeException {
        AncillaryFeeAndTaxType enumX = (AncillaryFeeAndTaxType)
            _table_.get(value);
        if (enumX==null) throw new java.lang.RuntimeException(getEnumErrorMsg(value));
        return enumX;
    }
    public static AncillaryFeeAndTaxType fromString(java.lang.String value)
          throws java.lang.RuntimeException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static java.lang.String getEnumErrorMsg(Object value) 
	{ 
		return "'" + value + "' is not a valid AncillaryFeeAndTaxType value."; 
	}
}
