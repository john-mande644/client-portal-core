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
 *         &lt;element ref="{urn:ebay:api:PayPalAPI}GetIncentiveEvaluationRequest"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "getIncentiveEvaluationRequest"
})
@XmlRootElement(name = "GetIncentiveEvaluationReq")
public class GetIncentiveEvaluationReq
{

    @XmlElement(name = "GetIncentiveEvaluationRequest", required = true)
    protected GetIncentiveEvaluationRequestType getIncentiveEvaluationRequest;

    /**
     * Gets the value of the getIncentiveEvaluationRequest property.
     *
     * @return possible object is
     *         {@link GetIncentiveEvaluationRequestType }
     */
    public GetIncentiveEvaluationRequestType getGetIncentiveEvaluationRequest()
    {
        return getIncentiveEvaluationRequest;
    }

    /**
     * Sets the value of the getIncentiveEvaluationRequest property.
     *
     * @param value allowed object is
     *              {@link GetIncentiveEvaluationRequestType }
     */
    public void setGetIncentiveEvaluationRequest(GetIncentiveEvaluationRequestType value)
    {
        this.getIncentiveEvaluationRequest = value;
    }

}
