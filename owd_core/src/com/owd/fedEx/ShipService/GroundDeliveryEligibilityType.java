/*
 * GroundDeliveryEligibilityType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;


/**
 * Represents features of FedEx Ground delivery for which the shipment
 * is eligible.
 */
public class GroundDeliveryEligibilityType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected GroundDeliveryEligibilityType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _ALTERNATE_DAY_SERVICE = "ALTERNATE_DAY_SERVICE";
    public static final java.lang.String _CARTAGE_AGENT_DELIVERY = "CARTAGE_AGENT_DELIVERY";
    public static final java.lang.String _SATURDAY_DELIVERY = "SATURDAY_DELIVERY";
    public static final java.lang.String _USPS_DELIVERY = "USPS_DELIVERY";
    public static final GroundDeliveryEligibilityType ALTERNATE_DAY_SERVICE = new GroundDeliveryEligibilityType(_ALTERNATE_DAY_SERVICE);
    public static final GroundDeliveryEligibilityType CARTAGE_AGENT_DELIVERY = new GroundDeliveryEligibilityType(_CARTAGE_AGENT_DELIVERY);
    public static final GroundDeliveryEligibilityType SATURDAY_DELIVERY = new GroundDeliveryEligibilityType(_SATURDAY_DELIVERY);
    public static final GroundDeliveryEligibilityType USPS_DELIVERY = new GroundDeliveryEligibilityType(_USPS_DELIVERY);
    public java.lang.String getValue() { return _value_;}
    public static GroundDeliveryEligibilityType fromValue(java.lang.String value)
          throws java.lang.RuntimeException {
        GroundDeliveryEligibilityType enumX = (GroundDeliveryEligibilityType)
            _table_.get(value);
        if (enumX==null) throw new java.lang.RuntimeException(getEnumErrorMsg(value));
        return enumX;
    }
    public static GroundDeliveryEligibilityType fromString(java.lang.String value)
          throws java.lang.RuntimeException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static java.lang.String getEnumErrorMsg(Object value) 
	{ 
		return "'" + value + "' is not a valid GroundDeliveryEligibilityType value."; 
	}
}
