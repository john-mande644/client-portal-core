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
 *         &lt;element ref="{urn:ebay:api:PayPalAPI}DoVoidRequest"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "doVoidRequest"
})
@XmlRootElement(name = "DoVoidReq")
public class DoVoidReq
{

    @XmlElement(name = "DoVoidRequest", required = true)
    protected DoVoidRequestType doVoidRequest;

    /**
     * Gets the value of the doVoidRequest property.
     *
     * @return possible object is
     *         {@link DoVoidRequestType }
     */
    public DoVoidRequestType getDoVoidRequest()
    {
        return doVoidRequest;
    }

    /**
     * Sets the value of the doVoidRequest property.
     *
     * @param value allowed object is
     *              {@link DoVoidRequestType }
     */
    public void setDoVoidRequest(DoVoidRequestType value)
    {
        this.doVoidRequest = value;
    }

}
