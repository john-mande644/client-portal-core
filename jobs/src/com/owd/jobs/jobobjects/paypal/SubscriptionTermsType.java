package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.*;


/**
 * SubscriptionTermsType
 * Terms of a PayPal subscription.
 * <p/>
 * <p/>
 * <p>Java class for SubscriptionTermsType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="SubscriptionTermsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="Amount" type="{urn:ebay:apis:CoreComponentTypes}BasicAmountType"/>
 *       &lt;/sequence>
 *       &lt;attribute name="period" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SubscriptionTermsType", namespace = "urn:ebay:apis:eBLBaseComponents", propOrder = {
        "amount"
})
public class SubscriptionTermsType
{

    @XmlElement(name = "Amount", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected BasicAmountType amount;
    @XmlAttribute(required = true)
    protected String period;

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
     * Gets the value of the period property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getPeriod()
    {
        return period;
    }

    /**
     * Sets the value of the period property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setPeriod(String value)
    {
        this.period = value;
    }

}
