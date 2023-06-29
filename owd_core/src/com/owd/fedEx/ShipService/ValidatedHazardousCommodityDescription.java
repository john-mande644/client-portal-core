/*
 * ValidatedHazardousCommodityDescription.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;


/**
 * Identifies and describes an individual hazardous commodity. For
 * 201001 load, this is based on data from the FedEx Ground Hazardous
 * Materials Shipping Guide.
 */
public class ValidatedHazardousCommodityDescription  implements java.io.Serializable {
    private java.lang.String id;
    private org.apache.axis.types.NonNegativeInteger sequenceNumber;
    /** Identifies DOT packing group for a hazardous commodity. */
    private com.owd.fedEx.ShipService.HazardousCommodityPackingGroupType packingGroup;
    private java.lang.String packingInstructions;
    private java.lang.String properShippingName;
    private java.lang.String properShippingNameAndDescription;
    private java.lang.String technicalName;
    private java.lang.String hazardClass;
    private java.lang.String[] subsidiaryClasses;
    private java.lang.String symbols;
    private java.lang.String tunnelRestrictionCode;
    private java.lang.String specialProvisions;
    private com.owd.fedEx.ShipService.HazardousCommodityAttributeType[] attributes;
    private java.lang.String authorization;
    private java.lang.String labelText;

    public ValidatedHazardousCommodityDescription() {
    }


    /**
     * Gets the id value for this ValidatedHazardousCommodityDescription.
     * 
     * @return id
     */
    public java.lang.String getId() {
        return id;
    }


    /**
     * Sets the id value for this ValidatedHazardousCommodityDescription.
     * 
     * @param id
     */
    public void setId(java.lang.String id) {
        this.id = id;
    }


    /**
     * Gets the sequenceNumber value for this ValidatedHazardousCommodityDescription.
     * 
     * @return sequenceNumber
     */
    public org.apache.axis.types.NonNegativeInteger getSequenceNumber() {
        return sequenceNumber;
    }


    /**
     * Sets the sequenceNumber value for this ValidatedHazardousCommodityDescription.
     * 
     * @param sequenceNumber
     */
    public void setSequenceNumber(org.apache.axis.types.NonNegativeInteger sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }


    /**
     * Gets the packingGroup value for this ValidatedHazardousCommodityDescription.
     * 
     * @return packingGroup Identifies DOT packing group for a hazardous commodity.
     */
    public com.owd.fedEx.ShipService.HazardousCommodityPackingGroupType getPackingGroup() {
        return packingGroup;
    }


    /**
     * Sets the packingGroup value for this ValidatedHazardousCommodityDescription.
     * 
     * @param packingGroup Identifies DOT packing group for a hazardous commodity.
     */
    public void setPackingGroup(com.owd.fedEx.ShipService.HazardousCommodityPackingGroupType packingGroup) {
        this.packingGroup = packingGroup;
    }


    /**
     * Gets the packingInstructions value for this ValidatedHazardousCommodityDescription.
     * 
     * @return packingInstructions
     */
    public java.lang.String getPackingInstructions() {
        return packingInstructions;
    }


    /**
     * Sets the packingInstructions value for this ValidatedHazardousCommodityDescription.
     * 
     * @param packingInstructions
     */
    public void setPackingInstructions(java.lang.String packingInstructions) {
        this.packingInstructions = packingInstructions;
    }


    /**
     * Gets the properShippingName value for this ValidatedHazardousCommodityDescription.
     * 
     * @return properShippingName
     */
    public java.lang.String getProperShippingName() {
        return properShippingName;
    }


    /**
     * Sets the properShippingName value for this ValidatedHazardousCommodityDescription.
     * 
     * @param properShippingName
     */
    public void setProperShippingName(java.lang.String properShippingName) {
        this.properShippingName = properShippingName;
    }


    /**
     * Gets the properShippingNameAndDescription value for this ValidatedHazardousCommodityDescription.
     * 
     * @return properShippingNameAndDescription
     */
    public java.lang.String getProperShippingNameAndDescription() {
        return properShippingNameAndDescription;
    }


    /**
     * Sets the properShippingNameAndDescription value for this ValidatedHazardousCommodityDescription.
     * 
     * @param properShippingNameAndDescription
     */
    public void setProperShippingNameAndDescription(java.lang.String properShippingNameAndDescription) {
        this.properShippingNameAndDescription = properShippingNameAndDescription;
    }


    /**
     * Gets the technicalName value for this ValidatedHazardousCommodityDescription.
     * 
     * @return technicalName
     */
    public java.lang.String getTechnicalName() {
        return technicalName;
    }


    /**
     * Sets the technicalName value for this ValidatedHazardousCommodityDescription.
     * 
     * @param technicalName
     */
    public void setTechnicalName(java.lang.String technicalName) {
        this.technicalName = technicalName;
    }


