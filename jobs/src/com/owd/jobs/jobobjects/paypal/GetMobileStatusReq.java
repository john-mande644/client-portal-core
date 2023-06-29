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
 *         &lt;element ref="{urn:ebay:api:PayPalAPI}GetMobileStatusRequest"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "getMobileStatusRequest"
})
@XmlRootElement(name = "GetMobileStatusReq")
public class GetMobileStatusReq
{

    @XmlElement(name = "GetMobileStatusRequest", required = true)
    protected GetMobileStatusRequestType getMobileStatusRequest;

    /**
     * Gets the value of the getMobileStatusRequest property.
     *
     * @return possible object is
     *         {@link GetMobileStatusRequestType }
     */
    public GetMobileStatusRequestType getGetMobileStatusRequest()
    {
        return getMobileStatusRequest;
    }

    /**
     * Sets the value of the getMobileStatusRequest property.
     *
     * @param value allowed object is
     *              {@link GetMobileStatusRequestType }
     */
    public void setGetMobileStatusRequest(GetMobileStatusRequestType value)
    {
        this.getMobileStatusRequest = value;
    }

}
