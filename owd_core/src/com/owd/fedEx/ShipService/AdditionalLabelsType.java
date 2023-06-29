/*
 * AdditionalLabelsType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;

public class AdditionalLabelsType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected AdditionalLabelsType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _BROKER = "BROKER";
    public static final java.lang.String _CONSIGNEE = "CONSIGNEE";
    public static final java.lang.String _CUSTOMS = "CUSTOMS";
    public static final java.lang.String _DESTINATION = "DESTINATION";
    public static final java.lang.String _FREIGHT_REFERENCE = "FREIGHT_REFERENCE";
    public static final java.lang.String _MANIFEST = "MANIFEST";
    public static final java.lang.String _ORIGIN = "ORIGIN";
    public static final java.lang.String _RECIPIENT = "RECIPIENT";
    public static final java.lang.String _SHIPPER = "SHIPPER";
    public static final AdditionalLabelsType BROKER = new AdditionalLabelsType(_BROKER);
    public static final AdditionalLabelsType CONSIGNEE = new AdditionalLabelsType(_CONSIGNEE);
    public static final AdditionalLabelsType CUSTOMS = new AdditionalLabelsType(_CUSTOMS);
    public static final AdditionalLabelsType DESTINATION = new AdditionalLabelsType(_DESTINATION);
    public static final AdditionalLabelsType FREIGHT_REFERENCE = new AdditionalLabelsType(_FREIGHT_REFERENCE);
    public static final AdditionalLabelsType MANIFEST = new AdditionalLabelsType(_MANIFEST);
    public static final AdditionalLabelsType ORIGIN = new AdditionalLabelsType(_ORIGIN);
    public static final AdditionalLabelsType RECIPIENT = new AdditionalLabelsType(_RECIPIENT);
    public static final AdditionalLabelsType SHIPPER = new AdditionalLabelsType(_SHIPPER);
    public java.lang.String getValue() { return _value_;}
    public static AdditionalLabelsType fromValue(java.lang.String value)
          throws java.lang.RuntimeException {
        AdditionalLabelsType enumX = (AdditionalLabelsType)
            _table_.get(value);
        if (enumX==null) throw new java.lang.RuntimeException(getEnumErrorMsg(value));
        return enumX;
    }
    public static AdditionalLabelsType fromString(java.lang.String value)
          throws java.lang.RuntimeException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static java.lang.String getEnumErrorMsg(Object value) 
	{ 
		return "'" + value + "' is not a valid AdditionalLabelsType value."; 
	}
}
