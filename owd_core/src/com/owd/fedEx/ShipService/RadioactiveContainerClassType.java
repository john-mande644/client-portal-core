/*
 * RadioactiveContainerClassType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;


/**
 * Indicates the packaging type of the container used to package radioactive
 * hazardous materials.
 */
public class RadioactiveContainerClassType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected RadioactiveContainerClassType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _EXCEPTED_PACKAGE = "EXCEPTED_PACKAGE";
    public static final java.lang.String _INDUSTRIAL_IP1 = "INDUSTRIAL_IP1";
    public static final java.lang.String _INDUSTRIAL_IP2 = "INDUSTRIAL_IP2";
    public static final java.lang.String _INDUSTRIAL_IP3 = "INDUSTRIAL_IP3";
    public static final java.lang.String _TYPE_A = "TYPE_A";
    public static final java.lang.String _TYPE_B_M = "TYPE_B_M";
    public static final java.lang.String _TYPE_B_U = "TYPE_B_U";
    public static final java.lang.String _TYPE_C = "TYPE_C";
    public static final RadioactiveContainerClassType EXCEPTED_PACKAGE = new RadioactiveContainerClassType(_EXCEPTED_PACKAGE);
    public static final RadioactiveContainerClassType INDUSTRIAL_IP1 = new RadioactiveContainerClassType(_INDUSTRIAL_IP1);
    public static final RadioactiveContainerClassType INDUSTRIAL_IP2 = new RadioactiveContainerClassType(_INDUSTRIAL_IP2);
    public static final RadioactiveContainerClassType INDUSTRIAL_IP3 = new RadioactiveContainerClassType(_INDUSTRIAL_IP3);
    public static final RadioactiveContainerClassType TYPE_A = new RadioactiveContainerClassType(_TYPE_A);
    public static final RadioactiveContainerClassType TYPE_B_M = new RadioactiveContainerClassType(_TYPE_B_M);
    public static final RadioactiveContainerClassType TYPE_B_U = new RadioactiveContainerClassType(_TYPE_B_U);
    public static final RadioactiveContainerClassType TYPE_C = new RadioactiveContainerClassType(_TYPE_C);
    public java.lang.String getValue() { return _value_;}
    public static RadioactiveContainerClassType fromValue(java.lang.String value)
          throws java.lang.RuntimeException {
        RadioactiveContainerClassType enumX = (RadioactiveContainerClassType)
            _table_.get(value);
        if (enumX==null) throw new java.lang.RuntimeException(getEnumErrorMsg(value));
        return enumX;
    }
    public static RadioactiveContainerClassType fromString(java.lang.String value)
          throws java.lang.RuntimeException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static java.lang.String getEnumErrorMsg(Object value) 
	{ 
		return "'" + value + "' is not a valid RadioactiveContainerClassType value."; 
	}
}
