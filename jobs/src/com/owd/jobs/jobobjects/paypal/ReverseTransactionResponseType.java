package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ReverseTransactionResponseType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="ReverseTransactionResponseType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:ebay:apis:eBLBaseComponents}AbstractResponseType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element ref="{urn:ebay:apis:eBLBaseComponents}ReverseTransactionResponseDetails"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReverseTransactionResponseType", propOrder = {
        "reverseTransactionResponseDetails"
})
public class ReverseTransactionResponseType
        extends AbstractResponseType
{

    @XmlElement(name = "ReverseTransactionResponseDetails", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected ReverseTransactionResponseDetailsType reverseTransactionResponseDetails;

    /**
     * Gets the value of the reverseTransactionResponseDetails property.
     *
     * @return possible object is
     *         {@link ReverseTransactionResponseDetailsType }
     */
    public ReverseTransactionResponseDetailsType getReverseTransactionResponseDetails()
    {
        return reverseTransactionResponseDetails;
    }

    /**
     * Sets the value of the reverseTransactionResponseDetails property.
     *
     * @param value allowed object is
     *              {@link ReverseTransactionResponseDetailsType }
     */
    public void setReverseTransactionResponseDetails(ReverseTransactionResponseDetailsType value)
    {
        this.reverseTransactionResponseDetails = value;
    }

}
