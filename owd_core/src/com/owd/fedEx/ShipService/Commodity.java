/*
 * Commodity.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;

public class Commodity  implements java.io.Serializable {
    private java.lang.String name;
    private org.apache.axis.types.NonNegativeInteger numberOfPieces;
    private java.lang.String description;
    private com.owd.fedEx.ShipService.CommodityPurposeType purpose;
    private java.lang.String countryOfManufacture;
    private java.lang.String harmonizedCode;
    /** The descriptive data for the heaviness of an object. */
    private com.owd.fedEx.ShipService.Weight weight;
    private java.math.BigDecimal quantity;
    private java.lang.String quantityUnits;
    /** Contains only additional quantitative information other than weight
 * and quantity to calculate duties and taxes. */
    private com.owd.fedEx.ShipService.Measure[] additionalMeasures;
    private com.owd.fedEx.ShipService.Money unitPrice;
    private com.owd.fedEx.ShipService.Money customsValue;
    /** Defines additional characteristic of commodity used to calculate
 * duties and taxes */
    private com.owd.fedEx.ShipService.EdtExciseCondition[] exciseConditions;
    private java.lang.String exportLicenseNumber;
    private java.util.Date exportLicenseExpirationDate;
    private java.lang.String CIMarksAndNumbers;
    private java.lang.String partNumber;
    private com.owd.fedEx.ShipService.NaftaCommodityDetail naftaDetail;

    public Commodity() {
    }


    /**
     * Gets the name value for this Commodity.
     * 
     * @return name
     */
    public java.lang.String getName() {
        return name;
    }


    /**
     * Sets the name value for this Commodity.
     * 
     * @param name
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }


    /**
     * Gets the numberOfPieces value for this Commodity.
     * 
     * @return numberOfPieces
     */
    public org.apache.axis.types.NonNegativeInteger getNumberOfPieces() {
        return numberOfPieces;
    }


    /**
     * Sets the numberOfPieces value for this Commodity.
     * 
     * @param numberOfPieces
     */
    public void setNumberOfPieces(org.apache.axis.types.NonNegativeInteger numberOfPieces) {
        this.numberOfPieces = numberOfPieces;
    }


    /**
     * Gets the description value for this Commodity.
     * 
     * @return description
     */
    public java.lang.String getDescription() {
        return description;
    }


    /**
     * Sets the description value for this Commodity.
     * 
     * @param description
     */
    public void setDescription(java.lang.String description) {
        this.description = description;
    }


    /**
     * Gets the purpose value for this Commodity.
     * 
     * @return purpose
     */
    public com.owd.fedEx.ShipService.CommodityPurposeType getPurpose() {
        return purpose;
    }


    /**
     * Sets the purpose value for this Commodity.
     * 
     * @param purpose
     */
    public void setPurpose(com.owd.fedEx.ShipService.CommodityPurposeType purpose) {
        this.purpose = purpose;
    }


    /**
     * Gets the countryOfManufacture value for this Commodity.
     * 
     * @return countryOfManufacture
     */
    public java.lang.String getCountryOfManufacture() {
        return countryOfManufacture;
    }


    /**
     * Sets the countryOfManufacture value for this Commodity.
     * 
     * @param countryOfManufacture
     */
    public void setCountryOfManufacture(java.lang.String countryOfManufacture) {
        this.countryOfManufacture = countryOfManufacture;
    }


    /**
     * Gets the harmonizedCode value for this Commodity.
     * 
     * @return harmonizedCode
     */
    public java.lang.String getHarmonizedCode() {
        return harmonizedCode;
    }


    /**
     * Sets the harmonizedCode value for this Commodity.
     * 
     * @param harmonizedCode
     */
    public void setHarmonizedCode(java.lang.String harmonizedCode) {
        this.harmonizedCode = harmonizedCode;
    }


    /**
     * Gets the weight value for this Commodity.
     * 
     * @return weight The descriptive data for the heaviness of an object.
     */
    public com.owd.fedEx.ShipService.Weight getWeight() {
        return weight;
    }


    /**
     * Sets the weight value for this Commodity.
     * 
     * @param weight The descriptive data for the heaviness of an object.
     */
    public void setWeight(com.owd.fedEx.ShipService.Weight weight) {
        this.weight = weight;
    }


    /**
     * Gets the quantity value for this Commodity.
     * 
     * @return quantity
     */
    public java.math.BigDecimal getQuantity() {
        return quantity;
    }


    /**
     * Sets the quantity value for this Commodity.
     * 
     * @param quantity
     */
    public void setQuantity(java.math.BigDecimal quantity) {
        this.quantity = quantity;
    }


    /**
     * Gets the quantityUnits value for this Commodity.
     * 
     * @return quantityUnits
     */
    public java.lang.String getQuantityUnits() {
        return quantityUnits;
    }


    /**
     * Sets the quantityUnits value for this Commodity.
     * 
     * @param quantityUnits
     */
    public void setQuantityUnits(java.lang.String quantityUnits) {
        this.quantityUnits = quantityUnits;
    }


    /**
     * Gets the additionalMeasures value for this Commodity.
     * 
     * @return additionalMeasures Contains only additional quantitative information other than weight
 * and quantity to calculate duties and taxes.
     */
    public com.owd.fedEx.ShipService.Measure[] getAdditionalMeasures() {
        return additionalMeasures;
    }


    /**
     * Sets the additionalMeasures value for this Commodity.
     * 
     * @param additionalMeasures Contains only additional quantitative information other than weight
 * and quantity to calculate duties and taxes.
     */
    public void setAdditionalMeasures(com.owd.fedEx.ShipService.Measure[] additionalMeasures) {
        this.additionalMeasures = additionalMeasures;
    }

    public com.owd.fedEx.ShipService.Measure getAdditionalMeasures(int i) {
        return additionalMeasures[i];
    }

    public void setAdditionalMeasures(int i, com.owd.fedEx.ShipService.Measure value) {
        this.additionalMeasures[i] = value;
    }


    /**
     * Gets the unitPrice value for this Commodity.
     * 
     * @return unitPrice
     */
    public com.owd.fedEx.ShipService.Money getUnitPrice() {
        return unitPrice;
    }


    /**
     * Sets the unitPrice value for this Commodity.
     * 
     * @param unitPrice
     */
    public void setUnitPrice(com.owd.fedEx.ShipService.Money unitPrice) {
        this.unitPrice = unitPrice;
    }


    /**
     * Gets the customsValue value for this Commodity.
     * 
     * @return customsValue
     */
    public com.owd.fedEx.ShipService.Money getCustomsValue() {
        return customsValue;
    }


    /**
     * Sets the customsValue value for this Commodity.
     * 
     * @param customsValue
     */
    public void setCustomsValue(com.owd.fedEx.ShipService.Money customsValue) {
        this.customsValue = customsValue;
    }


    /**
     * Gets the exciseConditions value for this Commodity.
     * 
     * @return exciseConditions Defines additional characteristic of commodity used to calculate
 * duties and taxes
     */
    public com.owd.fedEx.ShipService.EdtExciseCondition[] getExciseConditions() {
        return exciseConditions;
    }


    /**
     * Sets the exciseConditions value for this Commodity.
     * 
     * @param exciseConditions Defines additional characteristic of commodity used to calculate
 * duties and taxes
     */
    public void setExciseConditions(com.owd.fedEx.ShipService.EdtExciseCondition[] exciseConditions) {
        this.exciseConditions = exciseConditions;
    }

    public com.owd.fedEx.ShipService.EdtExciseCondition getExciseConditions(int i) {
        return exciseConditions[i];
    }

    public void setExciseConditions(int i, com.owd.fedEx.ShipService.EdtExciseCondition value) {
        this.exciseConditions[i] = value;
    }


    /**
     * Gets the exportLicenseNumber value for this Commodity.
     * 
     * @return exportLicenseNumber
     */
    public java.lang.String getExportLicenseNumber() {
        return exportLicenseNumber;
    }


    /**
     * Sets the exportLicenseNumber value for this Commodity.
     * 
     * @param exportLicenseNumber
     */
    public void setExportLicenseNumber(java.lang.String exportLicenseNumber) {
        this.exportLicenseNumber = exportLicenseNumber;
    }


    /**
     * Gets the exportLicenseExpirationDate value for this Commodity.
     * 
     * @return exportLicenseExpirationDate
     */
    public java.util.Date getExportLicenseExpirationDate() {
        return exportLicenseExpirationDate;
    }


    /**
     * Sets the exportLicenseExpirationDate value for this Commodity.
     * 
     * @param exportLicenseExpirationDate
     */
    public void setExportLicenseExpirationDate(java.util.Date exportLicenseExpirationDate) {
        this.exportLicenseExpirationDate = exportLicenseExpirationDate;
    }


    /**
     * Gets the CIMarksAndNumbers value for this Commodity.
     * 
     * @return CIMarksAndNumbers
     */
    public java.lang.String getCIMarksAndNumbers() {
        return CIMarksAndNumbers;
    }


    /**
     * Sets the CIMarksAndNumbers value for this Commodity.
     * 
     * @param CIMarksAndNumbers
     */
    public void setCIMarksAndNumbers(java.lang.String CIMarksAndNumbers) {
        this.CIMarksAndNumbers = CIMarksAndNumbers;
    }


    /**
     * Gets the partNumber value for this Commodity.
     * 
     * @return partNumber
     */
    public java.lang.String getPartNumber() {
        return partNumber;
    }


    /**
     * Sets the partNumber value for this Commodity.
     * 
     * @param partNumber
     */
    public void setPartNumber(java.lang.String partNumber) {
        this.partNumber = partNumber;
    }


    /**
     * Gets the naftaDetail value for this Commodity.
     * 
     * @return naftaDetail
     */
    public com.owd.fedEx.ShipService.NaftaCommodityDetail getNaftaDetail() {
        return naftaDetail;
    }


    /**
     * Sets the naftaDetail value for this Commodity.
     * 
     * @param naftaDetail
     */
    public void setNaftaDetail(com.owd.fedEx.ShipService.NaftaCommodityDetail naftaDetail) {
        this.naftaDetail = naftaDetail;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Commodity)) return false;
        Commodity other = (Commodity) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.name==null && other.getName()==null) || 
             (this.name!=null &&
              this.name.equals(other.getName()))) &&
            ((this.numberOfPieces==null && other.getNumberOfPieces()==null) || 
             (this.numberOfPieces!=null &&
              this.numberOfPieces.equals(other.getNumberOfPieces()))) &&
            ((this.description==null && other.getDescription()==null) || 
             (this.description!=null &&
              this.description.equals(other.getDescription()))) &&
            ((this.purpose==null && other.getPurpose()==null) || 
             (this.purpose!=null &&
              this.purpose.equals(other.getPurpose()))) &&
            ((this.countryOfManufacture==null && other.getCountryOfManufacture()==null) || 
             (this.countryOfManufacture!=null &&
              this.countryOfManufacture.equals(other.getCountryOfManufacture()))) &&
            ((this.harmonizedCode==null && other.getHarmonizedCode()==null) || 
             (this.harmonizedCode!=null &&
              this.harmonizedCode.equals(other.getHarmonizedCode()))) &&
            ((this.weight==null && other.getWeight()==null) || 
             (this.weight!=null &&
              this.weight.equals(other.getWeight()))) &&
            ((this.quantity==null && other.getQuantity()==null) || 
             (this.quantity!=null &&
              this.quantity.equals(other.getQuantity()))) &&
            ((this.quantityUnits==null && other.getQuantityUnits()==null) || 
             (this.quantityUnits!=null &&
              this.quantityUnits.equals(other.getQuantityUnits()))) &&
            ((this.additionalMeasures==null && other.getAdditionalMeasures()==null) || 
             (this.additionalMeasures!=null &&
              java.util.Arrays.equals(this.additionalMeasures, other.getAdditionalMeasures()))) &&
            ((this.unitPrice==null && other.getUnitPrice()==null) || 
             (this.unitPrice!=null &&
              this.unitPrice.equals(other.getUnitPrice()))) &&
            ((this.customsValue==null && other.getCustomsValue()==null) || 
             (this.customsValue!=null &&
              this.customsValue.equals(other.getCustomsValue()))) &&
            ((this.exciseConditions==null && other.getExciseConditions()==null) || 
             (this.exciseConditions!=null &&
              java.util.Arrays.equals(this.exciseConditions, other.getExciseConditions()))) &&
            ((this.exportLicenseNumber==null && other.getExportLicenseNumber()==null) || 
             (this.exportLicenseNumber!=null &&
              this.exportLicenseNumber.equals(other.getExportLicenseNumber()))) &&
            ((this.exportLicenseExpirationDate==null && other.getExportLicenseExpirationDate()==null) || 
             (this.exportLicenseExpirationDate!=null &&
              this.exportLicenseExpirationDate.equals(other.getExportLicenseExpirationDate()))) &&
            ((this.CIMarksAndNumbers==null && other.getCIMarksAndNumbers()==null) || 
             (this.CIMarksAndNumbers!=null &&
              this.CIMarksAndNumbers.equals(other.getCIMarksAndNumbers()))) &&
            ((this.partNumber==null && other.getPartNumber()==null) || 
             (this.partNumber!=null &&
              this.partNumber.equals(other.getPartNumber()))) &&
            ((this.naftaDetail==null && other.getNaftaDetail()==null) || 
             (this.naftaDetail!=null &&
              this.naftaDetail.equals(other.getNaftaDetail())));
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
        if (getName() != null) {
            _hashCode += getName().hashCode();
        }
        if (getNumberOfPieces() != null) {
            _hashCode += getNumberOfPieces().hashCode();
        }
        if (getDescription() != null) {
            _hashCode += getDescription().hashCode();
        }
        if (getPurpose() != null) {
            _hashCode += getPurpose().hashCode();
        }
        if (getCountryOfManufacture() != null) {
            _hashCode += getCountryOfManufacture().hashCode();
        }
        if (getHarmonizedCode() != null) {
            _hashCode += getHarmonizedCode().hashCode();
        }
        if (getWeight() != null) {
            _hashCode += getWeight().hashCode();
        }
        if (getQuantity() != null) {
            _hashCode += getQuantity().hashCode();
        }
        if (getQuantityUnits() != null) {
            _hashCode += getQuantityUnits().hashCode();
        }
        if (getAdditionalMeasures() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getAdditionalMeasures());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getAdditionalMeasures(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getUnitPrice() != null) {
            _hashCode += getUnitPrice().hashCode();
        }
        if (getCustomsValue() != null) {
            _hashCode += getCustomsValue().hashCode();
        }
        if (getExciseConditions() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getExciseConditions());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getExciseConditions(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getExportLicenseNumber() != null) {
            _hashCode += getExportLicenseNumber().hashCode();
        }
        if (getExportLicenseExpirationDate() != null) {
            _hashCode += getExportLicenseExpirationDate().hashCode();
        }
        if (getCIMarksAndNumbers() != null) {
            _hashCode += getCIMarksAndNumbers().hashCode();
        }
        if (getPartNumber() != null) {
            _hashCode += getPartNumber().hashCode();
        }
        if (getNaftaDetail() != null) {
            _hashCode += getNaftaDetail().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Commodity.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Commodity"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("name");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Name"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numberOfPieces");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "NumberOfPieces"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "nonNegativeInteger"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("description");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Description"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("purpose");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Purpose"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "CommodityPurposeType"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("countryOfManufacture");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "CountryOfManufacture"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("harmonizedCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "HarmonizedCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("weight");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Weight"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Weight"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("quantity");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Quantity"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("quantityUnits");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "QuantityUnits"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("additionalMeasures");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "AdditionalMeasures"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Measure"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("unitPrice");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "UnitPrice"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Money"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("customsValue");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "CustomsValue"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Money"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("exciseConditions");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ExciseConditions"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "EdtExciseCondition"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("exportLicenseNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ExportLicenseNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("exportLicenseExpirationDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ExportLicenseExpirationDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "date"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("CIMarksAndNumbers");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "CIMarksAndNumbers"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("partNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "PartNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("naftaDetail");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "NaftaDetail"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "NaftaCommodityDetail"));
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
