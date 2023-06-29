/*
 * ShippingDocument.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;

public class ShippingDocument  implements java.io.Serializable {
    private com.owd.fedEx.ShipService.ReturnedShippingDocumentType type;
    /** The localizations are populated if multiple language versions of
 * a shipping document are returned. */
    private com.owd.fedEx.ShipService.Localization[] localizations;
    /** Specifies how to organize all shipping documents of the same type. */
    private com.owd.fedEx.ShipService.ShippingDocumentGroupingType grouping;
    /** Specifies how to return a shipping document to the caller. */
    private com.owd.fedEx.ShipService.ShippingDocumentDispositionType shippingDocumentDisposition;
    private java.lang.String accessReference;
    /** Specifies the image format used for a shipping document. */
    private com.owd.fedEx.ShipService.ShippingDocumentImageType imageType;
    private org.apache.axis.types.NonNegativeInteger resolution;
    private org.apache.axis.types.NonNegativeInteger copiesToPrint;
    /** One or more document parts which make up a single logical document,
 * such as multiple pages of a single form. */
    private com.owd.fedEx.ShipService.ShippingDocumentPart[] parts;

    public ShippingDocument() {
    }


    /**
     * Gets the type value for this ShippingDocument.
     * 
     * @return type
     */
    public com.owd.fedEx.ShipService.ReturnedShippingDocumentType getType() {
        return type;
    }


    /**
     * Sets the type value for this ShippingDocument.
     * 
     * @param type
     */
    public void setType(com.owd.fedEx.ShipService.ReturnedShippingDocumentType type) {
        this.type = type;
    }


    /**
     * Gets the localizations value for this ShippingDocument.
     * 
     * @return localizations The localizations are populated if multiple language versions of
 * a shipping document are returned.
     */
    public com.owd.fedEx.ShipService.Localization[] getLocalizations() {
        return localizations;
    }


    /**
     * Sets the localizations value for this ShippingDocument.
     * 
     * @param localizations The localizations are populated if multiple language versions of
 * a shipping document are returned.
     */
    public void setLocalizations(com.owd.fedEx.ShipService.Localization[] localizations) {
        this.localizations = localizations;
    }

    public com.owd.fedEx.ShipService.Localization getLocalizations(int i) {
        return localizations[i];
    }

    public void setLocalizations(int i, com.owd.fedEx.ShipService.Localization value) {
        this.localizations[i] = value;
    }


    /**
     * Gets the grouping value for this ShippingDocument.
     * 
     * @return grouping Specifies how to organize all shipping documents of the same type.
     */
    public com.owd.fedEx.ShipService.ShippingDocumentGroupingType getGrouping() {
        return grouping;
    }


    /**
     * Sets the grouping value for this ShippingDocument.
     * 
     * @param grouping Specifies how to organize all shipping documents of the same type.
     */
    public void setGrouping(com.owd.fedEx.ShipService.ShippingDocumentGroupingType grouping) {
        this.grouping = grouping;
    }


    /**
     * Gets the shippingDocumentDisposition value for this ShippingDocument.
     * 
     * @return shippingDocumentDisposition Specifies how to return a shipping document to the caller.
     */
    public com.owd.fedEx.ShipService.ShippingDocumentDispositionType getShippingDocumentDisposition() {
        return shippingDocumentDisposition;
    }


    /**
     * Sets the shippingDocumentDisposition value for this ShippingDocument.
     * 
     * @param shippingDocumentDisposition Specifies how to return a shipping document to the caller.
     */
    public void setShippingDocumentDisposition(com.owd.fedEx.ShipService.ShippingDocumentDispositionType shippingDocumentDisposition) {
        this.shippingDocumentDisposition = shippingDocumentDisposition;
    }


    /**
     * Gets the accessReference value for this ShippingDocument.
     * 
     * @return accessReference
     */
    public java.lang.String getAccessReference() {
        return accessReference;
    }


    /**
     * Sets the accessReference value for this ShippingDocument.
     * 
     * @param accessReference
     */
    public void setAccessReference(java.lang.String accessReference) {
        this.accessReference = accessReference;
    }


    /**
     * Gets the imageType value for this ShippingDocument.
     * 
     * @return imageType Specifies the image format used for a shipping document.
     */
    public com.owd.fedEx.ShipService.ShippingDocumentImageType getImageType() {
        return imageType;
    }


    /**
     * Sets the imageType value for this ShippingDocument.
     * 
     * @param imageType Specifies the image format used for a shipping document.
     */
    public void setImageType(com.owd.fedEx.ShipService.ShippingDocumentImageType imageType) {
        this.imageType = imageType;
    }


    /**
     * Gets the resolution value for this ShippingDocument.
     * 
     * @return resolution
     */
    public org.apache.axis.types.NonNegativeInteger getResolution() {
        return resolution;
    }


    /**
     * Sets the resolution value for this ShippingDocument.
     * 
     * @param resolution
     */
    public void setResolution(org.apache.axis.types.NonNegativeInteger resolution) {
        this.resolution = resolution;
    }


    /**
     * Gets the copiesToPrint value for this ShippingDocument.
     * 
     * @return copiesToPrint
     */
    public org.apache.axis.types.NonNegativeInteger getCopiesToPrint() {
        return copiesToPrint;
    }


    /**
     * Sets the copiesToPrint value for this ShippingDocument.
     * 
     * @param copiesToPrint
     */
    public void setCopiesToPrint(org.apache.axis.types.NonNegativeInteger copiesToPrint) {
        this.copiesToPrint = copiesToPrint;
    }


    /**
     * Gets the parts value for this ShippingDocument.
     * 
     * @return parts One or more document parts which make up a single logical document,
 * such as multiple pages of a single form.
     */
    public com.owd.fedEx.ShipService.ShippingDocumentPart[] getParts() {
        return parts;
    }


    /**
     * Sets the parts value for this ShippingDocument.
     * 
     * @param parts One or more document parts which make up a single logical document,
 * such as multiple pages of a single form.
     */
    public void setParts(com.owd.fedEx.ShipService.ShippingDocumentPart[] parts) {
        this.parts = parts;
    }

    public com.owd.fedEx.ShipService.ShippingDocumentPart getParts(int i) {
        return parts[i];
    }

    public void setParts(int i, com.owd.fedEx.ShipService.ShippingDocumentPart value) {
        this.parts[i] = value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ShippingDocument)) return false;
        ShippingDocument other = (ShippingDocument) obj;
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
            ((this.localizations==null && other.getLocalizations()==null) || 
             (this.localizations!=null &&
              java.util.Arrays.equals(this.localizations, other.getLocalizations()))) &&
            ((this.grouping==null && other.getGrouping()==null) || 
             (this.grouping!=null &&
              this.grouping.equals(other.getGrouping()))) &&
            ((this.shippingDocumentDisposition==null && other.getShippingDocumentDisposition()==null) || 
             (this.shippingDocumentDisposition!=null &&
              this.shippingDocumentDisposition.equals(other.getShippingDocumentDisposition()))) &&
            ((this.accessReference==null && other.getAccessReference()==null) || 
             (this.accessReference!=null &&
              this.accessReference.equals(other.getAccessReference()))) &&
            ((this.imageType==null && other.getImageType()==null) || 
             (this.imageType!=null &&
              this.imageType.equals(other.getImageType()))) &&
            ((this.resolution==null && other.getResolution()==null) || 
             (this.resolution!=null &&
              this.resolution.equals(other.getResolution()))) &&
            ((this.copiesToPrint==null && other.getCopiesToPrint()==null) || 
             (this.copiesToPrint!=null &&
              this.copiesToPrint.equals(other.getCopiesToPrint()))) &&
            ((this.parts==null && other.getParts()==null) || 
             (this.parts!=null &&
              java.util.Arrays.equals(this.parts, other.getParts())));
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
        if (getLocalizations() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getLocalizations());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getLocalizations(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getGrouping() != null) {
            _hashCode += getGrouping().hashCode();
        }
        if (getShippingDocumentDisposition() != null) {
            _hashCode += getShippingDocumentDisposition().hashCode();
        }
        if (getAccessReference() != null) {
            _hashCode += getAccessReference().hashCode();
        }
        if (getImageType() != null) {
            _hashCode += getImageType().hashCode();
        }
        if (getResolution() != null) {
            _hashCode += getResolution().hashCode();
        }
        if (getCopiesToPrint() != null) {
            _hashCode += getCopiesToPrint().hashCode();
        }
        if (getParts() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getParts());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getParts(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ShippingDocument.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ShippingDocument"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("type");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Type"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ReturnedShippingDocumentType"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("localizations");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Localizations"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Localization"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("grouping");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Grouping"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ShippingDocumentGroupingType"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("shippingDocumentDisposition");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ShippingDocumentDisposition"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ShippingDocumentDispositionType"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("accessReference");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "AccessReference"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("imageType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ImageType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ShippingDocumentImageType"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("resolution");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Resolution"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "nonNegativeInteger"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("copiesToPrint");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "CopiesToPrint"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "nonNegativeInteger"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("parts");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Parts"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ShippingDocumentPart"));
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
