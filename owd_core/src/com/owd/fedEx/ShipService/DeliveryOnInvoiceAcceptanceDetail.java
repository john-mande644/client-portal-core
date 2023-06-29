/*
 * DeliveryOnInvoiceAcceptanceDetail.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;

public class DeliveryOnInvoiceAcceptanceDetail  implements java.io.Serializable {
    private com.owd.fedEx.ShipService.Party recipient;
    private com.owd.fedEx.ShipService.TrackingId trackingId;

    public DeliveryOnInvoiceAcceptanceDetail() {
    }


    /**
     * Gets the recipient value for this DeliveryOnInvoiceAcceptanceDetail.
     * 
     * @return recipient
     */
    public com.owd.fedEx.ShipService.Party getRecipient() {
        return recipient;
    }


    /**
     * Sets the recipient value for this DeliveryOnInvoiceAcceptanceDetail.
     * 
     * @param recipient
     */
    public void setRecipient(com.owd.fedEx.ShipService.Party recipient) {
        this.recipient = recipient;
    }


    /**
     * Gets the trackingId value for this DeliveryOnInvoiceAcceptanceDetail.
     * 
     * @return trackingId
     */
    public com.owd.fedEx.ShipService.TrackingId getTrackingId() {
        return trackingId;
    }


    /**
     * Sets the trackingId value for this DeliveryOnInvoiceAcceptanceDetail.
     * 
     * @param trackingId
     */
    public void setTrackingId(com.owd.fedEx.ShipService.TrackingId trackingId) {
        this.trackingId = trackingId;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DeliveryOnInvoiceAcceptanceDetail)) return false;
        DeliveryOnInvoiceAcceptanceDetail other = (DeliveryOnInvoiceAcceptanceDetail) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.recipient==null && other.getRecipient()==null) || 
             (this.recipient!=null &&
              this.recipient.equals(other.getRecipient()))) &&
            ((this.trackingId==null && other.getTrackingId()==null) || 
             (this.trackingId!=null &&
              this.trackingId.equals(other.getTrackingId())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getRecipient() != null) {
            _hashCode += getRecipient().hashCode();
        }
        if (getTrackingId() != null) {
            _hashCode += getTrackingId().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DeliveryOnInvoiceAcceptanceDetail.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "DeliveryOnInvoiceAcceptanceDetail"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("recipient");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Recipient"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Party"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("trackingId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "TrackingId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "TrackingId"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
