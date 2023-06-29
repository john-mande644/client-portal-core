/**
 * GetDestinationLocationsResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package TestGSSMailer;

public class GetDestinationLocationsResponse  implements java.io.Serializable {
    private TestGSSMailer.DestinationLocationsResult getDestinationLocationsResult;

    public GetDestinationLocationsResponse() {
    }

    public GetDestinationLocationsResponse(
           TestGSSMailer.DestinationLocationsResult getDestinationLocationsResult) {
           this.getDestinationLocationsResult = getDestinationLocationsResult;
    }


    /**
     * Gets the getDestinationLocationsResult value for this GetDestinationLocationsResponse.
     * 
     * @return getDestinationLocationsResult
     */
    public TestGSSMailer.DestinationLocationsResult getGetDestinationLocationsResult() {
        return getDestinationLocationsResult;
    }


    /**
     * Sets the getDestinationLocationsResult value for this GetDestinationLocationsResponse.
     * 
     * @param getDestinationLocationsResult
     */
    public void setGetDestinationLocationsResult(TestGSSMailer.DestinationLocationsResult getDestinationLocationsResult) {
        this.getDestinationLocationsResult = getDestinationLocationsResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetDestinationLocationsResponse)) return false;
        GetDestinationLocationsResponse other = (GetDestinationLocationsResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.getDestinationLocationsResult==null && other.getGetDestinationLocationsResult()==null) || 
             (this.getDestinationLocationsResult!=null &&
              this.getDestinationLocationsResult.equals(other.getGetDestinationLocationsResult())));
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
        if (getGetDestinationLocationsResult() != null) {
            _hashCode += getGetDestinationLocationsResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetDestinationLocationsResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.usps-cpas.com/usps-cpas/GSSAPI/", ">GetDestinationLocationsResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("getDestinationLocationsResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.usps-cpas.com/usps-cpas/GSSAPI/", "GetDestinationLocationsResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.usps-cpas.com/usps-cpas/GSSAPI/", "DestinationLocationsResult"));
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
