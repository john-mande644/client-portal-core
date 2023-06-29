/*
 * FreightSpecialServicePayment.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;


/**
 * Specifies which party will be responsible for payment of any surcharges
 * for Freight special services for which split billing is allowed.
 */
public class FreightSpecialServicePayment  implements java.io.Serializable {
    private com.owd.fedEx.ShipService.ShipmentSpecialServiceType specialService;
    /** Indicates the role of the party submitting the transaction. */
    private com.owd.fedEx.ShipService.FreightShipmentRoleType paymentType;

    public FreightSpecialServicePayment() {
    }


    /**
     * Gets the specialService value for this FreightSpecialServicePayment.
     * 
     * @return specialService
     */
    public com.owd.fedEx.ShipService.ShipmentSpecialServiceType getSpecialService() {
        return specialService;
    }


    /**
     * Sets the specialService value for this FreightSpecialServicePayment.
     * 
     * @param specialService
     */
    public void setSpecialService(com.owd.fedEx.ShipService.ShipmentSpecialServiceType specialService) {
        this.specialService = specialService;
    }


    /**
     * Gets the paymentType value for this FreightSpecialServicePayment.
     * 
     * @return paymentType Indicates the role of the party submitting the transaction.
     */
    public com.owd.fedEx.ShipService.FreightShipmentRoleType getPaymentType() {
        return paymentType;
    }


    /**
     * Sets the paymentType value for this FreightSpecialServicePayment.
     * 
     * @param paymentType Indicates the role of the party submitting the transaction.
     */
    public void setPaymentType(com.owd.fedEx.ShipService.FreightShipmentRoleType paymentType) {
        this.paymentType = paymentType;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof FreightSpecialServicePayment)) return false;
        FreightSpecialServicePayment other = (FreightSpecialServicePayment) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.specialService==null && other.getSpecialService()==null) || 
             (this.specialService!=null &&
              this.specialService.equals(other.getSpecialService()))) &&
            ((this.paymentType==null && other.getPaymentType()==null) || 
             (this.paymentType!=null &&
              this.paymentType.equals(other.getPaymentType())));
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
        if (getSpecialService() != null) {
            _hashCode += getSpecialService().hashCode();
        }
        if (getPaymentType() != null) {
            _hashCode += getPaymentType().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(FreightSpecialServicePayment.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "FreightSpecialServicePayment"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("specialService");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "SpecialService"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ShipmentSpecialServiceType"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("paymentType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "PaymentType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "FreightShipmentRoleType"));
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
