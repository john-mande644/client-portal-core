/*
 * NetExplosiveClassificationType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;

public class NetExplosiveClassificationType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected NetExplosiveClassificationType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _NET_EXPLOSIVE_CONTENT = "NET_EXPLOSIVE_CONTENT";
    public static final java.lang.String _NET_EXPLOSIVE_MASS = "NET_EXPLOSIVE_MASS";
    public static final java.lang.String _NET_EXPLOSIVE_QUANTITY = "NET_EXPLOSIVE_QUANTITY";
    public static final java.lang.String _NET_EXPLOSIVE_WEIGHT = "NET_EXPLOSIVE_WEIGHT";
    public static final NetExplosiveClassificationType NET_EXPLOSIVE_CONTENT = new NetExplosiveClassificationType(_NET_EXPLOSIVE_CONTENT);
    public static final NetExplosiveClassificationType NET_EXPLOSIVE_MASS = new NetExplosiveClassificationType(_NET_EXPLOSIVE_MASS);
    public static final NetExplosiveClassificationType NET_EXPLOSIVE_QUANTITY = new NetExplosiveClassificationType(_NET_EXPLOSIVE_QUANTITY);
    public static final NetExplosiveClassificationType NET_EXPLOSIVE_WEIGHT = new NetExplosiveClassificationType(_NET_EXPLOSIVE_WEIGHT);
    public java.lang.String getValue() { return _value_;}
    public static NetExplosiveClassificationType fromValue(java.lang.String value)
          throws java.lang.RuntimeException {
        NetExplosiveClassificationType enumX = (NetExplosiveClassificationType)
            _table_.get(value);
        if (enumX==null) throw new java.lang.RuntimeException(getEnumErrorMsg(value));
        return enumX;
    }
    public static NetExplosiveClassificationType fromString(java.lang.String value)
          throws java.lang.RuntimeException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static java.lang.String getEnumErrorMsg(Object value) 
	{ 
		return "'" + value + "' is not a valid NetExplosiveClassificationType value."; 
	}
}
