package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for RecurringPaymentsProfileDetailsType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="RecurringPaymentsProfileDetailsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="SubscriberName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SubscriberShippingAddress" type="{urn:ebay:apis:eBLBaseComponents}AddressType" minOccurs="0"/>
 *         &lt;element name="BillingStartDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="ProfileReference" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RecurringPaymentsProfileDetailsType", namespace = "urn:ebay:apis:eBLBaseComponents", propOrder = {
        "subscriberName",
        "subscriberShippingAddress",
        "billingStartDate",
        "profileReference"
})
public class RecurringPaymentsProfileDetailsType
{

    @XmlElement(name = "SubscriberName", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String subscriberName;
    @XmlElement(name = "SubscriberShippingAddress", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected AddressType subscriberShippingAddress;
    @XmlElement(name = "BillingStartDate", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected XMLGregorianCalendar billingStartDate;
    @XmlElement(name = "ProfileReference", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String profileReference;

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

}
