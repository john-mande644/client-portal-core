/*
 * FreightBaseChargeCalculationType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;


/**
 * Specifies the way in which base charges for a Freight shipment
 * or shipment leg are calculated.
 */
public class FreightBaseChargeCalculationType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected FreightBaseChargeCalculationType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _BEYOND = "BEYOND";
    public static final java.lang.String _LINE_ITEMS = "LINE_ITEMS";
    public static final java.lang.String _UNIT_PRICING = "UNIT_PRICING";
    public static final FreightBaseChargeCalculationType BEYOND = new FreightBaseChargeCalculationType(_BEYOND);
    public static final FreightBaseChargeCalculationType LINE_ITEMS = new FreightBaseChargeCalculationType(_LINE_ITEMS);
    public static final FreightBaseChargeCalculationType UNIT_PRICING = new FreightBaseChargeCalculationType(_UNIT_PRICING);
    public java.lang.String getValue() { return _value_;}
    public static FreightBaseChargeCalculationType fromValue(java.lang.String value)
          throws java.lang.RuntimeException {
        FreightBaseChargeCalculationType enumX = (FreightBaseChargeCalculationType)
            _table_.get(value);
        if (enumX==null) throw new java.lang.RuntimeException(getEnumErrorMsg(value));
        return enumX;
    }
    public static FreightBaseChargeCalculationType fromString(java.lang.String value)
          throws java.lang.RuntimeException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static java.lang.String getEnumErrorMsg(Object value) 
	{ 
		return "'" + value + "' is not a valid FreightBaseChargeCalculationType value."; 
	}
}
