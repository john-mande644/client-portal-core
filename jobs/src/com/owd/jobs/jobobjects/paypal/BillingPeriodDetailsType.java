package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BillingPeriodDetailsType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="BillingPeriodDetailsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="BillingPeriod" type="{urn:ebay:apis:eBLBaseComponents}BillingPeriodType"/>
 *         &lt;element name="BillingFrequency" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="TotalBillingCycles" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="Amount" type="{urn:ebay:apis:CoreComponentTypes}BasicAmountType"/>
 *         &lt;element name="ShippingAmount" type="{urn:ebay:apis:CoreComponentTypes}BasicAmountType" minOccurs="0"/>
 *         &lt;element name="TaxAmount" type="{urn:ebay:apis:CoreComponentTypes}BasicAmountType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BillingPeriodDetailsType", namespace = "urn:ebay:apis:eBLBaseComponents", propOrder = {
        "billingPeriod",
        "billingFrequency",
        "totalBillingCycles",
        "amount",
        "shippingAmount",
        "taxAmount"
})
public class BillingPeriodDetailsType
{

    @XmlElement(name = "BillingPeriod", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected BillingPeriodType billingPeriod;
    @XmlElement(name = "BillingFrequency", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected int billingFrequency;
    @XmlElement(name = "TotalBillingCycles", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected Integer totalBillingCycles;
    @XmlElement(name = "Amount", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected BasicAmountType amount;
    @XmlElement(name = "ShippingAmount", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected BasicAmountType shippingAmount;
    @XmlElement(name = "TaxAmount", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected BasicAmountType taxAmount;

    /**
     * Gets the value of the billingPeriod property.
     *
     * @return possible object is
     *         {@link BillingPeriodType }
     */
    public BillingPeriodType getBillingPeriod()
    {
        return billingPeriod;
    }

    /**
     * Sets the value of the billingPeriod property.
     *
     * @param value allowed object is
     *              {@link BillingPeriodType }
     */
    public void setBillingPeriod(BillingPeriodType value)
    {
        this.billingPeriod = value;
    }

    /**
     * Gets the value of the billingFrequency property.
     */
    public int getBillingFrequency()
    {
        return billingFrequency;
    }

    /**
     * Sets the value of the billingFrequency property.
     */
    public void setBillingFrequency(int value)
    {
        this.billingFrequency = value;
    }

    /**
     * Gets the value of the totalBillingCycles property.
     *
     * @return possible object is
     *         {@link Integer }
     */
    public Integer getTotalBillingCycles()
    {
        return totalBillingCycles;
    }

    /**
     * Sets the value of the totalBillingCycles property.
     *
     * @param value allowed object is
     *              {@link Integer }
     */
    public void setTotalBillingCycles(Integer value)
    {
        this.totalBillingCycles = value;
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

}
