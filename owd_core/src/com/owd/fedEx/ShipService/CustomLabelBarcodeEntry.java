/*
 * CustomLabelBarcodeEntry.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;


/**
 * Constructed string, based on format and zero or more data fields,
 * printed in specified barcode symbology.
 */
public class CustomLabelBarcodeEntry  implements java.io.Serializable {
    private com.owd.fedEx.ShipService.CustomLabelPosition position;
    private java.lang.String format;
    private java.lang.String[] dataFields;
    private java.lang.Integer barHeight;
    private java.lang.Integer thinBarWidth;
    /** Identification of the type of barcode (symbology) used on FedEx
 * documents and labels. */
    private com.owd.fedEx.ShipService.BarcodeSymbologyType barcodeSymbology;

    public CustomLabelBarcodeEntry() {
    }


    /**
     * Gets the position value for this CustomLabelBarcodeEntry.
     * 
     * @return position
     */
    public com.owd.fedEx.ShipService.CustomLabelPosition getPosition() {
        return position;
    }


    /**
     * Sets the position value for this CustomLabelBarcodeEntry.
     * 
     * @param position
     */
    public void setPosition(com.owd.fedEx.ShipService.CustomLabelPosition position) {
        this.position = position;
    }


    /**
     * Gets the format value for this CustomLabelBarcodeEntry.
     * 
     * @return format
     */
    public java.lang.String getFormat() {
        return format;
    }


    /**
     * Sets the format value for this CustomLabelBarcodeEntry.
     * 
     * @param format
     */
    public void setFormat(java.lang.String format) {
        this.format = format;
    }


    /**
     * Gets the dataFields value for this CustomLabelBarcodeEntry.
     * 
     * @return dataFields
     */
    public java.lang.String[] getDataFields() {
        return dataFields;
    }


    /**
     * Sets the dataFields value for this CustomLabelBarcodeEntry.
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
     * Gets the barHeight value for this CustomLabelBarcodeEntry.
     * 
     * @return barHeight
     */
    public java.lang.Integer getBarHeight() {
        return barHeight;
    }


    /**
     * Sets the barHeight value for this CustomLabelBarcodeEntry.
     * 
     * @param barHeight
     */
    public void setBarHeight(java.lang.Integer barHeight) {
        this.barHeight = barHeight;
    }


    /**
     * Gets the thinBarWidth value for this CustomLabelBarcodeEntry.
     * 
     * @return thinBarWidth
     */
    public java.lang.Integer getThinBarWidth() {
        return thinBarWidth;
    }


    /**
     * Sets the thinBarWidth value for this CustomLabelBarcodeEntry.
     * 
     * @param thinBarWidth
     */
    public void setThinBarWidth(java.lang.Integer thinBarWidth) {
        this.thinBarWidth = thinBarWidth;
    }


    /**
     * Gets the barcodeSymbology value for this CustomLabelBarcodeEntry.
     * 
     * @return barcodeSymbology Identification of the type of barcode (symbology) used on FedEx
 * documents and labels.
     */
    public com.owd.fedEx.ShipService.BarcodeSymbologyType getBarcodeSymbology() {
        return barcodeSymbology;
    }


    /**
     * Sets the barcodeSymbology value for this CustomLabelBarcodeEntry.
     * 
     * @param barcodeSymbology Identification of the type of barcode (symbology) used on FedEx
 * documents and labels.
     */
    public void setBarcodeSymbology(com.owd.fedEx.ShipService.BarcodeSymbologyType barcodeSymbology) {
        this.barcodeSymbology = barcodeSymbology;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CustomLabelBarcodeEntry)) return false;
        CustomLabelBarcodeEntry other = (CustomLabelBarcodeEntry) obj;
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
            ((this.barHeight==null && other.getBarHeight()==null) || 
             (this.barHeight!=null &&
              this.barHeight.equals(other.getBarHeight()))) &&
            ((this.thinBarWidth==null && other.getThinBarWidth()==null) || 
             (this.thinBarWidth!=null &&
              this.thinBarWidth.equals(other.getThinBarWidth()))) &&
            ((this.barcodeSymbology==null && other.getBarcodeSymbology()==null) || 
             (this.barcodeSymbology!=null &&
              this.barcodeSymbology.equals(other.getBarcodeSymbology())));
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
        if (getBarHeight() != null) {
            _hashCode += getBarHeight().hashCode();
        }
        if (getThinBarWidth() != null) {
            _hashCode += getThinBarWidth().hashCode();
        }
        if (getBarcodeSymbology() != null) {
            _hashCode += getBarcodeSymbology().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CustomLabelBarcodeEntry.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "CustomLabelBarcodeEntry"));
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
        elemField.setFieldName("barHeight");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "BarHeight"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "int"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("thinBarWidth");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ThinBarWidth"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "int"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("barcodeSymbology");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "BarcodeSymbology"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "BarcodeSymbologyType"));
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
