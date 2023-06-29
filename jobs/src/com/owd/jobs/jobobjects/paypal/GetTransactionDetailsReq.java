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
 *         &lt;element ref="{urn:ebay:api:PayPalAPI}GetTransactionDetailsRequest"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "getTransactionDetailsRequest"
})
@XmlRootElement(name = "GetTransactionDetailsReq")
public class GetTransactionDetailsReq
{

    @XmlElement(name = "GetTransactionDetailsRequest", required = true)
    protected GetTransactionDetailsRequestType getTransactionDetailsRequest;

    /**
     * Gets the value of the getTransactionDetailsRequest property.
     *
     * @return possible object is
     *         {@link GetTransactionDetailsRequestType }
     */
    public GetTransactionDetailsRequestType getGetTransactionDetailsRequest()
    {
        return getTransactionDetailsRequest;
    }

    /**
     * Sets the value of the getTransactionDetailsRequest property.
     *
     * @param value allowed object is
     *              {@link GetTransactionDetailsRequestType }
     */
    public void setGetTransactionDetailsRequest(GetTransactionDetailsRequestType value)
    {
        this.getTransactionDetailsRequest = value;
    }

}
