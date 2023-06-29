/*
 * CompletedPackageDetail.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;

public class CompletedPackageDetail  implements java.io.Serializable {
    private org.apache.axis.types.PositiveInteger sequenceNumber;
    private com.owd.fedEx.ShipService.TrackingId[] trackingIds;
    private org.apache.axis.types.NonNegativeInteger groupNumber;
    private com.owd.fedEx.ShipService.OversizeClassType oversizeClass;
    /** This class groups together for a single package all package-level
 * rate data (across all rate types) as part of the response to a shipping
 * request, which groups shipment-level data together and groups package-level
 * data by package. */
    private com.owd.fedEx.ShipService.PackageRating packageRating;
    /** Package-level data required for labeling and/or movement. */
    private com.owd.fedEx.ShipService.PackageOperationalDetail operationalDetail;
    private com.owd.fedEx.ShipService.ShippingDocument label;
    /** All package-level shipping documents (other than labels and barcodes).
 * For use in loads after January, 2008. */
    private com.owd.fedEx.ShipService.ShippingDocument[] packageDocuments;
    /** Specifies the information associated with a package that has COD
 * special service in a ground shipment. */
    private com.owd.fedEx.ShipService.CodReturnPackageDetail codReturnDetail;
    private com.owd.fedEx.ShipService.SignatureOptionType signatureOption;
    /** The descriptive data for the heaviness of an object. */
    private com.owd.fedEx.ShipService.Weight dryIceWeight;
    /** Completed package-level hazardous commodity information for a single
 * package. */
    private com.owd.fedEx.ShipService.CompletedHazardousPackageDetail hazardousPackageDetail;

    public CompletedPackageDetail() {
    }


    /**
     * Gets the sequenceNumber value for this CompletedPackageDetail.
     * 
     * @return sequenceNumber
     */
    public org.apache.axis.types.PositiveInteger getSequenceNumber() {
        return sequenceNumber;
    }


    /**
     * Sets the sequenceNumber value for this CompletedPackageDetail.
     * 
     * @param sequenceNumber
     */
    public void setSequenceNumber(org.apache.axis.types.PositiveInteger sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }


    /**
     * Gets the trackingIds value for this CompletedPackageDetail.
     * 
     * @return trackingIds
     */
    public com.owd.fedEx.ShipService.TrackingId[] getTrackingIds() {
        return trackingIds;
    }


    /**
     * Sets the trackingIds value for this CompletedPackageDetail.
     * 
     * @param trackingIds
     */
    public void setTrackingIds(com.owd.fedEx.ShipService.TrackingId[] trackingIds) {
        this.trackingIds = trackingIds;
    }

    public com.owd.fedEx.ShipService.TrackingId getTrackingIds(int i) {
        return trackingIds[i];
    }

    public void setTrackingIds(int i, com.owd.fedEx.ShipService.TrackingId value) {
        this.trackingIds[i] = value;
    }


    /**
     * Gets the groupNumber value for this CompletedPackageDetail.
     * 
     * @return groupNumber
     */
    public org.apache.axis.types.NonNegativeInteger getGroupNumber() {
        return groupNumber;
    }


    /**
     * Sets the groupNumber value for this CompletedPackageDetail.
     * 
     * @param groupNumber
     */
    public void setGroupNumber(org.apache.axis.types.NonNegativeInteger groupNumber) {
        this.groupNumber = groupNumber;
    }


    /**
     * Gets the oversizeClass value for this CompletedPackageDetail.
     * 
     * @return oversizeClass
     */
    public com.owd.fedEx.ShipService.OversizeClassType getOversizeClass() {
        return oversizeClass;
    }


    /**
     * Sets the oversizeClass value for this CompletedPackageDetail.
     * 
     * @param oversizeClass
     */
    public void setOversizeClass(com.owd.fedEx.ShipService.OversizeClassType oversizeClass) {
        this.oversizeClass = oversizeClass;
    }


    /**
     * Gets the packageRating value for this CompletedPackageDetail.
     * 
     * @return packageRating This class groups together for a single package all package-level
 * rate data (across all rate types) as part of the response to a shipping
 * request, which groups shipment-level data together and groups package-level
 * data by package.
     */
    public com.owd.fedEx.ShipService.PackageRating getPackageRating() {
        return packageRating;
    }


    /**
     * Sets the packageRating value for this CompletedPackageDetail.
     * 
     * @param packageRating This class groups together for a single package all package-level
 * rate data (across all rate types) as part of the response to a shipping
 * request, which groups shipment-level data together and groups package-level
 * data by package.
     */
    public void setPackageRating(com.owd.fedEx.ShipService.PackageRating packageRating) {
        this.packageRating = packageRating;
    }


    /**
     * Gets the operationalDetail value for this CompletedPackageDetail.
     * 
     * @return operationalDetail Package-level data required for labeling and/or movement.
     */
    public com.owd.fedEx.ShipService.PackageOperationalDetail getOperationalDetail() {
        return operationalDetail;
    }


    /**
     * Sets the operationalDetail value for this CompletedPackageDetail.
     * 
     * @param operationalDetail Package-level data required for labeling and/or movement.
     */
    public void setOperationalDetail(com.owd.fedEx.ShipService.PackageOperationalDetail operationalDetail) {
        this.operationalDetail = operationalDetail;
    }


    /**
     * Gets the label value for this CompletedPackageDetail.
     * 
     * @return label
     */
    public com.owd.fedEx.ShipService.ShippingDocument getLabel() {
        return label;
    }


    /**
     * Sets the label value for this CompletedPackageDetail.
     * 
     * @param label
     */
    public void setLabel(com.owd.fedEx.ShipService.ShippingDocument label) {
        this.label = label;
    }


    /**
     * Gets the packageDocuments value for this CompletedPackageDetail.
     * 
     * @return packageDocuments All package-level shipping documents (other than labels and barcodes).
 * For use in loads after January, 2008.
     */
    public com.owd.fedEx.ShipService.ShippingDocument[] getPackageDocuments() {
        return packageDocuments;
    }


    /**
     * Sets the packageDocuments value for this CompletedPackageDetail.
     * 
     * @param packageDocuments All package-level shipping documents (other than labels and barcodes).
 * For use in loads after January, 2008.
     */
    public void setPackageDocuments(com.owd.fedEx.ShipService.ShippingDocument[] packageDocuments) {
        this.packageDocuments = packageDocuments;
    }

    public com.owd.fedEx.ShipService.ShippingDocument getPackageDocuments(int i) {
        return packageDocuments[i];
    }

    public void setPackageDocuments(int i, com.owd.fedEx.ShipService.ShippingDocument value) {
        this.packageDocuments[i] = value;
    }


    /**
     * Gets the codReturnDetail value for this CompletedPackageDetail.
     * 
     * @return codReturnDetail Specifies the information associated with a package that has COD
 * special service in a ground shipment.
     */
    public com.owd.fedEx.ShipService.CodReturnPackageDetail getCodReturnDetail() {
        return codReturnDetail;
    }


    /**
     * Sets the codReturnDetail value for this CompletedPackageDetail.
     * 
     * @param codReturnDetail Specifies the information associated with a package that has COD
 * special service in a ground shipment.
     */
    public void setCodReturnDetail(com.owd.fedEx.ShipService.CodReturnPackageDetail codReturnDetail) {
        this.codReturnDetail = codReturnDetail;
    }


    /**
     * Gets the signatureOption value for this CompletedPackageDetail.
     * 
     * @return signatureOption
     */
    public com.owd.fedEx.ShipService.SignatureOptionType getSignatureOption() {
        return signatureOption;
    }


    /**
     * Sets the signatureOption value for this CompletedPackageDetail.
     * 
     * @param signatureOption
     */
    public void setSignatureOption(com.owd.fedEx.ShipService.SignatureOptionType signatureOption) {
        this.signatureOption = signatureOption;
    }


    /**
     * Gets the dryIceWeight value for this CompletedPackageDetail.
     * 
     * @return dryIceWeight The descriptive data for the heaviness of an object.
     */
    public com.owd.fedEx.ShipService.Weight getDryIceWeight() {
        return dryIceWeight;
    }


    /**
     * Sets the dryIceWeight value for this CompletedPackageDetail.
     * 
     * @param dryIceWeight The descriptive data for the heaviness of an object.
     */
    public void setDryIceWeight(com.owd.fedEx.ShipService.Weight dryIceWeight) {
        this.dryIceWeight = dryIceWeight;
    }


    /**
     * Gets the hazardousPackageDetail value for this CompletedPackageDetail.
     * 
     * @return hazardousPackageDetail Completed package-level hazardous commodity information for a single
 * package.
     */
    public com.owd.fedEx.ShipService.CompletedHazardousPackageDetail getHazardousPackageDetail() {
        return hazardousPackageDetail;
    }


    /**
     * Sets the hazardousPackageDetail value for this CompletedPackageDetail.
     * 
     * @param hazardousPackageDetail Completed package-level hazardous commodity information for a single
 * package.
     */
    public void setHazardousPackageDetail(com.owd.fedEx.ShipService.CompletedHazardousPackageDetail hazardousPackageDetail) {
        this.hazardousPackageDetail = hazardousPackageDetail;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CompletedPackageDetail)) return false;
        CompletedPackageDetail other = (CompletedPackageDetail) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.sequenceNumber==null && other.getSequenceNumber()==null) || 
             (this.sequenceNumber!=null &&
              this.sequenceNumber.equals(other.getSequenceNumber()))) &&
            ((this.trackingIds==null && other.getTrackingIds()==null) || 
             (this.trackingIds!=null &&
              java.util.Arrays.equals(this.trackingIds, other.getTrackingIds()))) &&
            ((this.groupNumber==null && other.getGroupNumber()==null) || 
             (this.groupNumber!=null &&
              this.groupNumber.equals(other.getGroupNumber()))) &&
            ((this.oversizeClass==null && other.getOversizeClass()==null) || 
             (this.oversizeClass!=null &&
              this.oversizeClass.equals(other.getOversizeClass()))) &&
            ((this.packageRating==null && other.getPackageRating()==null) || 
             (this.packageRating!=null &&
              this.packageRating.equals(other.getPackageRating()))) &&
            ((this.operationalDetail==null && other.getOperationalDetail()==null) || 
             (this.operationalDetail!=null &&
              this.operationalDetail.equals(other.getOperationalDetail()))) &&
            ((this.label==null && other.getLabel()==null) || 
             (this.label!=null &&
              this.label.equals(other.getLabel()))) &&
            ((this.packageDocuments==null && other.getPackageDocuments()==null) || 
             (this.packageDocuments!=null &&
              java.util.Arrays.equals(this.packageDocuments, other.getPackageDocuments()))) &&
            ((this.codReturnDetail==null && other.getCodReturnDetail()==null) || 
             (this.codReturnDetail!=null &&
              this.codReturnDetail.equals(other.getCodReturnDetail()))) &&
            ((this.signatureOption==null && other.getSignatureOption()==null) || 
             (this.signatureOption!=null &&
              this.signatureOption.equals(other.getSignatureOption()))) &&
            ((this.dryIceWeight==null && other.getDryIceWeight()==null) || 
             (this.dryIceWeight!=null &&
              this.dryIceWeight.equals(other.getDryIceWeight()))) &&
            ((this.hazardousPackageDetail==null && other.getHazardousPackageDetail()==null) || 
             (this.hazardousPackageDetail!=null &&
              this.hazardousPackageDetail.equals(other.getHazardousPackageDetail())));
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
        if (getSequenceNumber() != null) {
            _hashCode += getSequenceNumber().hashCode();
        }
        if (getTrackingIds() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getTrackingIds());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getTrackingIds(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getGroupNumber() != null) {
            _hashCode += getGroupNumber().hashCode();
        }
        if (getOversizeClass() != null) {
            _hashCode += getOversizeClass().hashCode();
        }
        if (getPackageRating() != null) {
            _hashCode += getPackageRating().hashCode();
        }
        if (getOperationalDetail() != null) {
            _hashCode += getOperationalDetail().hashCode();
        }
        if (getLabel() != null) {
            _hashCode += getLabel().hashCode();
        }
        if (getPackageDocuments() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getPackageDocuments());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getPackageDocuments(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getCodReturnDetail() != null) {
            _hashCode += getCodReturnDetail().hashCode();
        }
        if (getSignatureOption() != null) {
            _hashCode += getSignatureOption().hashCode();
        }
        if (getDryIceWeight() != null) {
            _hashCode += getDryIceWeight().hashCode();
        }
        if (getHazardousPackageDetail() != null) {
            _hashCode += getHazardousPackageDetail().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CompletedPackageDetail.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "CompletedPackageDetail"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sequenceNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "SequenceNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "positiveInteger"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("trackingIds");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "TrackingIds"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "TrackingId"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("groupNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "GroupNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "nonNegativeInteger"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("oversizeClass");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "OversizeClass"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "OversizeClassType"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("packageRating");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "PackageRating"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "PackageRating"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("operationalDetail");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "OperationalDetail"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "PackageOperationalDetail"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("label");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Label"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ShippingDocument"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("packageDocuments");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "PackageDocuments"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ShippingDocument"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codReturnDetail");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "CodReturnDetail"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "CodReturnPackageDetail"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("signatureOption");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "SignatureOption"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "SignatureOptionType"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dryIceWeight");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "DryIceWeight"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Weight"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("hazardousPackageDetail");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "HazardousPackageDetail"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "CompletedHazardousPackageDetail"));
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
