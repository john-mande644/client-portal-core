/*
 * SignatureOptionType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;

public class SignatureOptionType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected SignatureOptionType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _ADULT = "ADULT";
    public static final java.lang.String _DIRECT = "DIRECT";
    public static final java.lang.String _INDIRECT = "INDIRECT";
    public static final java.lang.String _NO_SIGNATURE_REQUIRED = "NO_SIGNATURE_REQUIRED";
    public static final java.lang.String _SERVICE_DEFAULT = "SERVICE_DEFAULT";
    public static final SignatureOptionType ADULT = new SignatureOptionType(_ADULT);
    public static final SignatureOptionType DIRECT = new SignatureOptionType(_DIRECT);
    public static final SignatureOptionType INDIRECT = new SignatureOptionType(_INDIRECT);
    public static final SignatureOptionType NO_SIGNATURE_REQUIRED = new SignatureOptionType(_NO_SIGNATURE_REQUIRED);
    public static final SignatureOptionType SERVICE_DEFAULT = new SignatureOptionType(_SERVICE_DEFAULT);
    public java.lang.String getValue() { return _value_;}
    public static SignatureOptionType fromValue(java.lang.String value)
          throws java.lang.RuntimeException {
        SignatureOptionType enumX = (SignatureOptionType)
            _table_.get(value);
        if (enumX==null) throw new java.lang.RuntimeException(getEnumErrorMsg(value));
        return enumX;
    }
    public static SignatureOptionType fromString(java.lang.String value)
          throws java.lang.RuntimeException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static java.lang.String getEnumErrorMsg(Object value) 
	{ 
		return "'" + value + "' is not a valid SignatureOptionType value."; 
	}
}
