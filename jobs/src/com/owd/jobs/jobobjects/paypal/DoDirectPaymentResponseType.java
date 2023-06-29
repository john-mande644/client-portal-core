package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DoDirectPaymentResponseType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="DoDirectPaymentResponseType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:ebay:apis:eBLBaseComponents}AbstractResponseType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="Amount" type="{urn:ebay:apis:CoreComponentTypes}BasicAmountType"/>
 *         &lt;element name="AVSCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CVV2Code" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="TransactionID" type="{urn:ebay:apis:eBLBaseComponents}TransactionId"/>
 *         &lt;element name="PendingReason" type="{urn:ebay:apis:eBLBaseComponents}PendingStatusCodeType" minOccurs="0"/>
 *         &lt;element name="PaymentStatus" type="{urn:ebay:apis:eBLBaseComponents}PaymentStatusCodeType" minOccurs="0"/>
 *         &lt;element name="FMFDetails" type="{urn:ebay:apis:eBLBaseComponents}FMFDetailsType" minOccurs="0"/>
 *         &lt;element name="ThreeDSecureResponse" type="{urn:ebay:apis:eBLBaseComponents}ThreeDSecureResponseType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DoDirectPaymentResponseType", propOrder = {
        "amount",
        "avsCode",
        "cvv2Code",
        "transactionID",
        "pendingReason",
        "paymentStatus",
        "fmfDetails",
        "threeDSecureResponse"
})
public class DoDirectPaymentResponseType
        extends AbstractResponseType
{

    @XmlElement(name = "Amount", required = true)
    protected BasicAmountType amount;
    @XmlElement(name = "AVSCode", required = true)
    protected String avsCode;
    @XmlElement(name = "CVV2Code", required = true)
    protected String cvv2Code;
    @XmlElement(name = "TransactionID", required = true)
    protected String transactionID;
    @XmlElement(name = "PendingReason")
    protected PendingStatusCodeType pendingReason;
    @XmlElement(name = "PaymentStatus")
    protected PaymentStatusCodeType paymentStatus;
    @XmlElement(name = "FMFDetails")
    protected FMFDetailsType fmfDetails;
    @XmlElement(name = "ThreeDSecureResponse")
    protected ThreeDSecureResponseType threeDSecureResponse;

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

    /**
     * Gets the value of the pendingReason property.
     *
     * @return possible object is
     *         {@link PendingStatusCodeType }
     */
    public PendingStatusCodeType getPendingReason()
    {
        return pendingReason;
    }

    /**
     * Sets the value of the pendingReason property.
     *
     * @param value allowed object is
     *              {@link PendingStatusCodeType }
     */
    public void setPendingReason(PendingStatusCodeType value)
    {
        this.pendingReason = value;
    }

    /**
     * Gets the value of the paymentStatus property.
     *
     * @return possible object is
     *         {@link PaymentStatusCodeType }
     */
    public PaymentStatusCodeType getPaymentStatus()
    {
        return paymentStatus;
    }

    /**
     * Sets the value of the paymentStatus property.
     *
     * @param value allowed object is
     *              {@link PaymentStatusCodeType }
     */
    public void setPaymentStatus(PaymentStatusCodeType value)
    {
        this.paymentStatus = value;
    }

    /**
     * Gets the value of the fmfDetails property.
     *
     * @return possible object is
     *         {@link FMFDetailsType }
     */
    public FMFDetailsType getFMFDetails()
    {
        return fmfDetails;
    }

    /**
     * Sets the value of the fmfDetails property.
     *
     * @param value allowed object is
     *              {@link FMFDetailsType }
     */
    public void setFMFDetails(FMFDetailsType value)
    {
        this.fmfDetails = value;
    }

    /**
     * Gets the value of the threeDSecureResponse property.
     *
     * @return possible object is
     *         {@link ThreeDSecureResponseType }
     */
    public ThreeDSecureResponseType getThreeDSecureResponse()
    {
        return threeDSecureResponse;
    }

    /**
     * Sets the value of the threeDSecureResponse property.
     *
     * @param value allowed object is
     *              {@link ThreeDSecureResponseType }
     */
    public void setThreeDSecureResponse(ThreeDSecureResponseType value)
    {
        this.threeDSecureResponse = value;
    }

}
