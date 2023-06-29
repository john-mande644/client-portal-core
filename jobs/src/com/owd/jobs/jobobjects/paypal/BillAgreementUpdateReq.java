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
 *         &lt;element ref="{urn:ebay:api:PayPalAPI}BAUpdateRequest"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "baUpdateRequest"
})
@XmlRootElement(name = "BillAgreementUpdateReq")
public class BillAgreementUpdateReq
{

    @XmlElement(name = "BAUpdateRequest", required = true)
    protected BAUpdateRequestType baUpdateRequest;

    /**
     * Gets the value of the baUpdateRequest property.
     *
     * @return possible object is
     *         {@link BAUpdateRequestType }
     */
    public BAUpdateRequestType getBAUpdateRequest()
    {
        return baUpdateRequest;
    }

    /**
     * Sets the value of the baUpdateRequest property.
     *
     * @param value allowed object is
     *              {@link BAUpdateRequestType }
     */
    public void setBAUpdateRequest(BAUpdateRequestType value)
    {
        this.baUpdateRequest = value;
    }

}
