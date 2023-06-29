package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.*;


/**
 * <p>Java class for RefundTransactionResponseType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="RefundTransactionResponseType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:ebay:apis:eBLBaseComponents}AbstractResponseType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="RefundTransactionID" type="{urn:ebay:apis:eBLBaseComponents}TransactionId" minOccurs="0"/>
 *         &lt;element name="NetRefundAmount" type="{urn:ebay:apis:CoreComponentTypes}BasicAmountType" minOccurs="0"/>
 *         &lt;element name="FeeRefundAmount" type="{urn:ebay:apis:CoreComponentTypes}BasicAmountType" minOccurs="0"/>
 *         &lt;element name="GrossRefundAmount" type="{urn:ebay:apis:CoreComponentTypes}BasicAmountType" minOccurs="0"/>
 *         &lt;element name="TotalRefundedAmount" type="{urn:ebay:apis:CoreComponentTypes}BasicAmountType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RefundTransactionResponseType", propOrder = {
        "refundTransactionID",
        "netRefundAmount",
        "feeRefundAmount",
        "grossRefundAmount",
        "totalRefundedAmount"
})
public class RefundTransactionResponseType
        extends AbstractResponseType
{

    @XmlElementRef(name = "RefundTransactionID", namespace = "urn:ebay:api:PayPalAPI", type = JAXBElement.class)
    protected JAXBElement<String> refundTransactionID;
    @XmlElement(name = "NetRefundAmount")
    protected BasicAmountType netRefundAmount;
    @XmlElement(name = "FeeRefundAmount")
    protected BasicAmountType feeRefundAmount;
    @XmlElement(name = "GrossRefundAmount")
    protected BasicAmountType grossRefundAmount;
    @XmlElement(name = "TotalRefundedAmount")
    protected BasicAmountType totalRefundedAmount;

    /**
     * Gets the value of the refundTransactionID property.
     *
     * @return possible object is
     *         {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    public JAXBElement<String> getRefundTransactionID()
    {
        return refundTransactionID;
    }

    /**
     * Sets the value of the refundTransactionID property.
     *
     * @param value allowed object is
     *              {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    public void setRefundTransactionID(JAXBElement<String> value)
    {
        this.refundTransactionID = ((JAXBElement<String>) value);
    }

    /**
     * Gets the value of the netRefundAmount property.
     *
     * @return possible object is
     *         {@link BasicAmountType }
     */
    public BasicAmountType getNetRefundAmount()
    {
        return netRefundAmount;
    }

    /**
     * Sets the value of the netRefundAmount property.
     *
     * @param value allowed object is
     *              {@link BasicAmountType }
     */
    public void setNetRefundAmount(BasicAmountType value)
    {
        this.netRefundAmount = value;
    }

    /**
     * Gets the value of the feeRefundAmount property.
     *
     * @return possible object is
     *         {@link BasicAmountType }
     */
    public BasicAmountType getFeeRefundAmount()
    {
        return feeRefundAmount;
    }

    /**
     * Sets the value of the feeRefundAmount property.
     *
     * @param value allowed object is
     *              {@link BasicAmountType }
     */
    public void setFeeRefundAmount(BasicAmountType value)
    {
        this.feeRefundAmount = value;
    }

    /**
     * Gets the value of the grossRefundAmount property.
     *
     * @return possible object is
     *         {@link BasicAmountType }
     */
    public BasicAmountType getGrossRefundAmount()
    {
        return grossRefundAmount;
    }

    /**
     * Sets the value of the grossRefundAmount property.
     *
     * @param value allowed object is
     *              {@link BasicAmountType }
     */
    public void setGrossRefundAmount(BasicAmountType value)
    {
        this.grossRefundAmount = value;
    }

    /**
     * Gets the value of the totalRefundedAmount property.
     *
     * @return possible object is
     *         {@link BasicAmountType }
     */
    public BasicAmountType getTotalRefundedAmount()
    {
        return totalRefundedAmount;
    }

    /**
     * Sets the value of the totalRefundedAmount property.
     *
     * @param value allowed object is
     *              {@link BasicAmountType }
     */
    public void setTotalRefundedAmount(BasicAmountType value)
    {
        this.totalRefundedAmount = value;
    }

}
