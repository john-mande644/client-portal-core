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
 *         &lt;element ref="{urn:ebay:api:PayPalAPI}GetBoardingDetailsRequest"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "getBoardingDetailsRequest"
})
@XmlRootElement(name = "GetBoardingDetailsReq")
public class GetBoardingDetailsReq
{

    @XmlElement(name = "GetBoardingDetailsRequest", required = true)
    protected GetBoardingDetailsRequestType getBoardingDetailsRequest;

    /**
     * Gets the value of the getBoardingDetailsRequest property.
     *
     * @return possible object is
     *         {@link GetBoardingDetailsRequestType }
     */
    public GetBoardingDetailsRequestType getGetBoardingDetailsRequest()
    {
        return getBoardingDetailsRequest;
    }

    /**
     * Sets the value of the getBoardingDetailsRequest property.
     *
     * @param value allowed object is
     *              {@link GetBoardingDetailsRequestType }
     */
    public void setGetBoardingDetailsRequest(GetBoardingDetailsRequestType value)
    {
        this.getBoardingDetailsRequest = value;
    }

}
