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
 *         &lt;element ref="{urn:ebay:api:PayPalAPI}AddressVerifyRequest"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "addressVerifyRequest"
})
@XmlRootElement(name = "AddressVerifyReq")
public class AddressVerifyReq
{

    @XmlElement(name = "AddressVerifyRequest", required = true)
    protected AddressVerifyRequestType addressVerifyRequest;

    /**
     * Gets the value of the addressVerifyRequest property.
     *
     * @return possible object is
     *         {@link AddressVerifyRequestType }
     */
    public AddressVerifyRequestType getAddressVerifyRequest()
    {
        return addressVerifyRequest;
    }

    /**
     * Sets the value of the addressVerifyRequest property.
     *
     * @param value allowed object is
     *              {@link AddressVerifyRequestType }
     */
    public void setAddressVerifyRequest(AddressVerifyRequestType value)
    {
        this.addressVerifyRequest = value;
    }

}
