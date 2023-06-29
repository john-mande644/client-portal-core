/*
 * SmartPostShipmentDetail.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;


/**
 * Data required for shipments handled under the SMART_POST and GROUND_SMART_POST
 * service types.
 */
public class SmartPostShipmentDetail  implements java.io.Serializable {
    private com.owd.fedEx.ShipService.SmartPostShipmentProcessingOptionsRequested processingOptionsRequested;
    private com.owd.fedEx.ShipService.SmartPostIndiciaType indicia;
    /** These values are mutually exclusive; at most one of them can be
 * attached to a SmartPost shipment. */
    private com.owd.fedEx.ShipService.SmartPostAncillaryEndorsementType ancillaryEndorsement;
    private java.lang.String hubId;
    private java.lang.String customerManifestId;

    public SmartPostShipmentDetail() {
    }


    /**
     * Gets the processingOptionsRequested value for this SmartPostShipmentDetail.
     * 
     * @return processingOptionsRequested
     */
    public com.owd.fedEx.ShipService.SmartPostShipmentProcessingOptionsRequested getProcessingOptionsRequested() {
        return processingOptionsRequested;
    }


    /**
     * Sets the processingOptionsRequested value for this SmartPostShipmentDetail.
     * 
     * @param processingOptionsRequested
     */
    public void setProcessingOptionsRequested(com.owd.fedEx.ShipService.SmartPostShipmentProcessingOptionsRequested processingOptionsRequested) {
        this.processingOptionsRequested = processingOptionsRequested;
    }


    /**
     * Gets the indicia value for this SmartPostShipmentDetail.
     * 
     * @return indicia
     */
    public com.owd.fedEx.ShipService.SmartPostIndiciaType getIndicia() {
        return indicia;
    }


    /**
     * Sets the indicia value for this SmartPostShipmentDetail.
     * 
     * @param indicia
     */
    public void setIndicia(com.owd.fedEx.ShipService.SmartPostIndiciaType indicia) {
        this.indicia = indicia;
    }


    /**
     * Gets the ancillaryEndorsement value for this SmartPostShipmentDetail.
     * 
     * @return ancillaryEndorsement These values are mutually exclusive; at most one of them can be
 * attached to a SmartPost shipment.
     */
    public com.owd.fedEx.ShipService.SmartPostAncillaryEndorsementType getAncillaryEndorsement() {
        return ancillaryEndorsement;
    }


    /**
     * Sets the ancillaryEndorsement value for this SmartPostShipmentDetail.
     * 
     * @param ancillaryEndorsement These values are mutually exclusive; at most one of them can be
 * attached to a SmartPost shipment.
     */
    public void setAncillaryEndorsement(com.owd.fedEx.ShipService.SmartPostAncillaryEndorsementType ancillaryEndorsement) {
        this.ancillaryEndorsement = ancillaryEndorsement;
    }


    /**
     * Gets the hubId value for this SmartPostShipmentDetail.
     * 
     * @return hubId
     */
    public java.lang.String getHubId() {
        return hubId;
    }


    /**
     * Sets the hubId value for this SmartPostShipmentDetail.
     * 
     * @param hubId
     */
    public void setHubId(java.lang.String hubId) {
        this.hubId = hubId;
    }


    /**
     * Gets the customerManifestId value for this SmartPostShipmentDetail.
     * 
     * @return customerManifestId
     */
    public java.lang.String getCustomerManifestId() {
        return customerManifestId;
    }


    /**
     * Sets the customerManifestId value for this SmartPostShipmentDetail.
     * 
     * @param customerManifestId
     */
    public void setCustomerManifestId(java.lang.String customerManifestId) {
        this.customerManifestId = customerManifestId;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SmartPostShipmentDetail)) return false;
        SmartPostShipmentDetail other = (SmartPostShipmentDetail) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.processingOptionsRequested==null && other.getProcessingOptionsRequested()==null) || 
             (this.processingOptionsRequested!=null &&
              this.processingOptionsRequested.equals(other.getProcessingOptionsRequested()))) &&
            ((this.indicia==null && other.getIndicia()==null) || 
             (this.indicia!=null &&
              this.indicia.equals(other.getIndicia()))) &&
            ((this.ancillaryEndorsement==null && other.getAncillaryEndorsement()==null) || 
             (this.ancillaryEndorsement!=null &&
              this.ancillaryEndorsement.equals(other.getAncillaryEndorsement()))) &&
            ((this.hubId==null && other.getHubId()==null) || 
             (this.hubId!=null &&
              this.hubId.equals(other.getHubId()))) &&
            ((this.customerManifestId==null && other.getCustomerManifestId()==null) || 
             (this.customerManifestId!=null &&
              this.customerManifestId.equals(other.getCustomerManifestId())));
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
        if (getProcessingOptionsRequested() != null) {
            _hashCode += getProcessingOptionsRequested().hashCode();
        }
        if (getIndicia() != null) {
            _hashCode += getIndicia().hashCode();
        }
        if (getAncillaryEndorsement() != null) {
            _hashCode += getAncillaryEndorsement().hashCode();
        }
        if (getHubId() != null) {
            _hashCode += getHubId().hashCode();
        }
        if (getCustomerManifestId() != null) {
            _hashCode += getCustomerManifestId().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SmartPostShipmentDetail.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "SmartPostShipmentDetail"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("processingOptionsRequested");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ProcessingOptionsRequested"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "SmartPostShipmentProcessingOptionsRequested"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("indicia");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Indicia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "SmartPostIndiciaType"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ancillaryEndorsement");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "AncillaryEndorsement"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "SmartPostAncillaryEndorsementType"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("hubId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "HubId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("customerManifestId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "CustomerManifestId"));
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
