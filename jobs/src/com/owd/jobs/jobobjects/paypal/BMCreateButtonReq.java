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
 *         &lt;element ref="{urn:ebay:api:PayPalAPI}BMCreateButtonRequest"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "bmCreateButtonRequest"
})
@XmlRootElement(name = "BMCreateButtonReq")
public class BMCreateButtonReq
{

    @XmlElement(name = "BMCreateButtonRequest", required = true)
    protected BMCreateButtonRequestType bmCreateButtonRequest;

    /**
     * Gets the value of the bmCreateButtonRequest property.
     *
     * @return possible object is
     *         {@link BMCreateButtonRequestType }
     */
    public BMCreateButtonRequestType getBMCreateButtonRequest()
    {
        return bmCreateButtonRequest;
    }

    /**
     * Sets the value of the bmCreateButtonRequest property.
     *
     * @param value allowed object is
     *              {@link BMCreateButtonRequestType }
     */
    public void setBMCreateButtonRequest(BMCreateButtonRequestType value)
    {
        this.bmCreateButtonRequest = value;
    }

}
