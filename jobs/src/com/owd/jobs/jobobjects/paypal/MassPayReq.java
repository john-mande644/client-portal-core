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
 *         &lt;element ref="{urn:ebay:api:PayPalAPI}MassPayRequest"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "massPayRequest"
})
@XmlRootElement(name = "MassPayReq")
public class MassPayReq
{

    @XmlElement(name = "MassPayRequest", required = true)
    protected MassPayRequestType massPayRequest;

    /**
     * Gets the value of the massPayRequest property.
     *
     * @return possible object is
     *         {@link MassPayRequestType }
     */
    public MassPayRequestType getMassPayRequest()
    {
        return massPayRequest;
    }

    /**
     * Sets the value of the massPayRequest property.
     *
     * @param value allowed object is
     *              {@link MassPayRequestType }
     */
    public void setMassPayRequest(MassPayRequestType value)
    {
        this.massPayRequest = value;
    }

}
