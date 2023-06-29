package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * PaymentInfoType
 * Payment information.
 * <p/>
 * <p/>
 * <p>Java class for PaymentInfoType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="PaymentInfoType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element ref="{urn:ebay:apis:eBLBaseComponents}TransactionID"/>
 *         &lt;element ref="{urn:ebay:apis:eBLBaseComponents}EbayTransactionID"/>
 *         &lt;element name="ParentTransactionID" type="{urn:ebay:apis:eBLBaseComponents}TransactionId" minOccurs="0"/>
 *         &lt;element ref="{urn:ebay:apis:eBLBaseComponents}ReceiptID" minOccurs="0"/>
 *         &lt;element name="TransactionType" type="{urn:ebay:apis:eBLBaseComponents}PaymentTransactionCodeType"/>
 *         &lt;element name="PaymentType" type="{urn:ebay:apis:eBLBaseComponents}PaymentCodeType" minOccurs="0"/>
 *         &lt;element name="ExpectedeCheckClearDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="PaymentDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="GrossAmount" type="{urn:ebay:apis:CoreComponentTypes}BasicAmountType"/>
 *         &lt;element name="FeeAmount" type="{urn:ebay:apis:CoreComponentTypes}BasicAmountType" minOccurs="0"/>
 *         &lt;element name="SettleAmount" type="{urn:ebay:apis:CoreComponentTypes}BasicAmountType" minOccurs="0"/>
 *         &lt;element name="TaxAmount" type="{urn:ebay:apis:CoreComponentTypes}BasicAmountType" minOccurs="0"/>
 *         &lt;element name="ExchangeRate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PaymentStatus" type="{urn:ebay:apis:eBLBaseComponents}PaymentStatusCodeType"/>
 *         &lt;element name="PendingReason" type="{urn:ebay:apis:eBLBaseComponents}PendingStatusCodeType" minOccurs="0"/>
 *         &lt;element name="ReasonCode" type="{urn:ebay:apis:eBLBaseComponents}ReversalReasonCodeType" minOccurs="0"/>
 *         &lt;element name="ShippingMethod" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ProtectionEligibility" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ProtectionEligibilityType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ShipAmount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ShipHandleAmount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ShipDiscount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="InsuranceAmount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Subject" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SellerDetails" type="{urn:ebay:apis:eBLBaseComponents}SellerDetailsType" minOccurs="0"/>
 *         &lt;element name="PaymentRequestID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FMFDetails" type="{urn:ebay:apis:eBLBaseComponents}FMFDetailsType" minOccurs="0"/>
 *         &lt;element name="EnhancedPaymentInfo" type="{urn:ebay:apis:EnhancedDataTypes}EnhancedPaymentInfoType" minOccurs="0"/>
 *         &lt;element name="PaymentError" type="{urn:ebay:apis:eBLBaseComponents}ErrorType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PaymentInfoType", namespace = "urn:ebay:apis:eBLBaseComponents", propOrder = {
        "transactionID",
        "ebayTransactionID",
        "parentTransactionID",
        "receiptID",
        "transactionType",
        "paymentType",
        "expectedeCheckClearDate",
        "paymentDate",
        "grossAmount",
        "feeAmount",
        "settleAmount",
        "taxAmount",
        "exchangeRate",
        "paymentStatus",
        "pendingReason",
        "reasonCode",
        "shippingMethod",
        "protectionEligibility",
        "protectionEligibilityType",
        "shipAmount",
        "shipHandleAmount",
        "shipDiscount",
        "insuranceAmount",
        "subject",
        "sellerDetails",
        "paymentRequestID",
        "fmfDetails",
        "enhancedPaymentInfo",
        "paymentError"
})
public class PaymentInfoType
{

