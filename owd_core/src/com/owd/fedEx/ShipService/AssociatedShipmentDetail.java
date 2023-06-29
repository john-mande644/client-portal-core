/*
 * AssociatedShipmentDetail.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;

public class AssociatedShipmentDetail  implements java.io.Serializable {
    private com.owd.fedEx.ShipService.AssociatedShipmentType type;
    private com.owd.fedEx.ShipService.Party sender;
    private com.owd.fedEx.ShipService.Party recipient;
    private com.owd.fedEx.ShipService.ServiceType serviceType;
    private com.owd.fedEx.ShipService.PackagingType packagingType;
    private com.owd.fedEx.ShipService.TrackingId trackingId;
    /** Specifies additional customer reference data about the associated
 * shipment. */
    private com.owd.fedEx.ShipService.CustomerReference[] customerReferences;
    private com.owd.fedEx.ShipService.ShipmentOperationalDetail shipmentOperationalDetail;
    /** Package-level data required for labeling and/or movement. */
    private com.owd.fedEx.ShipService.PackageOperationalDetail packageOperationalDetail;
    private com.owd.fedEx.ShipService.ShippingDocument label;

    public AssociatedShipmentDetail() {
    }


    /**
     * Gets the type value for this AssociatedShipmentDetail.
     * 
     * @return type
     */
    public com.owd.fedEx.ShipService.AssociatedShipmentType getType() {
        return type;
    }


    /**
     * Sets the type value for this AssociatedShipmentDetail.
     * 
     * @param type
     */
    public void setType(com.owd.fedEx.ShipService.AssociatedShipmentType type) {
        this.type = type;
    }


    /**
     * Gets the sender value for this AssociatedShipmentDetail.
     * 
     * @return sender
     */
    public com.owd.fedEx.ShipService.Party getSender() {
        return sender;
    }


    /**
     * Sets the sender value for this AssociatedShipmentDetail.
     * 
     * @param sender
     */
    public void setSender(com.owd.fedEx.ShipService.Party sender) {
        this.sender = sender;
    }


    /**
     * Gets the recipient value for this AssociatedShipmentDetail.
     * 
     * @return recipient
     */
    public com.owd.fedEx.ShipService.Party getRecipient() {
        return recipient;
    }


    /**
     * Sets the recipient value for this AssociatedShipmentDetail.
     * 
     * @param recipient
     */
    public void setRecipient(com.owd.fedEx.ShipService.Party recipient) {
        this.recipient = recipient;
    }


    /**
     * Gets the serviceType value for this AssociatedShipmentDetail.
     * 
     * @return serviceType
     */
    public com.owd.fedEx.ShipService.ServiceType getServiceType() {
        return serviceType;
    }


    /**
     * Sets the serviceType value for this AssociatedShipmentDetail.
     * 
     * @param serviceType
     */
    public void setServiceType(com.owd.fedEx.ShipService.ServiceType serviceType) {
        this.serviceType = serviceType;
    }


    /**
     * Gets the packagingType value for this AssociatedShipmentDetail.
     * 
     * @return packagingType
     */
    public com.owd.fedEx.ShipService.PackagingType getPackagingType() {
        return packagingType;
    }


    /**
     * Sets the packagingType value for this AssociatedShipmentDetail.
     * 
     * @param packagingType
     */
    public void setPackagingType(com.owd.fedEx.ShipService.PackagingType packagingType) {
        this.packagingType = packagingType;
    }


    /**
     * Gets the trackingId value for this AssociatedShipmentDetail.
     * 
     * @return trackingId
     */
    public com.owd.fedEx.ShipService.TrackingId getTrackingId() {
        return trackingId;
    }


    /**
     * Sets the trackingId value for this AssociatedShipmentDetail.
     * 
     * @param trackingId
     */
    public void setTrackingId(com.owd.fedEx.ShipService.TrackingId trackingId) {
        this.trackingId = trackingId;
    }


    /**
     * Gets the customerReferences value for this AssociatedShipmentDetail.
     * 
     * @return customerReferences Specifies additional customer reference data about the associated
 * shipment.
     */
    public com.owd.fedEx.ShipService.CustomerReference[] getCustomerReferences() {
        return customerReferences;
    }


    /**
     * Sets the customerReferences value for this AssociatedShipmentDetail.
     * 
     * @param customerReferences Specifies additional customer reference data about the associated
 * shipment.
     */
    public void setCustomerReferences(com.owd.fedEx.ShipService.CustomerReference[] customerReferences) {
        this.customerReferences = customerReferences;
    }

    public com.owd.fedEx.ShipService.CustomerReference getCustomerReferences(int i) {
        return customerReferences[i];
    }

    public void setCustomerReferences(int i, com.owd.fedEx.ShipService.CustomerReference value) {
        this.customerReferences[i] = value;
    }


    /**
     * Gets the shipmentOperationalDetail value for this AssociatedShipmentDetail.
     * 
     * @return shipmentOperationalDetail
     */
    public com.owd.fedEx.ShipService.ShipmentOperationalDetail getShipmentOperationalDetail() {
        return shipmentOperationalDetail;
    }


    /**
     * Sets the shipmentOperationalDetail value for this AssociatedShipmentDetail.
     * 
     * @param shipmentOperationalDetail
     */
    public void setShipmentOperationalDetail(com.owd.fedEx.ShipService.ShipmentOperationalDetail shipmentOperationalDetail) {
        this.shipmentOperationalDetail = shipmentOperationalDetail;
    }


    /**
     * Gets the packageOperationalDetail value for this AssociatedShipmentDetail.
     * 
     * @return packageOperationalDetail Package-level data required for labeling and/or movement.
     */
    public com.owd.fedEx.ShipService.PackageOperationalDetail getPackageOperationalDetail() {
        return packageOperationalDetail;
    }


    /**
     * Sets the packageOperationalDetail value for this AssociatedShipmentDetail.
     * 
     * @param packageOperationalDetail Package-level data required for labeling and/or movement.
     */
    public void setPackageOperationalDetail(com.owd.fedEx.ShipService.PackageOperationalDetail packageOperationalDetail) {
        this.packageOperationalDetail = packageOperationalDetail;
    }


    /**
     * Gets the label value for this AssociatedShipmentDetail.
     * 
     * @return label
     */
    public com.owd.fedEx.ShipService.ShippingDocument getLabel() {
        return label;
    }


    /**
     * Sets the label value for this AssociatedShipmentDetail.
     * 
     * @param label
     */
    public void setLabel(com.owd.fedEx.ShipService.ShippingDocument label) {
        this.label = label;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AssociatedShipmentDetail)) return false;
        AssociatedShipmentDetail other = (AssociatedShipmentDetail) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.type==null && other.getType()==null) || 
             (this.type!=null &&
              this.type.equals(other.getType()))) &&
            ((this.sender==null && other.getSender()==null) || 
             (this.sender!=null &&
              this.sender.equals(other.getSender()))) &&
            ((this.recipient==null && other.getRecipient()==null) || 
             (this.recipient!=null &&
              this.recipient.equals(other.getRecipient()))) &&
            ((this.serviceType==null && other.getServiceType()==null) || 
             (this.serviceType!=null &&
              this.serviceType.equals(other.getServiceType()))) &&
            ((this.packagingType==null && other.getPackagingType()==null) || 
             (this.packagingType!=null &&
              this.packagingType.equals(other.getPackagingType()))) &&
            ((this.trackingId==null && other.getTrackingId()==null) || 
             (this.trackingId!=null &&
              this.trackingId.equals(other.getTrackingId()))) &&
            ((this.customerReferences==null && other.getCustomerReferences()==null) || 
             (this.customerReferences!=null &&
              java.util.Arrays.equals(this.customerReferences, other.getCustomerReferences()))) &&
            ((this.shipmentOperationalDetail==null && other.getShipmentOperationalDetail()==null) || 
             (this.shipmentOperationalDetail!=null &&
              this.shipmentOperationalDetail.equals(other.getShipmentOperationalDetail()))) &&
            ((this.packageOperationalDetail==null && other.getPackageOperationalDetail()==null) || 
             (this.packageOperationalDetail!=null &&
              this.packageOperationalDetail.equals(other.getPackageOperationalDetail()))) &&
            ((this.label==null && other.getLabel()==null) || 
             (this.label!=null &&
              this.label.equals(other.getLabel())));
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
        if (getType() != null) {
            _hashCode += getType().hashCode();
        }
        if (getSender() != null) {
            _hashCode += getSender().hashCode();
        }
        if (getRecipient() != null) {
            _hashCode += getRecipient().hashCode();
        }
        if (getServiceType() != null) {
            _hashCode += getServiceType().hashCode();
        }
        if (getPackagingType() != null) {
            _hashCode += getPackagingType().hashCode();
        }
        if (getTrackingId() != null) {
            _hashCode += getTrackingId().hashCode();
        }
        if (getCustomerReferences() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getCustomerReferences());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getCustomerReferences(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getShipmentOperationalDetail() != null) {
            _hashCode += getShipmentOperationalDetail().hashCode();
        }
        if (getPackageOperationalDetail() != null) {
            _hashCode += getPackageOperationalDetail().hashCode();
        }
        if (getLabel() != null) {
            _hashCode += getLabel().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AssociatedShipmentDetail.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "AssociatedShipmentDetail"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("type");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Type"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "AssociatedShipmentType"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sender");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Sender"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Party"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("recipient");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Recipient"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Party"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("serviceType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ServiceType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ServiceType"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("packagingType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "PackagingType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "PackagingType"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("trackingId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "TrackingId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "TrackingId"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("customerReferences");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "CustomerReferences"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "CustomerReference"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("shipmentOperationalDetail");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ShipmentOperationalDetail"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ShipmentOperationalDetail"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("packageOperationalDetail");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "PackageOperationalDetail"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "PackageOperationalDetail"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("label");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Label"));
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
