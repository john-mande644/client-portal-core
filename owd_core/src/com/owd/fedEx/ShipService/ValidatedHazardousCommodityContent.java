/*
 * ValidatedHazardousCommodityContent.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;


/**
 * Documents the kind and quantity of an individual hazardous commodity
 * in a package.
 */
public class ValidatedHazardousCommodityContent  implements java.io.Serializable {
    /** Identifies and describes an individual hazardous commodity. For
 * 201001 load, this is based on data from the FedEx Ground Hazardous
 * Materials Shipping Guide. */
    private com.owd.fedEx.ShipService.ValidatedHazardousCommodityDescription description;
    /** Identifies amount and units for quantity of hazardous commodities. */
    private com.owd.fedEx.ShipService.HazardousCommodityQuantityDetail quantity;
    private java.math.BigDecimal massPoints;
    /** Customer-provided specifications for handling individual commodities. */
    private com.owd.fedEx.ShipService.HazardousCommodityOptionDetail options;
    private com.owd.fedEx.ShipService.NetExplosiveDetail netExplosiveDetail;

    public ValidatedHazardousCommodityContent() {
    }


    /**
     * Gets the description value for this ValidatedHazardousCommodityContent.
     * 
     * @return description Identifies and describes an individual hazardous commodity. For
 * 201001 load, this is based on data from the FedEx Ground Hazardous
 * Materials Shipping Guide.
     */
    public com.owd.fedEx.ShipService.ValidatedHazardousCommodityDescription getDescription() {
        return description;
    }


    /**
     * Sets the description value for this ValidatedHazardousCommodityContent.
     * 
     * @param description Identifies and describes an individual hazardous commodity. For
 * 201001 load, this is based on data from the FedEx Ground Hazardous
 * Materials Shipping Guide.
     */
    public void setDescription(com.owd.fedEx.ShipService.ValidatedHazardousCommodityDescription description) {
        this.description = description;
    }


    /**
     * Gets the quantity value for this ValidatedHazardousCommodityContent.
     * 
     * @return quantity Identifies amount and units for quantity of hazardous commodities.
     */
    public com.owd.fedEx.ShipService.HazardousCommodityQuantityDetail getQuantity() {
        return quantity;
    }


    /**
     * Sets the quantity value for this ValidatedHazardousCommodityContent.
     * 
     * @param quantity Identifies amount and units for quantity of hazardous commodities.
     */
    public void setQuantity(com.owd.fedEx.ShipService.HazardousCommodityQuantityDetail quantity) {
        this.quantity = quantity;
    }


    /**
     * Gets the massPoints value for this ValidatedHazardousCommodityContent.
     * 
     * @return massPoints
     */
    public java.math.BigDecimal getMassPoints() {
        return massPoints;
    }


    /**
     * Sets the massPoints value for this ValidatedHazardousCommodityContent.
     * 
     * @param massPoints
     */
    public void setMassPoints(java.math.BigDecimal massPoints) {
        this.massPoints = massPoints;
    }


    /**
     * Gets the options value for this ValidatedHazardousCommodityContent.
     * 
     * @return options Customer-provided specifications for handling individual commodities.
     */
    public com.owd.fedEx.ShipService.HazardousCommodityOptionDetail getOptions() {
        return options;
    }


    /**
     * Sets the options value for this ValidatedHazardousCommodityContent.
     * 
     * @param options Customer-provided specifications for handling individual commodities.
     */
    public void setOptions(com.owd.fedEx.ShipService.HazardousCommodityOptionDetail options) {
        this.options = options;
    }


    /**
     * Gets the netExplosiveDetail value for this ValidatedHazardousCommodityContent.
     * 
     * @return netExplosiveDetail
     */
    public com.owd.fedEx.ShipService.NetExplosiveDetail getNetExplosiveDetail() {
        return netExplosiveDetail;
    }


    /**
     * Sets the netExplosiveDetail value for this ValidatedHazardousCommodityContent.
     * 
     * @param netExplosiveDetail
     */
    public void setNetExplosiveDetail(com.owd.fedEx.ShipService.NetExplosiveDetail netExplosiveDetail) {
        this.netExplosiveDetail = netExplosiveDetail;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ValidatedHazardousCommodityContent)) return false;
        ValidatedHazardousCommodityContent other = (ValidatedHazardousCommodityContent) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.description==null && other.getDescription()==null) || 
             (this.description!=null &&
              this.description.equals(other.getDescription()))) &&
            ((this.quantity==null && other.getQuantity()==null) || 
             (this.quantity!=null &&
              this.quantity.equals(other.getQuantity()))) &&
            ((this.massPoints==null && other.getMassPoints()==null) || 
             (this.massPoints!=null &&
              this.massPoints.equals(other.getMassPoints()))) &&
            ((this.options==null && other.getOptions()==null) || 
             (this.options!=null &&
              this.options.equals(other.getOptions()))) &&
            ((this.netExplosiveDetail==null && other.getNetExplosiveDetail()==null) || 
             (this.netExplosiveDetail!=null &&
              this.netExplosiveDetail.equals(other.getNetExplosiveDetail())));
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
        if (getDescription() != null) {
            _hashCode += getDescription().hashCode();
        }
        if (getQuantity() != null) {
            _hashCode += getQuantity().hashCode();
        }
        if (getMassPoints() != null) {
            _hashCode += getMassPoints().hashCode();
        }
        if (getOptions() != null) {
            _hashCode += getOptions().hashCode();
        }
        if (getNetExplosiveDetail() != null) {
            _hashCode += getNetExplosiveDetail().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ValidatedHazardousCommodityContent.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ValidatedHazardousCommodityContent"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("description");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Description"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ValidatedHazardousCommodityDescription"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("quantity");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Quantity"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "HazardousCommodityQuantityDetail"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("massPoints");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "MassPoints"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("options");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Options"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "HazardousCommodityOptionDetail"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("netExplosiveDetail");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "NetExplosiveDetail"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "NetExplosiveDetail"));
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
