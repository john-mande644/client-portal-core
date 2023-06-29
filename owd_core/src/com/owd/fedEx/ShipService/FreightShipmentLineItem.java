/*
 * FreightShipmentLineItem.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;


/**
 * Description of an individual commodity or class of content in a
 * shipment.
 */
public class FreightShipmentLineItem  implements java.io.Serializable {
    private java.lang.String id;
    /** These values represent the industry-standard freight classes used
 * for FedEx Freight and FedEx National Freight shipment description.
 * (Note: The alphabetic prefixes are required to distinguish these values
 * from decimal numbers on some client platforms.) */
    private com.owd.fedEx.ShipService.FreightClassType freightClass;
    private java.lang.Boolean classProvidedByCustomer;
    private org.apache.axis.types.NonNegativeInteger handlingUnits;
    /** This enumeration rationalizes the former FedEx Express international
 * "admissibility package" types (based on ANSI X.12) and the FedEx Freight
 * packaging types. The values represented are those common to both carriers. */
    private com.owd.fedEx.ShipService.PhysicalPackagingType packaging;
    private org.apache.axis.types.NonNegativeInteger pieces;
    private java.lang.String nmfcCode;
    /** Indicates which kind of hazardous content is being reported. */
    private com.owd.fedEx.ShipService.HazardousCommodityOptionType hazardousMaterials;
    private java.lang.String purchaseOrderNumber;
    private java.lang.String description;
    /** The descriptive data for the heaviness of an object. */
    private com.owd.fedEx.ShipService.Weight weight;
    private com.owd.fedEx.ShipService.Dimensions dimensions;
    /** Three-dimensional volume/cubic measurement. */
    private com.owd.fedEx.ShipService.Volume volume;

    public FreightShipmentLineItem() {
    }


    /**
     * Gets the id value for this FreightShipmentLineItem.
     * 
     * @return id
     */
    public java.lang.String getId() {
        return id;
    }


    /**
     * Sets the id value for this FreightShipmentLineItem.
     * 
     * @param id
     */
    public void setId(java.lang.String id) {
        this.id = id;
    }


    /**
     * Gets the freightClass value for this FreightShipmentLineItem.
     * 
     * @return freightClass These values represent the industry-standard freight classes used
 * for FedEx Freight and FedEx National Freight shipment description.
 * (Note: The alphabetic prefixes are required to distinguish these values
 * from decimal numbers on some client platforms.)
     */
    public com.owd.fedEx.ShipService.FreightClassType getFreightClass() {
        return freightClass;
    }


    /**
     * Sets the freightClass value for this FreightShipmentLineItem.
     * 
     * @param freightClass These values represent the industry-standard freight classes used
 * for FedEx Freight and FedEx National Freight shipment description.
 * (Note: The alphabetic prefixes are required to distinguish these values
 * from decimal numbers on some client platforms.)
     */
    public void setFreightClass(com.owd.fedEx.ShipService.FreightClassType freightClass) {
        this.freightClass = freightClass;
    }


    /**
     * Gets the classProvidedByCustomer value for this FreightShipmentLineItem.
     * 
     * @return classProvidedByCustomer
     */
    public java.lang.Boolean getClassProvidedByCustomer() {
        return classProvidedByCustomer;
    }


    /**
     * Sets the classProvidedByCustomer value for this FreightShipmentLineItem.
     * 
     * @param classProvidedByCustomer
     */
    public void setClassProvidedByCustomer(java.lang.Boolean classProvidedByCustomer) {
        this.classProvidedByCustomer = classProvidedByCustomer;
    }


    /**
     * Gets the handlingUnits value for this FreightShipmentLineItem.
     * 
     * @return handlingUnits
     */
    public org.apache.axis.types.NonNegativeInteger getHandlingUnits() {
        return handlingUnits;
    }


    /**
     * Sets the handlingUnits value for this FreightShipmentLineItem.
     * 
     * @param handlingUnits
     */
    public void setHandlingUnits(org.apache.axis.types.NonNegativeInteger handlingUnits) {
        this.handlingUnits = handlingUnits;
    }


    /**
     * Gets the packaging value for this FreightShipmentLineItem.
     * 
     * @return packaging This enumeration rationalizes the former FedEx Express international
 * "admissibility package" types (based on ANSI X.12) and the FedEx Freight
 * packaging types. The values represented are those common to both carriers.
     */
    public com.owd.fedEx.ShipService.PhysicalPackagingType getPackaging() {
        return packaging;
    }


