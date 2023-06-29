/*
 * ClientDetail.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;


/**
 * Descriptive data for the client submitting a transaction.
 */
public class ClientDetail  implements java.io.Serializable {
    private java.lang.String accountNumber;
    private java.lang.String meterNumber;
    private java.lang.String integratorId;
    /** Identifies the representation of human-readable text. */
    private com.owd.fedEx.ShipService.Localization localization;

    public ClientDetail() {
    }


    /**
     * Gets the accountNumber value for this ClientDetail.
     * 
     * @return accountNumber
     */
    public java.lang.String getAccountNumber() {
        return accountNumber;
    }


    /**
     * Sets the accountNumber value for this ClientDetail.
     * 
     * @param accountNumber
     */
    public void setAccountNumber(java.lang.String accountNumber) {
        this.accountNumber = accountNumber;
    }


    /**
     * Gets the meterNumber value for this ClientDetail.
     * 
     * @return meterNumber
     */
    public java.lang.String getMeterNumber() {
        return meterNumber;
    }


    /**
     * Sets the meterNumber value for this ClientDetail.
     * 
     * @param meterNumber
     */
    public void setMeterNumber(java.lang.String meterNumber) {
        this.meterNumber = meterNumber;
    }


    /**
     * Gets the integratorId value for this ClientDetail.
     * 
     * @return integratorId
     */
    public java.lang.String getIntegratorId() {
        return integratorId;
    }


    /**
     * Sets the integratorId value for this ClientDetail.
     * 
     * @param integratorId
     */
    public void setIntegratorId(java.lang.String integratorId) {
        this.integratorId = integratorId;
    }


    /**
     * Gets the localization value for this ClientDetail.
     * 
     * @return localization Identifies the representation of human-readable text.
     */
    public com.owd.fedEx.ShipService.Localization getLocalization() {
        return localization;
    }


    /**
     * Sets the localization value for this ClientDetail.
     * 
     * @param localization Identifies the representation of human-readable text.
     */
    public void setLocalization(com.owd.fedEx.ShipService.Localization localization) {
        this.localization = localization;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ClientDetail)) return false;
        ClientDetail other = (ClientDetail) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.accountNumber==null && other.getAccountNumber()==null) || 
             (this.accountNumber!=null &&
              this.accountNumber.equals(other.getAccountNumber()))) &&
            ((this.meterNumber==null && other.getMeterNumber()==null) || 
             (this.meterNumber!=null &&
              this.meterNumber.equals(other.getMeterNumber()))) &&
            ((this.integratorId==null && other.getIntegratorId()==null) || 
             (this.integratorId!=null &&
              this.integratorId.equals(other.getIntegratorId()))) &&
            ((this.localization==null && other.getLocalization()==null) || 
             (this.localization!=null &&
              this.localization.equals(other.getLocalization())));
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
        if (getAccountNumber() != null) {
            _hashCode += getAccountNumber().hashCode();
        }
        if (getMeterNumber() != null) {
            _hashCode += getMeterNumber().hashCode();
        }
        if (getIntegratorId() != null) {
            _hashCode += getIntegratorId().hashCode();
        }
        if (getLocalization() != null) {
            _hashCode += getLocalization().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ClientDetail.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ClientDetail"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("accountNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "AccountNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("meterNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "MeterNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("integratorId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "IntegratorId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("localization");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Localization"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Localization"));
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
