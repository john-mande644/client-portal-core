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
 *         &lt;element ref="{urn:ebay:api:PayPalAPI}ReverseTransactionRequest"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "reverseTransactionRequest"
})
@XmlRootElement(name = "ReverseTransactionReq")
public class ReverseTransactionReq
{

    @XmlElement(name = "ReverseTransactionRequest", required = true)
    protected ReverseTransactionRequestType reverseTransactionRequest;

    /**
     * Gets the value of the reverseTransactionRequest property.
     *
     * @return possible object is
     *         {@link ReverseTransactionRequestType }
     */
    public ReverseTransactionRequestType getReverseTransactionRequest()
    {
        return reverseTransactionRequest;
    }

    /**
     * Sets the value of the reverseTransactionRequest property.
     *
     * @param value allowed object is
     *              {@link ReverseTransactionRequestType }
     */
    public void setReverseTransactionRequest(ReverseTransactionRequestType value)
    {
        this.reverseTransactionRequest = value;
    }

}
