/**
 * OrderProcessingLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.owd.onestop;

public class OrderProcessingLocator extends org.apache.axis.client.Service implements com.owd.onestop.OrderProcessing {

    public OrderProcessingLocator() {
    }


    public OrderProcessingLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public OrderProcessingLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for BasicHttpBinding_IOrderProcessing
    private java.lang.String BasicHttpBinding_IOrderProcessing_address = "http://www.babeland.com/blnd/api/Services/OrderProcessing.svc/soap";

    public java.lang.String getBasicHttpBinding_IOrderProcessingAddress() {
        return BasicHttpBinding_IOrderProcessing_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String BasicHttpBinding_IOrderProcessingWSDDServiceName = "BasicHttpBinding_IOrderProcessing";

    public java.lang.String getBasicHttpBinding_IOrderProcessingWSDDServiceName() {
        return BasicHttpBinding_IOrderProcessingWSDDServiceName;
    }

    public void setBasicHttpBinding_IOrderProcessingWSDDServiceName(java.lang.String name) {
        BasicHttpBinding_IOrderProcessingWSDDServiceName = name;
    }

    public com.owd.onestop.IOrderProcessing getBasicHttpBinding_IOrderProcessing() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(BasicHttpBinding_IOrderProcessing_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getBasicHttpBinding_IOrderProcessing(endpoint);
    }

    public com.owd.onestop.IOrderProcessing getBasicHttpBinding_IOrderProcessing(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.owd.onestop.BasicHttpBinding_IOrderProcessingStub _stub = new com.owd.onestop.BasicHttpBinding_IOrderProcessingStub(portAddress, this);
            _stub.setPortName(getBasicHttpBinding_IOrderProcessingWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setBasicHttpBinding_IOrderProcessingEndpointAddress(java.lang.String address) {
        BasicHttpBinding_IOrderProcessing_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.owd.onestop.IOrderProcessing.class.isAssignableFrom(serviceEndpointInterface)) {
                com.owd.onestop.BasicHttpBinding_IOrderProcessingStub _stub = new com.owd.onestop.BasicHttpBinding_IOrderProcessingStub(new java.net.URL(BasicHttpBinding_IOrderProcessing_address), this);
                _stub.setPortName(getBasicHttpBinding_IOrderProcessingWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("BasicHttpBinding_IOrderProcessing".equals(inputPortName)) {
            return getBasicHttpBinding_IOrderProcessing();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://tempuri.org/", "OrderProcessing");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://tempuri.org/", "BasicHttpBinding_IOrderProcessing"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("BasicHttpBinding_IOrderProcessing".equals(portName)) {
            setBasicHttpBinding_IOrderProcessingEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
