package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ItemTrackingDetailsType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="ItemTrackingDetailsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="ItemNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ItemQty" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ItemQtyDelta" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ItemAlert" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ItemCost" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ItemTrackingDetailsType", namespace = "urn:ebay:apis:eBLBaseComponents", propOrder = {
        "itemNumber",
        "itemQty",
        "itemQtyDelta",
        "itemAlert",
        "itemCost"
})
public class ItemTrackingDetailsType
{

    @XmlElement(name = "ItemNumber", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String itemNumber;
    @XmlElement(name = "ItemQty", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String itemQty;
    @XmlElement(name = "ItemQtyDelta", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String itemQtyDelta;
    @XmlElement(name = "ItemAlert", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String itemAlert;
    @XmlElement(name = "ItemCost", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String itemCost;

    /**
     * Gets the value of the itemNumber property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getItemNumber()
    {
        return itemNumber;
    }

    /**
     * Sets the value of the itemNumber property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setItemNumber(String value)
    {
        this.itemNumber = value;
    }

    /**
     * Gets the value of the itemQty property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getItemQty()
    {
        return itemQty;
    }

    /**
     * Sets the value of the itemQty property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setItemQty(String value)
    {
        this.itemQty = value;
    }

    /**
     * Gets the value of the itemQtyDelta property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getItemQtyDelta()
    {
        return itemQtyDelta;
    }

    /**
     * Sets the value of the itemQtyDelta property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setItemQtyDelta(String value)
    {
        this.itemQtyDelta = value;
    }

    /**
     * Gets the value of the itemAlert property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getItemAlert()
    {
        return itemAlert;
    }

    /**
     * Sets the value of the itemAlert property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setItemAlert(String value)
    {
        this.itemAlert = value;
    }

    /**
     * Gets the value of the itemCost property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getItemCost()
    {
        return itemCost;
    }

    /**
     * Sets the value of the itemCost property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setItemCost(String value)
    {
        this.itemCost = value;
    }

}
