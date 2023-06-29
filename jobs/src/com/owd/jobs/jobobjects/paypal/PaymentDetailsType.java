package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.ArrayList;
import java.util.List;


/**
 * PaymentDetailsType
 * Information about a payment.  Used by DCC and Express Checkout.
 * <p/>
 * <p/>
 * <p>Java class for PaymentDetailsType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="PaymentDetailsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="OrderTotal" type="{urn:ebay:apis:CoreComponentTypes}BasicAmountType" minOccurs="0"/>
 *         &lt;element name="ItemTotal" type="{urn:ebay:apis:CoreComponentTypes}BasicAmountType" minOccurs="0"/>
 *         &lt;element name="ShippingTotal" type="{urn:ebay:apis:CoreComponentTypes}BasicAmountType" minOccurs="0"/>
 *         &lt;element name="HandlingTotal" type="{urn:ebay:apis:CoreComponentTypes}BasicAmountType" minOccurs="0"/>
 *         &lt;element name="TaxTotal" type="{urn:ebay:apis:CoreComponentTypes}BasicAmountType" minOccurs="0"/>
 *         &lt;element name="OrderDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Custom" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="InvoiceID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ButtonSource" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NotifyURL" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ShipToAddress" type="{urn:ebay:apis:eBLBaseComponents}AddressType" minOccurs="0"/>
 *         &lt;element name="ShippingMethod" type="{urn:ebay:apis:eBLBaseComponents}ShippingServiceCodeType" minOccurs="0"/>
 *         &lt;element name="ProfileAddressChangeDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="PaymentDetailsItem" type="{urn:ebay:apis:eBLBaseComponents}PaymentDetailsItemType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="InsuranceTotal" type="{urn:ebay:apis:CoreComponentTypes}BasicAmountType" minOccurs="0"/>
 *         &lt;element name="ShippingDiscount" type="{urn:ebay:apis:CoreComponentTypes}BasicAmountType" minOccurs="0"/>
 *         &lt;element name="InsuranceOptionOffered" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AllowedPaymentMethod" type="{urn:ebay:apis:eBLBaseComponents}AllowedPaymentMethodType" minOccurs="0"/>
 *         &lt;element name="EnhancedPaymentData" type="{urn:ebay:apis:EnhancedDataTypes}EnhancedPaymentDataType" minOccurs="0"/>
 *         &lt;element name="SellerDetails" type="{urn:ebay:apis:eBLBaseComponents}SellerDetailsType" minOccurs="0"/>
 *         &lt;element name="NoteText" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TransactionId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PaymentAction" type="{urn:ebay:apis:eBLBaseComponents}PaymentActionCodeType" minOccurs="0"/>
 *         &lt;element name="PaymentRequestID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OrderURL" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SoftDescriptor" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PaymentDetailsType", namespace = "urn:ebay:apis:eBLBaseComponents", propOrder = {
        "orderTotal",
        "itemTotal",
        "shippingTotal",
        "handlingTotal",
        "taxTotal",
        "orderDescription",
        "custom",
        "invoiceID",
        "buttonSource",
        "notifyURL",
        "shipToAddress",
        "shippingMethod",
        "profileAddressChangeDate",
        "paymentDetailsItem",
        "insuranceTotal",
        "shippingDiscount",
        "insuranceOptionOffered",
        "allowedPaymentMethod",
        "enhancedPaymentData",
        "sellerDetails",
        "noteText",
        "transactionId",
        "paymentAction",
        "paymentRequestID",
        "orderURL",
        "softDescriptor"
})
public class PaymentDetailsType
{

    @XmlElement(name = "OrderTotal", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected BasicAmountType orderTotal;
    @XmlElement(name = "ItemTotal", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected BasicAmountType itemTotal;
    @XmlElement(name = "ShippingTotal", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected BasicAmountType shippingTotal;
    @XmlElement(name = "HandlingTotal", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected BasicAmountType handlingTotal;
    @XmlElement(name = "TaxTotal", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected BasicAmountType taxTotal;
    @XmlElement(name = "OrderDescription", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String orderDescription;
    @XmlElement(name = "Custom", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String custom;
    @XmlElement(name = "InvoiceID", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String invoiceID;
    @XmlElement(name = "ButtonSource", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String buttonSource;
    @XmlElement(name = "NotifyURL", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String notifyURL;
    @XmlElement(name = "ShipToAddress", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected AddressType shipToAddress;
    @XmlElement(name = "ShippingMethod", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected ShippingServiceCodeType shippingMethod;
    @XmlElement(name = "ProfileAddressChangeDate", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected XMLGregorianCalendar profileAddressChangeDate;
    @XmlElement(name = "PaymentDetailsItem", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected List<PaymentDetailsItemType> paymentDetailsItem;
    @XmlElement(name = "InsuranceTotal", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected BasicAmountType insuranceTotal;
    @XmlElement(name = "ShippingDiscount", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected BasicAmountType shippingDiscount;
    @XmlElement(name = "InsuranceOptionOffered", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String insuranceOptionOffered;
    @XmlElement(name = "AllowedPaymentMethod", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected AllowedPaymentMethodType allowedPaymentMethod;
    @XmlElement(name = "EnhancedPaymentData", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected EnhancedPaymentDataType enhancedPaymentData;
    @XmlElement(name = "SellerDetails", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected SellerDetailsType sellerDetails;
    @XmlElement(name = "NoteText", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String noteText;
    @XmlElement(name = "TransactionId", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String transactionId;
    @XmlElement(name = "PaymentAction", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected PaymentActionCodeType paymentAction;
    @XmlElement(name = "PaymentRequestID", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String paymentRequestID;
    @XmlElement(name = "OrderURL", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String orderURL;
    @XmlElement(name = "SoftDescriptor", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String softDescriptor;

    /**
     * Gets the value of the orderTotal property.
     *
     * @return possible object is
     *         {@link BasicAmountType }
     */
    public BasicAmountType getOrderTotal()
    {
        return orderTotal;
    }

    /**
     * Sets the value of the orderTotal property.
     *
     * @param value allowed object is
     *              {@link BasicAmountType }
     */
    public void setOrderTotal(BasicAmountType value)
    {
        this.orderTotal = value;
    }

    /**
     * Gets the value of the itemTotal property.
     *
     * @return possible object is
     *         {@link BasicAmountType }
     */
    public BasicAmountType getItemTotal()
    {
        return itemTotal;
    }

    /**
     * Sets the value of the itemTotal property.
     *
     * @param value allowed object is
     *              {@link BasicAmountType }
     */
    public void setItemTotal(BasicAmountType value)
    {
        this.itemTotal = value;
    }

    /**
     * Gets the value of the shippingTotal property.
     *
     * @return possible object is
     *         {@link BasicAmountType }
     */
    public BasicAmountType getShippingTotal()
    {
        return shippingTotal;
    }

    /**
     * Sets the value of the shippingTotal property.
     *
     * @param value allowed object is
     *              {@link BasicAmountType }
     */
    public void setShippingTotal(BasicAmountType value)
    {
        this.shippingTotal = value;
    }

    /**
     * Gets the value of the handlingTotal property.
     *
     * @return possible object is
     *         {@link BasicAmountType }
     */
    public BasicAmountType getHandlingTotal()
    {
        return handlingTotal;
    }

    /**
     * Sets the value of the handlingTotal property.
     *
     * @param value allowed object is
     *              {@link BasicAmountType }
     */
    public void setHandlingTotal(BasicAmountType value)
    {
        this.handlingTotal = value;
    }

    /**
     * Gets the value of the taxTotal property.
     *
     * @return possible object is
     *         {@link BasicAmountType }
     */
    public BasicAmountType getTaxTotal()
    {
        return taxTotal;
    }

    /**
     * Sets the value of the taxTotal property.
     *
     * @param value allowed object is
     *              {@link BasicAmountType }
     */
    public void setTaxTotal(BasicAmountType value)
    {
        this.taxTotal = value;
    }

    /**
     * Gets the value of the orderDescription property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getOrderDescription()
    {
        return orderDescription;
    }

    /**
     * Sets the value of the orderDescription property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setOrderDescription(String value)
    {
        this.orderDescription = value;
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
     * Gets the value of the buttonSource property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getButtonSource()
    {
        return buttonSource;
    }

    /**
     * Sets the value of the buttonSource property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setButtonSource(String value)
    {
        this.buttonSource = value;
    }

    /**
     * Gets the value of the notifyURL property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getNotifyURL()
    {
        return notifyURL;
    }

    /**
     * Sets the value of the notifyURL property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setNotifyURL(String value)
    {
        this.notifyURL = value;
    }

    /**
     * Gets the value of the shipToAddress property.
     *
     * @return possible object is
     *         {@link AddressType }
     */
    public AddressType getShipToAddress()
    {
        return shipToAddress;
    }

    /**
     * Sets the value of the shipToAddress property.
     *
     * @param value allowed object is
     *              {@link AddressType }
     */
    public void setShipToAddress(AddressType value)
    {
        this.shipToAddress = value;
    }

    /**
     * Gets the value of the shippingMethod property.
     *
     * @return possible object is
     *         {@link ShippingServiceCodeType }
     */
    public ShippingServiceCodeType getShippingMethod()
    {
        return shippingMethod;
    }

    /**
     * Sets the value of the shippingMethod property.
     *
     * @param value allowed object is
     *              {@link ShippingServiceCodeType }
     */
    public void setShippingMethod(ShippingServiceCodeType value)
    {
        this.shippingMethod = value;
    }

    /**
     * Gets the value of the profileAddressChangeDate property.
     *
     * @return possible object is
     *         {@link XMLGregorianCalendar }
     */
    public XMLGregorianCalendar getProfileAddressChangeDate()
    {
        return profileAddressChangeDate;
    }

    /**
     * Sets the value of the profileAddressChangeDate property.
     *
     * @param value allowed object is
     *              {@link XMLGregorianCalendar }
     */
    public void setProfileAddressChangeDate(XMLGregorianCalendar value)
    {
        this.profileAddressChangeDate = value;
    }

    /**
     * Gets the value of the paymentDetailsItem property.
     * <p/>
     * <p/>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the paymentDetailsItem property.
     * <p/>
     * <p/>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPaymentDetailsItem().add(newItem);
     * </pre>
     * <p/>
     * <p/>
     * <p/>
     * Objects of the following type(s) are allowed in the list
     * {@link PaymentDetailsItemType }
     */
    public List<PaymentDetailsItemType> getPaymentDetailsItem()
    {
        if (paymentDetailsItem == null)
        {
            paymentDetailsItem = new ArrayList<PaymentDetailsItemType>();
        }
        return this.paymentDetailsItem;
    }

    /**
     * Gets the value of the insuranceTotal property.
     *
     * @return possible object is
     *         {@link BasicAmountType }
     */
    public BasicAmountType getInsuranceTotal()
    {
        return insuranceTotal;
    }

    /**
     * Sets the value of the insuranceTotal property.
     *
     * @param value allowed object is
     *              {@link BasicAmountType }
     */
    public void setInsuranceTotal(BasicAmountType value)
    {
        this.insuranceTotal = value;
    }

    /**
     * Gets the value of the shippingDiscount property.
     *
     * @return possible object is
     *         {@link BasicAmountType }
     */
    public BasicAmountType getShippingDiscount()
    {
        return shippingDiscount;
    }

    /**
     * Sets the value of the shippingDiscount property.
     *
     * @param value allowed object is
     *              {@link BasicAmountType }
     */
    public void setShippingDiscount(BasicAmountType value)
    {
        this.shippingDiscount = value;
    }

    /**
     * Gets the value of the insuranceOptionOffered property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getInsuranceOptionOffered()
    {
        return insuranceOptionOffered;
    }

    /**
     * Sets the value of the insuranceOptionOffered property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setInsuranceOptionOffered(String value)
    {
        this.insuranceOptionOffered = value;
    }

    /**
     * Gets the value of the allowedPaymentMethod property.
     *
     * @return possible object is
     *         {@link AllowedPaymentMethodType }
     */
    public AllowedPaymentMethodType getAllowedPaymentMethod()
    {
        return allowedPaymentMethod;
    }

    /**
     * Sets the value of the allowedPaymentMethod property.
     *
     * @param value allowed object is
     *              {@link AllowedPaymentMethodType }
     */
    public void setAllowedPaymentMethod(AllowedPaymentMethodType value)
    {
        this.allowedPaymentMethod = value;
    }

    /**
     * Gets the value of the enhancedPaymentData property.
     *
     * @return possible object is
     *         {@link EnhancedPaymentDataType }
     */
    public EnhancedPaymentDataType getEnhancedPaymentData()
    {
        return enhancedPaymentData;
    }

    /**
     * Sets the value of the enhancedPaymentData property.
     *
     * @param value allowed object is
     *              {@link EnhancedPaymentDataType }
     */
    public void setEnhancedPaymentData(EnhancedPaymentDataType value)
    {
        this.enhancedPaymentData = value;
    }

    /**
     * Gets the value of the sellerDetails property.
     *
     * @return possible object is
     *         {@link SellerDetailsType }
     */
    public SellerDetailsType getSellerDetails()
    {
        return sellerDetails;
    }

    /**
     * Sets the value of the sellerDetails property.
     *
     * @param value allowed object is
     *              {@link SellerDetailsType }
     */
    public void setSellerDetails(SellerDetailsType value)
    {
        this.sellerDetails = value;
    }

    /**
     * Gets the value of the noteText property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getNoteText()
    {
        return noteText;
    }

    /**
     * Sets the value of the noteText property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setNoteText(String value)
    {
        this.noteText = value;
    }

    /**
     * Gets the value of the transactionId property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getTransactionId()
    {
        return transactionId;
    }

    /**
     * Sets the value of the transactionId property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setTransactionId(String value)
    {
        this.transactionId = value;
    }

    /**
     * Gets the value of the paymentAction property.
     *
     * @return possible object is
     *         {@link PaymentActionCodeType }
     */
    public PaymentActionCodeType getPaymentAction()
    {
        return paymentAction;
    }

    /**
     * Sets the value of the paymentAction property.
     *
     * @param value allowed object is
     *              {@link PaymentActionCodeType }
     */
    public void setPaymentAction(PaymentActionCodeType value)
    {
        this.paymentAction = value;
    }

    /**
     * Gets the value of the paymentRequestID property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getPaymentRequestID()
    {
        return paymentRequestID;
    }

    /**
     * Sets the value of the paymentRequestID property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setPaymentRequestID(String value)
    {
        this.paymentRequestID = value;
    }

    /**
     * Gets the value of the orderURL property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getOrderURL()
    {
        return orderURL;
    }

    /**
     * Sets the value of the orderURL property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setOrderURL(String value)
    {
        this.orderURL = value;
    }

    /**
     * Gets the value of the softDescriptor property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getSoftDescriptor()
    {
        return softDescriptor;
    }

    /**
     * Sets the value of the softDescriptor property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setSoftDescriptor(String value)
    {
        this.softDescriptor = value;
    }

}
