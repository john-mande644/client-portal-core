/*
 * HazardousCommodityPackingDetail.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;


/**
 * Specifies documentation and limits for validation of an individual
 * packing group/category.
 */
public class HazardousCommodityPackingDetail  implements java.io.Serializable {
    private java.lang.Boolean cargoAircraftOnly;
    private java.lang.String packingInstructions;

    public HazardousCommodityPackingDetail() {
    }


    /**
     * Gets the cargoAircraftOnly value for this HazardousCommodityPackingDetail.
     * 
     * @return cargoAircraftOnly
     */
    public java.lang.Boolean getCargoAircraftOnly() {
        return cargoAircraftOnly;
    }


    /**
     * Sets the cargoAircraftOnly value for this HazardousCommodityPackingDetail.
     * 
     * @param cargoAircraftOnly
     */
    public void setCargoAircraftOnly(java.lang.Boolean cargoAircraftOnly) {
        this.cargoAircraftOnly = cargoAircraftOnly;
    }


    /**
     * Gets the packingInstructions value for this HazardousCommodityPackingDetail.
     * 
     * @return packingInstructions
     */
    public java.lang.String getPackingInstructions() {
        return packingInstructions;
    }


    /**
     * Sets the packingInstructions value for this HazardousCommodityPackingDetail.
     * 
     * @param packingInstructions
     */
    public void setPackingInstructions(java.lang.String packingInstructions) {
        this.packingInstructions = packingInstructions;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof HazardousCommodityPackingDetail)) return false;
        HazardousCommodityPackingDetail other = (HazardousCommodityPackingDetail) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.cargoAircraftOnly==null && other.getCargoAircraftOnly()==null) || 
             (this.cargoAircraftOnly!=null &&
              this.cargoAircraftOnly.equals(other.getCargoAircraftOnly()))) &&
            ((this.packingInstructions==null && other.getPackingInstructions()==null) || 
             (this.packingInstructions!=null &&
              this.packingInstructions.equals(other.getPackingInstructions())));
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
        if (getCargoAircraftOnly() != null) {
            _hashCode += getCargoAircraftOnly().hashCode();
        }
        if (getPackingInstructions() != null) {
            _hashCode += getPackingInstructions().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(HazardousCommodityPackingDetail.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "HazardousCommodityPackingDetail"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cargoAircraftOnly");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "CargoAircraftOnly"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "boolean"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("packingInstructions");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "PackingInstructions"));
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
