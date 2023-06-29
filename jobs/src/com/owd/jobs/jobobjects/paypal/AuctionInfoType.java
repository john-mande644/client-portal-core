package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * AuctionInfoType
 * Basic information about an auction.
 * <p/>
 * <p/>
 * <p>Java class for AuctionInfoType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="AuctionInfoType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="BuyerID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ClosingDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="multiItem" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AuctionInfoType", namespace = "urn:ebay:apis:eBLBaseComponents", propOrder = {
        "buyerID",
        "closingDate"
})
public class AuctionInfoType
{

    @XmlElement(name = "BuyerID", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String buyerID;
    @XmlElement(name = "ClosingDate", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected XMLGregorianCalendar closingDate;
    @XmlAttribute(required = true)
    protected String multiItem;

    /**
     * Gets the value of the buyerID property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getBuyerID()
    {
        return buyerID;
    }

    /**
     * Sets the value of the buyerID property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setBuyerID(String value)
    {
        this.buyerID = value;
    }

    /**
     * Gets the value of the closingDate property.
     *
     * @return possible object is
     *         {@link XMLGregorianCalendar }
     */
    public XMLGregorianCalendar getClosingDate()
    {
        return closingDate;
    }

    /**
     * Sets the value of the closingDate property.
     *
     * @param value allowed object is
     *              {@link XMLGregorianCalendar }
     */
    public void setClosingDate(XMLGregorianCalendar value)
    {
        this.closingDate = value;
    }

    /**
     * Gets the value of the multiItem property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getMultiItem()
    {
        return multiItem;
    }

    /**
     * Sets the value of the multiItem property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setMultiItem(String value)
    {
        this.multiItem = value;
    }

}
