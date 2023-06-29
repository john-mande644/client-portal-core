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
 * <p>Java class for GetExpressCheckoutDetailsResponseDetailsType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="GetExpressCheckoutDetailsResponseDetailsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="Token" type="{urn:ebay:apis:eBLBaseComponents}ExpressCheckoutTokenType"/>
 *         &lt;element name="PayerInfo" type="{urn:ebay:apis:eBLBaseComponents}PayerInfoType"/>
 *         &lt;element name="Custom" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="InvoiceID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ContactPhone" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BillingAgreementAcceptedStatus" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="RedirectRequired" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BillingAddress" type="{urn:ebay:apis:eBLBaseComponents}AddressType" minOccurs="0"/>
 *         &lt;element name="Note" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CheckoutStatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PayPalAdjustment" type="{urn:ebay:apis:CoreComponentTypes}BasicAmountType" minOccurs="0"/>
 *         &lt;element name="PaymentDetails" type="{urn:ebay:apis:eBLBaseComponents}PaymentDetailsType" maxOccurs="10" minOccurs="0"/>
 *         &lt;element name="UserSelectedOptions" type="{urn:ebay:apis:eBLBaseComponents}UserSelectedOptionType" minOccurs="0"/>
 *         &lt;element name="IncentiveDetails" type="{urn:ebay:apis:eBLBaseComponents}IncentiveDetailsType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="GiftMessage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="GiftReceiptEnable" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="GiftWrapName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="GiftWrapAmount" type="{urn:ebay:apis:CoreComponentTypes}BasicAmountType" minOccurs="0"/>
 *         &lt;element name="BuyerMarketingEmail" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SurveyQuestion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SurveyChoiceSelected" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="PaymentRequestInfo" type="{urn:ebay:apis:eBLBaseComponents}PaymentRequestInfoType" maxOccurs="10" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetExpressCheckoutDetailsResponseDetailsType", namespace = "urn:ebay:apis:eBLBaseComponents", propOrder = {
        "token",
        "payerInfo",
        "custom",
        "invoiceID",
        "contactPhone",
        "billingAgreementAcceptedStatus",
        "redirectRequired",
        "billingAddress",
        "note",
        "checkoutStatus",
        "payPalAdjustment",
        "paymentDetails",
        "userSelectedOptions",
        "incentiveDetails",
        "giftMessage",
        "giftReceiptEnable",
        "giftWrapName",
        "giftWrapAmount",
        "buyerMarketingEmail",
        "surveyQuestion",
        "surveyChoiceSelected",
        "paymentRequestInfo"
})
public class GetExpressCheckoutDetailsResponseDetailsType
{

