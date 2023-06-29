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
 *         &lt;element ref="{urn:ebay:api:PayPalAPI}SetExpressCheckoutRequest"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "setExpressCheckoutRequest"
})
@XmlRootElement(name = "SetExpressCheckoutReq")
public class SetExpressCheckoutReq
{

    @XmlElement(name = "SetExpressCheckoutRequest", required = true)
    protected SetExpressCheckoutRequestType setExpressCheckoutRequest;

    /**
     * Gets the value of the setExpressCheckoutRequest property.
     *
     * @return possible object is
     *         {@link SetExpressCheckoutRequestType }
     */
    public SetExpressCheckoutRequestType getSetExpressCheckoutRequest()
    {
        return setExpressCheckoutRequest;
    }

    /**
     * Sets the value of the setExpressCheckoutRequest property.
     *
     * @param value allowed object is
     *              {@link SetExpressCheckoutRequestType }
     */
    public void setSetExpressCheckoutRequest(SetExpressCheckoutRequestType value)
    {
        this.setExpressCheckoutRequest = value;
    }

}
