
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.7-b01 
 * Generated source version: 2.1
 * 
 */
@WebServiceClient(name = "WsAdminServiceV2", targetNamespace = "http://service.admin.ws.five9.com/v2/", wsdlLocation = "https://api.five9.com/wsadmin/v2/AdminWebService?wsdl&user=One%20World%20Direct")
public class WsAdminServiceV2
    extends Service
{
private final static Logger log =  LogManager.getLogger();

    private final static URL WSADMINSERVICEV2_WSDL_LOCATION;
    private final static WebServiceException WSADMINSERVICEV2_EXCEPTION;
    private final static QName WSADMINSERVICEV2_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "WsAdminServiceV2");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("https://api.five9.com/wsadmin/v2/AdminWebService?wsdl&user=One%20World%20Direct");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        WSADMINSERVICEV2_WSDL_LOCATION = url;
        WSADMINSERVICEV2_EXCEPTION = e;
    }

    public WsAdminServiceV2() {
        super(__getWsdlLocation(), WSADMINSERVICEV2_QNAME);
    }

    public WsAdminServiceV2(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    /**
     * 
     * @return
     *     returns WsAdminV2
     */
    @WebEndpoint(name = "WsAdminPortV2")
    public WsAdminV2 getWsAdminPortV2() {
        return super.getPort(new QName("http://service.admin.ws.five9.com/v2/", "WsAdminPortV2"), WsAdminV2.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns WsAdminV2
     */
    @WebEndpoint(name = "WsAdminPortV2")
    public WsAdminV2 getWsAdminPortV2(WebServiceFeature... features) {
        return super.getPort(new QName("http://service.admin.ws.five9.com/v2/", "WsAdminPortV2"), WsAdminV2.class, features);
    }

    private static URL __getWsdlLocation() {
        if (WSADMINSERVICEV2_EXCEPTION!= null) {
            throw WSADMINSERVICEV2_EXCEPTION;
        }
        return WSADMINSERVICEV2_WSDL_LOCATION;
    }

}
