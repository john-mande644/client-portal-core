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
 *         &lt;element ref="{urn:ebay:api:PayPalAPI}GetBillingAgreementCustomerDetailsRequest"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "getBillingAgreementCustomerDetailsRequest"
})
@XmlRootElement(name = "GetBillingAgreementCustomerDetailsReq")
public class GetBillingAgreementCustomerDetailsReq
{

    @XmlElement(name = "GetBillingAgreementCustomerDetailsRequest", required = true)
    protected GetBillingAgreementCustomerDetailsRequestType getBillingAgreementCustomerDetailsRequest;

    /**
     * Gets the value of the getBillingAgreementCustomerDetailsRequest property.
     *
     * @return possible object is
     *         {@link GetBillingAgreementCustomerDetailsRequestType }
     */
    public GetBillingAgreementCustomerDetailsRequestType getGetBillingAgreementCustomerDetailsRequest()
    {
        return getBillingAgreementCustomerDetailsRequest;
    }

    /**
     * Sets the value of the getBillingAgreementCustomerDetailsRequest property.
     *
     * @param value allowed object is
     *              {@link GetBillingAgreementCustomerDetailsRequestType }
     */
    public void setGetBillingAgreementCustomerDetailsRequest(GetBillingAgreementCustomerDetailsRequestType value)
    {
        this.getBillingAgreementCustomerDetailsRequest = value;
    }

}
