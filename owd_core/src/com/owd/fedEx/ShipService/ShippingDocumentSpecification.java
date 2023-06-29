/*
 * ShippingDocumentSpecification.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;


/**
 * Contains all data required for additional (non-label) shipping
 * documents to be produced in conjunction with a specific shipment.
 */
public class ShippingDocumentSpecification  implements java.io.Serializable {
    /** Indicates the types of shipping documents produced for the shipper
 * by FedEx (see ShippingDocumentSpecification) which should be copied
 * back to the shipper in the shipment result data. */
    private com.owd.fedEx.ShipService.RequestedShippingDocumentType[] shippingDocumentTypes;
    /** The instructions indicating how to print the Certificate of Origin
 * ( e.g. whether or not to include the instructions, image type, etc
 * ...) */
    private com.owd.fedEx.ShipService.CertificateOfOriginDetail certificateOfOrigin;
    /** The instructions indicating how to print the Commercial Invoice(
 * e.g. image type) Specifies characteristics of a shipping document
 * to be produced. */
    private com.owd.fedEx.ShipService.CommercialInvoiceDetail commercialInvoiceDetail;
    /** Specifies the production of each package-level custom document
 * (the same specification is used for all packages). */
    private com.owd.fedEx.ShipService.CustomDocumentDetail[] customPackageDocumentDetail;
    /** Specifies the production of each package-level custom document
 * (the same specification is used for all packages). */
    private com.owd.fedEx.ShipService.CustomDocumentDetail[] customShipmentDocumentDetail;
    /** The instructions indicating how to print the Export Declaration. */
    private com.owd.fedEx.ShipService.ExportDeclarationDetail exportDeclarationDetail;
    /** Data required to produce a General Agency Agreement document. Remaining
 * content (business data) to be defined once requirements have been
 * completed. */
    private com.owd.fedEx.ShipService.GeneralAgencyAgreementDetail generalAgencyAgreementDetail;
    /** Data required to produce a Certificate of Origin document. Remaining
 * content (business data) to be defined once requirements have been
 * completed. */
    private com.owd.fedEx.ShipService.NaftaCertificateOfOriginDetail naftaCertificateOfOriginDetail;
    /** The instructions indicating how to print the OP-900 form for hazardous
 * materials packages. */
    private com.owd.fedEx.ShipService.Op900Detail op900Detail;
    /** The instructions indicating how to print the 1421c form for dangerous
 * goods shipment. */
    private com.owd.fedEx.ShipService.DangerousGoodsShippersDeclarationDetail dangerousGoodsShippersDeclarationDetail;
    /** Data required to produce the Freight handling-unit-level address
 * labels. Note that the number of UNIQUE labels (the N as in 1 of N,
 * 2 of N, etc.) is determined by total handling units. */
    private com.owd.fedEx.ShipService.FreightAddressLabelDetail freightAddressLabelDetail;
    private com.owd.fedEx.ShipService.FreightBillOfLadingDetail freightBillOfLadingDetail;
    /** The instructions indicating how to print the return instructions(
 * e.g. image type) Specifies characteristics of a shipping document
 * to be produced. */
    private com.owd.fedEx.ShipService.ReturnInstructionsDetail returnInstructionsDetail;

    public ShippingDocumentSpecification() {
    }


    /**
     * Gets the shippingDocumentTypes value for this ShippingDocumentSpecification.
     * 
     * @return shippingDocumentTypes Indicates the types of shipping documents produced for the shipper
 * by FedEx (see ShippingDocumentSpecification) which should be copied
 * back to the shipper in the shipment result data.
     */
    public com.owd.fedEx.ShipService.RequestedShippingDocumentType[] getShippingDocumentTypes() {
        return shippingDocumentTypes;
    }


    /**
     * Sets the shippingDocumentTypes value for this ShippingDocumentSpecification.
     * 
     * @param shippingDocumentTypes Indicates the types of shipping documents produced for the shipper
 * by FedEx (see ShippingDocumentSpecification) which should be copied
 * back to the shipper in the shipment result data.
     */
    public void setShippingDocumentTypes(com.owd.fedEx.ShipService.RequestedShippingDocumentType[] shippingDocumentTypes) {
        this.shippingDocumentTypes = shippingDocumentTypes;
    }

    public com.owd.fedEx.ShipService.RequestedShippingDocumentType getShippingDocumentTypes(int i) {
        return shippingDocumentTypes[i];
    }

    public void setShippingDocumentTypes(int i, com.owd.fedEx.ShipService.RequestedShippingDocumentType value) {
        this.shippingDocumentTypes[i] = value;
    }


