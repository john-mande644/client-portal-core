/**
 * ArrayOfTrackingEvent.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package TestGSSMailer;

public class ArrayOfTrackingEvent  implements java.io.Serializable {
    private TestGSSMailer.TrackingEvent[] trackingEvent;

    public ArrayOfTrackingEvent() {
    }

    public ArrayOfTrackingEvent(
           TestGSSMailer.TrackingEvent[] trackingEvent) {
           this.trackingEvent = trackingEvent;
    }


    /**
     * Gets the trackingEvent value for this ArrayOfTrackingEvent.
     * 
     * @return trackingEvent
     */
    public TestGSSMailer.TrackingEvent[] getTrackingEvent() {
        return trackingEvent;
    }


    /**
     * Sets the trackingEvent value for this ArrayOfTrackingEvent.
     * 
     * @param trackingEvent
     */
    public void setTrackingEvent(TestGSSMailer.TrackingEvent[] trackingEvent) {
        this.trackingEvent = trackingEvent;
    }

    public TestGSSMailer.TrackingEvent getTrackingEvent(int i) {
        return this.trackingEvent[i];
    }

    public void setTrackingEvent(int i, TestGSSMailer.TrackingEvent _value) {
        this.trackingEvent[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ArrayOfTrackingEvent)) return false;
        ArrayOfTrackingEvent other = (ArrayOfTrackingEvent) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.trackingEvent==null && other.getTrackingEvent()==null) || 
             (this.trackingEvent!=null &&
              java.util.Arrays.equals(this.trackingEvent, other.getTrackingEvent())));
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
        if (getTrackingEvent() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getTrackingEvent());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getTrackingEvent(), i);
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
        new org.apache.axis.description.TypeDesc(ArrayOfTrackingEvent.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.usps-cpas.com/usps-cpas/GSSAPI/", "ArrayOfTrackingEvent"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("trackingEvent");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.usps-cpas.com/usps-cpas/GSSAPI/", "TrackingEvent"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.usps-cpas.com/usps-cpas/GSSAPI/", "TrackingEvent"));
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
