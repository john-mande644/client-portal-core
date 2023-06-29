/**
 * RemovePackageFromFrozenDispatchResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package GSSMailer;

public class RemovePackageFromFrozenDispatchResponse  implements java.io.Serializable {
    private GSSMailer.CommonResult removePackageFromFrozenDispatchResult;

    public RemovePackageFromFrozenDispatchResponse() {
    }

    public RemovePackageFromFrozenDispatchResponse(
           GSSMailer.CommonResult removePackageFromFrozenDispatchResult) {
           this.removePackageFromFrozenDispatchResult = removePackageFromFrozenDispatchResult;
    }


    /**
     * Gets the removePackageFromFrozenDispatchResult value for this RemovePackageFromFrozenDispatchResponse.
     * 
     * @return removePackageFromFrozenDispatchResult
     */
    public GSSMailer.CommonResult getRemovePackageFromFrozenDispatchResult() {
        return removePackageFromFrozenDispatchResult;
    }


    /**
     * Sets the removePackageFromFrozenDispatchResult value for this RemovePackageFromFrozenDispatchResponse.
     * 
     * @param removePackageFromFrozenDispatchResult
     */
    public void setRemovePackageFromFrozenDispatchResult(GSSMailer.CommonResult removePackageFromFrozenDispatchResult) {
        this.removePackageFromFrozenDispatchResult = removePackageFromFrozenDispatchResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RemovePackageFromFrozenDispatchResponse)) return false;
        RemovePackageFromFrozenDispatchResponse other = (RemovePackageFromFrozenDispatchResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.removePackageFromFrozenDispatchResult==null && other.getRemovePackageFromFrozenDispatchResult()==null) || 
             (this.removePackageFromFrozenDispatchResult!=null &&
              this.removePackageFromFrozenDispatchResult.equals(other.getRemovePackageFromFrozenDispatchResult())));
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
        if (getRemovePackageFromFrozenDispatchResult() != null) {
            _hashCode += getRemovePackageFromFrozenDispatchResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RemovePackageFromFrozenDispatchResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.usps-cpas.com/usps-cpas/GSSAPI/", ">RemovePackageFromFrozenDispatchResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("removePackageFromFrozenDispatchResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.usps-cpas.com/usps-cpas/GSSAPI/", "RemovePackageFromFrozenDispatchResult"));
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
