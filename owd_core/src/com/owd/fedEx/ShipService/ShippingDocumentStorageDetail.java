/*
 * ShippingDocumentStorageDetail.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;


/**
 * Specifies how to store shipping documents.
 */
public class ShippingDocumentStorageDetail  implements java.io.Serializable {
    private com.owd.fedEx.ShipService.ShippingDocumentStorageDetailType type;
    private java.lang.String filePath;
    /** Identifies the convention by which file names are constructed for
 * STORED or DEFERRED documents. */
    private com.owd.fedEx.ShipService.ShippingDocumentNamingType fileNaming;
    private java.lang.String fileSuffix;

    public ShippingDocumentStorageDetail() {
    }


    /**
     * Gets the type value for this ShippingDocumentStorageDetail.
     * 
     * @return type
     */
    public com.owd.fedEx.ShipService.ShippingDocumentStorageDetailType getType() {
        return type;
    }


    /**
     * Sets the type value for this ShippingDocumentStorageDetail.
     * 
     * @param type
     */
    public void setType(com.owd.fedEx.ShipService.ShippingDocumentStorageDetailType type) {
        this.type = type;
    }


    /**
     * Gets the filePath value for this ShippingDocumentStorageDetail.
     * 
     * @return filePath
     */
    public java.lang.String getFilePath() {
        return filePath;
    }


    /**
     * Sets the filePath value for this ShippingDocumentStorageDetail.
     * 
     * @param filePath
     */
    public void setFilePath(java.lang.String filePath) {
        this.filePath = filePath;
    }


    /**
     * Gets the fileNaming value for this ShippingDocumentStorageDetail.
     * 
     * @return fileNaming Identifies the convention by which file names are constructed for
 * STORED or DEFERRED documents.
     */
    public com.owd.fedEx.ShipService.ShippingDocumentNamingType getFileNaming() {
        return fileNaming;
    }


    /**
     * Sets the fileNaming value for this ShippingDocumentStorageDetail.
     * 
     * @param fileNaming Identifies the convention by which file names are constructed for
 * STORED or DEFERRED documents.
     */
    public void setFileNaming(com.owd.fedEx.ShipService.ShippingDocumentNamingType fileNaming) {
        this.fileNaming = fileNaming;
    }


    /**
     * Gets the fileSuffix value for this ShippingDocumentStorageDetail.
     * 
     * @return fileSuffix
     */
    public java.lang.String getFileSuffix() {
        return fileSuffix;
    }


    /**
     * Sets the fileSuffix value for this ShippingDocumentStorageDetail.
     * 
     * @param fileSuffix
     */
    public void setFileSuffix(java.lang.String fileSuffix) {
        this.fileSuffix = fileSuffix;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ShippingDocumentStorageDetail)) return false;
        ShippingDocumentStorageDetail other = (ShippingDocumentStorageDetail) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.type==null && other.getType()==null) || 
             (this.type!=null &&
              this.type.equals(other.getType()))) &&
            ((this.filePath==null && other.getFilePath()==null) || 
             (this.filePath!=null &&
              this.filePath.equals(other.getFilePath()))) &&
            ((this.fileNaming==null && other.getFileNaming()==null) || 
             (this.fileNaming!=null &&
              this.fileNaming.equals(other.getFileNaming()))) &&
            ((this.fileSuffix==null && other.getFileSuffix()==null) || 
             (this.fileSuffix!=null &&
              this.fileSuffix.equals(other.getFileSuffix())));
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
        if (getType() != null) {
            _hashCode += getType().hashCode();
        }
        if (getFilePath() != null) {
            _hashCode += getFilePath().hashCode();
        }
        if (getFileNaming() != null) {
            _hashCode += getFileNaming().hashCode();
        }
        if (getFileSuffix() != null) {
            _hashCode += getFileSuffix().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ShippingDocumentStorageDetail.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ShippingDocumentStorageDetail"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("type");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Type"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ShippingDocumentStorageDetailType"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("filePath");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "FilePath"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fileNaming");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "FileNaming"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ShippingDocumentNamingType"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fileSuffix");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "FileSuffix"));
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
