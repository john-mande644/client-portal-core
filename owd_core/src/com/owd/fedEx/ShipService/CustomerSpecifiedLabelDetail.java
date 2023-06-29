/*
 * CustomerSpecifiedLabelDetail.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;


/**
 * Allows customer-specified control of label content.
 */
public class CustomerSpecifiedLabelDetail  implements java.io.Serializable {
    private com.owd.fedEx.ShipService.DocTabContent docTabContent;
    /** Describes the vertical position of an item relative to another
 * item. */
    private com.owd.fedEx.ShipService.RelativeVerticalPositionType customContentPosition;
    private com.owd.fedEx.ShipService.CustomLabelDetail customContent;
    private com.owd.fedEx.ShipService.ConfigurableLabelReferenceEntry[] configurableReferenceEntries;
    /** Controls which data/sections will be suppressed. */
    private com.owd.fedEx.ShipService.LabelMaskableDataType[] maskedData;
    private com.owd.fedEx.ShipService.SecondaryBarcodeType secondaryBarcode;
    /** Identifies the representation of human-readable text. */
    private com.owd.fedEx.ShipService.Localization termsAndConditionsLocalization;
    private com.owd.fedEx.ShipService.RegulatoryLabelContentDetail[] regulatoryLabels;
    /** Controls the number of additional copies of supplemental labels. */
    private com.owd.fedEx.ShipService.AdditionalLabelsDetail[] additionalLabels;
    private org.apache.axis.types.NonNegativeInteger airWaybillSuppressionCount;

    public CustomerSpecifiedLabelDetail() {
    }


    /**
     * Gets the docTabContent value for this CustomerSpecifiedLabelDetail.
     * 
     * @return docTabContent
     */
    public com.owd.fedEx.ShipService.DocTabContent getDocTabContent() {
        return docTabContent;
    }


    /**
     * Sets the docTabContent value for this CustomerSpecifiedLabelDetail.
     * 
     * @param docTabContent
     */
    public void setDocTabContent(com.owd.fedEx.ShipService.DocTabContent docTabContent) {
        this.docTabContent = docTabContent;
    }


    /**
     * Gets the customContentPosition value for this CustomerSpecifiedLabelDetail.
     * 
     * @return customContentPosition Describes the vertical position of an item relative to another
 * item.
     */
    public com.owd.fedEx.ShipService.RelativeVerticalPositionType getCustomContentPosition() {
        return customContentPosition;
    }


    /**
     * Sets the customContentPosition value for this CustomerSpecifiedLabelDetail.
     * 
     * @param customContentPosition Describes the vertical position of an item relative to another
 * item.
     */
    public void setCustomContentPosition(com.owd.fedEx.ShipService.RelativeVerticalPositionType customContentPosition) {
        this.customContentPosition = customContentPosition;
    }


    /**
     * Gets the customContent value for this CustomerSpecifiedLabelDetail.
     * 
     * @return customContent
     */
    public com.owd.fedEx.ShipService.CustomLabelDetail getCustomContent() {
        return customContent;
    }


    /**
     * Sets the customContent value for this CustomerSpecifiedLabelDetail.
     * 
     * @param customContent
     */
    public void setCustomContent(com.owd.fedEx.ShipService.CustomLabelDetail customContent) {
        this.customContent = customContent;
    }


    /**
     * Gets the configurableReferenceEntries value for this CustomerSpecifiedLabelDetail.
     * 
     * @return configurableReferenceEntries
     */
    public com.owd.fedEx.ShipService.ConfigurableLabelReferenceEntry[] getConfigurableReferenceEntries() {
        return configurableReferenceEntries;
    }


    /**
     * Sets the configurableReferenceEntries value for this CustomerSpecifiedLabelDetail.
     * 
     * @param configurableReferenceEntries
     */
    public void setConfigurableReferenceEntries(com.owd.fedEx.ShipService.ConfigurableLabelReferenceEntry[] configurableReferenceEntries) {
        this.configurableReferenceEntries = configurableReferenceEntries;
    }

    public com.owd.fedEx.ShipService.ConfigurableLabelReferenceEntry getConfigurableReferenceEntries(int i) {
        return configurableReferenceEntries[i];
    }

    public void setConfigurableReferenceEntries(int i, com.owd.fedEx.ShipService.ConfigurableLabelReferenceEntry value) {
        this.configurableReferenceEntries[i] = value;
    }


    /**
     * Gets the maskedData value for this CustomerSpecifiedLabelDetail.
     * 
     * @return maskedData Controls which data/sections will be suppressed.
     */
    public com.owd.fedEx.ShipService.LabelMaskableDataType[] getMaskedData() {
        return maskedData;
    }


