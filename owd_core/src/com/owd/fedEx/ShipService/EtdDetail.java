/*
 * EtdDetail.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;


/**
 * Electronic Trade document references used with the ETD special
 * service.
 */
public class EtdDetail  implements java.io.Serializable {
    private com.owd.fedEx.ShipService.EtdAttributeType[] attributes;
    /** Indicates the types of shipping documents produced for the shipper
 * by FedEx (see ShippingDocumentSpecification) which should be copied
 * back to the shipper in the shipment result data. */
    private com.owd.fedEx.ShipService.RequestedShippingDocumentType[] requestedDocumentCopies;
    private com.owd.fedEx.ShipService.UploadDocumentReferenceDetail[] documentReferences;

    public EtdDetail() {
    }


    /**
     * Gets the attributes value for this EtdDetail.
     * 
     * @return attributes
     */
    public com.owd.fedEx.ShipService.EtdAttributeType[] getAttributes() {
        return attributes;
    }


    /**
     * Sets the attributes value for this EtdDetail.
     * 
     * @param attributes
     */
    public void setAttributes(com.owd.fedEx.ShipService.EtdAttributeType[] attributes) {
        this.attributes = attributes;
    }

    public com.owd.fedEx.ShipService.EtdAttributeType getAttributes(int i) {
        return attributes[i];
    }

    public void setAttributes(int i, com.owd.fedEx.ShipService.EtdAttributeType value) {
        this.attributes[i] = value;
    }


    /**
     * Gets the requestedDocumentCopies value for this EtdDetail.
     * 
     * @return requestedDocumentCopies Indicates the types of shipping documents produced for the shipper
 * by FedEx (see ShippingDocumentSpecification) which should be copied
 * back to the shipper in the shipment result data.
     */
    public com.owd.fedEx.ShipService.RequestedShippingDocumentType[] getRequestedDocumentCopies() {
        return requestedDocumentCopies;
    }


    /**
     * Sets the requestedDocumentCopies value for this EtdDetail.
     * 
     * @param requestedDocumentCopies Indicates the types of shipping documents produced for the shipper
 * by FedEx (see ShippingDocumentSpecification) which should be copied
 * back to the shipper in the shipment result data.
     */
    public void setRequestedDocumentCopies(com.owd.fedEx.ShipService.RequestedShippingDocumentType[] requestedDocumentCopies) {
        this.requestedDocumentCopies = requestedDocumentCopies;
    }

    public com.owd.fedEx.ShipService.RequestedShippingDocumentType getRequestedDocumentCopies(int i) {
        return requestedDocumentCopies[i];
    }

    public void setRequestedDocumentCopies(int i, com.owd.fedEx.ShipService.RequestedShippingDocumentType value) {
        this.requestedDocumentCopies[i] = value;
    }


    /**
     * Gets the documentReferences value for this EtdDetail.
     * 
     * @return documentReferences
     */
    public com.owd.fedEx.ShipService.UploadDocumentReferenceDetail[] getDocumentReferences() {
        return documentReferences;
    }


    /**
     * Sets the documentReferences value for this EtdDetail.
     * 
     * @param documentReferences
     */
    public void setDocumentReferences(com.owd.fedEx.ShipService.UploadDocumentReferenceDetail[] documentReferences) {
        this.documentReferences = documentReferences;
    }

    public com.owd.fedEx.ShipService.UploadDocumentReferenceDetail getDocumentReferences(int i) {
        return documentReferences[i];
    }

    public void setDocumentReferences(int i, com.owd.fedEx.ShipService.UploadDocumentReferenceDetail value) {
        this.documentReferences[i] = value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof EtdDetail)) return false;
        EtdDetail other = (EtdDetail) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.attributes==null && other.getAttributes()==null) || 
             (this.attributes!=null &&
              java.util.Arrays.equals(this.attributes, other.getAttributes()))) &&
            ((this.requestedDocumentCopies==null && other.getRequestedDocumentCopies()==null) || 
             (this.requestedDocumentCopies!=null &&
              java.util.Arrays.equals(this.requestedDocumentCopies, other.getRequestedDocumentCopies()))) &&
            ((this.documentReferences==null && other.getDocumentReferences()==null) || 
             (this.documentReferences!=null &&
              java.util.Arrays.equals(this.documentReferences, other.getDocumentReferences())));
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
        if (getAttributes() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getAttributes());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getAttributes(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getRequestedDocumentCopies() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getRequestedDocumentCopies());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getRequestedDocumentCopies(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getDocumentReferences() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getDocumentReferences());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getDocumentReferences(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(EtdDetail.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "EtdDetail"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("attributes");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Attributes"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "EtdAttributeType"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("requestedDocumentCopies");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "RequestedDocumentCopies"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "RequestedShippingDocumentType"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("documentReferences");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "DocumentReferences"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "UploadDocumentReferenceDetail"));
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
