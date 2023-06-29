/*
 * Address.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;


/**
 * Descriptive data for a physical location. May be used as an actual
 * physical address (place to which one could go), or as a container
 * of "address parts" which should be handled as a unit (such as a city-state-ZIP
 * combination within the US).
 */
public class Address  implements java.io.Serializable {
    /** Combination of number, street name, etc. At least one line is required
 * for a valid physical address; empty lines should not be included. */
    private java.lang.String[] streetLines;
    private java.lang.String city;
    private java.lang.String stateOrProvinceCode;
    private java.lang.String postalCode;
    private java.lang.String urbanizationCode;
    private java.lang.String countryCode;
    private java.lang.String countryName;
    private java.lang.Boolean residential;
    private java.lang.String geographicCoordinates;

    public Address() {
    }


    /**
     * Gets the streetLines value for this Address.
     * 
     * @return streetLines Combination of number, street name, etc. At least one line is required
 * for a valid physical address; empty lines should not be included.
     */
    public java.lang.String[] getStreetLines() {
        return streetLines;
    }


    /**
     * Sets the streetLines value for this Address.
     * 
     * @param streetLines Combination of number, street name, etc. At least one line is required
 * for a valid physical address; empty lines should not be included.
     */
    public void setStreetLines(java.lang.String[] streetLines) {
        this.streetLines = streetLines;
    }

    public java.lang.String getStreetLines(int i) {
        return streetLines[i];
    }

    public void setStreetLines(int i, java.lang.String value) {
        this.streetLines[i] = value;
    }


    /**
     * Gets the city value for this Address.
     * 
     * @return city
     */
    public java.lang.String getCity() {
        return city;
    }


    /**
     * Sets the city value for this Address.
     * 
     * @param city
     */
    public void setCity(java.lang.String city) {
        this.city = city;
    }


    /**
     * Gets the stateOrProvinceCode value for this Address.
     * 
     * @return stateOrProvinceCode
     */
    public java.lang.String getStateOrProvinceCode() {
        return stateOrProvinceCode;
    }


    /**
     * Sets the stateOrProvinceCode value for this Address.
     * 
     * @param stateOrProvinceCode
     */
    public void setStateOrProvinceCode(java.lang.String stateOrProvinceCode) {
        this.stateOrProvinceCode = stateOrProvinceCode;
    }


    /**
     * Gets the postalCode value for this Address.
     * 
     * @return postalCode
     */
    public java.lang.String getPostalCode() {
        return postalCode;
    }


    /**
     * Sets the postalCode value for this Address.
     * 
     * @param postalCode
     */
    public void setPostalCode(java.lang.String postalCode) {
        this.postalCode = postalCode;
    }


    /**
     * Gets the urbanizationCode value for this Address.
     * 
     * @return urbanizationCode
     */
    public java.lang.String getUrbanizationCode() {
        return urbanizationCode;
    }


    /**
     * Sets the urbanizationCode value for this Address.
     * 
     * @param urbanizationCode
     */
    public void setUrbanizationCode(java.lang.String urbanizationCode) {
        this.urbanizationCode = urbanizationCode;
    }


    /**
     * Gets the countryCode value for this Address.
     * 
     * @return countryCode
     */
    public java.lang.String getCountryCode() {
        return countryCode;
    }


    /**
     * Sets the countryCode value for this Address.
     * 
     * @param countryCode
     */
    public void setCountryCode(java.lang.String countryCode) {
        this.countryCode = countryCode;
    }


    /**
     * Gets the countryName value for this Address.
     * 
     * @return countryName
     */
    public java.lang.String getCountryName() {
        return countryName;
    }


    /**
     * Sets the countryName value for this Address.
     * 
     * @param countryName
     */
    public void setCountryName(java.lang.String countryName) {
        this.countryName = countryName;
    }


    /**
     * Gets the residential value for this Address.
     * 
     * @return residential
     */
    public java.lang.Boolean getResidential() {
        return residential;
    }


    /**
     * Sets the residential value for this Address.
     * 
     * @param residential
     */
    public void setResidential(java.lang.Boolean residential) {
        this.residential = residential;
    }


    /**
     * Gets the geographicCoordinates value for this Address.
     * 
     * @return geographicCoordinates
     */
    public java.lang.String getGeographicCoordinates() {
        return geographicCoordinates;
    }


    /**
     * Sets the geographicCoordinates value for this Address.
     * 
     * @param geographicCoordinates
     */
    public void setGeographicCoordinates(java.lang.String geographicCoordinates) {
        this.geographicCoordinates = geographicCoordinates;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Address)) return false;
        Address other = (Address) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.streetLines==null && other.getStreetLines()==null) || 
             (this.streetLines!=null &&
              java.util.Arrays.equals(this.streetLines, other.getStreetLines()))) &&
            ((this.city==null && other.getCity()==null) || 
             (this.city!=null &&
              this.city.equals(other.getCity()))) &&
            ((this.stateOrProvinceCode==null && other.getStateOrProvinceCode()==null) || 
             (this.stateOrProvinceCode!=null &&
              this.stateOrProvinceCode.equals(other.getStateOrProvinceCode()))) &&
            ((this.postalCode==null && other.getPostalCode()==null) || 
             (this.postalCode!=null &&
              this.postalCode.equals(other.getPostalCode()))) &&
            ((this.urbanizationCode==null && other.getUrbanizationCode()==null) || 
             (this.urbanizationCode!=null &&
              this.urbanizationCode.equals(other.getUrbanizationCode()))) &&
            ((this.countryCode==null && other.getCountryCode()==null) || 
             (this.countryCode!=null &&
              this.countryCode.equals(other.getCountryCode()))) &&
            ((this.countryName==null && other.getCountryName()==null) || 
             (this.countryName!=null &&
              this.countryName.equals(other.getCountryName()))) &&
            ((this.residential==null && other.getResidential()==null) || 
             (this.residential!=null &&
              this.residential.equals(other.getResidential()))) &&
            ((this.geographicCoordinates==null && other.getGeographicCoordinates()==null) || 
             (this.geographicCoordinates!=null &&
              this.geographicCoordinates.equals(other.getGeographicCoordinates())));
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
        if (getStreetLines() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getStreetLines());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getStreetLines(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getCity() != null) {
            _hashCode += getCity().hashCode();
        }
        if (getStateOrProvinceCode() != null) {
            _hashCode += getStateOrProvinceCode().hashCode();
        }
        if (getPostalCode() != null) {
            _hashCode += getPostalCode().hashCode();
        }
        if (getUrbanizationCode() != null) {
            _hashCode += getUrbanizationCode().hashCode();
        }
        if (getCountryCode() != null) {
            _hashCode += getCountryCode().hashCode();
        }
        if (getCountryName() != null) {
            _hashCode += getCountryName().hashCode();
        }
        if (getResidential() != null) {
            _hashCode += getResidential().hashCode();
        }
        if (getGeographicCoordinates() != null) {
            _hashCode += getGeographicCoordinates().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Address.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Address"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("streetLines");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "StreetLines"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("city");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "City"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("stateOrProvinceCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "StateOrProvinceCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("postalCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "PostalCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("urbanizationCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "UrbanizationCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("countryCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "CountryCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("countryName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "CountryName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("residential");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Residential"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "boolean"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("geographicCoordinates");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "GeographicCoordinates"));
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