    /**
     * Gets the certificateOfOrigin value for this ShippingDocumentSpecification.
     * 
     * @return certificateOfOrigin The instructions indicating how to print the Certificate of Origin
 * ( e.g. whether or not to include the instructions, image type, etc
 * ...)
     */
    public com.owd.fedEx.ShipService.CertificateOfOriginDetail getCertificateOfOrigin() {
        return certificateOfOrigin;
    }


    /**
     * Sets the certificateOfOrigin value for this ShippingDocumentSpecification.
     * 
     * @param certificateOfOrigin The instructions indicating how to print the Certificate of Origin
 * ( e.g. whether or not to include the instructions, image type, etc
 * ...)
     */
    public void setCertificateOfOrigin(com.owd.fedEx.ShipService.CertificateOfOriginDetail certificateOfOrigin) {
        this.certificateOfOrigin = certificateOfOrigin;
    }


    /**
     * Gets the commercialInvoiceDetail value for this ShippingDocumentSpecification.
     * 
     * @return commercialInvoiceDetail The instructions indicating how to print the Commercial Invoice(
 * e.g. image type) Specifies characteristics of a shipping document
 * to be produced.
     */
    public com.owd.fedEx.ShipService.CommercialInvoiceDetail getCommercialInvoiceDetail() {
        return commercialInvoiceDetail;
    }


    /**
     * Sets the commercialInvoiceDetail value for this ShippingDocumentSpecification.
     * 
     * @param commercialInvoiceDetail The instructions indicating how to print the Commercial Invoice(
 * e.g. image type) Specifies characteristics of a shipping document
 * to be produced.
     */
    public void setCommercialInvoiceDetail(com.owd.fedEx.ShipService.CommercialInvoiceDetail commercialInvoiceDetail) {
        this.commercialInvoiceDetail = commercialInvoiceDetail;
    }


    /**
     * Gets the customPackageDocumentDetail value for this ShippingDocumentSpecification.
     * 
     * @return customPackageDocumentDetail Specifies the production of each package-level custom document
 * (the same specification is used for all packages).
     */
    public com.owd.fedEx.ShipService.CustomDocumentDetail[] getCustomPackageDocumentDetail() {
        return customPackageDocumentDetail;
    }


    /**
     * Sets the customPackageDocumentDetail value for this ShippingDocumentSpecification.
     * 
     * @param customPackageDocumentDetail Specifies the production of each package-level custom document
 * (the same specification is used for all packages).
     */
    public void setCustomPackageDocumentDetail(com.owd.fedEx.ShipService.CustomDocumentDetail[] customPackageDocumentDetail) {
        this.customPackageDocumentDetail = customPackageDocumentDetail;
    }

    public com.owd.fedEx.ShipService.CustomDocumentDetail getCustomPackageDocumentDetail(int i) {
        return customPackageDocumentDetail[i];
    }

    public void setCustomPackageDocumentDetail(int i, com.owd.fedEx.ShipService.CustomDocumentDetail value) {
        this.customPackageDocumentDetail[i] = value;
    }


    /**
     * Gets the customShipmentDocumentDetail value for this ShippingDocumentSpecification.
     * 
     * @return customShipmentDocumentDetail Specifies the production of each package-level custom document
 * (the same specification is used for all packages).
     */
    public com.owd.fedEx.ShipService.CustomDocumentDetail[] getCustomShipmentDocumentDetail() {
        return customShipmentDocumentDetail;
    }


    /**
     * Sets the customShipmentDocumentDetail value for this ShippingDocumentSpecification.
     * 
     * @param customShipmentDocumentDetail Specifies the production of each package-level custom document
 * (the same specification is used for all packages).
     */
    public void setCustomShipmentDocumentDetail(com.owd.fedEx.ShipService.CustomDocumentDetail[] customShipmentDocumentDetail) {
        this.customShipmentDocumentDetail = customShipmentDocumentDetail;
    }

    public com.owd.fedEx.ShipService.CustomDocumentDetail getCustomShipmentDocumentDetail(int i) {
        return customShipmentDocumentDetail[i];
    }

    public void setCustomShipmentDocumentDetail(int i, com.owd.fedEx.ShipService.CustomDocumentDetail value) {
        this.customShipmentDocumentDetail[i] = value;
    }


    /**
     * Gets the exportDeclarationDetail value for this ShippingDocumentSpecification.
     * 
     * @return exportDeclarationDetail The instructions indicating how to print the Export Declaration.
     */
    public com.owd.fedEx.ShipService.ExportDeclarationDetail getExportDeclarationDetail() {
        return exportDeclarationDetail;
    }


