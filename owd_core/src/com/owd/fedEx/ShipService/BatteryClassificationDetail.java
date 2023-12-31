/*
 * BatteryClassificationDetail.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;


/**
 * Describes attributes of a battery or cell that are used for classification
 * purposes. Typically this structure would be used to allow customers
 * to declare batteries or cells for which full dangerous goods documentation
 * and procedures are not required.
 */
public class BatteryClassificationDetail  implements java.io.Serializable {
    /** Describes the material composition of a battery or cell. */
    private com.owd.fedEx.ShipService.BatteryMaterialType material;
    /** Describes the packing arrangement of a battery or cell with respect
 * to other items within the same package. */
    private com.owd.fedEx.ShipService.BatteryPackingType packing;
    /** A regulation specific classification for a battery or cell. */
    private com.owd.fedEx.ShipService.BatteryRegulatorySubType regulatorySubType;

    public BatteryClassificationDetail() {
    }


    /**
     * Gets the material value for this BatteryClassificationDetail.
     * 
     * @return material Describes the material composition of a battery or cell.
     */
    public com.owd.fedEx.ShipService.BatteryMaterialType getMaterial() {
        return material;
    }


    /**
     * Sets the material value for this BatteryClassificationDetail.
     * 
     * @param material Describes the material composition of a battery or cell.
     */
    public void setMaterial(com.owd.fedEx.ShipService.BatteryMaterialType material) {
        this.material = material;
    }


    /**
     * Gets the packing value for this BatteryClassificationDetail.
     * 
     * @return packing Describes the packing arrangement of a battery or cell with respect
 * to other items within the same package.
     */
    public com.owd.fedEx.ShipService.BatteryPackingType getPacking() {
        return packing;
    }


    /**
     * Sets the packing value for this BatteryClassificationDetail.
     * 
     * @param packing Describes the packing arrangement of a battery or cell with respect
 * to other items within the same package.
     */
    public void setPacking(com.owd.fedEx.ShipService.BatteryPackingType packing) {
        this.packing = packing;
    }


    /**
     * Gets the regulatorySubType value for this BatteryClassificationDetail.
     * 
     * @return regulatorySubType A regulation specific classification for a battery or cell.
     */
    public com.owd.fedEx.ShipService.BatteryRegulatorySubType getRegulatorySubType() {
        return regulatorySubType;
    }


    /**
     * Sets the regulatorySubType value for this BatteryClassificationDetail.
     * 
     * @param regulatorySubType A regulation specific classification for a battery or cell.
     */
    public void setRegulatorySubType(com.owd.fedEx.ShipService.BatteryRegulatorySubType regulatorySubType) {
        this.regulatorySubType = regulatorySubType;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof BatteryClassificationDetail)) return false;
        BatteryClassificationDetail other = (BatteryClassificationDetail) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.material==null && other.getMaterial()==null) || 
             (this.material!=null &&
              this.material.equals(other.getMaterial()))) &&
            ((this.packing==null && other.getPacking()==null) || 
             (this.packing!=null &&
              this.packing.equals(other.getPacking()))) &&
            ((this.regulatorySubType==null && other.getRegulatorySubType()==null) || 
             (this.regulatorySubType!=null &&
              this.regulatorySubType.equals(other.getRegulatorySubType())));
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
        if (getMaterial() != null) {
            _hashCode += getMaterial().hashCode();
        }
        if (getPacking() != null) {
            _hashCode += getPacking().hashCode();
        }
        if (getRegulatorySubType() != null) {
            _hashCode += getRegulatorySubType().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(BatteryClassificationDetail.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "BatteryClassificationDetail"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("material");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Material"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "BatteryMaterialType"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("packing");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Packing"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "BatteryPackingType"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("regulatorySubType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "RegulatorySubType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "BatteryRegulatorySubType"));
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
