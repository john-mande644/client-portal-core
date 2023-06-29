package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.*;


/**
 * <p>Java class for anonymous complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element ref="{urn:ebay:api:PayPalAPI}RefundTransactionRequest"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "refundTransactionRequest"
})
@XmlRootElement(name = "RefundTransactionReq")
public class RefundTransactionReq
{

    @XmlElement(name = "RefundTransactionRequest", required = true)
    protected RefundTransactionRequestType refundTransactionRequest;

    /**
     * Gets the value of the refundTransactionRequest property.
     *
     * @return possible object is
     *         {@link RefundTransactionRequestType }
     */
    public RefundTransactionRequestType getRefundTransactionRequest()
    {
        return refundTransactionRequest;
    }

    /**
     * Sets the value of the refundTransactionRequest property.
     *
     * @param value allowed object is
     *              {@link RefundTransactionRequestType }
     */
    public void setRefundTransactionRequest(RefundTransactionRequestType value)
    {
        this.refundTransactionRequest = value;
    }

}
