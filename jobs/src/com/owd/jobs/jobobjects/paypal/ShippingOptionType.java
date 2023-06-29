package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Fallback shipping options type.
 * <p/>
 * <p/>
 * <p>Java class for ShippingOptionType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="ShippingOptionType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="ShippingOptionIsDefault" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ShippingOptionAmount" type="{urn:ebay:apis:CoreComponentTypes}BasicAmountType" minOccurs="0"/>
 *         &lt;element name="ShippingOptionName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ShippingOptionType", namespace = "urn:ebay:apis:eBLBaseComponents", propOrder = {
        "shippingOptionIsDefault",
        "shippingOptionAmount",
        "shippingOptionName"
})
public class ShippingOptionType
{

    @XmlElement(name = "ShippingOptionIsDefault", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String shippingOptionIsDefault;
    @XmlElement(name = "ShippingOptionAmount", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected BasicAmountType shippingOptionAmount;
    @XmlElement(name = "ShippingOptionName", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String shippingOptionName;

    /**
     * Gets the value of the shippingOptionIsDefault property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getShippingOptionIsDefault()
    {
        return shippingOptionIsDefault;
    }

    /**
     * Sets the value of the shippingOptionIsDefault property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setShippingOptionIsDefault(String value)
    {
        this.shippingOptionIsDefault = value;
    }

    /**
     * Gets the value of the shippingOptionAmount property.
     *
     * @return possible object is
     *         {@link BasicAmountType }
     */
    public BasicAmountType getShippingOptionAmount()
    {
        return shippingOptionAmount;
    }

    /**
     * Sets the value of the shippingOptionAmount property.
     *
     * @param value allowed object is
     *              {@link BasicAmountType }
     */
    public void setShippingOptionAmount(BasicAmountType value)
    {
        this.shippingOptionAmount = value;
    }

    /**
     * Gets the value of the shippingOptionName property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getShippingOptionName()
    {
        return shippingOptionName;
    }

    /**
     * Sets the value of the shippingOptionName property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setShippingOptionName(String value)
    {
        this.shippingOptionName = value;
    }

}
