/*
 * NaftaCommodityDetail.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;

public class NaftaCommodityDetail  implements java.io.Serializable {
    /** See instructions for NAFTA Certificate of Origin for code definitions. */
    private com.owd.fedEx.ShipService.NaftaPreferenceCriterionCode preferenceCriterion;
    /** See instructions for NAFTA Certificate of Origin for code definitions. */
    private com.owd.fedEx.ShipService.NaftaProducerDeterminationCode producerDetermination;
    private java.lang.String producerId;
    private com.owd.fedEx.ShipService.NaftaNetCostMethodCode netCostMethod;
    private com.owd.fedEx.ShipService.DateRange netCostDateRange;

    public NaftaCommodityDetail() {
    }


    /**
     * Gets the preferenceCriterion value for this NaftaCommodityDetail.
     * 
     * @return preferenceCriterion See instructions for NAFTA Certificate of Origin for code definitions.
     */
    public com.owd.fedEx.ShipService.NaftaPreferenceCriterionCode getPreferenceCriterion() {
        return preferenceCriterion;
    }


    /**
     * Sets the preferenceCriterion value for this NaftaCommodityDetail.
     * 
     * @param preferenceCriterion See instructions for NAFTA Certificate of Origin for code definitions.
     */
    public void setPreferenceCriterion(com.owd.fedEx.ShipService.NaftaPreferenceCriterionCode preferenceCriterion) {
        this.preferenceCriterion = preferenceCriterion;
    }


    /**
     * Gets the producerDetermination value for this NaftaCommodityDetail.
     * 
     * @return producerDetermination See instructions for NAFTA Certificate of Origin for code definitions.
     */
    public com.owd.fedEx.ShipService.NaftaProducerDeterminationCode getProducerDetermination() {
        return producerDetermination;
    }


    /**
     * Sets the producerDetermination value for this NaftaCommodityDetail.
     * 
     * @param producerDetermination See instructions for NAFTA Certificate of Origin for code definitions.
     */
    public void setProducerDetermination(com.owd.fedEx.ShipService.NaftaProducerDeterminationCode producerDetermination) {
        this.producerDetermination = producerDetermination;
    }


    /**
     * Gets the producerId value for this NaftaCommodityDetail.
     * 
     * @return producerId
     */
    public java.lang.String getProducerId() {
        return producerId;
    }


    /**
     * Sets the producerId value for this NaftaCommodityDetail.
     * 
     * @param producerId
     */
    public void setProducerId(java.lang.String producerId) {
        this.producerId = producerId;
    }


    /**
     * Gets the netCostMethod value for this NaftaCommodityDetail.
     * 
     * @return netCostMethod
     */
    public com.owd.fedEx.ShipService.NaftaNetCostMethodCode getNetCostMethod() {
        return netCostMethod;
    }


    /**
     * Sets the netCostMethod value for this NaftaCommodityDetail.
     * 
     * @param netCostMethod
     */
    public void setNetCostMethod(com.owd.fedEx.ShipService.NaftaNetCostMethodCode netCostMethod) {
        this.netCostMethod = netCostMethod;
    }


    /**
     * Gets the netCostDateRange value for this NaftaCommodityDetail.
     * 
     * @return netCostDateRange
     */
    public com.owd.fedEx.ShipService.DateRange getNetCostDateRange() {
        return netCostDateRange;
    }


    /**
     * Sets the netCostDateRange value for this NaftaCommodityDetail.
     * 
     * @param netCostDateRange
     */
    public void setNetCostDateRange(com.owd.fedEx.ShipService.DateRange netCostDateRange) {
        this.netCostDateRange = netCostDateRange;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof NaftaCommodityDetail)) return false;
        NaftaCommodityDetail other = (NaftaCommodityDetail) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.preferenceCriterion==null && other.getPreferenceCriterion()==null) || 
             (this.preferenceCriterion!=null &&
              this.preferenceCriterion.equals(other.getPreferenceCriterion()))) &&
            ((this.producerDetermination==null && other.getProducerDetermination()==null) || 
             (this.producerDetermination!=null &&
              this.producerDetermination.equals(other.getProducerDetermination()))) &&
            ((this.producerId==null && other.getProducerId()==null) || 
             (this.producerId!=null &&
              this.producerId.equals(other.getProducerId()))) &&
            ((this.netCostMethod==null && other.getNetCostMethod()==null) || 
             (this.netCostMethod!=null &&
              this.netCostMethod.equals(other.getNetCostMethod()))) &&
            ((this.netCostDateRange==null && other.getNetCostDateRange()==null) || 
             (this.netCostDateRange!=null &&
              this.netCostDateRange.equals(other.getNetCostDateRange())));
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
        if (getPreferenceCriterion() != null) {
            _hashCode += getPreferenceCriterion().hashCode();
        }
        if (getProducerDetermination() != null) {
            _hashCode += getProducerDetermination().hashCode();
        }
        if (getProducerId() != null) {
            _hashCode += getProducerId().hashCode();
        }
        if (getNetCostMethod() != null) {
            _hashCode += getNetCostMethod().hashCode();
        }
        if (getNetCostDateRange() != null) {
            _hashCode += getNetCostDateRange().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(NaftaCommodityDetail.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "NaftaCommodityDetail"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("preferenceCriterion");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "PreferenceCriterion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "NaftaPreferenceCriterionCode"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("producerDetermination");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ProducerDetermination"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "NaftaProducerDeterminationCode"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("producerId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ProducerId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("netCostMethod");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "NetCostMethod"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "NaftaNetCostMethodCode"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("netCostDateRange");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "NetCostDateRange"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "DateRange"));
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
