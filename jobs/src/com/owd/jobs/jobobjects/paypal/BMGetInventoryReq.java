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
 *         &lt;element ref="{urn:ebay:api:PayPalAPI}BMGetInventoryRequest"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "bmGetInventoryRequest"
})
@XmlRootElement(name = "BMGetInventoryReq")
public class BMGetInventoryReq
{

    @XmlElement(name = "BMGetInventoryRequest", required = true)
    protected BMGetInventoryRequestType bmGetInventoryRequest;

    /**
     * Gets the value of the bmGetInventoryRequest property.
     *
     * @return possible object is
     *         {@link BMGetInventoryRequestType }
     */
    public BMGetInventoryRequestType getBMGetInventoryRequest()
    {
        return bmGetInventoryRequest;
    }

    /**
     * Sets the value of the bmGetInventoryRequest property.
     *
     * @param value allowed object is
     *              {@link BMGetInventoryRequestType }
     */
    public void setBMGetInventoryRequest(BMGetInventoryRequestType value)
    {
        this.bmGetInventoryRequest = value;
    }

}
