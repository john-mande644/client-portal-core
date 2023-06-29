package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SalesTaxType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="SalesTaxType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="SalesTaxPercent" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/>
 *         &lt;element name="SalesTaxState" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ShippingIncludedInTax" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SalesTaxType", namespace = "urn:ebay:apis:eBLBaseComponents", propOrder = {
        "salesTaxPercent",
        "salesTaxState",
        "shippingIncludedInTax"
})
public class SalesTaxType
{

    @XmlElement(name = "SalesTaxPercent", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected Float salesTaxPercent;
    @XmlElement(name = "SalesTaxState", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String salesTaxState;
    @XmlElement(name = "ShippingIncludedInTax", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected Boolean shippingIncludedInTax;

    /**
     * Gets the value of the salesTaxPercent property.
     *
     * @return possible object is
     *         {@link Float }
     */
    public Float getSalesTaxPercent()
    {
        return salesTaxPercent;
    }

    /**
     * Sets the value of the salesTaxPercent property.
     *
     * @param value allowed object is
     *              {@link Float }
     */
    public void setSalesTaxPercent(Float value)
    {
        this.salesTaxPercent = value;
    }

    /**
     * Gets the value of the salesTaxState property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getSalesTaxState()
    {
        return salesTaxState;
    }

    /**
     * Sets the value of the salesTaxState property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setSalesTaxState(String value)
    {
        this.salesTaxState = value;
    }

    /**
     * Gets the value of the shippingIncludedInTax property.
     *
     * @return possible object is
     *         {@link Boolean }
     */
    public Boolean isShippingIncludedInTax()
    {
        return shippingIncludedInTax;
    }

    /**
     * Sets the value of the shippingIncludedInTax property.
     *
     * @param value allowed object is
     *              {@link Boolean }
     */
    public void setShippingIncludedInTax(Boolean value)
    {
        this.shippingIncludedInTax = value;
    }

}
