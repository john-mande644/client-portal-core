/*
 * AlcoholDetail.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;


/**
 * Specifies details for a package containing alcohol
 */
public class AlcoholDetail  implements java.io.Serializable {
    /** Specifies the type of license that the recipient of the alcohol
 * shipment has. */
    private com.owd.fedEx.ShipService.AlcoholRecipientType recipientType;

    public AlcoholDetail() {
    }


    /**
     * Gets the recipientType value for this AlcoholDetail.
     * 
     * @return recipientType Specifies the type of license that the recipient of the alcohol
 * shipment has.
     */
    public com.owd.fedEx.ShipService.AlcoholRecipientType getRecipientType() {
        return recipientType;
    }


    /**
     * Sets the recipientType value for this AlcoholDetail.
     * 
     * @param recipientType Specifies the type of license that the recipient of the alcohol
 * shipment has.
     */
    public void setRecipientType(com.owd.fedEx.ShipService.AlcoholRecipientType recipientType) {
        this.recipientType = recipientType;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AlcoholDetail)) return false;
        AlcoholDetail other = (AlcoholDetail) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.recipientType==null && other.getRecipientType()==null) || 
             (this.recipientType!=null &&
              this.recipientType.equals(other.getRecipientType())));
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
        if (getRecipientType() != null) {
            _hashCode += getRecipientType().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AlcoholDetail.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "AlcoholDetail"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("recipientType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "RecipientType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "AlcoholRecipientType"));
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
