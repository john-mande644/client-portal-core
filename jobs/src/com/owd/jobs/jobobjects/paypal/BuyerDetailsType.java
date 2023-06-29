package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * Details about the buyer's account passed in by the merchant or partner.
 * <pre>
 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;br xmlns="http://www.w3.org/2001/XMLSchema"/&gt;
 * </pre>
 * <p/>
 * Optional.
 * <pre>
 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;br xmlns="http://www.w3.org/2001/XMLSchema"/&gt;
 * </pre>
 * <p/>
 * <p/>
 * <p>Java class for BuyerDetailsType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="BuyerDetailsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="BuyerId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BuyerUserName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BuyerRegistrationDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BuyerDetailsType", namespace = "urn:ebay:apis:eBLBaseComponents", propOrder = {
        "buyerId",
        "buyerUserName",
        "buyerRegistrationDate"
})
public class BuyerDetailsType
{

    @XmlElement(name = "BuyerId", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String buyerId;
    @XmlElement(name = "BuyerUserName", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String buyerUserName;
    @XmlElement(name = "BuyerRegistrationDate", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected XMLGregorianCalendar buyerRegistrationDate;

    /**
     * Gets the value of the buyerId property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getBuyerId()
    {
        return buyerId;
    }

    /**
     * Sets the value of the buyerId property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setBuyerId(String value)
    {
        this.buyerId = value;
    }

    /**
     * Gets the value of the buyerUserName property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getBuyerUserName()
    {
        return buyerUserName;
    }

    /**
     * Sets the value of the buyerUserName property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setBuyerUserName(String value)
    {
        this.buyerUserName = value;
    }

    /**
     * Gets the value of the buyerRegistrationDate property.
     *
     * @return possible object is
     *         {@link XMLGregorianCalendar }
     */
    public XMLGregorianCalendar getBuyerRegistrationDate()
    {
        return buyerRegistrationDate;
    }

    /**
     * Sets the value of the buyerRegistrationDate property.
     *
     * @param value allowed object is
     *              {@link XMLGregorianCalendar }
     */
    public void setBuyerRegistrationDate(XMLGregorianCalendar value)
    {
        this.buyerRegistrationDate = value;
    }

}
