package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * EbayItemPaymentDetailsItemType - Type declaration to be used by other schemas.
 * Information about an Ebay Payment Item.
 * <p/>
 * <p/>
 * <p>Java class for EbayItemPaymentDetailsItemType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="EbayItemPaymentDetailsItemType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="ItemNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AuctionTransactionId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OrderId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CartID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EbayItemPaymentDetailsItemType", namespace = "urn:ebay:apis:eBLBaseComponents", propOrder = {
        "itemNumber",
        "auctionTransactionId",
        "orderId",
        "cartID"
})
public class EbayItemPaymentDetailsItemType
{

    @XmlElement(name = "ItemNumber", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String itemNumber;
    @XmlElement(name = "AuctionTransactionId", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String auctionTransactionId;
    @XmlElement(name = "OrderId", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String orderId;
    @XmlElement(name = "CartID", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String cartID;

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
     * Gets the value of the auctionTransactionId property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getAuctionTransactionId()
    {
        return auctionTransactionId;
    }

    /**
     * Sets the value of the auctionTransactionId property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setAuctionTransactionId(String value)
    {
        this.auctionTransactionId = value;
    }

    /**
     * Gets the value of the orderId property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getOrderId()
    {
        return orderId;
    }

    /**
     * Sets the value of the orderId property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setOrderId(String value)
    {
        this.orderId = value;
    }

    /**
     * Gets the value of the cartID property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getCartID()
    {
        return cartID;
    }

    /**
     * Sets the value of the cartID property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setCartID(String value)
    {
        this.cartID = value;
    }

}
