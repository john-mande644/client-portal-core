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
 *         &lt;element ref="{urn:ebay:api:PayPalAPI}BMManageButtonStatusRequest"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "bmManageButtonStatusRequest"
})
@XmlRootElement(name = "BMManageButtonStatusReq")
public class BMManageButtonStatusReq
{

    @XmlElement(name = "BMManageButtonStatusRequest", required = true)
    protected BMManageButtonStatusRequestType bmManageButtonStatusRequest;

    /**
     * Gets the value of the bmManageButtonStatusRequest property.
     *
     * @return possible object is
     *         {@link BMManageButtonStatusRequestType }
     */
    public BMManageButtonStatusRequestType getBMManageButtonStatusRequest()
    {
        return bmManageButtonStatusRequest;
    }

    /**
     * Sets the value of the bmManageButtonStatusRequest property.
     *
     * @param value allowed object is
     *              {@link BMManageButtonStatusRequestType }
     */
    public void setBMManageButtonStatusRequest(BMManageButtonStatusRequestType value)
    {
        this.bmManageButtonStatusRequest = value;
    }

}