    @XmlElement(name = "TransactionID", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected String transactionID;
    @XmlElement(name = "EbayTransactionID", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected String ebayTransactionID;
    @XmlElement(name = "ParentTransactionID", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String parentTransactionID;
    @XmlElement(name = "ReceiptID", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String receiptID;
    @XmlElement(name = "TransactionType", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected PaymentTransactionCodeType transactionType;
    @XmlElement(name = "PaymentType", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected PaymentCodeType paymentType;
    @XmlElement(name = "ExpectedeCheckClearDate", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected XMLGregorianCalendar expectedeCheckClearDate;
    @XmlElement(name = "PaymentDate", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected XMLGregorianCalendar paymentDate;
    @XmlElement(name = "GrossAmount", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected BasicAmountType grossAmount;
    @XmlElement(name = "FeeAmount", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected BasicAmountType feeAmount;
    @XmlElement(name = "SettleAmount", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected BasicAmountType settleAmount;
    @XmlElement(name = "TaxAmount", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected BasicAmountType taxAmount;
    @XmlElement(name = "ExchangeRate", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String exchangeRate;
    @XmlElement(name = "PaymentStatus", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected PaymentStatusCodeType paymentStatus;
    @XmlElement(name = "PendingReason", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected PendingStatusCodeType pendingReason;
    @XmlElement(name = "ReasonCode", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected ReversalReasonCodeType reasonCode;
    @XmlElement(name = "ShippingMethod", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String shippingMethod;
    @XmlElement(name = "ProtectionEligibility", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String protectionEligibility;
    @XmlElement(name = "ProtectionEligibilityType", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String protectionEligibilityType;
    @XmlElement(name = "ShipAmount", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String shipAmount;
    @XmlElement(name = "ShipHandleAmount", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String shipHandleAmount;
    @XmlElement(name = "ShipDiscount", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String shipDiscount;
    @XmlElement(name = "InsuranceAmount", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String insuranceAmount;
    @XmlElement(name = "Subject", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String subject;
    @XmlElement(name = "SellerDetails", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected SellerDetailsType sellerDetails;
    @XmlElement(name = "PaymentRequestID", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String paymentRequestID;
    @XmlElement(name = "FMFDetails", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected FMFDetailsType fmfDetails;
    @XmlElement(name = "EnhancedPaymentInfo", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected EnhancedPaymentInfoType enhancedPaymentInfo;
    @XmlElement(name = "PaymentError", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected ErrorType paymentError;

    /**
     * A transaction identification number.
     * <p/>
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;br xmlns="http://www.w3.org/2001/XMLSchema"/&gt;
     * </pre>
     * Character length and limits: 19 single-byte characters maximum
     *
     * @return possible object is
     *         {@link String }
     */
    public String getTransactionID()
    {
        return transactionID;
    }

    /**
     * A transaction identification number.
     * <p/>
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;br xmlns="http://www.w3.org/2001/XMLSchema"/&gt;
     * </pre>
     * Character length and limits: 19 single-byte characters maximum
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setTransactionID(String value)
    {
        this.transactionID = value;
    }

    /**
     * Its Ebay transaction id.
     * <p/>
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;br xmlns="http://www.w3.org/2001/XMLSchema"/&gt;
     * </pre>
     * EbayTransactionID will returned for immediate pay item transaction in ECA
     *
     * @return possible object is
     *         {@link String }
     */
    public String getEbayTransactionID()
    {
        return ebayTransactionID;
    }

    /**
     * Its Ebay transaction id.
     * <p/>
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;br xmlns="http://www.w3.org/2001/XMLSchema"/&gt;
     * </pre>
     * EbayTransactionID will returned for immediate pay item transaction in ECA
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setEbayTransactionID(String value)
    {
        this.ebayTransactionID = value;
    }

    /**
     * Gets the value of the parentTransactionID property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getParentTransactionID()
    {
        return parentTransactionID;
    }

    /**
     * Sets the value of the parentTransactionID property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setParentTransactionID(String value)
    {
        this.parentTransactionID = value;
    }

    /**
     * Receipt ID
     * <p/>
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;br xmlns="http://www.w3.org/2001/XMLSchema"/&gt;
     * </pre>
     * <p/>
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;br xmlns="http://www.w3.org/2001/XMLSchema"/&gt;
     * </pre>
     * <p/>
     * Character length and limitations: 16 digits in xxxx-xxxx-xxxx-xxxx format
     *
     * @return possible object is
     *         {@link String }
     */
    public String getReceiptID()
    {
        return receiptID;
    }

    /**
     * Receipt ID
     * <p/>
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;br xmlns="http://www.w3.org/2001/XMLSchema"/&gt;
     * </pre>
     * <p/>
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;br xmlns="http://www.w3.org/2001/XMLSchema"/&gt;
     * </pre>
     * <p/>
     * Character length and limitations: 16 digits in xxxx-xxxx-xxxx-xxxx format
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setReceiptID(String value)
    {
        this.receiptID = value;
    }

    /**
     * Gets the value of the transactionType property.
     *
     * @return possible object is
     *         {@link PaymentTransactionCodeType }
     */
    public PaymentTransactionCodeType getTransactionType()
    {
        return transactionType;
    }

    /**
     * Sets the value of the transactionType property.
     *
     * @param value allowed object is
     *              {@link PaymentTransactionCodeType }
     */
    public void setTransactionType(PaymentTransactionCodeType value)
    {
        this.transactionType = value;
    }

    /**
     * Gets the value of the paymentType property.
     *
     * @return possible object is
     *         {@link PaymentCodeType }
     */
    public PaymentCodeType getPaymentType()
    {
        return paymentType;
    }

    /**
     * Sets the value of the paymentType property.
     *
     * @param value allowed object is
     *              {@link PaymentCodeType }
     */
    public void setPaymentType(PaymentCodeType value)
    {
        this.paymentType = value;
    }

    /**
     * Gets the value of the expectedeCheckClearDate property.
     *
     * @return possible object is
     *         {@link XMLGregorianCalendar }
     */
    public XMLGregorianCalendar getExpectedeCheckClearDate()
    {
        return expectedeCheckClearDate;
    }

    /**
     * Sets the value of the expectedeCheckClearDate property.
     *
     * @param value allowed object is
     *              {@link XMLGregorianCalendar }
     */
    public void setExpectedeCheckClearDate(XMLGregorianCalendar value)
    {
        this.expectedeCheckClearDate = value;
    }

    /**
     * Gets the value of the paymentDate property.
     *
     * @return possible object is
     *         {@link XMLGregorianCalendar }
     */
    public XMLGregorianCalendar getPaymentDate()
    {
        return paymentDate;
    }

    /**
     * Sets the value of the paymentDate property.
     *
     * @param value allowed object is
     *              {@link XMLGregorianCalendar }
     */
    public void setPaymentDate(XMLGregorianCalendar value)
    {
        this.paymentDate = value;
    }

    /**
     * Gets the value of the grossAmount property.
     *
     * @return possible object is
     *         {@link BasicAmountType }
     */
    public BasicAmountType getGrossAmount()
    {
        return grossAmount;
    }

    /**
     * Sets the value of the grossAmount property.
     *
     * @param value allowed object is
     *              {@link BasicAmountType }
     */
    public void setGrossAmount(BasicAmountType value)
    {
        this.grossAmount = value;
    }

    /**
     * Gets the value of the feeAmount property.
     *
     * @return possible object is
     *         {@link BasicAmountType }
     */
    public BasicAmountType getFeeAmount()
    {
        return feeAmount;
    }

    /**
     * Sets the value of the feeAmount property.
     *
     * @param value allowed object is
     *              {@link BasicAmountType }
     */
    public void setFeeAmount(BasicAmountType value)
    {
        this.feeAmount = value;
    }

    /**
     * Gets the value of the settleAmount property.
     *
     * @return possible object is
     *         {@link BasicAmountType }
     */
    public BasicAmountType getSettleAmount()
    {
        return settleAmount;
    }

    /**
     * Sets the value of the settleAmount property.
     *
     * @param value allowed object is
     *              {@link BasicAmountType }
     */
    public void setSettleAmount(BasicAmountType value)
    {
        this.settleAmount = value;
    }

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
     * Gets the value of the exchangeRate property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getExchangeRate()
    {
        return exchangeRate;
    }

    /**
     * Sets the value of the exchangeRate property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setExchangeRate(String value)
    {
        this.exchangeRate = value;
    }

    /**
     * Gets the value of the paymentStatus property.
     *
     * @return possible object is
     *         {@link PaymentStatusCodeType }
     */
    public PaymentStatusCodeType getPaymentStatus()
    {
        return paymentStatus;
    }

    /**
     * Sets the value of the paymentStatus property.
     *
     * @param value allowed object is
     *              {@link PaymentStatusCodeType }
     */
    public void setPaymentStatus(PaymentStatusCodeType value)
    {
        this.paymentStatus = value;
    }

    /**
     * Gets the value of the pendingReason property.
     *
     * @return possible object is
     *         {@link PendingStatusCodeType }
     */
    public PendingStatusCodeType getPendingReason()
    {
        return pendingReason;
    }

    /**
     * Sets the value of the pendingReason property.
     *
     * @param value allowed object is
     *              {@link PendingStatusCodeType }
     */
    public void setPendingReason(PendingStatusCodeType value)
    {
        this.pendingReason = value;
    }

    /**
     * Gets the value of the reasonCode property.
     *
     * @return possible object is
     *         {@link ReversalReasonCodeType }
     */
    public ReversalReasonCodeType getReasonCode()
    {
        return reasonCode;
    }

    /**
     * Sets the value of the reasonCode property.
     *
     * @param value allowed object is
     *              {@link ReversalReasonCodeType }
     */
    public void setReasonCode(ReversalReasonCodeType value)
    {
        this.reasonCode = value;
    }

    /**
     * Gets the value of the shippingMethod property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getShippingMethod()
    {
        return shippingMethod;
    }

    /**
     * Sets the value of the shippingMethod property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setShippingMethod(String value)
    {
        this.shippingMethod = value;
    }

    /**
     * Gets the value of the protectionEligibility property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getProtectionEligibility()
    {
        return protectionEligibility;
    }

    /**
     * Sets the value of the protectionEligibility property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setProtectionEligibility(String value)
    {
        this.protectionEligibility = value;
    }

    /**
     * Gets the value of the protectionEligibilityType property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getProtectionEligibilityType()
    {
        return protectionEligibilityType;
    }

    /**
     * Sets the value of the protectionEligibilityType property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setProtectionEligibilityType(String value)
    {
        this.protectionEligibilityType = value;
    }

    /**
     * Gets the value of the shipAmount property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getShipAmount()
    {
        return shipAmount;
    }

    /**
     * Sets the value of the shipAmount property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setShipAmount(String value)
    {
        this.shipAmount = value;
    }

    /**
     * Gets the value of the shipHandleAmount property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getShipHandleAmount()
    {
        return shipHandleAmount;
    }

    /**
     * Sets the value of the shipHandleAmount property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setShipHandleAmount(String value)
    {
        this.shipHandleAmount = value;
    }

    /**
     * Gets the value of the shipDiscount property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getShipDiscount()
    {
        return shipDiscount;
    }

    /**
     * Sets the value of the shipDiscount property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setShipDiscount(String value)
    {
        this.shipDiscount = value;
    }

    /**
     * Gets the value of the insuranceAmount property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getInsuranceAmount()
    {
        return insuranceAmount;
    }

    /**
     * Sets the value of the insuranceAmount property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setInsuranceAmount(String value)
    {
        this.insuranceAmount = value;
    }

    /**
     * Gets the value of the subject property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getSubject()
    {
        return subject;
    }

    /**
     * Sets the value of the subject property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setSubject(String value)
    {
        this.subject = value;
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
     * Gets the value of the fmfDetails property.
     *
     * @return possible object is
     *         {@link FMFDetailsType }
     */
    public FMFDetailsType getFMFDetails()
    {
        return fmfDetails;
    }

    /**
     * Sets the value of the fmfDetails property.
     *
     * @param value allowed object is
     *              {@link FMFDetailsType }
     */
    public void setFMFDetails(FMFDetailsType value)
    {
        this.fmfDetails = value;
    }

    /**
     * Gets the value of the enhancedPaymentInfo property.
     *
     * @return possible object is
     *         {@link EnhancedPaymentInfoType }
     */
    public EnhancedPaymentInfoType getEnhancedPaymentInfo()
    {
        return enhancedPaymentInfo;
    }

    /**
     * Sets the value of the enhancedPaymentInfo property.
     *
     * @param value allowed object is
     *              {@link EnhancedPaymentInfoType }
     */
    public void setEnhancedPaymentInfo(EnhancedPaymentInfoType value)
    {
        this.enhancedPaymentInfo = value;
    }

    /**
     * Gets the value of the paymentError property.
     *
     * @return possible object is
     *         {@link ErrorType }
     */
    public ErrorType getPaymentError()
    {
        return paymentError;
    }

    /**
     * Sets the value of the paymentError property.
     *
     * @param value allowed object is
     *              {@link ErrorType }
     */
    public void setPaymentError(ErrorType value)
    {
        this.paymentError = value;
    }

}
