/**
 * ArrayOfBase64Binary.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package TestGSSMailer;

public class ArrayOfBase64Binary  implements java.io.Serializable {
    private byte[][] base64Binary;

    public ArrayOfBase64Binary() {
    }

    public ArrayOfBase64Binary(
           byte[][] base64Binary) {
           this.base64Binary = base64Binary;
    }


    /**
     * Gets the base64Binary value for this ArrayOfBase64Binary.
     * 
     * @return base64Binary
     */
    public byte[][] getBase64Binary() {
        return base64Binary;
    }


    /**
     * Sets the base64Binary value for this ArrayOfBase64Binary.
     * 
     * @param base64Binary
     */
    public void setBase64Binary(byte[][] base64Binary) {
        this.base64Binary = base64Binary;
    }

    public byte[] getBase64Binary(int i) {
        return this.base64Binary[i];
    }

    public void setBase64Binary(int i, byte[] _value) {
        this.base64Binary[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ArrayOfBase64Binary)) return false;
        ArrayOfBase64Binary other = (ArrayOfBase64Binary) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.base64Binary==null && other.getBase64Binary()==null) || 
             (this.base64Binary!=null &&
              java.util.Arrays.equals(this.base64Binary, other.getBase64Binary())));
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
        if (getBase64Binary() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getBase64Binary());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getBase64Binary(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ArrayOfBase64Binary.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.usps-cpas.com/usps-cpas/GSSAPI/", "ArrayOfBase64Binary"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("base64Binary");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.usps-cpas.com/usps-cpas/GSSAPI/", "base64Binary"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "base64Binary"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
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
