package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BillingAgreementDetailsType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="BillingAgreementDetailsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="BillingType" type="{urn:ebay:apis:eBLBaseComponents}BillingCodeType"/>
 *         &lt;element name="BillingAgreementDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PaymentType" type="{urn:ebay:apis:eBLBaseComponents}MerchantPullPaymentCodeType" minOccurs="0"/>
 *         &lt;element name="BillingAgreementCustom" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BillingAgreementDetailsType", namespace = "urn:ebay:apis:eBLBaseComponents", propOrder = {
        "billingType",
        "billingAgreementDescription",
        "paymentType",
        "billingAgreementCustom"
})
public class BillingAgreementDetailsType
{

    @XmlElement(name = "BillingType", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected BillingCodeType billingType;
    @XmlElement(name = "BillingAgreementDescription", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String billingAgreementDescription;
    @XmlElement(name = "PaymentType", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected MerchantPullPaymentCodeType paymentType;
    @XmlElement(name = "BillingAgreementCustom", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String billingAgreementCustom;

    /**
     * Gets the value of the billingType property.
     *
     * @return possible object is
     *         {@link BillingCodeType }
     */
    public BillingCodeType getBillingType()
    {
        return billingType;
    }

    /**
     * Sets the value of the billingType property.
     *
     * @param value allowed object is
     *              {@link BillingCodeType }
     */
    public void setBillingType(BillingCodeType value)
    {
        this.billingType = value;
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

}
