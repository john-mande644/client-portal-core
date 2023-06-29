package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PaymentRequestInfoType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="PaymentRequestInfoType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="TransactionId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PaymentRequestID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PaymentError" type="{urn:ebay:apis:eBLBaseComponents}ErrorType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PaymentRequestInfoType", namespace = "urn:ebay:apis:eBLBaseComponents", propOrder = {
        "transactionId",
        "paymentRequestID",
        "paymentError"
})
public class PaymentRequestInfoType
{

    @XmlElement(name = "TransactionId", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String transactionId;
    @XmlElement(name = "PaymentRequestID", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String paymentRequestID;
    @XmlElement(name = "PaymentError", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected ErrorType paymentError;

    /**
     * Gets the value of the transactionId property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getTransactionId()
    {
        return transactionId;
    }

    /**
     * Sets the value of the transactionId property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setTransactionId(String value)
    {
        this.transactionId = value;
    }

    /**
     * Gets the value of the paymentRequestID property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getPaymentRequestID()
    {
        return paymentRequestID;
    }

    /**
     * Sets the value of the paymentRequestID property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setPaymentRequestID(String value)
    {
        this.paymentRequestID = value;
    }

    /**
     * Gets the value of the paymentError property.
     *
     * @return possible object is
     *         {@link ErrorType }
     */
    public ErrorType getPaymentError()
    {
        return paymentError;
    }

    /**
     * Sets the value of the paymentError property.
     *
     * @param value allowed object is
     *              {@link ErrorType }
     */
    public void setPaymentError(ErrorType value)
    {
        this.paymentError = value;
    }

}
