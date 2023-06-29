/**
 * MarkOrderAsRetrievedResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.owd.onestop;

public class MarkOrderAsRetrievedResponse  implements java.io.Serializable {
    private com.owd.onestop.OperationResponseStatus operationResponseStatus;

    public MarkOrderAsRetrievedResponse() {
    }

    public MarkOrderAsRetrievedResponse(
           com.owd.onestop.OperationResponseStatus operationResponseStatus) {
           this.operationResponseStatus = operationResponseStatus;
    }


    /**
     * Gets the operationResponseStatus value for this MarkOrderAsRetrievedResponse.
     * 
     * @return operationResponseStatus
     */
    public com.owd.onestop.OperationResponseStatus getOperationResponseStatus() {
        return operationResponseStatus;
    }


    /**
     * Sets the operationResponseStatus value for this MarkOrderAsRetrievedResponse.
     * 
     * @param operationResponseStatus
     */
    public void setOperationResponseStatus(com.owd.onestop.OperationResponseStatus operationResponseStatus) {
        this.operationResponseStatus = operationResponseStatus;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof MarkOrderAsRetrievedResponse)) return false;
        MarkOrderAsRetrievedResponse other = (MarkOrderAsRetrievedResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.operationResponseStatus==null && other.getOperationResponseStatus()==null) || 
             (this.operationResponseStatus!=null &&
              this.operationResponseStatus.equals(other.getOperationResponseStatus())));
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
        if (getOperationResponseStatus() != null) {
            _hashCode += getOperationResponseStatus().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(MarkOrderAsRetrievedResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Onestop.Api.Services", "MarkOrderAsRetrievedResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("operationResponseStatus");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Onestop.Api.Services", "OperationResponseStatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Onestop.Api.Services", "OperationResponseStatus"));
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
