/**
 * LoadAndProcessPackageDataResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package GSSMailer;

public class LoadAndProcessPackageDataResponse  implements java.io.Serializable {
    private GSSMailer.LoadAndProcessPackageDataResponseLoadAndProcessPackageDataResult loadAndProcessPackageDataResult;

    public LoadAndProcessPackageDataResponse() {
    }

    public LoadAndProcessPackageDataResponse(
           GSSMailer.LoadAndProcessPackageDataResponseLoadAndProcessPackageDataResult loadAndProcessPackageDataResult) {
           this.loadAndProcessPackageDataResult = loadAndProcessPackageDataResult;
    }


    /**
     * Gets the loadAndProcessPackageDataResult value for this LoadAndProcessPackageDataResponse.
     * 
     * @return loadAndProcessPackageDataResult
     */
    public GSSMailer.LoadAndProcessPackageDataResponseLoadAndProcessPackageDataResult getLoadAndProcessPackageDataResult() {
        return loadAndProcessPackageDataResult;
    }


    /**
     * Sets the loadAndProcessPackageDataResult value for this LoadAndProcessPackageDataResponse.
     * 
     * @param loadAndProcessPackageDataResult
     */
    public void setLoadAndProcessPackageDataResult(GSSMailer.LoadAndProcessPackageDataResponseLoadAndProcessPackageDataResult loadAndProcessPackageDataResult) {
        this.loadAndProcessPackageDataResult = loadAndProcessPackageDataResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof LoadAndProcessPackageDataResponse)) return false;
        LoadAndProcessPackageDataResponse other = (LoadAndProcessPackageDataResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.loadAndProcessPackageDataResult==null && other.getLoadAndProcessPackageDataResult()==null) || 
             (this.loadAndProcessPackageDataResult!=null &&
              this.loadAndProcessPackageDataResult.equals(other.getLoadAndProcessPackageDataResult())));
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
        if (getLoadAndProcessPackageDataResult() != null) {
            _hashCode += getLoadAndProcessPackageDataResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(LoadAndProcessPackageDataResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.usps-cpas.com/usps-cpas/GSSAPI/", ">LoadAndProcessPackageDataResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("loadAndProcessPackageDataResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.usps-cpas.com/usps-cpas/GSSAPI/", "LoadAndProcessPackageDataResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.usps-cpas.com/usps-cpas/GSSAPI/", ">>LoadAndProcessPackageDataResponse>LoadAndProcessPackageDataResult"));
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
