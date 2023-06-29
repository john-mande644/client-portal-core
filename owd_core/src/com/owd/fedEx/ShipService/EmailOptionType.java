/*
 * EmailOptionType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;

public class EmailOptionType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected EmailOptionType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _SUPPRESS_ACCESS_EMAILS = "SUPPRESS_ACCESS_EMAILS";
    public static final java.lang.String _SUPPRESS_ADDITIONAL_LANGUAGES = "SUPPRESS_ADDITIONAL_LANGUAGES";
    public static final EmailOptionType SUPPRESS_ACCESS_EMAILS = new EmailOptionType(_SUPPRESS_ACCESS_EMAILS);
    public static final EmailOptionType SUPPRESS_ADDITIONAL_LANGUAGES = new EmailOptionType(_SUPPRESS_ADDITIONAL_LANGUAGES);
    public java.lang.String getValue() { return _value_;}
    public static EmailOptionType fromValue(java.lang.String value)
          throws java.lang.RuntimeException {
        EmailOptionType enumX = (EmailOptionType)
            _table_.get(value);
        if (enumX==null) throw new java.lang.RuntimeException(getEnumErrorMsg(value));
        return enumX;
    }
    public static EmailOptionType fromString(java.lang.String value)
          throws java.lang.RuntimeException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static java.lang.String getEnumErrorMsg(Object value) 
	{ 
		return "'" + value + "' is not a valid EmailOptionType value."; 
	}
}
