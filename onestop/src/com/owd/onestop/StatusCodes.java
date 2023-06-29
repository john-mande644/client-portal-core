/**
 * StatusCodes.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.owd.onestop;

public class StatusCodes implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected StatusCodes(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _Null = "Null";
    public static final java.lang.String _StatusCode_Placed = "StatusCode_Placed";
    public static final java.lang.String _StatusCode_InProgress = "StatusCode_InProgress";
    public static final java.lang.String _StatusCode_Shipped = "StatusCode_Shipped";
    public static final java.lang.String _StatusCode_Cancelled = "StatusCode_Cancelled";
    public static final java.lang.String _StatusCode_Picked = "StatusCode_Picked";
    public static final java.lang.String _StatusCode_Pending = "StatusCode_Pending";
    public static final java.lang.String _StatusCode_Priority_Placed = "StatusCode_Priority_Placed";
    public static final java.lang.String _StatusCode_Pending_Authorize = "StatusCode_Pending_Authorize";
    public static final java.lang.String _StatusCode_PickHold = "StatusCode_PickHold";
    public static final java.lang.String _StatusCode_PackHold = "StatusCode_PackHold";
    public static final java.lang.String _StatusCode_PaymentHold = "StatusCode_PaymentHold";
    public static final java.lang.String _StatusCode_Packed = "StatusCode_Packed";
    public static final java.lang.String _StatusCode_ShipCleared = "StatusCode_ShipCleared";
    public static final java.lang.String _StatusCode_TrackingHold = "StatusCode_TrackingHold";
    public static final java.lang.String _StatusCode_AddressHold = "StatusCode_AddressHold";
    public static final java.lang.String _StatusCode_Pending_Product = "StatusCode_Pending_Product";
    public static final java.lang.String _StatusCode_InProgress_Batch = "StatusCode_InProgress_Batch";
    public static final java.lang.String _StatusCode_Split = "StatusCode_Split";
    public static final java.lang.String _Technical_Hold = "Technical_Hold";
    public static final StatusCodes Null = new StatusCodes(_Null);
    public static final StatusCodes StatusCode_Placed = new StatusCodes(_StatusCode_Placed);
    public static final StatusCodes StatusCode_InProgress = new StatusCodes(_StatusCode_InProgress);
    public static final StatusCodes StatusCode_Shipped = new StatusCodes(_StatusCode_Shipped);
    public static final StatusCodes StatusCode_Cancelled = new StatusCodes(_StatusCode_Cancelled);
    public static final StatusCodes StatusCode_Picked = new StatusCodes(_StatusCode_Picked);
    public static final StatusCodes StatusCode_Pending = new StatusCodes(_StatusCode_Pending);
    public static final StatusCodes StatusCode_Priority_Placed = new StatusCodes(_StatusCode_Priority_Placed);
    public static final StatusCodes StatusCode_Pending_Authorize = new StatusCodes(_StatusCode_Pending_Authorize);
    public static final StatusCodes StatusCode_PickHold = new StatusCodes(_StatusCode_PickHold);
    public static final StatusCodes StatusCode_PackHold = new StatusCodes(_StatusCode_PackHold);
    public static final StatusCodes StatusCode_PaymentHold = new StatusCodes(_StatusCode_PaymentHold);
    public static final StatusCodes StatusCode_Packed = new StatusCodes(_StatusCode_Packed);
    public static final StatusCodes StatusCode_ShipCleared = new StatusCodes(_StatusCode_ShipCleared);
    public static final StatusCodes StatusCode_TrackingHold = new StatusCodes(_StatusCode_TrackingHold);
    public static final StatusCodes StatusCode_AddressHold = new StatusCodes(_StatusCode_AddressHold);
    public static final StatusCodes StatusCode_Pending_Product = new StatusCodes(_StatusCode_Pending_Product);
    public static final StatusCodes StatusCode_InProgress_Batch = new StatusCodes(_StatusCode_InProgress_Batch);
    public static final StatusCodes StatusCode_Split = new StatusCodes(_StatusCode_Split);
    public static final StatusCodes Technical_Hold = new StatusCodes(_Technical_Hold);
    public java.lang.String getValue() { return _value_;}
    public static StatusCodes fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        StatusCodes enumeration = (StatusCodes)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static StatusCodes fromString(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new org.apache.axis.encoding.ser.EnumSerializer(
            _javaType, _xmlType);
    }
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new org.apache.axis.encoding.ser.EnumDeserializer(
            _javaType, _xmlType);
    }
    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(StatusCodes.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/_onestop", "StatusCodes"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
