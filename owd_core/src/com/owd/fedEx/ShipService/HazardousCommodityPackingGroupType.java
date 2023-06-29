/*
 * HazardousCommodityPackingGroupType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;


/**
 * Identifies DOT packing group for a hazardous commodity.
 */
public class HazardousCommodityPackingGroupType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected HazardousCommodityPackingGroupType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _DEFAULT = "DEFAULT";
    public static final java.lang.String _I = "I";
    public static final java.lang.String _II = "II";
    public static final java.lang.String _III = "III";
    public static final HazardousCommodityPackingGroupType DEFAULT = new HazardousCommodityPackingGroupType(_DEFAULT);
    public static final HazardousCommodityPackingGroupType I = new HazardousCommodityPackingGroupType(_I);
    public static final HazardousCommodityPackingGroupType II = new HazardousCommodityPackingGroupType(_II);
    public static final HazardousCommodityPackingGroupType III = new HazardousCommodityPackingGroupType(_III);
    public java.lang.String getValue() { return _value_;}
    public static HazardousCommodityPackingGroupType fromValue(java.lang.String value)
          throws java.lang.RuntimeException {
        HazardousCommodityPackingGroupType enumX = (HazardousCommodityPackingGroupType)
            _table_.get(value);
        if (enumX==null) throw new java.lang.RuntimeException(getEnumErrorMsg(value));
        return enumX;
    }
    public static HazardousCommodityPackingGroupType fromString(java.lang.String value)
          throws java.lang.RuntimeException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static java.lang.String getEnumErrorMsg(Object value) 
	{ 
		return "'" + value + "' is not a valid HazardousCommodityPackingGroupType value."; 
	}
}
