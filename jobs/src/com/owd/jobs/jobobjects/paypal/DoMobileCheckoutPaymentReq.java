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
 *         &lt;element ref="{urn:ebay:api:PayPalAPI}DoMobileCheckoutPaymentRequest"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "doMobileCheckoutPaymentRequest"
})
@XmlRootElement(name = "DoMobileCheckoutPaymentReq")
public class DoMobileCheckoutPaymentReq
{

    @XmlElement(name = "DoMobileCheckoutPaymentRequest", required = true)
    protected DoMobileCheckoutPaymentRequestType doMobileCheckoutPaymentRequest;

    /**
     * Gets the value of the doMobileCheckoutPaymentRequest property.
     *
     * @return possible object is
     *         {@link DoMobileCheckoutPaymentRequestType }
     */
    public DoMobileCheckoutPaymentRequestType getDoMobileCheckoutPaymentRequest()
    {
        return doMobileCheckoutPaymentRequest;
    }

    /**
     * Sets the value of the doMobileCheckoutPaymentRequest property.
     *
     * @param value allowed object is
     *              {@link DoMobileCheckoutPaymentRequestType }
     */
    public void setDoMobileCheckoutPaymentRequest(DoMobileCheckoutPaymentRequestType value)
    {
        this.doMobileCheckoutPaymentRequest = value;
    }

}
