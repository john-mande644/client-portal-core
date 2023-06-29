package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for ItemType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="ItemType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="ApplicationData" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ListOfAttributeSets" type="{urn:ebay:apis:eBLBaseComponents}ListOfAttributeSetType" minOccurs="0"/>
 *         &lt;element name="AutoPay" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="BuyerProtection" type="{urn:ebay:apis:eBLBaseComponents}BuyerProtectionCodeType" minOccurs="0"/>
 *         &lt;element name="BuyItNowPrice" type="{urn:ebay:apis:CoreComponentTypes}AmountType" minOccurs="0"/>
 *         &lt;element name="Charity" type="{urn:ebay:apis:eBLBaseComponents}CharityType" minOccurs="0"/>
 *         &lt;element ref="{urn:ebay:apis:eBLBaseComponents}Country" minOccurs="0"/>
 *         &lt;element name="CrossPromotion" type="{urn:ebay:apis:eBLBaseComponents}CrossPromotionsType" minOccurs="0"/>
 *         &lt;element ref="{urn:ebay:apis:eBLBaseComponents}Currency" minOccurs="0"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Escrow" type="{urn:ebay:apis:eBLBaseComponents}EscrowCodeType" minOccurs="0"/>
 *         &lt;element name="GiftIcon" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="GiftServices" type="{urn:ebay:apis:eBLBaseComponents}GiftServicesCodeType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="HitCounter" type="{urn:ebay:apis:eBLBaseComponents}HitCounterCodeType" minOccurs="0"/>
 *         &lt;element ref="{urn:ebay:apis:eBLBaseComponents}ItemID" minOccurs="0"/>
 *         &lt;element name="ListingDetails" type="{urn:ebay:apis:eBLBaseComponents}ListingDetailsType" minOccurs="0"/>
 *         &lt;element name="ListingDesigner" type="{urn:ebay:apis:eBLBaseComponents}ListingDesignerType" minOccurs="0"/>
 *         &lt;element ref="{urn:ebay:apis:eBLBaseComponents}ListingDuration" minOccurs="0"/>
 *         &lt;element name="ListingEnhancement" type="{urn:ebay:apis:eBLBaseComponents}ListingEnhancementsCodeType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="ListingType" type="{urn:ebay:apis:eBLBaseComponents}ListingTypeCodeType" minOccurs="0"/>
 *         &lt;element name="Location" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PartnerCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PartnerName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element ref="{urn:ebay:apis:eBLBaseComponents}PaymentMethods" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="PayPalEmailAddress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PrimaryCategory" type="{urn:ebay:apis:eBLBaseComponents}CategoryType" minOccurs="0"/>
 *         &lt;element name="PrivateListing" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="Quantity" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element ref="{urn:ebay:apis:eBLBaseComponents}RegionID" minOccurs="0"/>
 *         &lt;element name="RelistLink" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="ReservePrice" type="{urn:ebay:apis:CoreComponentTypes}AmountType" minOccurs="0"/>
 *         &lt;element ref="{urn:ebay:apis:eBLBaseComponents}ReviseStatus" minOccurs="0"/>
 *         &lt;element name="ScheduleTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="SecondaryCategory" type="{urn:ebay:apis:eBLBaseComponents}CategoryType" minOccurs="0"/>
 *         &lt;element name="SiteHostedPicture" type="{urn:ebay:apis:eBLBaseComponents}SiteHostedPictureType" minOccurs="0"/>
 *         &lt;element ref="{urn:ebay:apis:eBLBaseComponents}Seller" minOccurs="0"/>
 *         &lt;element ref="{urn:ebay:apis:eBLBaseComponents}SellingStatus" minOccurs="0"/>
 *         &lt;element name="ShippingOption" type="{urn:ebay:apis:eBLBaseComponents}ShippingOptionCodeType" minOccurs="0"/>
 *         &lt;element ref="{urn:ebay:apis:eBLBaseComponents}ShippingDetails" minOccurs="0"/>
 *         &lt;element ref="{urn:ebay:apis:eBLBaseComponents}ShippingRegions" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="ShippingTerms" type="{urn:ebay:apis:eBLBaseComponents}ShippingTermsCodeType" minOccurs="0"/>
 *         &lt;element ref="{urn:ebay:apis:eBLBaseComponents}Site" minOccurs="0"/>
 *         &lt;element name="StartPrice" type="{urn:ebay:apis:CoreComponentTypes}AmountType" minOccurs="0"/>
 *         &lt;element name="Storefront" type="{urn:ebay:apis:eBLBaseComponents}StorefrontType" minOccurs="0"/>
 *         &lt;element name="SubTitle" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TimeLeft" type="{http://www.w3.org/2001/XMLSchema}duration" minOccurs="0"/>
 *         &lt;element name="Title" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element ref="{urn:ebay:apis:eBLBaseComponents}UUID" minOccurs="0"/>
 *         &lt;element name="VATDetails" type="{urn:ebay:apis:eBLBaseComponents}VATDetailsType" minOccurs="0"/>
 *         &lt;element name="VendorHostedPicture" type="{urn:ebay:apis:eBLBaseComponents}VendorHostedPictureType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ItemType", namespace = "urn:ebay:apis:eBLBaseComponents", propOrder = {
        "applicationData",
        "listOfAttributeSets",
        "autoPay",
        "buyerProtection",
        "buyItNowPrice",
        "charity",
        "country",
        "crossPromotion",
        "currency",
        "description",
        "escrow",
        "giftIcon",
        "giftServices",
        "hitCounter",
        "itemID",
        "listingDetails",
        "listingDesigner",
        "listingDuration",
        "listingEnhancement",
        "listingType",
        "location",
        "partnerCode",
        "partnerName",
        "paymentMethods",
        "payPalEmailAddress",
        "primaryCategory",
        "privateListing",
        "quantity",
        "regionID",
        "relistLink",
        "reservePrice",
        "reviseStatus",
        "scheduleTime",
        "secondaryCategory",
        "siteHostedPicture",
        "seller",
        "sellingStatus",
        "shippingOption",
        "shippingDetails",
        "shippingRegions",
        "shippingTerms",
        "site",
        "startPrice",
        "storefront",
        "subTitle",
        "timeLeft",
        "title",
        "uuid",
        "vatDetails",
        "vendorHostedPicture"
})
public class ItemType
{

