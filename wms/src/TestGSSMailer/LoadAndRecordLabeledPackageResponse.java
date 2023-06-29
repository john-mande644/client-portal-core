/**
 * LoadAndRecordLabeledPackageResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package TestGSSMailer;

public class LoadAndRecordLabeledPackageResponse  implements java.io.Serializable {
    private TestGSSMailer.LoadAndRecordLabeledPackageResponseLoadAndRecordLabeledPackageResult loadAndRecordLabeledPackageResult;

    public LoadAndRecordLabeledPackageResponse() {
    }

    public LoadAndRecordLabeledPackageResponse(
           TestGSSMailer.LoadAndRecordLabeledPackageResponseLoadAndRecordLabeledPackageResult loadAndRecordLabeledPackageResult) {
           this.loadAndRecordLabeledPackageResult = loadAndRecordLabeledPackageResult;
    }


    /**
     * Gets the loadAndRecordLabeledPackageResult value for this LoadAndRecordLabeledPackageResponse.
     * 
     * @return loadAndRecordLabeledPackageResult
     */
    public TestGSSMailer.LoadAndRecordLabeledPackageResponseLoadAndRecordLabeledPackageResult getLoadAndRecordLabeledPackageResult() {
        return loadAndRecordLabeledPackageResult;
    }


    /**
     * Sets the loadAndRecordLabeledPackageResult value for this LoadAndRecordLabeledPackageResponse.
     * 
     * @param loadAndRecordLabeledPackageResult
     */
    public void setLoadAndRecordLabeledPackageResult(TestGSSMailer.LoadAndRecordLabeledPackageResponseLoadAndRecordLabeledPackageResult loadAndRecordLabeledPackageResult) {
        this.loadAndRecordLabeledPackageResult = loadAndRecordLabeledPackageResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof LoadAndRecordLabeledPackageResponse)) return false;
        LoadAndRecordLabeledPackageResponse other = (LoadAndRecordLabeledPackageResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.loadAndRecordLabeledPackageResult==null && other.getLoadAndRecordLabeledPackageResult()==null) || 
             (this.loadAndRecordLabeledPackageResult!=null &&
              this.loadAndRecordLabeledPackageResult.equals(other.getLoadAndRecordLabeledPackageResult())));
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
        if (getLoadAndRecordLabeledPackageResult() != null) {
            _hashCode += getLoadAndRecordLabeledPackageResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(LoadAndRecordLabeledPackageResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.usps-cpas.com/usps-cpas/GSSAPI/", ">LoadAndRecordLabeledPackageResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("loadAndRecordLabeledPackageResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.usps-cpas.com/usps-cpas/GSSAPI/", "LoadAndRecordLabeledPackageResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.usps-cpas.com/usps-cpas/GSSAPI/", ">>LoadAndRecordLabeledPackageResponse>LoadAndRecordLabeledPackageResult"));
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