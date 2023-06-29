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
 *         &lt;element ref="{urn:ebay:api:PayPalAPI}DoReauthorizationRequest"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "doReauthorizationRequest"
})
@XmlRootElement(name = "DoReauthorizationReq")
public class DoReauthorizationReq
{

    @XmlElement(name = "DoReauthorizationRequest", required = true)
    protected DoReauthorizationRequestType doReauthorizationRequest;

    /**
     * Gets the value of the doReauthorizationRequest property.
     *
     * @return possible object is
     *         {@link DoReauthorizationRequestType }
     */
    public DoReauthorizationRequestType getDoReauthorizationRequest()
    {
        return doReauthorizationRequest;
    }

    /**
     * Sets the value of the doReauthorizationRequest property.
     *
     * @param value allowed object is
     *              {@link DoReauthorizationRequestType }
     */
    public void setDoReauthorizationRequest(DoReauthorizationRequestType value)
    {
        this.doReauthorizationRequest = value;
    }

}
