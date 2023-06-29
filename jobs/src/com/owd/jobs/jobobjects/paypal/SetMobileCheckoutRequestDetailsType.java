package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SetMobileCheckoutRequestDetailsType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="SetMobileCheckoutRequestDetailsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="BuyerPhone" type="{urn:ebay:apis:eBLBaseComponents}PhoneNumberType" minOccurs="0"/>
 *         &lt;element name="ItemAmount" type="{urn:ebay:apis:CoreComponentTypes}BasicAmountType"/>
 *         &lt;element name="Tax" type="{urn:ebay:apis:CoreComponentTypes}BasicAmountType" minOccurs="0"/>
 *         &lt;element name="Shipping" type="{urn:ebay:apis:CoreComponentTypes}BasicAmountType" minOccurs="0"/>
 *         &lt;element name="ItemName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ItemNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Custom" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="InvoiceID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ReturnURL" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CancelURL" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AddressDisplayOptions" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="SharePhone" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="ShipToAddress" type="{urn:ebay:apis:eBLBaseComponents}AddressType" minOccurs="0"/>
 *         &lt;element name="BuyerEmail" type="{urn:ebay:apis:eBLBaseComponents}EmailAddressType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SetMobileCheckoutRequestDetailsType", namespace = "urn:ebay:apis:eBLBaseComponents", propOrder = {
        "buyerPhone",
        "itemAmount",
        "tax",
        "shipping",
        "itemName",
        "itemNumber",
        "custom",
        "invoiceID",
        "returnURL",
        "cancelURL",
        "addressDisplayOptions",
        "sharePhone",
        "shipToAddress",
        "buyerEmail"
})
public class SetMobileCheckoutRequestDetailsType
{

    @XmlElement(name = "BuyerPhone", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected PhoneNumberType buyerPhone;
    @XmlElement(name = "ItemAmount", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected BasicAmountType itemAmount;
    @XmlElement(name = "Tax", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected BasicAmountType tax;
    @XmlElement(name = "Shipping", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected BasicAmountType shipping;
    @XmlElement(name = "ItemName", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected String itemName;
    @XmlElement(name = "ItemNumber", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String itemNumber;
    @XmlElement(name = "Custom", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String custom;
    @XmlElement(name = "InvoiceID", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String invoiceID;
    @XmlElement(name = "ReturnURL", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected String returnURL;
    @XmlElement(name = "CancelURL", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String cancelURL;
    @XmlElement(name = "AddressDisplayOptions", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected Integer addressDisplayOptions;
    @XmlElement(name = "SharePhone", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected Integer sharePhone;
    @XmlElement(name = "ShipToAddress", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected AddressType shipToAddress;
    @XmlElement(name = "BuyerEmail", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String buyerEmail;

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

    /**
     * Gets the value of the tax property.
     *
     * @return possible object is
     *         {@link BasicAmountType }
     */
    public BasicAmountType getTax()
    {
        return tax;
    }

    /**
     * Sets the value of the tax property.
     *
     * @param value allowed object is
     *              {@link BasicAmountType }
     */
    public void setTax(BasicAmountType value)
    {
        this.tax = value;
    }

    /**
     * Gets the value of the shipping property.
     *
     * @return possible object is
     *         {@link BasicAmountType }
     */
    public BasicAmountType getShipping()
    {
        return shipping;
    }

    /**
     * Sets the value of the shipping property.
     *
     * @param value allowed object is
     *              {@link BasicAmountType }
     */
    public void setShipping(BasicAmountType value)
    {
        this.shipping = value;
    }

    /**
     * Gets the value of the itemName property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getItemName()
    {
        return itemName;
    }

    /**
     * Sets the value of the itemName property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setItemName(String value)
    {
        this.itemName = value;
    }

    /**
     * Gets the value of the itemNumber property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getItemNumber()
    {
        return itemNumber;
    }

    /**
     * Sets the value of the itemNumber property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setItemNumber(String value)
    {
        this.itemNumber = value;
    }

    /**
     * Gets the value of the custom property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getCustom()
    {
        return custom;
    }

    /**
     * Sets the value of the custom property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setCustom(String value)
    {
        this.custom = value;
    }

    /**
     * Gets the value of the invoiceID property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getInvoiceID()
    {
        return invoiceID;
    }

    /**
     * Sets the value of the invoiceID property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setInvoiceID(String value)
    {
        this.invoiceID = value;
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
     * Gets the value of the addressDisplayOptions property.
     *
     * @return possible object is
     *         {@link Integer }
     */
    public Integer getAddressDisplayOptions()
    {
        return addressDisplayOptions;
    }

    /**
     * Sets the value of the addressDisplayOptions property.
     *
     * @param value allowed object is
     *              {@link Integer }
     */
    public void setAddressDisplayOptions(Integer value)
    {
        this.addressDisplayOptions = value;
    }

    /**
     * Gets the value of the sharePhone property.
     *
     * @return possible object is
     *         {@link Integer }
     */
    public Integer getSharePhone()
    {
        return sharePhone;
    }

    /**
     * Sets the value of the sharePhone property.
     *
     * @param value allowed object is
     *              {@link Integer }
     */
    public void setSharePhone(Integer value)
    {
        this.sharePhone = value;
    }

    /**
     * Gets the value of the shipToAddress property.
     *
     * @return possible object is
     *         {@link AddressType }
     */
    public AddressType getShipToAddress()
    {
        return shipToAddress;
    }

    /**
     * Sets the value of the shipToAddress property.
     *
     * @param value allowed object is
     *              {@link AddressType }
     */
    public void setShipToAddress(AddressType value)
    {
        this.shipToAddress = value;
    }

    /**
     * Gets the value of the buyerEmail property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getBuyerEmail()
    {
        return buyerEmail;
    }

    /**
     * Sets the value of the buyerEmail property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setBuyerEmail(String value)
    {
        this.buyerEmail = value;
    }

}
