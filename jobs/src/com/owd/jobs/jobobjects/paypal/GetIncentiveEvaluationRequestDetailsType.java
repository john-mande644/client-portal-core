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
 * <p>Java class for GetIncentiveEvaluationRequestDetailsType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="GetIncentiveEvaluationRequestDetailsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="ExternalBuyerId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="IncentiveCodes" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="1000" minOccurs="0"/>
 *         &lt;element name="ApplyIndication" type="{urn:ebay:apis:eBLBaseComponents}IncentiveApplyIndicationType" maxOccurs="1000" minOccurs="0"/>
 *         &lt;element name="Buckets" type="{urn:ebay:apis:eBLBaseComponents}IncentiveBucketType" maxOccurs="100" minOccurs="0"/>
 *         &lt;element name="CartTotalAmt" type="{urn:ebay:apis:CoreComponentTypes}BasicAmountType" minOccurs="0"/>
 *         &lt;element name="RequestDetails" type="{urn:ebay:apis:eBLBaseComponents}IncentiveRequestDetailsType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetIncentiveEvaluationRequestDetailsType", namespace = "urn:ebay:apis:eBLBaseComponents", propOrder = {
        "externalBuyerId",
        "incentiveCodes",
        "applyIndication",
        "buckets",
        "cartTotalAmt",
        "requestDetails"
})
public class GetIncentiveEvaluationRequestDetailsType
{

    @XmlElement(name = "ExternalBuyerId", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String externalBuyerId;
    @XmlElement(name = "IncentiveCodes", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected List<String> incentiveCodes;
    @XmlElement(name = "ApplyIndication", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected List<IncentiveApplyIndicationType> applyIndication;
    @XmlElement(name = "Buckets", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected List<IncentiveBucketType> buckets;
    @XmlElement(name = "CartTotalAmt", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected BasicAmountType cartTotalAmt;
    @XmlElement(name = "RequestDetails", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected IncentiveRequestDetailsType requestDetails;

    /**
     * Gets the value of the externalBuyerId property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getExternalBuyerId()
    {
        return externalBuyerId;
    }

    /**
     * Sets the value of the externalBuyerId property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setExternalBuyerId(String value)
    {
        this.externalBuyerId = value;
    }

    /**
     * Gets the value of the incentiveCodes property.
     * <p/>
     * <p/>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the incentiveCodes property.
     * <p/>
     * <p/>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIncentiveCodes().add(newItem);
     * </pre>
     * <p/>
     * <p/>
     * <p/>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     */
    public List<String> getIncentiveCodes()
    {
        if (incentiveCodes == null)
        {
            incentiveCodes = new ArrayList<String>();
        }
        return this.incentiveCodes;
    }

    /**
     * Gets the value of the applyIndication property.
     * <p/>
     * <p/>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the applyIndication property.
     * <p/>
     * <p/>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getApplyIndication().add(newItem);
     * </pre>
     * <p/>
     * <p/>
     * <p/>
     * Objects of the following type(s) are allowed in the list
     * {@link IncentiveApplyIndicationType }
     */
    public List<IncentiveApplyIndicationType> getApplyIndication()
    {
        if (applyIndication == null)
        {
            applyIndication = new ArrayList<IncentiveApplyIndicationType>();
        }
        return this.applyIndication;
    }

    /**
     * Gets the value of the buckets property.
     * <p/>
     * <p/>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the buckets property.
     * <p/>
     * <p/>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBuckets().add(newItem);
     * </pre>
     * <p/>
     * <p/>
     * <p/>
     * Objects of the following type(s) are allowed in the list
     * {@link IncentiveBucketType }
     */
    public List<IncentiveBucketType> getBuckets()
    {
        if (buckets == null)
        {
            buckets = new ArrayList<IncentiveBucketType>();
        }
        return this.buckets;
    }

    /**
     * Gets the value of the cartTotalAmt property.
     *
     * @return possible object is
     *         {@link BasicAmountType }
     */
    public BasicAmountType getCartTotalAmt()
    {
        return cartTotalAmt;
    }

    /**
     * Sets the value of the cartTotalAmt property.
     *
     * @param value allowed object is
     *              {@link BasicAmountType }
     */
    public void setCartTotalAmt(BasicAmountType value)
    {
        this.cartTotalAmt = value;
    }

    /**
     * Gets the value of the requestDetails property.
     *
     * @return possible object is
     *         {@link IncentiveRequestDetailsType }
     */
    public IncentiveRequestDetailsType getRequestDetails()
    {
        return requestDetails;
    }

    /**
     * Sets the value of the requestDetails property.
     *
     * @param value allowed object is
     *              {@link IncentiveRequestDetailsType }
     */
    public void setRequestDetails(IncentiveRequestDetailsType value)
    {
        this.requestDetails = value;
    }

}
