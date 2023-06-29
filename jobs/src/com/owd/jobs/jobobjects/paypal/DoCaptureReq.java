package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.*;


/**
 * <p>Java class for anonymous complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element ref="{urn:ebay:api:PayPalAPI}DoCaptureRequest"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "doCaptureRequest"
})
@XmlRootElement(name = "DoCaptureReq")
public class DoCaptureReq
{

    @XmlElement(name = "DoCaptureRequest", required = true)
    protected DoCaptureRequestType doCaptureRequest;

    /**
     * Gets the value of the doCaptureRequest property.
     *
     * @return possible object is
     *         {@link DoCaptureRequestType }
     */
    public DoCaptureRequestType getDoCaptureRequest()
    {
        return doCaptureRequest;
    }

    /**
     * Sets the value of the doCaptureRequest property.
     *
     * @param value allowed object is
     *              {@link DoCaptureRequestType }
     */
    public void setDoCaptureRequest(DoCaptureRequestType value)
    {
        this.doCaptureRequest = value;
    }

}
