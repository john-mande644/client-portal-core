/**
 * UpdateOrderStatusRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.owd.onestop;

public class UpdateOrderStatusRequest  extends com.owd.onestop.RequestBase  implements java.io.Serializable {
    private com.owd.onestop.ClientDetails clientDetails;

    private int orderId;

    private com.owd.onestop.StatusCodes orderStatus;

    public UpdateOrderStatusRequest() {
    }

    public UpdateOrderStatusRequest(
           com.owd.onestop.ClientDetails clientDetails,
           int orderId,
           com.owd.onestop.StatusCodes orderStatus) {
        this.clientDetails = clientDetails;
        this.orderId = orderId;
        this.orderStatus = orderStatus;
    }


    /**
     * Gets the clientDetails value for this UpdateOrderStatusRequest.
     * 
     * @return clientDetails
     */
    public com.owd.onestop.ClientDetails getClientDetails() {
        return clientDetails;
    }


    /**
     * Sets the clientDetails value for this UpdateOrderStatusRequest.
     * 
     * @param clientDetails
     */
    public void setClientDetails(com.owd.onestop.ClientDetails clientDetails) {
        this.clientDetails = clientDetails;
    }


    /**
     * Gets the orderId value for this UpdateOrderStatusRequest.
     * 
     * @return orderId
     */
    public int getOrderId() {
        return orderId;
    }


    /**
     * Sets the orderId value for this UpdateOrderStatusRequest.
     * 
     * @param orderId
     */
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }


    /**
     * Gets the orderStatus value for this UpdateOrderStatusRequest.
     * 
     * @return orderStatus
     */
    public com.owd.onestop.StatusCodes getOrderStatus() {
        return orderStatus;
    }


    /**
     * Sets the orderStatus value for this UpdateOrderStatusRequest.
     * 
     * @param orderStatus
     */
    public void setOrderStatus(com.owd.onestop.StatusCodes orderStatus) {
        this.orderStatus = orderStatus;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof UpdateOrderStatusRequest)) return false;
        UpdateOrderStatusRequest other = (UpdateOrderStatusRequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.clientDetails==null && other.getClientDetails()==null) || 
             (this.clientDetails!=null &&
              this.clientDetails.equals(other.getClientDetails()))) &&
            this.orderId == other.getOrderId() &&
            ((this.orderStatus==null && other.getOrderStatus()==null) || 
             (this.orderStatus!=null &&
              this.orderStatus.equals(other.getOrderStatus())));
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
        if (getClientDetails() != null) {
            _hashCode += getClientDetails().hashCode();
        }
        _hashCode += getOrderId();
        if (getOrderStatus() != null) {
            _hashCode += getOrderStatus().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(UpdateOrderStatusRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Onestop.Api.Services", "UpdateOrderStatusRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("clientDetails");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Onestop.Api.Services", "ClientDetails"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Onestop.Api.Services", "ClientDetails"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("orderId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Onestop.Api.Services", "OrderId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("orderStatus");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Onestop.Api.Services", "OrderStatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/_onestop", "StatusCodes"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
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
