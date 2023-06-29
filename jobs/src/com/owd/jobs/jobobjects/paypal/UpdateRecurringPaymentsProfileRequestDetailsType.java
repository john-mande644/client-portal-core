package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for UpdateRecurringPaymentsProfileRequestDetailsType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="UpdateRecurringPaymentsProfileRequestDetailsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="ProfileID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Note" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SubscriberName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SubscriberShippingAddress" type="{urn:ebay:apis:eBLBaseComponents}AddressType" minOccurs="0"/>
 *         &lt;element name="ProfileReference" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AdditionalBillingCycles" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="Amount" type="{urn:ebay:apis:CoreComponentTypes}BasicAmountType" minOccurs="0"/>
 *         &lt;element name="ShippingAmount" type="{urn:ebay:apis:CoreComponentTypes}BasicAmountType" minOccurs="0"/>
 *         &lt;element name="TaxAmount" type="{urn:ebay:apis:CoreComponentTypes}BasicAmountType" minOccurs="0"/>
 *         &lt;element name="OutstandingBalance" type="{urn:ebay:apis:CoreComponentTypes}BasicAmountType" minOccurs="0"/>
 *         &lt;element name="AutoBillOutstandingAmount" type="{urn:ebay:apis:eBLBaseComponents}AutoBillType" minOccurs="0"/>
 *         &lt;element name="MaxFailedPayments" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="CreditCard" type="{urn:ebay:apis:eBLBaseComponents}CreditCardDetailsType" minOccurs="0"/>
 *         &lt;element name="BillingStartDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="TrialPeriod" type="{urn:ebay:apis:eBLBaseComponents}BillingPeriodDetailsType_Update" minOccurs="0"/>
 *         &lt;element name="PaymentPeriod" type="{urn:ebay:apis:eBLBaseComponents}BillingPeriodDetailsType_Update" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UpdateRecurringPaymentsProfileRequestDetailsType", namespace = "urn:ebay:apis:eBLBaseComponents", propOrder = {
        "profileID",
        "note",
        "description",
        "subscriberName",
        "subscriberShippingAddress",
        "profileReference",
        "additionalBillingCycles",
        "amount",
        "shippingAmount",
        "taxAmount",
        "outstandingBalance",
        "autoBillOutstandingAmount",
        "maxFailedPayments",
        "creditCard",
        "billingStartDate",
        "trialPeriod",
        "paymentPeriod"
})
public class UpdateRecurringPaymentsProfileRequestDetailsType
{

