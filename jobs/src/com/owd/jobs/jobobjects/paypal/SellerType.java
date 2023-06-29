package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.*;


/**
 * Information about user used by selling applications
 * there are number of required elements - they will always show up for seller node
 * there is not such a call to do revise seller info. only added minoccur=0 to elements that will not show up in every
 * type of request/responce
 * <p/>
 * <p/>
 * <p>Java class for SellerType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="SellerType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="AllowPaymentEdit" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="BillingCurrency" type="{urn:ebay:apis:eBLBaseComponents}CurrencyCodeType" minOccurs="0"/>
 *         &lt;element ref="{urn:ebay:apis:eBLBaseComponents}CheckoutEnabled"/>
 *         &lt;element name="CIPBankAccountStored" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="GoodStanding" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="LiveAuctionAuthorized" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="MerchandizingPref" type="{urn:ebay:apis:eBLBaseComponents}MerchandizingPrefCodeType"/>
 *         &lt;element name="QualifiesForB2BVAT" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element ref="{urn:ebay:apis:eBLBaseComponents}SellerLevel"/>
 *         &lt;element ref="{urn:ebay:apis:eBLBaseComponents}SellerPaymentAddress" minOccurs="0"/>
 *         &lt;element name="SchedulingInfo" type="{urn:ebay:apis:eBLBaseComponents}SchedulingInfoType" minOccurs="0"/>
 *         &lt;element ref="{urn:ebay:apis:eBLBaseComponents}StoreOwner"/>
 *         &lt;element name="StoreURL" type="{http://www.w3.org/2001/XMLSchema}anyURI" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SellerType", namespace = "urn:ebay:apis:eBLBaseComponents", propOrder = {
        "allowPaymentEdit",
        "billingCurrency",
        "checkoutEnabled",
        "cipBankAccountStored",
        "goodStanding",
        "liveAuctionAuthorized",
        "merchandizingPref",
        "qualifiesForB2BVAT",
        "sellerLevel",
        "sellerPaymentAddress",
        "schedulingInfo",
        "storeOwner",
        "storeURL"
})
public class SellerType
{

    @XmlElement(name = "AllowPaymentEdit", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected boolean allowPaymentEdit;
    @XmlElement(name = "BillingCurrency", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected CurrencyCodeType billingCurrency;
    @XmlElement(name = "CheckoutEnabled", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected boolean checkoutEnabled;
    @XmlElement(name = "CIPBankAccountStored", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected boolean cipBankAccountStored;
    @XmlElement(name = "GoodStanding", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected boolean goodStanding;
    @XmlElement(name = "LiveAuctionAuthorized", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected boolean liveAuctionAuthorized;
    @XmlElement(name = "MerchandizingPref", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected MerchandizingPrefCodeType merchandizingPref;
    @XmlElement(name = "QualifiesForB2BVAT", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected boolean qualifiesForB2BVAT;
    @XmlElement(name = "SellerLevel", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected SellerLevelCodeType sellerLevel;
    @XmlElement(name = "SellerPaymentAddress", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected AddressType sellerPaymentAddress;
    @XmlElement(name = "SchedulingInfo", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected SchedulingInfoType schedulingInfo;
    @XmlElement(name = "StoreOwner", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected boolean storeOwner;
    @XmlElement(name = "StoreURL", namespace = "urn:ebay:apis:eBLBaseComponents")
    @XmlSchemaType(name = "anyURI")
    protected String storeURL;

    /**
     * Gets the value of the allowPaymentEdit property.
     */
    public boolean isAllowPaymentEdit()
    {
        return allowPaymentEdit;
    }

    /**
     * Sets the value of the allowPaymentEdit property.
     */
    public void setAllowPaymentEdit(boolean value)
    {
        this.allowPaymentEdit = value;
    }

    /**
     * Gets the value of the billingCurrency property.
     *
     * @return possible object is
     *         {@link CurrencyCodeType }
     */
    public CurrencyCodeType getBillingCurrency()
    {
        return billingCurrency;
    }

    /**
     * Sets the value of the billingCurrency property.
     *
     * @param value allowed object is
     *              {@link CurrencyCodeType }
     */
    public void setBillingCurrency(CurrencyCodeType value)
    {
        this.billingCurrency = value;
    }

    /**
     * Gets the value of the checkoutEnabled property.
     */
    public boolean isCheckoutEnabled()
    {
        return checkoutEnabled;
    }

    /**
     * Sets the value of the checkoutEnabled property.
     */
    public void setCheckoutEnabled(boolean value)
    {
        this.checkoutEnabled = value;
    }

    /**
     * Gets the value of the cipBankAccountStored property.
     */
    public boolean isCIPBankAccountStored()
    {
        return cipBankAccountStored;
    }

    /**
     * Sets the value of the cipBankAccountStored property.
     */
    public void setCIPBankAccountStored(boolean value)
    {
        this.cipBankAccountStored = value;
    }

    /**
     * Gets the value of the goodStanding property.
     */
    public boolean isGoodStanding()
    {
        return goodStanding;
    }

    /**
     * Sets the value of the goodStanding property.
     */
    public void setGoodStanding(boolean value)
    {
        this.goodStanding = value;
    }

    /**
     * Gets the value of the liveAuctionAuthorized property.
     */
    public boolean isLiveAuctionAuthorized()
    {
        return liveAuctionAuthorized;
    }

    /**
     * Sets the value of the liveAuctionAuthorized property.
     */
    public void setLiveAuctionAuthorized(boolean value)
    {
        this.liveAuctionAuthorized = value;
    }

    /**
     * Gets the value of the merchandizingPref property.
     *
     * @return possible object is
     *         {@link MerchandizingPrefCodeType }
     */
    public MerchandizingPrefCodeType getMerchandizingPref()
    {
        return merchandizingPref;
    }

    /**
     * Sets the value of the merchandizingPref property.
     *
     * @param value allowed object is
     *              {@link MerchandizingPrefCodeType }
     */
    public void setMerchandizingPref(MerchandizingPrefCodeType value)
    {
        this.merchandizingPref = value;
    }

    /**
     * Gets the value of the qualifiesForB2BVAT property.
     */
    public boolean isQualifiesForB2BVAT()
    {
        return qualifiesForB2BVAT;
    }

    /**
     * Sets the value of the qualifiesForB2BVAT property.
     */
    public void setQualifiesForB2BVAT(boolean value)
    {
        this.qualifiesForB2BVAT = value;
    }

    /**
     * Gets the value of the sellerLevel property.
     *
     * @return possible object is
     *         {@link SellerLevelCodeType }
     */
    public SellerLevelCodeType getSellerLevel()
    {
        return sellerLevel;
    }

    /**
     * Sets the value of the sellerLevel property.
     *
     * @param value allowed object is
     *              {@link SellerLevelCodeType }
     */
    public void setSellerLevel(SellerLevelCodeType value)
    {
        this.sellerLevel = value;
    }

    /**
     * Gets the value of the sellerPaymentAddress property.
     *
     * @return possible object is
     *         {@link AddressType }
     */
    public AddressType getSellerPaymentAddress()
    {
        return sellerPaymentAddress;
    }

    /**
     * Sets the value of the sellerPaymentAddress property.
     *
     * @param value allowed object is
     *              {@link AddressType }
     */
    public void setSellerPaymentAddress(AddressType value)
    {
        this.sellerPaymentAddress = value;
    }

    /**
     * Gets the value of the schedulingInfo property.
     *
     * @return possible object is
     *         {@link SchedulingInfoType }
     */
    public SchedulingInfoType getSchedulingInfo()
    {
        return schedulingInfo;
    }

    /**
     * Sets the value of the schedulingInfo property.
     *
     * @param value allowed object is
     *              {@link SchedulingInfoType }
     */
    public void setSchedulingInfo(SchedulingInfoType value)
    {
        this.schedulingInfo = value;
    }

    /**
     * Gets the value of the storeOwner property.
     */
    public boolean isStoreOwner()
    {
        return storeOwner;
    }

    /**
     * Sets the value of the storeOwner property.
     */
    public void setStoreOwner(boolean value)
    {
        this.storeOwner = value;
    }

    /**
     * Gets the value of the storeURL property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getStoreURL()
    {
        return storeURL;
    }

    /**
     * Sets the value of the storeURL property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setStoreURL(String value)
    {
        this.storeURL = value;
    }

}
