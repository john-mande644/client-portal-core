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
 *         &lt;element ref="{urn:ebay:api:PayPalAPI}BillUserRequest"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "billUserRequest"
})
@XmlRootElement(name = "BillUserReq")
public class BillUserReq
{

    @XmlElement(name = "BillUserRequest", required = true)
    protected BillUserRequestType billUserRequest;

    /**
     * Gets the value of the billUserRequest property.
     *
     * @return possible object is
     *         {@link BillUserRequestType }
     */
    public BillUserRequestType getBillUserRequest()
    {
        return billUserRequest;
    }

    /**
     * Sets the value of the billUserRequest property.
     *
     * @param value allowed object is
     *              {@link BillUserRequestType }
     */
    public void setBillUserRequest(BillUserRequestType value)
    {
        this.billUserRequest = value;
    }

}
