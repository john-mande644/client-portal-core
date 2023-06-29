/*
 * CompletedShipmentDetail.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;

public class CompletedShipmentDetail  implements java.io.Serializable {
    private java.lang.Boolean usDomestic;
    /** Identification of a FedEx operating company (transportation). */
    private com.owd.fedEx.ShipService.CarrierCodeType carrierCode;
    private com.owd.fedEx.ShipService.TrackingId masterTrackingId;
    private java.lang.String serviceTypeDescription;
    private com.owd.fedEx.ShipService.ServiceDescription serviceDescription;
    private java.lang.String packagingDescription;
    private com.owd.fedEx.ShipService.ShipmentOperationalDetail operationalDetail;
    /** This information describes how and when a pending shipment may
 * be accessed for completion. */
    private com.owd.fedEx.ShipService.PendingShipmentAccessDetail accessDetail;
    /** Provides reply information specific to a tag request. */
    private com.owd.fedEx.ShipService.CompletedTagDetail tagDetail;
    /** Provides reply information specific to SmartPost shipments. */
    private com.owd.fedEx.ShipService.CompletedSmartPostDetail smartPostDetail;
    /** Computed shipment level hazardous commodity information. */
    private com.owd.fedEx.ShipService.CompletedHazardousShipmentDetail hazardousShipmentDetail;
    /** This class groups together all shipment-level rate data (across
 * all rate types) as part of the response to a shipping request, which
 * groups shipment-level data together and groups package-level data
 * by package. */
    private com.owd.fedEx.ShipService.ShipmentRating shipmentRating;
    private com.owd.fedEx.ShipService.CompletedHoldAtLocationDetail completedHoldAtLocationDetail;
    private java.lang.String exportComplianceStatement;
    private com.owd.fedEx.ShipService.DocumentRequirementsDetail documentRequirements;
    private com.owd.fedEx.ShipService.CompletedEtdDetail completedEtdDetail;
    /** All package-level shipping documents (other than labels and barcodes).
 * For use in loads after January, 2008. */
    private com.owd.fedEx.ShipService.ShippingDocument[] shipmentDocuments;
    private com.owd.fedEx.ShipService.AssociatedShipmentDetail[] associatedShipments;
    /** Specifies the results of processing for the COD special service. */
    private com.owd.fedEx.ShipService.CompletedCodDetail completedCodDetail;
    private com.owd.fedEx.ShipService.CompletedPackageDetail[] completedPackageDetails;

    public CompletedShipmentDetail() {
    }


    /**
     * Gets the usDomestic value for this CompletedShipmentDetail.
     * 
     * @return usDomestic
     */
    public java.lang.Boolean getUsDomestic() {
        return usDomestic;
    }


    /**
     * Sets the usDomestic value for this CompletedShipmentDetail.
     * 
     * @param usDomestic
     */
    public void setUsDomestic(java.lang.Boolean usDomestic) {
        this.usDomestic = usDomestic;
    }


    /**
     * Gets the carrierCode value for this CompletedShipmentDetail.
     * 
     * @return carrierCode Identification of a FedEx operating company (transportation).
     */
    public com.owd.fedEx.ShipService.CarrierCodeType getCarrierCode() {
        return carrierCode;
    }


    /**
     * Sets the carrierCode value for this CompletedShipmentDetail.
     * 
     * @param carrierCode Identification of a FedEx operating company (transportation).
     */
    public void setCarrierCode(com.owd.fedEx.ShipService.CarrierCodeType carrierCode) {
        this.carrierCode = carrierCode;
    }


    /**
     * Gets the masterTrackingId value for this CompletedShipmentDetail.
     * 
     * @return masterTrackingId
     */
    public com.owd.fedEx.ShipService.TrackingId getMasterTrackingId() {
        return masterTrackingId;
    }


    /**
     * Sets the masterTrackingId value for this CompletedShipmentDetail.
     * 
     * @param masterTrackingId
     */
    public void setMasterTrackingId(com.owd.fedEx.ShipService.TrackingId masterTrackingId) {
        this.masterTrackingId = masterTrackingId;
    }


    /**
     * Gets the serviceTypeDescription value for this CompletedShipmentDetail.
     * 
     * @return serviceTypeDescription
     */
    public java.lang.String getServiceTypeDescription() {
        return serviceTypeDescription;
    }


    /**
     * Sets the serviceTypeDescription value for this CompletedShipmentDetail.
     * 
     * @param serviceTypeDescription
     */
    public void setServiceTypeDescription(java.lang.String serviceTypeDescription) {
        this.serviceTypeDescription = serviceTypeDescription;
    }


    /**
     * Gets the serviceDescription value for this CompletedShipmentDetail.
     * 
     * @return serviceDescription
     */
    public com.owd.fedEx.ShipService.ServiceDescription getServiceDescription() {
        return serviceDescription;
    }


    /**
     * Sets the serviceDescription value for this CompletedShipmentDetail.
     * 
     * @param serviceDescription
     */
    public void setServiceDescription(com.owd.fedEx.ShipService.ServiceDescription serviceDescription) {
        this.serviceDescription = serviceDescription;
    }


    /**
     * Gets the packagingDescription value for this CompletedShipmentDetail.
     * 
     * @return packagingDescription
     */
    public java.lang.String getPackagingDescription() {
        return packagingDescription;
    }


    /**
     * Sets the packagingDescription value for this CompletedShipmentDetail.
     * 
     * @param packagingDescription
     */
    public void setPackagingDescription(java.lang.String packagingDescription) {
        this.packagingDescription = packagingDescription;
    }


    /**
     * Gets the operationalDetail value for this CompletedShipmentDetail.
     * 
     * @return operationalDetail
     */
    public com.owd.fedEx.ShipService.ShipmentOperationalDetail getOperationalDetail() {
        return operationalDetail;
    }


    /**
     * Sets the operationalDetail value for this CompletedShipmentDetail.
     * 
     * @param operationalDetail
     */
    public void setOperationalDetail(com.owd.fedEx.ShipService.ShipmentOperationalDetail operationalDetail) {
        this.operationalDetail = operationalDetail;
    }


    /**
     * Gets the accessDetail value for this CompletedShipmentDetail.
     * 
     * @return accessDetail This information describes how and when a pending shipment may
 * be accessed for completion.
     */
    public com.owd.fedEx.ShipService.PendingShipmentAccessDetail getAccessDetail() {
        return accessDetail;
    }


    /**
     * Sets the accessDetail value for this CompletedShipmentDetail.
     * 
     * @param accessDetail This information describes how and when a pending shipment may
 * be accessed for completion.
     */
    public void setAccessDetail(com.owd.fedEx.ShipService.PendingShipmentAccessDetail accessDetail) {
        this.accessDetail = accessDetail;
    }


    /**
     * Gets the tagDetail value for this CompletedShipmentDetail.
     * 
     * @return tagDetail Provides reply information specific to a tag request.
     */
    public com.owd.fedEx.ShipService.CompletedTagDetail getTagDetail() {
        return tagDetail;
    }


    /**
     * Sets the tagDetail value for this CompletedShipmentDetail.
     * 
     * @param tagDetail Provides reply information specific to a tag request.
     */
    public void setTagDetail(com.owd.fedEx.ShipService.CompletedTagDetail tagDetail) {
        this.tagDetail = tagDetail;
    }


    /**
     * Gets the smartPostDetail value for this CompletedShipmentDetail.
     * 
     * @return smartPostDetail Provides reply information specific to SmartPost shipments.
     */
    public com.owd.fedEx.ShipService.CompletedSmartPostDetail getSmartPostDetail() {
        return smartPostDetail;
    }


    /**
     * Sets the smartPostDetail value for this CompletedShipmentDetail.
     * 
     * @param smartPostDetail Provides reply information specific to SmartPost shipments.
     */
    public void setSmartPostDetail(com.owd.fedEx.ShipService.CompletedSmartPostDetail smartPostDetail) {
        this.smartPostDetail = smartPostDetail;
    }


    /**
     * Gets the hazardousShipmentDetail value for this CompletedShipmentDetail.
     * 
     * @return hazardousShipmentDetail Computed shipment level hazardous commodity information.
     */
    public com.owd.fedEx.ShipService.CompletedHazardousShipmentDetail getHazardousShipmentDetail() {
        return hazardousShipmentDetail;
    }


    /**
     * Sets the hazardousShipmentDetail value for this CompletedShipmentDetail.
     * 
     * @param hazardousShipmentDetail Computed shipment level hazardous commodity information.
     */
    public void setHazardousShipmentDetail(com.owd.fedEx.ShipService.CompletedHazardousShipmentDetail hazardousShipmentDetail) {
        this.hazardousShipmentDetail = hazardousShipmentDetail;
    }


    /**
     * Gets the shipmentRating value for this CompletedShipmentDetail.
     * 
     * @return shipmentRating This class groups together all shipment-level rate data (across
 * all rate types) as part of the response to a shipping request, which
 * groups shipment-level data together and groups package-level data
 * by package.
     */
    public com.owd.fedEx.ShipService.ShipmentRating getShipmentRating() {
        return shipmentRating;
    }


    /**
     * Sets the shipmentRating value for this CompletedShipmentDetail.
     * 
     * @param shipmentRating This class groups together all shipment-level rate data (across
 * all rate types) as part of the response to a shipping request, which
 * groups shipment-level data together and groups package-level data
 * by package.
     */
    public void setShipmentRating(com.owd.fedEx.ShipService.ShipmentRating shipmentRating) {
        this.shipmentRating = shipmentRating;
    }


    /**
     * Gets the completedHoldAtLocationDetail value for this CompletedShipmentDetail.
     * 
     * @return completedHoldAtLocationDetail
     */
    public com.owd.fedEx.ShipService.CompletedHoldAtLocationDetail getCompletedHoldAtLocationDetail() {
        return completedHoldAtLocationDetail;
    }


    /**
     * Sets the completedHoldAtLocationDetail value for this CompletedShipmentDetail.
     * 
     * @param completedHoldAtLocationDetail
     */
    public void setCompletedHoldAtLocationDetail(com.owd.fedEx.ShipService.CompletedHoldAtLocationDetail completedHoldAtLocationDetail) {
        this.completedHoldAtLocationDetail = completedHoldAtLocationDetail;
    }


    /**
     * Gets the exportComplianceStatement value for this CompletedShipmentDetail.
     * 
     * @return exportComplianceStatement
     */
    public java.lang.String getExportComplianceStatement() {
        return exportComplianceStatement;
    }


    /**
     * Sets the exportComplianceStatement value for this CompletedShipmentDetail.
     * 
     * @param exportComplianceStatement
     */
    public void setExportComplianceStatement(java.lang.String exportComplianceStatement) {
        this.exportComplianceStatement = exportComplianceStatement;
    }


    /**
     * Gets the documentRequirements value for this CompletedShipmentDetail.
     * 
     * @return documentRequirements
     */
    public com.owd.fedEx.ShipService.DocumentRequirementsDetail getDocumentRequirements() {
        return documentRequirements;
    }


    /**
     * Sets the documentRequirements value for this CompletedShipmentDetail.
     * 
     * @param documentRequirements
     */
    public void setDocumentRequirements(com.owd.fedEx.ShipService.DocumentRequirementsDetail documentRequirements) {
        this.documentRequirements = documentRequirements;
    }


    /**
     * Gets the completedEtdDetail value for this CompletedShipmentDetail.
     * 
     * @return completedEtdDetail
     */
    public com.owd.fedEx.ShipService.CompletedEtdDetail getCompletedEtdDetail() {
        return completedEtdDetail;
    }


    /**
     * Sets the completedEtdDetail value for this CompletedShipmentDetail.
     * 
     * @param completedEtdDetail
     */
    public void setCompletedEtdDetail(com.owd.fedEx.ShipService.CompletedEtdDetail completedEtdDetail) {
        this.completedEtdDetail = completedEtdDetail;
    }


    /**
     * Gets the shipmentDocuments value for this CompletedShipmentDetail.
     * 
     * @return shipmentDocuments All package-level shipping documents (other than labels and barcodes).
 * For use in loads after January, 2008.
     */
    public com.owd.fedEx.ShipService.ShippingDocument[] getShipmentDocuments() {
        return shipmentDocuments;
    }


    /**
     * Sets the shipmentDocuments value for this CompletedShipmentDetail.
     * 
     * @param shipmentDocuments All package-level shipping documents (other than labels and barcodes).
 * For use in loads after January, 2008.
     */
    public void setShipmentDocuments(com.owd.fedEx.ShipService.ShippingDocument[] shipmentDocuments) {
        this.shipmentDocuments = shipmentDocuments;
    }

    public com.owd.fedEx.ShipService.ShippingDocument getShipmentDocuments(int i) {
        return shipmentDocuments[i];
    }

    public void setShipmentDocuments(int i, com.owd.fedEx.ShipService.ShippingDocument value) {
        this.shipmentDocuments[i] = value;
    }


    /**
     * Gets the associatedShipments value for this CompletedShipmentDetail.
     * 
     * @return associatedShipments
     */
    public com.owd.fedEx.ShipService.AssociatedShipmentDetail[] getAssociatedShipments() {
        return associatedShipments;
    }


    /**
     * Sets the associatedShipments value for this CompletedShipmentDetail.
     * 
     * @param associatedShipments
     */
    public void setAssociatedShipments(com.owd.fedEx.ShipService.AssociatedShipmentDetail[] associatedShipments) {
        this.associatedShipments = associatedShipments;
    }

    public com.owd.fedEx.ShipService.AssociatedShipmentDetail getAssociatedShipments(int i) {
        return associatedShipments[i];
    }

    public void setAssociatedShipments(int i, com.owd.fedEx.ShipService.AssociatedShipmentDetail value) {
        this.associatedShipments[i] = value;
    }


    /**
     * Gets the completedCodDetail value for this CompletedShipmentDetail.
     * 
     * @return completedCodDetail Specifies the results of processing for the COD special service.
     */
    public com.owd.fedEx.ShipService.CompletedCodDetail getCompletedCodDetail() {
        return completedCodDetail;
    }


    /**
     * Sets the completedCodDetail value for this CompletedShipmentDetail.
     * 
     * @param completedCodDetail Specifies the results of processing for the COD special service.
     */
    public void setCompletedCodDetail(com.owd.fedEx.ShipService.CompletedCodDetail completedCodDetail) {
        this.completedCodDetail = completedCodDetail;
    }


    /**
     * Gets the completedPackageDetails value for this CompletedShipmentDetail.
     * 
     * @return completedPackageDetails
     */
    public com.owd.fedEx.ShipService.CompletedPackageDetail[] getCompletedPackageDetails() {
        return completedPackageDetails;
    }


    /**
     * Sets the completedPackageDetails value for this CompletedShipmentDetail.
     * 
     * @param completedPackageDetails
     */
    public void setCompletedPackageDetails(com.owd.fedEx.ShipService.CompletedPackageDetail[] completedPackageDetails) {
        this.completedPackageDetails = completedPackageDetails;
    }

    public com.owd.fedEx.ShipService.CompletedPackageDetail getCompletedPackageDetails(int i) {
        return completedPackageDetails[i];
    }

    public void setCompletedPackageDetails(int i, com.owd.fedEx.ShipService.CompletedPackageDetail value) {
        this.completedPackageDetails[i] = value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CompletedShipmentDetail)) return false;
        CompletedShipmentDetail other = (CompletedShipmentDetail) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.usDomestic==null && other.getUsDomestic()==null) || 
             (this.usDomestic!=null &&
              this.usDomestic.equals(other.getUsDomestic()))) &&
            ((this.carrierCode==null && other.getCarrierCode()==null) || 
             (this.carrierCode!=null &&
              this.carrierCode.equals(other.getCarrierCode()))) &&
            ((this.masterTrackingId==null && other.getMasterTrackingId()==null) || 
             (this.masterTrackingId!=null &&
              this.masterTrackingId.equals(other.getMasterTrackingId()))) &&
            ((this.serviceTypeDescription==null && other.getServiceTypeDescription()==null) || 
             (this.serviceTypeDescription!=null &&
              this.serviceTypeDescription.equals(other.getServiceTypeDescription()))) &&
            ((this.serviceDescription==null && other.getServiceDescription()==null) || 
             (this.serviceDescription!=null &&
              this.serviceDescription.equals(other.getServiceDescription()))) &&
            ((this.packagingDescription==null && other.getPackagingDescription()==null) || 
             (this.packagingDescription!=null &&
              this.packagingDescription.equals(other.getPackagingDescription()))) &&
            ((this.operationalDetail==null && other.getOperationalDetail()==null) || 
             (this.operationalDetail!=null &&
              this.operationalDetail.equals(other.getOperationalDetail()))) &&
            ((this.accessDetail==null && other.getAccessDetail()==null) || 
             (this.accessDetail!=null &&
              this.accessDetail.equals(other.getAccessDetail()))) &&
            ((this.tagDetail==null && other.getTagDetail()==null) || 
             (this.tagDetail!=null &&
              this.tagDetail.equals(other.getTagDetail()))) &&
            ((this.smartPostDetail==null && other.getSmartPostDetail()==null) || 
             (this.smartPostDetail!=null &&
              this.smartPostDetail.equals(other.getSmartPostDetail()))) &&
            ((this.hazardousShipmentDetail==null && other.getHazardousShipmentDetail()==null) || 
             (this.hazardousShipmentDetail!=null &&
              this.hazardousShipmentDetail.equals(other.getHazardousShipmentDetail()))) &&
            ((this.shipmentRating==null && other.getShipmentRating()==null) || 
             (this.shipmentRating!=null &&
              this.shipmentRating.equals(other.getShipmentRating()))) &&
            ((this.completedHoldAtLocationDetail==null && other.getCompletedHoldAtLocationDetail()==null) || 
             (this.completedHoldAtLocationDetail!=null &&
              this.completedHoldAtLocationDetail.equals(other.getCompletedHoldAtLocationDetail()))) &&
            ((this.exportComplianceStatement==null && other.getExportComplianceStatement()==null) || 
             (this.exportComplianceStatement!=null &&
              this.exportComplianceStatement.equals(other.getExportComplianceStatement()))) &&
            ((this.documentRequirements==null && other.getDocumentRequirements()==null) || 
             (this.documentRequirements!=null &&
              this.documentRequirements.equals(other.getDocumentRequirements()))) &&
            ((this.completedEtdDetail==null && other.getCompletedEtdDetail()==null) || 
             (this.completedEtdDetail!=null &&
              this.completedEtdDetail.equals(other.getCompletedEtdDetail()))) &&
            ((this.shipmentDocuments==null && other.getShipmentDocuments()==null) || 
             (this.shipmentDocuments!=null &&
              java.util.Arrays.equals(this.shipmentDocuments, other.getShipmentDocuments()))) &&
            ((this.associatedShipments==null && other.getAssociatedShipments()==null) || 
             (this.associatedShipments!=null &&
              java.util.Arrays.equals(this.associatedShipments, other.getAssociatedShipments()))) &&
            ((this.completedCodDetail==null && other.getCompletedCodDetail()==null) || 
             (this.completedCodDetail!=null &&
              this.completedCodDetail.equals(other.getCompletedCodDetail()))) &&
            ((this.completedPackageDetails==null && other.getCompletedPackageDetails()==null) || 
             (this.completedPackageDetails!=null &&
              java.util.Arrays.equals(this.completedPackageDetails, other.getCompletedPackageDetails())));
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
        if (getUsDomestic() != null) {
            _hashCode += getUsDomestic().hashCode();
        }
        if (getCarrierCode() != null) {
            _hashCode += getCarrierCode().hashCode();
        }
        if (getMasterTrackingId() != null) {
            _hashCode += getMasterTrackingId().hashCode();
        }
        if (getServiceTypeDescription() != null) {
            _hashCode += getServiceTypeDescription().hashCode();
        }
        if (getServiceDescription() != null) {
            _hashCode += getServiceDescription().hashCode();
        }
        if (getPackagingDescription() != null) {
            _hashCode += getPackagingDescription().hashCode();
        }
        if (getOperationalDetail() != null) {
            _hashCode += getOperationalDetail().hashCode();
        }
        if (getAccessDetail() != null) {
            _hashCode += getAccessDetail().hashCode();
        }
        if (getTagDetail() != null) {
            _hashCode += getTagDetail().hashCode();
        }
        if (getSmartPostDetail() != null) {
            _hashCode += getSmartPostDetail().hashCode();
        }
        if (getHazardousShipmentDetail() != null) {
            _hashCode += getHazardousShipmentDetail().hashCode();
        }
        if (getShipmentRating() != null) {
            _hashCode += getShipmentRating().hashCode();
        }
        if (getCompletedHoldAtLocationDetail() != null) {
            _hashCode += getCompletedHoldAtLocationDetail().hashCode();
        }
        if (getExportComplianceStatement() != null) {
            _hashCode += getExportComplianceStatement().hashCode();
        }
        if (getDocumentRequirements() != null) {
            _hashCode += getDocumentRequirements().hashCode();
        }
        if (getCompletedEtdDetail() != null) {
            _hashCode += getCompletedEtdDetail().hashCode();
        }
        if (getShipmentDocuments() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getShipmentDocuments());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getShipmentDocuments(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getAssociatedShipments() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getAssociatedShipments());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getAssociatedShipments(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getCompletedCodDetail() != null) {
            _hashCode += getCompletedCodDetail().hashCode();
        }
        if (getCompletedPackageDetails() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getCompletedPackageDetails());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getCompletedPackageDetails(), i);
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
        new org.apache.axis.description.TypeDesc(CompletedShipmentDetail.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "CompletedShipmentDetail"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("usDomestic");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "UsDomestic"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "boolean"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("carrierCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "CarrierCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "CarrierCodeType"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("masterTrackingId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "MasterTrackingId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "TrackingId"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("serviceTypeDescription");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ServiceTypeDescription"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("serviceDescription");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ServiceDescription"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ServiceDescription"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("packagingDescription");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "PackagingDescription"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("operationalDetail");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "OperationalDetail"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ShipmentOperationalDetail"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("accessDetail");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "AccessDetail"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "PendingShipmentAccessDetail"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tagDetail");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "TagDetail"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "CompletedTagDetail"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("smartPostDetail");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "SmartPostDetail"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "CompletedSmartPostDetail"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("hazardousShipmentDetail");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "HazardousShipmentDetail"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "CompletedHazardousShipmentDetail"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("shipmentRating");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ShipmentRating"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ShipmentRating"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("completedHoldAtLocationDetail");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "CompletedHoldAtLocationDetail"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "CompletedHoldAtLocationDetail"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("exportComplianceStatement");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ExportComplianceStatement"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("documentRequirements");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "DocumentRequirements"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "DocumentRequirementsDetail"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("completedEtdDetail");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "CompletedEtdDetail"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "CompletedEtdDetail"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("shipmentDocuments");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ShipmentDocuments"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ShippingDocument"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("associatedShipments");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "AssociatedShipments"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "AssociatedShipmentDetail"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("completedCodDetail");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "CompletedCodDetail"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "CompletedCodDetail"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("completedPackageDetails");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "CompletedPackageDetails"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "CompletedPackageDetail"));
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