    @XmlElement(name = "ProfileID", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected String profileID;
    @XmlElement(name = "Note", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String note;
    @XmlElement(name = "Description", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String description;
    @XmlElement(name = "SubscriberName", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String subscriberName;
    @XmlElement(name = "SubscriberShippingAddress", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected AddressType subscriberShippingAddress;
    @XmlElement(name = "ProfileReference", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String profileReference;
    @XmlElement(name = "AdditionalBillingCycles", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected Integer additionalBillingCycles;
    @XmlElement(name = "Amount", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected BasicAmountType amount;
    @XmlElement(name = "ShippingAmount", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected BasicAmountType shippingAmount;
    @XmlElement(name = "TaxAmount", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected BasicAmountType taxAmount;
    @XmlElement(name = "OutstandingBalance", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected BasicAmountType outstandingBalance;
    @XmlElement(name = "AutoBillOutstandingAmount", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected AutoBillType autoBillOutstandingAmount;
    @XmlElement(name = "MaxFailedPayments", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected Integer maxFailedPayments;
    @XmlElement(name = "CreditCard", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected CreditCardDetailsType creditCard;
    @XmlElement(name = "BillingStartDate", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected XMLGregorianCalendar billingStartDate;
    @XmlElement(name = "TrialPeriod", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected BillingPeriodDetailsTypeUpdate trialPeriod;
    @XmlElement(name = "PaymentPeriod", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected BillingPeriodDetailsTypeUpdate paymentPeriod;

    /**
     * Gets the value of the profileID property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getProfileID()
    {
        return profileID;
    }

    /**
     * Sets the value of the profileID property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setProfileID(String value)
    {
        this.profileID = value;
    }

    /**
     * Gets the value of the note property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getNote()
    {
        return note;
    }

    /**
     * Sets the value of the note property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setNote(String value)
    {
        this.note = value;
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
     * Gets the value of the subscriberName property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getSubscriberName()
    {
        return subscriberName;
    }

    /**
     * Sets the value of the subscriberName property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setSubscriberName(String value)
    {
        this.subscriberName = value;
    }

    /**
     * Gets the value of the subscriberShippingAddress property.
     *
     * @return possible object is
     *         {@link AddressType }
     */
    public AddressType getSubscriberShippingAddress()
    {
        return subscriberShippingAddress;
    }

    /**
     * Sets the value of the subscriberShippingAddress property.
     *
     * @param value allowed object is
     *              {@link AddressType }
     */
    public void setSubscriberShippingAddress(AddressType value)
    {
        this.subscriberShippingAddress = value;
    }

    /**
     * Gets the value of the profileReference property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getProfileReference()
    {
        return profileReference;
    }

    /**
     * Sets the value of the profileReference property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setProfileReference(String value)
    {
        this.profileReference = value;
    }

    /**
     * Gets the value of the additionalBillingCycles property.
     *
     * @return possible object is
     *         {@link Integer }
     */
    public Integer getAdditionalBillingCycles()
    {
        return additionalBillingCycles;
    }

    /**
     * Sets the value of the additionalBillingCycles property.
     *
     * @param value allowed object is
     *              {@link Integer }
     */
    public void setAdditionalBillingCycles(Integer value)
    {
        this.additionalBillingCycles = value;
    }

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
     * Gets the value of the shippingAmount property.
     *
     * @return possible object is
     *         {@link BasicAmountType }
     */
    public BasicAmountType getShippingAmount()
    {
        return shippingAmount;
    }

    /**
     * Sets the value of the shippingAmount property.
     *
     * @param value allowed object is
     *              {@link BasicAmountType }
     */
    public void setShippingAmount(BasicAmountType value)
    {
        this.shippingAmount = value;
    }

    /**
     * Gets the value of the taxAmount property.
     *
     * @return possible object is
     *         {@link BasicAmountType }
     */
    public BasicAmountType getTaxAmount()
    {
        return taxAmount;
    }

    /**
     * Sets the value of the taxAmount property.
     *
     * @param value allowed object is
     *              {@link BasicAmountType }
     */
    public void setTaxAmount(BasicAmountType value)
    {
        this.taxAmount = value;
    }

    /**
     * Gets the value of the outstandingBalance property.
     *
     * @return possible object is
     *         {@link BasicAmountType }
     */
    public BasicAmountType getOutstandingBalance()
    {
        return outstandingBalance;
    }

    /**
     * Sets the value of the outstandingBalance property.
     *
     * @param value allowed object is
     *              {@link BasicAmountType }
     */
    public void setOutstandingBalance(BasicAmountType value)
    {
        this.outstandingBalance = value;
    }

    /**
     * Gets the value of the autoBillOutstandingAmount property.
     *
     * @return possible object is
     *         {@link AutoBillType }
     */
    public AutoBillType getAutoBillOutstandingAmount()
    {
        return autoBillOutstandingAmount;
    }

    /**
     * Sets the value of the autoBillOutstandingAmount property.
     *
     * @param value allowed object is
     *              {@link AutoBillType }
     */
    public void setAutoBillOutstandingAmount(AutoBillType value)
    {
        this.autoBillOutstandingAmount = value;
    }

    /**
     * Gets the value of the maxFailedPayments property.
     *
     * @return possible object is
     *         {@link Integer }
     */
    public Integer getMaxFailedPayments()
    {
        return maxFailedPayments;
    }

    /**
     * Sets the value of the maxFailedPayments property.
     *
     * @param value allowed object is
     *              {@link Integer }
     */
    public void setMaxFailedPayments(Integer value)
    {
        this.maxFailedPayments = value;
    }

    /**
     * Gets the value of the creditCard property.
     *
     * @return possible object is
     *         {@link CreditCardDetailsType }
     */
    public CreditCardDetailsType getCreditCard()
    {
        return creditCard;
    }

    /**
     * Sets the value of the creditCard property.
     *
     * @param value allowed object is
     *              {@link CreditCardDetailsType }
     */
    public void setCreditCard(CreditCardDetailsType value)
    {
        this.creditCard = value;
    }

    /**
     * Gets the value of the billingStartDate property.
     *
     * @return possible object is
     *         {@link XMLGregorianCalendar }
     */
    public XMLGregorianCalendar getBillingStartDate()
    {
        return billingStartDate;
    }

    /**
     * Sets the value of the billingStartDate property.
     *
     * @param value allowed object is
     *              {@link XMLGregorianCalendar }
     */
    public void setBillingStartDate(XMLGregorianCalendar value)
    {
        this.billingStartDate = value;
    }

    /**
     * Gets the value of the trialPeriod property.
     *
     * @return possible object is
     *         {@link BillingPeriodDetailsTypeUpdate }
     */
    public BillingPeriodDetailsTypeUpdate getTrialPeriod()
    {
        return trialPeriod;
    }

    /**
     * Sets the value of the trialPeriod property.
     *
     * @param value allowed object is
     *              {@link BillingPeriodDetailsTypeUpdate }
     */
    public void setTrialPeriod(BillingPeriodDetailsTypeUpdate value)
    {
        this.trialPeriod = value;
    }

    /**
     * Gets the value of the paymentPeriod property.
     *
     * @return possible object is
     *         {@link BillingPeriodDetailsTypeUpdate }
     */
    public BillingPeriodDetailsTypeUpdate getPaymentPeriod()
    {
        return paymentPeriod;
    }

    /**
     * Sets the value of the paymentPeriod property.
     *
     * @param value allowed object is
     *              {@link BillingPeriodDetailsTypeUpdate }
     */
    public void setPaymentPeriod(BillingPeriodDetailsTypeUpdate value)
    {
        this.paymentPeriod = value;
    }

}
