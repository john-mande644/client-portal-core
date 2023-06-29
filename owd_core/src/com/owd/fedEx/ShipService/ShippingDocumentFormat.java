/*
 * ShippingDocumentFormat.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;


/**
 * Specifies characteristics of a shipping document to be produced.
 */
public class ShippingDocumentFormat  implements java.io.Serializable {
    /** Specifies how to create, organize, and return the document. */
    private com.owd.fedEx.ShipService.ShippingDocumentDispositionDetail[] dispositions;
    /** Represents a one-dimensional measurement in small units (e.g. suitable
 * for measuring a package or document), contrasted with Distance, which
 * represents a large one-dimensional measurement (e.g. distance between
 * cities). */
    private com.owd.fedEx.ShipService.LinearMeasure topOfPageOffset;
    /** Specifies the image format used for a shipping document. */
    private com.owd.fedEx.ShipService.ShippingDocumentImageType imageType;
    /** Specifies the type of paper (stock) on which a document will be
 * printed. */
    private com.owd.fedEx.ShipService.ShippingDocumentStockType stockType;
    private java.lang.Boolean provideInstructions;
    private com.owd.fedEx.ShipService.DocumentFormatOptionsRequested optionsRequested;
    /** Identifies the representation of human-readable text. */
    private com.owd.fedEx.ShipService.Localization localization;
    private java.lang.String customDocumentIdentifier;

    public ShippingDocumentFormat() {
    }


    /**
     * Gets the dispositions value for this ShippingDocumentFormat.
     * 
     * @return dispositions Specifies how to create, organize, and return the document.
     */
    public com.owd.fedEx.ShipService.ShippingDocumentDispositionDetail[] getDispositions() {
        return dispositions;
    }


    /**
     * Sets the dispositions value for this ShippingDocumentFormat.
     * 
     * @param dispositions Specifies how to create, organize, and return the document.
     */
    public void setDispositions(com.owd.fedEx.ShipService.ShippingDocumentDispositionDetail[] dispositions) {
        this.dispositions = dispositions;
    }

    public com.owd.fedEx.ShipService.ShippingDocumentDispositionDetail getDispositions(int i) {
        return dispositions[i];
    }

    public void setDispositions(int i, com.owd.fedEx.ShipService.ShippingDocumentDispositionDetail value) {
        this.dispositions[i] = value;
    }


    /**
     * Gets the topOfPageOffset value for this ShippingDocumentFormat.
     * 
     * @return topOfPageOffset Represents a one-dimensional measurement in small units (e.g. suitable
 * for measuring a package or document), contrasted with Distance, which
 * represents a large one-dimensional measurement (e.g. distance between
 * cities).
     */
    public com.owd.fedEx.ShipService.LinearMeasure getTopOfPageOffset() {
        return topOfPageOffset;
    }


    /**
     * Sets the topOfPageOffset value for this ShippingDocumentFormat.
     * 
     * @param topOfPageOffset Represents a one-dimensional measurement in small units (e.g. suitable
 * for measuring a package or document), contrasted with Distance, which
 * represents a large one-dimensional measurement (e.g. distance between
 * cities).
     */
    public void setTopOfPageOffset(com.owd.fedEx.ShipService.LinearMeasure topOfPageOffset) {
        this.topOfPageOffset = topOfPageOffset;
    }


    /**
     * Gets the imageType value for this ShippingDocumentFormat.
     * 
     * @return imageType Specifies the image format used for a shipping document.
     */
    public com.owd.fedEx.ShipService.ShippingDocumentImageType getImageType() {
        return imageType;
    }


    /**
     * Sets the imageType value for this ShippingDocumentFormat.
     * 
     * @param imageType Specifies the image format used for a shipping document.
     */
    public void setImageType(com.owd.fedEx.ShipService.ShippingDocumentImageType imageType) {
        this.imageType = imageType;
    }


    /**
     * Gets the stockType value for this ShippingDocumentFormat.
     * 
     * @return stockType Specifies the type of paper (stock) on which a document will be
 * printed.
     */
    public com.owd.fedEx.ShipService.ShippingDocumentStockType getStockType() {
        return stockType;
    }


    /**
     * Sets the stockType value for this ShippingDocumentFormat.
     * 
     * @param stockType Specifies the type of paper (stock) on which a document will be
 * printed.
     */
    public void setStockType(com.owd.fedEx.ShipService.ShippingDocumentStockType stockType) {
        this.stockType = stockType;
    }


    /**
     * Gets the provideInstructions value for this ShippingDocumentFormat.
     * 
     * @return provideInstructions
     */
    public java.lang.Boolean getProvideInstructions() {
        return provideInstructions;
    }


