package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for GetRecurringPaymentsProfileDetailsResponseDetailsType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="GetRecurringPaymentsProfileDetailsResponseDetailsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="ProfileID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ProfileStatus" type="{urn:ebay:apis:eBLBaseComponents}RecurringPaymentsProfileStatusType"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="AutoBillOutstandingAmount" type="{urn:ebay:apis:eBLBaseComponents}AutoBillType"/>
 *         &lt;element name="MaxFailedPayments" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="RecurringPaymentsProfileDetails" type="{urn:ebay:apis:eBLBaseComponents}RecurringPaymentsProfileDetailsType"/>
 *         &lt;element name="CurrentRecurringPaymentsPeriod" type="{urn:ebay:apis:eBLBaseComponents}BillingPeriodDetailsType" minOccurs="0"/>
 *         &lt;element name="RecurringPaymentsSummary" type="{urn:ebay:apis:eBLBaseComponents}RecurringPaymentsSummaryType"/>
 *         &lt;element name="CreditCard" type="{urn:ebay:apis:eBLBaseComponents}CreditCardDetailsType" minOccurs="0"/>
 *         &lt;element name="TrialRecurringPaymentsPeriod" type="{urn:ebay:apis:eBLBaseComponents}BillingPeriodDetailsType" minOccurs="0"/>
 *         &lt;element name="RegularRecurringPaymentsPeriod" type="{urn:ebay:apis:eBLBaseComponents}BillingPeriodDetailsType" minOccurs="0"/>
 *         &lt;element name="TrialAmountPaid" type="{urn:ebay:apis:CoreComponentTypes}BasicAmountType" minOccurs="0"/>
 *         &lt;element name="RegularAmountPaid" type="{urn:ebay:apis:CoreComponentTypes}BasicAmountType" minOccurs="0"/>
 *         &lt;element name="AggregateAmount" type="{urn:ebay:apis:CoreComponentTypes}BasicAmountType" minOccurs="0"/>
 *         &lt;element name="AggregateOptionalAmount" type="{urn:ebay:apis:CoreComponentTypes}BasicAmountType" minOccurs="0"/>
 *         &lt;element name="FinalPaymentDueDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetRecurringPaymentsProfileDetailsResponseDetailsType", namespace = "urn:ebay:apis:eBLBaseComponents", propOrder = {
        "profileID",
        "profileStatus",
        "description",
        "autoBillOutstandingAmount",
        "maxFailedPayments",
        "recurringPaymentsProfileDetails",
        "currentRecurringPaymentsPeriod",
        "recurringPaymentsSummary",
        "creditCard",
        "trialRecurringPaymentsPeriod",
        "regularRecurringPaymentsPeriod",
        "trialAmountPaid",
        "regularAmountPaid",
        "aggregateAmount",
        "aggregateOptionalAmount",
        "finalPaymentDueDate"
})
public class GetRecurringPaymentsProfileDetailsResponseDetailsType
{

