/*
 * FreightBaseCharge.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;


/**
 * Individual charge which contributes to the total base charge for
 * the shipment.
 */
public class FreightBaseCharge  implements java.io.Serializable {
    /** These values represent the industry-standard freight classes used
 * for FedEx Freight and FedEx National Freight shipment description.
 * (Note: The alphabetic prefixes are required to distinguish these values
 * from decimal numbers on some client platforms.) */
    private com.owd.fedEx.ShipService.FreightClassType freightClass;
    /** These values represent the industry-standard freight classes used
 * for FedEx Freight and FedEx National Freight shipment description.
 * (Note: The alphabetic prefixes are required to distinguish these values
 * from decimal numbers on some client platforms.) */
    private com.owd.fedEx.ShipService.FreightClassType ratedAsClass;
    private java.lang.String nmfcCode;
    private java.lang.String description;
    /** The descriptive data for the heaviness of an object. */
    private com.owd.fedEx.ShipService.Weight weight;
    private com.owd.fedEx.ShipService.Money chargeRate;
    private com.owd.fedEx.ShipService.FreightChargeBasisType chargeBasis;
    private com.owd.fedEx.ShipService.Money extendedAmount;

    public FreightBaseCharge() {
    }


    /**
     * Gets the freightClass value for this FreightBaseCharge.
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
     * Sets the freightClass value for this FreightBaseCharge.
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
     * Gets the ratedAsClass value for this FreightBaseCharge.
     * 
     * @return ratedAsClass These values represent the industry-standard freight classes used
 * for FedEx Freight and FedEx National Freight shipment description.
 * (Note: The alphabetic prefixes are required to distinguish these values
 * from decimal numbers on some client platforms.)
     */
    public com.owd.fedEx.ShipService.FreightClassType getRatedAsClass() {
        return ratedAsClass;
    }


    /**
     * Sets the ratedAsClass value for this FreightBaseCharge.
     * 
     * @param ratedAsClass These values represent the industry-standard freight classes used
 * for FedEx Freight and FedEx National Freight shipment description.
 * (Note: The alphabetic prefixes are required to distinguish these values
 * from decimal numbers on some client platforms.)
     */
    public void setRatedAsClass(com.owd.fedEx.ShipService.FreightClassType ratedAsClass) {
        this.ratedAsClass = ratedAsClass;
    }


    /**
     * Gets the nmfcCode value for this FreightBaseCharge.
     * 
     * @return nmfcCode
     */
    public java.lang.String getNmfcCode() {
        return nmfcCode;
    }


    /**
     * Sets the nmfcCode value for this FreightBaseCharge.
     * 
     * @param nmfcCode
     */
    public void setNmfcCode(java.lang.String nmfcCode) {
        this.nmfcCode = nmfcCode;
    }


    /**
     * Gets the description value for this FreightBaseCharge.
     * 
     * @return description
     */
    public java.lang.String getDescription() {
        return description;
    }


    /**
     * Sets the description value for this FreightBaseCharge.
     * 
     * @param description
     */
    public void setDescription(java.lang.String description) {
        this.description = description;
    }


    /**
     * Gets the weight value for this FreightBaseCharge.
     * 
     * @return weight The descriptive data for the heaviness of an object.
     */
    public com.owd.fedEx.ShipService.Weight getWeight() {
        return weight;
    }


    /**
     * Sets the weight value for this FreightBaseCharge.
     * 
     * @param weight The descriptive data for the heaviness of an object.
     */
    public void setWeight(com.owd.fedEx.ShipService.Weight weight) {
        this.weight = weight;
    }


    /**
     * Gets the chargeRate value for this FreightBaseCharge.
     * 
     * @return chargeRate
     */
    public com.owd.fedEx.ShipService.Money getChargeRate() {
        return chargeRate;
    }


    /**
     * Sets the chargeRate value for this FreightBaseCharge.
     * 
     * @param chargeRate
     */
    public void setChargeRate(com.owd.fedEx.ShipService.Money chargeRate) {
        this.chargeRate = chargeRate;
    }


    /**
     * Gets the chargeBasis value for this FreightBaseCharge.
     * 
     * @return chargeBasis
     */
    public com.owd.fedEx.ShipService.FreightChargeBasisType getChargeBasis() {
        return chargeBasis;
    }


    /**
     * Sets the chargeBasis value for this FreightBaseCharge.
     * 
     * @param chargeBasis
     */
    public void setChargeBasis(com.owd.fedEx.ShipService.FreightChargeBasisType chargeBasis) {
        this.chargeBasis = chargeBasis;
    }


    /**
     * Gets the extendedAmount value for this FreightBaseCharge.
     * 
     * @return extendedAmount
     */
    public com.owd.fedEx.ShipService.Money getExtendedAmount() {
        return extendedAmount;
    }


    /**
     * Sets the extendedAmount value for this FreightBaseCharge.
     * 
     * @param extendedAmount
     */
    public void setExtendedAmount(com.owd.fedEx.ShipService.Money extendedAmount) {
        this.extendedAmount = extendedAmount;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof FreightBaseCharge)) return false;
        FreightBaseCharge other = (FreightBaseCharge) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.freightClass==null && other.getFreightClass()==null) || 
             (this.freightClass!=null &&
              this.freightClass.equals(other.getFreightClass()))) &&
            ((this.ratedAsClass==null && other.getRatedAsClass()==null) || 
             (this.ratedAsClass!=null &&
              this.ratedAsClass.equals(other.getRatedAsClass()))) &&
            ((this.nmfcCode==null && other.getNmfcCode()==null) || 
             (this.nmfcCode!=null &&
              this.nmfcCode.equals(other.getNmfcCode()))) &&
            ((this.description==null && other.getDescription()==null) || 
             (this.description!=null &&
              this.description.equals(other.getDescription()))) &&
            ((this.weight==null && other.getWeight()==null) || 
             (this.weight!=null &&
              this.weight.equals(other.getWeight()))) &&
            ((this.chargeRate==null && other.getChargeRate()==null) || 
             (this.chargeRate!=null &&
              this.chargeRate.equals(other.getChargeRate()))) &&
            ((this.chargeBasis==null && other.getChargeBasis()==null) || 
             (this.chargeBasis!=null &&
              this.chargeBasis.equals(other.getChargeBasis()))) &&
            ((this.extendedAmount==null && other.getExtendedAmount()==null) || 
             (this.extendedAmount!=null &&
              this.extendedAmount.equals(other.getExtendedAmount())));
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
        if (getFreightClass() != null) {
            _hashCode += getFreightClass().hashCode();
        }
        if (getRatedAsClass() != null) {
            _hashCode += getRatedAsClass().hashCode();
        }
        if (getNmfcCode() != null) {
            _hashCode += getNmfcCode().hashCode();
        }
        if (getDescription() != null) {
            _hashCode += getDescription().hashCode();
        }
        if (getWeight() != null) {
            _hashCode += getWeight().hashCode();
        }
        if (getChargeRate() != null) {
            _hashCode += getChargeRate().hashCode();
        }
        if (getChargeBasis() != null) {
            _hashCode += getChargeBasis().hashCode();
        }
        if (getExtendedAmount() != null) {
            _hashCode += getExtendedAmount().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(FreightBaseCharge.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "FreightBaseCharge"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("freightClass");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "FreightClass"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "FreightClassType"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ratedAsClass");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "RatedAsClass"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "FreightClassType"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nmfcCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "NmfcCode"));
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
        elemField.setFieldName("chargeRate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ChargeRate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Money"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("chargeBasis");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ChargeBasis"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "FreightChargeBasisType"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("extendedAmount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ExtendedAmount"));
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
