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
 *         &lt;element ref="{urn:ebay:api:PayPalAPI}CreateRecurringPaymentsProfileRequest"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "createRecurringPaymentsProfileRequest"
})
@XmlRootElement(name = "CreateRecurringPaymentsProfileReq")
public class CreateRecurringPaymentsProfileReq
{

    @XmlElement(name = "CreateRecurringPaymentsProfileRequest", required = true)
    protected CreateRecurringPaymentsProfileRequestType createRecurringPaymentsProfileRequest;

    /**
     * Gets the value of the createRecurringPaymentsProfileRequest property.
     *
     * @return possible object is
     *         {@link CreateRecurringPaymentsProfileRequestType }
     */
    public CreateRecurringPaymentsProfileRequestType getCreateRecurringPaymentsProfileRequest()
    {
        return createRecurringPaymentsProfileRequest;
    }

    /**
     * Sets the value of the createRecurringPaymentsProfileRequest property.
     *
     * @param value allowed object is
     *              {@link CreateRecurringPaymentsProfileRequestType }
     */
    public void setCreateRecurringPaymentsProfileRequest(CreateRecurringPaymentsProfileRequestType value)
    {
        this.createRecurringPaymentsProfileRequest = value;
    }

}
