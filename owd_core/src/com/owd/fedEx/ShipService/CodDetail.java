/*
 * CodDetail.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;


/**
 * Descriptive data required for a FedEx COD (Collect-On-Delivery)
 * shipment.
 */
public class CodDetail  implements java.io.Serializable {
    private com.owd.fedEx.ShipService.Money codCollectionAmount;
    private com.owd.fedEx.ShipService.CodAddTransportationChargesDetail addTransportationChargesDetail;
    /** Identifies the type of funds FedEx should collect upon shipment
 * delivery. */
    private com.owd.fedEx.ShipService.CodCollectionType collectionType;
    private com.owd.fedEx.ShipService.Party codRecipient;
    private com.owd.fedEx.ShipService.ContactAndAddress financialInstitutionContactAndAddress;
    private java.lang.String remitToName;
    private com.owd.fedEx.ShipService.CodReturnReferenceIndicatorType referenceIndicator;
    private com.owd.fedEx.ShipService.TrackingId returnTrackingId;

    public CodDetail() {
    }


    /**
     * Gets the codCollectionAmount value for this CodDetail.
     * 
     * @return codCollectionAmount
     */
    public com.owd.fedEx.ShipService.Money getCodCollectionAmount() {
        return codCollectionAmount;
    }


    /**
     * Sets the codCollectionAmount value for this CodDetail.
     * 
     * @param codCollectionAmount
     */
    public void setCodCollectionAmount(com.owd.fedEx.ShipService.Money codCollectionAmount) {
        this.codCollectionAmount = codCollectionAmount;
    }


    /**
     * Gets the addTransportationChargesDetail value for this CodDetail.
     * 
     * @return addTransportationChargesDetail
     */
    public com.owd.fedEx.ShipService.CodAddTransportationChargesDetail getAddTransportationChargesDetail() {
        return addTransportationChargesDetail;
    }


    /**
     * Sets the addTransportationChargesDetail value for this CodDetail.
     * 
     * @param addTransportationChargesDetail
     */
    public void setAddTransportationChargesDetail(com.owd.fedEx.ShipService.CodAddTransportationChargesDetail addTransportationChargesDetail) {
        this.addTransportationChargesDetail = addTransportationChargesDetail;
    }


    /**
     * Gets the collectionType value for this CodDetail.
     * 
     * @return collectionType Identifies the type of funds FedEx should collect upon shipment
 * delivery.
     */
    public com.owd.fedEx.ShipService.CodCollectionType getCollectionType() {
        return collectionType;
    }


    /**
     * Sets the collectionType value for this CodDetail.
     * 
     * @param collectionType Identifies the type of funds FedEx should collect upon shipment
 * delivery.
     */
    public void setCollectionType(com.owd.fedEx.ShipService.CodCollectionType collectionType) {
        this.collectionType = collectionType;
    }


    /**
     * Gets the codRecipient value for this CodDetail.
     * 
     * @return codRecipient
     */
    public com.owd.fedEx.ShipService.Party getCodRecipient() {
        return codRecipient;
    }


    /**
     * Sets the codRecipient value for this CodDetail.
     * 
     * @param codRecipient
     */
    public void setCodRecipient(com.owd.fedEx.ShipService.Party codRecipient) {
        this.codRecipient = codRecipient;
    }


    /**
     * Gets the financialInstitutionContactAndAddress value for this CodDetail.
     * 
     * @return financialInstitutionContactAndAddress
     */
    public com.owd.fedEx.ShipService.ContactAndAddress getFinancialInstitutionContactAndAddress() {
        return financialInstitutionContactAndAddress;
    }


    /**
     * Sets the financialInstitutionContactAndAddress value for this CodDetail.
     * 
     * @param financialInstitutionContactAndAddress
     */
    public void setFinancialInstitutionContactAndAddress(com.owd.fedEx.ShipService.ContactAndAddress financialInstitutionContactAndAddress) {
        this.financialInstitutionContactAndAddress = financialInstitutionContactAndAddress;
    }


    /**
     * Gets the remitToName value for this CodDetail.
     * 
     * @return remitToName
     */
    public java.lang.String getRemitToName() {
        return remitToName;
    }


    /**
     * Sets the remitToName value for this CodDetail.
     * 
     * @param remitToName
     */
    public void setRemitToName(java.lang.String remitToName) {
        this.remitToName = remitToName;
    }


