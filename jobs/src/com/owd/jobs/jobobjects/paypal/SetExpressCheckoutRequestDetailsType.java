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
 * <p>Java class for SetExpressCheckoutRequestDetailsType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="SetExpressCheckoutRequestDetailsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="OrderTotal" type="{urn:ebay:apis:CoreComponentTypes}BasicAmountType" minOccurs="0"/>
 *         &lt;element name="ReturnURL" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CancelURL" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="TrackingImageURL" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="giropaySuccessURL" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="giropayCancelURL" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BanktxnPendingURL" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Token" type="{urn:ebay:apis:eBLBaseComponents}ExpressCheckoutTokenType" minOccurs="0"/>
 *         &lt;element name="MaxAmount" type="{urn:ebay:apis:CoreComponentTypes}BasicAmountType" minOccurs="0"/>
 *         &lt;element name="OrderDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Custom" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="InvoiceID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ReqConfirmShipping" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ReqBillingAddress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BillingAddress" type="{urn:ebay:apis:eBLBaseComponents}AddressType" minOccurs="0"/>
 *         &lt;element name="NoShipping" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AddressOverride" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LocaleCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PageStyle" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cpp-header-image" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cpp-header-border-color" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cpp-header-back-color" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cpp-payflow-color" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cpp-cart-border-color" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cpp-logo-image" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Address" type="{urn:ebay:apis:eBLBaseComponents}AddressType" minOccurs="0"/>
 *         &lt;element name="PaymentAction" type="{urn:ebay:apis:eBLBaseComponents}PaymentActionCodeType" minOccurs="0"/>
 *         &lt;element name="SolutionType" type="{urn:ebay:apis:eBLBaseComponents}SolutionTypeType" minOccurs="0"/>
 *         &lt;element name="LandingPage" type="{urn:ebay:apis:eBLBaseComponents}LandingPageType" minOccurs="0"/>
 *         &lt;element name="BuyerEmail" type="{urn:ebay:apis:eBLBaseComponents}EmailAddressType" minOccurs="0"/>
 *         &lt;element name="ChannelType" type="{urn:ebay:apis:eBLBaseComponents}ChannelType" minOccurs="0"/>
 *         &lt;element name="BillingAgreementDetails" type="{urn:ebay:apis:eBLBaseComponents}BillingAgreementDetailsType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="PromoCodes" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="PayPalCheckOutBtnType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ProductCategory" type="{urn:ebay:apis:eBLBaseComponents}ProductCategoryType" minOccurs="0"/>
 *         &lt;element name="ShippingMethod" type="{urn:ebay:apis:eBLBaseComponents}ShippingServiceCodeType" minOccurs="0"/>
 *         &lt;element name="ProfileAddressChangeDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="AllowNote" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FundingSourceDetails" type="{urn:ebay:apis:eBLBaseComponents}FundingSourceDetailsType" minOccurs="0"/>
 *         &lt;element name="BrandName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CallbackURL" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EnhancedCheckoutData" type="{urn:ebay:apis:EnhancedDataTypes}EnhancedCheckoutDataType" minOccurs="0"/>
 *         &lt;element name="OtherPaymentMethods" type="{urn:ebay:apis:eBLBaseComponents}OtherPaymentMethodDetailsType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="BuyerDetails" type="{urn:ebay:apis:eBLBaseComponents}BuyerDetailsType" minOccurs="0"/>
 *         &lt;element name="PaymentDetails" type="{urn:ebay:apis:eBLBaseComponents}PaymentDetailsType" maxOccurs="10" minOccurs="0"/>
 *         &lt;element name="FlatRateShippingOptions" type="{urn:ebay:apis:eBLBaseComponents}ShippingOptionType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="CallbackTimeout" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CallbackVersion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CustomerServiceNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="GiftMessageEnable" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="GiftReceiptEnable" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="GiftWrapEnable" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="GiftWrapName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="GiftWrapAmount" type="{urn:ebay:apis:CoreComponentTypes}BasicAmountType" minOccurs="0"/>
 *         &lt;element name="BuyerEmailOptInEnable" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SurveyEnable" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SurveyQuestion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SurveyChoice" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="TotalType" type="{urn:ebay:apis:eBLBaseComponents}TotalType" minOccurs="0"/>
 *         &lt;element name="NoteToBuyer" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Incentives" type="{urn:ebay:apis:eBLBaseComponents}IncentiveInfoType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SetExpressCheckoutRequestDetailsType", namespace = "urn:ebay:apis:eBLBaseComponents", propOrder = {
        "orderTotal",
        "returnURL",
        "cancelURL",
        "trackingImageURL",
        "giropaySuccessURL",
        "giropayCancelURL",
        "banktxnPendingURL",
        "token",
        "maxAmount",
        "orderDescription",
        "custom",
        "invoiceID",
        "reqConfirmShipping",
        "reqBillingAddress",
        "billingAddress",
        "noShipping",
        "addressOverride",
        "localeCode",
        "pageStyle",
        "cppHeaderImage",
        "cppHeaderBorderColor",
        "cppHeaderBackColor",
        "cppPayflowColor",
        "cppCartBorderColor",
        "cppLogoImage",
        "address",
        "paymentAction",
        "solutionType",
        "landingPage",
        "buyerEmail",
        "channelType",
        "billingAgreementDetails",
        "promoCodes",
        "payPalCheckOutBtnType",
        "productCategory",
        "shippingMethod",
        "profileAddressChangeDate",
        "allowNote",
        "fundingSourceDetails",
        "brandName",
        "callbackURL",
        "enhancedCheckoutData",
        "otherPaymentMethods",
        "buyerDetails",
        "paymentDetails",
        "flatRateShippingOptions",
        "callbackTimeout",
        "callbackVersion",
        "customerServiceNumber",
        "giftMessageEnable",
        "giftReceiptEnable",
        "giftWrapEnable",
        "giftWrapName",
        "giftWrapAmount",
        "buyerEmailOptInEnable",
        "surveyEnable",
        "surveyQuestion",
        "surveyChoice",
        "totalType",
        "noteToBuyer",
        "incentives"
})
public class SetExpressCheckoutRequestDetailsType
{

