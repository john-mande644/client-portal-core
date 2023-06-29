/*
 * HazardousCommodityOptionDetail.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;


/**
 * Customer-provided specifications for handling individual commodities.
 */
public class HazardousCommodityOptionDetail  implements java.io.Serializable {
    /** Specifies how the commodity is to be labeled. */
    private com.owd.fedEx.ShipService.HazardousCommodityLabelTextOptionType labelTextOption;
    private java.lang.String customerSuppliedLabelText;

    public HazardousCommodityOptionDetail() {
    }


    /**
     * Gets the labelTextOption value for this HazardousCommodityOptionDetail.
     * 
     * @return labelTextOption Specifies how the commodity is to be labeled.
     */
    public com.owd.fedEx.ShipService.HazardousCommodityLabelTextOptionType getLabelTextOption() {
        return labelTextOption;
    }


    /**
     * Sets the labelTextOption value for this HazardousCommodityOptionDetail.
     * 
     * @param labelTextOption Specifies how the commodity is to be labeled.
     */
    public void setLabelTextOption(com.owd.fedEx.ShipService.HazardousCommodityLabelTextOptionType labelTextOption) {
        this.labelTextOption = labelTextOption;
    }


    /**
     * Gets the customerSuppliedLabelText value for this HazardousCommodityOptionDetail.
     * 
     * @return customerSuppliedLabelText
     */
    public java.lang.String getCustomerSuppliedLabelText() {
        return customerSuppliedLabelText;
    }


    /**
     * Sets the customerSuppliedLabelText value for this HazardousCommodityOptionDetail.
     * 
     * @param customerSuppliedLabelText
     */
    public void setCustomerSuppliedLabelText(java.lang.String customerSuppliedLabelText) {
        this.customerSuppliedLabelText = customerSuppliedLabelText;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof HazardousCommodityOptionDetail)) return false;
        HazardousCommodityOptionDetail other = (HazardousCommodityOptionDetail) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.labelTextOption==null && other.getLabelTextOption()==null) || 
             (this.labelTextOption!=null &&
              this.labelTextOption.equals(other.getLabelTextOption()))) &&
            ((this.customerSuppliedLabelText==null && other.getCustomerSuppliedLabelText()==null) || 
             (this.customerSuppliedLabelText!=null &&
              this.customerSuppliedLabelText.equals(other.getCustomerSuppliedLabelText())));
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
        if (getLabelTextOption() != null) {
            _hashCode += getLabelTextOption().hashCode();
        }
        if (getCustomerSuppliedLabelText() != null) {
            _hashCode += getCustomerSuppliedLabelText().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(HazardousCommodityOptionDetail.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "HazardousCommodityOptionDetail"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("labelTextOption");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "LabelTextOption"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "HazardousCommodityLabelTextOptionType"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("customerSuppliedLabelText");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "CustomerSuppliedLabelText"));
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
