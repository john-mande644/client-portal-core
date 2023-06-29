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
 *         &lt;element ref="{urn:ebay:api:PayPalAPI}EnterBoardingRequest"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "enterBoardingRequest"
})
@XmlRootElement(name = "EnterBoardingReq")
public class EnterBoardingReq
{

    @XmlElement(name = "EnterBoardingRequest", required = true)
    protected EnterBoardingRequestType enterBoardingRequest;

    /**
     * Gets the value of the enterBoardingRequest property.
     *
     * @return possible object is
     *         {@link EnterBoardingRequestType }
     */
    public EnterBoardingRequestType getEnterBoardingRequest()
    {
        return enterBoardingRequest;
    }

    /**
     * Sets the value of the enterBoardingRequest property.
     *
     * @param value allowed object is
     *              {@link EnterBoardingRequestType }
     */
    public void setEnterBoardingRequest(EnterBoardingRequestType value)
    {
        this.enterBoardingRequest = value;
    }

}
