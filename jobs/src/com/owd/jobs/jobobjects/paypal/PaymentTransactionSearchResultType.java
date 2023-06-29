package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * PaymentTransactionSearchResultType
 * Results from a PaymentTransaction search
 * <p/>
 * <p/>
 * <p>Java class for PaymentTransactionSearchResultType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="PaymentTransactionSearchResultType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="Timestamp" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="Timezone" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Type" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Payer" type="{urn:ebay:apis:eBLBaseComponents}EmailAddressType"/>
 *         &lt;element name="PayerDisplayName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element ref="{urn:ebay:apis:eBLBaseComponents}TransactionID"/>
 *         &lt;element name="Status" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="GrossAmount" type="{urn:ebay:apis:CoreComponentTypes}BasicAmountType"/>
 *         &lt;element name="FeeAmount" type="{urn:ebay:apis:CoreComponentTypes}BasicAmountType"/>
 *         &lt;element name="NetAmount" type="{urn:ebay:apis:CoreComponentTypes}BasicAmountType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PaymentTransactionSearchResultType", namespace = "urn:ebay:apis:eBLBaseComponents", propOrder = {
        "timestamp",
        "timezone",
        "type",
        "payer",
        "payerDisplayName",
        "transactionID",
        "status",
        "grossAmount",
        "feeAmount",
        "netAmount"
})
public class PaymentTransactionSearchResultType
{

    @XmlElement(name = "Timestamp", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected XMLGregorianCalendar timestamp;
    @XmlElement(name = "Timezone", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected String timezone;
    @XmlElement(name = "Type", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected String type;
    @XmlElement(name = "Payer", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected String payer;
    @XmlElement(name = "PayerDisplayName", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected String payerDisplayName;
    @XmlElement(name = "TransactionID", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected String transactionID;
    @XmlElement(name = "Status", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected String status;
    @XmlElement(name = "GrossAmount", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected BasicAmountType grossAmount;
    @XmlElement(name = "FeeAmount", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected BasicAmountType feeAmount;
    @XmlElement(name = "NetAmount", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected BasicAmountType netAmount;

    /**
     * Gets the value of the timestamp property.
     *
     * @return possible object is
     *         {@link XMLGregorianCalendar }
     */
    public XMLGregorianCalendar getTimestamp()
    {
        return timestamp;
    }

    /**
     * Sets the value of the timestamp property.
     *
     * @param value allowed object is
     *              {@link XMLGregorianCalendar }
     */
    public void setTimestamp(XMLGregorianCalendar value)
    {
        this.timestamp = value;
    }

    /**
     * Gets the value of the timezone property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getTimezone()
    {
        return timezone;
    }

    /**
     * Sets the value of the timezone property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setTimezone(String value)
    {
        this.timezone = value;
    }

    /**
     * Gets the value of the type property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getType()
    {
        return type;
    }

    /**
     * Sets the value of the type property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setType(String value)
    {
        this.type = value;
    }

    /**
     * Gets the value of the payer property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getPayer()
    {
        return payer;
    }

    /**
     * Sets the value of the payer property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setPayer(String value)
    {
        this.payer = value;
    }

    /**
     * Gets the value of the payerDisplayName property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getPayerDisplayName()
    {
        return payerDisplayName;
    }

    /**
     * Sets the value of the payerDisplayName property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setPayerDisplayName(String value)
    {
        this.payerDisplayName = value;
    }

    /**
     * The transaction ID of the seller
     *
     * @return possible object is
     *         {@link String }
     */
    public String getTransactionID()
    {
        return transactionID;
    }

    /**
     * The transaction ID of the seller
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setTransactionID(String value)
    {
        this.transactionID = value;
    }

    /**
     * Gets the value of the status property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getStatus()
    {
        return status;
    }

    /**
     * Sets the value of the status property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setStatus(String value)
    {
        this.status = value;
    }

    /**
     * Gets the value of the grossAmount property.
     *
     * @return possible object is
     *         {@link BasicAmountType }
     */
    public BasicAmountType getGrossAmount()
    {
        return grossAmount;
    }

    /**
     * Sets the value of the grossAmount property.
     *
     * @param value allowed object is
     *              {@link BasicAmountType }
     */
    public void setGrossAmount(BasicAmountType value)
    {
        this.grossAmount = value;
    }

    /**
     * Gets the value of the feeAmount property.
     *
     * @return possible object is
     *         {@link BasicAmountType }
     */
    public BasicAmountType getFeeAmount()
    {
        return feeAmount;
    }

    /**
     * Sets the value of the feeAmount property.
     *
     * @param value allowed object is
     *              {@link BasicAmountType }
     */
    public void setFeeAmount(BasicAmountType value)
    {
        this.feeAmount = value;
    }

    /**
     * Gets the value of the netAmount property.
     *
     * @return possible object is
     *         {@link BasicAmountType }
     */
    public BasicAmountType getNetAmount()
    {
        return netAmount;
    }

    /**
     * Sets the value of the netAmount property.
     *
     * @param value allowed object is
     *              {@link BasicAmountType }
     */
    public void setNetAmount(BasicAmountType value)
    {
        this.netAmount = value;
    }

}
