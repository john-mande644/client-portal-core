package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * Details about the seller.
 * <p/>
 * <p/>
 * <p>Java class for SellerDetailsType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="SellerDetailsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="SellerId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SellerUserName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SellerRegistrationDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="PayPalAccountID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SellerDetailsType", namespace = "urn:ebay:apis:eBLBaseComponents", propOrder = {
        "sellerId",
        "sellerUserName",
        "sellerRegistrationDate",
        "payPalAccountID"
})
public class SellerDetailsType
{

    @XmlElement(name = "SellerId", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String sellerId;
    @XmlElement(name = "SellerUserName", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String sellerUserName;
    @XmlElement(name = "SellerRegistrationDate", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected XMLGregorianCalendar sellerRegistrationDate;
    @XmlElement(name = "PayPalAccountID", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String payPalAccountID;

    /**
     * Gets the value of the sellerId property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getSellerId()
    {
        return sellerId;
    }

    /**
     * Sets the value of the sellerId property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setSellerId(String value)
    {
        this.sellerId = value;
    }

    /**
     * Gets the value of the sellerUserName property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getSellerUserName()
    {
        return sellerUserName;
    }

    /**
     * Sets the value of the sellerUserName property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setSellerUserName(String value)
    {
        this.sellerUserName = value;
    }

    /**
     * Gets the value of the sellerRegistrationDate property.
     *
     * @return possible object is
     *         {@link XMLGregorianCalendar }
     */
    public XMLGregorianCalendar getSellerRegistrationDate()
    {
        return sellerRegistrationDate;
    }

    /**
     * Sets the value of the sellerRegistrationDate property.
     *
     * @param value allowed object is
     *              {@link XMLGregorianCalendar }
     */
    public void setSellerRegistrationDate(XMLGregorianCalendar value)
    {
        this.sellerRegistrationDate = value;
    }

    /**
     * Gets the value of the payPalAccountID property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getPayPalAccountID()
    {
        return payPalAccountID;
    }

    /**
     * Sets the value of the payPalAccountID property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setPayPalAccountID(String value)
    {
        this.payPalAccountID = value;
    }

}
