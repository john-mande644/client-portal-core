/*
 * CustomerSpecifiedLabelGenerationOptionType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;

public class CustomerSpecifiedLabelGenerationOptionType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected CustomerSpecifiedLabelGenerationOptionType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _CONTENT_ON_SHIPPING_LABEL_ONLY = "CONTENT_ON_SHIPPING_LABEL_ONLY";
    public static final java.lang.String _CONTENT_ON_SHIPPING_LABEL_PREFERRED = "CONTENT_ON_SHIPPING_LABEL_PREFERRED";
    public static final java.lang.String _CONTENT_ON_SUPPLEMENTAL_LABEL_ONLY = "CONTENT_ON_SUPPLEMENTAL_LABEL_ONLY";
    public static final CustomerSpecifiedLabelGenerationOptionType CONTENT_ON_SHIPPING_LABEL_ONLY = new CustomerSpecifiedLabelGenerationOptionType(_CONTENT_ON_SHIPPING_LABEL_ONLY);
    public static final CustomerSpecifiedLabelGenerationOptionType CONTENT_ON_SHIPPING_LABEL_PREFERRED = new CustomerSpecifiedLabelGenerationOptionType(_CONTENT_ON_SHIPPING_LABEL_PREFERRED);
    public static final CustomerSpecifiedLabelGenerationOptionType CONTENT_ON_SUPPLEMENTAL_LABEL_ONLY = new CustomerSpecifiedLabelGenerationOptionType(_CONTENT_ON_SUPPLEMENTAL_LABEL_ONLY);
    public java.lang.String getValue() { return _value_;}
    public static CustomerSpecifiedLabelGenerationOptionType fromValue(java.lang.String value)
          throws java.lang.RuntimeException {
        CustomerSpecifiedLabelGenerationOptionType enumX = (CustomerSpecifiedLabelGenerationOptionType)
            _table_.get(value);
        if (enumX==null) throw new java.lang.RuntimeException(getEnumErrorMsg(value));
        return enumX;
    }
    public static CustomerSpecifiedLabelGenerationOptionType fromString(java.lang.String value)
          throws java.lang.RuntimeException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static java.lang.String getEnumErrorMsg(Object value) 
	{ 
		return "'" + value + "' is not a valid CustomerSpecifiedLabelGenerationOptionType value."; 
	}
}
