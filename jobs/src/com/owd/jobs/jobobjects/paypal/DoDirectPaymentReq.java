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
 *         &lt;element ref="{urn:ebay:api:PayPalAPI}DoDirectPaymentRequest"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "doDirectPaymentRequest"
})
@XmlRootElement(name = "DoDirectPaymentReq")
public class DoDirectPaymentReq
{

    @XmlElement(name = "DoDirectPaymentRequest", required = true)
    protected DoDirectPaymentRequestType doDirectPaymentRequest;

    /**
     * Gets the value of the doDirectPaymentRequest property.
     *
     * @return possible object is
     *         {@link DoDirectPaymentRequestType }
     */
    public DoDirectPaymentRequestType getDoDirectPaymentRequest()
    {
        return doDirectPaymentRequest;
    }

    /**
     * Sets the value of the doDirectPaymentRequest property.
     *
     * @param value allowed object is
     *              {@link DoDirectPaymentRequestType }
     */
    public void setDoDirectPaymentRequest(DoDirectPaymentRequestType value)
    {
        this.doDirectPaymentRequest = value;
    }

}
