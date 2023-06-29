package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SetCustomerBillingAgreementRequestDetailsType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="SetCustomerBillingAgreementRequestDetailsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="BillingAgreementDetails" type="{urn:ebay:apis:eBLBaseComponents}BillingAgreementDetailsType"/>
 *         &lt;element name="ReturnURL" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CancelURL" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="LocaleCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PageStyle" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cpp-header-image" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cpp-header-border-color" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cpp-header-back-color" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cpp-payflow-color" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BuyerEmail" type="{urn:ebay:apis:eBLBaseComponents}EmailAddressType" minOccurs="0"/>
 *         &lt;element name="ReqBillingAddress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SetCustomerBillingAgreementRequestDetailsType", namespace = "urn:ebay:apis:eBLBaseComponents", propOrder = {
        "billingAgreementDetails",
        "returnURL",
        "cancelURL",
        "localeCode",
        "pageStyle",
        "cppHeaderImage",
        "cppHeaderBorderColor",
        "cppHeaderBackColor",
        "cppPayflowColor",
        "buyerEmail",
        "reqBillingAddress"
})
public class SetCustomerBillingAgreementRequestDetailsType
{

    @XmlElement(name = "BillingAgreementDetails", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected BillingAgreementDetailsType billingAgreementDetails;
    @XmlElement(name = "ReturnURL", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected String returnURL;
    @XmlElement(name = "CancelURL", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected String cancelURL;
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
    @XmlElement(name = "BuyerEmail", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String buyerEmail;
    @XmlElement(name = "ReqBillingAddress", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String reqBillingAddress;

    /**
     * Gets the value of the billingAgreementDetails property.
     *
     * @return possible object is
     *         {@link BillingAgreementDetailsType }
     */
    public BillingAgreementDetailsType getBillingAgreementDetails()
    {
        return billingAgreementDetails;
    }

    /**
     * Sets the value of the billingAgreementDetails property.
     *
     * @param value allowed object is
     *              {@link BillingAgreementDetailsType }
     */
    public void setBillingAgreementDetails(BillingAgreementDetailsType value)
    {
        this.billingAgreementDetails = value;
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

}
