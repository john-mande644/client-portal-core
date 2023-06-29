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
 *         &lt;element ref="{urn:ebay:api:PayPalAPI}BMGetButtonDetailsRequest"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "bmGetButtonDetailsRequest"
})
@XmlRootElement(name = "BMGetButtonDetailsReq")
public class BMGetButtonDetailsReq
{

    @XmlElement(name = "BMGetButtonDetailsRequest", required = true)
    protected BMGetButtonDetailsRequestType bmGetButtonDetailsRequest;

    /**
     * Gets the value of the bmGetButtonDetailsRequest property.
     *
     * @return possible object is
     *         {@link BMGetButtonDetailsRequestType }
     */
    public BMGetButtonDetailsRequestType getBMGetButtonDetailsRequest()
    {
        return bmGetButtonDetailsRequest;
    }

    /**
     * Sets the value of the bmGetButtonDetailsRequest property.
     *
     * @param value allowed object is
     *              {@link BMGetButtonDetailsRequestType }
     */
    public void setBMGetButtonDetailsRequest(BMGetButtonDetailsRequestType value)
    {
        this.bmGetButtonDetailsRequest = value;
    }

}
