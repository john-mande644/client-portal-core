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
 * Merchandizing info for an Item. This contains a list of crosssell
 * or upsell items.
 * PrimaryScheme, PromotionMethod,SellerId,ItemId, ShippingDiscount
 * do not have be min occur 0
 * <p/>
 * <p/>
 * <p>Java class for CrossPromotionsType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="CrossPromotionsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="ItemID" type="{urn:ebay:apis:eBLBaseComponents}ItemIDType"/>
 *         &lt;element name="PrimaryScheme" type="{urn:ebay:apis:eBLBaseComponents}PromotionSchemeCodeType"/>
 *         &lt;element name="PromotionMethod" type="{urn:ebay:apis:eBLBaseComponents}PromotionMethodCodeType"/>
 *         &lt;element name="SellerID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ShippingDiscount" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="SellerKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="StoreName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PromotedItem" type="{urn:ebay:apis:eBLBaseComponents}PromotedItemType" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CrossPromotionsType", namespace = "urn:ebay:apis:eBLBaseComponents", propOrder = {
        "itemID",
        "primaryScheme",
        "promotionMethod",
        "sellerID",
        "shippingDiscount",
        "sellerKey",
        "storeName",
        "promotedItem"
})
public class CrossPromotionsType
{

    @XmlElement(name = "ItemID", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected String itemID;
    @XmlElement(name = "PrimaryScheme", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected PromotionSchemeCodeType primaryScheme;
    @XmlElement(name = "PromotionMethod", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected PromotionMethodCodeType promotionMethod;
    @XmlElement(name = "SellerID", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected String sellerID;
    @XmlElement(name = "ShippingDiscount", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected boolean shippingDiscount;
    @XmlElement(name = "SellerKey", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String sellerKey;
    @XmlElement(name = "StoreName", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String storeName;
    @XmlElement(name = "PromotedItem", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected List<PromotedItemType> promotedItem;

    /**
     * Gets the value of the itemID property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getItemID()
    {
        return itemID;
    }

    /**
     * Sets the value of the itemID property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setItemID(String value)
    {
        this.itemID = value;
    }

    /**
     * Gets the value of the primaryScheme property.
     *
     * @return possible object is
     *         {@link PromotionSchemeCodeType }
     */
    public PromotionSchemeCodeType getPrimaryScheme()
    {
        return primaryScheme;
    }

    /**
     * Sets the value of the primaryScheme property.
     *
     * @param value allowed object is
     *              {@link PromotionSchemeCodeType }
     */
    public void setPrimaryScheme(PromotionSchemeCodeType value)
    {
        this.primaryScheme = value;
    }

    /**
     * Gets the value of the promotionMethod property.
     *
     * @return possible object is
     *         {@link PromotionMethodCodeType }
     */
    public PromotionMethodCodeType getPromotionMethod()
    {
        return promotionMethod;
    }

    /**
     * Sets the value of the promotionMethod property.
     *
     * @param value allowed object is
     *              {@link PromotionMethodCodeType }
     */
    public void setPromotionMethod(PromotionMethodCodeType value)
    {
        this.promotionMethod = value;
    }

    /**
     * Gets the value of the sellerID property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getSellerID()
    {
        return sellerID;
    }

    /**
     * Sets the value of the sellerID property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setSellerID(String value)
    {
        this.sellerID = value;
    }

    /**
     * Gets the value of the shippingDiscount property.
     */
    public boolean isShippingDiscount()
    {
        return shippingDiscount;
    }

    /**
     * Sets the value of the shippingDiscount property.
     */
    public void setShippingDiscount(boolean value)
    {
        this.shippingDiscount = value;
    }

    /**
     * Gets the value of the sellerKey property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getSellerKey()
    {
        return sellerKey;
    }

    /**
     * Sets the value of the sellerKey property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setSellerKey(String value)
    {
        this.sellerKey = value;
    }

    /**
     * Gets the value of the storeName property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getStoreName()
    {
        return storeName;
    }

    /**
     * Sets the value of the storeName property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setStoreName(String value)
    {
        this.storeName = value;
    }

    /**
     * Gets the value of the promotedItem property.
     * <p/>
     * <p/>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the promotedItem property.
     * <p/>
     * <p/>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPromotedItem().add(newItem);
     * </pre>
     * <p/>
     * <p/>
     * <p/>
     * Objects of the following type(s) are allowed in the list
     * {@link PromotedItemType }
     */
    public List<PromotedItemType> getPromotedItem()
    {
        if (promotedItem == null)
        {
            promotedItem = new ArrayList<PromotedItemType>();
        }
        return this.promotedItem;
    }

}
