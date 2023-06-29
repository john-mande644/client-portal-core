/*
 * CustomsClearanceDetail.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;

public class CustomsClearanceDetail  implements java.io.Serializable {
    private com.owd.fedEx.ShipService.BrokerDetail[] brokers;
    /** Specifies the type of brokerage to be applied to a shipment. */
    private com.owd.fedEx.ShipService.ClearanceBrokerageType clearanceBrokerage;
    private com.owd.fedEx.ShipService.CustomsOptionDetail customsOptions;
    private com.owd.fedEx.ShipService.Party importerOfRecord;
    /** Specifies how the recipient is identified for customs purposes;
 * the requirements on this information vary with destination country. */
    private com.owd.fedEx.ShipService.RecipientCustomsId recipientCustomsId;
    private com.owd.fedEx.ShipService.Payment dutiesPayment;
    private com.owd.fedEx.ShipService.InternationalDocumentContentType documentContent;
    private com.owd.fedEx.ShipService.Money customsValue;
    /** Identifies responsibilities with respect to loss, damage, etc. */
    private com.owd.fedEx.ShipService.FreightOnValueType freightOnValue;
    private com.owd.fedEx.ShipService.Money insuranceCharges;
    private java.lang.Boolean partiesToTransactionAreRelated;
    /** CommercialInvoice element is required for electronic upload of
 * CI data. It will serve to create/transmit an Electronic Commercial
 * Invoice through the FedEx Systems. Customers are responsible for printing
 * their own Commercial Invoice.If you would likeFedEx to generate a
 * Commercial Invoice and transmit it to Customs. for clearance purposes,
 * you need to specify that in the ShippingDocumentSpecification element.
 * If you would like a copy of the Commercial Invoice that FedEx generated
 * returned to you in reply it needs to be specified in the ETDDetail/RequestedDocumentCopies
 * element. Commercial Invoice support consists of maximum of 99 commodity
 * line items. */
    private com.owd.fedEx.ShipService.CommercialInvoice commercialInvoice;
    private com.owd.fedEx.ShipService.Commodity[] commodities;
    private com.owd.fedEx.ShipService.ExportDetail exportDetail;
    private com.owd.fedEx.ShipService.RegulatoryControlType[] regulatoryControls;
    /** This provides the information necessary to identify the different
 * statements, declarations, acts, and/or certifications that apply to
 * this shipment. */
    private com.owd.fedEx.ShipService.CustomsDeclarationStatementDetail declarationStatementDetail;

    public CustomsClearanceDetail() {
    }


    /**
     * Gets the brokers value for this CustomsClearanceDetail.
     * 
     * @return brokers
     */
    public com.owd.fedEx.ShipService.BrokerDetail[] getBrokers() {
        return brokers;
    }


    /**
     * Sets the brokers value for this CustomsClearanceDetail.
     * 
     * @param brokers
     */
    public void setBrokers(com.owd.fedEx.ShipService.BrokerDetail[] brokers) {
        this.brokers = brokers;
    }

    public com.owd.fedEx.ShipService.BrokerDetail getBrokers(int i) {
        return brokers[i];
    }

    public void setBrokers(int i, com.owd.fedEx.ShipService.BrokerDetail value) {
        this.brokers[i] = value;
    }


    /**
     * Gets the clearanceBrokerage value for this CustomsClearanceDetail.
     * 
     * @return clearanceBrokerage Specifies the type of brokerage to be applied to a shipment.
     */
    public com.owd.fedEx.ShipService.ClearanceBrokerageType getClearanceBrokerage() {
        return clearanceBrokerage;
    }


    /**
     * Sets the clearanceBrokerage value for this CustomsClearanceDetail.
     * 
     * @param clearanceBrokerage Specifies the type of brokerage to be applied to a shipment.
     */
    public void setClearanceBrokerage(com.owd.fedEx.ShipService.ClearanceBrokerageType clearanceBrokerage) {
        this.clearanceBrokerage = clearanceBrokerage;
    }


    /**
     * Gets the customsOptions value for this CustomsClearanceDetail.
     * 
     * @return customsOptions
     */
    public com.owd.fedEx.ShipService.CustomsOptionDetail getCustomsOptions() {
        return customsOptions;
    }


    /**
     * Sets the customsOptions value for this CustomsClearanceDetail.
     * 
     * @param customsOptions
     */
    public void setCustomsOptions(com.owd.fedEx.ShipService.CustomsOptionDetail customsOptions) {
        this.customsOptions = customsOptions;
    }


    /**
     * Gets the importerOfRecord value for this CustomsClearanceDetail.
     * 
     * @return importerOfRecord
     */
    public com.owd.fedEx.ShipService.Party getImporterOfRecord() {
        return importerOfRecord;
    }


    /**
     * Sets the importerOfRecord value for this CustomsClearanceDetail.
     * 
     * @param importerOfRecord
     */
    public void setImporterOfRecord(com.owd.fedEx.ShipService.Party importerOfRecord) {
        this.importerOfRecord = importerOfRecord;
    }


    /**
     * Gets the recipientCustomsId value for this CustomsClearanceDetail.
     * 
     * @return recipientCustomsId Specifies how the recipient is identified for customs purposes;
 * the requirements on this information vary with destination country.
     */
    public com.owd.fedEx.ShipService.RecipientCustomsId getRecipientCustomsId() {
        return recipientCustomsId;
    }


    /**
     * Sets the recipientCustomsId value for this CustomsClearanceDetail.
     * 
     * @param recipientCustomsId Specifies how the recipient is identified for customs purposes;
 * the requirements on this information vary with destination country.
     */
    public void setRecipientCustomsId(com.owd.fedEx.ShipService.RecipientCustomsId recipientCustomsId) {
        this.recipientCustomsId = recipientCustomsId;
    }


    /**
     * Gets the dutiesPayment value for this CustomsClearanceDetail.
     * 
     * @return dutiesPayment
     */
    public com.owd.fedEx.ShipService.Payment getDutiesPayment() {
        return dutiesPayment;
    }


    /**
     * Sets the dutiesPayment value for this CustomsClearanceDetail.
     * 
     * @param dutiesPayment
     */
    public void setDutiesPayment(com.owd.fedEx.ShipService.Payment dutiesPayment) {
        this.dutiesPayment = dutiesPayment;
    }


    /**
     * Gets the documentContent value for this CustomsClearanceDetail.
     * 
     * @return documentContent
     */
    public com.owd.fedEx.ShipService.InternationalDocumentContentType getDocumentContent() {
        return documentContent;
    }


    /**
     * Sets the documentContent value for this CustomsClearanceDetail.
     * 
     * @param documentContent
     */
    public void setDocumentContent(com.owd.fedEx.ShipService.InternationalDocumentContentType documentContent) {
        this.documentContent = documentContent;
    }


    /**
     * Gets the customsValue value for this CustomsClearanceDetail.
     * 
     * @return customsValue
     */
    public com.owd.fedEx.ShipService.Money getCustomsValue() {
        return customsValue;
    }


    /**
     * Sets the customsValue value for this CustomsClearanceDetail.
     * 
     * @param customsValue
     */
    public void setCustomsValue(com.owd.fedEx.ShipService.Money customsValue) {
        this.customsValue = customsValue;
    }


    /**
     * Gets the freightOnValue value for this CustomsClearanceDetail.
     * 
     * @return freightOnValue Identifies responsibilities with respect to loss, damage, etc.
     */
    public com.owd.fedEx.ShipService.FreightOnValueType getFreightOnValue() {
        return freightOnValue;
    }


    /**
     * Sets the freightOnValue value for this CustomsClearanceDetail.
     * 
     * @param freightOnValue Identifies responsibilities with respect to loss, damage, etc.
     */
    public void setFreightOnValue(com.owd.fedEx.ShipService.FreightOnValueType freightOnValue) {
        this.freightOnValue = freightOnValue;
    }


    /**
     * Gets the insuranceCharges value for this CustomsClearanceDetail.
     * 
     * @return insuranceCharges
     */
    public com.owd.fedEx.ShipService.Money getInsuranceCharges() {
        return insuranceCharges;
    }


    /**
     * Sets the insuranceCharges value for this CustomsClearanceDetail.
     * 
     * @param insuranceCharges
     */
    public void setInsuranceCharges(com.owd.fedEx.ShipService.Money insuranceCharges) {
        this.insuranceCharges = insuranceCharges;
    }


    /**
     * Gets the partiesToTransactionAreRelated value for this CustomsClearanceDetail.
     * 
     * @return partiesToTransactionAreRelated
     */
    public java.lang.Boolean getPartiesToTransactionAreRelated() {
        return partiesToTransactionAreRelated;
    }


    /**
     * Sets the partiesToTransactionAreRelated value for this CustomsClearanceDetail.
     * 
     * @param partiesToTransactionAreRelated
     */
    public void setPartiesToTransactionAreRelated(java.lang.Boolean partiesToTransactionAreRelated) {
        this.partiesToTransactionAreRelated = partiesToTransactionAreRelated;
    }


    /**
     * Gets the commercialInvoice value for this CustomsClearanceDetail.
     * 
     * @return commercialInvoice CommercialInvoice element is required for electronic upload of
 * CI data. It will serve to create/transmit an Electronic Commercial
 * Invoice through the FedEx Systems. Customers are responsible for printing
 * their own Commercial Invoice.If you would likeFedEx to generate a
 * Commercial Invoice and transmit it to Customs. for clearance purposes,
 * you need to specify that in the ShippingDocumentSpecification element.
 * If you would like a copy of the Commercial Invoice that FedEx generated
 * returned to you in reply it needs to be specified in the ETDDetail/RequestedDocumentCopies
 * element. Commercial Invoice support consists of maximum of 99 commodity
 * line items.
     */
    public com.owd.fedEx.ShipService.CommercialInvoice getCommercialInvoice() {
        return commercialInvoice;
    }


    /**
     * Sets the commercialInvoice value for this CustomsClearanceDetail.
     * 
     * @param commercialInvoice CommercialInvoice element is required for electronic upload of
 * CI data. It will serve to create/transmit an Electronic Commercial
 * Invoice through the FedEx Systems. Customers are responsible for printing
 * their own Commercial Invoice.If you would likeFedEx to generate a
 * Commercial Invoice and transmit it to Customs. for clearance purposes,
 * you need to specify that in the ShippingDocumentSpecification element.
 * If you would like a copy of the Commercial Invoice that FedEx generated
 * returned to you in reply it needs to be specified in the ETDDetail/RequestedDocumentCopies
 * element. Commercial Invoice support consists of maximum of 99 commodity
 * line items.
     */
    public void setCommercialInvoice(com.owd.fedEx.ShipService.CommercialInvoice commercialInvoice) {
        this.commercialInvoice = commercialInvoice;
    }


    /**
     * Gets the commodities value for this CustomsClearanceDetail.
     * 
     * @return commodities
     */
    public com.owd.fedEx.ShipService.Commodity[] getCommodities() {
        return commodities;
    }


    /**
     * Sets the commodities value for this CustomsClearanceDetail.
     * 
     * @param commodities
     */
    public void setCommodities(com.owd.fedEx.ShipService.Commodity[] commodities) {
        this.commodities = commodities;
    }

    public com.owd.fedEx.ShipService.Commodity getCommodities(int i) {
        return commodities[i];
    }

    public void setCommodities(int i, com.owd.fedEx.ShipService.Commodity value) {
        this.commodities[i] = value;
    }


    /**
     * Gets the exportDetail value for this CustomsClearanceDetail.
     * 
     * @return exportDetail
     */
    public com.owd.fedEx.ShipService.ExportDetail getExportDetail() {
        return exportDetail;
    }


    /**
     * Sets the exportDetail value for this CustomsClearanceDetail.
     * 
     * @param exportDetail
     */
    public void setExportDetail(com.owd.fedEx.ShipService.ExportDetail exportDetail) {
        this.exportDetail = exportDetail;
    }


    /**
     * Gets the regulatoryControls value for this CustomsClearanceDetail.
     * 
     * @return regulatoryControls
     */
    public com.owd.fedEx.ShipService.RegulatoryControlType[] getRegulatoryControls() {
        return regulatoryControls;
    }


    /**
     * Sets the regulatoryControls value for this CustomsClearanceDetail.
     * 
     * @param regulatoryControls
     */
    public void setRegulatoryControls(com.owd.fedEx.ShipService.RegulatoryControlType[] regulatoryControls) {
        this.regulatoryControls = regulatoryControls;
    }

    public com.owd.fedEx.ShipService.RegulatoryControlType getRegulatoryControls(int i) {
        return regulatoryControls[i];
    }

    public void setRegulatoryControls(int i, com.owd.fedEx.ShipService.RegulatoryControlType value) {
        this.regulatoryControls[i] = value;
    }


    /**
     * Gets the declarationStatementDetail value for this CustomsClearanceDetail.
     * 
     * @return declarationStatementDetail This provides the information necessary to identify the different
 * statements, declarations, acts, and/or certifications that apply to
 * this shipment.
     */
    public com.owd.fedEx.ShipService.CustomsDeclarationStatementDetail getDeclarationStatementDetail() {
        return declarationStatementDetail;
    }


    /**
     * Sets the declarationStatementDetail value for this CustomsClearanceDetail.
     * 
     * @param declarationStatementDetail This provides the information necessary to identify the different
 * statements, declarations, acts, and/or certifications that apply to
 * this shipment.
     */
    public void setDeclarationStatementDetail(com.owd.fedEx.ShipService.CustomsDeclarationStatementDetail declarationStatementDetail) {
        this.declarationStatementDetail = declarationStatementDetail;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CustomsClearanceDetail)) return false;
        CustomsClearanceDetail other = (CustomsClearanceDetail) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.brokers==null && other.getBrokers()==null) || 
             (this.brokers!=null &&
              java.util.Arrays.equals(this.brokers, other.getBrokers()))) &&
            ((this.clearanceBrokerage==null && other.getClearanceBrokerage()==null) || 
             (this.clearanceBrokerage!=null &&
              this.clearanceBrokerage.equals(other.getClearanceBrokerage()))) &&
            ((this.customsOptions==null && other.getCustomsOptions()==null) || 
             (this.customsOptions!=null &&
              this.customsOptions.equals(other.getCustomsOptions()))) &&
            ((this.importerOfRecord==null && other.getImporterOfRecord()==null) || 
             (this.importerOfRecord!=null &&
              this.importerOfRecord.equals(other.getImporterOfRecord()))) &&
            ((this.recipientCustomsId==null && other.getRecipientCustomsId()==null) || 
             (this.recipientCustomsId!=null &&
              this.recipientCustomsId.equals(other.getRecipientCustomsId()))) &&
            ((this.dutiesPayment==null && other.getDutiesPayment()==null) || 
             (this.dutiesPayment!=null &&
              this.dutiesPayment.equals(other.getDutiesPayment()))) &&
            ((this.documentContent==null && other.getDocumentContent()==null) || 
             (this.documentContent!=null &&
              this.documentContent.equals(other.getDocumentContent()))) &&
            ((this.customsValue==null && other.getCustomsValue()==null) || 
             (this.customsValue!=null &&
              this.customsValue.equals(other.getCustomsValue()))) &&
            ((this.freightOnValue==null && other.getFreightOnValue()==null) || 
             (this.freightOnValue!=null &&
              this.freightOnValue.equals(other.getFreightOnValue()))) &&
            ((this.insuranceCharges==null && other.getInsuranceCharges()==null) || 
             (this.insuranceCharges!=null &&
              this.insuranceCharges.equals(other.getInsuranceCharges()))) &&
            ((this.partiesToTransactionAreRelated==null && other.getPartiesToTransactionAreRelated()==null) || 
             (this.partiesToTransactionAreRelated!=null &&
              this.partiesToTransactionAreRelated.equals(other.getPartiesToTransactionAreRelated()))) &&
            ((this.commercialInvoice==null && other.getCommercialInvoice()==null) || 
             (this.commercialInvoice!=null &&
              this.commercialInvoice.equals(other.getCommercialInvoice()))) &&
            ((this.commodities==null && other.getCommodities()==null) || 
             (this.commodities!=null &&
              java.util.Arrays.equals(this.commodities, other.getCommodities()))) &&
            ((this.exportDetail==null && other.getExportDetail()==null) || 
             (this.exportDetail!=null &&
              this.exportDetail.equals(other.getExportDetail()))) &&
            ((this.regulatoryControls==null && other.getRegulatoryControls()==null) || 
             (this.regulatoryControls!=null &&
              java.util.Arrays.equals(this.regulatoryControls, other.getRegulatoryControls()))) &&
            ((this.declarationStatementDetail==null && other.getDeclarationStatementDetail()==null) || 
             (this.declarationStatementDetail!=null &&
              this.declarationStatementDetail.equals(other.getDeclarationStatementDetail())));
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
        if (getBrokers() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getBrokers());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getBrokers(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getClearanceBrokerage() != null) {
            _hashCode += getClearanceBrokerage().hashCode();
        }
        if (getCustomsOptions() != null) {
            _hashCode += getCustomsOptions().hashCode();
        }
        if (getImporterOfRecord() != null) {
            _hashCode += getImporterOfRecord().hashCode();
        }
        if (getRecipientCustomsId() != null) {
            _hashCode += getRecipientCustomsId().hashCode();
        }
        if (getDutiesPayment() != null) {
            _hashCode += getDutiesPayment().hashCode();
        }
        if (getDocumentContent() != null) {
            _hashCode += getDocumentContent().hashCode();
        }
        if (getCustomsValue() != null) {
            _hashCode += getCustomsValue().hashCode();
        }
        if (getFreightOnValue() != null) {
            _hashCode += getFreightOnValue().hashCode();
        }
        if (getInsuranceCharges() != null) {
            _hashCode += getInsuranceCharges().hashCode();
        }
        if (getPartiesToTransactionAreRelated() != null) {
            _hashCode += getPartiesToTransactionAreRelated().hashCode();
        }
        if (getCommercialInvoice() != null) {
            _hashCode += getCommercialInvoice().hashCode();
        }
        if (getCommodities() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getCommodities());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getCommodities(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getExportDetail() != null) {
            _hashCode += getExportDetail().hashCode();
        }
        if (getRegulatoryControls() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getRegulatoryControls());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getRegulatoryControls(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getDeclarationStatementDetail() != null) {
            _hashCode += getDeclarationStatementDetail().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CustomsClearanceDetail.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "CustomsClearanceDetail"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("brokers");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Brokers"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "BrokerDetail"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("clearanceBrokerage");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ClearanceBrokerage"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ClearanceBrokerageType"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("customsOptions");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "CustomsOptions"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "CustomsOptionDetail"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("importerOfRecord");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ImporterOfRecord"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Party"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("recipientCustomsId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "RecipientCustomsId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "RecipientCustomsId"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dutiesPayment");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "DutiesPayment"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Payment"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("documentContent");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "DocumentContent"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "InternationalDocumentContentType"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("customsValue");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "CustomsValue"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Money"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("freightOnValue");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "FreightOnValue"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "FreightOnValueType"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("insuranceCharges");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "InsuranceCharges"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Money"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("partiesToTransactionAreRelated");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "PartiesToTransactionAreRelated"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "boolean"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("commercialInvoice");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "CommercialInvoice"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "CommercialInvoice"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("commodities");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Commodities"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Commodity"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("exportDetail");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ExportDetail"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ExportDetail"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("regulatoryControls");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "RegulatoryControls"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "RegulatoryControlType"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("declarationStatementDetail");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "DeclarationStatementDetail"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "CustomsDeclarationStatementDetail"));
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
