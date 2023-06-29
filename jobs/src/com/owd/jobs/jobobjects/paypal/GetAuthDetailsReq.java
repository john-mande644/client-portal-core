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
 *         &lt;element ref="{urn:ebay:api:PayPalAPI}GetAuthDetailsRequest"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "getAuthDetailsRequest"
})
@XmlRootElement(name = "GetAuthDetailsReq")
public class GetAuthDetailsReq
{

    @XmlElement(name = "GetAuthDetailsRequest", required = true)
    protected GetAuthDetailsRequestType getAuthDetailsRequest;

    /**
     * Gets the value of the getAuthDetailsRequest property.
     *
     * @return possible object is
     *         {@link GetAuthDetailsRequestType }
     */
    public GetAuthDetailsRequestType getGetAuthDetailsRequest()
    {
        return getAuthDetailsRequest;
    }

    /**
     * Sets the value of the getAuthDetailsRequest property.
     *
     * @param value allowed object is
     *              {@link GetAuthDetailsRequestType }
     */
    public void setGetAuthDetailsRequest(GetAuthDetailsRequestType value)
    {
        this.getAuthDetailsRequest = value;
    }

}
