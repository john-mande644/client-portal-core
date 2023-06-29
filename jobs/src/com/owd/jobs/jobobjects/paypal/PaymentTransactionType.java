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
 * PaymentTransactionType
 * Information about a PayPal payment from the seller side
 * <p/>
 * <p/>
 * <p>Java class for PaymentTransactionType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="PaymentTransactionType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="ReceiverInfo" type="{urn:ebay:apis:eBLBaseComponents}ReceiverInfoType"/>
 *         &lt;element name="PayerInfo" type="{urn:ebay:apis:eBLBaseComponents}PayerInfoType"/>
 *         &lt;element name="PaymentInfo" type="{urn:ebay:apis:eBLBaseComponents}PaymentInfoType"/>
 *         &lt;element name="PaymentItemInfo" type="{urn:ebay:apis:eBLBaseComponents}PaymentItemInfoType" minOccurs="0"/>
 *         &lt;element name="UserSelectedOptions" type="{urn:ebay:apis:eBLBaseComponents}UserSelectedOptionType" minOccurs="0"/>
 *         &lt;element name="GiftMessage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="GiftReceipt" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="GiftWrapName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="GiftWrapAmount" type="{urn:ebay:apis:CoreComponentTypes}BasicAmountType" minOccurs="0"/>
 *         &lt;element name="BuyerEmailOptIn" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SurveyQuestion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SurveyChoiceSelected" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PaymentTransactionType", namespace = "urn:ebay:apis:eBLBaseComponents", propOrder = {
        "receiverInfo",
        "payerInfo",
        "paymentInfo",
        "paymentItemInfo",
        "userSelectedOptions",
        "giftMessage",
        "giftReceipt",
        "giftWrapName",
        "giftWrapAmount",
        "buyerEmailOptIn",
        "surveyQuestion",
        "surveyChoiceSelected"
})
public class PaymentTransactionType
{

    @XmlElement(name = "ReceiverInfo", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected ReceiverInfoType receiverInfo;
    @XmlElement(name = "PayerInfo", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected PayerInfoType payerInfo;
    @XmlElement(name = "PaymentInfo", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected PaymentInfoType paymentInfo;
    @XmlElement(name = "PaymentItemInfo", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected PaymentItemInfoType paymentItemInfo;
    @XmlElement(name = "UserSelectedOptions", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected UserSelectedOptionType userSelectedOptions;
    @XmlElement(name = "GiftMessage", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String giftMessage;
    @XmlElement(name = "GiftReceipt", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String giftReceipt;
    @XmlElement(name = "GiftWrapName", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String giftWrapName;
    @XmlElement(name = "GiftWrapAmount", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected BasicAmountType giftWrapAmount;
    @XmlElement(name = "BuyerEmailOptIn", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String buyerEmailOptIn;
    @XmlElement(name = "SurveyQuestion", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String surveyQuestion;
    @XmlElement(name = "SurveyChoiceSelected", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected List<String> surveyChoiceSelected;

    /**
     * Gets the value of the receiverInfo property.
     *
     * @return possible object is
     *         {@link ReceiverInfoType }
     */
    public ReceiverInfoType getReceiverInfo()
    {
        return receiverInfo;
    }

    /**
     * Sets the value of the receiverInfo property.
     *
     * @param value allowed object is
     *              {@link ReceiverInfoType }
     */
    public void setReceiverInfo(ReceiverInfoType value)
    {
        this.receiverInfo = value;
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
     * Gets the value of the paymentInfo property.
     *
     * @return possible object is
     *         {@link PaymentInfoType }
     */
    public PaymentInfoType getPaymentInfo()
    {
        return paymentInfo;
    }

    /**
     * Sets the value of the paymentInfo property.
     *
     * @param value allowed object is
     *              {@link PaymentInfoType }
     */
    public void setPaymentInfo(PaymentInfoType value)
    {
        this.paymentInfo = value;
    }

    /**
     * Gets the value of the paymentItemInfo property.
     *
     * @return possible object is
     *         {@link PaymentItemInfoType }
     */
    public PaymentItemInfoType getPaymentItemInfo()
    {
        return paymentItemInfo;
    }

    /**
     * Sets the value of the paymentItemInfo property.
     *
     * @param value allowed object is
     *              {@link PaymentItemInfoType }
     */
    public void setPaymentItemInfo(PaymentItemInfoType value)
    {
        this.paymentItemInfo = value;
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
     * Gets the value of the giftReceipt property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getGiftReceipt()
    {
        return giftReceipt;
    }

    /**
     * Sets the value of the giftReceipt property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setGiftReceipt(String value)
    {
        this.giftReceipt = value;
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
     * Gets the value of the buyerEmailOptIn property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getBuyerEmailOptIn()
    {
        return buyerEmailOptIn;
    }

    /**
     * Sets the value of the buyerEmailOptIn property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setBuyerEmailOptIn(String value)
    {
        this.buyerEmailOptIn = value;
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

}
