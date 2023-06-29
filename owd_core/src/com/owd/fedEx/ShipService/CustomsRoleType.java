/*
 * CustomsRoleType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;

public class CustomsRoleType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected CustomsRoleType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _EXPORTER = "EXPORTER";
    public static final java.lang.String _IMPORTER = "IMPORTER";
    public static final java.lang.String _LEGAL_AGENT = "LEGAL_AGENT";
    public static final java.lang.String _PRODUCER = "PRODUCER";
    public static final CustomsRoleType EXPORTER = new CustomsRoleType(_EXPORTER);
    public static final CustomsRoleType IMPORTER = new CustomsRoleType(_IMPORTER);
    public static final CustomsRoleType LEGAL_AGENT = new CustomsRoleType(_LEGAL_AGENT);
    public static final CustomsRoleType PRODUCER = new CustomsRoleType(_PRODUCER);
    public java.lang.String getValue() { return _value_;}
    public static CustomsRoleType fromValue(java.lang.String value)
          throws java.lang.RuntimeException {
        CustomsRoleType enumX = (CustomsRoleType)
            _table_.get(value);
        if (enumX==null) throw new java.lang.RuntimeException(getEnumErrorMsg(value));
        return enumX;
    }
    public static CustomsRoleType fromString(java.lang.String value)
          throws java.lang.RuntimeException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static java.lang.String getEnumErrorMsg(Object value) 
	{ 
		return "'" + value + "' is not a valid CustomsRoleType value."; 
	}
}
