/*
 * ShippingDocumentEMailDetail.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;


/**
 * Specifies how to e-mail shipping documents.
 */
public class ShippingDocumentEMailDetail  implements java.io.Serializable {
    /** Provides the roles and email addresses for e-mail recipients. */
    private com.owd.fedEx.ShipService.ShippingDocumentEMailRecipient[] EMailRecipients;
    private com.owd.fedEx.ShipService.ShippingDocumentEMailGroupingType grouping;
    /** Identifies the representation of human-readable text. */
    private com.owd.fedEx.ShipService.Localization localization;

    public ShippingDocumentEMailDetail() {
    }


    /**
     * Gets the EMailRecipients value for this ShippingDocumentEMailDetail.
     * 
     * @return EMailRecipients Provides the roles and email addresses for e-mail recipients.
     */
    public com.owd.fedEx.ShipService.ShippingDocumentEMailRecipient[] getEMailRecipients() {
        return EMailRecipients;
    }


    /**
     * Sets the EMailRecipients value for this ShippingDocumentEMailDetail.
     * 
     * @param EMailRecipients Provides the roles and email addresses for e-mail recipients.
     */
    public void setEMailRecipients(com.owd.fedEx.ShipService.ShippingDocumentEMailRecipient[] EMailRecipients) {
        this.EMailRecipients = EMailRecipients;
    }

    public com.owd.fedEx.ShipService.ShippingDocumentEMailRecipient getEMailRecipients(int i) {
        return EMailRecipients[i];
    }

    public void setEMailRecipients(int i, com.owd.fedEx.ShipService.ShippingDocumentEMailRecipient value) {
        this.EMailRecipients[i] = value;
    }


    /**
     * Gets the grouping value for this ShippingDocumentEMailDetail.
     * 
     * @return grouping
     */
    public com.owd.fedEx.ShipService.ShippingDocumentEMailGroupingType getGrouping() {
        return grouping;
    }


    /**
     * Sets the grouping value for this ShippingDocumentEMailDetail.
     * 
     * @param grouping
     */
    public void setGrouping(com.owd.fedEx.ShipService.ShippingDocumentEMailGroupingType grouping) {
        this.grouping = grouping;
    }


    /**
     * Gets the localization value for this ShippingDocumentEMailDetail.
     * 
     * @return localization Identifies the representation of human-readable text.
     */
    public com.owd.fedEx.ShipService.Localization getLocalization() {
        return localization;
    }


    /**
     * Sets the localization value for this ShippingDocumentEMailDetail.
     * 
     * @param localization Identifies the representation of human-readable text.
     */
    public void setLocalization(com.owd.fedEx.ShipService.Localization localization) {
        this.localization = localization;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ShippingDocumentEMailDetail)) return false;
        ShippingDocumentEMailDetail other = (ShippingDocumentEMailDetail) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.EMailRecipients==null && other.getEMailRecipients()==null) || 
             (this.EMailRecipients!=null &&
              java.util.Arrays.equals(this.EMailRecipients, other.getEMailRecipients()))) &&
            ((this.grouping==null && other.getGrouping()==null) || 
             (this.grouping!=null &&
              this.grouping.equals(other.getGrouping()))) &&
            ((this.localization==null && other.getLocalization()==null) || 
             (this.localization!=null &&
              this.localization.equals(other.getLocalization())));
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
        if (getEMailRecipients() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getEMailRecipients());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getEMailRecipients(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getGrouping() != null) {
            _hashCode += getGrouping().hashCode();
        }
        if (getLocalization() != null) {
            _hashCode += getLocalization().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ShippingDocumentEMailDetail.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ShippingDocumentEMailDetail"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("EMailRecipients");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "EMailRecipients"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ShippingDocumentEMailRecipient"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("grouping");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Grouping"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ShippingDocumentEMailGroupingType"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("localization");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Localization"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Localization"));
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
