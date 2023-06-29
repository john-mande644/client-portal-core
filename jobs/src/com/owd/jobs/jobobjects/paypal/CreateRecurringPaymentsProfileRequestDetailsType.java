package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CreateRecurringPaymentsProfileRequestDetailsType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="CreateRecurringPaymentsProfileRequestDetailsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="Token" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CreditCard" type="{urn:ebay:apis:eBLBaseComponents}CreditCardDetailsType" minOccurs="0"/>
 *         &lt;element name="RecurringPaymentsProfileDetails" type="{urn:ebay:apis:eBLBaseComponents}RecurringPaymentsProfileDetailsType"/>
 *         &lt;element name="ScheduleDetails" type="{urn:ebay:apis:eBLBaseComponents}ScheduleDetailsType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CreateRecurringPaymentsProfileRequestDetailsType", namespace = "urn:ebay:apis:eBLBaseComponents", propOrder = {
        "token",
        "creditCard",
        "recurringPaymentsProfileDetails",
        "scheduleDetails"
})
public class CreateRecurringPaymentsProfileRequestDetailsType
{

    @XmlElement(name = "Token", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String token;
    @XmlElement(name = "CreditCard", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected CreditCardDetailsType creditCard;
    @XmlElement(name = "RecurringPaymentsProfileDetails", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected RecurringPaymentsProfileDetailsType recurringPaymentsProfileDetails;
    @XmlElement(name = "ScheduleDetails", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected ScheduleDetailsType scheduleDetails;

    /**
     * Gets the value of the token property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getToken()
    {
        return token;
    }

    /**
     * Sets the value of the token property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setToken(String value)
    {
        this.token = value;
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
     * Gets the value of the scheduleDetails property.
     *
     * @return possible object is
     *         {@link ScheduleDetailsType }
     */
    public ScheduleDetailsType getScheduleDetails()
    {
        return scheduleDetails;
    }

    /**
     * Sets the value of the scheduleDetails property.
     *
     * @param value allowed object is
     *              {@link ScheduleDetailsType }
     */
    public void setScheduleDetails(ScheduleDetailsType value)
    {
        this.scheduleDetails = value;
    }

}
