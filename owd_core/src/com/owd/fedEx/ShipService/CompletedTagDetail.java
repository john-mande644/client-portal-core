/*
 * CompletedTagDetail.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;


/**
 * Provides reply information specific to a tag request.
 */
public class CompletedTagDetail  implements java.io.Serializable {
    private java.lang.String confirmationNumber;
    private org.apache.axis.types.Duration accessTime;
    private org.apache.axis.types.Time cutoffTime;
    private java.lang.String location;
    private java.util.Calendar deliveryCommitment;
    private java.util.Date dispatchDate;

    public CompletedTagDetail() {
    }


    /**
     * Gets the confirmationNumber value for this CompletedTagDetail.
     * 
     * @return confirmationNumber
     */
    public java.lang.String getConfirmationNumber() {
        return confirmationNumber;
    }


    /**
     * Sets the confirmationNumber value for this CompletedTagDetail.
     * 
     * @param confirmationNumber
     */
    public void setConfirmationNumber(java.lang.String confirmationNumber) {
        this.confirmationNumber = confirmationNumber;
    }


    /**
     * Gets the accessTime value for this CompletedTagDetail.
     * 
     * @return accessTime
     */
    public org.apache.axis.types.Duration getAccessTime() {
        return accessTime;
    }


    /**
     * Sets the accessTime value for this CompletedTagDetail.
     * 
     * @param accessTime
     */
    public void setAccessTime(org.apache.axis.types.Duration accessTime) {
        this.accessTime = accessTime;
    }


    /**
     * Gets the cutoffTime value for this CompletedTagDetail.
     * 
     * @return cutoffTime
     */
    public org.apache.axis.types.Time getCutoffTime() {
        return cutoffTime;
    }


    /**
     * Sets the cutoffTime value for this CompletedTagDetail.
     * 
     * @param cutoffTime
     */
    public void setCutoffTime(org.apache.axis.types.Time cutoffTime) {
        this.cutoffTime = cutoffTime;
    }


    /**
     * Gets the location value for this CompletedTagDetail.
     * 
     * @return location
     */
    public java.lang.String getLocation() {
        return location;
    }


    /**
     * Sets the location value for this CompletedTagDetail.
     * 
     * @param location
     */
    public void setLocation(java.lang.String location) {
        this.location = location;
    }


    /**
     * Gets the deliveryCommitment value for this CompletedTagDetail.
     * 
     * @return deliveryCommitment
     */
    public java.util.Calendar getDeliveryCommitment() {
        return deliveryCommitment;
    }


    /**
     * Sets the deliveryCommitment value for this CompletedTagDetail.
     * 
     * @param deliveryCommitment
     */
    public void setDeliveryCommitment(java.util.Calendar deliveryCommitment) {
        this.deliveryCommitment = deliveryCommitment;
    }


    /**
     * Gets the dispatchDate value for this CompletedTagDetail.
     * 
     * @return dispatchDate
     */
    public java.util.Date getDispatchDate() {
        return dispatchDate;
    }


    /**
     * Sets the dispatchDate value for this CompletedTagDetail.
     * 
     * @param dispatchDate
     */
    public void setDispatchDate(java.util.Date dispatchDate) {
        this.dispatchDate = dispatchDate;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CompletedTagDetail)) return false;
        CompletedTagDetail other = (CompletedTagDetail) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.confirmationNumber==null && other.getConfirmationNumber()==null) || 
             (this.confirmationNumber!=null &&
              this.confirmationNumber.equals(other.getConfirmationNumber()))) &&
            ((this.accessTime==null && other.getAccessTime()==null) || 
             (this.accessTime!=null &&
              this.accessTime.equals(other.getAccessTime()))) &&
            ((this.cutoffTime==null && other.getCutoffTime()==null) || 
             (this.cutoffTime!=null &&
              this.cutoffTime.equals(other.getCutoffTime()))) &&
            ((this.location==null && other.getLocation()==null) || 
             (this.location!=null &&
              this.location.equals(other.getLocation()))) &&
            ((this.deliveryCommitment==null && other.getDeliveryCommitment()==null) || 
             (this.deliveryCommitment!=null &&
              this.deliveryCommitment.equals(other.getDeliveryCommitment()))) &&
            ((this.dispatchDate==null && other.getDispatchDate()==null) || 
             (this.dispatchDate!=null &&
              this.dispatchDate.equals(other.getDispatchDate())));
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
        if (getConfirmationNumber() != null) {
            _hashCode += getConfirmationNumber().hashCode();
        }
        if (getAccessTime() != null) {
            _hashCode += getAccessTime().hashCode();
        }
        if (getCutoffTime() != null) {
            _hashCode += getCutoffTime().hashCode();
        }
        if (getLocation() != null) {
            _hashCode += getLocation().hashCode();
        }
        if (getDeliveryCommitment() != null) {
            _hashCode += getDeliveryCommitment().hashCode();
        }
        if (getDispatchDate() != null) {
            _hashCode += getDispatchDate().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CompletedTagDetail.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "CompletedTagDetail"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("confirmationNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ConfirmationNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("accessTime");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "AccessTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "duration"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cutoffTime");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "CutoffTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "time"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("location");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Location"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("deliveryCommitment");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "DeliveryCommitment"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dispatchDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "DispatchDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "date"));
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
