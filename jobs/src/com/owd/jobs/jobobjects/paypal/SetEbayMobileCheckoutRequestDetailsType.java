package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SetEbayMobileCheckoutRequestDetailsType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="SetEbayMobileCheckoutRequestDetailsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="CheckoutType" type="{urn:ebay:apis:eBLBaseComponents}EbayCheckoutType"/>
 *         &lt;element name="ItemId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="TransactionId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="SiteId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="BuyerId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ClientType" type="{urn:ebay:apis:eBLBaseComponents}DyneticClientType"/>
 *         &lt;element name="BuyerPhone" type="{urn:ebay:apis:eBLBaseComponents}PhoneNumberType" minOccurs="0"/>
 *         &lt;element name="ReturnURL" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CancelURL" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Quantity" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="ItemAmount" type="{urn:ebay:apis:CoreComponentTypes}BasicAmountType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SetEbayMobileCheckoutRequestDetailsType", namespace = "urn:ebay:apis:eBLBaseComponents", propOrder = {
        "checkoutType",
        "itemId",
        "transactionId",
        "siteId",
        "buyerId",
        "clientType",
        "buyerPhone",
        "returnURL",
        "cancelURL",
        "quantity",
        "itemAmount"
})
public class SetEbayMobileCheckoutRequestDetailsType
{

    @XmlElement(name = "CheckoutType", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected EbayCheckoutType checkoutType;
    @XmlElement(name = "ItemId", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected String itemId;
    @XmlElement(name = "TransactionId", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected String transactionId;
    @XmlElement(name = "SiteId", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected String siteId;
    @XmlElement(name = "BuyerId", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected String buyerId;
    @XmlElement(name = "ClientType", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected DyneticClientType clientType;
    @XmlElement(name = "BuyerPhone", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected PhoneNumberType buyerPhone;
    @XmlElement(name = "ReturnURL", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String returnURL;
    @XmlElement(name = "CancelURL", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String cancelURL;
    @XmlElement(name = "Quantity", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected Integer quantity;
    @XmlElement(name = "ItemAmount", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected BasicAmountType itemAmount;

    /**
     * Gets the value of the checkoutType property.
     *
     * @return possible object is
     *         {@link EbayCheckoutType }
     */
    public EbayCheckoutType getCheckoutType()
    {
        return checkoutType;
    }

    /**
     * Sets the value of the checkoutType property.
     *
     * @param value allowed object is
     *              {@link EbayCheckoutType }
     */
    public void setCheckoutType(EbayCheckoutType value)
    {
        this.checkoutType = value;
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
     * Gets the value of the transactionId property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getTransactionId()
    {
        return transactionId;
    }

    /**
     * Sets the value of the transactionId property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setTransactionId(String value)
    {
        this.transactionId = value;
    }

    /**
     * Gets the value of the siteId property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getSiteId()
    {
        return siteId;
    }

    /**
     * Sets the value of the siteId property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setSiteId(String value)
    {
        this.siteId = value;
    }

    /**
     * Gets the value of the buyerId property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getBuyerId()
    {
        return buyerId;
    }

    /**
     * Sets the value of the buyerId property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setBuyerId(String value)
    {
        this.buyerId = value;
    }

    /**
     * Gets the value of the clientType property.
     *
     * @return possible object is
     *         {@link DyneticClientType }
     */
    public DyneticClientType getClientType()
    {
        return clientType;
    }

    /**
     * Sets the value of the clientType property.
     *
     * @param value allowed object is
     *              {@link DyneticClientType }
     */
    public void setClientType(DyneticClientType value)
    {
        this.clientType = value;
    }

    /**
     * Gets the value of the buyerPhone property.
     *
     * @return possible object is
     *         {@link PhoneNumberType }
     */
    public PhoneNumberType getBuyerPhone()
    {
        return buyerPhone;
    }

    /**
     * Sets the value of the buyerPhone property.
     *
     * @param value allowed object is
     *              {@link PhoneNumberType }
     */
    public void setBuyerPhone(PhoneNumberType value)
    {
        this.buyerPhone = value;
    }

    /**
     * Gets the value of the returnURL property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getReturnURL()
    {
        return returnURL;
    }

    /**
     * Sets the value of the returnURL property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setReturnURL(String value)
    {
        this.returnURL = value;
    }

    /**
     * Gets the value of the cancelURL property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getCancelURL()
    {
        return cancelURL;
    }

    /**
     * Sets the value of the cancelURL property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setCancelURL(String value)
    {
        this.cancelURL = value;
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
     * Gets the value of the itemAmount property.
     *
     * @return possible object is
     *         {@link BasicAmountType }
     */
    public BasicAmountType getItemAmount()
    {
        return itemAmount;
    }

    /**
     * Sets the value of the itemAmount property.
     *
     * @param value allowed object is
     *              {@link BasicAmountType }
     */
    public void setItemAmount(BasicAmountType value)
    {
        this.itemAmount = value;
    }

}
