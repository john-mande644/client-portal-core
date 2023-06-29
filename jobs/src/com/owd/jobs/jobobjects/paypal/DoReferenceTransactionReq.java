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
 *         &lt;element ref="{urn:ebay:api:PayPalAPI}DoReferenceTransactionRequest"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "doReferenceTransactionRequest"
})
@XmlRootElement(name = "DoReferenceTransactionReq")
public class DoReferenceTransactionReq
{

    @XmlElement(name = "DoReferenceTransactionRequest", required = true)
    protected DoReferenceTransactionRequestType doReferenceTransactionRequest;

    /**
     * Gets the value of the doReferenceTransactionRequest property.
     *
     * @return possible object is
     *         {@link DoReferenceTransactionRequestType }
     */
    public DoReferenceTransactionRequestType getDoReferenceTransactionRequest()
    {
        return doReferenceTransactionRequest;
    }

    /**
     * Sets the value of the doReferenceTransactionRequest property.
     *
     * @param value allowed object is
     *              {@link DoReferenceTransactionRequestType }
     */
    public void setDoReferenceTransactionRequest(DoReferenceTransactionRequestType value)
    {
        this.doReferenceTransactionRequest = value;
    }

}
