/*
 * FreightAddressLabelDetail.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;


/**
 * Data required to produce the Freight handling-unit-level address
 * labels. Note that the number of UNIQUE labels (the N as in 1 of N,
 * 2 of N, etc.) is determined by total handling units.
 */
public class FreightAddressLabelDetail  implements java.io.Serializable {
    /** Specifies characteristics of a shipping document to be produced. */
    private com.owd.fedEx.ShipService.ShippingDocumentFormat format;
    private org.apache.axis.types.NonNegativeInteger copies;
    private com.owd.fedEx.ShipService.PageQuadrantType startingPosition;
    private com.owd.fedEx.ShipService.DocTabContent docTabContent;
    /** Describes the vertical position of an item relative to another
 * item. */
    private com.owd.fedEx.ShipService.RelativeVerticalPositionType customContentPosition;
    private com.owd.fedEx.ShipService.CustomLabelDetail customContent;

    public FreightAddressLabelDetail() {
    }


    /**
     * Gets the format value for this FreightAddressLabelDetail.
     * 
     * @return format Specifies characteristics of a shipping document to be produced.
     */
    public com.owd.fedEx.ShipService.ShippingDocumentFormat getFormat() {
        return format;
    }


    /**
     * Sets the format value for this FreightAddressLabelDetail.
     * 
     * @param format Specifies characteristics of a shipping document to be produced.
     */
    public void setFormat(com.owd.fedEx.ShipService.ShippingDocumentFormat format) {
        this.format = format;
    }


    /**
     * Gets the copies value for this FreightAddressLabelDetail.
     * 
     * @return copies
     */
    public org.apache.axis.types.NonNegativeInteger getCopies() {
        return copies;
    }


    /**
     * Sets the copies value for this FreightAddressLabelDetail.
     * 
     * @param copies
     */
    public void setCopies(org.apache.axis.types.NonNegativeInteger copies) {
        this.copies = copies;
    }


    /**
     * Gets the startingPosition value for this FreightAddressLabelDetail.
     * 
     * @return startingPosition
     */
    public com.owd.fedEx.ShipService.PageQuadrantType getStartingPosition() {
        return startingPosition;
    }


    /**
     * Sets the startingPosition value for this FreightAddressLabelDetail.
     * 
     * @param startingPosition
     */
    public void setStartingPosition(com.owd.fedEx.ShipService.PageQuadrantType startingPosition) {
        this.startingPosition = startingPosition;
    }


    /**
     * Gets the docTabContent value for this FreightAddressLabelDetail.
     * 
     * @return docTabContent
     */
    public com.owd.fedEx.ShipService.DocTabContent getDocTabContent() {
        return docTabContent;
    }


    /**
     * Sets the docTabContent value for this FreightAddressLabelDetail.
     * 
     * @param docTabContent
     */
    public void setDocTabContent(com.owd.fedEx.ShipService.DocTabContent docTabContent) {
        this.docTabContent = docTabContent;
    }


    /**
     * Gets the customContentPosition value for this FreightAddressLabelDetail.
     * 
     * @return customContentPosition Describes the vertical position of an item relative to another
 * item.
     */
    public com.owd.fedEx.ShipService.RelativeVerticalPositionType getCustomContentPosition() {
        return customContentPosition;
    }


    /**
     * Sets the customContentPosition value for this FreightAddressLabelDetail.
     * 
     * @param customContentPosition Describes the vertical position of an item relative to another
 * item.
     */
    public void setCustomContentPosition(com.owd.fedEx.ShipService.RelativeVerticalPositionType customContentPosition) {
        this.customContentPosition = customContentPosition;
    }


    /**
     * Gets the customContent value for this FreightAddressLabelDetail.
     * 
     * @return customContent
     */
    public com.owd.fedEx.ShipService.CustomLabelDetail getCustomContent() {
        return customContent;
    }


    /**
     * Sets the customContent value for this FreightAddressLabelDetail.
     * 
     * @param customContent
     */
    public void setCustomContent(com.owd.fedEx.ShipService.CustomLabelDetail customContent) {
        this.customContent = customContent;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof FreightAddressLabelDetail)) return false;
        FreightAddressLabelDetail other = (FreightAddressLabelDetail) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.format==null && other.getFormat()==null) || 
             (this.format!=null &&
              this.format.equals(other.getFormat()))) &&
            ((this.copies==null && other.getCopies()==null) || 
             (this.copies!=null &&
              this.copies.equals(other.getCopies()))) &&
            ((this.startingPosition==null && other.getStartingPosition()==null) || 
             (this.startingPosition!=null &&
              this.startingPosition.equals(other.getStartingPosition()))) &&
            ((this.docTabContent==null && other.getDocTabContent()==null) || 
             (this.docTabContent!=null &&
              this.docTabContent.equals(other.getDocTabContent()))) &&
            ((this.customContentPosition==null && other.getCustomContentPosition()==null) || 
             (this.customContentPosition!=null &&
              this.customContentPosition.equals(other.getCustomContentPosition()))) &&
            ((this.customContent==null && other.getCustomContent()==null) || 
             (this.customContent!=null &&
              this.customContent.equals(other.getCustomContent())));
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
        if (getFormat() != null) {
            _hashCode += getFormat().hashCode();
        }
        if (getCopies() != null) {
            _hashCode += getCopies().hashCode();
        }
        if (getStartingPosition() != null) {
            _hashCode += getStartingPosition().hashCode();
        }
        if (getDocTabContent() != null) {
            _hashCode += getDocTabContent().hashCode();
        }
        if (getCustomContentPosition() != null) {
            _hashCode += getCustomContentPosition().hashCode();
        }
        if (getCustomContent() != null) {
            _hashCode += getCustomContent().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(FreightAddressLabelDetail.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "FreightAddressLabelDetail"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("format");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Format"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ShippingDocumentFormat"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("copies");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Copies"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "nonNegativeInteger"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("startingPosition");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "StartingPosition"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "PageQuadrantType"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
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
