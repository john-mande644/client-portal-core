/*
 * PackageSpecialServicesRequested.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;


/**
 * These special services are available at the package level for some
 * or all service types. If the shipper is requesting a special service
 * which requires additional data, the package special service type must
 * be present in the specialServiceTypes collection, and the supporting
 * detail must be provided in the appropriate sub-object below.
 */
public class PackageSpecialServicesRequested  implements java.io.Serializable {
    /** The types of all special services requested for the enclosing shipment
 * or package. */
    private com.owd.fedEx.ShipService.PackageSpecialServiceType[] specialServiceTypes;
    /** Descriptive data required for a FedEx COD (Collect-On-Delivery)
 * shipment. */
    private com.owd.fedEx.ShipService.CodDetail codDetail;
    private com.owd.fedEx.ShipService.DangerousGoodsDetail dangerousGoodsDetail;
    /** Provides details about the batteries or cells that are contained
 * within this specific package. */
    private com.owd.fedEx.ShipService.BatteryClassificationDetail[] batteryDetails;
    /** The descriptive data for the heaviness of an object. */
    private com.owd.fedEx.ShipService.Weight dryIceWeight;
    private com.owd.fedEx.ShipService.SignatureOptionDetail signatureOptionDetail;
    private com.owd.fedEx.ShipService.PriorityAlertDetail priorityAlertDetail;
    /** Specifies details for a package containing alcohol */
    private com.owd.fedEx.ShipService.AlcoholDetail alcoholDetail;

    public PackageSpecialServicesRequested() {
    }


    /**
     * Gets the specialServiceTypes value for this PackageSpecialServicesRequested.
     * 
     * @return specialServiceTypes The types of all special services requested for the enclosing shipment
 * or package.
     */
    public com.owd.fedEx.ShipService.PackageSpecialServiceType[] getSpecialServiceTypes() {
        return specialServiceTypes;
    }


    /**
     * Sets the specialServiceTypes value for this PackageSpecialServicesRequested.
     * 
     * @param specialServiceTypes The types of all special services requested for the enclosing shipment
 * or package.
     */
    public void setSpecialServiceTypes(com.owd.fedEx.ShipService.PackageSpecialServiceType[] specialServiceTypes) {
        this.specialServiceTypes = specialServiceTypes;
    }

    public com.owd.fedEx.ShipService.PackageSpecialServiceType getSpecialServiceTypes(int i) {
        return specialServiceTypes[i];
    }

    public void setSpecialServiceTypes(int i, com.owd.fedEx.ShipService.PackageSpecialServiceType value) {
        this.specialServiceTypes[i] = value;
    }


    /**
     * Gets the codDetail value for this PackageSpecialServicesRequested.
     * 
     * @return codDetail Descriptive data required for a FedEx COD (Collect-On-Delivery)
 * shipment.
     */
    public com.owd.fedEx.ShipService.CodDetail getCodDetail() {
        return codDetail;
    }


    /**
     * Sets the codDetail value for this PackageSpecialServicesRequested.
     * 
     * @param codDetail Descriptive data required for a FedEx COD (Collect-On-Delivery)
 * shipment.
     */
    public void setCodDetail(com.owd.fedEx.ShipService.CodDetail codDetail) {
        this.codDetail = codDetail;
    }


    /**
     * Gets the dangerousGoodsDetail value for this PackageSpecialServicesRequested.
     * 
     * @return dangerousGoodsDetail
     */
    public com.owd.fedEx.ShipService.DangerousGoodsDetail getDangerousGoodsDetail() {
        return dangerousGoodsDetail;
    }


    /**
     * Sets the dangerousGoodsDetail value for this PackageSpecialServicesRequested.
     * 
     * @param dangerousGoodsDetail
     */
    public void setDangerousGoodsDetail(com.owd.fedEx.ShipService.DangerousGoodsDetail dangerousGoodsDetail) {
        this.dangerousGoodsDetail = dangerousGoodsDetail;
    }


    /**
     * Gets the batteryDetails value for this PackageSpecialServicesRequested.
     * 
     * @return batteryDetails Provides details about the batteries or cells that are contained
 * within this specific package.
     */
    public com.owd.fedEx.ShipService.BatteryClassificationDetail[] getBatteryDetails() {
        return batteryDetails;
    }


    /**
     * Sets the batteryDetails value for this PackageSpecialServicesRequested.
     * 
     * @param batteryDetails Provides details about the batteries or cells that are contained
 * within this specific package.
     */
    public void setBatteryDetails(com.owd.fedEx.ShipService.BatteryClassificationDetail[] batteryDetails) {
        this.batteryDetails = batteryDetails;
    }

    public com.owd.fedEx.ShipService.BatteryClassificationDetail getBatteryDetails(int i) {
        return batteryDetails[i];
    }

    public void setBatteryDetails(int i, com.owd.fedEx.ShipService.BatteryClassificationDetail value) {
        this.batteryDetails[i] = value;
    }


    /**
     * Gets the dryIceWeight value for this PackageSpecialServicesRequested.
     * 
     * @return dryIceWeight The descriptive data for the heaviness of an object.
     */
    public com.owd.fedEx.ShipService.Weight getDryIceWeight() {
        return dryIceWeight;
    }


    /**
     * Sets the dryIceWeight value for this PackageSpecialServicesRequested.
     * 
     * @param dryIceWeight The descriptive data for the heaviness of an object.
     */
    public void setDryIceWeight(com.owd.fedEx.ShipService.Weight dryIceWeight) {
        this.dryIceWeight = dryIceWeight;
    }