    @XmlElement(name = "OrderTotal", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected BasicAmountType orderTotal;
    @XmlElement(name = "ReturnURL", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected String returnURL;
    @XmlElement(name = "CancelURL", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected String cancelURL;
    @XmlElement(name = "TrackingImageURL", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String trackingImageURL;
    @XmlElement(namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String giropaySuccessURL;
    @XmlElement(namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String giropayCancelURL;
    @XmlElement(name = "BanktxnPendingURL", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String banktxnPendingURL;
    @XmlElement(name = "Token", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String token;
    @XmlElement(name = "MaxAmount", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected BasicAmountType maxAmount;
    @XmlElement(name = "OrderDescription", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String orderDescription;
    @XmlElement(name = "Custom", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String custom;
    @XmlElement(name = "InvoiceID", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String invoiceID;
    @XmlElement(name = "ReqConfirmShipping", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String reqConfirmShipping;
    @XmlElement(name = "ReqBillingAddress", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String reqBillingAddress;
    @XmlElement(name = "BillingAddress", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected AddressType billingAddress;
    @XmlElement(name = "NoShipping", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String noShipping;
    @XmlElement(name = "AddressOverride", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String addressOverride;
    @XmlElement(name = "LocaleCode", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String localeCode;
    @XmlElement(name = "PageStyle", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String pageStyle;
    @XmlElement(name = "cpp-header-image", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String cppHeaderImage;
    @XmlElement(name = "cpp-header-border-color", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String cppHeaderBorderColor;
    @XmlElement(name = "cpp-header-back-color", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String cppHeaderBackColor;
    @XmlElement(name = "cpp-payflow-color", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String cppPayflowColor;
    @XmlElement(name = "cpp-cart-border-color", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String cppCartBorderColor;
    @XmlElement(name = "cpp-logo-image", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String cppLogoImage;
    @XmlElement(name = "Address", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected AddressType address;
    @XmlElement(name = "PaymentAction", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected PaymentActionCodeType paymentAction;
    @XmlElement(name = "SolutionType", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected SolutionTypeType solutionType;
    @XmlElement(name = "LandingPage", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected LandingPageType landingPage;
    @XmlElement(name = "BuyerEmail", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String buyerEmail;
    @XmlElement(name = "ChannelType", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected ChannelType channelType;
    @XmlElement(name = "BillingAgreementDetails", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected List<BillingAgreementDetailsType> billingAgreementDetails;
    @XmlElement(name = "PromoCodes", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected List<String> promoCodes;
    @XmlElement(name = "PayPalCheckOutBtnType", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String payPalCheckOutBtnType;
    @XmlElement(name = "ProductCategory", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected ProductCategoryType productCategory;
    @XmlElement(name = "ShippingMethod", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected ShippingServiceCodeType shippingMethod;
    @XmlElement(name = "ProfileAddressChangeDate", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected XMLGregorianCalendar profileAddressChangeDate;
    @XmlElement(name = "AllowNote", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String allowNote;
    @XmlElement(name = "FundingSourceDetails", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected FundingSourceDetailsType fundingSourceDetails;
    @XmlElement(name = "BrandName", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String brandName;
    @XmlElement(name = "CallbackURL", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String callbackURL;
    @XmlElement(name = "EnhancedCheckoutData", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected EnhancedCheckoutDataType enhancedCheckoutData;
    @XmlElement(name = "OtherPaymentMethods", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected List<OtherPaymentMethodDetailsType> otherPaymentMethods;
    @XmlElement(name = "BuyerDetails", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected BuyerDetailsType buyerDetails;
    @XmlElement(name = "PaymentDetails", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected List<PaymentDetailsType> paymentDetails;
    @XmlElement(name = "FlatRateShippingOptions", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected List<ShippingOptionType> flatRateShippingOptions;
    @XmlElement(name = "CallbackTimeout", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String callbackTimeout;
    @XmlElement(name = "CallbackVersion", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String callbackVersion;
    @XmlElement(name = "CustomerServiceNumber", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String customerServiceNumber;
    @XmlElement(name = "GiftMessageEnable", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String giftMessageEnable;
    @XmlElement(name = "GiftReceiptEnable", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String giftReceiptEnable;
    @XmlElement(name = "GiftWrapEnable", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String giftWrapEnable;
    @XmlElement(name = "GiftWrapName", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String giftWrapName;
    @XmlElement(name = "GiftWrapAmount", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected BasicAmountType giftWrapAmount;
    @XmlElement(name = "BuyerEmailOptInEnable", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String buyerEmailOptInEnable;
    @XmlElement(name = "SurveyEnable", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String surveyEnable;
    @XmlElement(name = "SurveyQuestion", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String surveyQuestion;
    @XmlElement(name = "SurveyChoice", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected List<String> surveyChoice;
    @XmlElement(name = "TotalType", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected TotalType totalType;
    @XmlElement(name = "NoteToBuyer", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String noteToBuyer;
    @XmlElement(name = "Incentives", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected List<IncentiveInfoType> incentives;

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
     * Gets the value of the returnURL property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getReturnURL()
    {
        return returnURL;
    }

    /**
     * Sets the value of the returnURL property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setReturnURL(String value)
    {
        this.returnURL = value;
    }

    /**
     * Gets the value of the cancelURL property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getCancelURL()
    {
        return cancelURL;
    }

    /**
     * Sets the value of the cancelURL property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setCancelURL(String value)
    {
        this.cancelURL = value;
    }

    /**
     * Gets the value of the trackingImageURL property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getTrackingImageURL()
    {
        return trackingImageURL;
    }

    /**
     * Sets the value of the trackingImageURL property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setTrackingImageURL(String value)
    {
        this.trackingImageURL = value;
    }

    /**
     * Gets the value of the giropaySuccessURL property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getGiropaySuccessURL()
    {
        return giropaySuccessURL;
    }

    /**
     * Sets the value of the giropaySuccessURL property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setGiropaySuccessURL(String value)
    {
        this.giropaySuccessURL = value;
    }

    /**
     * Gets the value of the giropayCancelURL property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getGiropayCancelURL()
    {
        return giropayCancelURL;
    }

    /**
     * Sets the value of the giropayCancelURL property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setGiropayCancelURL(String value)
    {
        this.giropayCancelURL = value;
    }

    /**
     * Gets the value of the banktxnPendingURL property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getBanktxnPendingURL()
    {
        return banktxnPendingURL;
    }

    /**
     * Sets the value of the banktxnPendingURL property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setBanktxnPendingURL(String value)
    {
        this.banktxnPendingURL = value;
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
     * Gets the value of the maxAmount property.
     *
     * @return possible object is
     *         {@link BasicAmountType }
     */
    public BasicAmountType getMaxAmount()
    {
        return maxAmount;
    }

    /**
     * Sets the value of the maxAmount property.
     *
     * @param value allowed object is
     *              {@link BasicAmountType }
     */
    public void setMaxAmount(BasicAmountType value)
    {
        this.maxAmount = value;
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
     * Gets the value of the reqConfirmShipping property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getReqConfirmShipping()
    {
        return reqConfirmShipping;
    }

    /**
     * Sets the value of the reqConfirmShipping property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setReqConfirmShipping(String value)
    {
        this.reqConfirmShipping = value;
    }

    /**
     * Gets the value of the reqBillingAddress property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getReqBillingAddress()
    {
        return reqBillingAddress;
    }

    /**
     * Sets the value of the reqBillingAddress property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setReqBillingAddress(String value)
    {
        this.reqBillingAddress = value;
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
     * Gets the value of the noShipping property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getNoShipping()
    {
        return noShipping;
    }

    /**
     * Sets the value of the noShipping property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setNoShipping(String value)
    {
        this.noShipping = value;
    }

    /**
     * Gets the value of the addressOverride property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getAddressOverride()
    {
        return addressOverride;
    }

    /**
     * Sets the value of the addressOverride property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setAddressOverride(String value)
    {
        this.addressOverride = value;
    }

    /**
     * Gets the value of the localeCode property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getLocaleCode()
    {
        return localeCode;
    }

    /**
     * Sets the value of the localeCode property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setLocaleCode(String value)
    {
        this.localeCode = value;
    }

    /**
     * Gets the value of the pageStyle property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getPageStyle()
    {
        return pageStyle;
    }

    /**
     * Sets the value of the pageStyle property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setPageStyle(String value)
    {
        this.pageStyle = value;
    }

    /**
     * Gets the value of the cppHeaderImage property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getCppHeaderImage()
    {
        return cppHeaderImage;
    }

    /**
     * Sets the value of the cppHeaderImage property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setCppHeaderImage(String value)
    {
        this.cppHeaderImage = value;
    }

    /**
     * Gets the value of the cppHeaderBorderColor property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getCppHeaderBorderColor()
    {
        return cppHeaderBorderColor;
    }

    /**
     * Sets the value of the cppHeaderBorderColor property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setCppHeaderBorderColor(String value)
    {
        this.cppHeaderBorderColor = value;
    }

    /**
     * Gets the value of the cppHeaderBackColor property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getCppHeaderBackColor()
    {
        return cppHeaderBackColor;
    }

    /**
     * Sets the value of the cppHeaderBackColor property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setCppHeaderBackColor(String value)
    {
        this.cppHeaderBackColor = value;
    }

    /**
     * Gets the value of the cppPayflowColor property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getCppPayflowColor()
    {
        return cppPayflowColor;
    }

    /**
     * Sets the value of the cppPayflowColor property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setCppPayflowColor(String value)
    {
        this.cppPayflowColor = value;
    }

    /**
     * Gets the value of the cppCartBorderColor property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getCppCartBorderColor()
    {
        return cppCartBorderColor;
    }

    /**
     * Sets the value of the cppCartBorderColor property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setCppCartBorderColor(String value)
    {
        this.cppCartBorderColor = value;
    }

    /**
     * Gets the value of the cppLogoImage property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getCppLogoImage()
    {
        return cppLogoImage;
    }

    /**
     * Sets the value of the cppLogoImage property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setCppLogoImage(String value)
    {
        this.cppLogoImage = value;
    }

    /**
     * Gets the value of the address property.
     *
     * @return possible object is
     *         {@link AddressType }
     */
    public AddressType getAddress()
    {
        return address;
    }

    /**
     * Sets the value of the address property.
     *
     * @param value allowed object is
     *              {@link AddressType }
     */
    public void setAddress(AddressType value)
    {
        this.address = value;
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
     * Gets the value of the solutionType property.
     *
     * @return possible object is
     *         {@link SolutionTypeType }
     */
    public SolutionTypeType getSolutionType()
    {
        return solutionType;
    }

    /**
     * Sets the value of the solutionType property.
     *
     * @param value allowed object is
     *              {@link SolutionTypeType }
     */
    public void setSolutionType(SolutionTypeType value)
    {
        this.solutionType = value;
    }

    /**
     * Gets the value of the landingPage property.
     *
     * @return possible object is
     *         {@link LandingPageType }
     */
    public LandingPageType getLandingPage()
    {
        return landingPage;
    }

    /**
     * Sets the value of the landingPage property.
     *
     * @param value allowed object is
     *              {@link LandingPageType }
     */
    public void setLandingPage(LandingPageType value)
    {
        this.landingPage = value;
    }

    /**
     * Gets the value of the buyerEmail property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getBuyerEmail()
    {
        return buyerEmail;
    }

    /**
     * Sets the value of the buyerEmail property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setBuyerEmail(String value)
    {
        this.buyerEmail = value;
    }

    /**
     * Gets the value of the channelType property.
     *
     * @return possible object is
     *         {@link ChannelType }
     */
    public ChannelType getChannelType()
    {
        return channelType;
    }

    /**
     * Sets the value of the channelType property.
     *
     * @param value allowed object is
     *              {@link ChannelType }
     */
    public void setChannelType(ChannelType value)
    {
        this.channelType = value;
    }

    /**
     * Gets the value of the billingAgreementDetails property.
     * <p/>
     * <p/>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the billingAgreementDetails property.
     * <p/>
     * <p/>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBillingAgreementDetails().add(newItem);
     * </pre>
     * <p/>
     * <p/>
     * <p/>
     * Objects of the following type(s) are allowed in the list
     * {@link BillingAgreementDetailsType }
     */
    public List<BillingAgreementDetailsType> getBillingAgreementDetails()
    {
        if (billingAgreementDetails == null)
        {
            billingAgreementDetails = new ArrayList<BillingAgreementDetailsType>();
        }
        return this.billingAgreementDetails;
    }

    /**
     * Gets the value of the promoCodes property.
     * <p/>
     * <p/>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the promoCodes property.
     * <p/>
     * <p/>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPromoCodes().add(newItem);
     * </pre>
     * <p/>
     * <p/>
     * <p/>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     */
    public List<String> getPromoCodes()
    {
        if (promoCodes == null)
        {
            promoCodes = new ArrayList<String>();
        }
        return this.promoCodes;
    }

    /**
     * Gets the value of the payPalCheckOutBtnType property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getPayPalCheckOutBtnType()
    {
        return payPalCheckOutBtnType;
    }

    /**
     * Sets the value of the payPalCheckOutBtnType property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setPayPalCheckOutBtnType(String value)
    {
        this.payPalCheckOutBtnType = value;
    }

    /**
     * Gets the value of the productCategory property.
     *
     * @return possible object is
     *         {@link ProductCategoryType }
     */
    public ProductCategoryType getProductCategory()
    {
        return productCategory;
    }

    /**
     * Sets the value of the productCategory property.
     *
     * @param value allowed object is
     *              {@link ProductCategoryType }
     */
    public void setProductCategory(ProductCategoryType value)
    {
        this.productCategory = value;
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
     * Gets the value of the allowNote property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getAllowNote()
    {
        return allowNote;
    }

    /**
     * Sets the value of the allowNote property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setAllowNote(String value)
    {
        this.allowNote = value;
    }

    /**
     * Gets the value of the fundingSourceDetails property.
     *
     * @return possible object is
     *         {@link FundingSourceDetailsType }
     */
    public FundingSourceDetailsType getFundingSourceDetails()
    {
        return fundingSourceDetails;
    }

    /**
     * Sets the value of the fundingSourceDetails property.
     *
     * @param value allowed object is
     *              {@link FundingSourceDetailsType }
     */
    public void setFundingSourceDetails(FundingSourceDetailsType value)
    {
        this.fundingSourceDetails = value;
    }

    /**
     * Gets the value of the brandName property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getBrandName()
    {
        return brandName;
    }

    /**
     * Sets the value of the brandName property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setBrandName(String value)
    {
        this.brandName = value;
    }

    /**
     * Gets the value of the callbackURL property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getCallbackURL()
    {
        return callbackURL;
    }

    /**
     * Sets the value of the callbackURL property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setCallbackURL(String value)
    {
        this.callbackURL = value;
    }

    /**
     * Gets the value of the enhancedCheckoutData property.
     *
     * @return possible object is
     *         {@link EnhancedCheckoutDataType }
     */
    public EnhancedCheckoutDataType getEnhancedCheckoutData()
    {
        return enhancedCheckoutData;
    }

    /**
     * Sets the value of the enhancedCheckoutData property.
     *
     * @param value allowed object is
     *              {@link EnhancedCheckoutDataType }
     */
    public void setEnhancedCheckoutData(EnhancedCheckoutDataType value)
    {
        this.enhancedCheckoutData = value;
    }

    /**
     * Gets the value of the otherPaymentMethods property.
     * <p/>
     * <p/>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the otherPaymentMethods property.
     * <p/>
     * <p/>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOtherPaymentMethods().add(newItem);
     * </pre>
     * <p/>
     * <p/>
     * <p/>
     * Objects of the following type(s) are allowed in the list
     * {@link OtherPaymentMethodDetailsType }
     */
    public List<OtherPaymentMethodDetailsType> getOtherPaymentMethods()
    {
        if (otherPaymentMethods == null)
        {
            otherPaymentMethods = new ArrayList<OtherPaymentMethodDetailsType>();
        }
        return this.otherPaymentMethods;
    }

    /**
     * Gets the value of the buyerDetails property.
     *
     * @return possible object is
     *         {@link BuyerDetailsType }
     */
    public BuyerDetailsType getBuyerDetails()
    {
        return buyerDetails;
    }

    /**
     * Sets the value of the buyerDetails property.
     *
     * @param value allowed object is
     *              {@link BuyerDetailsType }
     */
    public void setBuyerDetails(BuyerDetailsType value)
    {
        this.buyerDetails = value;
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
     * Gets the value of the flatRateShippingOptions property.
     * <p/>
     * <p/>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the flatRateShippingOptions property.
     * <p/>
     * <p/>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFlatRateShippingOptions().add(newItem);
     * </pre>
     * <p/>
     * <p/>
     * <p/>
     * Objects of the following type(s) are allowed in the list
     * {@link ShippingOptionType }
     */
    public List<ShippingOptionType> getFlatRateShippingOptions()
    {
        if (flatRateShippingOptions == null)
        {
            flatRateShippingOptions = new ArrayList<ShippingOptionType>();
        }
        return this.flatRateShippingOptions;
    }

    /**
     * Gets the value of the callbackTimeout property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getCallbackTimeout()
    {
        return callbackTimeout;
    }

    /**
     * Sets the value of the callbackTimeout property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setCallbackTimeout(String value)
    {
        this.callbackTimeout = value;
    }

    /**
     * Gets the value of the callbackVersion property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getCallbackVersion()
    {
        return callbackVersion;
    }

    /**
     * Sets the value of the callbackVersion property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setCallbackVersion(String value)
    {
        this.callbackVersion = value;
    }

    /**
     * Gets the value of the customerServiceNumber property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getCustomerServiceNumber()
    {
        return customerServiceNumber;
    }

    /**
     * Sets the value of the customerServiceNumber property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setCustomerServiceNumber(String value)
    {
        this.customerServiceNumber = value;
    }

    /**
     * Gets the value of the giftMessageEnable property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getGiftMessageEnable()
    {
        return giftMessageEnable;
    }

    /**
     * Sets the value of the giftMessageEnable property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setGiftMessageEnable(String value)
    {
        this.giftMessageEnable = value;
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
     * Gets the value of the giftWrapEnable property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getGiftWrapEnable()
    {
        return giftWrapEnable;
    }

    /**
     * Sets the value of the giftWrapEnable property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setGiftWrapEnable(String value)
    {
        this.giftWrapEnable = value;
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
     * Gets the value of the buyerEmailOptInEnable property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getBuyerEmailOptInEnable()
    {
        return buyerEmailOptInEnable;
    }

    /**
     * Sets the value of the buyerEmailOptInEnable property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setBuyerEmailOptInEnable(String value)
    {
        this.buyerEmailOptInEnable = value;
    }

    /**
     * Gets the value of the surveyEnable property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getSurveyEnable()
    {
        return surveyEnable;
    }

    /**
     * Sets the value of the surveyEnable property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setSurveyEnable(String value)
    {
        this.surveyEnable = value;
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
     * Gets the value of the surveyChoice property.
     * <p/>
     * <p/>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the surveyChoice property.
     * <p/>
     * <p/>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSurveyChoice().add(newItem);
     * </pre>
     * <p/>
     * <p/>
     * <p/>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     */
    public List<String> getSurveyChoice()
    {
        if (surveyChoice == null)
        {
            surveyChoice = new ArrayList<String>();
        }
        return this.surveyChoice;
    }

    /**
     * Gets the value of the totalType property.
     *
     * @return possible object is
     *         {@link TotalType }
     */
    public TotalType getTotalType()
    {
        return totalType;
    }

    /**
     * Sets the value of the totalType property.
     *
     * @param value allowed object is
     *              {@link TotalType }
     */
    public void setTotalType(TotalType value)
    {
        this.totalType = value;
    }

    /**
     * Gets the value of the noteToBuyer property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getNoteToBuyer()
    {
        return noteToBuyer;
    }

    /**
     * Sets the value of the noteToBuyer property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setNoteToBuyer(String value)
    {
        this.noteToBuyer = value;
    }

    /**
     * Gets the value of the incentives property.
     * <p/>
     * <p/>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the incentives property.
     * <p/>
     * <p/>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIncentives().add(newItem);
     * </pre>
     * <p/>
     * <p/>
     * <p/>
     * Objects of the following type(s) are allowed in the list
     * {@link IncentiveInfoType }
     */
    public List<IncentiveInfoType> getIncentives()
    {
        if (incentives == null)
        {
            incentives = new ArrayList<IncentiveInfoType>();
        }
        return this.incentives;
    }

}
