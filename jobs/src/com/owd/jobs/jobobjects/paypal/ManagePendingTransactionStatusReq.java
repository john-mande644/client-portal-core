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
 *         &lt;element ref="{urn:ebay:api:PayPalAPI}ManagePendingTransactionStatusRequest"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "managePendingTransactionStatusRequest"
})
@XmlRootElement(name = "ManagePendingTransactionStatusReq")
public class ManagePendingTransactionStatusReq
{

    @XmlElement(name = "ManagePendingTransactionStatusRequest", required = true)
    protected ManagePendingTransactionStatusRequestType managePendingTransactionStatusRequest;

    /**
     * Gets the value of the managePendingTransactionStatusRequest property.
     *
     * @return possible object is
     *         {@link ManagePendingTransactionStatusRequestType }
     */
    public ManagePendingTransactionStatusRequestType getManagePendingTransactionStatusRequest()
    {
        return managePendingTransactionStatusRequest;
    }

    /**
     * Sets the value of the managePendingTransactionStatusRequest property.
     *
     * @param value allowed object is
     *              {@link ManagePendingTransactionStatusRequestType }
     */
    public void setManagePendingTransactionStatusRequest(ManagePendingTransactionStatusRequestType value)
    {
        this.managePendingTransactionStatusRequest = value;
    }

}
