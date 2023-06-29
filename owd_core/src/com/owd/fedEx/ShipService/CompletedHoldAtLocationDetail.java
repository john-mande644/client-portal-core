/*
 * CompletedHoldAtLocationDetail.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;

public class CompletedHoldAtLocationDetail  implements java.io.Serializable {
    private com.owd.fedEx.ShipService.ContactAndAddress holdingLocation;
    /** Identifies a kind of FedEx facility. */
    private com.owd.fedEx.ShipService.FedExLocationType holdingLocationType;
    private java.lang.String holdingLocationTypeForDisplay;

    public CompletedHoldAtLocationDetail() {
    }


    /**
     * Gets the holdingLocation value for this CompletedHoldAtLocationDetail.
     * 
     * @return holdingLocation
     */
    public com.owd.fedEx.ShipService.ContactAndAddress getHoldingLocation() {
        return holdingLocation;
    }


    /**
     * Sets the holdingLocation value for this CompletedHoldAtLocationDetail.
     * 
     * @param holdingLocation
     */
    public void setHoldingLocation(com.owd.fedEx.ShipService.ContactAndAddress holdingLocation) {
        this.holdingLocation = holdingLocation;
    }


    /**
     * Gets the holdingLocationType value for this CompletedHoldAtLocationDetail.
     * 
     * @return holdingLocationType Identifies a kind of FedEx facility.
     */
    public com.owd.fedEx.ShipService.FedExLocationType getHoldingLocationType() {
        return holdingLocationType;
    }


    /**
     * Sets the holdingLocationType value for this CompletedHoldAtLocationDetail.
     * 
     * @param holdingLocationType Identifies a kind of FedEx facility.
     */
    public void setHoldingLocationType(com.owd.fedEx.ShipService.FedExLocationType holdingLocationType) {
        this.holdingLocationType = holdingLocationType;
    }


    /**
     * Gets the holdingLocationTypeForDisplay value for this CompletedHoldAtLocationDetail.
     * 
     * @return holdingLocationTypeForDisplay
     */
    public java.lang.String getHoldingLocationTypeForDisplay() {
        return holdingLocationTypeForDisplay;
    }


    /**
     * Sets the holdingLocationTypeForDisplay value for this CompletedHoldAtLocationDetail.
     * 
     * @param holdingLocationTypeForDisplay
     */
    public void setHoldingLocationTypeForDisplay(java.lang.String holdingLocationTypeForDisplay) {
        this.holdingLocationTypeForDisplay = holdingLocationTypeForDisplay;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CompletedHoldAtLocationDetail)) return false;
        CompletedHoldAtLocationDetail other = (CompletedHoldAtLocationDetail) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.holdingLocation==null && other.getHoldingLocation()==null) || 
             (this.holdingLocation!=null &&
              this.holdingLocation.equals(other.getHoldingLocation()))) &&
            ((this.holdingLocationType==null && other.getHoldingLocationType()==null) || 
             (this.holdingLocationType!=null &&
              this.holdingLocationType.equals(other.getHoldingLocationType()))) &&
            ((this.holdingLocationTypeForDisplay==null && other.getHoldingLocationTypeForDisplay()==null) || 
             (this.holdingLocationTypeForDisplay!=null &&
              this.holdingLocationTypeForDisplay.equals(other.getHoldingLocationTypeForDisplay())));
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
        if (getHoldingLocation() != null) {
            _hashCode += getHoldingLocation().hashCode();
        }
        if (getHoldingLocationType() != null) {
            _hashCode += getHoldingLocationType().hashCode();
        }
        if (getHoldingLocationTypeForDisplay() != null) {
            _hashCode += getHoldingLocationTypeForDisplay().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CompletedHoldAtLocationDetail.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "CompletedHoldAtLocationDetail"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("holdingLocation");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "HoldingLocation"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ContactAndAddress"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("holdingLocationType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "HoldingLocationType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "FedExLocationType"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("holdingLocationTypeForDisplay");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "HoldingLocationTypeForDisplay"));
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