    /**
     * Sets the packaging value for this FreightShipmentLineItem.
     * 
     * @param packaging This enumeration rationalizes the former FedEx Express international
 * "admissibility package" types (based on ANSI X.12) and the FedEx Freight
 * packaging types. The values represented are those common to both carriers.
     */
    public void setPackaging(com.owd.fedEx.ShipService.PhysicalPackagingType packaging) {
        this.packaging = packaging;
    }


    /**
     * Gets the pieces value for this FreightShipmentLineItem.
     * 
     * @return pieces
     */
    public org.apache.axis.types.NonNegativeInteger getPieces() {
        return pieces;
    }


    /**
     * Sets the pieces value for this FreightShipmentLineItem.
     * 
     * @param pieces
     */
    public void setPieces(org.apache.axis.types.NonNegativeInteger pieces) {
        this.pieces = pieces;
    }


    /**
     * Gets the nmfcCode value for this FreightShipmentLineItem.
     * 
     * @return nmfcCode
     */
    public java.lang.String getNmfcCode() {
        return nmfcCode;
    }


    /**
     * Sets the nmfcCode value for this FreightShipmentLineItem.
     * 
     * @param nmfcCode
     */
    public void setNmfcCode(java.lang.String nmfcCode) {
        this.nmfcCode = nmfcCode;
    }


    /**
     * Gets the hazardousMaterials value for this FreightShipmentLineItem.
     * 
     * @return hazardousMaterials Indicates which kind of hazardous content is being reported.
     */
    public com.owd.fedEx.ShipService.HazardousCommodityOptionType getHazardousMaterials() {
        return hazardousMaterials;
    }


    /**
     * Sets the hazardousMaterials value for this FreightShipmentLineItem.
     * 
     * @param hazardousMaterials Indicates which kind of hazardous content is being reported.
     */
    public void setHazardousMaterials(com.owd.fedEx.ShipService.HazardousCommodityOptionType hazardousMaterials) {
        this.hazardousMaterials = hazardousMaterials;
    }


    /**
     * Gets the purchaseOrderNumber value for this FreightShipmentLineItem.
     * 
     * @return purchaseOrderNumber
     */
    public java.lang.String getPurchaseOrderNumber() {
        return purchaseOrderNumber;
    }


    /**
     * Sets the purchaseOrderNumber value for this FreightShipmentLineItem.
     * 
     * @param purchaseOrderNumber
     */
    public void setPurchaseOrderNumber(java.lang.String purchaseOrderNumber) {
        this.purchaseOrderNumber = purchaseOrderNumber;
    }


    /**
     * Gets the description value for this FreightShipmentLineItem.
     * 
     * @return description
     */
    public java.lang.String getDescription() {
        return description;
    }


    /**
     * Sets the description value for this FreightShipmentLineItem.
     * 
     * @param description
     */
    public void setDescription(java.lang.String description) {
        this.description = description;
    }


    /**
     * Gets the weight value for this FreightShipmentLineItem.
     * 
     * @return weight The descriptive data for the heaviness of an object.
     */
    public com.owd.fedEx.ShipService.Weight getWeight() {
        return weight;
    }


    /**
     * Sets the weight value for this FreightShipmentLineItem.
     * 
     * @param weight The descriptive data for the heaviness of an object.
     */
    public void setWeight(com.owd.fedEx.ShipService.Weight weight) {
        this.weight = weight;
    }


    /**
     * Gets the dimensions value for this FreightShipmentLineItem.
     * 
     * @return dimensions
     */
    public com.owd.fedEx.ShipService.Dimensions getDimensions() {
        return dimensions;
    }


    /**
     * Sets the dimensions value for this FreightShipmentLineItem.
     * 
     * @param dimensions
     */
    public void setDimensions(com.owd.fedEx.ShipService.Dimensions dimensions) {
        this.dimensions = dimensions;
    }


    /**
     * Gets the volume value for this FreightShipmentLineItem.
     * 
     * @return volume Three-dimensional volume/cubic measurement.
     */
    public com.owd.fedEx.ShipService.Volume getVolume() {
        return volume;
    }


