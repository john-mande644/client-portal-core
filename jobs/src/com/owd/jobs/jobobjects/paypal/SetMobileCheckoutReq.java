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
 *         &lt;element ref="{urn:ebay:api:PayPalAPI}SetMobileCheckoutRequest"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "setMobileCheckoutRequest"
})
@XmlRootElement(name = "SetMobileCheckoutReq")
public class SetMobileCheckoutReq
{

    @XmlElement(name = "SetMobileCheckoutRequest", required = true)
    protected SetMobileCheckoutRequestType setMobileCheckoutRequest;

    /**
     * Gets the value of the setMobileCheckoutRequest property.
     *
     * @return possible object is
     *         {@link SetMobileCheckoutRequestType }
     */
    public SetMobileCheckoutRequestType getSetMobileCheckoutRequest()
    {
        return setMobileCheckoutRequest;
    }

    /**
     * Sets the value of the setMobileCheckoutRequest property.
     *
     * @param value allowed object is
     *              {@link SetMobileCheckoutRequestType }
     */
    public void setSetMobileCheckoutRequest(SetMobileCheckoutRequestType value)
    {
        this.setMobileCheckoutRequest = value;
    }

}
