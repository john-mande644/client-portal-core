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
 *         &lt;element ref="{urn:ebay:api:PayPalAPI}BMUpdateButtonRequest"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "bmUpdateButtonRequest"
})
@XmlRootElement(name = "BMUpdateButtonReq")
public class BMUpdateButtonReq
{

    @XmlElement(name = "BMUpdateButtonRequest", required = true)
    protected BMUpdateButtonRequestType bmUpdateButtonRequest;

    /**
     * Gets the value of the bmUpdateButtonRequest property.
     *
     * @return possible object is
     *         {@link BMUpdateButtonRequestType }
     */
    public BMUpdateButtonRequestType getBMUpdateButtonRequest()
    {
        return bmUpdateButtonRequest;
    }

    /**
     * Sets the value of the bmUpdateButtonRequest property.
     *
     * @param value allowed object is
     *              {@link BMUpdateButtonRequestType }
     */
    public void setBMUpdateButtonRequest(BMUpdateButtonRequestType value)
    {
        this.bmUpdateButtonRequest = value;
    }

}
