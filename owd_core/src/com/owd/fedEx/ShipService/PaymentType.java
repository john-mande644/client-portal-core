/*
 * PaymentType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;

public class PaymentType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected PaymentType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _ACCOUNT = "ACCOUNT";
    public static final java.lang.String _COLLECT = "COLLECT";
    public static final java.lang.String _RECIPIENT = "RECIPIENT";
    public static final java.lang.String _SENDER = "SENDER";
    public static final java.lang.String _THIRD_PARTY = "THIRD_PARTY";
    public static final PaymentType ACCOUNT = new PaymentType(_ACCOUNT);
    public static final PaymentType COLLECT = new PaymentType(_COLLECT);
    public static final PaymentType RECIPIENT = new PaymentType(_RECIPIENT);
    public static final PaymentType SENDER = new PaymentType(_SENDER);
    public static final PaymentType THIRD_PARTY = new PaymentType(_THIRD_PARTY);
    public java.lang.String getValue() { return _value_;}
    public static PaymentType fromValue(java.lang.String value)
          throws java.lang.RuntimeException {
        PaymentType enumX = (PaymentType)
            _table_.get(value);
        if (enumX==null) throw new java.lang.RuntimeException(getEnumErrorMsg(value));
        return enumX;
    }
    public static PaymentType fromString(java.lang.String value)
          throws java.lang.RuntimeException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static java.lang.String getEnumErrorMsg(Object value) 
	{ 
		return "'" + value + "' is not a valid PaymentType value."; 
	}
}
