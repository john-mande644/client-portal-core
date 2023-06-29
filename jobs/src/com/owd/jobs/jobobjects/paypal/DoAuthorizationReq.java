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
 *         &lt;element ref="{urn:ebay:api:PayPalAPI}DoAuthorizationRequest"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "doAuthorizationRequest"
})
@XmlRootElement(name = "DoAuthorizationReq")
public class DoAuthorizationReq
{

    @XmlElement(name = "DoAuthorizationRequest", required = true)
    protected DoAuthorizationRequestType doAuthorizationRequest;

    /**
     * Gets the value of the doAuthorizationRequest property.
     *
     * @return possible object is
     *         {@link DoAuthorizationRequestType }
     */
    public DoAuthorizationRequestType getDoAuthorizationRequest()
    {
        return doAuthorizationRequest;
    }

    /**
     * Sets the value of the doAuthorizationRequest property.
     *
     * @param value allowed object is
     *              {@link DoAuthorizationRequestType }
     */
    public void setDoAuthorizationRequest(DoAuthorizationRequestType value)
    {
        this.doAuthorizationRequest = value;
    }

}
