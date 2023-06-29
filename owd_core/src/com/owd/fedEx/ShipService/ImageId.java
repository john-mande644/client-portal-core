/*
 * ImageId.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;

public class ImageId implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected ImageId(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _IMAGE_1 = "IMAGE_1";
    public static final java.lang.String _IMAGE_2 = "IMAGE_2";
    public static final java.lang.String _IMAGE_3 = "IMAGE_3";
    public static final java.lang.String _IMAGE_4 = "IMAGE_4";
    public static final java.lang.String _IMAGE_5 = "IMAGE_5";
    public static final ImageId IMAGE_1 = new ImageId(_IMAGE_1);
    public static final ImageId IMAGE_2 = new ImageId(_IMAGE_2);
    public static final ImageId IMAGE_3 = new ImageId(_IMAGE_3);
    public static final ImageId IMAGE_4 = new ImageId(_IMAGE_4);
    public static final ImageId IMAGE_5 = new ImageId(_IMAGE_5);
    public java.lang.String getValue() { return _value_;}
    public static ImageId fromValue(java.lang.String value)
          throws java.lang.RuntimeException {
        ImageId enumX = (ImageId)
            _table_.get(value);
        if (enumX==null) throw new java.lang.RuntimeException(getEnumErrorMsg(value));
        return enumX;
    }
    public static ImageId fromString(java.lang.String value)
          throws java.lang.RuntimeException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static java.lang.String getEnumErrorMsg(Object value) 
	{ 
		return "'" + value + "' is not a valid ImageId value."; 
	}
}
