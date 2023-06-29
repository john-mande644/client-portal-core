/*
 * FreightRateDetail.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;


/**
 * Rate data specific to FedEx Freight or FedEx National Freight services.
 */
public class FreightRateDetail  implements java.io.Serializable {
    private java.lang.String quoteNumber;
    /** Specifies the type of rate quote */
    private com.owd.fedEx.ShipService.FreightRateQuoteType quoteType;
    /** Specifies the way in which base charges for a Freight shipment
 * or shipment leg are calculated. */
    private com.owd.fedEx.ShipService.FreightBaseChargeCalculationType baseChargeCalculation;
    /** Freight charges which accumulate to the total base charge for the
 * shipment. */
    private com.owd.fedEx.ShipService.FreightBaseCharge[] baseCharges;
    /** Human-readable descriptions of additional information on this shipment
 * rating. */
    private com.owd.fedEx.ShipService.FreightRateNotation[] notations;

    public FreightRateDetail() {
    }


    /**
     * Gets the quoteNumber value for this FreightRateDetail.
     * 
     * @return quoteNumber
     */
    public java.lang.String getQuoteNumber() {
        return quoteNumber;
    }


    /**
     * Sets the quoteNumber value for this FreightRateDetail.
     * 
     * @param quoteNumber
     */
    public void setQuoteNumber(java.lang.String quoteNumber) {
        this.quoteNumber = quoteNumber;
    }


    /**
     * Gets the quoteType value for this FreightRateDetail.
     * 
     * @return quoteType Specifies the type of rate quote
     */
    public com.owd.fedEx.ShipService.FreightRateQuoteType getQuoteType() {
        return quoteType;
    }


    /**
     * Sets the quoteType value for this FreightRateDetail.
     * 
     * @param quoteType Specifies the type of rate quote
     */
    public void setQuoteType(com.owd.fedEx.ShipService.FreightRateQuoteType quoteType) {
        this.quoteType = quoteType;
    }


    /**
     * Gets the baseChargeCalculation value for this FreightRateDetail.
     * 
     * @return baseChargeCalculation Specifies the way in which base charges for a Freight shipment
 * or shipment leg are calculated.
     */
    public com.owd.fedEx.ShipService.FreightBaseChargeCalculationType getBaseChargeCalculation() {
        return baseChargeCalculation;
    }


    /**
     * Sets the baseChargeCalculation value for this FreightRateDetail.
     * 
     * @param baseChargeCalculation Specifies the way in which base charges for a Freight shipment
 * or shipment leg are calculated.
     */
    public void setBaseChargeCalculation(com.owd.fedEx.ShipService.FreightBaseChargeCalculationType baseChargeCalculation) {
        this.baseChargeCalculation = baseChargeCalculation;
    }


    /**
     * Gets the baseCharges value for this FreightRateDetail.
     * 
     * @return baseCharges Freight charges which accumulate to the total base charge for the
 * shipment.
     */
    public com.owd.fedEx.ShipService.FreightBaseCharge[] getBaseCharges() {
        return baseCharges;
    }


    /**
     * Sets the baseCharges value for this FreightRateDetail.
     * 
     * @param baseCharges Freight charges which accumulate to the total base charge for the
 * shipment.
     */
    public void setBaseCharges(com.owd.fedEx.ShipService.FreightBaseCharge[] baseCharges) {
        this.baseCharges = baseCharges;
    }

    public com.owd.fedEx.ShipService.FreightBaseCharge getBaseCharges(int i) {
        return baseCharges[i];
    }

    public void setBaseCharges(int i, com.owd.fedEx.ShipService.FreightBaseCharge value) {
        this.baseCharges[i] = value;
    }


    /**
     * Gets the notations value for this FreightRateDetail.
     * 
     * @return notations Human-readable descriptions of additional information on this shipment
 * rating.
     */
    public com.owd.fedEx.ShipService.FreightRateNotation[] getNotations() {
        return notations;
    }


    /**
     * Sets the notations value for this FreightRateDetail.
     * 
     * @param notations Human-readable descriptions of additional information on this shipment
 * rating.
     */
    public void setNotations(com.owd.fedEx.ShipService.FreightRateNotation[] notations) {
        this.notations = notations;
    }

    public com.owd.fedEx.ShipService.FreightRateNotation getNotations(int i) {
        return notations[i];
    }

    public void setNotations(int i, com.owd.fedEx.ShipService.FreightRateNotation value) {
        this.notations[i] = value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof FreightRateDetail)) return false;
        FreightRateDetail other = (FreightRateDetail) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.quoteNumber==null && other.getQuoteNumber()==null) || 
             (this.quoteNumber!=null &&
              this.quoteNumber.equals(other.getQuoteNumber()))) &&
            ((this.quoteType==null && other.getQuoteType()==null) || 
             (this.quoteType!=null &&
              this.quoteType.equals(other.getQuoteType()))) &&
            ((this.baseChargeCalculation==null && other.getBaseChargeCalculation()==null) || 
             (this.baseChargeCalculation!=null &&
              this.baseChargeCalculation.equals(other.getBaseChargeCalculation()))) &&
            ((this.baseCharges==null && other.getBaseCharges()==null) || 
             (this.baseCharges!=null &&
              java.util.Arrays.equals(this.baseCharges, other.getBaseCharges()))) &&
            ((this.notations==null && other.getNotations()==null) || 
             (this.notations!=null &&
              java.util.Arrays.equals(this.notations, other.getNotations())));
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
        if (getQuoteNumber() != null) {
            _hashCode += getQuoteNumber().hashCode();
        }
        if (getQuoteType() != null) {
            _hashCode += getQuoteType().hashCode();
        }
        if (getBaseChargeCalculation() != null) {
            _hashCode += getBaseChargeCalculation().hashCode();
        }
        if (getBaseCharges() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getBaseCharges());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getBaseCharges(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getNotations() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getNotations());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getNotations(), i);
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
        new org.apache.axis.description.TypeDesc(FreightRateDetail.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "FreightRateDetail"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("quoteNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "QuoteNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("quoteType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "QuoteType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "FreightRateQuoteType"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("baseChargeCalculation");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "BaseChargeCalculation"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "FreightBaseChargeCalculationType"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("baseCharges");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "BaseCharges"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "FreightBaseCharge"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("notations");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Notations"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "FreightRateNotation"));
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
