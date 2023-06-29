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
 *         &lt;element ref="{urn:ebay:api:PayPalAPI}SetCustomerBillingAgreementRequest"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "setCustomerBillingAgreementRequest"
})
@XmlRootElement(name = "SetCustomerBillingAgreementReq")
public class SetCustomerBillingAgreementReq
{

    @XmlElement(name = "SetCustomerBillingAgreementRequest", required = true)
    protected SetCustomerBillingAgreementRequestType setCustomerBillingAgreementRequest;

    /**
     * Gets the value of the setCustomerBillingAgreementRequest property.
     *
     * @return possible object is
     *         {@link SetCustomerBillingAgreementRequestType }
     */
    public SetCustomerBillingAgreementRequestType getSetCustomerBillingAgreementRequest()
    {
        return setCustomerBillingAgreementRequest;
    }

    /**
     * Sets the value of the setCustomerBillingAgreementRequest property.
     *
     * @param value allowed object is
     *              {@link SetCustomerBillingAgreementRequestType }
     */
    public void setSetCustomerBillingAgreementRequest(SetCustomerBillingAgreementRequestType value)
    {
        this.setCustomerBillingAgreementRequest = value;
    }

}
