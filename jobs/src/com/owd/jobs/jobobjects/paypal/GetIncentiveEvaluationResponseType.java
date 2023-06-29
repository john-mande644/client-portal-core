package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GetIncentiveEvaluationResponseType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="GetIncentiveEvaluationResponseType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:ebay:apis:eBLBaseComponents}AbstractResponseType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element ref="{urn:ebay:apis:eBLBaseComponents}GetIncentiveEvaluationResponseDetails"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetIncentiveEvaluationResponseType", propOrder = {
        "getIncentiveEvaluationResponseDetails"
})
public class GetIncentiveEvaluationResponseType
        extends AbstractResponseType
{

    @XmlElement(name = "GetIncentiveEvaluationResponseDetails", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected GetIncentiveEvaluationResponseDetailsType getIncentiveEvaluationResponseDetails;

    /**
     * Gets the value of the getIncentiveEvaluationResponseDetails property.
     *
     * @return possible object is
     *         {@link GetIncentiveEvaluationResponseDetailsType }
     */
    public GetIncentiveEvaluationResponseDetailsType getGetIncentiveEvaluationResponseDetails()
    {
        return getIncentiveEvaluationResponseDetails;
    }

    /**
     * Sets the value of the getIncentiveEvaluationResponseDetails property.
     *
     * @param value allowed object is
     *              {@link GetIncentiveEvaluationResponseDetailsType }
     */
    public void setGetIncentiveEvaluationResponseDetails(GetIncentiveEvaluationResponseDetailsType value)
    {
        this.getIncentiveEvaluationResponseDetails = value;
    }

}
