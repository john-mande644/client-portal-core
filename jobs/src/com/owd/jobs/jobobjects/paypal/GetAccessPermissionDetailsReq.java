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
 *         &lt;element ref="{urn:ebay:api:PayPalAPI}GetAccessPermissionDetailsRequest"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "getAccessPermissionDetailsRequest"
})
@XmlRootElement(name = "GetAccessPermissionDetailsReq")
public class GetAccessPermissionDetailsReq
{

    @XmlElement(name = "GetAccessPermissionDetailsRequest", required = true)
    protected GetAccessPermissionDetailsRequestType getAccessPermissionDetailsRequest;

    /**
     * Gets the value of the getAccessPermissionDetailsRequest property.
     *
     * @return possible object is
     *         {@link GetAccessPermissionDetailsRequestType }
     */
    public GetAccessPermissionDetailsRequestType getGetAccessPermissionDetailsRequest()
    {
        return getAccessPermissionDetailsRequest;
    }

    /**
     * Sets the value of the getAccessPermissionDetailsRequest property.
     *
     * @param value allowed object is
     *              {@link GetAccessPermissionDetailsRequestType }
     */
    public void setGetAccessPermissionDetailsRequest(GetAccessPermissionDetailsRequestType value)
    {
        this.getAccessPermissionDetailsRequest = value;
    }

}
