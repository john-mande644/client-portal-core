package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Defines which bucket or item that the incentive should be applied to.
 * <p/>
 * <p/>
 * <p>Java class for IncentiveApplyIndicationType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="IncentiveApplyIndicationType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="PaymentRequestID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ItemId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IncentiveApplyIndicationType", namespace = "urn:ebay:apis:eBLBaseComponents", propOrder = {
        "paymentRequestID",
        "itemId"
})
public class IncentiveApplyIndicationType
{

    @XmlElement(name = "PaymentRequestID", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String paymentRequestID;
    @XmlElement(name = "ItemId", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String itemId;

    /**
     * Gets the value of the paymentRequestID property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getPaymentRequestID()
    {
        return paymentRequestID;
    }

    /**
     * Sets the value of the paymentRequestID property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setPaymentRequestID(String value)
    {
        this.paymentRequestID = value;
    }

    /**
     * Gets the value of the itemId property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getItemId()
    {
        return itemId;
    }

    /**
     * Sets the value of the itemId property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setItemId(String value)
    {
        this.itemId = value;
    }

}
