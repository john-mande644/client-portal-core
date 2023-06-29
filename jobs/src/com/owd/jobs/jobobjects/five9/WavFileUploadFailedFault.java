
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
@WebFault(name = "WavFileUploadFailedFault", targetNamespace = "http://service.admin.ws.five9.com/v2/")
public class WavFileUploadFailedFault
    extends Exception
{
private final static Logger log =  LogManager.getLogger();

    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private WavFileUploadFailedException faultInfo;

    /**
     * 
     * @param message
     * @param faultInfo
     */
    public WavFileUploadFailedFault(String message, WavFileUploadFailedException faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param message
     * @param faultInfo
     * @param cause
     */
    public WavFileUploadFailedFault(String message, WavFileUploadFailedException faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: com.owd.jobs.jobobjects.five9.WavFileUploadFailedException
     */
    public WavFileUploadFailedException getFaultInfo() {
        return faultInfo;
    }

}
