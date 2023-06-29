package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * PaymentItemType
 * Information about a Payment Item.
 * <p/>
 * <p/>
 * <p>Java class for PaymentItemType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="PaymentItemType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Number" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Quantity" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SalesTax" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ShippingAmount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="HandlingAmount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Amount" type="{urn:ebay:apis:CoreComponentTypes}BasicAmountType" minOccurs="0"/>
 *         &lt;element name="Options" type="{urn:ebay:apis:eBLBaseComponents}OptionType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PaymentItemType", namespace = "urn:ebay:apis:eBLBaseComponents", propOrder = {
        "name",
        "number",
        "quantity",
        "salesTax",
        "shippingAmount",
        "handlingAmount",
        "amount",
        "options"
})
public class PaymentItemType
{

    @XmlElement(name = "Name", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String name;
    @XmlElement(name = "Number", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String number;
    @XmlElement(name = "Quantity", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String quantity;
    @XmlElement(name = "SalesTax", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String salesTax;
    @XmlElement(name = "ShippingAmount", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String shippingAmount;
    @XmlElement(name = "HandlingAmount", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String handlingAmount;
    @XmlElement(name = "Amount", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected BasicAmountType amount;
    @XmlElement(name = "Options", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected List<OptionType> options;

    /**
     * Gets the value of the name property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getName()
    {
        return name;
    }

    /**
     * Sets the value of the name property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setName(String value)
    {
        this.name = value;
    }

    /**
     * Gets the value of the number property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getNumber()
    {
        return number;
    }

    /**
     * Sets the value of the number property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setNumber(String value)
    {
        this.number = value;
    }

    /**
     * Gets the value of the quantity property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getQuantity()
    {
        return quantity;
    }

    /**
     * Sets the value of the quantity property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setQuantity(String value)
    {
        this.quantity = value;
    }

    /**
     * Gets the value of the salesTax property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getSalesTax()
    {
        return salesTax;
    }

    /**
     * Sets the value of the salesTax property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setSalesTax(String value)
    {
        this.salesTax = value;
    }

    /**
     * Gets the value of the shippingAmount property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getShippingAmount()
    {
        return shippingAmount;
    }

    /**
     * Sets the value of the shippingAmount property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setShippingAmount(String value)
    {
        this.shippingAmount = value;
    }

    /**
     * Gets the value of the handlingAmount property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getHandlingAmount()
    {
        return handlingAmount;
    }

    /**
     * Sets the value of the handlingAmount property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setHandlingAmount(String value)
    {
        this.handlingAmount = value;
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
     * Gets the value of the options property.
     * <p/>
     * <p/>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the options property.
     * <p/>
     * <p/>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOptions().add(newItem);
     * </pre>
     * <p/>
     * <p/>
     * <p/>
     * Objects of the following type(s) are allowed in the list
     * {@link OptionType }
     */
    public List<OptionType> getOptions()
    {
        if (options == null)
        {
            options = new ArrayList<OptionType>();
        }
        return this.options;
    }

}
