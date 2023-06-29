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
 * <p>Java class for DoExpressCheckoutPaymentResponseDetailsType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="DoExpressCheckoutPaymentResponseDetailsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="Token" type="{urn:ebay:apis:eBLBaseComponents}ExpressCheckoutTokenType"/>
 *         &lt;element name="PaymentInfo" type="{urn:ebay:apis:eBLBaseComponents}PaymentInfoType" maxOccurs="10" minOccurs="0"/>
 *         &lt;element name="BillingAgreementID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RedirectRequired" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Note" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SuccessPageRedirectRequested" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="UserSelectedOptions" type="{urn:ebay:apis:eBLBaseComponents}UserSelectedOptionType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DoExpressCheckoutPaymentResponseDetailsType", namespace = "urn:ebay:apis:eBLBaseComponents", propOrder = {
        "token",
        "paymentInfo",
        "billingAgreementID",
        "redirectRequired",
        "note",
        "successPageRedirectRequested",
        "userSelectedOptions"
})
public class DoExpressCheckoutPaymentResponseDetailsType
{

    @XmlElement(name = "Token", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected String token;
    @XmlElement(name = "PaymentInfo", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected List<PaymentInfoType> paymentInfo;
    @XmlElement(name = "BillingAgreementID", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String billingAgreementID;
    @XmlElement(name = "RedirectRequired", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String redirectRequired;
    @XmlElement(name = "Note", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String note;
    @XmlElement(name = "SuccessPageRedirectRequested", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String successPageRedirectRequested;
    @XmlElement(name = "UserSelectedOptions", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected UserSelectedOptionType userSelectedOptions;

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
     * Gets the value of the paymentInfo property.
     * <p/>
     * <p/>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the paymentInfo property.
     * <p/>
     * <p/>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPaymentInfo().add(newItem);
     * </pre>
     * <p/>
     * <p/>
     * <p/>
     * Objects of the following type(s) are allowed in the list
     * {@link PaymentInfoType }
     */
    public List<PaymentInfoType> getPaymentInfo()
    {
        if (paymentInfo == null)
        {
            paymentInfo = new ArrayList<PaymentInfoType>();
        }
        return this.paymentInfo;
    }

    /**
     * Gets the value of the billingAgreementID property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getBillingAgreementID()
    {
        return billingAgreementID;
    }

    /**
     * Sets the value of the billingAgreementID property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setBillingAgreementID(String value)
    {
        this.billingAgreementID = value;
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
     * Gets the value of the successPageRedirectRequested property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getSuccessPageRedirectRequested()
    {
        return successPageRedirectRequested;
    }

    /**
     * Sets the value of the successPageRedirectRequested property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setSuccessPageRedirectRequested(String value)
    {
        this.successPageRedirectRequested = value;
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

}