    /**
     * Sets the maskedData value for this CustomerSpecifiedLabelDetail.
     * 
     * @param maskedData Controls which data/sections will be suppressed.
     */
    public void setMaskedData(com.owd.fedEx.ShipService.LabelMaskableDataType[] maskedData) {
        this.maskedData = maskedData;
    }

    public com.owd.fedEx.ShipService.LabelMaskableDataType getMaskedData(int i) {
        return maskedData[i];
    }

    public void setMaskedData(int i, com.owd.fedEx.ShipService.LabelMaskableDataType value) {
        this.maskedData[i] = value;
    }


    /**
     * Gets the secondaryBarcode value for this CustomerSpecifiedLabelDetail.
     * 
     * @return secondaryBarcode
     */
    public com.owd.fedEx.ShipService.SecondaryBarcodeType getSecondaryBarcode() {
        return secondaryBarcode;
    }


    /**
     * Sets the secondaryBarcode value for this CustomerSpecifiedLabelDetail.
     * 
     * @param secondaryBarcode
     */
    public void setSecondaryBarcode(com.owd.fedEx.ShipService.SecondaryBarcodeType secondaryBarcode) {
        this.secondaryBarcode = secondaryBarcode;
    }


    /**
     * Gets the termsAndConditionsLocalization value for this CustomerSpecifiedLabelDetail.
     * 
     * @return termsAndConditionsLocalization Identifies the representation of human-readable text.
     */
    public com.owd.fedEx.ShipService.Localization getTermsAndConditionsLocalization() {
        return termsAndConditionsLocalization;
    }


    /**
     * Sets the termsAndConditionsLocalization value for this CustomerSpecifiedLabelDetail.
     * 
     * @param termsAndConditionsLocalization Identifies the representation of human-readable text.
     */
    public void setTermsAndConditionsLocalization(com.owd.fedEx.ShipService.Localization termsAndConditionsLocalization) {
        this.termsAndConditionsLocalization = termsAndConditionsLocalization;
    }


    /**
     * Gets the regulatoryLabels value for this CustomerSpecifiedLabelDetail.
     * 
     * @return regulatoryLabels
     */
    public com.owd.fedEx.ShipService.RegulatoryLabelContentDetail[] getRegulatoryLabels() {
        return regulatoryLabels;
    }


    /**
     * Sets the regulatoryLabels value for this CustomerSpecifiedLabelDetail.
     * 
     * @param regulatoryLabels
     */
    public void setRegulatoryLabels(com.owd.fedEx.ShipService.RegulatoryLabelContentDetail[] regulatoryLabels) {
        this.regulatoryLabels = regulatoryLabels;
    }

    public com.owd.fedEx.ShipService.RegulatoryLabelContentDetail getRegulatoryLabels(int i) {
        return regulatoryLabels[i];
    }

    public void setRegulatoryLabels(int i, com.owd.fedEx.ShipService.RegulatoryLabelContentDetail value) {
        this.regulatoryLabels[i] = value;
    }


    /**
     * Gets the additionalLabels value for this CustomerSpecifiedLabelDetail.
     * 
     * @return additionalLabels Controls the number of additional copies of supplemental labels.
     */
    public com.owd.fedEx.ShipService.AdditionalLabelsDetail[] getAdditionalLabels() {
        return additionalLabels;
    }


    /**
     * Sets the additionalLabels value for this CustomerSpecifiedLabelDetail.
     * 
     * @param additionalLabels Controls the number of additional copies of supplemental labels.
     */
    public void setAdditionalLabels(com.owd.fedEx.ShipService.AdditionalLabelsDetail[] additionalLabels) {
        this.additionalLabels = additionalLabels;
    }

    public com.owd.fedEx.ShipService.AdditionalLabelsDetail getAdditionalLabels(int i) {
        return additionalLabels[i];
    }

    public void setAdditionalLabels(int i, com.owd.fedEx.ShipService.AdditionalLabelsDetail value) {
        this.additionalLabels[i] = value;
    }


    /**
     * Gets the airWaybillSuppressionCount value for this CustomerSpecifiedLabelDetail.
     * 
     * @return airWaybillSuppressionCount
     */
    public org.apache.axis.types.NonNegativeInteger getAirWaybillSuppressionCount() {
        return airWaybillSuppressionCount;
    }


