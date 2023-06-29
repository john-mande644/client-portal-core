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
 *         &lt;element ref="{urn:ebay:api:PayPalAPI}BMButtonSearchRequest"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "bmButtonSearchRequest"
})
@XmlRootElement(name = "BMButtonSearchReq")
public class BMButtonSearchReq
{

    @XmlElement(name = "BMButtonSearchRequest", required = true)
    protected BMButtonSearchRequestType bmButtonSearchRequest;

    /**
     * Gets the value of the bmButtonSearchRequest property.
     *
     * @return possible object is
     *         {@link BMButtonSearchRequestType }
     */
    public BMButtonSearchRequestType getBMButtonSearchRequest()
    {
        return bmButtonSearchRequest;
    }

    /**
     * Sets the value of the bmButtonSearchRequest property.
     *
     * @param value allowed object is
     *              {@link BMButtonSearchRequestType }
     */
    public void setBMButtonSearchRequest(BMButtonSearchRequestType value)
    {
        this.bmButtonSearchRequest = value;
    }

}
