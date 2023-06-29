package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * ReceiverInfoType
 * Receiver information.
 * <p/>
 * <p/>
 * <p>Java class for ReceiverInfoType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="ReceiverInfoType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="Business" type="{urn:ebay:apis:eBLBaseComponents}EmailAddressType"/>
 *         &lt;element name="Receiver" type="{urn:ebay:apis:eBLBaseComponents}EmailAddressType"/>
 *         &lt;element name="ReceiverID" type="{urn:ebay:apis:eBLBaseComponents}UserIDType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReceiverInfoType", namespace = "urn:ebay:apis:eBLBaseComponents", propOrder = {
        "business",
        "receiver",
        "receiverID"
})
public class ReceiverInfoType
{

    @XmlElement(name = "Business", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected String business;
    @XmlElement(name = "Receiver", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected String receiver;
    @XmlElement(name = "ReceiverID", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected String receiverID;

    /**
     * Gets the value of the business property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getBusiness()
    {
        return business;
    }

    /**
     * Sets the value of the business property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setBusiness(String value)
    {
        this.business = value;
    }

    /**
     * Gets the value of the receiver property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getReceiver()
    {
        return receiver;
    }

    /**
     * Sets the value of the receiver property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setReceiver(String value)
    {
        this.receiver = value;
    }

    /**
     * Gets the value of the receiverID property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getReceiverID()
    {
        return receiverID;
    }

    /**
     * Sets the value of the receiverID property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setReceiverID(String value)
    {
        this.receiverID = value;
    }

}
