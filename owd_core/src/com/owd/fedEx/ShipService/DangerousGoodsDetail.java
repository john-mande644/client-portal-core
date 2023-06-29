/*
 * DangerousGoodsDetail.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;

public class DangerousGoodsDetail  implements java.io.Serializable {
    private java.lang.String uploadedTrackingNumber;
    /** Identifies the source of regulation for hazardous commodity data. */
    private com.owd.fedEx.ShipService.HazardousCommodityRegulationType regulation;
    private com.owd.fedEx.ShipService.DangerousGoodsAccessibilityType accessibility;
    private java.lang.Boolean cargoAircraftOnly;
    /** Indicates which kinds of hazardous content are in the current package. */
    private com.owd.fedEx.ShipService.HazardousCommodityOptionType[] options;
    private com.owd.fedEx.ShipService.DangerousGoodsPackingOptionType packingOption;
    private java.lang.String referenceId;
    /** Indicates one or more containers used to pack dangerous goods commodities. */
    private com.owd.fedEx.ShipService.DangerousGoodsContainer[] containers;
    /** Identifies number and type of packaging units for hazardous commodities. */
    private com.owd.fedEx.ShipService.HazardousCommodityPackagingDetail packaging;
    /** Specifies that name, title and place of the signatory responsible
 * for the dangerous goods shipment. */
    private com.owd.fedEx.ShipService.DangerousGoodsSignatory signatory;
    private java.lang.String emergencyContactNumber;
    private java.lang.String offeror;
    /** The descriptive data for a point-of-contact person. */
    private com.owd.fedEx.ShipService.Contact infectiousSubstanceResponsibleContact;
    private java.lang.String additionalHandling;
    private com.owd.fedEx.ShipService.RadioactivityDetail radioactivityDetail;

    public DangerousGoodsDetail() {
    }


    /**
     * Gets the uploadedTrackingNumber value for this DangerousGoodsDetail.
     * 
     * @return uploadedTrackingNumber
     */
    public java.lang.String getUploadedTrackingNumber() {
        return uploadedTrackingNumber;
    }


    /**
     * Sets the uploadedTrackingNumber value for this DangerousGoodsDetail.
     * 
     * @param uploadedTrackingNumber
     */
    public void setUploadedTrackingNumber(java.lang.String uploadedTrackingNumber) {
        this.uploadedTrackingNumber = uploadedTrackingNumber;
    }


    /**
     * Gets the regulation value for this DangerousGoodsDetail.
     * 
     * @return regulation Identifies the source of regulation for hazardous commodity data.
     */
    public com.owd.fedEx.ShipService.HazardousCommodityRegulationType getRegulation() {
        return regulation;
    }


    /**
     * Sets the regulation value for this DangerousGoodsDetail.
     * 
     * @param regulation Identifies the source of regulation for hazardous commodity data.
     */
    public void setRegulation(com.owd.fedEx.ShipService.HazardousCommodityRegulationType regulation) {
        this.regulation = regulation;
    }


    /**
     * Gets the accessibility value for this DangerousGoodsDetail.
     * 
     * @return accessibility
     */
    public com.owd.fedEx.ShipService.DangerousGoodsAccessibilityType getAccessibility() {
        return accessibility;
    }


    /**
     * Sets the accessibility value for this DangerousGoodsDetail.
     * 
     * @param accessibility
     */
    public void setAccessibility(com.owd.fedEx.ShipService.DangerousGoodsAccessibilityType accessibility) {
        this.accessibility = accessibility;
    }


    /**
     * Gets the cargoAircraftOnly value for this DangerousGoodsDetail.
     * 
     * @return cargoAircraftOnly
     */
    public java.lang.Boolean getCargoAircraftOnly() {
        return cargoAircraftOnly;
    }


    /**
     * Sets the cargoAircraftOnly value for this DangerousGoodsDetail.
     * 
     * @param cargoAircraftOnly
     */
    public void setCargoAircraftOnly(java.lang.Boolean cargoAircraftOnly) {
        this.cargoAircraftOnly = cargoAircraftOnly;
    }


    /**
     * Gets the options value for this DangerousGoodsDetail.
     * 
     * @return options Indicates which kinds of hazardous content are in the current package.
     */
    public com.owd.fedEx.ShipService.HazardousCommodityOptionType[] getOptions() {
        return options;
    }


    /**
     * Sets the options value for this DangerousGoodsDetail.
     * 
     * @param options Indicates which kinds of hazardous content are in the current package.
     */
    public void setOptions(com.owd.fedEx.ShipService.HazardousCommodityOptionType[] options) {
        this.options = options;
    }

    public com.owd.fedEx.ShipService.HazardousCommodityOptionType getOptions(int i) {
        return options[i];
    }

    public void setOptions(int i, com.owd.fedEx.ShipService.HazardousCommodityOptionType value) {
        this.options[i] = value;
    }


    /**
     * Gets the packingOption value for this DangerousGoodsDetail.
     * 
     * @return packingOption
     */
    public com.owd.fedEx.ShipService.DangerousGoodsPackingOptionType getPackingOption() {
        return packingOption;
    }


    /**
     * Sets the packingOption value for this DangerousGoodsDetail.
     * 
     * @param packingOption
     */
    public void setPackingOption(com.owd.fedEx.ShipService.DangerousGoodsPackingOptionType packingOption) {
        this.packingOption = packingOption;
    }


    /**
     * Gets the referenceId value for this DangerousGoodsDetail.
     * 
     * @return referenceId
     */
    public java.lang.String getReferenceId() {
        return referenceId;
    }


    /**
     * Sets the referenceId value for this DangerousGoodsDetail.
     * 
     * @param referenceId
     */
    public void setReferenceId(java.lang.String referenceId) {
        this.referenceId = referenceId;
    }


    /**
     * Gets the containers value for this DangerousGoodsDetail.
     * 
     * @return containers Indicates one or more containers used to pack dangerous goods commodities.
     */
    public com.owd.fedEx.ShipService.DangerousGoodsContainer[] getContainers() {
        return containers;
    }


    /**
     * Sets the containers value for this DangerousGoodsDetail.
     * 
     * @param containers Indicates one or more containers used to pack dangerous goods commodities.
     */
    public void setContainers(com.owd.fedEx.ShipService.DangerousGoodsContainer[] containers) {
        this.containers = containers;
    }

    public com.owd.fedEx.ShipService.DangerousGoodsContainer getContainers(int i) {
        return containers[i];
    }

    public void setContainers(int i, com.owd.fedEx.ShipService.DangerousGoodsContainer value) {
        this.containers[i] = value;
    }


    /**
     * Gets the packaging value for this DangerousGoodsDetail.
     * 
     * @return packaging Identifies number and type of packaging units for hazardous commodities.
     */
    public com.owd.fedEx.ShipService.HazardousCommodityPackagingDetail getPackaging() {
        return packaging;
    }


    /**
     * Sets the packaging value for this DangerousGoodsDetail.
     * 
     * @param packaging Identifies number and type of packaging units for hazardous commodities.
     */
    public void setPackaging(com.owd.fedEx.ShipService.HazardousCommodityPackagingDetail packaging) {
        this.packaging = packaging;
    }


    /**
     * Gets the signatory value for this DangerousGoodsDetail.
     * 
     * @return signatory Specifies that name, title and place of the signatory responsible
 * for the dangerous goods shipment.
     */
    public com.owd.fedEx.ShipService.DangerousGoodsSignatory getSignatory() {
        return signatory;
    }


    /**
     * Sets the signatory value for this DangerousGoodsDetail.
     * 
     * @param signatory Specifies that name, title and place of the signatory responsible
 * for the dangerous goods shipment.
     */
    public void setSignatory(com.owd.fedEx.ShipService.DangerousGoodsSignatory signatory) {
        this.signatory = signatory;
    }


    /**
     * Gets the emergencyContactNumber value for this DangerousGoodsDetail.
     * 
     * @return emergencyContactNumber
     */
    public java.lang.String getEmergencyContactNumber() {
        return emergencyContactNumber;
    }


    /**
     * Sets the emergencyContactNumber value for this DangerousGoodsDetail.
     * 
     * @param emergencyContactNumber
     */
    public void setEmergencyContactNumber(java.lang.String emergencyContactNumber) {
        this.emergencyContactNumber = emergencyContactNumber;
    }


    /**
     * Gets the offeror value for this DangerousGoodsDetail.
     * 
     * @return offeror
     */
    public java.lang.String getOfferor() {
        return offeror;
    }


    /**
     * Sets the offeror value for this DangerousGoodsDetail.
     * 
     * @param offeror
     */
    public void setOfferor(java.lang.String offeror) {
        this.offeror = offeror;
    }


    /**
     * Gets the infectiousSubstanceResponsibleContact value for this DangerousGoodsDetail.
     * 
     * @return infectiousSubstanceResponsibleContact The descriptive data for a point-of-contact person.
     */
    public com.owd.fedEx.ShipService.Contact getInfectiousSubstanceResponsibleContact() {
        return infectiousSubstanceResponsibleContact;
    }


    /**
     * Sets the infectiousSubstanceResponsibleContact value for this DangerousGoodsDetail.
     * 
     * @param infectiousSubstanceResponsibleContact The descriptive data for a point-of-contact person.
     */
    public void setInfectiousSubstanceResponsibleContact(com.owd.fedEx.ShipService.Contact infectiousSubstanceResponsibleContact) {
        this.infectiousSubstanceResponsibleContact = infectiousSubstanceResponsibleContact;
    }


    /**
     * Gets the additionalHandling value for this DangerousGoodsDetail.
     * 
     * @return additionalHandling
     */
    public java.lang.String getAdditionalHandling() {
        return additionalHandling;
    }


    /**
     * Sets the additionalHandling value for this DangerousGoodsDetail.
     * 
     * @param additionalHandling
     */
    public void setAdditionalHandling(java.lang.String additionalHandling) {
        this.additionalHandling = additionalHandling;
    }


    /**
     * Gets the radioactivityDetail value for this DangerousGoodsDetail.
     * 
     * @return radioactivityDetail
     */
    public com.owd.fedEx.ShipService.RadioactivityDetail getRadioactivityDetail() {
        return radioactivityDetail;
    }


    /**
     * Sets the radioactivityDetail value for this DangerousGoodsDetail.
     * 
     * @param radioactivityDetail
     */
    public void setRadioactivityDetail(com.owd.fedEx.ShipService.RadioactivityDetail radioactivityDetail) {
        this.radioactivityDetail = radioactivityDetail;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DangerousGoodsDetail)) return false;
        DangerousGoodsDetail other = (DangerousGoodsDetail) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.uploadedTrackingNumber==null && other.getUploadedTrackingNumber()==null) || 
             (this.uploadedTrackingNumber!=null &&
              this.uploadedTrackingNumber.equals(other.getUploadedTrackingNumber()))) &&
            ((this.regulation==null && other.getRegulation()==null) || 
             (this.regulation!=null &&
              this.regulation.equals(other.getRegulation()))) &&
            ((this.accessibility==null && other.getAccessibility()==null) || 
             (this.accessibility!=null &&
              this.accessibility.equals(other.getAccessibility()))) &&
            ((this.cargoAircraftOnly==null && other.getCargoAircraftOnly()==null) || 
             (this.cargoAircraftOnly!=null &&
              this.cargoAircraftOnly.equals(other.getCargoAircraftOnly()))) &&
            ((this.options==null && other.getOptions()==null) || 
             (this.options!=null &&
              java.util.Arrays.equals(this.options, other.getOptions()))) &&
            ((this.packingOption==null && other.getPackingOption()==null) || 
             (this.packingOption!=null &&
              this.packingOption.equals(other.getPackingOption()))) &&
            ((this.referenceId==null && other.getReferenceId()==null) || 
             (this.referenceId!=null &&
              this.referenceId.equals(other.getReferenceId()))) &&
            ((this.containers==null && other.getContainers()==null) || 
             (this.containers!=null &&
              java.util.Arrays.equals(this.containers, other.getContainers()))) &&
            ((this.packaging==null && other.getPackaging()==null) || 
             (this.packaging!=null &&
              this.packaging.equals(other.getPackaging()))) &&
            ((this.signatory==null && other.getSignatory()==null) || 
             (this.signatory!=null &&
              this.signatory.equals(other.getSignatory()))) &&
            ((this.emergencyContactNumber==null && other.getEmergencyContactNumber()==null) || 
             (this.emergencyContactNumber!=null &&
              this.emergencyContactNumber.equals(other.getEmergencyContactNumber()))) &&
            ((this.offeror==null && other.getOfferor()==null) || 
             (this.offeror!=null &&
              this.offeror.equals(other.getOfferor()))) &&
            ((this.infectiousSubstanceResponsibleContact==null && other.getInfectiousSubstanceResponsibleContact()==null) || 
             (this.infectiousSubstanceResponsibleContact!=null &&
              this.infectiousSubstanceResponsibleContact.equals(other.getInfectiousSubstanceResponsibleContact()))) &&
            ((this.additionalHandling==null && other.getAdditionalHandling()==null) || 
             (this.additionalHandling!=null &&
              this.additionalHandling.equals(other.getAdditionalHandling()))) &&
            ((this.radioactivityDetail==null && other.getRadioactivityDetail()==null) || 
             (this.radioactivityDetail!=null &&
              this.radioactivityDetail.equals(other.getRadioactivityDetail())));
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
        if (getUploadedTrackingNumber() != null) {
            _hashCode += getUploadedTrackingNumber().hashCode();
        }
        if (getRegulation() != null) {
            _hashCode += getRegulation().hashCode();
        }
        if (getAccessibility() != null) {
            _hashCode += getAccessibility().hashCode();
        }
        if (getCargoAircraftOnly() != null) {
            _hashCode += getCargoAircraftOnly().hashCode();
        }
        if (getOptions() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getOptions());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getOptions(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getPackingOption() != null) {
            _hashCode += getPackingOption().hashCode();
        }
        if (getReferenceId() != null) {
            _hashCode += getReferenceId().hashCode();
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
        if (getPackaging() != null) {
            _hashCode += getPackaging().hashCode();
        }
        if (getSignatory() != null) {
            _hashCode += getSignatory().hashCode();
        }
        if (getEmergencyContactNumber() != null) {
            _hashCode += getEmergencyContactNumber().hashCode();
        }
        if (getOfferor() != null) {
            _hashCode += getOfferor().hashCode();
        }
        if (getInfectiousSubstanceResponsibleContact() != null) {
            _hashCode += getInfectiousSubstanceResponsibleContact().hashCode();
        }
        if (getAdditionalHandling() != null) {
            _hashCode += getAdditionalHandling().hashCode();
        }
        if (getRadioactivityDetail() != null) {
            _hashCode += getRadioactivityDetail().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DangerousGoodsDetail.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "DangerousGoodsDetail"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("uploadedTrackingNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "UploadedTrackingNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("regulation");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Regulation"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "HazardousCommodityRegulationType"));
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
        elemField.setFieldName("options");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Options"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "HazardousCommodityOptionType"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("packingOption");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "PackingOption"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "DangerousGoodsPackingOptionType"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("referenceId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ReferenceId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("containers");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Containers"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "DangerousGoodsContainer"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("packaging");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Packaging"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "HazardousCommodityPackagingDetail"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("signatory");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Signatory"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "DangerousGoodsSignatory"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("emergencyContactNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "EmergencyContactNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("offeror");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Offeror"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("infectiousSubstanceResponsibleContact");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "InfectiousSubstanceResponsibleContact"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Contact"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("additionalHandling");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "AdditionalHandling"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("radioactivityDetail");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "RadioactivityDetail"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "RadioactivityDetail"));
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
