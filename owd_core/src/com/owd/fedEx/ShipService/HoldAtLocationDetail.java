/*
 * HoldAtLocationDetail.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;

public class HoldAtLocationDetail  implements java.io.Serializable {
    private java.lang.String phoneNumber;
    private com.owd.fedEx.ShipService.ContactAndAddress locationContactAndAddress;
    /** Identifies a kind of FedEx facility. */
    private com.owd.fedEx.ShipService.FedExLocationType locationType;
    private java.lang.String locationId;

    public HoldAtLocationDetail() {
    }


    /**
     * Gets the phoneNumber value for this HoldAtLocationDetail.
     * 
     * @return phoneNumber
     */
    public java.lang.String getPhoneNumber() {
        return phoneNumber;
    }


    /**
     * Sets the phoneNumber value for this HoldAtLocationDetail.
     * 
     * @param phoneNumber
     */
    public void setPhoneNumber(java.lang.String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    /**
     * Gets the locationContactAndAddress value for this HoldAtLocationDetail.
     * 
     * @return locationContactAndAddress
     */
    public com.owd.fedEx.ShipService.ContactAndAddress getLocationContactAndAddress() {
        return locationContactAndAddress;
    }


    /**
     * Sets the locationContactAndAddress value for this HoldAtLocationDetail.
     * 
     * @param locationContactAndAddress
     */
    public void setLocationContactAndAddress(com.owd.fedEx.ShipService.ContactAndAddress locationContactAndAddress) {
        this.locationContactAndAddress = locationContactAndAddress;
    }


    /**
     * Gets the locationType value for this HoldAtLocationDetail.
     * 
     * @return locationType Identifies a kind of FedEx facility.
     */
    public com.owd.fedEx.ShipService.FedExLocationType getLocationType() {
        return locationType;
    }


    /**
     * Sets the locationType value for this HoldAtLocationDetail.
     * 
     * @param locationType Identifies a kind of FedEx facility.
     */
    public void setLocationType(com.owd.fedEx.ShipService.FedExLocationType locationType) {
        this.locationType = locationType;
    }


    /**
     * Gets the locationId value for this HoldAtLocationDetail.
     * 
     * @return locationId
     */
    public java.lang.String getLocationId() {
        return locationId;
    }


    /**
     * Sets the locationId value for this HoldAtLocationDetail.
     * 
     * @param locationId
     */
    public void setLocationId(java.lang.String locationId) {
        this.locationId = locationId;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof HoldAtLocationDetail)) return false;
        HoldAtLocationDetail other = (HoldAtLocationDetail) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.phoneNumber==null && other.getPhoneNumber()==null) || 
             (this.phoneNumber!=null &&
              this.phoneNumber.equals(other.getPhoneNumber()))) &&
            ((this.locationContactAndAddress==null && other.getLocationContactAndAddress()==null) || 
             (this.locationContactAndAddress!=null &&
              this.locationContactAndAddress.equals(other.getLocationContactAndAddress()))) &&
            ((this.locationType==null && other.getLocationType()==null) || 
             (this.locationType!=null &&
              this.locationType.equals(other.getLocationType()))) &&
            ((this.locationId==null && other.getLocationId()==null) || 
             (this.locationId!=null &&
              this.locationId.equals(other.getLocationId())));
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
        if (getPhoneNumber() != null) {
            _hashCode += getPhoneNumber().hashCode();
        }
        if (getLocationContactAndAddress() != null) {
            _hashCode += getLocationContactAndAddress().hashCode();
        }
        if (getLocationType() != null) {
            _hashCode += getLocationType().hashCode();
        }
        if (getLocationId() != null) {
            _hashCode += getLocationId().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(HoldAtLocationDetail.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "HoldAtLocationDetail"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("phoneNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "PhoneNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("locationContactAndAddress");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "LocationContactAndAddress"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ContactAndAddress"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("locationType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "LocationType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "FedExLocationType"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("locationId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "LocationId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
