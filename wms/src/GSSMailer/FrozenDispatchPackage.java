/**
 * FrozenDispatchPackage.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package GSSMailer;

public class FrozenDispatchPackage  implements java.io.Serializable {
    private java.lang.String packageID;

    private java.lang.String USPSPackageID;

    private java.lang.String originatingMailerName;

    private java.lang.String receipentName;

    public FrozenDispatchPackage() {
    }

    public FrozenDispatchPackage(
           java.lang.String packageID,
           java.lang.String USPSPackageID,
           java.lang.String originatingMailerName,
           java.lang.String receipentName) {
           this.packageID = packageID;
           this.USPSPackageID = USPSPackageID;
           this.originatingMailerName = originatingMailerName;
           this.receipentName = receipentName;
    }


    /**
     * Gets the packageID value for this FrozenDispatchPackage.
     * 
     * @return packageID
     */
    public java.lang.String getPackageID() {
        return packageID;
    }


    /**
     * Sets the packageID value for this FrozenDispatchPackage.
     * 
     * @param packageID
     */
    public void setPackageID(java.lang.String packageID) {
        this.packageID = packageID;
    }


    /**
     * Gets the USPSPackageID value for this FrozenDispatchPackage.
     * 
     * @return USPSPackageID
     */
    public java.lang.String getUSPSPackageID() {
        return USPSPackageID;
    }


    /**
     * Sets the USPSPackageID value for this FrozenDispatchPackage.
     * 
     * @param USPSPackageID
     */
    public void setUSPSPackageID(java.lang.String USPSPackageID) {
        this.USPSPackageID = USPSPackageID;
    }


    /**
     * Gets the originatingMailerName value for this FrozenDispatchPackage.
     * 
     * @return originatingMailerName
     */
    public java.lang.String getOriginatingMailerName() {
        return originatingMailerName;
    }


    /**
     * Sets the originatingMailerName value for this FrozenDispatchPackage.
     * 
     * @param originatingMailerName
     */
    public void setOriginatingMailerName(java.lang.String originatingMailerName) {
        this.originatingMailerName = originatingMailerName;
    }


    /**
     * Gets the receipentName value for this FrozenDispatchPackage.
     * 
     * @return receipentName
     */
    public java.lang.String getReceipentName() {
        return receipentName;
    }


    /**
     * Sets the receipentName value for this FrozenDispatchPackage.
     * 
     * @param receipentName
     */
    public void setReceipentName(java.lang.String receipentName) {
        this.receipentName = receipentName;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof FrozenDispatchPackage)) return false;
        FrozenDispatchPackage other = (FrozenDispatchPackage) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.packageID==null && other.getPackageID()==null) || 
             (this.packageID!=null &&
              this.packageID.equals(other.getPackageID()))) &&
            ((this.USPSPackageID==null && other.getUSPSPackageID()==null) || 
             (this.USPSPackageID!=null &&
              this.USPSPackageID.equals(other.getUSPSPackageID()))) &&
            ((this.originatingMailerName==null && other.getOriginatingMailerName()==null) || 
             (this.originatingMailerName!=null &&
              this.originatingMailerName.equals(other.getOriginatingMailerName()))) &&
            ((this.receipentName==null && other.getReceipentName()==null) || 
             (this.receipentName!=null &&
              this.receipentName.equals(other.getReceipentName())));
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
        if (getPackageID() != null) {
            _hashCode += getPackageID().hashCode();
        }
        if (getUSPSPackageID() != null) {
            _hashCode += getUSPSPackageID().hashCode();
        }
        if (getOriginatingMailerName() != null) {
            _hashCode += getOriginatingMailerName().hashCode();
        }
        if (getReceipentName() != null) {
            _hashCode += getReceipentName().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(FrozenDispatchPackage.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.usps-cpas.com/usps-cpas/GSSAPI/", "FrozenDispatchPackage"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("packageID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.usps-cpas.com/usps-cpas/GSSAPI/", "PackageID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("USPSPackageID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.usps-cpas.com/usps-cpas/GSSAPI/", "USPSPackageID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("originatingMailerName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.usps-cpas.com/usps-cpas/GSSAPI/", "OriginatingMailerName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("receipentName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.usps-cpas.com/usps-cpas/GSSAPI/", "ReceipentName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
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
