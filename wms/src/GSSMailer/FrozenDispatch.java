/**
 * FrozenDispatch.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package GSSMailer;

public class FrozenDispatch  implements java.io.Serializable {
    private java.lang.String frozenDispGUID;

    private java.lang.String frozenDispatchID;

    private java.lang.String creationDateTime;

    private java.lang.String destinationLocation;

    private java.lang.String originationLocation;

    private int packageCount;

    public FrozenDispatch() {
    }

    public FrozenDispatch(
           java.lang.String frozenDispGUID,
           java.lang.String frozenDispatchID,
           java.lang.String creationDateTime,
           java.lang.String destinationLocation,
           java.lang.String originationLocation,
           int packageCount) {
           this.frozenDispGUID = frozenDispGUID;
           this.frozenDispatchID = frozenDispatchID;
           this.creationDateTime = creationDateTime;
           this.destinationLocation = destinationLocation;
           this.originationLocation = originationLocation;
           this.packageCount = packageCount;
    }


    /**
     * Gets the frozenDispGUID value for this FrozenDispatch.
     * 
     * @return frozenDispGUID
     */
    public java.lang.String getFrozenDispGUID() {
        return frozenDispGUID;
    }


    /**
     * Sets the frozenDispGUID value for this FrozenDispatch.
     * 
     * @param frozenDispGUID
     */
    public void setFrozenDispGUID(java.lang.String frozenDispGUID) {
        this.frozenDispGUID = frozenDispGUID;
    }


    /**
     * Gets the frozenDispatchID value for this FrozenDispatch.
     * 
     * @return frozenDispatchID
     */
    public java.lang.String getFrozenDispatchID() {
        return frozenDispatchID;
    }


    /**
     * Sets the frozenDispatchID value for this FrozenDispatch.
     * 
     * @param frozenDispatchID
     */
    public void setFrozenDispatchID(java.lang.String frozenDispatchID) {
        this.frozenDispatchID = frozenDispatchID;
    }


    /**
     * Gets the creationDateTime value for this FrozenDispatch.
     * 
     * @return creationDateTime
     */
    public java.lang.String getCreationDateTime() {
        return creationDateTime;
    }


    /**
     * Sets the creationDateTime value for this FrozenDispatch.
     * 
     * @param creationDateTime
     */
    public void setCreationDateTime(java.lang.String creationDateTime) {
        this.creationDateTime = creationDateTime;
    }


    /**
     * Gets the destinationLocation value for this FrozenDispatch.
     * 
     * @return destinationLocation
     */
    public java.lang.String getDestinationLocation() {
        return destinationLocation;
    }


    /**
     * Sets the destinationLocation value for this FrozenDispatch.
     * 
     * @param destinationLocation
     */
    public void setDestinationLocation(java.lang.String destinationLocation) {
        this.destinationLocation = destinationLocation;
    }


    /**
     * Gets the originationLocation value for this FrozenDispatch.
     * 
     * @return originationLocation
     */
    public java.lang.String getOriginationLocation() {
        return originationLocation;
    }


    /**
     * Sets the originationLocation value for this FrozenDispatch.
     * 
     * @param originationLocation
     */
    public void setOriginationLocation(java.lang.String originationLocation) {
        this.originationLocation = originationLocation;
    }


    /**
     * Gets the packageCount value for this FrozenDispatch.
     * 
     * @return packageCount
     */
    public int getPackageCount() {
        return packageCount;
    }


    /**
     * Sets the packageCount value for this FrozenDispatch.
     * 
     * @param packageCount
     */
    public void setPackageCount(int packageCount) {
        this.packageCount = packageCount;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof FrozenDispatch)) return false;
        FrozenDispatch other = (FrozenDispatch) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.frozenDispGUID==null && other.getFrozenDispGUID()==null) || 
             (this.frozenDispGUID!=null &&
              this.frozenDispGUID.equals(other.getFrozenDispGUID()))) &&
            ((this.frozenDispatchID==null && other.getFrozenDispatchID()==null) || 
             (this.frozenDispatchID!=null &&
              this.frozenDispatchID.equals(other.getFrozenDispatchID()))) &&
            ((this.creationDateTime==null && other.getCreationDateTime()==null) || 
             (this.creationDateTime!=null &&
              this.creationDateTime.equals(other.getCreationDateTime()))) &&
            ((this.destinationLocation==null && other.getDestinationLocation()==null) || 
             (this.destinationLocation!=null &&
              this.destinationLocation.equals(other.getDestinationLocation()))) &&
            ((this.originationLocation==null && other.getOriginationLocation()==null) || 
             (this.originationLocation!=null &&
              this.originationLocation.equals(other.getOriginationLocation()))) &&
            this.packageCount == other.getPackageCount();
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
        if (getFrozenDispGUID() != null) {
            _hashCode += getFrozenDispGUID().hashCode();
        }
        if (getFrozenDispatchID() != null) {
            _hashCode += getFrozenDispatchID().hashCode();
        }
        if (getCreationDateTime() != null) {
            _hashCode += getCreationDateTime().hashCode();
        }
        if (getDestinationLocation() != null) {
            _hashCode += getDestinationLocation().hashCode();
        }
        if (getOriginationLocation() != null) {
            _hashCode += getOriginationLocation().hashCode();
        }
        _hashCode += getPackageCount();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(FrozenDispatch.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.usps-cpas.com/usps-cpas/GSSAPI/", "FrozenDispatch"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("frozenDispGUID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.usps-cpas.com/usps-cpas/GSSAPI/", "FrozenDispGUID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("frozenDispatchID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.usps-cpas.com/usps-cpas/GSSAPI/", "FrozenDispatchID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("creationDateTime");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.usps-cpas.com/usps-cpas/GSSAPI/", "CreationDateTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("destinationLocation");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.usps-cpas.com/usps-cpas/GSSAPI/", "DestinationLocation"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("originationLocation");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.usps-cpas.com/usps-cpas/GSSAPI/", "OriginationLocation"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("packageCount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.usps-cpas.com/usps-cpas/GSSAPI/", "PackageCount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
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
