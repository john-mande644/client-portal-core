/**
 * ClientDetails.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.owd.onestop;

public class ClientDetails  extends com.owd.onestop.RequestBase  implements java.io.Serializable {
    private java.lang.String clientId;

    private java.lang.String clinetPassword;

    private java.lang.String clinetUsername;

    public ClientDetails() {
    }

    public ClientDetails(
           java.lang.String clientId,
           java.lang.String clinetPassword,
           java.lang.String clinetUsername) {
        this.clientId = clientId;
        this.clinetPassword = clinetPassword;
        this.clinetUsername = clinetUsername;
    }


    /**
     * Gets the clientId value for this ClientDetails.
     * 
     * @return clientId
     */
    public java.lang.String getClientId() {
        return clientId;
    }


    /**
     * Sets the clientId value for this ClientDetails.
     * 
     * @param clientId
     */
    public void setClientId(java.lang.String clientId) {
        this.clientId = clientId;
    }


    /**
     * Gets the clinetPassword value for this ClientDetails.
     * 
     * @return clinetPassword
     */
    public java.lang.String getClinetPassword() {
        return clinetPassword;
    }


    /**
     * Sets the clinetPassword value for this ClientDetails.
     * 
     * @param clinetPassword
     */
    public void setClinetPassword(java.lang.String clinetPassword) {
        this.clinetPassword = clinetPassword;
    }


    /**
     * Gets the clinetUsername value for this ClientDetails.
     * 
     * @return clinetUsername
     */
    public java.lang.String getClinetUsername() {
        return clinetUsername;
    }


    /**
     * Sets the clinetUsername value for this ClientDetails.
     * 
     * @param clinetUsername
     */
    public void setClinetUsername(java.lang.String clinetUsername) {
        this.clinetUsername = clinetUsername;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ClientDetails)) return false;
        ClientDetails other = (ClientDetails) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.clientId==null && other.getClientId()==null) || 
             (this.clientId!=null &&
              this.clientId.equals(other.getClientId()))) &&
            ((this.clinetPassword==null && other.getClinetPassword()==null) || 
             (this.clinetPassword!=null &&
              this.clinetPassword.equals(other.getClinetPassword()))) &&
            ((this.clinetUsername==null && other.getClinetUsername()==null) || 
             (this.clinetUsername!=null &&
              this.clinetUsername.equals(other.getClinetUsername())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = super.hashCode();
        if (getClientId() != null) {
            _hashCode += getClientId().hashCode();
        }
        if (getClinetPassword() != null) {
            _hashCode += getClinetPassword().hashCode();
        }
        if (getClinetUsername() != null) {
            _hashCode += getClinetUsername().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ClientDetails.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Onestop.Api.Services", "ClientDetails"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("clientId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Onestop.Api.Services", "ClientId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("clinetPassword");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Onestop.Api.Services", "ClinetPassword"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("clinetUsername");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Onestop.Api.Services", "ClinetUsername"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
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
