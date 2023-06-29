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
 *         &lt;element ref="{urn:ebay:api:PayPalAPI}GetExpressCheckoutDetailsRequest"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "getExpressCheckoutDetailsRequest"
})
@XmlRootElement(name = "GetExpressCheckoutDetailsReq")
public class GetExpressCheckoutDetailsReq
{

    @XmlElement(name = "GetExpressCheckoutDetailsRequest", required = true)
    protected GetExpressCheckoutDetailsRequestType getExpressCheckoutDetailsRequest;

    /**
     * Gets the value of the getExpressCheckoutDetailsRequest property.
     *
     * @return possible object is
     *         {@link GetExpressCheckoutDetailsRequestType }
     */
    public GetExpressCheckoutDetailsRequestType getGetExpressCheckoutDetailsRequest()
    {
        return getExpressCheckoutDetailsRequest;
    }

    /**
     * Sets the value of the getExpressCheckoutDetailsRequest property.
     *
     * @param value allowed object is
     *              {@link GetExpressCheckoutDetailsRequestType }
     */
    public void setGetExpressCheckoutDetailsRequest(GetExpressCheckoutDetailsRequestType value)
    {
        this.getExpressCheckoutDetailsRequest = value;
    }

}
