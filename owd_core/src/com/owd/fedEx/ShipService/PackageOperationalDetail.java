/*
 * PackageOperationalDetail.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;


/**
 * Package-level data required for labeling and/or movement.
 */
public class PackageOperationalDetail  implements java.io.Serializable {
    private java.lang.String astraHandlingText;
    /** Human-readable content for use on a label. */
    private com.owd.fedEx.ShipService.OperationalInstruction[] operationalInstructions;
    /** Each instance of this data type represents the set of barcodes
 * (of all types) which are associated with a specific package. */
    private com.owd.fedEx.ShipService.PackageBarcodes barcodes;
    private java.lang.String groundServiceCode;

    public PackageOperationalDetail() {
    }


    /**
     * Gets the astraHandlingText value for this PackageOperationalDetail.
     * 
     * @return astraHandlingText
     */
    public java.lang.String getAstraHandlingText() {
        return astraHandlingText;
    }


    /**
     * Sets the astraHandlingText value for this PackageOperationalDetail.
     * 
     * @param astraHandlingText
     */
    public void setAstraHandlingText(java.lang.String astraHandlingText) {
        this.astraHandlingText = astraHandlingText;
    }


    /**
     * Gets the operationalInstructions value for this PackageOperationalDetail.
     * 
     * @return operationalInstructions Human-readable content for use on a label.
     */
    public com.owd.fedEx.ShipService.OperationalInstruction[] getOperationalInstructions() {
        return operationalInstructions;
    }


    /**
     * Sets the operationalInstructions value for this PackageOperationalDetail.
     * 
     * @param operationalInstructions Human-readable content for use on a label.
     */
    public void setOperationalInstructions(com.owd.fedEx.ShipService.OperationalInstruction[] operationalInstructions) {
        this.operationalInstructions = operationalInstructions;
    }

    public com.owd.fedEx.ShipService.OperationalInstruction getOperationalInstructions(int i) {
        return operationalInstructions[i];
    }

    public void setOperationalInstructions(int i, com.owd.fedEx.ShipService.OperationalInstruction value) {
        this.operationalInstructions[i] = value;
    }


    /**
     * Gets the barcodes value for this PackageOperationalDetail.
     * 
     * @return barcodes Each instance of this data type represents the set of barcodes
 * (of all types) which are associated with a specific package.
     */
    public com.owd.fedEx.ShipService.PackageBarcodes getBarcodes() {
        return barcodes;
    }


    /**
     * Sets the barcodes value for this PackageOperationalDetail.
     * 
     * @param barcodes Each instance of this data type represents the set of barcodes
 * (of all types) which are associated with a specific package.
     */
    public void setBarcodes(com.owd.fedEx.ShipService.PackageBarcodes barcodes) {
        this.barcodes = barcodes;
    }


    /**
     * Gets the groundServiceCode value for this PackageOperationalDetail.
     * 
     * @return groundServiceCode
     */
    public java.lang.String getGroundServiceCode() {
        return groundServiceCode;
    }


    /**
     * Sets the groundServiceCode value for this PackageOperationalDetail.
     * 
     * @param groundServiceCode
     */
    public void setGroundServiceCode(java.lang.String groundServiceCode) {
        this.groundServiceCode = groundServiceCode;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PackageOperationalDetail)) return false;
        PackageOperationalDetail other = (PackageOperationalDetail) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.astraHandlingText==null && other.getAstraHandlingText()==null) || 
             (this.astraHandlingText!=null &&
              this.astraHandlingText.equals(other.getAstraHandlingText()))) &&
            ((this.operationalInstructions==null && other.getOperationalInstructions()==null) || 
             (this.operationalInstructions!=null &&
              java.util.Arrays.equals(this.operationalInstructions, other.getOperationalInstructions()))) &&
            ((this.barcodes==null && other.getBarcodes()==null) || 
             (this.barcodes!=null &&
              this.barcodes.equals(other.getBarcodes()))) &&
            ((this.groundServiceCode==null && other.getGroundServiceCode()==null) || 
             (this.groundServiceCode!=null &&
              this.groundServiceCode.equals(other.getGroundServiceCode())));
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
        if (getAstraHandlingText() != null) {
            _hashCode += getAstraHandlingText().hashCode();
        }
        if (getOperationalInstructions() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getOperationalInstructions());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getOperationalInstructions(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getBarcodes() != null) {
            _hashCode += getBarcodes().hashCode();
        }
        if (getGroundServiceCode() != null) {
            _hashCode += getGroundServiceCode().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PackageOperationalDetail.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "PackageOperationalDetail"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("astraHandlingText");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "AstraHandlingText"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("operationalInstructions");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "OperationalInstructions"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "OperationalInstruction"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("barcodes");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Barcodes"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "PackageBarcodes"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("groundServiceCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "GroundServiceCode"));
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
