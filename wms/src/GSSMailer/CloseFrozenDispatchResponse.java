/**
 * CloseFrozenDispatchResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package GSSMailer;

public class CloseFrozenDispatchResponse  implements java.io.Serializable {
    private GSSMailer.CloseDispatchResult closeFrozenDispatchResult;

    public CloseFrozenDispatchResponse() {
    }

    public CloseFrozenDispatchResponse(
           GSSMailer.CloseDispatchResult closeFrozenDispatchResult) {
           this.closeFrozenDispatchResult = closeFrozenDispatchResult;
    }


    /**
     * Gets the closeFrozenDispatchResult value for this CloseFrozenDispatchResponse.
     * 
     * @return closeFrozenDispatchResult
     */
    public GSSMailer.CloseDispatchResult getCloseFrozenDispatchResult() {
        return closeFrozenDispatchResult;
    }


    /**
     * Sets the closeFrozenDispatchResult value for this CloseFrozenDispatchResponse.
     * 
     * @param closeFrozenDispatchResult
     */
    public void setCloseFrozenDispatchResult(GSSMailer.CloseDispatchResult closeFrozenDispatchResult) {
        this.closeFrozenDispatchResult = closeFrozenDispatchResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CloseFrozenDispatchResponse)) return false;
        CloseFrozenDispatchResponse other = (CloseFrozenDispatchResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.closeFrozenDispatchResult==null && other.getCloseFrozenDispatchResult()==null) || 
             (this.closeFrozenDispatchResult!=null &&
              this.closeFrozenDispatchResult.equals(other.getCloseFrozenDispatchResult())));
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
        if (getCloseFrozenDispatchResult() != null) {
            _hashCode += getCloseFrozenDispatchResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CloseFrozenDispatchResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.usps-cpas.com/usps-cpas/GSSAPI/", ">CloseFrozenDispatchResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("closeFrozenDispatchResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.usps-cpas.com/usps-cpas/GSSAPI/", "CloseFrozenDispatchResult"));
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
