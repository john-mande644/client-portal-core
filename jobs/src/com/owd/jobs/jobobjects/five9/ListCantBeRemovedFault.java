
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.ws.WebFault;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.7-b01 
 * Generated source version: 2.1
 * 
 */
@WebFault(name = "ListCantBeRemovedFault", targetNamespace = "http://service.admin.ws.five9.com/v2/")
public class ListCantBeRemovedFault
    extends Exception
{
private final static Logger log =  LogManager.getLogger();

    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private ListCantBeRemovedException faultInfo;

    /**
     * 
     * @param message
     * @param faultInfo
     */
    public ListCantBeRemovedFault(String message, ListCantBeRemovedException faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param message
     * @param faultInfo
     * @param cause
     */
    public ListCantBeRemovedFault(String message, ListCantBeRemovedException faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: com.owd.jobs.jobobjects.five9.ListCantBeRemovedException
     */
    public ListCantBeRemovedException getFaultInfo() {
        return faultInfo;
    }

}