    /**
     * Sets the volume value for this FreightShipmentLineItem.
     * 
     * @param volume Three-dimensional volume/cubic measurement.
     */
    public void setVolume(com.owd.fedEx.ShipService.Volume volume) {
        this.volume = volume;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof FreightShipmentLineItem)) return false;
        FreightShipmentLineItem other = (FreightShipmentLineItem) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.id==null && other.getId()==null) || 
             (this.id!=null &&
              this.id.equals(other.getId()))) &&
            ((this.freightClass==null && other.getFreightClass()==null) || 
             (this.freightClass!=null &&
              this.freightClass.equals(other.getFreightClass()))) &&
            ((this.classProvidedByCustomer==null && other.getClassProvidedByCustomer()==null) || 
             (this.classProvidedByCustomer!=null &&
              this.classProvidedByCustomer.equals(other.getClassProvidedByCustomer()))) &&
            ((this.handlingUnits==null && other.getHandlingUnits()==null) || 
             (this.handlingUnits!=null &&
              this.handlingUnits.equals(other.getHandlingUnits()))) &&
            ((this.packaging==null && other.getPackaging()==null) || 
             (this.packaging!=null &&
              this.packaging.equals(other.getPackaging()))) &&
            ((this.pieces==null && other.getPieces()==null) || 
             (this.pieces!=null &&
              this.pieces.equals(other.getPieces()))) &&
            ((this.nmfcCode==null && other.getNmfcCode()==null) || 
             (this.nmfcCode!=null &&
              this.nmfcCode.equals(other.getNmfcCode()))) &&
            ((this.hazardousMaterials==null && other.getHazardousMaterials()==null) || 
             (this.hazardousMaterials!=null &&
              this.hazardousMaterials.equals(other.getHazardousMaterials()))) &&
            ((this.purchaseOrderNumber==null && other.getPurchaseOrderNumber()==null) || 
             (this.purchaseOrderNumber!=null &&
              this.purchaseOrderNumber.equals(other.getPurchaseOrderNumber()))) &&
            ((this.description==null && other.getDescription()==null) || 
             (this.description!=null &&
              this.description.equals(other.getDescription()))) &&
            ((this.weight==null && other.getWeight()==null) || 
             (this.weight!=null &&
              this.weight.equals(other.getWeight()))) &&
            ((this.dimensions==null && other.getDimensions()==null) || 
             (this.dimensions!=null &&
              this.dimensions.equals(other.getDimensions()))) &&
            ((this.volume==null && other.getVolume()==null) || 
             (this.volume!=null &&
              this.volume.equals(other.getVolume())));
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
        if (getId() != null) {
            _hashCode += getId().hashCode();
        }
        if (getFreightClass() != null) {
            _hashCode += getFreightClass().hashCode();
        }
        if (getClassProvidedByCustomer() != null) {
            _hashCode += getClassProvidedByCustomer().hashCode();
        }
        if (getHandlingUnits() != null) {
            _hashCode += getHandlingUnits().hashCode();
        }
        if (getPackaging() != null) {
            _hashCode += getPackaging().hashCode();
        }
        if (getPieces() != null) {
            _hashCode += getPieces().hashCode();
        }
        if (getNmfcCode() != null) {
            _hashCode += getNmfcCode().hashCode();
        }
        if (getHazardousMaterials() != null) {
            _hashCode += getHazardousMaterials().hashCode();
        }
        if (getPurchaseOrderNumber() != null) {
            _hashCode += getPurchaseOrderNumber().hashCode();
        }
        if (getDescription() != null) {
            _hashCode += getDescription().hashCode();
        }
        if (getWeight() != null) {
            _hashCode += getWeight().hashCode();
        }
        if (getDimensions() != null) {
            _hashCode += getDimensions().hashCode();
        }
        if (getVolume() != null) {
            _hashCode += getVolume().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(FreightShipmentLineItem.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "FreightShipmentLineItem"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("freightClass");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "FreightClass"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "FreightClassType"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("classProvidedByCustomer");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ClassProvidedByCustomer"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "boolean"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("handlingUnits");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "HandlingUnits"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "nonNegativeInteger"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("packaging");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Packaging"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "PhysicalPackagingType"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pieces");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Pieces"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "nonNegativeInteger"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nmfcCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "NmfcCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("hazardousMaterials");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "HazardousMaterials"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "HazardousCommodityOptionType"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("purchaseOrderNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "PurchaseOrderNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("description");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Description"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("weight");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Weight"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Weight"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dimensions");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Dimensions"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Dimensions"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("volume");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Volume"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Volume"));
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
