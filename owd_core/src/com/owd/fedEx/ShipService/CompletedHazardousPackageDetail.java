/*
 * CompletedHazardousPackageDetail.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;


/**
 * Completed package-level hazardous commodity information for a single
 * package.
 */
public class CompletedHazardousPackageDetail  implements java.io.Serializable {
    private java.lang.String referenceId;
    private com.owd.fedEx.ShipService.DangerousGoodsAccessibilityType accessibility;
    private java.lang.Boolean cargoAircraftOnly;
    /** Identifies the source of regulation for hazardous commodity data. */
    private com.owd.fedEx.ShipService.HazardousCommodityRegulationType regulation;
    private java.math.BigDecimal radioactiveTransportIndex;
    private com.owd.fedEx.ShipService.RadioactiveLabelType labelType;
    /** Documents the kinds and quantities of all hazardous commodities
 * in the current package. */
    private com.owd.fedEx.ShipService.ValidatedHazardousContainer[] containers;

    public CompletedHazardousPackageDetail() {
    }


    /**
     * Gets the referenceId value for this CompletedHazardousPackageDetail.
     * 
     * @return referenceId
     */
    public java.lang.String getReferenceId() {
        return referenceId;
    }


    /**
     * Sets the referenceId value for this CompletedHazardousPackageDetail.
     * 
     * @param referenceId
     */
    public void setReferenceId(java.lang.String referenceId) {
        this.referenceId = referenceId;
    }


    /**
     * Gets the accessibility value for this CompletedHazardousPackageDetail.
     * 
     * @return accessibility
     */
    public com.owd.fedEx.ShipService.DangerousGoodsAccessibilityType getAccessibility() {
        return accessibility;
    }


    /**
     * Sets the accessibility value for this CompletedHazardousPackageDetail.
     * 
     * @param accessibility
     */
    public void setAccessibility(com.owd.fedEx.ShipService.DangerousGoodsAccessibilityType accessibility) {
        this.accessibility = accessibility;
    }


    /**
     * Gets the cargoAircraftOnly value for this CompletedHazardousPackageDetail.
     * 
     * @return cargoAircraftOnly
     */
    public java.lang.Boolean getCargoAircraftOnly() {
        return cargoAircraftOnly;
    }


    /**
     * Sets the cargoAircraftOnly value for this CompletedHazardousPackageDetail.
     * 
     * @param cargoAircraftOnly
     */
    public void setCargoAircraftOnly(java.lang.Boolean cargoAircraftOnly) {
        this.cargoAircraftOnly = cargoAircraftOnly;
    }


    /**
     * Gets the regulation value for this CompletedHazardousPackageDetail.
     * 
     * @return regulation Identifies the source of regulation for hazardous commodity data.
     */
    public com.owd.fedEx.ShipService.HazardousCommodityRegulationType getRegulation() {
        return regulation;
    }


    /**
     * Sets the regulation value for this CompletedHazardousPackageDetail.
     * 
     * @param regulation Identifies the source of regulation for hazardous commodity data.
     */
    public void setRegulation(com.owd.fedEx.ShipService.HazardousCommodityRegulationType regulation) {
        this.regulation = regulation;
    }


    /**
     * Gets the radioactiveTransportIndex value for this CompletedHazardousPackageDetail.
     * 
     * @return radioactiveTransportIndex
     */
    public java.math.BigDecimal getRadioactiveTransportIndex() {
        return radioactiveTransportIndex;
    }


    /**
     * Sets the radioactiveTransportIndex value for this CompletedHazardousPackageDetail.
     * 
     * @param radioactiveTransportIndex
     */
    public void setRadioactiveTransportIndex(java.math.BigDecimal radioactiveTransportIndex) {
        this.radioactiveTransportIndex = radioactiveTransportIndex;
    }


    /**
     * Gets the labelType value for this CompletedHazardousPackageDetail.
     * 
     * @return labelType
     */
    public com.owd.fedEx.ShipService.RadioactiveLabelType getLabelType() {
        return labelType;
    }


