/*
 * CustomLabelTextEntry.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;


/**
 * Constructed string, based on format and zero or more data fields,
 * printed in specified printer font (for thermal labels) or generic
 * font/size (for plain paper labels).
 */
public class CustomLabelTextEntry  implements java.io.Serializable {
    private com.owd.fedEx.ShipService.CustomLabelPosition position;
    private java.lang.String format;
    private java.lang.String[] dataFields;
    private java.lang.String thermalFontId;
    private java.lang.String fontName;
    private org.apache.axis.types.PositiveInteger fontSize;
    /** Describes the rotation of an item from its default orientation. */
    private com.owd.fedEx.ShipService.RotationType rotation;

    public CustomLabelTextEntry() {
    }


    /**
     * Gets the position value for this CustomLabelTextEntry.
     * 
     * @return position
     */
    public com.owd.fedEx.ShipService.CustomLabelPosition getPosition() {
        return position;
    }


    /**
     * Sets the position value for this CustomLabelTextEntry.
     * 
     * @param position
     */
    public void setPosition(com.owd.fedEx.ShipService.CustomLabelPosition position) {
        this.position = position;
    }


    /**
     * Gets the format value for this CustomLabelTextEntry.
     * 
     * @return format
     */
    public java.lang.String getFormat() {
        return format;
    }


    /**
     * Sets the format value for this CustomLabelTextEntry.
     * 
     * @param format
     */
    public void setFormat(java.lang.String format) {
        this.format = format;
    }


    /**
     * Gets the dataFields value for this CustomLabelTextEntry.
     * 
     * @return dataFields
     */
    public java.lang.String[] getDataFields() {
        return dataFields;
    }


    /**
     * Sets the dataFields value for this CustomLabelTextEntry.
     * 
     * @param dataFields
     */
    public void setDataFields(java.lang.String[] dataFields) {
        this.dataFields = dataFields;
    }

    public java.lang.String getDataFields(int i) {
        return dataFields[i];
    }

    public void setDataFields(int i, java.lang.String value) {
        this.dataFields[i] = value;
    }


    /**
     * Gets the thermalFontId value for this CustomLabelTextEntry.
     * 
     * @return thermalFontId
     */
    public java.lang.String getThermalFontId() {
        return thermalFontId;
    }


    /**
     * Sets the thermalFontId value for this CustomLabelTextEntry.
     * 
     * @param thermalFontId
     */
    public void setThermalFontId(java.lang.String thermalFontId) {
        this.thermalFontId = thermalFontId;
    }


    /**
     * Gets the fontName value for this CustomLabelTextEntry.
     * 
     * @return fontName
     */
    public java.lang.String getFontName() {
        return fontName;
    }


    /**
     * Sets the fontName value for this CustomLabelTextEntry.
     * 
     * @param fontName
     */
    public void setFontName(java.lang.String fontName) {
        this.fontName = fontName;
    }


    /**
     * Gets the fontSize value for this CustomLabelTextEntry.
     * 
     * @return fontSize
     */
    public org.apache.axis.types.PositiveInteger getFontSize() {
        return fontSize;
    }


    /**
     * Sets the fontSize value for this CustomLabelTextEntry.
     * 
     * @param fontSize
     */
    public void setFontSize(org.apache.axis.types.PositiveInteger fontSize) {
        this.fontSize = fontSize;
    }


    /**
     * Gets the rotation value for this CustomLabelTextEntry.
     * 
     * @return rotation Describes the rotation of an item from its default orientation.
     */
    public com.owd.fedEx.ShipService.RotationType getRotation() {
        return rotation;
    }


    /**
     * Sets the rotation value for this CustomLabelTextEntry.
     * 
     * @param rotation Describes the rotation of an item from its default orientation.
     */
    public void setRotation(com.owd.fedEx.ShipService.RotationType rotation) {
        this.rotation = rotation;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CustomLabelTextEntry)) return false;
        CustomLabelTextEntry other = (CustomLabelTextEntry) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.position==null && other.getPosition()==null) || 
             (this.position!=null &&
              this.position.equals(other.getPosition()))) &&
            ((this.format==null && other.getFormat()==null) || 
             (this.format!=null &&
              this.format.equals(other.getFormat()))) &&
            ((this.dataFields==null && other.getDataFields()==null) || 
             (this.dataFields!=null &&
              java.util.Arrays.equals(this.dataFields, other.getDataFields()))) &&
            ((this.thermalFontId==null && other.getThermalFontId()==null) || 
             (this.thermalFontId!=null &&
              this.thermalFontId.equals(other.getThermalFontId()))) &&
            ((this.fontName==null && other.getFontName()==null) || 
             (this.fontName!=null &&
              this.fontName.equals(other.getFontName()))) &&
            ((this.fontSize==null && other.getFontSize()==null) || 
             (this.fontSize!=null &&
              this.fontSize.equals(other.getFontSize()))) &&
            ((this.rotation==null && other.getRotation()==null) || 
             (this.rotation!=null &&
              this.rotation.equals(other.getRotation())));
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
        if (getPosition() != null) {
            _hashCode += getPosition().hashCode();
        }
        if (getFormat() != null) {
            _hashCode += getFormat().hashCode();
        }
        if (getDataFields() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getDataFields());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getDataFields(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getThermalFontId() != null) {
            _hashCode += getThermalFontId().hashCode();
        }
        if (getFontName() != null) {
            _hashCode += getFontName().hashCode();
        }
        if (getFontSize() != null) {
            _hashCode += getFontSize().hashCode();
        }
        if (getRotation() != null) {
            _hashCode += getRotation().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CustomLabelTextEntry.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "CustomLabelTextEntry"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("position");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Position"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "CustomLabelPosition"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("format");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Format"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataFields");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "DataFields"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("thermalFontId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ThermalFontId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fontName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "FontName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fontSize");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "FontSize"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "positiveInteger"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rotation");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Rotation"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "RotationType"));
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
