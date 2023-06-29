/*
 * NaftaImporterSpecificationType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;

public class NaftaImporterSpecificationType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected NaftaImporterSpecificationType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _IMPORTER_OF_RECORD = "IMPORTER_OF_RECORD";
    public static final java.lang.String _RECIPIENT = "RECIPIENT";
    public static final java.lang.String _UNKNOWN = "UNKNOWN";
    public static final java.lang.String _VARIOUS = "VARIOUS";
    public static final NaftaImporterSpecificationType IMPORTER_OF_RECORD = new NaftaImporterSpecificationType(_IMPORTER_OF_RECORD);
    public static final NaftaImporterSpecificationType RECIPIENT = new NaftaImporterSpecificationType(_RECIPIENT);
    public static final NaftaImporterSpecificationType UNKNOWN = new NaftaImporterSpecificationType(_UNKNOWN);
    public static final NaftaImporterSpecificationType VARIOUS = new NaftaImporterSpecificationType(_VARIOUS);
    public java.lang.String getValue() { return _value_;}
    public static NaftaImporterSpecificationType fromValue(java.lang.String value)
          throws java.lang.RuntimeException {
        NaftaImporterSpecificationType enumX = (NaftaImporterSpecificationType)
            _table_.get(value);
        if (enumX==null) throw new java.lang.RuntimeException(getEnumErrorMsg(value));
        return enumX;
    }
    public static NaftaImporterSpecificationType fromString(java.lang.String value)
          throws java.lang.RuntimeException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static java.lang.String getEnumErrorMsg(Object value) 
	{ 
		return "'" + value + "' is not a valid NaftaImporterSpecificationType value."; 
	}
}