    /**
     * Sets the provideInstructions value for this ShippingDocumentFormat.
     * 
     * @param provideInstructions
     */
    public void setProvideInstructions(java.lang.Boolean provideInstructions) {
        this.provideInstructions = provideInstructions;
    }


    /**
     * Gets the optionsRequested value for this ShippingDocumentFormat.
     * 
     * @return optionsRequested
     */
    public com.owd.fedEx.ShipService.DocumentFormatOptionsRequested getOptionsRequested() {
        return optionsRequested;
    }


    /**
     * Sets the optionsRequested value for this ShippingDocumentFormat.
     * 
     * @param optionsRequested
     */
    public void setOptionsRequested(com.owd.fedEx.ShipService.DocumentFormatOptionsRequested optionsRequested) {
        this.optionsRequested = optionsRequested;
    }


    /**
     * Gets the localization value for this ShippingDocumentFormat.
     * 
     * @return localization Identifies the representation of human-readable text.
     */
    public com.owd.fedEx.ShipService.Localization getLocalization() {
        return localization;
    }


    /**
     * Sets the localization value for this ShippingDocumentFormat.
     * 
     * @param localization Identifies the representation of human-readable text.
     */
    public void setLocalization(com.owd.fedEx.ShipService.Localization localization) {
        this.localization = localization;
    }


    /**
     * Gets the customDocumentIdentifier value for this ShippingDocumentFormat.
     * 
     * @return customDocumentIdentifier
     */
    public java.lang.String getCustomDocumentIdentifier() {
        return customDocumentIdentifier;
    }


    /**
     * Sets the customDocumentIdentifier value for this ShippingDocumentFormat.
     * 
     * @param customDocumentIdentifier
     */
    public void setCustomDocumentIdentifier(java.lang.String customDocumentIdentifier) {
        this.customDocumentIdentifier = customDocumentIdentifier;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ShippingDocumentFormat)) return false;
        ShippingDocumentFormat other = (ShippingDocumentFormat) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.dispositions==null && other.getDispositions()==null) || 
             (this.dispositions!=null &&
              java.util.Arrays.equals(this.dispositions, other.getDispositions()))) &&
            ((this.topOfPageOffset==null && other.getTopOfPageOffset()==null) || 
             (this.topOfPageOffset!=null &&
              this.topOfPageOffset.equals(other.getTopOfPageOffset()))) &&
            ((this.imageType==null && other.getImageType()==null) || 
             (this.imageType!=null &&
              this.imageType.equals(other.getImageType()))) &&
            ((this.stockType==null && other.getStockType()==null) || 
             (this.stockType!=null &&
              this.stockType.equals(other.getStockType()))) &&
            ((this.provideInstructions==null && other.getProvideInstructions()==null) || 
             (this.provideInstructions!=null &&
              this.provideInstructions.equals(other.getProvideInstructions()))) &&
            ((this.optionsRequested==null && other.getOptionsRequested()==null) || 
             (this.optionsRequested!=null &&
              this.optionsRequested.equals(other.getOptionsRequested()))) &&
            ((this.localization==null && other.getLocalization()==null) || 
             (this.localization!=null &&
              this.localization.equals(other.getLocalization()))) &&
            ((this.customDocumentIdentifier==null && other.getCustomDocumentIdentifier()==null) || 
             (this.customDocumentIdentifier!=null &&
              this.customDocumentIdentifier.equals(other.getCustomDocumentIdentifier())));
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
        if (getDispositions() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getDispositions());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getDispositions(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getTopOfPageOffset() != null) {
            _hashCode += getTopOfPageOffset().hashCode();
        }
        if (getImageType() != null) {
            _hashCode += getImageType().hashCode();
        }
        if (getStockType() != null) {
            _hashCode += getStockType().hashCode();
        }
        if (getProvideInstructions() != null) {
            _hashCode += getProvideInstructions().hashCode();
        }
        if (getOptionsRequested() != null) {
            _hashCode += getOptionsRequested().hashCode();
        }
        if (getLocalization() != null) {
            _hashCode += getLocalization().hashCode();
        }
        if (getCustomDocumentIdentifier() != null) {
            _hashCode += getCustomDocumentIdentifier().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ShippingDocumentFormat.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ShippingDocumentFormat"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dispositions");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Dispositions"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ShippingDocumentDispositionDetail"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("topOfPageOffset");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "TopOfPageOffset"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "LinearMeasure"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("imageType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ImageType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ShippingDocumentImageType"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("stockType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "StockType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ShippingDocumentStockType"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("provideInstructions");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ProvideInstructions"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "boolean"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("optionsRequested");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "OptionsRequested"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "DocumentFormatOptionsRequested"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("localization");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Localization"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Localization"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("customDocumentIdentifier");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "CustomDocumentIdentifier"));
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
