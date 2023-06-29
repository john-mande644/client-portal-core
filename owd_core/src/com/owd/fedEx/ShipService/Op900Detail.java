/*
 * Op900Detail.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;


/**
 * The instructions indicating how to print the OP-900 form for hazardous
 * materials packages.
 */
public class Op900Detail  implements java.io.Serializable {
    /** Specifies characteristics of a shipping document to be produced. */
    private com.owd.fedEx.ShipService.ShippingDocumentFormat format;
    private com.owd.fedEx.ShipService.CustomerReferenceType reference;
    /** Specifies the usage and identification of customer supplied images
 * to be used on this document. */
    private com.owd.fedEx.ShipService.CustomerImageUsage[] customerImageUsages;
    private java.lang.String signatureName;

    public Op900Detail() {
    }


    /**
     * Gets the format value for this Op900Detail.
     * 
     * @return format Specifies characteristics of a shipping document to be produced.
     */
    public com.owd.fedEx.ShipService.ShippingDocumentFormat getFormat() {
        return format;
    }


    /**
     * Sets the format value for this Op900Detail.
     * 
     * @param format Specifies characteristics of a shipping document to be produced.
     */
    public void setFormat(com.owd.fedEx.ShipService.ShippingDocumentFormat format) {
        this.format = format;
    }


    /**
     * Gets the reference value for this Op900Detail.
     * 
     * @return reference
     */
    public com.owd.fedEx.ShipService.CustomerReferenceType getReference() {
        return reference;
    }


    /**
     * Sets the reference value for this Op900Detail.
     * 
     * @param reference
     */
    public void setReference(com.owd.fedEx.ShipService.CustomerReferenceType reference) {
        this.reference = reference;
    }


    /**
     * Gets the customerImageUsages value for this Op900Detail.
     * 
     * @return customerImageUsages Specifies the usage and identification of customer supplied images
 * to be used on this document.
     */
    public com.owd.fedEx.ShipService.CustomerImageUsage[] getCustomerImageUsages() {
        return customerImageUsages;
    }


    /**
     * Sets the customerImageUsages value for this Op900Detail.
     * 
     * @param customerImageUsages Specifies the usage and identification of customer supplied images
 * to be used on this document.
     */
    public void setCustomerImageUsages(com.owd.fedEx.ShipService.CustomerImageUsage[] customerImageUsages) {
        this.customerImageUsages = customerImageUsages;
    }

    public com.owd.fedEx.ShipService.CustomerImageUsage getCustomerImageUsages(int i) {
        return customerImageUsages[i];
    }

    public void setCustomerImageUsages(int i, com.owd.fedEx.ShipService.CustomerImageUsage value) {
        this.customerImageUsages[i] = value;
    }


    /**
     * Gets the signatureName value for this Op900Detail.
     * 
     * @return signatureName
     */
    public java.lang.String getSignatureName() {
        return signatureName;
    }


    /**
     * Sets the signatureName value for this Op900Detail.
     * 
     * @param signatureName
     */
    public void setSignatureName(java.lang.String signatureName) {
        this.signatureName = signatureName;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Op900Detail)) return false;
        Op900Detail other = (Op900Detail) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.format==null && other.getFormat()==null) || 
             (this.format!=null &&
              this.format.equals(other.getFormat()))) &&
            ((this.reference==null && other.getReference()==null) || 
             (this.reference!=null &&
              this.reference.equals(other.getReference()))) &&
            ((this.customerImageUsages==null && other.getCustomerImageUsages()==null) || 
             (this.customerImageUsages!=null &&
              java.util.Arrays.equals(this.customerImageUsages, other.getCustomerImageUsages()))) &&
            ((this.signatureName==null && other.getSignatureName()==null) || 
             (this.signatureName!=null &&
              this.signatureName.equals(other.getSignatureName())));
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
        if (getFormat() != null) {
            _hashCode += getFormat().hashCode();
        }
        if (getReference() != null) {
            _hashCode += getReference().hashCode();
        }
        if (getCustomerImageUsages() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getCustomerImageUsages());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getCustomerImageUsages(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getSignatureName() != null) {
            _hashCode += getSignatureName().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Op900Detail.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Op900Detail"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("format");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Format"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ShippingDocumentFormat"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reference");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Reference"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "CustomerReferenceType"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("customerImageUsages");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "CustomerImageUsages"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "CustomerImageUsage"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("signatureName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "SignatureName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
