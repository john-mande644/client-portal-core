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
 * PaymentItemInfoType
 * Information about a PayPal item.
 * <p/>
 * <p/>
 * <p>Java class for PaymentItemInfoType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="PaymentItemInfoType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="InvoiceID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Custom" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Memo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SalesTax" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PaymentItem" type="{urn:ebay:apis:eBLBaseComponents}PaymentItemType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="Subscription" type="{urn:ebay:apis:eBLBaseComponents}SubscriptionInfoType" minOccurs="0"/>
 *         &lt;element name="Auction" type="{urn:ebay:apis:eBLBaseComponents}AuctionInfoType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PaymentItemInfoType", namespace = "urn:ebay:apis:eBLBaseComponents", propOrder = {
        "invoiceID",
        "custom",
        "memo",
        "salesTax",
        "paymentItem",
        "subscription",
        "auction"
})
public class PaymentItemInfoType
{

    @XmlElement(name = "InvoiceID", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String invoiceID;
    @XmlElement(name = "Custom", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String custom;
    @XmlElement(name = "Memo", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String memo;
    @XmlElement(name = "SalesTax", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String salesTax;
    @XmlElement(name = "PaymentItem", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected List<PaymentItemType> paymentItem;
    @XmlElement(name = "Subscription", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected SubscriptionInfoType subscription;
    @XmlElement(name = "Auction", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected AuctionInfoType auction;

    /**
     * Gets the value of the invoiceID property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getInvoiceID()
    {
        return invoiceID;
    }

    /**
     * Sets the value of the invoiceID property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setInvoiceID(String value)
    {
        this.invoiceID = value;
    }

    /**
     * Gets the value of the custom property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getCustom()
    {
        return custom;
    }

    /**
     * Sets the value of the custom property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setCustom(String value)
    {
        this.custom = value;
    }

    /**
     * Gets the value of the memo property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getMemo()
    {
        return memo;
    }

    /**
     * Sets the value of the memo property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setMemo(String value)
    {
        this.memo = value;
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
     * Gets the value of the paymentItem property.
     * <p/>
     * <p/>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the paymentItem property.
     * <p/>
     * <p/>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPaymentItem().add(newItem);
     * </pre>
     * <p/>
     * <p/>
     * <p/>
     * Objects of the following type(s) are allowed in the list
     * {@link PaymentItemType }
     */
    public List<PaymentItemType> getPaymentItem()
    {
        if (paymentItem == null)
        {
            paymentItem = new ArrayList<PaymentItemType>();
        }
        return this.paymentItem;
    }

    /**
     * Gets the value of the subscription property.
     *
     * @return possible object is
     *         {@link SubscriptionInfoType }
     */
    public SubscriptionInfoType getSubscription()
    {
        return subscription;
    }

    /**
     * Sets the value of the subscription property.
     *
     * @param value allowed object is
     *              {@link SubscriptionInfoType }
     */
    public void setSubscription(SubscriptionInfoType value)
    {
        this.subscription = value;
    }

    /**
     * Gets the value of the auction property.
     *
     * @return possible object is
     *         {@link AuctionInfoType }
     */
    public AuctionInfoType getAuction()
    {
        return auction;
    }

    /**
     * Sets the value of the auction property.
     *
     * @param value allowed object is
     *              {@link AuctionInfoType }
     */
    public void setAuction(AuctionInfoType value)
    {
        this.auction = value;
    }

}
