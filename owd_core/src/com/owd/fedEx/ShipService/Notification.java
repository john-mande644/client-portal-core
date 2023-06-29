/*
 * Notification.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;


/**
 * The descriptive data regarding the result of the submitted transaction.
 */
public class Notification  implements java.io.Serializable {
    private com.owd.fedEx.ShipService.NotificationSeverityType severity;
    private java.lang.String source;
    private java.lang.String code;
    private java.lang.String message;
    private java.lang.String localizedMessage;
    /** A collection of name/value pairs that provide specific data to
 * help the client determine the nature of an error (or warning, etc.)
 * without having to parse the message string. */
    private com.owd.fedEx.ShipService.NotificationParameter[] messageParameters;

    public Notification() {
    }


    /**
     * Gets the severity value for this Notification.
     * 
     * @return severity
     */
    public com.owd.fedEx.ShipService.NotificationSeverityType getSeverity() {
        return severity;
    }


    /**
     * Sets the severity value for this Notification.
     * 
     * @param severity
     */
    public void setSeverity(com.owd.fedEx.ShipService.NotificationSeverityType severity) {
        this.severity = severity;
    }


    /**
     * Gets the source value for this Notification.
     * 
     * @return source
     */
    public java.lang.String getSource() {
        return source;
    }


    /**
     * Sets the source value for this Notification.
     * 
     * @param source
     */
    public void setSource(java.lang.String source) {
        this.source = source;
    }


    /**
     * Gets the code value for this Notification.
     * 
     * @return code
     */
    public java.lang.String getCode() {
        return code;
    }


    /**
     * Sets the code value for this Notification.
     * 
     * @param code
     */
    public void setCode(java.lang.String code) {
        this.code = code;
    }


    /**
     * Gets the message value for this Notification.
     * 
     * @return message
     */
    public java.lang.String getMessage() {
        return message;
    }


    /**
     * Sets the message value for this Notification.
     * 
     * @param message
     */
    public void setMessage(java.lang.String message) {
        this.message = message;
    }


    /**
     * Gets the localizedMessage value for this Notification.
     * 
     * @return localizedMessage
     */
    public java.lang.String getLocalizedMessage() {
        return localizedMessage;
    }


    /**
     * Sets the localizedMessage value for this Notification.
     * 
     * @param localizedMessage
     */
    public void setLocalizedMessage(java.lang.String localizedMessage) {
        this.localizedMessage = localizedMessage;
    }


    /**
     * Gets the messageParameters value for this Notification.
     * 
     * @return messageParameters A collection of name/value pairs that provide specific data to
 * help the client determine the nature of an error (or warning, etc.)
 * without having to parse the message string.
     */
    public com.owd.fedEx.ShipService.NotificationParameter[] getMessageParameters() {
        return messageParameters;
    }


    /**
     * Sets the messageParameters value for this Notification.
     * 
     * @param messageParameters A collection of name/value pairs that provide specific data to
 * help the client determine the nature of an error (or warning, etc.)
 * without having to parse the message string.
     */
    public void setMessageParameters(com.owd.fedEx.ShipService.NotificationParameter[] messageParameters) {
        this.messageParameters = messageParameters;
    }

    public com.owd.fedEx.ShipService.NotificationParameter getMessageParameters(int i) {
        return messageParameters[i];
    }

    public void setMessageParameters(int i, com.owd.fedEx.ShipService.NotificationParameter value) {
        this.messageParameters[i] = value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Notification)) return false;
        Notification other = (Notification) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.severity==null && other.getSeverity()==null) || 
             (this.severity!=null &&
              this.severity.equals(other.getSeverity()))) &&
            ((this.source==null && other.getSource()==null) || 
             (this.source!=null &&
              this.source.equals(other.getSource()))) &&
            ((this.code==null && other.getCode()==null) || 
             (this.code!=null &&
              this.code.equals(other.getCode()))) &&
            ((this.message==null && other.getMessage()==null) || 
             (this.message!=null &&
              this.message.equals(other.getMessage()))) &&
            ((this.localizedMessage==null && other.getLocalizedMessage()==null) || 
             (this.localizedMessage!=null &&
              this.localizedMessage.equals(other.getLocalizedMessage()))) &&
            ((this.messageParameters==null && other.getMessageParameters()==null) || 
             (this.messageParameters!=null &&
              java.util.Arrays.equals(this.messageParameters, other.getMessageParameters())));
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
        if (getSeverity() != null) {
            _hashCode += getSeverity().hashCode();
        }
        if (getSource() != null) {
            _hashCode += getSource().hashCode();
        }
        if (getCode() != null) {
            _hashCode += getCode().hashCode();
        }
        if (getMessage() != null) {
            _hashCode += getMessage().hashCode();
        }
        if (getLocalizedMessage() != null) {
            _hashCode += getLocalizedMessage().hashCode();
        }
        if (getMessageParameters() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getMessageParameters());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getMessageParameters(), i);
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
        new org.apache.axis.description.TypeDesc(Notification.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Notification"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("severity");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Severity"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "NotificationSeverityType"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("source");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Source"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("code");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Code"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("message");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Message"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("localizedMessage");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "LocalizedMessage"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("messageParameters");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "MessageParameters"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "NotificationParameter"));
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