    /**
     * Gets the hazardClass value for this ValidatedHazardousCommodityDescription.
     * 
     * @return hazardClass
     */
    public java.lang.String getHazardClass() {
        return hazardClass;
    }


    /**
     * Sets the hazardClass value for this ValidatedHazardousCommodityDescription.
     * 
     * @param hazardClass
     */
    public void setHazardClass(java.lang.String hazardClass) {
        this.hazardClass = hazardClass;
    }


    /**
     * Gets the subsidiaryClasses value for this ValidatedHazardousCommodityDescription.
     * 
     * @return subsidiaryClasses
     */
    public java.lang.String[] getSubsidiaryClasses() {
        return subsidiaryClasses;
    }


    /**
     * Sets the subsidiaryClasses value for this ValidatedHazardousCommodityDescription.
     * 
     * @param subsidiaryClasses
     */
    public void setSubsidiaryClasses(java.lang.String[] subsidiaryClasses) {
        this.subsidiaryClasses = subsidiaryClasses;
    }

    public java.lang.String getSubsidiaryClasses(int i) {
        return subsidiaryClasses[i];
    }

    public void setSubsidiaryClasses(int i, java.lang.String value) {
        this.subsidiaryClasses[i] = value;
    }


    /**
     * Gets the symbols value for this ValidatedHazardousCommodityDescription.
     * 
     * @return symbols
     */
    public java.lang.String getSymbols() {
        return symbols;
    }


    /**
     * Sets the symbols value for this ValidatedHazardousCommodityDescription.
     * 
     * @param symbols
     */
    public void setSymbols(java.lang.String symbols) {
        this.symbols = symbols;
    }


    /**
     * Gets the tunnelRestrictionCode value for this ValidatedHazardousCommodityDescription.
     * 
     * @return tunnelRestrictionCode
     */
    public java.lang.String getTunnelRestrictionCode() {
        return tunnelRestrictionCode;
    }


    /**
     * Sets the tunnelRestrictionCode value for this ValidatedHazardousCommodityDescription.
     * 
     * @param tunnelRestrictionCode
     */
    public void setTunnelRestrictionCode(java.lang.String tunnelRestrictionCode) {
        this.tunnelRestrictionCode = tunnelRestrictionCode;
    }


    /**
     * Gets the specialProvisions value for this ValidatedHazardousCommodityDescription.
     * 
     * @return specialProvisions
     */
    public java.lang.String getSpecialProvisions() {
        return specialProvisions;
    }


    /**
     * Sets the specialProvisions value for this ValidatedHazardousCommodityDescription.
     * 
     * @param specialProvisions
     */
    public void setSpecialProvisions(java.lang.String specialProvisions) {
        this.specialProvisions = specialProvisions;
    }


    /**
     * Gets the attributes value for this ValidatedHazardousCommodityDescription.
     * 
     * @return attributes
     */
    public com.owd.fedEx.ShipService.HazardousCommodityAttributeType[] getAttributes() {
        return attributes;
    }


    /**
     * Sets the attributes value for this ValidatedHazardousCommodityDescription.
     * 
     * @param attributes
     */
    public void setAttributes(com.owd.fedEx.ShipService.HazardousCommodityAttributeType[] attributes) {
        this.attributes = attributes;
    }

    public com.owd.fedEx.ShipService.HazardousCommodityAttributeType getAttributes(int i) {
        return attributes[i];
    }

    public void setAttributes(int i, com.owd.fedEx.ShipService.HazardousCommodityAttributeType value) {
        this.attributes[i] = value;
    }


    /**
     * Gets the authorization value for this ValidatedHazardousCommodityDescription.
     * 
     * @return authorization
     */
    public java.lang.String getAuthorization() {
        return authorization;
    }


    /**
     * Sets the authorization value for this ValidatedHazardousCommodityDescription.
     * 
     * @param authorization
     */
    public void setAuthorization(java.lang.String authorization) {
        this.authorization = authorization;
    }


    /**
     * Gets the labelText value for this ValidatedHazardousCommodityDescription.
     * 
     * @return labelText
     */
    public java.lang.String getLabelText() {
        return labelText;
    }


