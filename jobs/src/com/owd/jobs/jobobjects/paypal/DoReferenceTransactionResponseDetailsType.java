package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DoReferenceTransactionResponseDetailsType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="DoReferenceTransactionResponseDetailsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="BillingAgreementID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PaymentInfo" type="{urn:ebay:apis:eBLBaseComponents}PaymentInfoType" minOccurs="0"/>
 *         &lt;element name="Amount" type="{urn:ebay:apis:CoreComponentTypes}BasicAmountType" minOccurs="0"/>
 *         &lt;element name="AVSCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CVV2Code" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TransactionID" type="{urn:ebay:apis:eBLBaseComponents}TransactionId" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DoReferenceTransactionResponseDetailsType", namespace = "urn:ebay:apis:eBLBaseComponents", propOrder = {
        "billingAgreementID",
        "paymentInfo",
        "amount",
        "avsCode",
        "cvv2Code",
        "transactionID"
})
public class DoReferenceTransactionResponseDetailsType
{

    @XmlElement(name = "BillingAgreementID", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String billingAgreementID;
    @XmlElement(name = "PaymentInfo", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected PaymentInfoType paymentInfo;
    @XmlElement(name = "Amount", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected BasicAmountType amount;
    @XmlElement(name = "AVSCode", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String avsCode;
    @XmlElement(name = "CVV2Code", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String cvv2Code;
    @XmlElement(name = "TransactionID", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String transactionID;

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
     * Gets the value of the amount property.
     *
     * @return possible object is
     *         {@link BasicAmountType }
     */
    public BasicAmountType getAmount()
    {
        return amount;
    }

    /**
     * Sets the value of the amount property.
     *
     * @param value allowed object is
     *              {@link BasicAmountType }
     */
    public void setAmount(BasicAmountType value)
    {
        this.amount = value;
    }

    /**
     * Gets the value of the avsCode property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getAVSCode()
    {
        return avsCode;
    }

    /**
     * Sets the value of the avsCode property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setAVSCode(String value)
    {
        this.avsCode = value;
    }

    /**
     * Gets the value of the cvv2Code property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getCVV2Code()
    {
        return cvv2Code;
    }

    /**
     * Sets the value of the cvv2Code property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setCVV2Code(String value)
    {
        this.cvv2Code = value;
    }

    /**
     * Gets the value of the transactionID property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getTransactionID()
    {
        return transactionID;
    }

    /**
     * Sets the value of the transactionID property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setTransactionID(String value)
    {
        this.transactionID = value;
    }

}
