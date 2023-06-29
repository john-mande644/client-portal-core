/*
 * ShipmentDryIceDetail.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;


/**
 * Shipment-level totals of dry ice data across all packages.
 */
public class ShipmentDryIceDetail  implements java.io.Serializable {
    private org.apache.axis.types.NonNegativeInteger packageCount;
    /** The descriptive data for the heaviness of an object. */
    private com.owd.fedEx.ShipService.Weight totalWeight;
    private com.owd.fedEx.ShipService.ShipmentDryIceProcessingOptionsRequested processingOptions;

    public ShipmentDryIceDetail() {
    }


    /**
     * Gets the packageCount value for this ShipmentDryIceDetail.
     * 
     * @return packageCount
     */
    public org.apache.axis.types.NonNegativeInteger getPackageCount() {
        return packageCount;
    }


    /**
     * Sets the packageCount value for this ShipmentDryIceDetail.
     * 
     * @param packageCount
     */
    public void setPackageCount(org.apache.axis.types.NonNegativeInteger packageCount) {
        this.packageCount = packageCount;
    }


    /**
     * Gets the totalWeight value for this ShipmentDryIceDetail.
     * 
     * @return totalWeight The descriptive data for the heaviness of an object.
     */
    public com.owd.fedEx.ShipService.Weight getTotalWeight() {
        return totalWeight;
    }


    /**
     * Sets the totalWeight value for this ShipmentDryIceDetail.
     * 
     * @param totalWeight The descriptive data for the heaviness of an object.
     */
    public void setTotalWeight(com.owd.fedEx.ShipService.Weight totalWeight) {
        this.totalWeight = totalWeight;
    }


    /**
     * Gets the processingOptions value for this ShipmentDryIceDetail.
     * 
     * @return processingOptions
     */
    public com.owd.fedEx.ShipService.ShipmentDryIceProcessingOptionsRequested getProcessingOptions() {
        return processingOptions;
    }


    /**
     * Sets the processingOptions value for this ShipmentDryIceDetail.
     * 
     * @param processingOptions
     */
    public void setProcessingOptions(com.owd.fedEx.ShipService.ShipmentDryIceProcessingOptionsRequested processingOptions) {
        this.processingOptions = processingOptions;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ShipmentDryIceDetail)) return false;
        ShipmentDryIceDetail other = (ShipmentDryIceDetail) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.packageCount==null && other.getPackageCount()==null) || 
             (this.packageCount!=null &&
              this.packageCount.equals(other.getPackageCount()))) &&
            ((this.totalWeight==null && other.getTotalWeight()==null) || 
             (this.totalWeight!=null &&
              this.totalWeight.equals(other.getTotalWeight()))) &&
            ((this.processingOptions==null && other.getProcessingOptions()==null) || 
             (this.processingOptions!=null &&
              this.processingOptions.equals(other.getProcessingOptions())));
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
        if (getPackageCount() != null) {
            _hashCode += getPackageCount().hashCode();
        }
        if (getTotalWeight() != null) {
            _hashCode += getTotalWeight().hashCode();
        }
        if (getProcessingOptions() != null) {
            _hashCode += getProcessingOptions().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ShipmentDryIceDetail.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ShipmentDryIceDetail"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("packageCount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "PackageCount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "nonNegativeInteger"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalWeight");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "TotalWeight"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Weight"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("processingOptions");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ProcessingOptions"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ShipmentDryIceProcessingOptionsRequested"));
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