    /**
     * Sets the exportDeclarationDetail value for this ShippingDocumentSpecification.
     * 
     * @param exportDeclarationDetail The instructions indicating how to print the Export Declaration.
     */
    public void setExportDeclarationDetail(com.owd.fedEx.ShipService.ExportDeclarationDetail exportDeclarationDetail) {
        this.exportDeclarationDetail = exportDeclarationDetail;
    }


    /**
     * Gets the generalAgencyAgreementDetail value for this ShippingDocumentSpecification.
     * 
     * @return generalAgencyAgreementDetail Data required to produce a General Agency Agreement document. Remaining
 * content (business data) to be defined once requirements have been
 * completed.
     */
    public com.owd.fedEx.ShipService.GeneralAgencyAgreementDetail getGeneralAgencyAgreementDetail() {
        return generalAgencyAgreementDetail;
    }


    /**
     * Sets the generalAgencyAgreementDetail value for this ShippingDocumentSpecification.
     * 
     * @param generalAgencyAgreementDetail Data required to produce a General Agency Agreement document. Remaining
 * content (business data) to be defined once requirements have been
 * completed.
     */
    public void setGeneralAgencyAgreementDetail(com.owd.fedEx.ShipService.GeneralAgencyAgreementDetail generalAgencyAgreementDetail) {
        this.generalAgencyAgreementDetail = generalAgencyAgreementDetail;
    }


    /**
     * Gets the naftaCertificateOfOriginDetail value for this ShippingDocumentSpecification.
     * 
     * @return naftaCertificateOfOriginDetail Data required to produce a Certificate of Origin document. Remaining
 * content (business data) to be defined once requirements have been
 * completed.
     */
    public com.owd.fedEx.ShipService.NaftaCertificateOfOriginDetail getNaftaCertificateOfOriginDetail() {
        return naftaCertificateOfOriginDetail;
    }


    /**
     * Sets the naftaCertificateOfOriginDetail value for this ShippingDocumentSpecification.
     * 
     * @param naftaCertificateOfOriginDetail Data required to produce a Certificate of Origin document. Remaining
 * content (business data) to be defined once requirements have been
 * completed.
     */
    public void setNaftaCertificateOfOriginDetail(com.owd.fedEx.ShipService.NaftaCertificateOfOriginDetail naftaCertificateOfOriginDetail) {
        this.naftaCertificateOfOriginDetail = naftaCertificateOfOriginDetail;
    }


    /**
     * Gets the op900Detail value for this ShippingDocumentSpecification.
     * 
     * @return op900Detail The instructions indicating how to print the OP-900 form for hazardous
 * materials packages.
     */
    public com.owd.fedEx.ShipService.Op900Detail getOp900Detail() {
        return op900Detail;
    }


    /**
     * Sets the op900Detail value for this ShippingDocumentSpecification.
     * 
     * @param op900Detail The instructions indicating how to print the OP-900 form for hazardous
 * materials packages.
     */
    public void setOp900Detail(com.owd.fedEx.ShipService.Op900Detail op900Detail) {
        this.op900Detail = op900Detail;
    }


    /**
     * Gets the dangerousGoodsShippersDeclarationDetail value for this ShippingDocumentSpecification.
     * 
     * @return dangerousGoodsShippersDeclarationDetail The instructions indicating how to print the 1421c form for dangerous
 * goods shipment.
     */
    public com.owd.fedEx.ShipService.DangerousGoodsShippersDeclarationDetail getDangerousGoodsShippersDeclarationDetail() {
        return dangerousGoodsShippersDeclarationDetail;
    }


    /**
     * Sets the dangerousGoodsShippersDeclarationDetail value for this ShippingDocumentSpecification.
     * 
     * @param dangerousGoodsShippersDeclarationDetail The instructions indicating how to print the 1421c form for dangerous
 * goods shipment.
     */
    public void setDangerousGoodsShippersDeclarationDetail(com.owd.fedEx.ShipService.DangerousGoodsShippersDeclarationDetail dangerousGoodsShippersDeclarationDetail) {
        this.dangerousGoodsShippersDeclarationDetail = dangerousGoodsShippersDeclarationDetail;
    }


    /**
     * Gets the freightAddressLabelDetail value for this ShippingDocumentSpecification.
     * 
     * @return freightAddressLabelDetail Data required to produce the Freight handling-unit-level address
 * labels. Note that the number of UNIQUE labels (the N as in 1 of N,
 * 2 of N, etc.) is determined by total handling units.
     */
    public com.owd.fedEx.ShipService.FreightAddressLabelDetail getFreightAddressLabelDetail() {
        return freightAddressLabelDetail;
    }


