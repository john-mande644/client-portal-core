package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TaxInfoType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="TaxInfoType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="TaxAmount" type="{urn:ebay:apis:CoreComponentTypes}BasicAmountType"/>
 *         &lt;element name="SalesTaxPercentage" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="TaxState" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TaxInfoType", namespace = "urn:ebay:apis:eBLBaseComponents", propOrder = {
        "taxAmount",
        "salesTaxPercentage",
        "taxState"
})
public class TaxInfoType
{

    @XmlElement(name = "TaxAmount", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected BasicAmountType taxAmount;
    @XmlElement(name = "SalesTaxPercentage", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected String salesTaxPercentage;
    @XmlElement(name = "TaxState", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected String taxState;

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
     * Gets the value of the salesTaxPercentage property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getSalesTaxPercentage()
    {
        return salesTaxPercentage;
    }

    /**
     * Sets the value of the salesTaxPercentage property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setSalesTaxPercentage(String value)
    {
        this.salesTaxPercentage = value;
    }

    /**
     * Gets the value of the taxState property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getTaxState()
    {
        return taxState;
    }

    /**
     * Sets the value of the taxState property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setTaxState(String value)
    {
        this.taxState = value;
    }

}
