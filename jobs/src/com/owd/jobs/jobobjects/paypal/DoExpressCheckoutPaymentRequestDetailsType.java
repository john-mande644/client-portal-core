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
 * <p>Java class for DoExpressCheckoutPaymentRequestDetailsType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="DoExpressCheckoutPaymentRequestDetailsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="PaymentAction" type="{urn:ebay:apis:eBLBaseComponents}PaymentActionCodeType" minOccurs="0"/>
 *         &lt;element name="Token" type="{urn:ebay:apis:eBLBaseComponents}ExpressCheckoutTokenType"/>
 *         &lt;element name="PayerID" type="{urn:ebay:apis:eBLBaseComponents}UserIDType"/>
 *         &lt;element name="OrderURL" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PaymentDetails" type="{urn:ebay:apis:eBLBaseComponents}PaymentDetailsType" maxOccurs="10" minOccurs="0"/>
 *         &lt;element name="PromoOverrideFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PromoCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EnhancedData" type="{urn:ebay:apis:eBLBaseComponents}EnhancedDataType" minOccurs="0"/>
 *         &lt;element name="SoftDescriptor" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="UserSelectedOptions" type="{urn:ebay:apis:eBLBaseComponents}UserSelectedOptionType" minOccurs="0"/>
 *         &lt;element name="GiftMessage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="GiftReceiptEnable" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="GiftWrapName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="GiftWrapAmount" type="{urn:ebay:apis:CoreComponentTypes}BasicAmountType" minOccurs="0"/>
 *         &lt;element name="BuyerMarketingEmail" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SurveyQuestion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SurveyChoiceSelected" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="ButtonSource" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SkipBACreation" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DoExpressCheckoutPaymentRequestDetailsType", namespace = "urn:ebay:apis:eBLBaseComponents", propOrder = {
        "paymentAction",
        "token",
        "payerID",
        "orderURL",
        "paymentDetails",
        "promoOverrideFlag",
        "promoCode",
        "enhancedData",
        "softDescriptor",
        "userSelectedOptions",
        "giftMessage",
        "giftReceiptEnable",
        "giftWrapName",
        "giftWrapAmount",
        "buyerMarketingEmail",
        "surveyQuestion",
        "surveyChoiceSelected",
        "buttonSource",
        "skipBACreation"
})
public class DoExpressCheckoutPaymentRequestDetailsType
{

    @XmlElement(name = "PaymentAction", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected PaymentActionCodeType paymentAction;
    @XmlElement(name = "Token", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected String token;
    @XmlElement(name = "PayerID", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected String payerID;
    @XmlElement(name = "OrderURL", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String orderURL;
    @XmlElement(name = "PaymentDetails", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected List<PaymentDetailsType> paymentDetails;
    @XmlElement(name = "PromoOverrideFlag", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String promoOverrideFlag;
    @XmlElement(name = "PromoCode", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String promoCode;
    @XmlElement(name = "EnhancedData", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected EnhancedDataType enhancedData;
    @XmlElement(name = "SoftDescriptor", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String softDescriptor;
    @XmlElement(name = "UserSelectedOptions", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected UserSelectedOptionType userSelectedOptions;
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
    @XmlElement(name = "ButtonSource", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String buttonSource;
    @XmlElement(name = "SkipBACreation", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected Boolean skipBACreation;

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
     * Gets the value of the payerID property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getPayerID()
    {
        return payerID;
    }

    /**
     * Sets the value of the payerID property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setPayerID(String value)
    {
        this.payerID = value;
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
     * Gets the value of the promoOverrideFlag property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getPromoOverrideFlag()
    {
        return promoOverrideFlag;
    }

    /**
     * Sets the value of the promoOverrideFlag property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setPromoOverrideFlag(String value)
    {
        this.promoOverrideFlag = value;
    }

    /**
     * Gets the value of the promoCode property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getPromoCode()
    {
        return promoCode;
    }

    /**
     * Sets the value of the promoCode property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setPromoCode(String value)
    {
        this.promoCode = value;
    }

    /**
     * Gets the value of the enhancedData property.
     *
     * @return possible object is
     *         {@link EnhancedDataType }
     */
    public EnhancedDataType getEnhancedData()
    {
        return enhancedData;
    }

    /**
     * Sets the value of the enhancedData property.
     *
     * @param value allowed object is
     *              {@link EnhancedDataType }
     */
    public void setEnhancedData(EnhancedDataType value)
    {
        this.enhancedData = value;
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
     * Gets the value of the skipBACreation property.
     *
     * @return possible object is
     *         {@link Boolean }
     */
    public Boolean isSkipBACreation()
    {
        return skipBACreation;
    }

    /**
     * Sets the value of the skipBACreation property.
     *
     * @param value allowed object is
     *              {@link Boolean }
     */
    public void setSkipBACreation(Boolean value)
    {
        this.skipBACreation = value;
    }

}
