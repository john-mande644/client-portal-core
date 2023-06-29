package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.*;


/**
 * <p>Java class for ReverseTransactionResponseDetailsType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="ReverseTransactionResponseDetailsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="ReverseTransactionID" type="{urn:ebay:apis:eBLBaseComponents}TransactionId" minOccurs="0"/>
 *         &lt;element name="Status" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReverseTransactionResponseDetailsType", namespace = "urn:ebay:apis:eBLBaseComponents", propOrder = {
        "reverseTransactionID",
        "status"
})
public class ReverseTransactionResponseDetailsType
{

    @XmlElementRef(name = "ReverseTransactionID", namespace = "urn:ebay:apis:eBLBaseComponents", type = JAXBElement.class)
    protected JAXBElement<String> reverseTransactionID;
    @XmlElement(name = "Status", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected String status;

    /**
     * Gets the value of the reverseTransactionID property.
     *
     * @return possible object is
     *         {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    public JAXBElement<String> getReverseTransactionID()
    {
        return reverseTransactionID;
    }

    /**
     * Sets the value of the reverseTransactionID property.
     *
     * @param value allowed object is
     *              {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    public void setReverseTransactionID(JAXBElement<String> value)
    {
        this.reverseTransactionID = ((JAXBElement<String>) value);
    }

    /**
     * Gets the value of the status property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getStatus()
    {
        return status;
    }

    /**
     * Sets the value of the status property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setStatus(String value)
    {
        this.status = value;
    }

}
