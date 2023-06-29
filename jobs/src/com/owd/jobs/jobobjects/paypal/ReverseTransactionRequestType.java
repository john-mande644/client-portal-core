package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ReverseTransactionRequestType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="ReverseTransactionRequestType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:ebay:apis:eBLBaseComponents}AbstractRequestType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element ref="{urn:ebay:apis:eBLBaseComponents}ReverseTransactionRequestDetails"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReverseTransactionRequestType", propOrder = {
        "reverseTransactionRequestDetails"
})
public class ReverseTransactionRequestType
        extends AbstractRequestType
{

    @XmlElement(name = "ReverseTransactionRequestDetails", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected ReverseTransactionRequestDetailsType reverseTransactionRequestDetails;

    /**
     * Gets the value of the reverseTransactionRequestDetails property.
     *
     * @return possible object is
     *         {@link ReverseTransactionRequestDetailsType }
     */
    public ReverseTransactionRequestDetailsType getReverseTransactionRequestDetails()
    {
        return reverseTransactionRequestDetails;
    }

    /**
     * Sets the value of the reverseTransactionRequestDetails property.
     *
     * @param value allowed object is
     *              {@link ReverseTransactionRequestDetailsType }
     */
    public void setReverseTransactionRequestDetails(ReverseTransactionRequestDetailsType value)
    {
        this.reverseTransactionRequestDetails = value;
    }

}
