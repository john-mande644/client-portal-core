/*
 * CommercialInvoice.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;


/**
 * CommercialInvoice element is required for electronic upload of
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
public class CommercialInvoice  implements java.io.Serializable {
    /** Any comments that need to be communicated about this shipment. */
    private java.lang.String[] comments;
    private com.owd.fedEx.ShipService.Money freightCharge;
    private com.owd.fedEx.ShipService.Money taxesOrMiscellaneousCharge;
    /** Specifice the kind of tax or miscellaneous charge being reported
 * on a Commercial Invoice. */
    private com.owd.fedEx.ShipService.TaxesOrMiscellaneousChargeType taxesOrMiscellaneousChargeType;
    private com.owd.fedEx.ShipService.Money packingCosts;
    private com.owd.fedEx.ShipService.Money handlingCosts;
    private java.lang.String specialInstructions;
    private java.lang.String declarationStatement;
    private java.lang.String paymentTerms;
    private com.owd.fedEx.ShipService.PurposeOfShipmentType purpose;
    /** Specifies additional customer reference data about the associated
 * shipment. */
    private com.owd.fedEx.ShipService.CustomerReference[] customerReferences;
    private java.lang.String originatorName;
    private java.lang.String termsOfSale;

    public CommercialInvoice() {
    }


    /**
     * Gets the comments value for this CommercialInvoice.
     * 
     * @return comments Any comments that need to be communicated about this shipment.
     */
    public java.lang.String[] getComments() {
        return comments;
    }


    /**
     * Sets the comments value for this CommercialInvoice.
     * 
     * @param comments Any comments that need to be communicated about this shipment.
     */
    public void setComments(java.lang.String[] comments) {
        this.comments = comments;
    }

    public java.lang.String getComments(int i) {
        return comments[i];
    }

    public void setComments(int i, java.lang.String value) {
        this.comments[i] = value;
    }


    /**
     * Gets the freightCharge value for this CommercialInvoice.
     * 
     * @return freightCharge
     */
    public com.owd.fedEx.ShipService.Money getFreightCharge() {
        return freightCharge;
    }


    /**
     * Sets the freightCharge value for this CommercialInvoice.
     * 
     * @param freightCharge
     */
    public void setFreightCharge(com.owd.fedEx.ShipService.Money freightCharge) {
        this.freightCharge = freightCharge;
    }


    /**
     * Gets the taxesOrMiscellaneousCharge value for this CommercialInvoice.
     * 
     * @return taxesOrMiscellaneousCharge
     */
    public com.owd.fedEx.ShipService.Money getTaxesOrMiscellaneousCharge() {
        return taxesOrMiscellaneousCharge;
    }


    /**
     * Sets the taxesOrMiscellaneousCharge value for this CommercialInvoice.
     * 
     * @param taxesOrMiscellaneousCharge
     */
    public void setTaxesOrMiscellaneousCharge(com.owd.fedEx.ShipService.Money taxesOrMiscellaneousCharge) {
        this.taxesOrMiscellaneousCharge = taxesOrMiscellaneousCharge;
    }


    /**
     * Gets the taxesOrMiscellaneousChargeType value for this CommercialInvoice.
     * 
     * @return taxesOrMiscellaneousChargeType Specifice the kind of tax or miscellaneous charge being reported
 * on a Commercial Invoice.
     */
    public com.owd.fedEx.ShipService.TaxesOrMiscellaneousChargeType getTaxesOrMiscellaneousChargeType() {
        return taxesOrMiscellaneousChargeType;
    }


    /**
     * Sets the taxesOrMiscellaneousChargeType value for this CommercialInvoice.
     * 
     * @param taxesOrMiscellaneousChargeType Specifice the kind of tax or miscellaneous charge being reported
 * on a Commercial Invoice.
     */
    public void setTaxesOrMiscellaneousChargeType(com.owd.fedEx.ShipService.TaxesOrMiscellaneousChargeType taxesOrMiscellaneousChargeType) {
        this.taxesOrMiscellaneousChargeType = taxesOrMiscellaneousChargeType;
    }


    /**
     * Gets the packingCosts value for this CommercialInvoice.
     * 
     * @return packingCosts
     */
    public com.owd.fedEx.ShipService.Money getPackingCosts() {
        return packingCosts;
    }


    /**
     * Sets the packingCosts value for this CommercialInvoice.
     * 
     * @param packingCosts
     */
    public void setPackingCosts(com.owd.fedEx.ShipService.Money packingCosts) {
        this.packingCosts = packingCosts;
    }


    /**
     * Gets the handlingCosts value for this CommercialInvoice.
     * 
     * @return handlingCosts
     */
    public com.owd.fedEx.ShipService.Money getHandlingCosts() {
        return handlingCosts;
    }


    /**
     * Sets the handlingCosts value for this CommercialInvoice.
     * 
     * @param handlingCosts
     */
    public void setHandlingCosts(com.owd.fedEx.ShipService.Money handlingCosts) {
        this.handlingCosts = handlingCosts;
    }


    /**
     * Gets the specialInstructions value for this CommercialInvoice.
     * 
     * @return specialInstructions
     */
    public java.lang.String getSpecialInstructions() {
        return specialInstructions;
    }


    /**
     * Sets the specialInstructions value for this CommercialInvoice.
     * 
     * @param specialInstructions
     */
    public void setSpecialInstructions(java.lang.String specialInstructions) {
        this.specialInstructions = specialInstructions;
    }


    /**
     * Gets the declarationStatement value for this CommercialInvoice.
     * 
     * @return declarationStatement
     */
    public java.lang.String getDeclarationStatement() {
        return declarationStatement;
    }


    /**
     * Sets the declarationStatement value for this CommercialInvoice.
     * 
     * @param declarationStatement
     */
    public void setDeclarationStatement(java.lang.String declarationStatement) {
        this.declarationStatement = declarationStatement;
    }


    /**
     * Gets the paymentTerms value for this CommercialInvoice.
     * 
     * @return paymentTerms
     */
    public java.lang.String getPaymentTerms() {
        return paymentTerms;
    }


    /**
     * Sets the paymentTerms value for this CommercialInvoice.
     * 
     * @param paymentTerms
     */
    public void setPaymentTerms(java.lang.String paymentTerms) {
        this.paymentTerms = paymentTerms;
    }


    /**
     * Gets the purpose value for this CommercialInvoice.
     * 
     * @return purpose
     */
    public com.owd.fedEx.ShipService.PurposeOfShipmentType getPurpose() {
        return purpose;
    }


    /**
     * Sets the purpose value for this CommercialInvoice.
     * 
     * @param purpose
     */
    public void setPurpose(com.owd.fedEx.ShipService.PurposeOfShipmentType purpose) {
        this.purpose = purpose;
    }


    /**
     * Gets the customerReferences value for this CommercialInvoice.
     * 
     * @return customerReferences Specifies additional customer reference data about the associated
 * shipment.
     */
    public com.owd.fedEx.ShipService.CustomerReference[] getCustomerReferences() {
        return customerReferences;
    }


    /**
     * Sets the customerReferences value for this CommercialInvoice.
     * 
     * @param customerReferences Specifies additional customer reference data about the associated
 * shipment.
     */
    public void setCustomerReferences(com.owd.fedEx.ShipService.CustomerReference[] customerReferences) {
        this.customerReferences = customerReferences;
    }

    public com.owd.fedEx.ShipService.CustomerReference getCustomerReferences(int i) {
        return customerReferences[i];
    }

    public void setCustomerReferences(int i, com.owd.fedEx.ShipService.CustomerReference value) {
        this.customerReferences[i] = value;
    }


    /**
     * Gets the originatorName value for this CommercialInvoice.
     * 
     * @return originatorName
     */
    public java.lang.String getOriginatorName() {
        return originatorName;
    }


    /**
     * Sets the originatorName value for this CommercialInvoice.
     * 
     * @param originatorName
     */
    public void setOriginatorName(java.lang.String originatorName) {
        this.originatorName = originatorName;
    }


    /**
     * Gets the termsOfSale value for this CommercialInvoice.
     * 
     * @return termsOfSale
     */
    public java.lang.String getTermsOfSale() {
        return termsOfSale;
    }


    /**
     * Sets the termsOfSale value for this CommercialInvoice.
     * 
     * @param termsOfSale
     */
    public void setTermsOfSale(java.lang.String termsOfSale) {
        this.termsOfSale = termsOfSale;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CommercialInvoice)) return false;
        CommercialInvoice other = (CommercialInvoice) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.comments==null && other.getComments()==null) || 
             (this.comments!=null &&
              java.util.Arrays.equals(this.comments, other.getComments()))) &&
            ((this.freightCharge==null && other.getFreightCharge()==null) || 
             (this.freightCharge!=null &&
              this.freightCharge.equals(other.getFreightCharge()))) &&
            ((this.taxesOrMiscellaneousCharge==null && other.getTaxesOrMiscellaneousCharge()==null) || 
             (this.taxesOrMiscellaneousCharge!=null &&
              this.taxesOrMiscellaneousCharge.equals(other.getTaxesOrMiscellaneousCharge()))) &&
            ((this.taxesOrMiscellaneousChargeType==null && other.getTaxesOrMiscellaneousChargeType()==null) || 
             (this.taxesOrMiscellaneousChargeType!=null &&
              this.taxesOrMiscellaneousChargeType.equals(other.getTaxesOrMiscellaneousChargeType()))) &&
            ((this.packingCosts==null && other.getPackingCosts()==null) || 
             (this.packingCosts!=null &&
              this.packingCosts.equals(other.getPackingCosts()))) &&
            ((this.handlingCosts==null && other.getHandlingCosts()==null) || 
             (this.handlingCosts!=null &&
              this.handlingCosts.equals(other.getHandlingCosts()))) &&
            ((this.specialInstructions==null && other.getSpecialInstructions()==null) || 
             (this.specialInstructions!=null &&
              this.specialInstructions.equals(other.getSpecialInstructions()))) &&
            ((this.declarationStatement==null && other.getDeclarationStatement()==null) || 
             (this.declarationStatement!=null &&
              this.declarationStatement.equals(other.getDeclarationStatement()))) &&
            ((this.paymentTerms==null && other.getPaymentTerms()==null) || 
             (this.paymentTerms!=null &&
              this.paymentTerms.equals(other.getPaymentTerms()))) &&
            ((this.purpose==null && other.getPurpose()==null) || 
             (this.purpose!=null &&
              this.purpose.equals(other.getPurpose()))) &&
            ((this.customerReferences==null && other.getCustomerReferences()==null) || 
             (this.customerReferences!=null &&
              java.util.Arrays.equals(this.customerReferences, other.getCustomerReferences()))) &&
            ((this.originatorName==null && other.getOriginatorName()==null) || 
             (this.originatorName!=null &&
              this.originatorName.equals(other.getOriginatorName()))) &&
            ((this.termsOfSale==null && other.getTermsOfSale()==null) || 
             (this.termsOfSale!=null &&
              this.termsOfSale.equals(other.getTermsOfSale())));
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
        if (getComments() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getComments());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getComments(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getFreightCharge() != null) {
            _hashCode += getFreightCharge().hashCode();
        }
        if (getTaxesOrMiscellaneousCharge() != null) {
            _hashCode += getTaxesOrMiscellaneousCharge().hashCode();
        }
        if (getTaxesOrMiscellaneousChargeType() != null) {
            _hashCode += getTaxesOrMiscellaneousChargeType().hashCode();
        }
        if (getPackingCosts() != null) {
            _hashCode += getPackingCosts().hashCode();
        }
        if (getHandlingCosts() != null) {
            _hashCode += getHandlingCosts().hashCode();
        }
        if (getSpecialInstructions() != null) {
            _hashCode += getSpecialInstructions().hashCode();
        }
        if (getDeclarationStatement() != null) {
            _hashCode += getDeclarationStatement().hashCode();
        }
        if (getPaymentTerms() != null) {
            _hashCode += getPaymentTerms().hashCode();
        }
        if (getPurpose() != null) {
            _hashCode += getPurpose().hashCode();
        }
        if (getCustomerReferences() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getCustomerReferences());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getCustomerReferences(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getOriginatorName() != null) {
            _hashCode += getOriginatorName().hashCode();
        }
        if (getTermsOfSale() != null) {
            _hashCode += getTermsOfSale().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CommercialInvoice.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "CommercialInvoice"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("comments");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Comments"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("freightCharge");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "FreightCharge"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Money"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("taxesOrMiscellaneousCharge");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "TaxesOrMiscellaneousCharge"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Money"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("taxesOrMiscellaneousChargeType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "TaxesOrMiscellaneousChargeType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "TaxesOrMiscellaneousChargeType"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("packingCosts");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "PackingCosts"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Money"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("handlingCosts");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "HandlingCosts"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Money"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("specialInstructions");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "SpecialInstructions"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("declarationStatement");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "DeclarationStatement"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("paymentTerms");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "PaymentTerms"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("purpose");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Purpose"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "PurposeOfShipmentType"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("customerReferences");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "CustomerReferences"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "CustomerReference"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("originatorName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "OriginatorName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("termsOfSale");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "TermsOfSale"));
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
