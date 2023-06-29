/*
 * NotificationSeverityType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;

public class NotificationSeverityType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected NotificationSeverityType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _ERROR = "ERROR";
    public static final java.lang.String _FAILURE = "FAILURE";
    public static final java.lang.String _NOTE = "NOTE";
    public static final java.lang.String _SUCCESS = "SUCCESS";
    public static final java.lang.String _WARNING = "WARNING";
    public static final NotificationSeverityType ERROR = new NotificationSeverityType(_ERROR);
    public static final NotificationSeverityType FAILURE = new NotificationSeverityType(_FAILURE);
    public static final NotificationSeverityType NOTE = new NotificationSeverityType(_NOTE);
    public static final NotificationSeverityType SUCCESS = new NotificationSeverityType(_SUCCESS);
    public static final NotificationSeverityType WARNING = new NotificationSeverityType(_WARNING);
    public java.lang.String getValue() { return _value_;}
    public static NotificationSeverityType fromValue(java.lang.String value)
          throws java.lang.RuntimeException {
        NotificationSeverityType enumX = (NotificationSeverityType)
            _table_.get(value);
        if (enumX==null) throw new java.lang.RuntimeException(getEnumErrorMsg(value));
        return enumX;
    }
    public static NotificationSeverityType fromString(java.lang.String value)
          throws java.lang.RuntimeException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static java.lang.String getEnumErrorMsg(Object value) 
	{ 
		return "'" + value + "' is not a valid NotificationSeverityType value."; 
	}
}