    /**
     * Sets the labelType value for this CompletedHazardousPackageDetail.
     * 
     * @param labelType
     */
    public void setLabelType(com.owd.fedEx.ShipService.RadioactiveLabelType labelType) {
        this.labelType = labelType;
    }


    /**
     * Gets the containers value for this CompletedHazardousPackageDetail.
     * 
     * @return containers Documents the kinds and quantities of all hazardous commodities
 * in the current package.
     */
    public com.owd.fedEx.ShipService.ValidatedHazardousContainer[] getContainers() {
        return containers;
    }


    /**
     * Sets the containers value for this CompletedHazardousPackageDetail.
     * 
     * @param containers Documents the kinds and quantities of all hazardous commodities
 * in the current package.
     */
    public void setContainers(com.owd.fedEx.ShipService.ValidatedHazardousContainer[] containers) {
        this.containers = containers;
    }

    public com.owd.fedEx.ShipService.ValidatedHazardousContainer getContainers(int i) {
        return containers[i];
    }

    public void setContainers(int i, com.owd.fedEx.ShipService.ValidatedHazardousContainer value) {
        this.containers[i] = value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CompletedHazardousPackageDetail)) return false;
        CompletedHazardousPackageDetail other = (CompletedHazardousPackageDetail) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.referenceId==null && other.getReferenceId()==null) || 
             (this.referenceId!=null &&
              this.referenceId.equals(other.getReferenceId()))) &&
            ((this.accessibility==null && other.getAccessibility()==null) || 
             (this.accessibility!=null &&
              this.accessibility.equals(other.getAccessibility()))) &&
            ((this.cargoAircraftOnly==null && other.getCargoAircraftOnly()==null) || 
             (this.cargoAircraftOnly!=null &&
              this.cargoAircraftOnly.equals(other.getCargoAircraftOnly()))) &&
            ((this.regulation==null && other.getRegulation()==null) || 
             (this.regulation!=null &&
              this.regulation.equals(other.getRegulation()))) &&
            ((this.radioactiveTransportIndex==null && other.getRadioactiveTransportIndex()==null) || 
             (this.radioactiveTransportIndex!=null &&
              this.radioactiveTransportIndex.equals(other.getRadioactiveTransportIndex()))) &&
            ((this.labelType==null && other.getLabelType()==null) || 
             (this.labelType!=null &&
              this.labelType.equals(other.getLabelType()))) &&
            ((this.containers==null && other.getContainers()==null) || 
             (this.containers!=null &&
              java.util.Arrays.equals(this.containers, other.getContainers())));
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
        if (getReferenceId() != null) {
            _hashCode += getReferenceId().hashCode();
        }
        if (getAccessibility() != null) {
            _hashCode += getAccessibility().hashCode();
        }
        if (getCargoAircraftOnly() != null) {
            _hashCode += getCargoAircraftOnly().hashCode();
        }
        if (getRegulation() != null) {
            _hashCode += getRegulation().hashCode();
        }
        if (getRadioactiveTransportIndex() != null) {
            _hashCode += getRadioactiveTransportIndex().hashCode();
        }
        if (getLabelType() != null) {
            _hashCode += getLabelType().hashCode();
        }
        if (getContainers() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getContainers());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getContainers(), i);
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
        new org.apache.axis.description.TypeDesc(CompletedHazardousPackageDetail.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "CompletedHazardousPackageDetail"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("referenceId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ReferenceId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("accessibility");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Accessibility"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "DangerousGoodsAccessibilityType"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cargoAircraftOnly");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "CargoAircraftOnly"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "boolean"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("regulation");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Regulation"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "HazardousCommodityRegulationType"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("radioactiveTransportIndex");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "RadioactiveTransportIndex"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("labelType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "LabelType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "RadioactiveLabelType"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("containers");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Containers"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ValidatedHazardousContainer"));
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
