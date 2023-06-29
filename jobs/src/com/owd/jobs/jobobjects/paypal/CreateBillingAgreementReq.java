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
 *         &lt;element ref="{urn:ebay:api:PayPalAPI}CreateBillingAgreementRequest"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "createBillingAgreementRequest"
})
@XmlRootElement(name = "CreateBillingAgreementReq")
public class CreateBillingAgreementReq
{

    @XmlElement(name = "CreateBillingAgreementRequest", required = true)
    protected CreateBillingAgreementRequestType createBillingAgreementRequest;

    /**
     * Gets the value of the createBillingAgreementRequest property.
     *
     * @return possible object is
     *         {@link CreateBillingAgreementRequestType }
     */
    public CreateBillingAgreementRequestType getCreateBillingAgreementRequest()
    {
        return createBillingAgreementRequest;
    }

    /**
     * Sets the value of the createBillingAgreementRequest property.
     *
     * @param value allowed object is
     *              {@link CreateBillingAgreementRequestType }
     */
    public void setCreateBillingAgreementRequest(CreateBillingAgreementRequestType value)
    {
        this.createBillingAgreementRequest = value;
    }

}
