package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DoReferenceTransactionRequestDetailsType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="DoReferenceTransactionRequestDetailsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="ReferenceID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="PaymentAction" type="{urn:ebay:apis:eBLBaseComponents}PaymentActionCodeType"/>
 *         &lt;element name="PaymentType" type="{urn:ebay:apis:eBLBaseComponents}MerchantPullPaymentCodeType" minOccurs="0"/>
 *         &lt;element name="PaymentDetails" type="{urn:ebay:apis:eBLBaseComponents}PaymentDetailsType"/>
 *         &lt;element name="CreditCard" type="{urn:ebay:apis:eBLBaseComponents}ReferenceCreditCardDetailsType" minOccurs="0"/>
 *         &lt;element name="IPAddress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MerchantSessionId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ReqConfirmShipping" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SoftDescriptor" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DoReferenceTransactionRequestDetailsType", namespace = "urn:ebay:apis:eBLBaseComponents", propOrder = {
        "referenceID",
        "paymentAction",
        "paymentType",
        "paymentDetails",
        "creditCard",
        "ipAddress",
        "merchantSessionId",
        "reqConfirmShipping",
        "softDescriptor"
})
public class DoReferenceTransactionRequestDetailsType
{

    @XmlElement(name = "ReferenceID", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected String referenceID;
    @XmlElement(name = "PaymentAction", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected PaymentActionCodeType paymentAction;
    @XmlElement(name = "PaymentType", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected MerchantPullPaymentCodeType paymentType;
    @XmlElement(name = "PaymentDetails", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected PaymentDetailsType paymentDetails;
    @XmlElement(name = "CreditCard", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected ReferenceCreditCardDetailsType creditCard;
    @XmlElement(name = "IPAddress", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String ipAddress;
    @XmlElement(name = "MerchantSessionId", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String merchantSessionId;
    @XmlElement(name = "ReqConfirmShipping", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String reqConfirmShipping;
    @XmlElement(name = "SoftDescriptor", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String softDescriptor;

    /**
     * Gets the value of the referenceID property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getReferenceID()
    {
        return referenceID;
    }

    /**
     * Sets the value of the referenceID property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setReferenceID(String value)
    {
        this.referenceID = value;
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
     * Gets the value of the paymentType property.
     *
     * @return possible object is
     *         {@link MerchantPullPaymentCodeType }
     */
    public MerchantPullPaymentCodeType getPaymentType()
    {
        return paymentType;
    }

    /**
     * Sets the value of the paymentType property.
     *
     * @param value allowed object is
     *              {@link MerchantPullPaymentCodeType }
     */
    public void setPaymentType(MerchantPullPaymentCodeType value)
    {
        this.paymentType = value;
    }

    /**
     * Gets the value of the paymentDetails property.
     *
     * @return possible object is
     *         {@link PaymentDetailsType }
     */
    public PaymentDetailsType getPaymentDetails()
    {
        return paymentDetails;
    }

    /**
     * Sets the value of the paymentDetails property.
     *
     * @param value allowed object is
     *              {@link PaymentDetailsType }
     */
    public void setPaymentDetails(PaymentDetailsType value)
    {
        this.paymentDetails = value;
    }

    /**
     * Gets the value of the creditCard property.
     *
     * @return possible object is
     *         {@link ReferenceCreditCardDetailsType }
     */
    public ReferenceCreditCardDetailsType getCreditCard()
    {
        return creditCard;
    }

    /**
     * Sets the value of the creditCard property.
     *
     * @param value allowed object is
     *              {@link ReferenceCreditCardDetailsType }
     */
    public void setCreditCard(ReferenceCreditCardDetailsType value)
    {
        this.creditCard = value;
    }

    /**
     * Gets the value of the ipAddress property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getIPAddress()
    {
        return ipAddress;
    }

    /**
     * Sets the value of the ipAddress property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setIPAddress(String value)
    {
        this.ipAddress = value;
    }

    /**
     * Gets the value of the merchantSessionId property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getMerchantSessionId()
    {
        return merchantSessionId;
    }

    /**
     * Sets the value of the merchantSessionId property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setMerchantSessionId(String value)
    {
        this.merchantSessionId = value;
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
