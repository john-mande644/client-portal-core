/*
 * B13AFilingOptionType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;

public class B13AFilingOptionType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected B13AFilingOptionType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _FEDEX_TO_STAMP = "FEDEX_TO_STAMP";
    public static final java.lang.String _FILED_ELECTRONICALLY = "FILED_ELECTRONICALLY";
    public static final java.lang.String _MANUALLY_ATTACHED = "MANUALLY_ATTACHED";
    public static final java.lang.String _NOT_REQUIRED = "NOT_REQUIRED";
    public static final java.lang.String _SUMMARY_REPORTING = "SUMMARY_REPORTING";
    public static final B13AFilingOptionType FEDEX_TO_STAMP = new B13AFilingOptionType(_FEDEX_TO_STAMP);
    public static final B13AFilingOptionType FILED_ELECTRONICALLY = new B13AFilingOptionType(_FILED_ELECTRONICALLY);
    public static final B13AFilingOptionType MANUALLY_ATTACHED = new B13AFilingOptionType(_MANUALLY_ATTACHED);
    public static final B13AFilingOptionType NOT_REQUIRED = new B13AFilingOptionType(_NOT_REQUIRED);
    public static final B13AFilingOptionType SUMMARY_REPORTING = new B13AFilingOptionType(_SUMMARY_REPORTING);
    public java.lang.String getValue() { return _value_;}
    public static B13AFilingOptionType fromValue(java.lang.String value)
          throws java.lang.RuntimeException {
        B13AFilingOptionType enumX = (B13AFilingOptionType)
            _table_.get(value);
        if (enumX==null) throw new java.lang.RuntimeException(getEnumErrorMsg(value));
        return enumX;
    }
    public static B13AFilingOptionType fromString(java.lang.String value)
          throws java.lang.RuntimeException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static java.lang.String getEnumErrorMsg(Object value) 
	{ 
		return "'" + value + "' is not a valid B13AFilingOptionType value."; 
	}
}
