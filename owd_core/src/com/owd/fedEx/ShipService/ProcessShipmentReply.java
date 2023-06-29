/*
 * ProcessShipmentReply.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;

public class ProcessShipmentReply  implements java.io.Serializable {
    private com.owd.fedEx.ShipService.NotificationSeverityType highestSeverity;
    private com.owd.fedEx.ShipService.Notification[] notifications;
    private com.owd.fedEx.ShipService.TransactionDetail transactionDetail;
    /** Identifies the version/level of a service operation expected by
 * a caller (in each request) and performed by the callee (in each reply). */
    private com.owd.fedEx.ShipService.VersionId version;
    private java.lang.String jobId;
    private com.owd.fedEx.ShipService.CompletedShipmentDetail completedShipmentDetail;
    /** All package-level shipping documents (other than labels and barcodes).
 * For use in loads after January, 2008. */
    private com.owd.fedEx.ShipService.ShippingDocument[] errorLabels;

    public ProcessShipmentReply() {
    }


    /**
     * Gets the highestSeverity value for this ProcessShipmentReply.
     * 
     * @return highestSeverity
     */
    public com.owd.fedEx.ShipService.NotificationSeverityType getHighestSeverity() {
        return highestSeverity;
    }


    /**
     * Sets the highestSeverity value for this ProcessShipmentReply.
     * 
     * @param highestSeverity
     */
    public void setHighestSeverity(com.owd.fedEx.ShipService.NotificationSeverityType highestSeverity) {
        this.highestSeverity = highestSeverity;
    }


    /**
     * Gets the notifications value for this ProcessShipmentReply.
     * 
     * @return notifications
     */
    public com.owd.fedEx.ShipService.Notification[] getNotifications() {
        return notifications;
    }


    /**
     * Sets the notifications value for this ProcessShipmentReply.
     * 
     * @param notifications
     */
    public void setNotifications(com.owd.fedEx.ShipService.Notification[] notifications) {
        this.notifications = notifications;
    }

    public com.owd.fedEx.ShipService.Notification getNotifications(int i) {
        return notifications[i];
    }

    public void setNotifications(int i, com.owd.fedEx.ShipService.Notification value) {
        this.notifications[i] = value;
    }


    /**
     * Gets the transactionDetail value for this ProcessShipmentReply.
     * 
     * @return transactionDetail
     */
    public com.owd.fedEx.ShipService.TransactionDetail getTransactionDetail() {
        return transactionDetail;
    }


    /**
     * Sets the transactionDetail value for this ProcessShipmentReply.
     * 
     * @param transactionDetail
     */
    public void setTransactionDetail(com.owd.fedEx.ShipService.TransactionDetail transactionDetail) {
        this.transactionDetail = transactionDetail;
    }


    /**
     * Gets the version value for this ProcessShipmentReply.
     * 
     * @return version Identifies the version/level of a service operation expected by
 * a caller (in each request) and performed by the callee (in each reply).
     */
    public com.owd.fedEx.ShipService.VersionId getVersion() {
        return version;
    }


    /**
     * Sets the version value for this ProcessShipmentReply.
     * 
     * @param version Identifies the version/level of a service operation expected by
 * a caller (in each request) and performed by the callee (in each reply).
     */
    public void setVersion(com.owd.fedEx.ShipService.VersionId version) {
        this.version = version;
    }


    /**
     * Gets the jobId value for this ProcessShipmentReply.
     * 
     * @return jobId
     */
    public java.lang.String getJobId() {
        return jobId;
    }


    /**
     * Sets the jobId value for this ProcessShipmentReply.
     * 
     * @param jobId
     */
    public void setJobId(java.lang.String jobId) {
        this.jobId = jobId;
    }


    /**
     * Gets the completedShipmentDetail value for this ProcessShipmentReply.
     * 
     * @return completedShipmentDetail
     */
    public com.owd.fedEx.ShipService.CompletedShipmentDetail getCompletedShipmentDetail() {
        return completedShipmentDetail;
    }


    /**
     * Sets the completedShipmentDetail value for this ProcessShipmentReply.
     * 
     * @param completedShipmentDetail
     */
    public void setCompletedShipmentDetail(com.owd.fedEx.ShipService.CompletedShipmentDetail completedShipmentDetail) {
        this.completedShipmentDetail = completedShipmentDetail;
    }


    /**
     * Gets the errorLabels value for this ProcessShipmentReply.
     * 
     * @return errorLabels All package-level shipping documents (other than labels and barcodes).
 * For use in loads after January, 2008.
     */
    public com.owd.fedEx.ShipService.ShippingDocument[] getErrorLabels() {
        return errorLabels;
    }


    /**
     * Sets the errorLabels value for this ProcessShipmentReply.
     * 
     * @param errorLabels All package-level shipping documents (other than labels and barcodes).
 * For use in loads after January, 2008.
     */
    public void setErrorLabels(com.owd.fedEx.ShipService.ShippingDocument[] errorLabels) {
        this.errorLabels = errorLabels;
    }

    public com.owd.fedEx.ShipService.ShippingDocument getErrorLabels(int i) {
        return errorLabels[i];
    }

    public void setErrorLabels(int i, com.owd.fedEx.ShipService.ShippingDocument value) {
        this.errorLabels[i] = value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ProcessShipmentReply)) return false;
        ProcessShipmentReply other = (ProcessShipmentReply) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.highestSeverity==null && other.getHighestSeverity()==null) || 
             (this.highestSeverity!=null &&
              this.highestSeverity.equals(other.getHighestSeverity()))) &&
            ((this.notifications==null && other.getNotifications()==null) || 
             (this.notifications!=null &&
              java.util.Arrays.equals(this.notifications, other.getNotifications()))) &&
            ((this.transactionDetail==null && other.getTransactionDetail()==null) || 
             (this.transactionDetail!=null &&
              this.transactionDetail.equals(other.getTransactionDetail()))) &&
            ((this.version==null && other.getVersion()==null) || 
             (this.version!=null &&
              this.version.equals(other.getVersion()))) &&
            ((this.jobId==null && other.getJobId()==null) || 
             (this.jobId!=null &&
              this.jobId.equals(other.getJobId()))) &&
            ((this.completedShipmentDetail==null && other.getCompletedShipmentDetail()==null) || 
             (this.completedShipmentDetail!=null &&
              this.completedShipmentDetail.equals(other.getCompletedShipmentDetail()))) &&
            ((this.errorLabels==null && other.getErrorLabels()==null) || 
             (this.errorLabels!=null &&
              java.util.Arrays.equals(this.errorLabels, other.getErrorLabels())));
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
        if (getHighestSeverity() != null) {
            _hashCode += getHighestSeverity().hashCode();
        }
        if (getNotifications() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getNotifications());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getNotifications(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getTransactionDetail() != null) {
            _hashCode += getTransactionDetail().hashCode();
        }
        if (getVersion() != null) {
            _hashCode += getVersion().hashCode();
        }
        if (getJobId() != null) {
            _hashCode += getJobId().hashCode();
        }
        if (getCompletedShipmentDetail() != null) {
            _hashCode += getCompletedShipmentDetail().hashCode();
        }
        if (getErrorLabels() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getErrorLabels());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getErrorLabels(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ProcessShipmentReply.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ProcessShipmentReply"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("highestSeverity");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "HighestSeverity"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "NotificationSeverityType"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("notifications");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Notifications"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Notification"));
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
        elemField.setFieldName("jobId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "JobId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("completedShipmentDetail");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "CompletedShipmentDetail"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "CompletedShipmentDetail"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("errorLabels");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ErrorLabels"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ShippingDocument"));
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
