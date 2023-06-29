package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for GetIncentiveEvaluationResponseDetailsType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="GetIncentiveEvaluationResponseDetailsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="IncentiveDetails" type="{urn:ebay:apis:eBLBaseComponents}IncentiveDetailType" maxOccurs="1000" minOccurs="0"/>
 *         &lt;element name="RequestId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetIncentiveEvaluationResponseDetailsType", namespace = "urn:ebay:apis:eBLBaseComponents", propOrder = {
        "incentiveDetails",
        "requestId"
})
public class GetIncentiveEvaluationResponseDetailsType
{

    @XmlElement(name = "IncentiveDetails", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected List<IncentiveDetailType> incentiveDetails;
    @XmlElement(name = "RequestId", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String requestId;

    /**
     * Gets the value of the incentiveDetails property.
     * <p/>
     * <p/>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the incentiveDetails property.
     * <p/>
     * <p/>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIncentiveDetails().add(newItem);
     * </pre>
     * <p/>
     * <p/>
     * <p/>
     * Objects of the following type(s) are allowed in the list
     * {@link IncentiveDetailType }
     */
    public List<IncentiveDetailType> getIncentiveDetails()
    {
        if (incentiveDetails == null)
        {
            incentiveDetails = new ArrayList<IncentiveDetailType>();
        }
        return this.incentiveDetails;
    }

    /**
     * Gets the value of the requestId property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getRequestId()
    {
        return requestId;
    }

    /**
     * Sets the value of the requestId property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setRequestId(String value)
    {
        this.requestId = value;
    }

}
