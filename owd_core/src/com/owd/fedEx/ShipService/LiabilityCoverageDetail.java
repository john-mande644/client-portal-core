/*
 * LiabilityCoverageDetail.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;

public class LiabilityCoverageDetail  implements java.io.Serializable {
    private com.owd.fedEx.ShipService.LiabilityCoverageType coverageType;
    private com.owd.fedEx.ShipService.Money coverageAmount;

    public LiabilityCoverageDetail() {
    }


    /**
     * Gets the coverageType value for this LiabilityCoverageDetail.
     * 
     * @return coverageType
     */
    public com.owd.fedEx.ShipService.LiabilityCoverageType getCoverageType() {
        return coverageType;
    }


    /**
     * Sets the coverageType value for this LiabilityCoverageDetail.
     * 
     * @param coverageType
     */
    public void setCoverageType(com.owd.fedEx.ShipService.LiabilityCoverageType coverageType) {
        this.coverageType = coverageType;
    }


    /**
     * Gets the coverageAmount value for this LiabilityCoverageDetail.
     * 
     * @return coverageAmount
     */
    public com.owd.fedEx.ShipService.Money getCoverageAmount() {
        return coverageAmount;
    }


    /**
     * Sets the coverageAmount value for this LiabilityCoverageDetail.
     * 
     * @param coverageAmount
     */
    public void setCoverageAmount(com.owd.fedEx.ShipService.Money coverageAmount) {
        this.coverageAmount = coverageAmount;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof LiabilityCoverageDetail)) return false;
        LiabilityCoverageDetail other = (LiabilityCoverageDetail) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.coverageType==null && other.getCoverageType()==null) || 
             (this.coverageType!=null &&
              this.coverageType.equals(other.getCoverageType()))) &&
            ((this.coverageAmount==null && other.getCoverageAmount()==null) || 
             (this.coverageAmount!=null &&
              this.coverageAmount.equals(other.getCoverageAmount())));
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
        if (getCoverageType() != null) {
            _hashCode += getCoverageType().hashCode();
        }
        if (getCoverageAmount() != null) {
            _hashCode += getCoverageAmount().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(LiabilityCoverageDetail.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "LiabilityCoverageDetail"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("coverageType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "CoverageType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "LiabilityCoverageType"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("coverageAmount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "CoverageAmount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Money"));
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
