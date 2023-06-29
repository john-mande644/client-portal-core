/**
 * ProcessPackage.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package GSSMailer;

public interface ProcessPackage extends javax.xml.rpc.Service {

/**
 * GSS Mailer Web Service
 */
    public java.lang.String getProcessPackageSoapAddress();

    public GSSMailer.ProcessPackageSoap_PortType getProcessPackageSoap() throws javax.xml.rpc.ServiceException;

    public GSSMailer.ProcessPackageSoap_PortType getProcessPackageSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
    public java.lang.String getProcessPackageSoap12Address();

    public GSSMailer.ProcessPackageSoap_PortType getProcessPackageSoap12() throws javax.xml.rpc.ServiceException;

    public GSSMailer.ProcessPackageSoap_PortType getProcessPackageSoap12(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
