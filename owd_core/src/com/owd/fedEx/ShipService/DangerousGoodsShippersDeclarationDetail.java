/*
 * DangerousGoodsShippersDeclarationDetail.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;


/**
 * The instructions indicating how to print the 1421c form for dangerous
 * goods shipment.
 */
public class DangerousGoodsShippersDeclarationDetail  implements java.io.Serializable {
    /** Specifies characteristics of a shipping document to be produced. */
    private com.owd.fedEx.ShipService.ShippingDocumentFormat format;
    /** Specifies the usage and identification of customer supplied images
 * to be used on this document. */
    private com.owd.fedEx.ShipService.CustomerImageUsage[] customerImageUsages;

    public DangerousGoodsShippersDeclarationDetail() {
    }


    /**
     * Gets the format value for this DangerousGoodsShippersDeclarationDetail.
     * 
     * @return format Specifies characteristics of a shipping document to be produced.
     */
    public com.owd.fedEx.ShipService.ShippingDocumentFormat getFormat() {
        return format;
    }


    /**
     * Sets the format value for this DangerousGoodsShippersDeclarationDetail.
     * 
     * @param format Specifies characteristics of a shipping document to be produced.
     */
    public void setFormat(com.owd.fedEx.ShipService.ShippingDocumentFormat format) {
        this.format = format;
    }


    /**
     * Gets the customerImageUsages value for this DangerousGoodsShippersDeclarationDetail.
     * 
     * @return customerImageUsages Specifies the usage and identification of customer supplied images
 * to be used on this document.
     */
    public com.owd.fedEx.ShipService.CustomerImageUsage[] getCustomerImageUsages() {
        return customerImageUsages;
    }


    /**
     * Sets the customerImageUsages value for this DangerousGoodsShippersDeclarationDetail.
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

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DangerousGoodsShippersDeclarationDetail)) return false;
        DangerousGoodsShippersDeclarationDetail other = (DangerousGoodsShippersDeclarationDetail) obj;
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
            ((this.customerImageUsages==null && other.getCustomerImageUsages()==null) || 
             (this.customerImageUsages!=null &&
              java.util.Arrays.equals(this.customerImageUsages, other.getCustomerImageUsages())));
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
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DangerousGoodsShippersDeclarationDetail.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "DangerousGoodsShippersDeclarationDetail"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("format");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Format"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ShippingDocumentFormat"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("customerImageUsages");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "CustomerImageUsages"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "CustomerImageUsage"));
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
