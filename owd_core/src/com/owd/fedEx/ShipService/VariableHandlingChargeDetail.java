/*
 * VariableHandlingChargeDetail.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;


/**
 * This definition of variable handling charge detail is intended
 * for use in Jan 2011 corp load.
 */
public class VariableHandlingChargeDetail  implements java.io.Serializable {
    private com.owd.fedEx.ShipService.Money fixedValue;
    private java.math.BigDecimal percentValue;
    /** Selects the value from a set of rate data to which the percentage
 * is applied. */
    private com.owd.fedEx.ShipService.RateElementBasisType rateElementBasis;
    /** Select the type of rate from which the element is to be selected. */
    private com.owd.fedEx.ShipService.RateTypeBasisType rateTypeBasis;

    public VariableHandlingChargeDetail() {
    }


    /**
     * Gets the fixedValue value for this VariableHandlingChargeDetail.
     * 
     * @return fixedValue
     */
    public com.owd.fedEx.ShipService.Money getFixedValue() {
        return fixedValue;
    }


    /**
     * Sets the fixedValue value for this VariableHandlingChargeDetail.
     * 
     * @param fixedValue
     */
    public void setFixedValue(com.owd.fedEx.ShipService.Money fixedValue) {
        this.fixedValue = fixedValue;
    }


    /**
     * Gets the percentValue value for this VariableHandlingChargeDetail.
     * 
     * @return percentValue
     */
    public java.math.BigDecimal getPercentValue() {
        return percentValue;
    }


    /**
     * Sets the percentValue value for this VariableHandlingChargeDetail.
     * 
     * @param percentValue
     */
    public void setPercentValue(java.math.BigDecimal percentValue) {
        this.percentValue = percentValue;
    }


    /**
     * Gets the rateElementBasis value for this VariableHandlingChargeDetail.
     * 
     * @return rateElementBasis Selects the value from a set of rate data to which the percentage
 * is applied.
     */
    public com.owd.fedEx.ShipService.RateElementBasisType getRateElementBasis() {
        return rateElementBasis;
    }


    /**
     * Sets the rateElementBasis value for this VariableHandlingChargeDetail.
     * 
     * @param rateElementBasis Selects the value from a set of rate data to which the percentage
 * is applied.
     */
    public void setRateElementBasis(com.owd.fedEx.ShipService.RateElementBasisType rateElementBasis) {
        this.rateElementBasis = rateElementBasis;
    }


    /**
     * Gets the rateTypeBasis value for this VariableHandlingChargeDetail.
     * 
     * @return rateTypeBasis Select the type of rate from which the element is to be selected.
     */
    public com.owd.fedEx.ShipService.RateTypeBasisType getRateTypeBasis() {
        return rateTypeBasis;
    }


    /**
     * Sets the rateTypeBasis value for this VariableHandlingChargeDetail.
     * 
     * @param rateTypeBasis Select the type of rate from which the element is to be selected.
     */
    public void setRateTypeBasis(com.owd.fedEx.ShipService.RateTypeBasisType rateTypeBasis) {
        this.rateTypeBasis = rateTypeBasis;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof VariableHandlingChargeDetail)) return false;
        VariableHandlingChargeDetail other = (VariableHandlingChargeDetail) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.fixedValue==null && other.getFixedValue()==null) || 
             (this.fixedValue!=null &&
              this.fixedValue.equals(other.getFixedValue()))) &&
            ((this.percentValue==null && other.getPercentValue()==null) || 
             (this.percentValue!=null &&
              this.percentValue.equals(other.getPercentValue()))) &&
            ((this.rateElementBasis==null && other.getRateElementBasis()==null) || 
             (this.rateElementBasis!=null &&
              this.rateElementBasis.equals(other.getRateElementBasis()))) &&
            ((this.rateTypeBasis==null && other.getRateTypeBasis()==null) || 
             (this.rateTypeBasis!=null &&
              this.rateTypeBasis.equals(other.getRateTypeBasis())));
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
        if (getFixedValue() != null) {
            _hashCode += getFixedValue().hashCode();
        }
        if (getPercentValue() != null) {
            _hashCode += getPercentValue().hashCode();
        }
        if (getRateElementBasis() != null) {
            _hashCode += getRateElementBasis().hashCode();
        }
        if (getRateTypeBasis() != null) {
            _hashCode += getRateTypeBasis().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(VariableHandlingChargeDetail.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "VariableHandlingChargeDetail"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fixedValue");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "FixedValue"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Money"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("percentValue");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "PercentValue"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rateElementBasis");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "RateElementBasis"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "RateElementBasisType"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rateTypeBasis");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "RateTypeBasis"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "RateTypeBasisType"));
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