    @XmlElement(name = "ProfileID", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected String profileID;
    @XmlElement(name = "ProfileStatus", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected RecurringPaymentsProfileStatusType profileStatus;
    @XmlElement(name = "Description", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected String description;
    @XmlElement(name = "AutoBillOutstandingAmount", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected AutoBillType autoBillOutstandingAmount;
    @XmlElement(name = "MaxFailedPayments", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected int maxFailedPayments;
    @XmlElement(name = "RecurringPaymentsProfileDetails", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected RecurringPaymentsProfileDetailsType recurringPaymentsProfileDetails;
    @XmlElement(name = "CurrentRecurringPaymentsPeriod", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected BillingPeriodDetailsType currentRecurringPaymentsPeriod;
    @XmlElement(name = "RecurringPaymentsSummary", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected RecurringPaymentsSummaryType recurringPaymentsSummary;
    @XmlElement(name = "CreditCard", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected CreditCardDetailsType creditCard;
    @XmlElement(name = "TrialRecurringPaymentsPeriod", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected BillingPeriodDetailsType trialRecurringPaymentsPeriod;
    @XmlElement(name = "RegularRecurringPaymentsPeriod", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected BillingPeriodDetailsType regularRecurringPaymentsPeriod;
    @XmlElement(name = "TrialAmountPaid", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected BasicAmountType trialAmountPaid;
    @XmlElement(name = "RegularAmountPaid", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected BasicAmountType regularAmountPaid;
    @XmlElement(name = "AggregateAmount", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected BasicAmountType aggregateAmount;
    @XmlElement(name = "AggregateOptionalAmount", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected BasicAmountType aggregateOptionalAmount;
    @XmlElement(name = "FinalPaymentDueDate", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected XMLGregorianCalendar finalPaymentDueDate;

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
     * Gets the value of the profileStatus property.
     *
     * @return possible object is
     *         {@link RecurringPaymentsProfileStatusType }
     */
    public RecurringPaymentsProfileStatusType getProfileStatus()
    {
        return profileStatus;
    }

    /**
     * Sets the value of the profileStatus property.
     *
     * @param value allowed object is
     *              {@link RecurringPaymentsProfileStatusType }
     */
    public void setProfileStatus(RecurringPaymentsProfileStatusType value)
    {
        this.profileStatus = value;
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
     */
    public int getMaxFailedPayments()
    {
        return maxFailedPayments;
    }

    /**
     * Sets the value of the maxFailedPayments property.
     */
    public void setMaxFailedPayments(int value)
    {
        this.maxFailedPayments = value;
    }

    /**
     * Gets the value of the recurringPaymentsProfileDetails property.
     *
     * @return possible object is
     *         {@link RecurringPaymentsProfileDetailsType }
     */
    public RecurringPaymentsProfileDetailsType getRecurringPaymentsProfileDetails()
    {
        return recurringPaymentsProfileDetails;
    }

    /**
     * Sets the value of the recurringPaymentsProfileDetails property.
     *
     * @param value allowed object is
     *              {@link RecurringPaymentsProfileDetailsType }
     */
    public void setRecurringPaymentsProfileDetails(RecurringPaymentsProfileDetailsType value)
    {
        this.recurringPaymentsProfileDetails = value;
    }

    /**
     * Gets the value of the currentRecurringPaymentsPeriod property.
     *
     * @return possible object is
     *         {@link BillingPeriodDetailsType }
     */
    public BillingPeriodDetailsType getCurrentRecurringPaymentsPeriod()
    {
        return currentRecurringPaymentsPeriod;
    }

    /**
     * Sets the value of the currentRecurringPaymentsPeriod property.
     *
     * @param value allowed object is
     *              {@link BillingPeriodDetailsType }
     */
    public void setCurrentRecurringPaymentsPeriod(BillingPeriodDetailsType value)
    {
        this.currentRecurringPaymentsPeriod = value;
    }

    /**
     * Gets the value of the recurringPaymentsSummary property.
     *
     * @return possible object is
     *         {@link RecurringPaymentsSummaryType }
     */
    public RecurringPaymentsSummaryType getRecurringPaymentsSummary()
    {
        return recurringPaymentsSummary;
    }

    /**
     * Sets the value of the recurringPaymentsSummary property.
     *
     * @param value allowed object is
     *              {@link RecurringPaymentsSummaryType }
     */
    public void setRecurringPaymentsSummary(RecurringPaymentsSummaryType value)
    {
        this.recurringPaymentsSummary = value;
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
     * Gets the value of the trialRecurringPaymentsPeriod property.
     *
     * @return possible object is
     *         {@link BillingPeriodDetailsType }
     */
    public BillingPeriodDetailsType getTrialRecurringPaymentsPeriod()
    {
        return trialRecurringPaymentsPeriod;
    }

    /**
     * Sets the value of the trialRecurringPaymentsPeriod property.
     *
     * @param value allowed object is
     *              {@link BillingPeriodDetailsType }
     */
    public void setTrialRecurringPaymentsPeriod(BillingPeriodDetailsType value)
    {
        this.trialRecurringPaymentsPeriod = value;
    }

    /**
     * Gets the value of the regularRecurringPaymentsPeriod property.
     *
     * @return possible object is
     *         {@link BillingPeriodDetailsType }
     */
    public BillingPeriodDetailsType getRegularRecurringPaymentsPeriod()
    {
        return regularRecurringPaymentsPeriod;
    }

    /**
     * Sets the value of the regularRecurringPaymentsPeriod property.
     *
     * @param value allowed object is
     *              {@link BillingPeriodDetailsType }
     */
    public void setRegularRecurringPaymentsPeriod(BillingPeriodDetailsType value)
    {
        this.regularRecurringPaymentsPeriod = value;
    }

    /**
     * Gets the value of the trialAmountPaid property.
     *
     * @return possible object is
     *         {@link BasicAmountType }
     */
    public BasicAmountType getTrialAmountPaid()
    {
        return trialAmountPaid;
    }

    /**
     * Sets the value of the trialAmountPaid property.
     *
     * @param value allowed object is
     *              {@link BasicAmountType }
     */
    public void setTrialAmountPaid(BasicAmountType value)
    {
        this.trialAmountPaid = value;
    }

    /**
     * Gets the value of the regularAmountPaid property.
     *
     * @return possible object is
     *         {@link BasicAmountType }
     */
    public BasicAmountType getRegularAmountPaid()
    {
        return regularAmountPaid;
    }

    /**
     * Sets the value of the regularAmountPaid property.
     *
     * @param value allowed object is
     *              {@link BasicAmountType }
     */
    public void setRegularAmountPaid(BasicAmountType value)
    {
        this.regularAmountPaid = value;
    }

    /**
     * Gets the value of the aggregateAmount property.
     *
     * @return possible object is
     *         {@link BasicAmountType }
     */
    public BasicAmountType getAggregateAmount()
    {
        return aggregateAmount;
    }

    /**
     * Sets the value of the aggregateAmount property.
     *
     * @param value allowed object is
     *              {@link BasicAmountType }
     */
    public void setAggregateAmount(BasicAmountType value)
    {
        this.aggregateAmount = value;
    }

    /**
     * Gets the value of the aggregateOptionalAmount property.
     *
     * @return possible object is
     *         {@link BasicAmountType }
     */
    public BasicAmountType getAggregateOptionalAmount()
    {
        return aggregateOptionalAmount;
    }

    /**
     * Sets the value of the aggregateOptionalAmount property.
     *
     * @param value allowed object is
     *              {@link BasicAmountType }
     */
    public void setAggregateOptionalAmount(BasicAmountType value)
    {
        this.aggregateOptionalAmount = value;
    }

    /**
     * Gets the value of the finalPaymentDueDate property.
     *
     * @return possible object is
     *         {@link XMLGregorianCalendar }
     */
    public XMLGregorianCalendar getFinalPaymentDueDate()
    {
        return finalPaymentDueDate;
    }

    /**
     * Sets the value of the finalPaymentDueDate property.
     *
     * @param value allowed object is
     *              {@link XMLGregorianCalendar }
     */
    public void setFinalPaymentDueDate(XMLGregorianCalendar value)
    {
        this.finalPaymentDueDate = value;
    }

}
