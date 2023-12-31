/**
 * CloseDispatchToDestinationLocation.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package GSSMailer;

public class CloseDispatchToDestinationLocation  implements java.io.Serializable {
    private java.lang.String destinationLocationID;

    private java.lang.String vehicleNum;

    private java.lang.String vehicleType;

    private java.lang.String depDateTime;

    private java.lang.String arrDateTime;

    private java.lang.String accessToken;

    public CloseDispatchToDestinationLocation() {
    }

    public CloseDispatchToDestinationLocation(
           java.lang.String destinationLocationID,
           java.lang.String vehicleNum,
           java.lang.String vehicleType,
           java.lang.String depDateTime,
           java.lang.String arrDateTime,
           java.lang.String accessToken) {
           this.destinationLocationID = destinationLocationID;
           this.vehicleNum = vehicleNum;
           this.vehicleType = vehicleType;
           this.depDateTime = depDateTime;
           this.arrDateTime = arrDateTime;
           this.accessToken = accessToken;
    }


    /**
     * Gets the destinationLocationID value for this CloseDispatchToDestinationLocation.
     * 
     * @return destinationLocationID
     */
    public java.lang.String getDestinationLocationID() {
        return destinationLocationID;
    }


    /**
     * Sets the destinationLocationID value for this CloseDispatchToDestinationLocation.
     * 
     * @param destinationLocationID
     */
    public void setDestinationLocationID(java.lang.String destinationLocationID) {
        this.destinationLocationID = destinationLocationID;
    }


    /**
     * Gets the vehicleNum value for this CloseDispatchToDestinationLocation.
     * 
     * @return vehicleNum
     */
    public java.lang.String getVehicleNum() {
        return vehicleNum;
    }


    /**
     * Sets the vehicleNum value for this CloseDispatchToDestinationLocation.
     * 
     * @param vehicleNum
     */
    public void setVehicleNum(java.lang.String vehicleNum) {
        this.vehicleNum = vehicleNum;
    }


    /**
     * Gets the vehicleType value for this CloseDispatchToDestinationLocation.
     * 
     * @return vehicleType
     */
    public java.lang.String getVehicleType() {
        return vehicleType;
    }


    /**
     * Sets the vehicleType value for this CloseDispatchToDestinationLocation.
     * 
     * @param vehicleType
     */
    public void setVehicleType(java.lang.String vehicleType) {
        this.vehicleType = vehicleType;
    }


    /**
     * Gets the depDateTime value for this CloseDispatchToDestinationLocation.
     * 
     * @return depDateTime
     */
    public java.lang.String getDepDateTime() {
        return depDateTime;
    }


    /**
     * Sets the depDateTime value for this CloseDispatchToDestinationLocation.
     * 
     * @param depDateTime
     */
    public void setDepDateTime(java.lang.String depDateTime) {
        this.depDateTime = depDateTime;
    }


    /**
     * Gets the arrDateTime value for this CloseDispatchToDestinationLocation.
     * 
     * @return arrDateTime
     */
    public java.lang.String getArrDateTime() {
        return arrDateTime;
    }


    /**
     * Sets the arrDateTime value for this CloseDispatchToDestinationLocation.
     * 
     * @param arrDateTime
     */
    public void setArrDateTime(java.lang.String arrDateTime) {
        this.arrDateTime = arrDateTime;
    }


    /**
     * Gets the accessToken value for this CloseDispatchToDestinationLocation.
     * 
     * @return accessToken
     */
    public java.lang.String getAccessToken() {
        return accessToken;
    }


    /**
     * Sets the accessToken value for this CloseDispatchToDestinationLocation.
     * 
     * @param accessToken
     */
    public void setAccessToken(java.lang.String accessToken) {
        this.accessToken = accessToken;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CloseDispatchToDestinationLocation)) return false;
        CloseDispatchToDestinationLocation other = (CloseDispatchToDestinationLocation) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.destinationLocationID==null && other.getDestinationLocationID()==null) || 
             (this.destinationLocationID!=null &&
              this.destinationLocationID.equals(other.getDestinationLocationID()))) &&
            ((this.vehicleNum==null && other.getVehicleNum()==null) || 
             (this.vehicleNum!=null &&
              this.vehicleNum.equals(other.getVehicleNum()))) &&
            ((this.vehicleType==null && other.getVehicleType()==null) || 
             (this.vehicleType!=null &&
              this.vehicleType.equals(other.getVehicleType()))) &&
            ((this.depDateTime==null && other.getDepDateTime()==null) || 
             (this.depDateTime!=null &&
              this.depDateTime.equals(other.getDepDateTime()))) &&
            ((this.arrDateTime==null && other.getArrDateTime()==null) || 
             (this.arrDateTime!=null &&
              this.arrDateTime.equals(other.getArrDateTime()))) &&
            ((this.accessToken==null && other.getAccessToken()==null) || 
             (this.accessToken!=null &&
              this.accessToken.equals(other.getAccessToken())));
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
        if (getDestinationLocationID() != null) {
            _hashCode += getDestinationLocationID().hashCode();
        }
        if (getVehicleNum() != null) {
            _hashCode += getVehicleNum().hashCode();
        }
        if (getVehicleType() != null) {
            _hashCode += getVehicleType().hashCode();
        }
        if (getDepDateTime() != null) {
            _hashCode += getDepDateTime().hashCode();
        }
        if (getArrDateTime() != null) {
            _hashCode += getArrDateTime().hashCode();
        }
        if (getAccessToken() != null) {
            _hashCode += getAccessToken().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CloseDispatchToDestinationLocation.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.usps-cpas.com/usps-cpas/GSSAPI/", ">CloseDispatchToDestinationLocation"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("destinationLocationID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.usps-cpas.com/usps-cpas/GSSAPI/", "DestinationLocationID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vehicleNum");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.usps-cpas.com/usps-cpas/GSSAPI/", "VehicleNum"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vehicleType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.usps-cpas.com/usps-cpas/GSSAPI/", "VehicleType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("depDateTime");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.usps-cpas.com/usps-cpas/GSSAPI/", "DepDateTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("arrDateTime");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.usps-cpas.com/usps-cpas/GSSAPI/", "ArrDateTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("accessToken");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.usps-cpas.com/usps-cpas/GSSAPI/", "AccessToken"));
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
