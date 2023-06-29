/**
 * CloseDispatchToDestinationLocationResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package TestGSSMailer;

public class CloseDispatchToDestinationLocationResponse  implements java.io.Serializable {
    private TestGSSMailer.CloseDispatchResult closeDispatchToDestinationLocationResult;

    public CloseDispatchToDestinationLocationResponse() {
    }

    public CloseDispatchToDestinationLocationResponse(
           TestGSSMailer.CloseDispatchResult closeDispatchToDestinationLocationResult) {
           this.closeDispatchToDestinationLocationResult = closeDispatchToDestinationLocationResult;
    }


    /**
     * Gets the closeDispatchToDestinationLocationResult value for this CloseDispatchToDestinationLocationResponse.
     * 
     * @return closeDispatchToDestinationLocationResult
     */
    public TestGSSMailer.CloseDispatchResult getCloseDispatchToDestinationLocationResult() {
        return closeDispatchToDestinationLocationResult;
    }


    /**
     * Sets the closeDispatchToDestinationLocationResult value for this CloseDispatchToDestinationLocationResponse.
     * 
     * @param closeDispatchToDestinationLocationResult
     */
    public void setCloseDispatchToDestinationLocationResult(TestGSSMailer.CloseDispatchResult closeDispatchToDestinationLocationResult) {
        this.closeDispatchToDestinationLocationResult = closeDispatchToDestinationLocationResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CloseDispatchToDestinationLocationResponse)) return false;
        CloseDispatchToDestinationLocationResponse other = (CloseDispatchToDestinationLocationResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.closeDispatchToDestinationLocationResult==null && other.getCloseDispatchToDestinationLocationResult()==null) || 
             (this.closeDispatchToDestinationLocationResult!=null &&
              this.closeDispatchToDestinationLocationResult.equals(other.getCloseDispatchToDestinationLocationResult())));
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
        if (getCloseDispatchToDestinationLocationResult() != null) {
            _hashCode += getCloseDispatchToDestinationLocationResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CloseDispatchToDestinationLocationResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.usps-cpas.com/usps-cpas/GSSAPI/", ">CloseDispatchToDestinationLocationResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("closeDispatchToDestinationLocationResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.usps-cpas.com/usps-cpas/GSSAPI/", "CloseDispatchToDestinationLocationResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.usps-cpas.com/usps-cpas/GSSAPI/", "CloseDispatchResult"));
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
