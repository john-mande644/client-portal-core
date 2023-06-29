/*
 * DangerousGoodsContainer.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;


/**
 * Describes an approved container used to package dangerous goods
 * commodities. This does not describe any individual inner receptacles
 * that may be within this container.
 */
public class DangerousGoodsContainer  implements java.io.Serializable {
    private com.owd.fedEx.ShipService.HazardousContainerPackingType packingType;
    private java.lang.String containerType;
    /** Indicates the packaging type of the container used to package radioactive
 * hazardous materials. */
    private com.owd.fedEx.ShipService.RadioactiveContainerClassType radioactiveContainerClass;
    private org.apache.axis.types.NonNegativeInteger numberOfContainers;
    /** Documents the kinds and quantities of all hazardous commodities
 * in the current container. */
    private com.owd.fedEx.ShipService.HazardousCommodityContent[] hazardousCommodities;

    public DangerousGoodsContainer() {
    }


    /**
     * Gets the packingType value for this DangerousGoodsContainer.
     * 
     * @return packingType
     */
    public com.owd.fedEx.ShipService.HazardousContainerPackingType getPackingType() {
        return packingType;
    }


    /**
     * Sets the packingType value for this DangerousGoodsContainer.
     * 
     * @param packingType
     */
    public void setPackingType(com.owd.fedEx.ShipService.HazardousContainerPackingType packingType) {
        this.packingType = packingType;
    }


    /**
     * Gets the containerType value for this DangerousGoodsContainer.
     * 
     * @return containerType
     */
    public java.lang.String getContainerType() {
        return containerType;
    }


    /**
     * Sets the containerType value for this DangerousGoodsContainer.
     * 
     * @param containerType
     */
    public void setContainerType(java.lang.String containerType) {
        this.containerType = containerType;
    }


    /**
     * Gets the radioactiveContainerClass value for this DangerousGoodsContainer.
     * 
     * @return radioactiveContainerClass Indicates the packaging type of the container used to package radioactive
 * hazardous materials.
     */
    public com.owd.fedEx.ShipService.RadioactiveContainerClassType getRadioactiveContainerClass() {
        return radioactiveContainerClass;
    }


    /**
     * Sets the radioactiveContainerClass value for this DangerousGoodsContainer.
     * 
     * @param radioactiveContainerClass Indicates the packaging type of the container used to package radioactive
 * hazardous materials.
     */
    public void setRadioactiveContainerClass(com.owd.fedEx.ShipService.RadioactiveContainerClassType radioactiveContainerClass) {
        this.radioactiveContainerClass = radioactiveContainerClass;
    }


    /**
     * Gets the numberOfContainers value for this DangerousGoodsContainer.
     * 
     * @return numberOfContainers
     */
    public org.apache.axis.types.NonNegativeInteger getNumberOfContainers() {
        return numberOfContainers;
    }


    /**
     * Sets the numberOfContainers value for this DangerousGoodsContainer.
     * 
     * @param numberOfContainers
     */
    public void setNumberOfContainers(org.apache.axis.types.NonNegativeInteger numberOfContainers) {
        this.numberOfContainers = numberOfContainers;
    }


    /**
     * Gets the hazardousCommodities value for this DangerousGoodsContainer.
     * 
     * @return hazardousCommodities Documents the kinds and quantities of all hazardous commodities
 * in the current container.
     */
    public com.owd.fedEx.ShipService.HazardousCommodityContent[] getHazardousCommodities() {
        return hazardousCommodities;
    }


    /**
     * Sets the hazardousCommodities value for this DangerousGoodsContainer.
     * 
     * @param hazardousCommodities Documents the kinds and quantities of all hazardous commodities
 * in the current container.
     */
    public void setHazardousCommodities(com.owd.fedEx.ShipService.HazardousCommodityContent[] hazardousCommodities) {
        this.hazardousCommodities = hazardousCommodities;
    }

    public com.owd.fedEx.ShipService.HazardousCommodityContent getHazardousCommodities(int i) {
        return hazardousCommodities[i];
    }

    public void setHazardousCommodities(int i, com.owd.fedEx.ShipService.HazardousCommodityContent value) {
        this.hazardousCommodities[i] = value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DangerousGoodsContainer)) return false;
        DangerousGoodsContainer other = (DangerousGoodsContainer) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.packingType==null && other.getPackingType()==null) || 
             (this.packingType!=null &&
              this.packingType.equals(other.getPackingType()))) &&
            ((this.containerType==null && other.getContainerType()==null) || 
             (this.containerType!=null &&
              this.containerType.equals(other.getContainerType()))) &&
            ((this.radioactiveContainerClass==null && other.getRadioactiveContainerClass()==null) || 
             (this.radioactiveContainerClass!=null &&
              this.radioactiveContainerClass.equals(other.getRadioactiveContainerClass()))) &&
            ((this.numberOfContainers==null && other.getNumberOfContainers()==null) || 
             (this.numberOfContainers!=null &&
              this.numberOfContainers.equals(other.getNumberOfContainers()))) &&
            ((this.hazardousCommodities==null && other.getHazardousCommodities()==null) || 
             (this.hazardousCommodities!=null &&
              java.util.Arrays.equals(this.hazardousCommodities, other.getHazardousCommodities())));
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
        if (getPackingType() != null) {
            _hashCode += getPackingType().hashCode();
        }
        if (getContainerType() != null) {
            _hashCode += getContainerType().hashCode();
        }
        if (getRadioactiveContainerClass() != null) {
            _hashCode += getRadioactiveContainerClass().hashCode();
        }
        if (getNumberOfContainers() != null) {
            _hashCode += getNumberOfContainers().hashCode();
        }
        if (getHazardousCommodities() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getHazardousCommodities());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getHazardousCommodities(), i);
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
        new org.apache.axis.description.TypeDesc(DangerousGoodsContainer.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "DangerousGoodsContainer"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("packingType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "PackingType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "HazardousContainerPackingType"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("containerType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ContainerType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("radioactiveContainerClass");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "RadioactiveContainerClass"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "RadioactiveContainerClassType"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numberOfContainers");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "NumberOfContainers"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "nonNegativeInteger"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("hazardousCommodities");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "HazardousCommodities"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "HazardousCommodityContent"));
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
