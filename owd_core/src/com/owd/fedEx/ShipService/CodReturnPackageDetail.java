/*
 * CodReturnPackageDetail.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;


/**
 * Specifies the information associated with a package that has COD
 * special service in a ground shipment.
 */
public class CodReturnPackageDetail  implements java.io.Serializable {
    private com.owd.fedEx.ShipService.Money collectionAmount;
    /** Specifies the type of adjustment was performed to the COD collection
 * amount during rating. */
    private com.owd.fedEx.ShipService.CodAdjustmentType adjustmentType;
    private java.lang.Boolean electronic;
    /** Each instance of this data type represents the set of barcodes
 * (of all types) which are associated with a specific package. */
    private com.owd.fedEx.ShipService.PackageBarcodes barcodes;
    private com.owd.fedEx.ShipService.ShippingDocument label;

    public CodReturnPackageDetail() {
    }


    /**
     * Gets the collectionAmount value for this CodReturnPackageDetail.
     * 
     * @return collectionAmount
     */
    public com.owd.fedEx.ShipService.Money getCollectionAmount() {
        return collectionAmount;
    }


    /**
     * Sets the collectionAmount value for this CodReturnPackageDetail.
     * 
     * @param collectionAmount
     */
    public void setCollectionAmount(com.owd.fedEx.ShipService.Money collectionAmount) {
        this.collectionAmount = collectionAmount;
    }


    /**
     * Gets the adjustmentType value for this CodReturnPackageDetail.
     * 
     * @return adjustmentType Specifies the type of adjustment was performed to the COD collection
 * amount during rating.
     */
    public com.owd.fedEx.ShipService.CodAdjustmentType getAdjustmentType() {
        return adjustmentType;
    }


    /**
     * Sets the adjustmentType value for this CodReturnPackageDetail.
     * 
     * @param adjustmentType Specifies the type of adjustment was performed to the COD collection
 * amount during rating.
     */
    public void setAdjustmentType(com.owd.fedEx.ShipService.CodAdjustmentType adjustmentType) {
        this.adjustmentType = adjustmentType;
    }


    /**
     * Gets the electronic value for this CodReturnPackageDetail.
     * 
     * @return electronic
     */
    public java.lang.Boolean getElectronic() {
        return electronic;
    }


    /**
     * Sets the electronic value for this CodReturnPackageDetail.
     * 
     * @param electronic
     */
    public void setElectronic(java.lang.Boolean electronic) {
        this.electronic = electronic;
    }


    /**
     * Gets the barcodes value for this CodReturnPackageDetail.
     * 
     * @return barcodes Each instance of this data type represents the set of barcodes
 * (of all types) which are associated with a specific package.
     */
    public com.owd.fedEx.ShipService.PackageBarcodes getBarcodes() {
        return barcodes;
    }


    /**
     * Sets the barcodes value for this CodReturnPackageDetail.
     * 
     * @param barcodes Each instance of this data type represents the set of barcodes
 * (of all types) which are associated with a specific package.
     */
    public void setBarcodes(com.owd.fedEx.ShipService.PackageBarcodes barcodes) {
        this.barcodes = barcodes;
    }


    /**
     * Gets the label value for this CodReturnPackageDetail.
     * 
     * @return label
     */
    public com.owd.fedEx.ShipService.ShippingDocument getLabel() {
        return label;
    }


    /**
     * Sets the label value for this CodReturnPackageDetail.
     * 
     * @param label
     */
    public void setLabel(com.owd.fedEx.ShipService.ShippingDocument label) {
        this.label = label;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CodReturnPackageDetail)) return false;
        CodReturnPackageDetail other = (CodReturnPackageDetail) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.collectionAmount==null && other.getCollectionAmount()==null) || 
             (this.collectionAmount!=null &&
              this.collectionAmount.equals(other.getCollectionAmount()))) &&
            ((this.adjustmentType==null && other.getAdjustmentType()==null) || 
             (this.adjustmentType!=null &&
              this.adjustmentType.equals(other.getAdjustmentType()))) &&
            ((this.electronic==null && other.getElectronic()==null) || 
             (this.electronic!=null &&
              this.electronic.equals(other.getElectronic()))) &&
            ((this.barcodes==null && other.getBarcodes()==null) || 
             (this.barcodes!=null &&
              this.barcodes.equals(other.getBarcodes()))) &&
            ((this.label==null && other.getLabel()==null) || 
             (this.label!=null &&
              this.label.equals(other.getLabel())));
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
        if (getCollectionAmount() != null) {
            _hashCode += getCollectionAmount().hashCode();
        }
        if (getAdjustmentType() != null) {
            _hashCode += getAdjustmentType().hashCode();
        }
        if (getElectronic() != null) {
            _hashCode += getElectronic().hashCode();
        }
        if (getBarcodes() != null) {
            _hashCode += getBarcodes().hashCode();
        }
        if (getLabel() != null) {
            _hashCode += getLabel().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CodReturnPackageDetail.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "CodReturnPackageDetail"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("collectionAmount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "CollectionAmount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Money"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("adjustmentType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "AdjustmentType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "CodAdjustmentType"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("electronic");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Electronic"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "boolean"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("barcodes");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Barcodes"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "PackageBarcodes"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("label");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Label"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ShippingDocument"));
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
