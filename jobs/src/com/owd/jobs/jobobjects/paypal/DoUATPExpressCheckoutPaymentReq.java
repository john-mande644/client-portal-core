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
 *         &lt;element ref="{urn:ebay:api:PayPalAPI}DoUATPExpressCheckoutPaymentRequest"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "doUATPExpressCheckoutPaymentRequest"
})
@XmlRootElement(name = "DoUATPExpressCheckoutPaymentReq")
public class DoUATPExpressCheckoutPaymentReq
{

    @XmlElement(name = "DoUATPExpressCheckoutPaymentRequest", required = true)
    protected DoUATPExpressCheckoutPaymentRequestType doUATPExpressCheckoutPaymentRequest;

    /**
     * Gets the value of the doUATPExpressCheckoutPaymentRequest property.
     *
     * @return possible object is
     *         {@link DoUATPExpressCheckoutPaymentRequestType }
     */
    public DoUATPExpressCheckoutPaymentRequestType getDoUATPExpressCheckoutPaymentRequest()
    {
        return doUATPExpressCheckoutPaymentRequest;
    }

    /**
     * Sets the value of the doUATPExpressCheckoutPaymentRequest property.
     *
     * @param value allowed object is
     *              {@link DoUATPExpressCheckoutPaymentRequestType }
     */
    public void setDoUATPExpressCheckoutPaymentRequest(DoUATPExpressCheckoutPaymentRequestType value)
    {
        this.doUATPExpressCheckoutPaymentRequest = value;
    }

}
