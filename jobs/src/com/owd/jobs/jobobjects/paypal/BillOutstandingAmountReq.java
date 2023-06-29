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
 *         &lt;element ref="{urn:ebay:api:PayPalAPI}BillOutstandingAmountRequest"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "billOutstandingAmountRequest"
})
@XmlRootElement(name = "BillOutstandingAmountReq")
public class BillOutstandingAmountReq
{

    @XmlElement(name = "BillOutstandingAmountRequest", required = true)
    protected BillOutstandingAmountRequestType billOutstandingAmountRequest;

    /**
     * Gets the value of the billOutstandingAmountRequest property.
     *
     * @return possible object is
     *         {@link BillOutstandingAmountRequestType }
     */
    public BillOutstandingAmountRequestType getBillOutstandingAmountRequest()
    {
        return billOutstandingAmountRequest;
    }

    /**
     * Sets the value of the billOutstandingAmountRequest property.
     *
     * @param value allowed object is
     *              {@link BillOutstandingAmountRequestType }
     */
    public void setBillOutstandingAmountRequest(BillOutstandingAmountRequestType value)
    {
        this.billOutstandingAmountRequest = value;
    }

}
