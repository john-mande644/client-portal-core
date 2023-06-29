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
 *         &lt;element ref="{urn:ebay:api:PayPalAPI}GetRecurringPaymentsProfileDetailsRequest"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "getRecurringPaymentsProfileDetailsRequest"
})
@XmlRootElement(name = "GetRecurringPaymentsProfileDetailsReq")
public class GetRecurringPaymentsProfileDetailsReq
{

    @XmlElement(name = "GetRecurringPaymentsProfileDetailsRequest", required = true)
    protected GetRecurringPaymentsProfileDetailsRequestType getRecurringPaymentsProfileDetailsRequest;

    /**
     * Gets the value of the getRecurringPaymentsProfileDetailsRequest property.
     *
     * @return possible object is
     *         {@link GetRecurringPaymentsProfileDetailsRequestType }
     */
    public GetRecurringPaymentsProfileDetailsRequestType getGetRecurringPaymentsProfileDetailsRequest()
    {
        return getRecurringPaymentsProfileDetailsRequest;
    }

    /**
     * Sets the value of the getRecurringPaymentsProfileDetailsRequest property.
     *
     * @param value allowed object is
     *              {@link GetRecurringPaymentsProfileDetailsRequestType }
     */
    public void setGetRecurringPaymentsProfileDetailsRequest(GetRecurringPaymentsProfileDetailsRequestType value)
    {
        this.getRecurringPaymentsProfileDetailsRequest = value;
    }

}
