package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BAUpdateResponseDetailsType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="BAUpdateResponseDetailsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="BillingAgreementID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="BillingAgreementDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BillingAgreementStatus" type="{urn:ebay:apis:eBLBaseComponents}MerchantPullStatusCodeType"/>
 *         &lt;element name="BillingAgreementCustom" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PayerInfo" type="{urn:ebay:apis:eBLBaseComponents}PayerInfoType"/>
 *         &lt;element name="BillingAgreementMax" type="{urn:ebay:apis:CoreComponentTypes}BasicAmountType" minOccurs="0"/>
 *         &lt;element name="BillingAddress" type="{urn:ebay:apis:eBLBaseComponents}AddressType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BAUpdateResponseDetailsType", namespace = "urn:ebay:apis:eBLBaseComponents", propOrder = {
        "billingAgreementID",
        "billingAgreementDescription",
        "billingAgreementStatus",
        "billingAgreementCustom",
        "payerInfo",
        "billingAgreementMax",
        "billingAddress"
})
public class BAUpdateResponseDetailsType
{

    @XmlElement(name = "BillingAgreementID", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected String billingAgreementID;
    @XmlElement(name = "BillingAgreementDescription", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String billingAgreementDescription;
    @XmlElement(name = "BillingAgreementStatus", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected MerchantPullStatusCodeType billingAgreementStatus;
    @XmlElement(name = "BillingAgreementCustom", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String billingAgreementCustom;
    @XmlElement(name = "PayerInfo", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected PayerInfoType payerInfo;
    @XmlElement(name = "BillingAgreementMax", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected BasicAmountType billingAgreementMax;
    @XmlElement(name = "BillingAddress", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected AddressType billingAddress;

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
     * Gets the value of the billingAgreementDescription property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getBillingAgreementDescription()
    {
        return billingAgreementDescription;
    }

    /**
     * Sets the value of the billingAgreementDescription property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setBillingAgreementDescription(String value)
    {
        this.billingAgreementDescription = value;
    }

    /**
     * Gets the value of the billingAgreementStatus property.
     *
     * @return possible object is
     *         {@link MerchantPullStatusCodeType }
     */
    public MerchantPullStatusCodeType getBillingAgreementStatus()
    {
        return billingAgreementStatus;
    }

    /**
     * Sets the value of the billingAgreementStatus property.
     *
     * @param value allowed object is
     *              {@link MerchantPullStatusCodeType }
     */
    public void setBillingAgreementStatus(MerchantPullStatusCodeType value)
    {
        this.billingAgreementStatus = value;
    }

    /**
     * Gets the value of the billingAgreementCustom property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getBillingAgreementCustom()
    {
        return billingAgreementCustom;
    }

    /**
     * Sets the value of the billingAgreementCustom property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setBillingAgreementCustom(String value)
    {
        this.billingAgreementCustom = value;
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
     * Gets the value of the billingAgreementMax property.
     *
     * @return possible object is
     *         {@link BasicAmountType }
     */
    public BasicAmountType getBillingAgreementMax()
    {
        return billingAgreementMax;
    }

    /**
     * Sets the value of the billingAgreementMax property.
     *
     * @param value allowed object is
     *              {@link BasicAmountType }
     */
    public void setBillingAgreementMax(BasicAmountType value)
    {
        this.billingAgreementMax = value;
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

}
