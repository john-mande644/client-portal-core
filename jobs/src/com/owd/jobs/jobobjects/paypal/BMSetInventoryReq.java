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
 *         &lt;element ref="{urn:ebay:api:PayPalAPI}BMSetInventoryRequest"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "bmSetInventoryRequest"
})
@XmlRootElement(name = "BMSetInventoryReq")
public class BMSetInventoryReq
{

    @XmlElement(name = "BMSetInventoryRequest", required = true)
    protected BMSetInventoryRequestType bmSetInventoryRequest;

    /**
     * Gets the value of the bmSetInventoryRequest property.
     *
     * @return possible object is
     *         {@link BMSetInventoryRequestType }
     */
    public BMSetInventoryRequestType getBMSetInventoryRequest()
    {
        return bmSetInventoryRequest;
    }

    /**
     * Sets the value of the bmSetInventoryRequest property.
     *
     * @param value allowed object is
     *              {@link BMSetInventoryRequestType }
     */
    public void setBMSetInventoryRequest(BMSetInventoryRequestType value)
    {
        this.bmSetInventoryRequest = value;
    }

}
