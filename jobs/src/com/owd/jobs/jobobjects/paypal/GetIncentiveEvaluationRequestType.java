package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GetIncentiveEvaluationRequestType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="GetIncentiveEvaluationRequestType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:ebay:apis:eBLBaseComponents}AbstractRequestType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element ref="{urn:ebay:apis:eBLBaseComponents}GetIncentiveEvaluationRequestDetails"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetIncentiveEvaluationRequestType", propOrder = {
        "getIncentiveEvaluationRequestDetails"
})
public class GetIncentiveEvaluationRequestType
        extends AbstractRequestType
{

    @XmlElement(name = "GetIncentiveEvaluationRequestDetails", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected GetIncentiveEvaluationRequestDetailsType getIncentiveEvaluationRequestDetails;

    /**
     * Gets the value of the getIncentiveEvaluationRequestDetails property.
     *
     * @return possible object is
     *         {@link GetIncentiveEvaluationRequestDetailsType }
     */
    public GetIncentiveEvaluationRequestDetailsType getGetIncentiveEvaluationRequestDetails()
    {
        return getIncentiveEvaluationRequestDetails;
    }

    /**
     * Sets the value of the getIncentiveEvaluationRequestDetails property.
     *
     * @param value allowed object is
     *              {@link GetIncentiveEvaluationRequestDetailsType }
     */
    public void setGetIncentiveEvaluationRequestDetails(GetIncentiveEvaluationRequestDetailsType value)
    {
        this.getIncentiveEvaluationRequestDetails = value;
    }

}