    /**
     * Gets the referenceIndicator value for this CodDetail.
     * 
     * @return referenceIndicator
     */
    public com.owd.fedEx.ShipService.CodReturnReferenceIndicatorType getReferenceIndicator() {
        return referenceIndicator;
    }


    /**
     * Sets the referenceIndicator value for this CodDetail.
     * 
     * @param referenceIndicator
     */
    public void setReferenceIndicator(com.owd.fedEx.ShipService.CodReturnReferenceIndicatorType referenceIndicator) {
        this.referenceIndicator = referenceIndicator;
    }


    /**
     * Gets the returnTrackingId value for this CodDetail.
     * 
     * @return returnTrackingId
     */
    public com.owd.fedEx.ShipService.TrackingId getReturnTrackingId() {
        return returnTrackingId;
    }


    /**
     * Sets the returnTrackingId value for this CodDetail.
     * 
     * @param returnTrackingId
     */
    public void setReturnTrackingId(com.owd.fedEx.ShipService.TrackingId returnTrackingId) {
        this.returnTrackingId = returnTrackingId;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CodDetail)) return false;
        CodDetail other = (CodDetail) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.codCollectionAmount==null && other.getCodCollectionAmount()==null) || 
             (this.codCollectionAmount!=null &&
              this.codCollectionAmount.equals(other.getCodCollectionAmount()))) &&
            ((this.addTransportationChargesDetail==null && other.getAddTransportationChargesDetail()==null) || 
             (this.addTransportationChargesDetail!=null &&
              this.addTransportationChargesDetail.equals(other.getAddTransportationChargesDetail()))) &&
            ((this.collectionType==null && other.getCollectionType()==null) || 
             (this.collectionType!=null &&
              this.collectionType.equals(other.getCollectionType()))) &&
            ((this.codRecipient==null && other.getCodRecipient()==null) || 
             (this.codRecipient!=null &&
              this.codRecipient.equals(other.getCodRecipient()))) &&
            ((this.financialInstitutionContactAndAddress==null && other.getFinancialInstitutionContactAndAddress()==null) || 
             (this.financialInstitutionContactAndAddress!=null &&
              this.financialInstitutionContactAndAddress.equals(other.getFinancialInstitutionContactAndAddress()))) &&
            ((this.remitToName==null && other.getRemitToName()==null) || 
             (this.remitToName!=null &&
              this.remitToName.equals(other.getRemitToName()))) &&
            ((this.referenceIndicator==null && other.getReferenceIndicator()==null) || 
             (this.referenceIndicator!=null &&
              this.referenceIndicator.equals(other.getReferenceIndicator()))) &&
            ((this.returnTrackingId==null && other.getReturnTrackingId()==null) || 
             (this.returnTrackingId!=null &&
              this.returnTrackingId.equals(other.getReturnTrackingId())));
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
        if (getCodCollectionAmount() != null) {
            _hashCode += getCodCollectionAmount().hashCode();
        }
        if (getAddTransportationChargesDetail() != null) {
            _hashCode += getAddTransportationChargesDetail().hashCode();
        }
        if (getCollectionType() != null) {
            _hashCode += getCollectionType().hashCode();
        }
        if (getCodRecipient() != null) {
            _hashCode += getCodRecipient().hashCode();
        }
        if (getFinancialInstitutionContactAndAddress() != null) {
            _hashCode += getFinancialInstitutionContactAndAddress().hashCode();
        }
        if (getRemitToName() != null) {
            _hashCode += getRemitToName().hashCode();
        }
        if (getReferenceIndicator() != null) {
            _hashCode += getReferenceIndicator().hashCode();
        }
        if (getReturnTrackingId() != null) {
            _hashCode += getReturnTrackingId().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CodDetail.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "CodDetail"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codCollectionAmount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "CodCollectionAmount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Money"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("addTransportationChargesDetail");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "AddTransportationChargesDetail"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "CodAddTransportationChargesDetail"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("collectionType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "CollectionType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "CodCollectionType"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codRecipient");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "CodRecipient"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Party"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("financialInstitutionContactAndAddress");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "FinancialInstitutionContactAndAddress"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ContactAndAddress"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("remitToName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "RemitToName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("referenceIndicator");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ReferenceIndicator"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "CodReturnReferenceIndicatorType"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("returnTrackingId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ReturnTrackingId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "TrackingId"));
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
