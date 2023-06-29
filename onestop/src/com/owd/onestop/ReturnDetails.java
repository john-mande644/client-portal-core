/**
 * ReturnDetails.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.owd.onestop;

public class ReturnDetails  extends com.owd.onestop.RequestBase  implements java.io.Serializable {
    private com.owd.onestop.ClientDetails clientDetails;

    private java.lang.String returnDataXml;

    public ReturnDetails() {
    }

    public ReturnDetails(
           com.owd.onestop.ClientDetails clientDetails,
           java.lang.String returnDataXml) {
        this.clientDetails = clientDetails;
        this.returnDataXml = returnDataXml;
    }


    /**
     * Gets the clientDetails value for this ReturnDetails.
     * 
     * @return clientDetails
     */
    public com.owd.onestop.ClientDetails getClientDetails() {
        return clientDetails;
    }


    /**
     * Sets the clientDetails value for this ReturnDetails.
     * 
     * @param clientDetails
     */
    public void setClientDetails(com.owd.onestop.ClientDetails clientDetails) {
        this.clientDetails = clientDetails;
    }


    /**
     * Gets the returnDataXml value for this ReturnDetails.
     * 
     * @return returnDataXml
     */
    public java.lang.String getReturnDataXml() {
        return returnDataXml;
    }


    /**
     * Sets the returnDataXml value for this ReturnDetails.
     * 
     * @param returnDataXml
     */
    public void setReturnDataXml(java.lang.String returnDataXml) {
        this.returnDataXml = returnDataXml;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ReturnDetails)) return false;
        ReturnDetails other = (ReturnDetails) obj;
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
            ((this.returnDataXml==null && other.getReturnDataXml()==null) || 
             (this.returnDataXml!=null &&
              this.returnDataXml.equals(other.getReturnDataXml())));
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
        if (getReturnDataXml() != null) {
            _hashCode += getReturnDataXml().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ReturnDetails.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Onestop.Api.Services", "ReturnDetails"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("clientDetails");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Onestop.Api.Services", "ClientDetails"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Onestop.Api.Services", "ClientDetails"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("returnDataXml");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Onestop.Api.Services", "ReturnDataXml"));
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
