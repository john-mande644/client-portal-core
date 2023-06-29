/*
 * ValidatedHazardousContainer.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;


/**
 * Specifies the concept of a container used to package dangerous
 * goods commodities.
 */
public class ValidatedHazardousContainer  implements java.io.Serializable {
    private java.math.BigDecimal QValue;
    /** Documents the kinds and quantities of all hazardous commodities
 * in the current package. */
    private com.owd.fedEx.ShipService.ValidatedHazardousCommodityContent[] hazardousCommodities;

    public ValidatedHazardousContainer() {
    }


    /**
     * Gets the QValue value for this ValidatedHazardousContainer.
     * 
     * @return QValue
     */
    public java.math.BigDecimal getQValue() {
        return QValue;
    }


    /**
     * Sets the QValue value for this ValidatedHazardousContainer.
     * 
     * @param QValue
     */
    public void setQValue(java.math.BigDecimal QValue) {
        this.QValue = QValue;
    }


    /**
     * Gets the hazardousCommodities value for this ValidatedHazardousContainer.
     * 
     * @return hazardousCommodities Documents the kinds and quantities of all hazardous commodities
 * in the current package.
     */
    public com.owd.fedEx.ShipService.ValidatedHazardousCommodityContent[] getHazardousCommodities() {
        return hazardousCommodities;
    }


    /**
     * Sets the hazardousCommodities value for this ValidatedHazardousContainer.
     * 
     * @param hazardousCommodities Documents the kinds and quantities of all hazardous commodities
 * in the current package.
     */
    public void setHazardousCommodities(com.owd.fedEx.ShipService.ValidatedHazardousCommodityContent[] hazardousCommodities) {
        this.hazardousCommodities = hazardousCommodities;
    }

    public com.owd.fedEx.ShipService.ValidatedHazardousCommodityContent getHazardousCommodities(int i) {
        return hazardousCommodities[i];
    }

    public void setHazardousCommodities(int i, com.owd.fedEx.ShipService.ValidatedHazardousCommodityContent value) {
        this.hazardousCommodities[i] = value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ValidatedHazardousContainer)) return false;
        ValidatedHazardousContainer other = (ValidatedHazardousContainer) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.QValue==null && other.getQValue()==null) || 
             (this.QValue!=null &&
              this.QValue.equals(other.getQValue()))) &&
            ((this.hazardousCommodities==null && other.getHazardousCommodities()==null) || 
             (this.hazardousCommodities!=null &&
              java.util.Arrays.equals(this.hazardousCommodities, other.getHazardousCommodities())));
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
        if (getQValue() != null) {
            _hashCode += getQValue().hashCode();
        }
        if (getHazardousCommodities() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getHazardousCommodities());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getHazardousCommodities(), i);
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
        new org.apache.axis.description.TypeDesc(ValidatedHazardousContainer.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ValidatedHazardousContainer"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("QValue");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "QValue"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("hazardousCommodities");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "HazardousCommodities"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ValidatedHazardousCommodityContent"));
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
