
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
@WebFault(name = "InvalidRegexpPatternFault", targetNamespace = "http://service.admin.ws.five9.com/v2/")
public class InvalidRegexpPatternFault
    extends Exception
{
private final static Logger log =  LogManager.getLogger();

    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private InvalidRegexpPatternException faultInfo;

    /**
     * 
     * @param message
     * @param faultInfo
     */
    public InvalidRegexpPatternFault(String message, InvalidRegexpPatternException faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param message
     * @param faultInfo
     * @param cause
     */
    public InvalidRegexpPatternFault(String message, InvalidRegexpPatternException faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: com.owd.jobs.jobobjects.five9.InvalidRegexpPatternException
     */
    public InvalidRegexpPatternException getFaultInfo() {
        return faultInfo;
    }

}
