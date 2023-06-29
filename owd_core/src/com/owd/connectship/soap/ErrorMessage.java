
package com.owd.connectship.soap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.ws.WebFault;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.6 in JDK 6
 * Generated source version: 2.1
 * 
 */
@WebFault(name = "errorResponse", targetNamespace = "urn:connectship-com:ampcore")
public class ErrorMessage
    extends Exception
{
private final static Logger log =  LogManager.getLogger();

    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private ErrorResponse faultInfo;

    /**
     * 
     * @param message
     * @param faultInfo
     */
    public ErrorMessage(String message, ErrorResponse faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param message
     * @param faultInfo
     * @param cause
     */
    public ErrorMessage(String message, ErrorResponse faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: com.owd.connectship.soap.ErrorResponse
     */
    public ErrorResponse getFaultInfo() {
        return faultInfo;
    }

}
