/*
 * HazardousCommodityLabelTextOptionType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;


/**
 * Specifies how the commodity is to be labeled.
 */
public class HazardousCommodityLabelTextOptionType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected HazardousCommodityLabelTextOptionType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _APPEND = "APPEND";
    public static final java.lang.String _OVERRIDE = "OVERRIDE";
    public static final java.lang.String _STANDARD = "STANDARD";
    public static final HazardousCommodityLabelTextOptionType APPEND = new HazardousCommodityLabelTextOptionType(_APPEND);
    public static final HazardousCommodityLabelTextOptionType OVERRIDE = new HazardousCommodityLabelTextOptionType(_OVERRIDE);
    public static final HazardousCommodityLabelTextOptionType STANDARD = new HazardousCommodityLabelTextOptionType(_STANDARD);
    public java.lang.String getValue() { return _value_;}
    public static HazardousCommodityLabelTextOptionType fromValue(java.lang.String value)
          throws java.lang.RuntimeException {
        HazardousCommodityLabelTextOptionType enumX = (HazardousCommodityLabelTextOptionType)
            _table_.get(value);
        if (enumX==null) throw new java.lang.RuntimeException(getEnumErrorMsg(value));
        return enumX;
    }
    public static HazardousCommodityLabelTextOptionType fromString(java.lang.String value)
          throws java.lang.RuntimeException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static java.lang.String getEnumErrorMsg(Object value) 
	{ 
		return "'" + value + "' is not a valid HazardousCommodityLabelTextOptionType value."; 
	}
}
