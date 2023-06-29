package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;


/**
 * Information about the incentives that were applied from Ebay RYP page and PayPal RYP page.
 * <p/>
 * <p/>
 * <p>Java class for IncentiveDetailsType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="IncentiveDetailsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="UniqueIdentifier" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SiteAppliedOn" type="{urn:ebay:apis:eBLBaseComponents}IncentiveSiteAppliedOnType" minOccurs="0"/>
 *         &lt;element name="TotalDiscountAmount" type="{urn:ebay:apis:CoreComponentTypes}BasicAmountType" minOccurs="0"/>
 *         &lt;element name="Status" type="{urn:ebay:apis:eBLBaseComponents}IncentiveAppliedStatusType" minOccurs="0"/>
 *         &lt;element name="ErrorCode" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="IncentiveAppliedDetails" type="{urn:ebay:apis:eBLBaseComponents}IncentiveAppliedDetailsType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IncentiveDetailsType", namespace = "urn:ebay:apis:eBLBaseComponents", propOrder = {
        "uniqueIdentifier",
        "siteAppliedOn",
        "totalDiscountAmount",
        "status",
        "errorCode",
        "incentiveAppliedDetails"
})
public class IncentiveDetailsType
{

    @XmlElement(name = "UniqueIdentifier", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String uniqueIdentifier;
    @XmlElement(name = "SiteAppliedOn", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected IncentiveSiteAppliedOnType siteAppliedOn;
    @XmlElement(name = "TotalDiscountAmount", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected BasicAmountType totalDiscountAmount;
    @XmlElement(name = "Status", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected IncentiveAppliedStatusType status;
    @XmlElement(name = "ErrorCode", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected BigInteger errorCode;
    @XmlElement(name = "IncentiveAppliedDetails", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected List<IncentiveAppliedDetailsType> incentiveAppliedDetails;

    /**
     * Gets the value of the uniqueIdentifier property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getUniqueIdentifier()
    {
        return uniqueIdentifier;
    }

    /**
     * Sets the value of the uniqueIdentifier property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setUniqueIdentifier(String value)
    {
        this.uniqueIdentifier = value;
    }

    /**
     * Gets the value of the siteAppliedOn property.
     *
     * @return possible object is
     *         {@link IncentiveSiteAppliedOnType }
     */
    public IncentiveSiteAppliedOnType getSiteAppliedOn()
    {
        return siteAppliedOn;
    }

    /**
     * Sets the value of the siteAppliedOn property.
     *
     * @param value allowed object is
     *              {@link IncentiveSiteAppliedOnType }
     */
    public void setSiteAppliedOn(IncentiveSiteAppliedOnType value)
    {
        this.siteAppliedOn = value;
    }

    /**
     * Gets the value of the totalDiscountAmount property.
     *
     * @return possible object is
     *         {@link BasicAmountType }
     */
    public BasicAmountType getTotalDiscountAmount()
    {
        return totalDiscountAmount;
    }

    /**
     * Sets the value of the totalDiscountAmount property.
     *
     * @param value allowed object is
     *              {@link BasicAmountType }
     */
    public void setTotalDiscountAmount(BasicAmountType value)
    {
        this.totalDiscountAmount = value;
    }

    /**
     * Gets the value of the status property.
     *
     * @return possible object is
     *         {@link IncentiveAppliedStatusType }
     */
    public IncentiveAppliedStatusType getStatus()
    {
        return status;
    }

    /**
     * Sets the value of the status property.
     *
     * @param value allowed object is
     *              {@link IncentiveAppliedStatusType }
     */
    public void setStatus(IncentiveAppliedStatusType value)
    {
        this.status = value;
    }

    /**
     * Gets the value of the errorCode property.
     *
     * @return possible object is
     *         {@link BigInteger }
     */
    public BigInteger getErrorCode()
    {
        return errorCode;
    }

    /**
     * Sets the value of the errorCode property.
     *
     * @param value allowed object is
     *              {@link BigInteger }
     */
    public void setErrorCode(BigInteger value)
    {
        this.errorCode = value;
    }

    /**
     * Gets the value of the incentiveAppliedDetails property.
     * <p/>
     * <p/>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the incentiveAppliedDetails property.
     * <p/>
     * <p/>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIncentiveAppliedDetails().add(newItem);
     * </pre>
     * <p/>
     * <p/>
     * <p/>
     * Objects of the following type(s) are allowed in the list
     * {@link IncentiveAppliedDetailsType }
     */
    public List<IncentiveAppliedDetailsType> getIncentiveAppliedDetails()
    {
        if (incentiveAppliedDetails == null)
        {
            incentiveAppliedDetails = new ArrayList<IncentiveAppliedDetailsType>();
        }
        return this.incentiveAppliedDetails;
    }

}
