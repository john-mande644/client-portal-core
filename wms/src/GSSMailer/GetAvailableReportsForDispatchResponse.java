/**
 * GetAvailableReportsForDispatchResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package GSSMailer;

public class GetAvailableReportsForDispatchResponse  implements java.io.Serializable {
    private GSSMailer.GetAvailableReportResult getAvailableReportsForDispatchResult;

    public GetAvailableReportsForDispatchResponse() {
    }

    public GetAvailableReportsForDispatchResponse(
           GSSMailer.GetAvailableReportResult getAvailableReportsForDispatchResult) {
           this.getAvailableReportsForDispatchResult = getAvailableReportsForDispatchResult;
    }


    /**
     * Gets the getAvailableReportsForDispatchResult value for this GetAvailableReportsForDispatchResponse.
     * 
     * @return getAvailableReportsForDispatchResult
     */
    public GSSMailer.GetAvailableReportResult getGetAvailableReportsForDispatchResult() {
        return getAvailableReportsForDispatchResult;
    }


    /**
     * Sets the getAvailableReportsForDispatchResult value for this GetAvailableReportsForDispatchResponse.
     * 
     * @param getAvailableReportsForDispatchResult
     */
    public void setGetAvailableReportsForDispatchResult(GSSMailer.GetAvailableReportResult getAvailableReportsForDispatchResult) {
        this.getAvailableReportsForDispatchResult = getAvailableReportsForDispatchResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetAvailableReportsForDispatchResponse)) return false;
        GetAvailableReportsForDispatchResponse other = (GetAvailableReportsForDispatchResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.getAvailableReportsForDispatchResult==null && other.getGetAvailableReportsForDispatchResult()==null) || 
             (this.getAvailableReportsForDispatchResult!=null &&
              this.getAvailableReportsForDispatchResult.equals(other.getGetAvailableReportsForDispatchResult())));
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
        if (getGetAvailableReportsForDispatchResult() != null) {
            _hashCode += getGetAvailableReportsForDispatchResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetAvailableReportsForDispatchResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.usps-cpas.com/usps-cpas/GSSAPI/", ">GetAvailableReportsForDispatchResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("getAvailableReportsForDispatchResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.usps-cpas.com/usps-cpas/GSSAPI/", "GetAvailableReportsForDispatchResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.usps-cpas.com/usps-cpas/GSSAPI/", "GetAvailableReportResult"));
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
