/*
 * CustomsDeclarationStatementType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;


/**
 * This indicates the different statements, declarations, acts, and
 * certifications that may apply to a shipment.
 */
public class CustomsDeclarationStatementType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected CustomsDeclarationStatementType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _NAFTA_LOW_VALUE = "NAFTA_LOW_VALUE";
    public static final CustomsDeclarationStatementType NAFTA_LOW_VALUE = new CustomsDeclarationStatementType(_NAFTA_LOW_VALUE);
    public java.lang.String getValue() { return _value_;}
    public static CustomsDeclarationStatementType fromValue(java.lang.String value)
          throws java.lang.RuntimeException {
        CustomsDeclarationStatementType enumX = (CustomsDeclarationStatementType)
            _table_.get(value);
        if (enumX==null) throw new java.lang.RuntimeException(getEnumErrorMsg(value));
        return enumX;
    }
    public static CustomsDeclarationStatementType fromString(java.lang.String value)
          throws java.lang.RuntimeException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static java.lang.String getEnumErrorMsg(Object value) 
	{ 
		return "'" + value + "' is not a valid CustomsDeclarationStatementType value."; 
	}
}
