/*
 * NaftaProducerDeterminationCode.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;


/**
 * See instructions for NAFTA Certificate of Origin for code definitions.
 */
public class NaftaProducerDeterminationCode implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected NaftaProducerDeterminationCode(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _NO_1 = "NO_1";
    public static final java.lang.String _NO_2 = "NO_2";
    public static final java.lang.String _NO_3 = "NO_3";
    public static final java.lang.String _YES = "YES";
    public static final NaftaProducerDeterminationCode NO_1 = new NaftaProducerDeterminationCode(_NO_1);
    public static final NaftaProducerDeterminationCode NO_2 = new NaftaProducerDeterminationCode(_NO_2);
    public static final NaftaProducerDeterminationCode NO_3 = new NaftaProducerDeterminationCode(_NO_3);
    public static final NaftaProducerDeterminationCode YES = new NaftaProducerDeterminationCode(_YES);
    public java.lang.String getValue() { return _value_;}
    public static NaftaProducerDeterminationCode fromValue(java.lang.String value)
          throws java.lang.RuntimeException {
        NaftaProducerDeterminationCode enumX = (NaftaProducerDeterminationCode)
            _table_.get(value);
        if (enumX==null) throw new java.lang.RuntimeException(getEnumErrorMsg(value));
        return enumX;
    }
    public static NaftaProducerDeterminationCode fromString(java.lang.String value)
          throws java.lang.RuntimeException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static java.lang.String getEnumErrorMsg(Object value) 
	{ 
		return "'" + value + "' is not a valid NaftaProducerDeterminationCode value."; 
	}
}
