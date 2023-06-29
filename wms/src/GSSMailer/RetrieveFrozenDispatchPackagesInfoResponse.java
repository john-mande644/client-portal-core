/**
 * RetrieveFrozenDispatchPackagesInfoResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package GSSMailer;

public class RetrieveFrozenDispatchPackagesInfoResponse  implements java.io.Serializable {
    private GSSMailer.FrozenDispatchPackagesResult retrieveFrozenDispatchPackagesInfoResult;

    public RetrieveFrozenDispatchPackagesInfoResponse() {
    }

    public RetrieveFrozenDispatchPackagesInfoResponse(
           GSSMailer.FrozenDispatchPackagesResult retrieveFrozenDispatchPackagesInfoResult) {
           this.retrieveFrozenDispatchPackagesInfoResult = retrieveFrozenDispatchPackagesInfoResult;
    }


    /**
     * Gets the retrieveFrozenDispatchPackagesInfoResult value for this RetrieveFrozenDispatchPackagesInfoResponse.
     * 
     * @return retrieveFrozenDispatchPackagesInfoResult
     */
    public GSSMailer.FrozenDispatchPackagesResult getRetrieveFrozenDispatchPackagesInfoResult() {
        return retrieveFrozenDispatchPackagesInfoResult;
    }


    /**
     * Sets the retrieveFrozenDispatchPackagesInfoResult value for this RetrieveFrozenDispatchPackagesInfoResponse.
     * 
     * @param retrieveFrozenDispatchPackagesInfoResult
     */
    public void setRetrieveFrozenDispatchPackagesInfoResult(GSSMailer.FrozenDispatchPackagesResult retrieveFrozenDispatchPackagesInfoResult) {
        this.retrieveFrozenDispatchPackagesInfoResult = retrieveFrozenDispatchPackagesInfoResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RetrieveFrozenDispatchPackagesInfoResponse)) return false;
        RetrieveFrozenDispatchPackagesInfoResponse other = (RetrieveFrozenDispatchPackagesInfoResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.retrieveFrozenDispatchPackagesInfoResult==null && other.getRetrieveFrozenDispatchPackagesInfoResult()==null) || 
             (this.retrieveFrozenDispatchPackagesInfoResult!=null &&
              this.retrieveFrozenDispatchPackagesInfoResult.equals(other.getRetrieveFrozenDispatchPackagesInfoResult())));
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
        if (getRetrieveFrozenDispatchPackagesInfoResult() != null) {
            _hashCode += getRetrieveFrozenDispatchPackagesInfoResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RetrieveFrozenDispatchPackagesInfoResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.usps-cpas.com/usps-cpas/GSSAPI/", ">RetrieveFrozenDispatchPackagesInfoResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("retrieveFrozenDispatchPackagesInfoResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.usps-cpas.com/usps-cpas/GSSAPI/", "RetrieveFrozenDispatchPackagesInfoResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.usps-cpas.com/usps-cpas/GSSAPI/", "FrozenDispatchPackagesResult"));
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
