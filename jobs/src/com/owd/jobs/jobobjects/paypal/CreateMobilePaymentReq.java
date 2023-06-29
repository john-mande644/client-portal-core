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
 *         &lt;element ref="{urn:ebay:api:PayPalAPI}CreateMobilePaymentRequest"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "createMobilePaymentRequest"
})
@XmlRootElement(name = "CreateMobilePaymentReq")
public class CreateMobilePaymentReq
{

    @XmlElement(name = "CreateMobilePaymentRequest", required = true)
    protected CreateMobilePaymentRequestType createMobilePaymentRequest;

    /**
     * Gets the value of the createMobilePaymentRequest property.
     *
     * @return possible object is
     *         {@link CreateMobilePaymentRequestType }
     */
    public CreateMobilePaymentRequestType getCreateMobilePaymentRequest()
    {
        return createMobilePaymentRequest;
    }

    /**
     * Sets the value of the createMobilePaymentRequest property.
     *
     * @param value allowed object is
     *              {@link CreateMobilePaymentRequestType }
     */
    public void setCreateMobilePaymentRequest(CreateMobilePaymentRequestType value)
    {
        this.createMobilePaymentRequest = value;
    }

}
