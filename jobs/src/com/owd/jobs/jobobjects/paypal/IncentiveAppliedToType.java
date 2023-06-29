package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for IncentiveAppliedToType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="IncentiveAppliedToType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="BucketId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ItemId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="IncentiveAmount" type="{urn:ebay:apis:CoreComponentTypes}BasicAmountType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IncentiveAppliedToType", namespace = "urn:ebay:apis:eBLBaseComponents", propOrder = {
        "bucketId",
        "itemId",
        "incentiveAmount"
})
public class IncentiveAppliedToType
{

    @XmlElement(name = "BucketId", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String bucketId;
    @XmlElement(name = "ItemId", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String itemId;
    @XmlElement(name = "IncentiveAmount", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected BasicAmountType incentiveAmount;

    /**
     * Gets the value of the bucketId property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getBucketId()
    {
        return bucketId;
    }

    /**
     * Sets the value of the bucketId property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setBucketId(String value)
    {
        this.bucketId = value;
    }

    /**
     * Gets the value of the itemId property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getItemId()
    {
        return itemId;
    }

    /**
     * Sets the value of the itemId property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setItemId(String value)
    {
        this.itemId = value;
    }

    /**
     * Gets the value of the incentiveAmount property.
     *
     * @return possible object is
     *         {@link BasicAmountType }
     */
    public BasicAmountType getIncentiveAmount()
    {
        return incentiveAmount;
    }

    /**
     * Sets the value of the incentiveAmount property.
     *
     * @param value allowed object is
     *              {@link BasicAmountType }
     */
    public void setIncentiveAmount(BasicAmountType value)
    {
        this.incentiveAmount = value;
    }

}
