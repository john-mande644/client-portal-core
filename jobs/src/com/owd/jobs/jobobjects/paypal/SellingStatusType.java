package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Contains the listed items price details which consists of
 * following information: BuyItNowPrice, ConvertedBuyItNowPrice,
 * ConvertedPrice, ConvertedStartPrice, CurrentPrice, MinimumToBid,
 * ReservePrice, and StartPrice.  need to take in account get seller events when defining minoccurs = 0
 * <p/>
 * <p/>
 * <p>Java class for SellingStatusType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="SellingStatusType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="BidCount" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="BidIncrement" type="{urn:ebay:apis:CoreComponentTypes}AmountType" minOccurs="0"/>
 *         &lt;element name="ConvertedCurrentPrice" type="{urn:ebay:apis:CoreComponentTypes}AmountType" minOccurs="0"/>
 *         &lt;element name="CurrentPrice" type="{urn:ebay:apis:CoreComponentTypes}AmountType"/>
 *         &lt;element ref="{urn:ebay:apis:eBLBaseComponents}HighBidder" minOccurs="0"/>
 *         &lt;element name="LeadCount" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="MinimumToBid" type="{urn:ebay:apis:CoreComponentTypes}AmountType" minOccurs="0"/>
 *         &lt;element name="QuantitySold" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ReserveMet" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="SecondChanceEligible" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SellingStatusType", namespace = "urn:ebay:apis:eBLBaseComponents", propOrder = {
        "bidCount",
        "bidIncrement",
        "convertedCurrentPrice",
        "currentPrice",
        "highBidder",
        "leadCount",
        "minimumToBid",
        "quantitySold",
        "reserveMet",
        "secondChanceEligible"
})
public class SellingStatusType
{

    @XmlElement(name = "BidCount", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected Integer bidCount;
    @XmlElement(name = "BidIncrement", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected AmountType bidIncrement;
    @XmlElement(name = "ConvertedCurrentPrice", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected AmountType convertedCurrentPrice;
    @XmlElement(name = "CurrentPrice", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected AmountType currentPrice;
    @XmlElement(name = "HighBidder", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected UserType highBidder;
    @XmlElement(name = "LeadCount", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected Integer leadCount;
    @XmlElement(name = "MinimumToBid", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected AmountType minimumToBid;
    @XmlElement(name = "QuantitySold", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected int quantitySold;
    @XmlElement(name = "ReserveMet", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected Boolean reserveMet;
    @XmlElement(name = "SecondChanceEligible", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected Boolean secondChanceEligible;

    /**
     * Gets the value of the bidCount property.
     *
     * @return possible object is
     *         {@link Integer }
     */
    public Integer getBidCount()
    {
        return bidCount;
    }

    /**
     * Sets the value of the bidCount property.
     *
     * @param value allowed object is
     *              {@link Integer }
     */
    public void setBidCount(Integer value)
    {
        this.bidCount = value;
    }

    /**
     * Gets the value of the bidIncrement property.
     *
     * @return possible object is
     *         {@link AmountType }
     */
    public AmountType getBidIncrement()
    {
        return bidIncrement;
    }

    /**
     * Sets the value of the bidIncrement property.
     *
     * @param value allowed object is
     *              {@link AmountType }
     */
    public void setBidIncrement(AmountType value)
    {
        this.bidIncrement = value;
    }

    /**
     * Gets the value of the convertedCurrentPrice property.
     *
     * @return possible object is
     *         {@link AmountType }
     */
    public AmountType getConvertedCurrentPrice()
    {
        return convertedCurrentPrice;
    }

    /**
     * Sets the value of the convertedCurrentPrice property.
     *
     * @param value allowed object is
     *              {@link AmountType }
     */
    public void setConvertedCurrentPrice(AmountType value)
    {
        this.convertedCurrentPrice = value;
    }

    /**
     * Gets the value of the currentPrice property.
     *
     * @return possible object is
     *         {@link AmountType }
     */
    public AmountType getCurrentPrice()
    {
        return currentPrice;
    }

    /**
     * Sets the value of the currentPrice property.
     *
     * @param value allowed object is
     *              {@link AmountType }
     */
    public void setCurrentPrice(AmountType value)
    {
        this.currentPrice = value;
    }

    /**
     * Contains one User node representing the current high
     * bidder. GetItem returns a high bidder for auctions that have
     * ended and have a winning bidder. For Fixed Price listings,
     * in-progress auctions, or auction items that received no
     * bids, GetItem returns a HighBidder node with empty tags.
     *
     * @return possible object is
     *         {@link UserType }
     */
    public UserType getHighBidder()
    {
        return highBidder;
    }

    /**
     * Contains one User node representing the current high
     * bidder. GetItem returns a high bidder for auctions that have
     * ended and have a winning bidder. For Fixed Price listings,
     * in-progress auctions, or auction items that received no
     * bids, GetItem returns a HighBidder node with empty tags.
     *
     * @param value allowed object is
     *              {@link UserType }
     */
    public void setHighBidder(UserType value)
    {
        this.highBidder = value;
    }

    /**
     * Gets the value of the leadCount property.
     *
     * @return possible object is
     *         {@link Integer }
     */
    public Integer getLeadCount()
    {
        return leadCount;
    }

    /**
     * Sets the value of the leadCount property.
     *
     * @param value allowed object is
     *              {@link Integer }
     */
    public void setLeadCount(Integer value)
    {
        this.leadCount = value;
    }

    /**
     * Gets the value of the minimumToBid property.
     *
     * @return possible object is
     *         {@link AmountType }
     */
    public AmountType getMinimumToBid()
    {
        return minimumToBid;
    }

    /**
     * Sets the value of the minimumToBid property.
     *
     * @param value allowed object is
     *              {@link AmountType }
     */
    public void setMinimumToBid(AmountType value)
    {
        this.minimumToBid = value;
    }

    /**
     * Gets the value of the quantitySold property.
     */
    public int getQuantitySold()
    {
        return quantitySold;
    }

    /**
     * Sets the value of the quantitySold property.
     */
    public void setQuantitySold(int value)
    {
        this.quantitySold = value;
    }

    /**
     * Gets the value of the reserveMet property.
     *
     * @return possible object is
     *         {@link Boolean }
     */
    public Boolean isReserveMet()
    {
        return reserveMet;
    }

    /**
     * Sets the value of the reserveMet property.
     *
     * @param value allowed object is
     *              {@link Boolean }
     */
    public void setReserveMet(Boolean value)
    {
        this.reserveMet = value;
    }

    /**
     * Gets the value of the secondChanceEligible property.
     *
     * @return possible object is
     *         {@link Boolean }
     */
    public Boolean isSecondChanceEligible()
    {
        return secondChanceEligible;
    }

    /**
     * Sets the value of the secondChanceEligible property.
     *
     * @param value allowed object is
     *              {@link Boolean }
     */
    public void setSecondChanceEligible(Boolean value)
    {
        this.secondChanceEligible = value;
    }

}
