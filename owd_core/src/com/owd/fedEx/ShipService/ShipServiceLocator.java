/*
 * ShipServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;

public class ShipServiceLocator extends org.apache.axis.client.Service implements com.owd.fedEx.ShipService.ShipService {

    // Use to get a proxy class for ShipServicePort
    private final java.lang.String ShipServicePort_address = "https://ws.fedex.com:443/web-services/ship";

    public java.lang.String getShipServicePortAddress() {
        return ShipServicePort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ShipServicePortWSDDServiceName = "ShipServicePort";

    public java.lang.String getShipServicePortWSDDServiceName() {
        return ShipServicePortWSDDServiceName;
    }

    public void setShipServicePortWSDDServiceName(java.lang.String name) {
        ShipServicePortWSDDServiceName = name;
    }

    public com.owd.fedEx.ShipService.ShipPortType getShipServicePort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ShipServicePort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getShipServicePort(endpoint);
    }

    public com.owd.fedEx.ShipService.ShipPortType getShipServicePort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.owd.fedEx.ShipService.ShipServiceSoapBindingStub _stub = new com.owd.fedEx.ShipService.ShipServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getShipServicePortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.owd.fedEx.ShipService.ShipPortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.owd.fedEx.ShipService.ShipServiceSoapBindingStub _stub = new com.owd.fedEx.ShipService.ShipServiceSoapBindingStub(new java.net.URL(ShipServicePort_address), this);
                _stub.setPortName(getShipServicePortWSDDServiceName());
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
        String inputPortName = portName.getLocalPart();
        if ("ShipServicePort".equals(inputPortName)) {
            return getShipServicePort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ShipService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("ShipServicePort"));
        }
        return ports.iterator();
    }

}
