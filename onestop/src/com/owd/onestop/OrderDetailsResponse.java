/**
 * OrderDetailsResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.owd.onestop;

public class OrderDetailsResponse  implements java.io.Serializable {
    private java.lang.String clientId;

    private java.lang.String clientNameShortHand;

    private com.owd.onestop.OperationResponseStatus operationResponseStatus;

    private java.lang.String orderDataXml;

    public OrderDetailsResponse() {
    }

    public OrderDetailsResponse(
           java.lang.String clientId,
           java.lang.String clientNameShortHand,
           com.owd.onestop.OperationResponseStatus operationResponseStatus,
           java.lang.String orderDataXml) {
           this.clientId = clientId;
           this.clientNameShortHand = clientNameShortHand;
           this.operationResponseStatus = operationResponseStatus;
           this.orderDataXml = orderDataXml;
    }


    /**
     * Gets the clientId value for this OrderDetailsResponse.
     * 
     * @return clientId
     */
    public java.lang.String getClientId() {
        return clientId;
    }


    /**
     * Sets the clientId value for this OrderDetailsResponse.
     * 
     * @param clientId
     */
    public void setClientId(java.lang.String clientId) {
        this.clientId = clientId;
    }


    /**
     * Gets the clientNameShortHand value for this OrderDetailsResponse.
     * 
     * @return clientNameShortHand
     */
    public java.lang.String getClientNameShortHand() {
        return clientNameShortHand;
    }


    /**
     * Sets the clientNameShortHand value for this OrderDetailsResponse.
     * 
     * @param clientNameShortHand
     */
    public void setClientNameShortHand(java.lang.String clientNameShortHand) {
        this.clientNameShortHand = clientNameShortHand;
    }


    /**
     * Gets the operationResponseStatus value for this OrderDetailsResponse.
     * 
     * @return operationResponseStatus
     */
    public com.owd.onestop.OperationResponseStatus getOperationResponseStatus() {
        return operationResponseStatus;
    }


    /**
     * Sets the operationResponseStatus value for this OrderDetailsResponse.
     * 
     * @param operationResponseStatus
     */
    public void setOperationResponseStatus(com.owd.onestop.OperationResponseStatus operationResponseStatus) {
        this.operationResponseStatus = operationResponseStatus;
    }


    /**
     * Gets the orderDataXml value for this OrderDetailsResponse.
     * 
     * @return orderDataXml
     */
    public java.lang.String getOrderDataXml() {
        return orderDataXml;
    }


    /**
     * Sets the orderDataXml value for this OrderDetailsResponse.
     * 
     * @param orderDataXml
     */
    public void setOrderDataXml(java.lang.String orderDataXml) {
        this.orderDataXml = orderDataXml;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof OrderDetailsResponse)) return false;
        OrderDetailsResponse other = (OrderDetailsResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.clientId==null && other.getClientId()==null) || 
             (this.clientId!=null &&
              this.clientId.equals(other.getClientId()))) &&
            ((this.clientNameShortHand==null && other.getClientNameShortHand()==null) || 
             (this.clientNameShortHand!=null &&
              this.clientNameShortHand.equals(other.getClientNameShortHand()))) &&
            ((this.operationResponseStatus==null && other.getOperationResponseStatus()==null) || 
             (this.operationResponseStatus!=null &&
              this.operationResponseStatus.equals(other.getOperationResponseStatus()))) &&
            ((this.orderDataXml==null && other.getOrderDataXml()==null) || 
             (this.orderDataXml!=null &&
              this.orderDataXml.equals(other.getOrderDataXml())));
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
        if (getClientId() != null) {
            _hashCode += getClientId().hashCode();
        }
        if (getClientNameShortHand() != null) {
            _hashCode += getClientNameShortHand().hashCode();
        }
        if (getOperationResponseStatus() != null) {
            _hashCode += getOperationResponseStatus().hashCode();
        }
        if (getOrderDataXml() != null) {
            _hashCode += getOrderDataXml().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(OrderDetailsResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Onestop.Api.Services", "OrderDetailsResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("clientId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Onestop.Api.Services", "ClientId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("clientNameShortHand");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Onestop.Api.Services", "ClientNameShortHand"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("operationResponseStatus");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Onestop.Api.Services", "OperationResponseStatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Onestop.Api.Services", "OperationResponseStatus"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("orderDataXml");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Onestop.Api.Services", "OrderDataXml"));
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
