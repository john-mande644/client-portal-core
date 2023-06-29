/*
 * NaftaProducerSpecificationType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;

public class NaftaProducerSpecificationType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected NaftaProducerSpecificationType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _AVAILABLE_UPON_REQUEST = "AVAILABLE_UPON_REQUEST";
    public static final java.lang.String _MULTIPLE_SPECIFIED = "MULTIPLE_SPECIFIED";
    public static final java.lang.String _SAME = "SAME";
    public static final java.lang.String _SINGLE_SPECIFIED = "SINGLE_SPECIFIED";
    public static final java.lang.String _UNKNOWN = "UNKNOWN";
    public static final NaftaProducerSpecificationType AVAILABLE_UPON_REQUEST = new NaftaProducerSpecificationType(_AVAILABLE_UPON_REQUEST);
    public static final NaftaProducerSpecificationType MULTIPLE_SPECIFIED = new NaftaProducerSpecificationType(_MULTIPLE_SPECIFIED);
    public static final NaftaProducerSpecificationType SAME = new NaftaProducerSpecificationType(_SAME);
    public static final NaftaProducerSpecificationType SINGLE_SPECIFIED = new NaftaProducerSpecificationType(_SINGLE_SPECIFIED);
    public static final NaftaProducerSpecificationType UNKNOWN = new NaftaProducerSpecificationType(_UNKNOWN);
    public java.lang.String getValue() { return _value_;}
    public static NaftaProducerSpecificationType fromValue(java.lang.String value)
          throws java.lang.RuntimeException {
        NaftaProducerSpecificationType enumX = (NaftaProducerSpecificationType)
            _table_.get(value);
        if (enumX==null) throw new java.lang.RuntimeException(getEnumErrorMsg(value));
        return enumX;
    }
    public static NaftaProducerSpecificationType fromString(java.lang.String value)
          throws java.lang.RuntimeException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static java.lang.String getEnumErrorMsg(Object value) 
	{ 
		return "'" + value + "' is not a valid NaftaProducerSpecificationType value."; 
	}
}
