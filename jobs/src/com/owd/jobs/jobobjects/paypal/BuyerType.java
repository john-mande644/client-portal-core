package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Information about user used by buying applications
 * <p/>
 * <p/>
 * <p>Java class for BuyerType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="BuyerType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element ref="{urn:ebay:apis:eBLBaseComponents}ShippingAddress" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BuyerType", namespace = "urn:ebay:apis:eBLBaseComponents", propOrder = {
        "shippingAddress"
})
public class BuyerType
{

    @XmlElement(name = "ShippingAddress", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected AddressType shippingAddress;

    /**
     * Gets the value of the shippingAddress property.
     *
     * @return possible object is
     *         {@link AddressType }
     */
    public AddressType getShippingAddress()
    {
        return shippingAddress;
    }

    /**
     * Sets the value of the shippingAddress property.
     *
     * @param value allowed object is
     *              {@link AddressType }
     */
    public void setShippingAddress(AddressType value)
    {
        this.shippingAddress = value;
    }

}
