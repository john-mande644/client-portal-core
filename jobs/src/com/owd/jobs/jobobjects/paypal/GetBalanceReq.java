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
 *         &lt;element ref="{urn:ebay:api:PayPalAPI}GetBalanceRequest"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "getBalanceRequest"
})
@XmlRootElement(name = "GetBalanceReq")
public class GetBalanceReq
{

    @XmlElement(name = "GetBalanceRequest", required = true)
    protected GetBalanceRequestType getBalanceRequest;

    /**
     * Gets the value of the getBalanceRequest property.
     *
     * @return possible object is
     *         {@link GetBalanceRequestType }
     */
    public GetBalanceRequestType getGetBalanceRequest()
    {
        return getBalanceRequest;
    }

    /**
     * Sets the value of the getBalanceRequest property.
     *
     * @param value allowed object is
     *              {@link GetBalanceRequestType }
     */
    public void setGetBalanceRequest(GetBalanceRequestType value)
    {
        this.getBalanceRequest = value;
    }

}
