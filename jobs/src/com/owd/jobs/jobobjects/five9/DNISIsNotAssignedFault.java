
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
@WebFault(name = "DNISIsNotAssignedFault", targetNamespace = "http://service.admin.ws.five9.com/v2/")
public class DNISIsNotAssignedFault
    extends Exception
{
private final static Logger log =  LogManager.getLogger();

    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private DNISIsNotAssignedException faultInfo;

    /**
     * 
     * @param message
     * @param faultInfo
     */
    public DNISIsNotAssignedFault(String message, DNISIsNotAssignedException faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param message
     * @param faultInfo
     * @param cause
     */
    public DNISIsNotAssignedFault(String message, DNISIsNotAssignedException faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: com.owd.jobs.jobobjects.five9.DNISIsNotAssignedException
     */
    public DNISIsNotAssignedException getFaultInfo() {
        return faultInfo;
    }

}
