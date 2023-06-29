/*
 * HazardousCommodityDescriptionProcessingOptionType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;


/**
 * Specifies any special processing to be applied to the dangerous
 * goods commodity description validation.
 */
public class HazardousCommodityDescriptionProcessingOptionType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected HazardousCommodityDescriptionProcessingOptionType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _INCLUDE_SPECIAL_PROVISIONS = "INCLUDE_SPECIAL_PROVISIONS";
    public static final HazardousCommodityDescriptionProcessingOptionType INCLUDE_SPECIAL_PROVISIONS = new HazardousCommodityDescriptionProcessingOptionType(_INCLUDE_SPECIAL_PROVISIONS);
    public java.lang.String getValue() { return _value_;}
    public static HazardousCommodityDescriptionProcessingOptionType fromValue(java.lang.String value)
          throws java.lang.RuntimeException {
        HazardousCommodityDescriptionProcessingOptionType enumX = (HazardousCommodityDescriptionProcessingOptionType)
            _table_.get(value);
        if (enumX==null) throw new java.lang.RuntimeException(getEnumErrorMsg(value));
        return enumX;
    }
    public static HazardousCommodityDescriptionProcessingOptionType fromString(java.lang.String value)
          throws java.lang.RuntimeException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static java.lang.String getEnumErrorMsg(Object value) 
	{ 
		return "'" + value + "' is not a valid HazardousCommodityDescriptionProcessingOptionType value."; 
	}
}
