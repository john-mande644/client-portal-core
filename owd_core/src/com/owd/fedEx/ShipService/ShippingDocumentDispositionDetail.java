/*
 * ShippingDocumentDispositionDetail.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;


/**
 * Each occurrence of this class specifies a particular way in which
 * a kind of shipping document is to be produced and provided.
 */
public class ShippingDocumentDispositionDetail  implements java.io.Serializable {
    /** Specifies how to return a shipping document to the caller. */
    private com.owd.fedEx.ShipService.ShippingDocumentDispositionType dispositionType;
    /** Specifies how to organize all shipping documents of the same type. */
    private com.owd.fedEx.ShipService.ShippingDocumentGroupingType grouping;
    /** Specifies how to store shipping documents. */
    private com.owd.fedEx.ShipService.ShippingDocumentStorageDetail storageDetail;
    /** Specifies how to e-mail shipping documents. */
    private com.owd.fedEx.ShipService.ShippingDocumentEMailDetail EMailDetail;
    /** Specifies printing options for a shipping document. */
    private com.owd.fedEx.ShipService.ShippingDocumentPrintDetail printDetail;

    public ShippingDocumentDispositionDetail() {
    }


    /**
     * Gets the dispositionType value for this ShippingDocumentDispositionDetail.
     * 
     * @return dispositionType Specifies how to return a shipping document to the caller.
     */
    public com.owd.fedEx.ShipService.ShippingDocumentDispositionType getDispositionType() {
        return dispositionType;
    }


    /**
     * Sets the dispositionType value for this ShippingDocumentDispositionDetail.
     * 
     * @param dispositionType Specifies how to return a shipping document to the caller.
     */
    public void setDispositionType(com.owd.fedEx.ShipService.ShippingDocumentDispositionType dispositionType) {
        this.dispositionType = dispositionType;
    }


    /**
     * Gets the grouping value for this ShippingDocumentDispositionDetail.
     * 
     * @return grouping Specifies how to organize all shipping documents of the same type.
     */
    public com.owd.fedEx.ShipService.ShippingDocumentGroupingType getGrouping() {
        return grouping;
    }


    /**
     * Sets the grouping value for this ShippingDocumentDispositionDetail.
     * 
     * @param grouping Specifies how to organize all shipping documents of the same type.
     */
    public void setGrouping(com.owd.fedEx.ShipService.ShippingDocumentGroupingType grouping) {
        this.grouping = grouping;
    }


    /**
     * Gets the storageDetail value for this ShippingDocumentDispositionDetail.
     * 
     * @return storageDetail Specifies how to store shipping documents.
     */
    public com.owd.fedEx.ShipService.ShippingDocumentStorageDetail getStorageDetail() {
        return storageDetail;
    }


    /**
     * Sets the storageDetail value for this ShippingDocumentDispositionDetail.
     * 
     * @param storageDetail Specifies how to store shipping documents.
     */
    public void setStorageDetail(com.owd.fedEx.ShipService.ShippingDocumentStorageDetail storageDetail) {
        this.storageDetail = storageDetail;
    }


    /**
     * Gets the EMailDetail value for this ShippingDocumentDispositionDetail.
     * 
     * @return EMailDetail Specifies how to e-mail shipping documents.
     */
    public com.owd.fedEx.ShipService.ShippingDocumentEMailDetail getEMailDetail() {
        return EMailDetail;
    }


    /**
     * Sets the EMailDetail value for this ShippingDocumentDispositionDetail.
     * 
     * @param EMailDetail Specifies how to e-mail shipping documents.
     */
    public void setEMailDetail(com.owd.fedEx.ShipService.ShippingDocumentEMailDetail EMailDetail) {
        this.EMailDetail = EMailDetail;
    }


    /**
     * Gets the printDetail value for this ShippingDocumentDispositionDetail.
     * 
     * @return printDetail Specifies printing options for a shipping document.
     */
    public com.owd.fedEx.ShipService.ShippingDocumentPrintDetail getPrintDetail() {
        return printDetail;
    }


    /**
     * Sets the printDetail value for this ShippingDocumentDispositionDetail.
     * 
     * @param printDetail Specifies printing options for a shipping document.
     */
    public void setPrintDetail(com.owd.fedEx.ShipService.ShippingDocumentPrintDetail printDetail) {
        this.printDetail = printDetail;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ShippingDocumentDispositionDetail)) return false;
        ShippingDocumentDispositionDetail other = (ShippingDocumentDispositionDetail) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.dispositionType==null && other.getDispositionType()==null) || 
             (this.dispositionType!=null &&
              this.dispositionType.equals(other.getDispositionType()))) &&
            ((this.grouping==null && other.getGrouping()==null) || 
             (this.grouping!=null &&
              this.grouping.equals(other.getGrouping()))) &&
            ((this.storageDetail==null && other.getStorageDetail()==null) || 
             (this.storageDetail!=null &&
              this.storageDetail.equals(other.getStorageDetail()))) &&
            ((this.EMailDetail==null && other.getEMailDetail()==null) || 
             (this.EMailDetail!=null &&
              this.EMailDetail.equals(other.getEMailDetail()))) &&
            ((this.printDetail==null && other.getPrintDetail()==null) || 
             (this.printDetail!=null &&
              this.printDetail.equals(other.getPrintDetail())));
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
        if (getDispositionType() != null) {
            _hashCode += getDispositionType().hashCode();
        }
        if (getGrouping() != null) {
            _hashCode += getGrouping().hashCode();
        }
        if (getStorageDetail() != null) {
            _hashCode += getStorageDetail().hashCode();
        }
        if (getEMailDetail() != null) {
            _hashCode += getEMailDetail().hashCode();
        }
        if (getPrintDetail() != null) {
            _hashCode += getPrintDetail().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ShippingDocumentDispositionDetail.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ShippingDocumentDispositionDetail"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dispositionType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "DispositionType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ShippingDocumentDispositionType"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("grouping");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Grouping"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ShippingDocumentGroupingType"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("storageDetail");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "StorageDetail"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ShippingDocumentStorageDetail"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("EMailDetail");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "EMailDetail"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ShippingDocumentEMailDetail"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("printDetail");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "PrintDetail"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ShippingDocumentPrintDetail"));
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