    /**
     * Sets the freightAddressLabelDetail value for this ShippingDocumentSpecification.
     * 
     * @param freightAddressLabelDetail Data required to produce the Freight handling-unit-level address
 * labels. Note that the number of UNIQUE labels (the N as in 1 of N,
 * 2 of N, etc.) is determined by total handling units.
     */
    public void setFreightAddressLabelDetail(com.owd.fedEx.ShipService.FreightAddressLabelDetail freightAddressLabelDetail) {
        this.freightAddressLabelDetail = freightAddressLabelDetail;
    }


    /**
     * Gets the freightBillOfLadingDetail value for this ShippingDocumentSpecification.
     * 
     * @return freightBillOfLadingDetail
     */
    public com.owd.fedEx.ShipService.FreightBillOfLadingDetail getFreightBillOfLadingDetail() {
        return freightBillOfLadingDetail;
    }


    /**
     * Sets the freightBillOfLadingDetail value for this ShippingDocumentSpecification.
     * 
     * @param freightBillOfLadingDetail
     */
    public void setFreightBillOfLadingDetail(com.owd.fedEx.ShipService.FreightBillOfLadingDetail freightBillOfLadingDetail) {
        this.freightBillOfLadingDetail = freightBillOfLadingDetail;
    }


    /**
     * Gets the returnInstructionsDetail value for this ShippingDocumentSpecification.
     * 
     * @return returnInstructionsDetail The instructions indicating how to print the return instructions(
 * e.g. image type) Specifies characteristics of a shipping document
 * to be produced.
     */
    public com.owd.fedEx.ShipService.ReturnInstructionsDetail getReturnInstructionsDetail() {
        return returnInstructionsDetail;
    }