    /**
     * Sets the airWaybillSuppressionCount value for this CustomerSpecifiedLabelDetail.
     * 
     * @param airWaybillSuppressionCount
     */
    public void setAirWaybillSuppressionCount(org.apache.axis.types.NonNegativeInteger airWaybillSuppressionCount) {
        this.airWaybillSuppressionCount = airWaybillSuppressionCount;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CustomerSpecifiedLabelDetail)) return false;
        CustomerSpecifiedLabelDetail other = (CustomerSpecifiedLabelDetail) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.docTabContent==null && other.getDocTabContent()==null) || 
             (this.docTabContent!=null &&
              this.docTabContent.equals(other.getDocTabContent()))) &&
            ((this.customContentPosition==null && other.getCustomContentPosition()==null) || 
             (this.customContentPosition!=null &&
              this.customContentPosition.equals(other.getCustomContentPosition()))) &&
            ((this.customContent==null && other.getCustomContent()==null) || 
             (this.customContent!=null &&
              this.customContent.equals(other.getCustomContent()))) &&
            ((this.configurableReferenceEntries==null && other.getConfigurableReferenceEntries()==null) || 
             (this.configurableReferenceEntries!=null &&
              java.util.Arrays.equals(this.configurableReferenceEntries, other.getConfigurableReferenceEntries()))) &&
            ((this.maskedData==null && other.getMaskedData()==null) || 
             (this.maskedData!=null &&
              java.util.Arrays.equals(this.maskedData, other.getMaskedData()))) &&
            ((this.secondaryBarcode==null && other.getSecondaryBarcode()==null) || 
             (this.secondaryBarcode!=null &&
              this.secondaryBarcode.equals(other.getSecondaryBarcode()))) &&
            ((this.termsAndConditionsLocalization==null && other.getTermsAndConditionsLocalization()==null) || 
             (this.termsAndConditionsLocalization!=null &&
              this.termsAndConditionsLocalization.equals(other.getTermsAndConditionsLocalization()))) &&
            ((this.regulatoryLabels==null && other.getRegulatoryLabels()==null) || 
             (this.regulatoryLabels!=null &&
              java.util.Arrays.equals(this.regulatoryLabels, other.getRegulatoryLabels()))) &&
            ((this.additionalLabels==null && other.getAdditionalLabels()==null) || 
             (this.additionalLabels!=null &&
              java.util.Arrays.equals(this.additionalLabels, other.getAdditionalLabels()))) &&
            ((this.airWaybillSuppressionCount==null && other.getAirWaybillSuppressionCount()==null) || 
             (this.airWaybillSuppressionCount!=null &&
              this.airWaybillSuppressionCount.equals(other.getAirWaybillSuppressionCount())));
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
        if (getDocTabContent() != null) {
            _hashCode += getDocTabContent().hashCode();
        }
        if (getCustomContentPosition() != null) {
            _hashCode += getCustomContentPosition().hashCode();
        }
        if (getCustomContent() != null) {
            _hashCode += getCustomContent().hashCode();
        }
        if (getConfigurableReferenceEntries() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getConfigurableReferenceEntries());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getConfigurableReferenceEntries(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getMaskedData() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getMaskedData());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getMaskedData(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getSecondaryBarcode() != null) {
            _hashCode += getSecondaryBarcode().hashCode();
        }
        if (getTermsAndConditionsLocalization() != null) {
            _hashCode += getTermsAndConditionsLocalization().hashCode();
        }
        if (getRegulatoryLabels() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getRegulatoryLabels());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getRegulatoryLabels(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getAdditionalLabels() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getAdditionalLabels());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getAdditionalLabels(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getAirWaybillSuppressionCount() != null) {
            _hashCode += getAirWaybillSuppressionCount().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CustomerSpecifiedLabelDetail.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "CustomerSpecifiedLabelDetail"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("docTabContent");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "DocTabContent"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "DocTabContent"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("customContentPosition");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "CustomContentPosition"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "RelativeVerticalPositionType"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("customContent");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "CustomContent"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "CustomLabelDetail"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("configurableReferenceEntries");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ConfigurableReferenceEntries"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ConfigurableLabelReferenceEntry"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("maskedData");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "MaskedData"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "LabelMaskableDataType"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("secondaryBarcode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "SecondaryBarcode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "SecondaryBarcodeType"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("termsAndConditionsLocalization");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "TermsAndConditionsLocalization"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Localization"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("regulatoryLabels");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "RegulatoryLabels"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "RegulatoryLabelContentDetail"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("additionalLabels");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "AdditionalLabels"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "AdditionalLabelsDetail"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("airWaybillSuppressionCount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "AirWaybillSuppressionCount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "nonNegativeInteger"));
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
