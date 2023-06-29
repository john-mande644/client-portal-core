/**
 * OpenReceptacleResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package TestGSSMailer;

public class OpenReceptacleResponse  implements java.io.Serializable {
    private TestGSSMailer.CommonResult openReceptacleResult;

    public OpenReceptacleResponse() {
    }

    public OpenReceptacleResponse(
           TestGSSMailer.CommonResult openReceptacleResult) {
           this.openReceptacleResult = openReceptacleResult;
    }


    /**
     * Gets the openReceptacleResult value for this OpenReceptacleResponse.
     * 
     * @return openReceptacleResult
     */
    public TestGSSMailer.CommonResult getOpenReceptacleResult() {
        return openReceptacleResult;
    }


    /**
     * Sets the openReceptacleResult value for this OpenReceptacleResponse.
     * 
     * @param openReceptacleResult
     */
    public void setOpenReceptacleResult(TestGSSMailer.CommonResult openReceptacleResult) {
        this.openReceptacleResult = openReceptacleResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof OpenReceptacleResponse)) return false;
        OpenReceptacleResponse other = (OpenReceptacleResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.openReceptacleResult==null && other.getOpenReceptacleResult()==null) || 
             (this.openReceptacleResult!=null &&
              this.openReceptacleResult.equals(other.getOpenReceptacleResult())));
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
        if (getOpenReceptacleResult() != null) {
            _hashCode += getOpenReceptacleResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(OpenReceptacleResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.usps-cpas.com/usps-cpas/GSSAPI/", ">OpenReceptacleResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("openReceptacleResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.usps-cpas.com/usps-cpas/GSSAPI/", "OpenReceptacleResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.usps-cpas.com/usps-cpas/GSSAPI/", "CommonResult"));
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
