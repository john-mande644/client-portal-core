/**
 * InventoryDetails.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.owd.onestop;

public class InventoryDetails  extends com.owd.onestop.RequestBase  implements java.io.Serializable {
    private com.owd.onestop.ClientDetails clientDetails;

    private java.lang.String inventoryDataXml;

    public InventoryDetails() {
    }

    public InventoryDetails(
           com.owd.onestop.ClientDetails clientDetails,
           java.lang.String inventoryDataXml) {
        this.clientDetails = clientDetails;
        this.inventoryDataXml = inventoryDataXml;
    }


    /**
     * Gets the clientDetails value for this InventoryDetails.
     * 
     * @return clientDetails
     */
    public com.owd.onestop.ClientDetails getClientDetails() {
        return clientDetails;
    }


    /**
     * Sets the clientDetails value for this InventoryDetails.
     * 
     * @param clientDetails
     */
    public void setClientDetails(com.owd.onestop.ClientDetails clientDetails) {
        this.clientDetails = clientDetails;
    }


    /**
     * Gets the inventoryDataXml value for this InventoryDetails.
     * 
     * @return inventoryDataXml
     */
    public java.lang.String getInventoryDataXml() {
        return inventoryDataXml;
    }


    /**
     * Sets the inventoryDataXml value for this InventoryDetails.
     * 
     * @param inventoryDataXml
     */
    public void setInventoryDataXml(java.lang.String inventoryDataXml) {
        this.inventoryDataXml = inventoryDataXml;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof InventoryDetails)) return false;
        InventoryDetails other = (InventoryDetails) obj;
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
            ((this.inventoryDataXml==null && other.getInventoryDataXml()==null) || 
             (this.inventoryDataXml!=null &&
              this.inventoryDataXml.equals(other.getInventoryDataXml())));
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
        if (getInventoryDataXml() != null) {
            _hashCode += getInventoryDataXml().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(InventoryDetails.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Onestop.Api.Services", "InventoryDetails"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("clientDetails");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Onestop.Api.Services", "ClientDetails"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Onestop.Api.Services", "ClientDetails"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("inventoryDataXml");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Onestop.Api.Services", "InventoryDataXml"));
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
