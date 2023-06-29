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
 *         &lt;element ref="{urn:ebay:api:PayPalAPI}DoNonReferencedCreditRequest"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "doNonReferencedCreditRequest"
})
@XmlRootElement(name = "DoNonReferencedCreditReq")
public class DoNonReferencedCreditReq
{

    @XmlElement(name = "DoNonReferencedCreditRequest", required = true)
    protected DoNonReferencedCreditRequestType doNonReferencedCreditRequest;

    /**
     * Gets the value of the doNonReferencedCreditRequest property.
     *
     * @return possible object is
     *         {@link DoNonReferencedCreditRequestType }
     */
    public DoNonReferencedCreditRequestType getDoNonReferencedCreditRequest()
    {
        return doNonReferencedCreditRequest;
    }

    /**
     * Sets the value of the doNonReferencedCreditRequest property.
     *
     * @param value allowed object is
     *              {@link DoNonReferencedCreditRequestType }
     */
    public void setDoNonReferencedCreditRequest(DoNonReferencedCreditRequestType value)
    {
        this.doNonReferencedCreditRequest = value;
    }

}