    @XmlElement(name = "ApplicationData", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String applicationData;
    @XmlElement(name = "ListOfAttributeSets", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected ListOfAttributeSetType listOfAttributeSets;
    @XmlElement(name = "AutoPay", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected Boolean autoPay;
    @XmlElement(name = "BuyerProtection", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected BuyerProtectionCodeType buyerProtection;
    @XmlElement(name = "BuyItNowPrice", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected AmountType buyItNowPrice;
    @XmlElement(name = "Charity", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected CharityType charity;
    @XmlElement(name = "Country", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected CountryCodeType country;
    @XmlElement(name = "CrossPromotion", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected CrossPromotionsType crossPromotion;
    @XmlElement(name = "Currency", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected CurrencyCodeType currency;
    @XmlElement(name = "Description", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String description;
    @XmlElement(name = "Escrow", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected EscrowCodeType escrow;
    @XmlElement(name = "GiftIcon", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected Integer giftIcon;
    @XmlElement(name = "GiftServices", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected List<GiftServicesCodeType> giftServices;
    @XmlElement(name = "HitCounter", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected HitCounterCodeType hitCounter;
    @XmlElement(name = "ItemID", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String itemID;
    @XmlElement(name = "ListingDetails", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected ListingDetailsType listingDetails;
    @XmlElement(name = "ListingDesigner", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected ListingDesignerType listingDesigner;
    @XmlElement(name = "ListingDuration", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected ListingDurationCodeType listingDuration;
    @XmlElement(name = "ListingEnhancement", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected List<ListingEnhancementsCodeType> listingEnhancement;
    @XmlElement(name = "ListingType", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected ListingTypeCodeType listingType;
    @XmlElement(name = "Location", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String location;
    @XmlElement(name = "PartnerCode", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String partnerCode;
    @XmlElement(name = "PartnerName", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String partnerName;
    @XmlElement(name = "PaymentMethods", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected List<BuyerPaymentMethodCodeType> paymentMethods;
    @XmlElement(name = "PayPalEmailAddress", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String payPalEmailAddress;
    @XmlElement(name = "PrimaryCategory", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected CategoryType primaryCategory;
    @XmlElement(name = "PrivateListing", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected Boolean privateListing;
    @XmlElement(name = "Quantity", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected Integer quantity;
    @XmlElement(name = "RegionID", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String regionID;
    @XmlElement(name = "RelistLink", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected Boolean relistLink;
    @XmlElement(name = "ReservePrice", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected AmountType reservePrice;
    @XmlElement(name = "ReviseStatus", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected ReviseStatusType reviseStatus;
    @XmlElement(name = "ScheduleTime", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected XMLGregorianCalendar scheduleTime;
    @XmlElement(name = "SecondaryCategory", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected CategoryType secondaryCategory;
    @XmlElement(name = "SiteHostedPicture", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected SiteHostedPictureType siteHostedPicture;
    @XmlElement(name = "Seller", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected UserType seller;
    @XmlElement(name = "SellingStatus", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected SellingStatusType sellingStatus;
    @XmlElement(name = "ShippingOption", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected ShippingOptionCodeType shippingOption;
    @XmlElement(name = "ShippingDetails", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected ShippingDetailsType shippingDetails;
    @XmlElement(name = "ShippingRegions", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected List<ShippingRegionCodeType> shippingRegions;
    @XmlElement(name = "ShippingTerms", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected ShippingTermsCodeType shippingTerms;
    @XmlElement(name = "Site", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected SiteCodeType site;
    @XmlElement(name = "StartPrice", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected AmountType startPrice;
    @XmlElement(name = "Storefront", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected StorefrontType storefront;
    @XmlElement(name = "SubTitle", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String subTitle;
    @XmlElement(name = "TimeLeft", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected Duration timeLeft;
    @XmlElement(name = "Title", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String title;
    @XmlElement(name = "UUID", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String uuid;
    @XmlElement(name = "VATDetails", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected VATDetailsType vatDetails;
    @XmlElement(name = "VendorHostedPicture", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected VendorHostedPictureType vendorHostedPicture;

    /**
     * Gets the value of the applicationData property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getApplicationData()
    {
        return applicationData;
    }

    /**
     * Sets the value of the applicationData property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setApplicationData(String value)
    {
        this.applicationData = value;
    }

    /**
     * Gets the value of the listOfAttributeSets property.
     *
     * @return possible object is
     *         {@link ListOfAttributeSetType }
     */
    public ListOfAttributeSetType getListOfAttributeSets()
    {
        return listOfAttributeSets;
    }

    /**
     * Sets the value of the listOfAttributeSets property.
     *
     * @param value allowed object is
     *              {@link ListOfAttributeSetType }
     */
    public void setListOfAttributeSets(ListOfAttributeSetType value)
    {
        this.listOfAttributeSets = value;
    }

    /**
     * Gets the value of the autoPay property.
     *
     * @return possible object is
     *         {@link Boolean }
     */
    public Boolean isAutoPay()
    {
        return autoPay;
    }

    /**
     * Sets the value of the autoPay property.
     *
     * @param value allowed object is
     *              {@link Boolean }
     */
    public void setAutoPay(Boolean value)
    {
        this.autoPay = value;
    }

    /**
     * Gets the value of the buyerProtection property.
     *
     * @return possible object is
     *         {@link BuyerProtectionCodeType }
     */
    public BuyerProtectionCodeType getBuyerProtection()
    {
        return buyerProtection;
    }

    /**
     * Sets the value of the buyerProtection property.
     *
     * @param value allowed object is
     *              {@link BuyerProtectionCodeType }
     */
    public void setBuyerProtection(BuyerProtectionCodeType value)
    {
        this.buyerProtection = value;
    }

    /**
     * Gets the value of the buyItNowPrice property.
     *
     * @return possible object is
     *         {@link AmountType }
     */
    public AmountType getBuyItNowPrice()
    {
        return buyItNowPrice;
    }

    /**
     * Sets the value of the buyItNowPrice property.
     *
     * @param value allowed object is
     *              {@link AmountType }
     */
    public void setBuyItNowPrice(AmountType value)
    {
        this.buyItNowPrice = value;
    }

    /**
     * Gets the value of the charity property.
     *
     * @return possible object is
     *         {@link CharityType }
     */
    public CharityType getCharity()
    {
        return charity;
    }

    /**
     * Sets the value of the charity property.
     *
     * @param value allowed object is
     *              {@link CharityType }
     */
    public void setCharity(CharityType value)
    {
        this.charity = value;
    }

    /**
     * 2-letter ISO 3166 Country Code.
     *
     * @return possible object is
     *         {@link CountryCodeType }
     */
    public CountryCodeType getCountry()
    {
        return country;
    }

    /**
     * 2-letter ISO 3166 Country Code.
     *
     * @param value allowed object is
     *              {@link CountryCodeType }
     */
    public void setCountry(CountryCodeType value)
    {
        this.country = value;
    }

    /**
     * Gets the value of the crossPromotion property.
     *
     * @return possible object is
     *         {@link CrossPromotionsType }
     */
    public CrossPromotionsType getCrossPromotion()
    {
        return crossPromotion;
    }

    /**
     * Sets the value of the crossPromotion property.
     *
     * @param value allowed object is
     *              {@link CrossPromotionsType }
     */
    public void setCrossPromotion(CrossPromotionsType value)
    {
        this.crossPromotion = value;
    }

    /**
     * 3-letter ISO Currency Code.
     *
     * @return possible object is
     *         {@link CurrencyCodeType }
     */
    public CurrencyCodeType getCurrency()
    {
        return currency;
    }

    /**
     * 3-letter ISO Currency Code.
     *
     * @param value allowed object is
     *              {@link CurrencyCodeType }
     */
    public void setCurrency(CurrencyCodeType value)
    {
        this.currency = value;
    }

    /**
     * Gets the value of the description property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * Sets the value of the description property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setDescription(String value)
    {
        this.description = value;
    }

    /**
     * Gets the value of the escrow property.
     *
     * @return possible object is
     *         {@link EscrowCodeType }
     */
    public EscrowCodeType getEscrow()
    {
        return escrow;
    }

    /**
     * Sets the value of the escrow property.
     *
     * @param value allowed object is
     *              {@link EscrowCodeType }
     */
    public void setEscrow(EscrowCodeType value)
    {
        this.escrow = value;
    }

    /**
     * Gets the value of the giftIcon property.
     *
     * @return possible object is
     *         {@link Integer }
     */
    public Integer getGiftIcon()
    {
        return giftIcon;
    }

    /**
     * Sets the value of the giftIcon property.
     *
     * @param value allowed object is
     *              {@link Integer }
     */
    public void setGiftIcon(Integer value)
    {
        this.giftIcon = value;
    }

    /**
     * Gets the value of the giftServices property.
     * <p/>
     * <p/>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the giftServices property.
     * <p/>
     * <p/>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getGiftServices().add(newItem);
     * </pre>
     * <p/>
     * <p/>
     * <p/>
     * Objects of the following type(s) are allowed in the list
     * {@link GiftServicesCodeType }
     */
    public List<GiftServicesCodeType> getGiftServices()
    {
        if (giftServices == null)
        {
            giftServices = new ArrayList<GiftServicesCodeType>();
        }
        return this.giftServices;
    }

    /**
     * Gets the value of the hitCounter property.
     *
     * @return possible object is
     *         {@link HitCounterCodeType }
     */
    public HitCounterCodeType getHitCounter()
    {
        return hitCounter;
    }

    /**
     * Sets the value of the hitCounter property.
     *
     * @param value allowed object is
     *              {@link HitCounterCodeType }
     */
    public void setHitCounter(HitCounterCodeType value)
    {
        this.hitCounter = value;
    }

    /**
     * The ID that uniquely identifies the item listing.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getItemID()
    {
        return itemID;
    }

    /**
     * The ID that uniquely identifies the item listing.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setItemID(String value)
    {
        this.itemID = value;
    }

    /**
     * Gets the value of the listingDetails property.
     *
     * @return possible object is
     *         {@link ListingDetailsType }
     */
    public ListingDetailsType getListingDetails()
    {
        return listingDetails;
    }

    /**
     * Sets the value of the listingDetails property.
     *
     * @param value allowed object is
     *              {@link ListingDetailsType }
     */
    public void setListingDetails(ListingDetailsType value)
    {
        this.listingDetails = value;
    }

    /**
     * Gets the value of the listingDesigner property.
     *
     * @return possible object is
     *         {@link ListingDesignerType }
     */
    public ListingDesignerType getListingDesigner()
    {
        return listingDesigner;
    }

    /**
     * Sets the value of the listingDesigner property.
     *
     * @param value allowed object is
     *              {@link ListingDesignerType }
     */
    public void setListingDesigner(ListingDesignerType value)
    {
        this.listingDesigner = value;
    }

    /**
     * Describes the number of days the auction will be active.
     *
     * @return possible object is
     *         {@link ListingDurationCodeType }
     */
    public ListingDurationCodeType getListingDuration()
    {
        return listingDuration;
    }

    /**
     * Describes the number of days the auction will be active.
     *
     * @param value allowed object is
     *              {@link ListingDurationCodeType }
     */
    public void setListingDuration(ListingDurationCodeType value)
    {
        this.listingDuration = value;
    }

    /**
     * Gets the value of the listingEnhancement property.
     * <p/>
     * <p/>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the listingEnhancement property.
     * <p/>
     * <p/>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getListingEnhancement().add(newItem);
     * </pre>
     * <p/>
     * <p/>
     * <p/>
     * Objects of the following type(s) are allowed in the list
     * {@link ListingEnhancementsCodeType }
     */
    public List<ListingEnhancementsCodeType> getListingEnhancement()
    {
        if (listingEnhancement == null)
        {
            listingEnhancement = new ArrayList<ListingEnhancementsCodeType>();
        }
        return this.listingEnhancement;
    }

    /**
     * Gets the value of the listingType property.
     *
     * @return possible object is
     *         {@link ListingTypeCodeType }
     */
    public ListingTypeCodeType getListingType()
    {
        return listingType;
    }

    /**
     * Sets the value of the listingType property.
     *
     * @param value allowed object is
     *              {@link ListingTypeCodeType }
     */
    public void setListingType(ListingTypeCodeType value)
    {
        this.listingType = value;
    }

    /**
     * Gets the value of the location property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getLocation()
    {
        return location;
    }

    /**
     * Sets the value of the location property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setLocation(String value)
    {
        this.location = value;
    }

    /**
     * Gets the value of the partnerCode property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getPartnerCode()
    {
        return partnerCode;
    }

    /**
     * Sets the value of the partnerCode property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setPartnerCode(String value)
    {
        this.partnerCode = value;
    }

    /**
     * Gets the value of the partnerName property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getPartnerName()
    {
        return partnerName;
    }

    /**
     * Sets the value of the partnerName property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setPartnerName(String value)
    {
        this.partnerName = value;
    }

    /**
     * List of payment methods accepted by a seller from a buyer for
     * a (checkout) transaction.
     * Gets the value of the paymentMethods property.
     * <p/>
     * <p/>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the paymentMethods property.
     * <p/>
     * <p/>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPaymentMethods().add(newItem);
     * </pre>
     * <p/>
     * <p/>
     * <p/>
     * Objects of the following type(s) are allowed in the list
     * {@link BuyerPaymentMethodCodeType }
     */
    public List<BuyerPaymentMethodCodeType> getPaymentMethods()
    {
        if (paymentMethods == null)
        {
            paymentMethods = new ArrayList<BuyerPaymentMethodCodeType>();
        }
        return this.paymentMethods;
    }

    /**
     * Gets the value of the payPalEmailAddress property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getPayPalEmailAddress()
    {
        return payPalEmailAddress;
    }

    /**
     * Sets the value of the payPalEmailAddress property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setPayPalEmailAddress(String value)
    {
        this.payPalEmailAddress = value;
    }

    /**
     * Gets the value of the primaryCategory property.
     *
     * @return possible object is
     *         {@link CategoryType }
     */
    public CategoryType getPrimaryCategory()
    {
        return primaryCategory;
    }

    /**
     * Sets the value of the primaryCategory property.
     *
     * @param value allowed object is
     *              {@link CategoryType }
     */
    public void setPrimaryCategory(CategoryType value)
    {
        this.primaryCategory = value;
    }

    /**
     * Gets the value of the privateListing property.
     *
     * @return possible object is
     *         {@link Boolean }
     */
    public Boolean isPrivateListing()
    {
        return privateListing;
    }

    /**
     * Sets the value of the privateListing property.
     *
     * @param value allowed object is
     *              {@link Boolean }
     */
    public void setPrivateListing(Boolean value)
    {
        this.privateListing = value;
    }

    /**
     * Gets the value of the quantity property.
     *
     * @return possible object is
     *         {@link Integer }
     */
    public Integer getQuantity()
    {
        return quantity;
    }

    /**
     * Sets the value of the quantity property.
     *
     * @param value allowed object is
     *              {@link Integer }
     */
    public void setQuantity(Integer value)
    {
        this.quantity = value;
    }

    /**
     * Region where the item is listed. See Region Table for values.
     * If the item is listed with a Region of 0 (zero), then this return
     * field denotes no region association with the item, meaning
     * that it is not listing the item regionally.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getRegionID()
    {
        return regionID;
    }

    /**
     * Region where the item is listed. See Region Table for values.
     * If the item is listed with a Region of 0 (zero), then this return
     * field denotes no region association with the item, meaning
     * that it is not listing the item regionally.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setRegionID(String value)
    {
        this.regionID = value;
    }

    /**
     * Gets the value of the relistLink property.
     *
     * @return possible object is
     *         {@link Boolean }
     */
    public Boolean isRelistLink()
    {
        return relistLink;
    }

    /**
     * Sets the value of the relistLink property.
     *
     * @param value allowed object is
     *              {@link Boolean }
     */
    public void setRelistLink(Boolean value)
    {
        this.relistLink = value;
    }

    /**
     * Gets the value of the reservePrice property.
     *
     * @return possible object is
     *         {@link AmountType }
     */
    public AmountType getReservePrice()
    {
        return reservePrice;
    }

    /**
     * Sets the value of the reservePrice property.
     *
     * @param value allowed object is
     *              {@link AmountType }
     */
    public void setReservePrice(AmountType value)
    {
        this.reservePrice = value;
    }

    /**
     * Revise Status contains information about the item being
     * revised.
     *
     * @return possible object is
     *         {@link ReviseStatusType }
     */
    public ReviseStatusType getReviseStatus()
    {
        return reviseStatus;
    }

    /**
     * Revise Status contains information about the item being
     * revised.
     *
     * @param value allowed object is
     *              {@link ReviseStatusType }
     */
    public void setReviseStatus(ReviseStatusType value)
    {
        this.reviseStatus = value;
    }

    /**
     * Gets the value of the scheduleTime property.
     *
     * @return possible object is
     *         {@link XMLGregorianCalendar }
     */
    public XMLGregorianCalendar getScheduleTime()
    {
        return scheduleTime;
    }

    /**
     * Sets the value of the scheduleTime property.
     *
     * @param value allowed object is
     *              {@link XMLGregorianCalendar }
     */
    public void setScheduleTime(XMLGregorianCalendar value)
    {
        this.scheduleTime = value;
    }

    /**
     * Gets the value of the secondaryCategory property.
     *
     * @return possible object is
     *         {@link CategoryType }
     */
    public CategoryType getSecondaryCategory()
    {
        return secondaryCategory;
    }

    /**
     * Sets the value of the secondaryCategory property.
     *
     * @param value allowed object is
     *              {@link CategoryType }
     */
    public void setSecondaryCategory(CategoryType value)
    {
        this.secondaryCategory = value;
    }

    /**
     * Gets the value of the siteHostedPicture property.
     *
     * @return possible object is
     *         {@link SiteHostedPictureType }
     */
    public SiteHostedPictureType getSiteHostedPicture()
    {
        return siteHostedPicture;
    }

    /**
     * Sets the value of the siteHostedPicture property.
     *
     * @param value allowed object is
     *              {@link SiteHostedPictureType }
     */
    public void setSiteHostedPicture(SiteHostedPictureType value)
    {
        this.siteHostedPicture = value;
    }

    /**
     * Seller user.
     *
     * @return possible object is
     *         {@link UserType }
     */
    public UserType getSeller()
    {
        return seller;
    }

    /**
     * Seller user.
     *
     * @param value allowed object is
     *              {@link UserType }
     */
    public void setSeller(UserType value)
    {
        this.seller = value;
    }

    /**
     * Container for for selling status information (e.g., BidCount,
     * BidIncrement, HighBidder, MinimimumToBid, etc).
     *
     * @return possible object is
     *         {@link SellingStatusType }
     */
    public SellingStatusType getSellingStatus()
    {
        return sellingStatus;
    }

    /**
     * Container for for selling status information (e.g., BidCount,
     * BidIncrement, HighBidder, MinimimumToBid, etc).
     *
     * @param value allowed object is
     *              {@link SellingStatusType }
     */
    public void setSellingStatus(SellingStatusType value)
    {
        this.sellingStatus = value;
    }

    /**
     * Gets the value of the shippingOption property.
     *
     * @return possible object is
     *         {@link ShippingOptionCodeType }
     */
    public ShippingOptionCodeType getShippingOption()
    {
        return shippingOption;
    }

    /**
     * Sets the value of the shippingOption property.
     *
     * @param value allowed object is
     *              {@link ShippingOptionCodeType }
     */
    public void setShippingOption(ShippingOptionCodeType value)
    {
        this.shippingOption = value;
    }

    /**
     * Contains the shipping payment related information for the
     * listed item.
     *
     * @return possible object is
     *         {@link ShippingDetailsType }
     */
    public ShippingDetailsType getShippingDetails()
    {
        return shippingDetails;
    }

    /**
     * Contains the shipping payment related information for the
     * listed item.
     *
     * @param value allowed object is
     *              {@link ShippingDetailsType }
     */
    public void setShippingDetails(ShippingDetailsType value)
    {
        this.shippingDetails = value;
    }

    /**
     * Regions that seller will ship to.
     * Gets the value of the shippingRegions property.
     * <p/>
     * <p/>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the shippingRegions property.
     * <p/>
     * <p/>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getShippingRegions().add(newItem);
     * </pre>
     * <p/>
     * <p/>
     * <p/>
     * Objects of the following type(s) are allowed in the list
     * {@link ShippingRegionCodeType }
     */
    public List<ShippingRegionCodeType> getShippingRegions()
    {
        if (shippingRegions == null)
        {
            shippingRegions = new ArrayList<ShippingRegionCodeType>();
        }
        return this.shippingRegions;
    }

    /**
     * Gets the value of the shippingTerms property.
     *
     * @return possible object is
     *         {@link ShippingTermsCodeType }
     */
    public ShippingTermsCodeType getShippingTerms()
    {
        return shippingTerms;
    }

    /**
     * Sets the value of the shippingTerms property.
     *
     * @param value allowed object is
     *              {@link ShippingTermsCodeType }
     */
    public void setShippingTerms(ShippingTermsCodeType value)
    {
        this.shippingTerms = value;
    }

    /**
     * eBay site on which item is listed.
     *
     * @return possible object is
     *         {@link SiteCodeType }
     */
    public SiteCodeType getSite()
    {
        return site;
    }

    /**
     * eBay site on which item is listed.
     *
     * @param value allowed object is
     *              {@link SiteCodeType }
     */
    public void setSite(SiteCodeType value)
    {
        this.site = value;
    }

    /**
     * Gets the value of the startPrice property.
     *
     * @return possible object is
     *         {@link AmountType }
     */
    public AmountType getStartPrice()
    {
        return startPrice;
    }

    /**
     * Sets the value of the startPrice property.
     *
     * @param value allowed object is
     *              {@link AmountType }
     */
    public void setStartPrice(AmountType value)
    {
        this.startPrice = value;
    }

    /**
     * Gets the value of the storefront property.
     *
     * @return possible object is
     *         {@link StorefrontType }
     */
    public StorefrontType getStorefront()
    {
        return storefront;
    }

    /**
     * Sets the value of the storefront property.
     *
     * @param value allowed object is
     *              {@link StorefrontType }
     */
    public void setStorefront(StorefrontType value)
    {
        this.storefront = value;
    }

    /**
     * Gets the value of the subTitle property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getSubTitle()
    {
        return subTitle;
    }

    /**
     * Sets the value of the subTitle property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setSubTitle(String value)
    {
        this.subTitle = value;
    }

    /**
     * Gets the value of the timeLeft property.
     *
     * @return possible object is
     *         {@link Duration }
     */
    public Duration getTimeLeft()
    {
        return timeLeft;
    }

    /**
     * Sets the value of the timeLeft property.
     *
     * @param value allowed object is
     *              {@link Duration }
     */
    public void setTimeLeft(Duration value)
    {
        this.timeLeft = value;
    }

    /**
     * Gets the value of the title property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getTitle()
    {
        return title;
    }

    /**
     * Sets the value of the title property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setTitle(String value)
    {
        this.title = value;
    }

    /**
     * Universally unique constraint tag. The UUID is unique to a category.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getUUID()
    {
        return uuid;
    }

    /**
     * Universally unique constraint tag. The UUID is unique to a category.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setUUID(String value)
    {
        this.uuid = value;
    }

    /**
     * Gets the value of the vatDetails property.
     *
     * @return possible object is
     *         {@link VATDetailsType }
     */
    public VATDetailsType getVATDetails()
    {
        return vatDetails;
    }

    /**
     * Sets the value of the vatDetails property.
     *
     * @param value allowed object is
     *              {@link VATDetailsType }
     */
    public void setVATDetails(VATDetailsType value)
    {
        this.vatDetails = value;
    }

    /**
     * Gets the value of the vendorHostedPicture property.
     *
     * @return possible object is
     *         {@link VendorHostedPictureType }
     */
    public VendorHostedPictureType getVendorHostedPicture()
    {
        return vendorHostedPicture;
    }

    /**
     * Sets the value of the vendorHostedPicture property.
     *
     * @param value allowed object is
     *              {@link VendorHostedPictureType }
     */
    public void setVendorHostedPicture(VendorHostedPictureType value)
    {
        this.vendorHostedPicture = value;
    }

}
