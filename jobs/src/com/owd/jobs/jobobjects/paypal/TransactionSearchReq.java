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
 *         &lt;element ref="{urn:ebay:api:PayPalAPI}TransactionSearchRequest"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "transactionSearchRequest"
})
@XmlRootElement(name = "TransactionSearchReq")
public class TransactionSearchReq
{

    @XmlElement(name = "TransactionSearchRequest", required = true)
    protected TransactionSearchRequestType transactionSearchRequest;

    /**
     * Gets the value of the transactionSearchRequest property.
     *
     * @return possible object is
     *         {@link TransactionSearchRequestType }
     */
    public TransactionSearchRequestType getTransactionSearchRequest()
    {
        return transactionSearchRequest;
    }

    /**
     * Sets the value of the transactionSearchRequest property.
     *
     * @param value allowed object is
     *              {@link TransactionSearchRequestType }
     */
    public void setTransactionSearchRequest(TransactionSearchRequestType value)
    {
        this.transactionSearchRequest = value;
    }

}
