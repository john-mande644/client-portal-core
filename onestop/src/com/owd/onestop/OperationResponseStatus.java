/**
 * OperationResponseStatus.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.owd.onestop;

public class OperationResponseStatus  implements java.io.Serializable {
    private java.lang.String processStatusCode;

    private java.lang.String processStatusMessage;

    private java.lang.String responseStatusCode;

    private java.lang.String responseStatusMessage;

    public OperationResponseStatus() {
    }

    public OperationResponseStatus(
           java.lang.String processStatusCode,
           java.lang.String processStatusMessage,
           java.lang.String responseStatusCode,
           java.lang.String responseStatusMessage) {
           this.processStatusCode = processStatusCode;
           this.processStatusMessage = processStatusMessage;
           this.responseStatusCode = responseStatusCode;
           this.responseStatusMessage = responseStatusMessage;
    }


    /**
     * Gets the processStatusCode value for this OperationResponseStatus.
     * 
     * @return processStatusCode
     */
    public java.lang.String getProcessStatusCode() {
        return processStatusCode;
    }


    /**
     * Sets the processStatusCode value for this OperationResponseStatus.
     * 
     * @param processStatusCode
     */
    public void setProcessStatusCode(java.lang.String processStatusCode) {
        this.processStatusCode = processStatusCode;
    }


    /**
     * Gets the processStatusMessage value for this OperationResponseStatus.
     * 
     * @return processStatusMessage
     */
    public java.lang.String getProcessStatusMessage() {
        return processStatusMessage;
    }


    /**
     * Sets the processStatusMessage value for this OperationResponseStatus.
     * 
     * @param processStatusMessage
     */
    public void setProcessStatusMessage(java.lang.String processStatusMessage) {
        this.processStatusMessage = processStatusMessage;
    }


    /**
     * Gets the responseStatusCode value for this OperationResponseStatus.
     * 
     * @return responseStatusCode
     */
    public java.lang.String getResponseStatusCode() {
        return responseStatusCode;
    }


    /**
     * Sets the responseStatusCode value for this OperationResponseStatus.
     * 
     * @param responseStatusCode
     */
    public void setResponseStatusCode(java.lang.String responseStatusCode) {
        this.responseStatusCode = responseStatusCode;
    }


    /**
     * Gets the responseStatusMessage value for this OperationResponseStatus.
     * 
     * @return responseStatusMessage
     */
    public java.lang.String getResponseStatusMessage() {
        return responseStatusMessage;
    }


    /**
     * Sets the responseStatusMessage value for this OperationResponseStatus.
     * 
     * @param responseStatusMessage
     */
    public void setResponseStatusMessage(java.lang.String responseStatusMessage) {
        this.responseStatusMessage = responseStatusMessage;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof OperationResponseStatus)) return false;
        OperationResponseStatus other = (OperationResponseStatus) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.processStatusCode==null && other.getProcessStatusCode()==null) || 
             (this.processStatusCode!=null &&
              this.processStatusCode.equals(other.getProcessStatusCode()))) &&
            ((this.processStatusMessage==null && other.getProcessStatusMessage()==null) || 
             (this.processStatusMessage!=null &&
              this.processStatusMessage.equals(other.getProcessStatusMessage()))) &&
            ((this.responseStatusCode==null && other.getResponseStatusCode()==null) || 
             (this.responseStatusCode!=null &&
              this.responseStatusCode.equals(other.getResponseStatusCode()))) &&
            ((this.responseStatusMessage==null && other.getResponseStatusMessage()==null) || 
             (this.responseStatusMessage!=null &&
              this.responseStatusMessage.equals(other.getResponseStatusMessage())));
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
        if (getProcessStatusCode() != null) {
            _hashCode += getProcessStatusCode().hashCode();
        }
        if (getProcessStatusMessage() != null) {
            _hashCode += getProcessStatusMessage().hashCode();
        }
        if (getResponseStatusCode() != null) {
            _hashCode += getResponseStatusCode().hashCode();
        }
        if (getResponseStatusMessage() != null) {
            _hashCode += getResponseStatusMessage().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(OperationResponseStatus.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Onestop.Api.Services", "OperationResponseStatus"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("processStatusCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Onestop.Api.Services", "ProcessStatusCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("processStatusMessage");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Onestop.Api.Services", "ProcessStatusMessage"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("responseStatusCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Onestop.Api.Services", "ResponseStatusCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("responseStatusMessage");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Onestop.Api.Services", "ResponseStatusMessage"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
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
