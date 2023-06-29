/*
 * DeleteShipmentRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;

public class DeleteShipmentRequest  implements java.io.Serializable {
    /** Used in authentication of the sender's identity. */
    private com.owd.fedEx.ShipService.WebAuthenticationDetail webAuthenticationDetail;
    /** Descriptive data for the client submitting a transaction. */
    private com.owd.fedEx.ShipService.ClientDetail clientDetail;
    private com.owd.fedEx.ShipService.TransactionDetail transactionDetail;
    /** Identifies the version/level of a service operation expected by
 * a caller (in each request) and performed by the callee (in each reply). */
    private com.owd.fedEx.ShipService.VersionId version;
    private java.util.Calendar shipTimestamp;
    private com.owd.fedEx.ShipService.TrackingId trackingId;
    /** Specifies the type of deletion to be performed on a shipment. */
    private com.owd.fedEx.ShipService.DeletionControlType deletionControl;

    public DeleteShipmentRequest() {
    }


    /**
     * Gets the webAuthenticationDetail value for this DeleteShipmentRequest.
     * 
     * @return webAuthenticationDetail Used in authentication of the sender's identity.
     */
    public com.owd.fedEx.ShipService.WebAuthenticationDetail getWebAuthenticationDetail() {
        return webAuthenticationDetail;
    }


    /**
     * Sets the webAuthenticationDetail value for this DeleteShipmentRequest.
     * 
     * @param webAuthenticationDetail Used in authentication of the sender's identity.
     */
    public void setWebAuthenticationDetail(com.owd.fedEx.ShipService.WebAuthenticationDetail webAuthenticationDetail) {
        this.webAuthenticationDetail = webAuthenticationDetail;
    }


    /**
     * Gets the clientDetail value for this DeleteShipmentRequest.
     * 
     * @return clientDetail Descriptive data for the client submitting a transaction.
     */
    public com.owd.fedEx.ShipService.ClientDetail getClientDetail() {
        return clientDetail;
    }


    /**
     * Sets the clientDetail value for this DeleteShipmentRequest.
     * 
     * @param clientDetail Descriptive data for the client submitting a transaction.
     */
    public void setClientDetail(com.owd.fedEx.ShipService.ClientDetail clientDetail) {
        this.clientDetail = clientDetail;
    }


    /**
     * Gets the transactionDetail value for this DeleteShipmentRequest.
     * 
     * @return transactionDetail
     */
    public com.owd.fedEx.ShipService.TransactionDetail getTransactionDetail() {
        return transactionDetail;
    }


    /**
     * Sets the transactionDetail value for this DeleteShipmentRequest.
     * 
     * @param transactionDetail
     */
    public void setTransactionDetail(com.owd.fedEx.ShipService.TransactionDetail transactionDetail) {
        this.transactionDetail = transactionDetail;
    }


    /**
     * Gets the version value for this DeleteShipmentRequest.
     * 
     * @return version Identifies the version/level of a service operation expected by
 * a caller (in each request) and performed by the callee (in each reply).
     */
    public com.owd.fedEx.ShipService.VersionId getVersion() {
        return version;
    }


    /**
     * Sets the version value for this DeleteShipmentRequest.
     * 
     * @param version Identifies the version/level of a service operation expected by
 * a caller (in each request) and performed by the callee (in each reply).
     */
    public void setVersion(com.owd.fedEx.ShipService.VersionId version) {
        this.version = version;
    }


    /**
     * Gets the shipTimestamp value for this DeleteShipmentRequest.
     * 
     * @return shipTimestamp
     */
    public java.util.Calendar getShipTimestamp() {
        return shipTimestamp;
    }


    /**
     * Sets the shipTimestamp value for this DeleteShipmentRequest.
     * 
     * @param shipTimestamp
     */
    public void setShipTimestamp(java.util.Calendar shipTimestamp) {
        this.shipTimestamp = shipTimestamp;
    }


    /**
     * Gets the trackingId value for this DeleteShipmentRequest.
     * 
     * @return trackingId
     */
    public com.owd.fedEx.ShipService.TrackingId getTrackingId() {
        return trackingId;
    }


    /**
     * Sets the trackingId value for this DeleteShipmentRequest.
     * 
     * @param trackingId
     */
    public void setTrackingId(com.owd.fedEx.ShipService.TrackingId trackingId) {
        this.trackingId = trackingId;
    }


    /**
     * Gets the deletionControl value for this DeleteShipmentRequest.
     * 
     * @return deletionControl Specifies the type of deletion to be performed on a shipment.
     */
    public com.owd.fedEx.ShipService.DeletionControlType getDeletionControl() {
        return deletionControl;
    }


    /**
     * Sets the deletionControl value for this DeleteShipmentRequest.
     * 
     * @param deletionControl Specifies the type of deletion to be performed on a shipment.
     */
    public void setDeletionControl(com.owd.fedEx.ShipService.DeletionControlType deletionControl) {
        this.deletionControl = deletionControl;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DeleteShipmentRequest)) return false;
        DeleteShipmentRequest other = (DeleteShipmentRequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.webAuthenticationDetail==null && other.getWebAuthenticationDetail()==null) || 
             (this.webAuthenticationDetail!=null &&
              this.webAuthenticationDetail.equals(other.getWebAuthenticationDetail()))) &&
            ((this.clientDetail==null && other.getClientDetail()==null) || 
             (this.clientDetail!=null &&
              this.clientDetail.equals(other.getClientDetail()))) &&
            ((this.transactionDetail==null && other.getTransactionDetail()==null) || 
             (this.transactionDetail!=null &&
              this.transactionDetail.equals(other.getTransactionDetail()))) &&
            ((this.version==null && other.getVersion()==null) || 
             (this.version!=null &&
              this.version.equals(other.getVersion()))) &&
            ((this.shipTimestamp==null && other.getShipTimestamp()==null) || 
             (this.shipTimestamp!=null &&
              this.shipTimestamp.equals(other.getShipTimestamp()))) &&
            ((this.trackingId==null && other.getTrackingId()==null) || 
             (this.trackingId!=null &&
              this.trackingId.equals(other.getTrackingId()))) &&
            ((this.deletionControl==null && other.getDeletionControl()==null) || 
             (this.deletionControl!=null &&
              this.deletionControl.equals(other.getDeletionControl())));
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
        if (getWebAuthenticationDetail() != null) {
            _hashCode += getWebAuthenticationDetail().hashCode();
        }
        if (getClientDetail() != null) {
            _hashCode += getClientDetail().hashCode();
        }
        if (getTransactionDetail() != null) {
            _hashCode += getTransactionDetail().hashCode();
        }
        if (getVersion() != null) {
            _hashCode += getVersion().hashCode();
        }
        if (getShipTimestamp() != null) {
            _hashCode += getShipTimestamp().hashCode();
        }
        if (getTrackingId() != null) {
            _hashCode += getTrackingId().hashCode();
        }
        if (getDeletionControl() != null) {
            _hashCode += getDeletionControl().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DeleteShipmentRequest.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "DeleteShipmentRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("webAuthenticationDetail");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "WebAuthenticationDetail"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "WebAuthenticationDetail"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("clientDetail");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ClientDetail"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ClientDetail"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("transactionDetail");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "TransactionDetail"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "TransactionDetail"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("version");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Version"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "VersionId"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("shipTimestamp");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ShipTimestamp"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("trackingId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "TrackingId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "TrackingId"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("deletionControl");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "DeletionControl"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "DeletionControlType"));
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
