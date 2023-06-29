/*
 * DeletionControlType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;


/**
 * Specifies the type of deletion to be performed on a shipment.
 */
public class DeletionControlType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected DeletionControlType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _DELETE_ALL_PACKAGES = "DELETE_ALL_PACKAGES";
    public static final java.lang.String _DELETE_ENTIRE_CONSOLIDATION = "DELETE_ENTIRE_CONSOLIDATION";
    public static final java.lang.String _DELETE_ONE_PACKAGE = "DELETE_ONE_PACKAGE";
    public static final java.lang.String _LEGACY = "LEGACY";
    public static final DeletionControlType DELETE_ALL_PACKAGES = new DeletionControlType(_DELETE_ALL_PACKAGES);
    public static final DeletionControlType DELETE_ENTIRE_CONSOLIDATION = new DeletionControlType(_DELETE_ENTIRE_CONSOLIDATION);
    public static final DeletionControlType DELETE_ONE_PACKAGE = new DeletionControlType(_DELETE_ONE_PACKAGE);
    public static final DeletionControlType LEGACY = new DeletionControlType(_LEGACY);
    public java.lang.String getValue() { return _value_;}
    public static DeletionControlType fromValue(java.lang.String value)
          throws java.lang.RuntimeException {
        DeletionControlType enumX = (DeletionControlType)
            _table_.get(value);
        if (enumX==null) throw new java.lang.RuntimeException(getEnumErrorMsg(value));
        return enumX;
    }
    public static DeletionControlType fromString(java.lang.String value)
          throws java.lang.RuntimeException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static java.lang.String getEnumErrorMsg(Object value) 
	{ 
		return "'" + value + "' is not a valid DeletionControlType value."; 
	}
}