    @XmlElement(name = "Token", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected String token;
    @XmlElement(name = "PayerInfo", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected PayerInfoType payerInfo;
    @XmlElement(name = "Custom", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String custom;
    @XmlElement(name = "InvoiceID", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String invoiceID;
    @XmlElement(name = "ContactPhone", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String contactPhone;
    @XmlElement(name = "BillingAgreementAcceptedStatus", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected Boolean billingAgreementAcceptedStatus;
    @XmlElement(name = "RedirectRequired", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String redirectRequired;
    @XmlElement(name = "BillingAddress", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected AddressType billingAddress;
    @XmlElement(name = "Note", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String note;
    @XmlElement(name = "CheckoutStatus", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String checkoutStatus;
    @XmlElement(name = "PayPalAdjustment", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected BasicAmountType payPalAdjustment;
    @XmlElement(name = "PaymentDetails", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected List<PaymentDetailsType> paymentDetails;
    @XmlElement(name = "UserSelectedOptions", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected UserSelectedOptionType userSelectedOptions;
    @XmlElement(name = "IncentiveDetails", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected List<IncentiveDetailsType> incentiveDetails;
    @XmlElement(name = "GiftMessage", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String giftMessage;
    @XmlElement(name = "GiftReceiptEnable", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String giftReceiptEnable;
    @XmlElement(name = "GiftWrapName", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String giftWrapName;
    @XmlElement(name = "GiftWrapAmount", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected BasicAmountType giftWrapAmount;
    @XmlElement(name = "BuyerMarketingEmail", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String buyerMarketingEmail;
    @XmlElement(name = "SurveyQuestion", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String surveyQuestion;
    @XmlElement(name = "SurveyChoiceSelected", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected List<String> surveyChoiceSelected;
    @XmlElement(name = "PaymentRequestInfo", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected List<PaymentRequestInfoType> paymentRequestInfo;

    /**
     * Gets the value of the token property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getToken()
    {
        return token;
    }

    /**
     * Sets the value of the token property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setToken(String value)
    {
        this.token = value;
    }

    /**
     * Gets the value of the payerInfo property.
     *
     * @return possible object is
     *         {@link PayerInfoType }
     */
    public PayerInfoType getPayerInfo()
    {
        return payerInfo;
    }

    /**
     * Sets the value of the payerInfo property.
     *
     * @param value allowed object is
     *              {@link PayerInfoType }
     */
    public void setPayerInfo(PayerInfoType value)
    {
        this.payerInfo = value;
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
     * Gets the value of the contactPhone property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getContactPhone()
    {
        return contactPhone;
    }

    /**
     * Sets the value of the contactPhone property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setContactPhone(String value)
    {
        this.contactPhone = value;
    }

    /**
     * Gets the value of the billingAgreementAcceptedStatus property.
     *
     * @return possible object is
     *         {@link Boolean }
     */
    public Boolean isBillingAgreementAcceptedStatus()
    {
        return billingAgreementAcceptedStatus;
    }

    /**
     * Sets the value of the billingAgreementAcceptedStatus property.
     *
     * @param value allowed object is
     *              {@link Boolean }
     */
    public void setBillingAgreementAcceptedStatus(Boolean value)
    {
        this.billingAgreementAcceptedStatus = value;
    }

    /**
     * Gets the value of the redirectRequired property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getRedirectRequired()
    {
        return redirectRequired;
    }

    /**
     * Sets the value of the redirectRequired property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setRedirectRequired(String value)
    {
        this.redirectRequired = value;
    }

    /**
     * Gets the value of the billingAddress property.
     *
     * @return possible object is
     *         {@link AddressType }
     */
    public AddressType getBillingAddress()
    {
        return billingAddress;
    }

    /**
     * Sets the value of the billingAddress property.
     *
     * @param value allowed object is
     *              {@link AddressType }
     */
    public void setBillingAddress(AddressType value)
    {
        this.billingAddress = value;
    }

    /**
     * Gets the value of the note property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getNote()
    {
        return note;
    }

    /**
     * Sets the value of the note property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setNote(String value)
    {
        this.note = value;
    }

    /**
     * Gets the value of the checkoutStatus property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getCheckoutStatus()
    {
        return checkoutStatus;
    }

    /**
     * Sets the value of the checkoutStatus property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setCheckoutStatus(String value)
    {
        this.checkoutStatus = value;
    }

    /**
     * Gets the value of the payPalAdjustment property.
     *
     * @return possible object is
     *         {@link BasicAmountType }
     */
    public BasicAmountType getPayPalAdjustment()
    {
        return payPalAdjustment;
    }

    /**
     * Sets the value of the payPalAdjustment property.
     *
     * @param value allowed object is
     *              {@link BasicAmountType }
     */
    public void setPayPalAdjustment(BasicAmountType value)
    {
        this.payPalAdjustment = value;
    }

    /**
     * Gets the value of the paymentDetails property.
     * <p/>
     * <p/>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the paymentDetails property.
     * <p/>
     * <p/>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPaymentDetails().add(newItem);
     * </pre>
     * <p/>
     * <p/>
     * <p/>
     * Objects of the following type(s) are allowed in the list
     * {@link PaymentDetailsType }
     */
    public List<PaymentDetailsType> getPaymentDetails()
    {
        if (paymentDetails == null)
        {
            paymentDetails = new ArrayList<PaymentDetailsType>();
        }
        return this.paymentDetails;
    }

    /**
     * Gets the value of the userSelectedOptions property.
     *
     * @return possible object is
     *         {@link UserSelectedOptionType }
     */
    public UserSelectedOptionType getUserSelectedOptions()
    {
        return userSelectedOptions;
    }

    /**
     * Sets the value of the userSelectedOptions property.
     *
     * @param value allowed object is
     *              {@link UserSelectedOptionType }
     */
    public void setUserSelectedOptions(UserSelectedOptionType value)
    {
        this.userSelectedOptions = value;
    }

    /**
     * Gets the value of the incentiveDetails property.
     * <p/>
     * <p/>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the incentiveDetails property.
     * <p/>
     * <p/>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIncentiveDetails().add(newItem);
     * </pre>
     * <p/>
     * <p/>
     * <p/>
     * Objects of the following type(s) are allowed in the list
     * {@link IncentiveDetailsType }
     */
    public List<IncentiveDetailsType> getIncentiveDetails()
    {
        if (incentiveDetails == null)
        {
            incentiveDetails = new ArrayList<IncentiveDetailsType>();
        }
        return this.incentiveDetails;
    }

    /**
     * Gets the value of the giftMessage property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getGiftMessage()
    {
        return giftMessage;
    }

    /**
     * Sets the value of the giftMessage property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setGiftMessage(String value)
    {
        this.giftMessage = value;
    }

    /**
     * Gets the value of the giftReceiptEnable property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getGiftReceiptEnable()
    {
        return giftReceiptEnable;
    }

    /**
     * Sets the value of the giftReceiptEnable property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setGiftReceiptEnable(String value)
    {
        this.giftReceiptEnable = value;
    }

    /**
     * Gets the value of the giftWrapName property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getGiftWrapName()
    {
        return giftWrapName;
    }

    /**
     * Sets the value of the giftWrapName property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setGiftWrapName(String value)
    {
        this.giftWrapName = value;
    }

    /**
     * Gets the value of the giftWrapAmount property.
     *
     * @return possible object is
     *         {@link BasicAmountType }
     */
    public BasicAmountType getGiftWrapAmount()
    {
        return giftWrapAmount;
    }

    /**
     * Sets the value of the giftWrapAmount property.
     *
     * @param value allowed object is
     *              {@link BasicAmountType }
     */
    public void setGiftWrapAmount(BasicAmountType value)
    {
        this.giftWrapAmount = value;
    }

    /**
     * Gets the value of the buyerMarketingEmail property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getBuyerMarketingEmail()
    {
        return buyerMarketingEmail;
    }

    /**
     * Sets the value of the buyerMarketingEmail property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setBuyerMarketingEmail(String value)
    {
        this.buyerMarketingEmail = value;
    }

    /**
     * Gets the value of the surveyQuestion property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getSurveyQuestion()
    {
        return surveyQuestion;
    }

    /**
     * Sets the value of the surveyQuestion property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setSurveyQuestion(String value)
    {
        this.surveyQuestion = value;
    }

    /**
     * Gets the value of the surveyChoiceSelected property.
     * <p/>
     * <p/>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the surveyChoiceSelected property.
     * <p/>
     * <p/>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSurveyChoiceSelected().add(newItem);
     * </pre>
     * <p/>
     * <p/>
     * <p/>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     */
    public List<String> getSurveyChoiceSelected()
    {
        if (surveyChoiceSelected == null)
        {
            surveyChoiceSelected = new ArrayList<String>();
        }
        return this.surveyChoiceSelected;
    }

    /**
     * Gets the value of the paymentRequestInfo property.
     * <p/>
     * <p/>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the paymentRequestInfo property.
     * <p/>
     * <p/>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPaymentRequestInfo().add(newItem);
     * </pre>
     * <p/>
     * <p/>
     * <p/>
     * Objects of the following type(s) are allowed in the list
     * {@link PaymentRequestInfoType }
     */
    public List<PaymentRequestInfoType> getPaymentRequestInfo()
    {
        if (paymentRequestInfo == null)
        {
            paymentRequestInfo = new ArrayList<PaymentRequestInfoType>();
        }
        return this.paymentRequestInfo;
    }

}