    /**
     * Sets the returnInstructionsDetail value for this ShippingDocumentSpecification.
     * 
     * @param returnInstructionsDetail The instructions indicating how to print the return instructions(
 * e.g. image type) Specifies characteristics of a shipping document
 * to be produced.
     */
    public void setReturnInstructionsDetail(com.owd.fedEx.ShipService.ReturnInstructionsDetail returnInstructionsDetail) {
        this.returnInstructionsDetail = returnInstructionsDetail;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ShippingDocumentSpecification)) return false;
        ShippingDocumentSpecification other = (ShippingDocumentSpecification) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.shippingDocumentTypes==null && other.getShippingDocumentTypes()==null) || 
             (this.shippingDocumentTypes!=null &&
              java.util.Arrays.equals(this.shippingDocumentTypes, other.getShippingDocumentTypes()))) &&
            ((this.certificateOfOrigin==null && other.getCertificateOfOrigin()==null) || 
             (this.certificateOfOrigin!=null &&
              this.certificateOfOrigin.equals(other.getCertificateOfOrigin()))) &&
            ((this.commercialInvoiceDetail==null && other.getCommercialInvoiceDetail()==null) || 
             (this.commercialInvoiceDetail!=null &&
              this.commercialInvoiceDetail.equals(other.getCommercialInvoiceDetail()))) &&
            ((this.customPackageDocumentDetail==null && other.getCustomPackageDocumentDetail()==null) || 
             (this.customPackageDocumentDetail!=null &&
              java.util.Arrays.equals(this.customPackageDocumentDetail, other.getCustomPackageDocumentDetail()))) &&
            ((this.customShipmentDocumentDetail==null && other.getCustomShipmentDocumentDetail()==null) || 
             (this.customShipmentDocumentDetail!=null &&
              java.util.Arrays.equals(this.customShipmentDocumentDetail, other.getCustomShipmentDocumentDetail()))) &&
            ((this.exportDeclarationDetail==null && other.getExportDeclarationDetail()==null) || 
             (this.exportDeclarationDetail!=null &&
              this.exportDeclarationDetail.equals(other.getExportDeclarationDetail()))) &&
            ((this.generalAgencyAgreementDetail==null && other.getGeneralAgencyAgreementDetail()==null) || 
             (this.generalAgencyAgreementDetail!=null &&
              this.generalAgencyAgreementDetail.equals(other.getGeneralAgencyAgreementDetail()))) &&
            ((this.naftaCertificateOfOriginDetail==null && other.getNaftaCertificateOfOriginDetail()==null) || 
             (this.naftaCertificateOfOriginDetail!=null &&
              this.naftaCertificateOfOriginDetail.equals(other.getNaftaCertificateOfOriginDetail()))) &&
            ((this.op900Detail==null && other.getOp900Detail()==null) || 
             (this.op900Detail!=null &&
              this.op900Detail.equals(other.getOp900Detail()))) &&
            ((this.dangerousGoodsShippersDeclarationDetail==null && other.getDangerousGoodsShippersDeclarationDetail()==null) || 
             (this.dangerousGoodsShippersDeclarationDetail!=null &&
              this.dangerousGoodsShippersDeclarationDetail.equals(other.getDangerousGoodsShippersDeclarationDetail()))) &&
            ((this.freightAddressLabelDetail==null && other.getFreightAddressLabelDetail()==null) || 
             (this.freightAddressLabelDetail!=null &&
              this.freightAddressLabelDetail.equals(other.getFreightAddressLabelDetail()))) &&
            ((this.freightBillOfLadingDetail==null && other.getFreightBillOfLadingDetail()==null) || 
             (this.freightBillOfLadingDetail!=null &&
              this.freightBillOfLadingDetail.equals(other.getFreightBillOfLadingDetail()))) &&
            ((this.returnInstructionsDetail==null && other.getReturnInstructionsDetail()==null) || 
             (this.returnInstructionsDetail!=null &&
              this.returnInstructionsDetail.equals(other.getReturnInstructionsDetail())));
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
        if (getShippingDocumentTypes() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getShippingDocumentTypes());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getShippingDocumentTypes(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getCertificateOfOrigin() != null) {
            _hashCode += getCertificateOfOrigin().hashCode();
        }
        if (getCommercialInvoiceDetail() != null) {
            _hashCode += getCommercialInvoiceDetail().hashCode();
        }
        if (getCustomPackageDocumentDetail() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getCustomPackageDocumentDetail());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getCustomPackageDocumentDetail(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getCustomShipmentDocumentDetail() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getCustomShipmentDocumentDetail());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getCustomShipmentDocumentDetail(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getExportDeclarationDetail() != null) {
            _hashCode += getExportDeclarationDetail().hashCode();
        }
        if (getGeneralAgencyAgreementDetail() != null) {
            _hashCode += getGeneralAgencyAgreementDetail().hashCode();
        }
        if (getNaftaCertificateOfOriginDetail() != null) {
            _hashCode += getNaftaCertificateOfOriginDetail().hashCode();
        }
        if (getOp900Detail() != null) {
            _hashCode += getOp900Detail().hashCode();
        }
        if (getDangerousGoodsShippersDeclarationDetail() != null) {
            _hashCode += getDangerousGoodsShippersDeclarationDetail().hashCode();
        }
        if (getFreightAddressLabelDetail() != null) {
            _hashCode += getFreightAddressLabelDetail().hashCode();
        }
        if (getFreightBillOfLadingDetail() != null) {
            _hashCode += getFreightBillOfLadingDetail().hashCode();
        }
        if (getReturnInstructionsDetail() != null) {
            _hashCode += getReturnInstructionsDetail().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ShippingDocumentSpecification.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ShippingDocumentSpecification"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("shippingDocumentTypes");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ShippingDocumentTypes"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "RequestedShippingDocumentType"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("certificateOfOrigin");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "CertificateOfOrigin"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "CertificateOfOriginDetail"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("commercialInvoiceDetail");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "CommercialInvoiceDetail"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "CommercialInvoiceDetail"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("customPackageDocumentDetail");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "CustomPackageDocumentDetail"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "CustomDocumentDetail"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("customShipmentDocumentDetail");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "CustomShipmentDocumentDetail"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "CustomDocumentDetail"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("exportDeclarationDetail");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ExportDeclarationDetail"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ExportDeclarationDetail"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("generalAgencyAgreementDetail");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "GeneralAgencyAgreementDetail"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "GeneralAgencyAgreementDetail"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("naftaCertificateOfOriginDetail");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "NaftaCertificateOfOriginDetail"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "NaftaCertificateOfOriginDetail"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("op900Detail");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Op900Detail"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Op900Detail"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dangerousGoodsShippersDeclarationDetail");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "DangerousGoodsShippersDeclarationDetail"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "DangerousGoodsShippersDeclarationDetail"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("freightAddressLabelDetail");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "FreightAddressLabelDetail"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "FreightAddressLabelDetail"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("freightBillOfLadingDetail");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "FreightBillOfLadingDetail"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "FreightBillOfLadingDetail"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("returnInstructionsDetail");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ReturnInstructionsDetail"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ReturnInstructionsDetail"));
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