    /**
     * Gets the signatureOptionDetail value for this PackageSpecialServicesRequested.
     * 
     * @return signatureOptionDetail
     */
    public com.owd.fedEx.ShipService.SignatureOptionDetail getSignatureOptionDetail() {
        return signatureOptionDetail;
    }


    /**
     * Sets the signatureOptionDetail value for this PackageSpecialServicesRequested.
     * 
     * @param signatureOptionDetail
     */
    public void setSignatureOptionDetail(com.owd.fedEx.ShipService.SignatureOptionDetail signatureOptionDetail) {
        this.signatureOptionDetail = signatureOptionDetail;
    }


    /**
     * Gets the priorityAlertDetail value for this PackageSpecialServicesRequested.
     * 
     * @return priorityAlertDetail
     */
    public com.owd.fedEx.ShipService.PriorityAlertDetail getPriorityAlertDetail() {
        return priorityAlertDetail;
    }


    /**
     * Sets the priorityAlertDetail value for this PackageSpecialServicesRequested.
     * 
     * @param priorityAlertDetail
     */
    public void setPriorityAlertDetail(com.owd.fedEx.ShipService.PriorityAlertDetail priorityAlertDetail) {
        this.priorityAlertDetail = priorityAlertDetail;
    }


    /**
     * Gets the alcoholDetail value for this PackageSpecialServicesRequested.
     * 
     * @return alcoholDetail Specifies details for a package containing alcohol
     */
    public com.owd.fedEx.ShipService.AlcoholDetail getAlcoholDetail() {
        return alcoholDetail;
    }


    /**
     * Sets the alcoholDetail value for this PackageSpecialServicesRequested.
     * 
     * @param alcoholDetail Specifies details for a package containing alcohol
     */
    public void setAlcoholDetail(com.owd.fedEx.ShipService.AlcoholDetail alcoholDetail) {
        this.alcoholDetail = alcoholDetail;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PackageSpecialServicesRequested)) return false;
        PackageSpecialServicesRequested other = (PackageSpecialServicesRequested) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.specialServiceTypes==null && other.getSpecialServiceTypes()==null) || 
             (this.specialServiceTypes!=null &&
              java.util.Arrays.equals(this.specialServiceTypes, other.getSpecialServiceTypes()))) &&
            ((this.codDetail==null && other.getCodDetail()==null) || 
             (this.codDetail!=null &&
              this.codDetail.equals(other.getCodDetail()))) &&
            ((this.dangerousGoodsDetail==null && other.getDangerousGoodsDetail()==null) || 
             (this.dangerousGoodsDetail!=null &&
              this.dangerousGoodsDetail.equals(other.getDangerousGoodsDetail()))) &&
            ((this.batteryDetails==null && other.getBatteryDetails()==null) || 
             (this.batteryDetails!=null &&
              java.util.Arrays.equals(this.batteryDetails, other.getBatteryDetails()))) &&
            ((this.dryIceWeight==null && other.getDryIceWeight()==null) || 
             (this.dryIceWeight!=null &&
              this.dryIceWeight.equals(other.getDryIceWeight()))) &&
            ((this.signatureOptionDetail==null && other.getSignatureOptionDetail()==null) || 
             (this.signatureOptionDetail!=null &&
              this.signatureOptionDetail.equals(other.getSignatureOptionDetail()))) &&
            ((this.priorityAlertDetail==null && other.getPriorityAlertDetail()==null) || 
             (this.priorityAlertDetail!=null &&
              this.priorityAlertDetail.equals(other.getPriorityAlertDetail()))) &&
            ((this.alcoholDetail==null && other.getAlcoholDetail()==null) || 
             (this.alcoholDetail!=null &&
              this.alcoholDetail.equals(other.getAlcoholDetail())));
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
        if (getSpecialServiceTypes() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getSpecialServiceTypes());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getSpecialServiceTypes(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getCodDetail() != null) {
            _hashCode += getCodDetail().hashCode();
        }
        if (getDangerousGoodsDetail() != null) {
            _hashCode += getDangerousGoodsDetail().hashCode();
        }
        if (getBatteryDetails() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getBatteryDetails());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getBatteryDetails(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getDryIceWeight() != null) {
            _hashCode += getDryIceWeight().hashCode();
        }
        if (getSignatureOptionDetail() != null) {
            _hashCode += getSignatureOptionDetail().hashCode();
        }
        if (getPriorityAlertDetail() != null) {
            _hashCode += getPriorityAlertDetail().hashCode();
        }
        if (getAlcoholDetail() != null) {
            _hashCode += getAlcoholDetail().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PackageSpecialServicesRequested.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "PackageSpecialServicesRequested"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("specialServiceTypes");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "SpecialServiceTypes"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "PackageSpecialServiceType"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codDetail");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "CodDetail"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "CodDetail"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dangerousGoodsDetail");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "DangerousGoodsDetail"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "DangerousGoodsDetail"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("batteryDetails");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "BatteryDetails"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "BatteryClassificationDetail"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dryIceWeight");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "DryIceWeight"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Weight"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("signatureOptionDetail");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "SignatureOptionDetail"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "SignatureOptionDetail"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("priorityAlertDetail");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "PriorityAlertDetail"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "PriorityAlertDetail"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("alcoholDetail");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "AlcoholDetail"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "AlcoholDetail"));
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
