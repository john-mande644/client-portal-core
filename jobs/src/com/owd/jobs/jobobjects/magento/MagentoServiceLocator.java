/*
 * MagentoServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.jobs.jobobjects.magento;

public class MagentoServiceLocator extends org.apache.axis.client.Service implements MagentoService {


    private java.lang.String Mage_Api_Model_Server_HandlerPort_address;//AV = "http://67.23.36.36/index.php/api/index/index/";

    private MagentoServiceLocator()
    {


    }
    public MagentoServiceLocator(String url)
    {
         Mage_Api_Model_Server_HandlerPort_address = url;
    }

    public java.lang.String getMage_Api_Model_Server_HandlerPortAddress() {
        return Mage_Api_Model_Server_HandlerPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String Mage_Api_Model_Server_HandlerPortWSDDServiceName = "Mage_Api_Model_Server_HandlerPort";

    public java.lang.String getMage_Api_Model_Server_HandlerPortWSDDServiceName() {
        return Mage_Api_Model_Server_HandlerPortWSDDServiceName;
    }

    public void setMage_Api_Model_Server_HandlerPortWSDDServiceName(java.lang.String name) {
        Mage_Api_Model_Server_HandlerPortWSDDServiceName = name;
    }

    public Mage_Api_Model_Server_HandlerPortType getMage_Api_Model_Server_HandlerPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(Mage_Api_Model_Server_HandlerPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getMage_Api_Model_Server_HandlerPort(endpoint);
    }

    public Mage_Api_Model_Server_HandlerPortType getMage_Api_Model_Server_HandlerPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            Mage_Api_Model_Server_HandlerBindingStub _stub = new Mage_Api_Model_Server_HandlerBindingStub(portAddress, this);
            _stub.setPortName(getMage_Api_Model_Server_HandlerPortWSDDServiceName());
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
            if (Mage_Api_Model_Server_HandlerPortType.class.isAssignableFrom(serviceEndpointInterface)) {
                Mage_Api_Model_Server_HandlerBindingStub _stub = new Mage_Api_Model_Server_HandlerBindingStub(new java.net.URL(Mage_Api_Model_Server_HandlerPort_address), this);
                _stub.setPortName(getMage_Api_Model_Server_HandlerPortWSDDServiceName());
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
        if ("Mage_Api_Model_Server_HandlerPort".equals(inputPortName)) {
            return getMage_Api_Model_Server_HandlerPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("urn:Magento", "MagentoService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("Mage_Api_Model_Server_HandlerPort"));
        }
        return ports.iterator();
    }

}
