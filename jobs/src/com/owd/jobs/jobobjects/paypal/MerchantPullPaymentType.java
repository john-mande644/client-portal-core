package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * MerchantPullPayment
 * Parameters to make initiate a pull payment
 * <p/>
 * <p/>
 * <p>Java class for MerchantPullPaymentType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="MerchantPullPaymentType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="Amount" type="{urn:ebay:apis:CoreComponentTypes}BasicAmountType"/>
 *         &lt;element name="MpID" type="{urn:ebay:apis:eBLBaseComponents}MerchantPullIDType"/>
 *         &lt;element name="PaymentType" type="{urn:ebay:apis:eBLBaseComponents}MerchantPullPaymentCodeType" minOccurs="0"/>
 *         &lt;element name="Memo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EmailSubject" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Tax" type="{urn:ebay:apis:CoreComponentTypes}BasicAmountType" minOccurs="0"/>
 *         &lt;element name="Shipping" type="{urn:ebay:apis:CoreComponentTypes}BasicAmountType" minOccurs="0"/>
 *         &lt;element name="Handling" type="{urn:ebay:apis:CoreComponentTypes}BasicAmountType" minOccurs="0"/>
 *         &lt;element name="ItemName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ItemNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Invoice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Custom" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ButtonSource" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SoftDescriptor" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MerchantPullPaymentType", namespace = "urn:ebay:apis:eBLBaseComponents", propOrder = {
        "amount",
        "mpID",
        "paymentType",
        "memo",
        "emailSubject",
        "tax",
        "shipping",
        "handling",
        "itemName",
        "itemNumber",
        "invoice",
        "custom",
        "buttonSource",
        "softDescriptor"
})
public class MerchantPullPaymentType
{

    @XmlElement(name = "Amount", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected BasicAmountType amount;
    @XmlElement(name = "MpID", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected String mpID;
    @XmlElement(name = "PaymentType", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected MerchantPullPaymentCodeType paymentType;
    @XmlElement(name = "Memo", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String memo;
    @XmlElement(name = "EmailSubject", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String emailSubject;
    @XmlElement(name = "Tax", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected BasicAmountType tax;
    @XmlElement(name = "Shipping", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected BasicAmountType shipping;
    @XmlElement(name = "Handling", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected BasicAmountType handling;
    @XmlElement(name = "ItemName", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String itemName;
    @XmlElement(name = "ItemNumber", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String itemNumber;
    @XmlElement(name = "Invoice", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String invoice;
    @XmlElement(name = "Custom", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String custom;
    @XmlElement(name = "ButtonSource", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String buttonSource;
    @XmlElement(name = "SoftDescriptor", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String softDescriptor;

    /**
     * Gets the value of the amount property.
     *
     * @return possible object is
     *         {@link BasicAmountType }
     */
    public BasicAmountType getAmount()
    {
        return amount;
    }

    /**
     * Sets the value of the amount property.
     *
     * @param value allowed object is
     *              {@link BasicAmountType }
     */
    public void setAmount(BasicAmountType value)
    {
        this.amount = value;
    }

    /**
     * Gets the value of the mpID property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getMpID()
    {
        return mpID;
    }

    /**
     * Sets the value of the mpID property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setMpID(String value)
    {
        this.mpID = value;
    }

    /**
     * Gets the value of the paymentType property.
     *
     * @return possible object is
     *         {@link MerchantPullPaymentCodeType }
     */
    public MerchantPullPaymentCodeType getPaymentType()
    {
        return paymentType;
    }

    /**
     * Sets the value of the paymentType property.
     *
     * @param value allowed object is
     *              {@link MerchantPullPaymentCodeType }
     */
    public void setPaymentType(MerchantPullPaymentCodeType value)
    {
        this.paymentType = value;
    }

    /**
     * Gets the value of the memo property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getMemo()
    {
        return memo;
    }

    /**
     * Sets the value of the memo property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setMemo(String value)
    {
        this.memo = value;
    }

    /**
     * Gets the value of the emailSubject property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getEmailSubject()
    {
        return emailSubject;
    }

    /**
     * Sets the value of the emailSubject property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setEmailSubject(String value)
    {
        this.emailSubject = value;
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
     * Gets the value of the handling property.
     *
     * @return possible object is
     *         {@link BasicAmountType }
     */
    public BasicAmountType getHandling()
    {
        return handling;
    }

    /**
     * Sets the value of the handling property.
     *
     * @param value allowed object is
     *              {@link BasicAmountType }
     */
    public void setHandling(BasicAmountType value)
    {
        this.handling = value;
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
     * Gets the value of the invoice property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getInvoice()
    {
        return invoice;
    }

    /**
     * Sets the value of the invoice property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setInvoice(String value)
    {
        this.invoice = value;
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
     * Gets the value of the buttonSource property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getButtonSource()
    {
        return buttonSource;
    }

    /**
     * Sets the value of the buttonSource property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setButtonSource(String value)
    {
        this.buttonSource = value;
    }

    /**
     * Gets the value of the softDescriptor property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getSoftDescriptor()
    {
        return softDescriptor;
    }

    /**
     * Sets the value of the softDescriptor property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setSoftDescriptor(String value)
    {
        this.softDescriptor = value;
    }

}