    /**
     * Sets the labelText value for this ValidatedHazardousCommodityDescription.
     * 
     * @param labelText
     */
    public void setLabelText(java.lang.String labelText) {
        this.labelText = labelText;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ValidatedHazardousCommodityDescription)) return false;
        ValidatedHazardousCommodityDescription other = (ValidatedHazardousCommodityDescription) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.id==null && other.getId()==null) || 
             (this.id!=null &&
              this.id.equals(other.getId()))) &&
            ((this.sequenceNumber==null && other.getSequenceNumber()==null) || 
             (this.sequenceNumber!=null &&
              this.sequenceNumber.equals(other.getSequenceNumber()))) &&
            ((this.packingGroup==null && other.getPackingGroup()==null) || 
             (this.packingGroup!=null &&
              this.packingGroup.equals(other.getPackingGroup()))) &&
            ((this.packingInstructions==null && other.getPackingInstructions()==null) || 
             (this.packingInstructions!=null &&
              this.packingInstructions.equals(other.getPackingInstructions()))) &&
            ((this.properShippingName==null && other.getProperShippingName()==null) || 
             (this.properShippingName!=null &&
              this.properShippingName.equals(other.getProperShippingName()))) &&
            ((this.properShippingNameAndDescription==null && other.getProperShippingNameAndDescription()==null) || 
             (this.properShippingNameAndDescription!=null &&
              this.properShippingNameAndDescription.equals(other.getProperShippingNameAndDescription()))) &&
            ((this.technicalName==null && other.getTechnicalName()==null) || 
             (this.technicalName!=null &&
              this.technicalName.equals(other.getTechnicalName()))) &&
            ((this.hazardClass==null && other.getHazardClass()==null) || 
             (this.hazardClass!=null &&
              this.hazardClass.equals(other.getHazardClass()))) &&
            ((this.subsidiaryClasses==null && other.getSubsidiaryClasses()==null) || 
             (this.subsidiaryClasses!=null &&
              java.util.Arrays.equals(this.subsidiaryClasses, other.getSubsidiaryClasses()))) &&
            ((this.symbols==null && other.getSymbols()==null) || 
             (this.symbols!=null &&
              this.symbols.equals(other.getSymbols()))) &&
            ((this.tunnelRestrictionCode==null && other.getTunnelRestrictionCode()==null) || 
             (this.tunnelRestrictionCode!=null &&
              this.tunnelRestrictionCode.equals(other.getTunnelRestrictionCode()))) &&
            ((this.specialProvisions==null && other.getSpecialProvisions()==null) || 
             (this.specialProvisions!=null &&
              this.specialProvisions.equals(other.getSpecialProvisions()))) &&
            ((this.attributes==null && other.getAttributes()==null) || 
             (this.attributes!=null &&
              java.util.Arrays.equals(this.attributes, other.getAttributes()))) &&
            ((this.authorization==null && other.getAuthorization()==null) || 
             (this.authorization!=null &&
              this.authorization.equals(other.getAuthorization()))) &&
            ((this.labelText==null && other.getLabelText()==null) || 
             (this.labelText!=null &&
              this.labelText.equals(other.getLabelText())));
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
        if (getId() != null) {
            _hashCode += getId().hashCode();
        }
        if (getSequenceNumber() != null) {
            _hashCode += getSequenceNumber().hashCode();
        }
        if (getPackingGroup() != null) {
            _hashCode += getPackingGroup().hashCode();
        }
        if (getPackingInstructions() != null) {
            _hashCode += getPackingInstructions().hashCode();
        }
        if (getProperShippingName() != null) {
            _hashCode += getProperShippingName().hashCode();
        }
        if (getProperShippingNameAndDescription() != null) {
            _hashCode += getProperShippingNameAndDescription().hashCode();
        }
        if (getTechnicalName() != null) {
            _hashCode += getTechnicalName().hashCode();
        }
        if (getHazardClass() != null) {
            _hashCode += getHazardClass().hashCode();
        }
        if (getSubsidiaryClasses() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getSubsidiaryClasses());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getSubsidiaryClasses(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getSymbols() != null) {
            _hashCode += getSymbols().hashCode();
        }
        if (getTunnelRestrictionCode() != null) {
            _hashCode += getTunnelRestrictionCode().hashCode();
        }
        if (getSpecialProvisions() != null) {
            _hashCode += getSpecialProvisions().hashCode();
        }
        if (getAttributes() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getAttributes());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getAttributes(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getAuthorization() != null) {
            _hashCode += getAuthorization().hashCode();
        }
        if (getLabelText() != null) {
            _hashCode += getLabelText().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ValidatedHazardousCommodityDescription.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ValidatedHazardousCommodityDescription"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sequenceNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "SequenceNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "nonNegativeInteger"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("packingGroup");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "PackingGroup"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "HazardousCommodityPackingGroupType"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("packingInstructions");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "PackingInstructions"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("properShippingName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ProperShippingName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("properShippingNameAndDescription");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ProperShippingNameAndDescription"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("technicalName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "TechnicalName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("hazardClass");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "HazardClass"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("subsidiaryClasses");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "SubsidiaryClasses"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("symbols");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Symbols"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tunnelRestrictionCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "TunnelRestrictionCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("specialProvisions");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "SpecialProvisions"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("attributes");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Attributes"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "HazardousCommodityAttributeType"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("authorization");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Authorization"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("labelText");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "LabelText"));
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
