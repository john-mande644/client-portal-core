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
 *         &lt;element ref="{urn:ebay:api:PayPalAPI}DoExpressCheckoutPaymentRequest"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "doExpressCheckoutPaymentRequest"
})
@XmlRootElement(name = "DoExpressCheckoutPaymentReq")
public class DoExpressCheckoutPaymentReq
{

    @XmlElement(name = "DoExpressCheckoutPaymentRequest", required = true)
    protected DoExpressCheckoutPaymentRequestType doExpressCheckoutPaymentRequest;

    /**
     * Gets the value of the doExpressCheckoutPaymentRequest property.
     *
     * @return possible object is
     *         {@link DoExpressCheckoutPaymentRequestType }
     */
    public DoExpressCheckoutPaymentRequestType getDoExpressCheckoutPaymentRequest()
    {
        return doExpressCheckoutPaymentRequest;
    }

    /**
     * Sets the value of the doExpressCheckoutPaymentRequest property.
     *
     * @param value allowed object is
     *              {@link DoExpressCheckoutPaymentRequestType }
     */
    public void setDoExpressCheckoutPaymentRequest(DoExpressCheckoutPaymentRequestType value)
    {
        this.doExpressCheckoutPaymentRequest = value;
    }

}
