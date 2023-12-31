/**
 * CloseReceptacleResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package TestGSSMailer;

public class CloseReceptacleResponse  implements java.io.Serializable {
    private TestGSSMailer.CommonResult closeReceptacleResult;

    public CloseReceptacleResponse() {
    }

    public CloseReceptacleResponse(
           TestGSSMailer.CommonResult closeReceptacleResult) {
           this.closeReceptacleResult = closeReceptacleResult;
    }


    /**
     * Gets the closeReceptacleResult value for this CloseReceptacleResponse.
     * 
     * @return closeReceptacleResult
     */
    public TestGSSMailer.CommonResult getCloseReceptacleResult() {
        return closeReceptacleResult;
    }


    /**
     * Sets the closeReceptacleResult value for this CloseReceptacleResponse.
     * 
     * @param closeReceptacleResult
     */
    public void setCloseReceptacleResult(TestGSSMailer.CommonResult closeReceptacleResult) {
        this.closeReceptacleResult = closeReceptacleResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CloseReceptacleResponse)) return false;
        CloseReceptacleResponse other = (CloseReceptacleResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.closeReceptacleResult==null && other.getCloseReceptacleResult()==null) || 
             (this.closeReceptacleResult!=null &&
              this.closeReceptacleResult.equals(other.getCloseReceptacleResult())));
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
        if (getCloseReceptacleResult() != null) {
            _hashCode += getCloseReceptacleResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CloseReceptacleResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.usps-cpas.com/usps-cpas/GSSAPI/", ">CloseReceptacleResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("closeReceptacleResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.usps-cpas.com/usps-cpas/GSSAPI/", "CloseReceptacleResult"));
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
