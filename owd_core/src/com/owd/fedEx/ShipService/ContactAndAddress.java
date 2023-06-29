/*
 * ContactAndAddress.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;

public class ContactAndAddress  implements java.io.Serializable {
    /** The descriptive data for a point-of-contact person. */
    private com.owd.fedEx.ShipService.Contact contact;
    /** Descriptive data for a physical location. May be used as an actual
 * physical address (place to which one could go), or as a container
 * of "address parts" which should be handled as a unit (such as a city-state-ZIP
 * combination within the US). */
    private com.owd.fedEx.ShipService.Address address;

    public ContactAndAddress() {
    }


    /**
     * Gets the contact value for this ContactAndAddress.
     * 
     * @return contact The descriptive data for a point-of-contact person.
     */
    public com.owd.fedEx.ShipService.Contact getContact() {
        return contact;
    }


    /**
     * Sets the contact value for this ContactAndAddress.
     * 
     * @param contact The descriptive data for a point-of-contact person.
     */
    public void setContact(com.owd.fedEx.ShipService.Contact contact) {
        this.contact = contact;
    }


    /**
     * Gets the address value for this ContactAndAddress.
     * 
     * @return address Descriptive data for a physical location. May be used as an actual
 * physical address (place to which one could go), or as a container
 * of "address parts" which should be handled as a unit (such as a city-state-ZIP
 * combination within the US).
     */
    public com.owd.fedEx.ShipService.Address getAddress() {
        return address;
    }


    /**
     * Sets the address value for this ContactAndAddress.
     * 
     * @param address Descriptive data for a physical location. May be used as an actual
 * physical address (place to which one could go), or as a container
 * of "address parts" which should be handled as a unit (such as a city-state-ZIP
 * combination within the US).
     */
    public void setAddress(com.owd.fedEx.ShipService.Address address) {
        this.address = address;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ContactAndAddress)) return false;
        ContactAndAddress other = (ContactAndAddress) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.contact==null && other.getContact()==null) || 
             (this.contact!=null &&
              this.contact.equals(other.getContact()))) &&
            ((this.address==null && other.getAddress()==null) || 
             (this.address!=null &&
              this.address.equals(other.getAddress())));
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
        if (getContact() != null) {
            _hashCode += getContact().hashCode();
        }
        if (getAddress() != null) {
            _hashCode += getAddress().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ContactAndAddress.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ContactAndAddress"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("contact");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Contact"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Contact"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("address");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Address"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Address"));
        elemField.setMinOccurs(0);
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
