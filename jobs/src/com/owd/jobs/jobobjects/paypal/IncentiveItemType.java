package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigInteger;


/**
 * <p>Java class for IncentiveItemType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="IncentiveItemType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="ItemId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PurchaseTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="ItemCategoryList" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ItemPrice" type="{urn:ebay:apis:CoreComponentTypes}BasicAmountType" minOccurs="0"/>
 *         &lt;element name="ItemQuantity" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IncentiveItemType", namespace = "urn:ebay:apis:eBLBaseComponents", propOrder = {
        "itemId",
        "purchaseTime",
        "itemCategoryList",
        "itemPrice",
        "itemQuantity"
})
public class IncentiveItemType
{

    @XmlElement(name = "ItemId", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String itemId;
    @XmlElement(name = "PurchaseTime", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected XMLGregorianCalendar purchaseTime;
    @XmlElement(name = "ItemCategoryList", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String itemCategoryList;
    @XmlElement(name = "ItemPrice", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected BasicAmountType itemPrice;
    @XmlElement(name = "ItemQuantity", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected BigInteger itemQuantity;

    /**
     * Gets the value of the itemId property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getItemId()
    {
        return itemId;
    }

    /**
     * Sets the value of the itemId property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setItemId(String value)
    {
        this.itemId = value;
    }

    /**
     * Gets the value of the purchaseTime property.
     *
     * @return possible object is
     *         {@link XMLGregorianCalendar }
     */
    public XMLGregorianCalendar getPurchaseTime()
    {
        return purchaseTime;
    }

    /**
     * Sets the value of the purchaseTime property.
     *
     * @param value allowed object is
     *              {@link XMLGregorianCalendar }
     */
    public void setPurchaseTime(XMLGregorianCalendar value)
    {
        this.purchaseTime = value;
    }

    /**
     * Gets the value of the itemCategoryList property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getItemCategoryList()
    {
        return itemCategoryList;
    }

    /**
     * Sets the value of the itemCategoryList property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setItemCategoryList(String value)
    {
        this.itemCategoryList = value;
    }

    /**
     * Gets the value of the itemPrice property.
     *
     * @return possible object is
     *         {@link BasicAmountType }
     */
    public BasicAmountType getItemPrice()
    {
        return itemPrice;
    }

    /**
     * Sets the value of the itemPrice property.
     *
     * @param value allowed object is
     *              {@link BasicAmountType }
     */
    public void setItemPrice(BasicAmountType value)
    {
        this.itemPrice = value;
    }

    /**
     * Gets the value of the itemQuantity property.
     *
     * @return possible object is
     *         {@link BigInteger }
     */
    public BigInteger getItemQuantity()
    {
        return itemQuantity;
    }

    /**
     * Sets the value of the itemQuantity property.
     *
     * @param value allowed object is
     *              {@link BigInteger }
     */
    public void setItemQuantity(BigInteger value)
    {
        this.itemQuantity = value;
    }

}
