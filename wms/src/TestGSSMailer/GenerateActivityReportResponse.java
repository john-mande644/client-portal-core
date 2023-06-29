/**
 * GenerateActivityReportResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package TestGSSMailer;

public class GenerateActivityReportResponse  implements java.io.Serializable {
    private TestGSSMailer.GenerateReportResult generateActivityReportResult;

    public GenerateActivityReportResponse() {
    }

    public GenerateActivityReportResponse(
           TestGSSMailer.GenerateReportResult generateActivityReportResult) {
           this.generateActivityReportResult = generateActivityReportResult;
    }


    /**
     * Gets the generateActivityReportResult value for this GenerateActivityReportResponse.
     * 
     * @return generateActivityReportResult
     */
    public TestGSSMailer.GenerateReportResult getGenerateActivityReportResult() {
        return generateActivityReportResult;
    }


    /**
     * Sets the generateActivityReportResult value for this GenerateActivityReportResponse.
     * 
     * @param generateActivityReportResult
     */
    public void setGenerateActivityReportResult(TestGSSMailer.GenerateReportResult generateActivityReportResult) {
        this.generateActivityReportResult = generateActivityReportResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GenerateActivityReportResponse)) return false;
        GenerateActivityReportResponse other = (GenerateActivityReportResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.generateActivityReportResult==null && other.getGenerateActivityReportResult()==null) || 
             (this.generateActivityReportResult!=null &&
              this.generateActivityReportResult.equals(other.getGenerateActivityReportResult())));
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
        if (getGenerateActivityReportResult() != null) {
            _hashCode += getGenerateActivityReportResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GenerateActivityReportResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.usps-cpas.com/usps-cpas/GSSAPI/", ">GenerateActivityReportResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("generateActivityReportResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.usps-cpas.com/usps-cpas/GSSAPI/", "GenerateActivityReportResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.usps-cpas.com/usps-cpas/GSSAPI/", "GenerateReportResult"));
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
