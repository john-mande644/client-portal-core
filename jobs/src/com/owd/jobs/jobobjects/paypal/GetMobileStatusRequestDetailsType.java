package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GetMobileStatusRequestDetailsType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="GetMobileStatusRequestDetailsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="Phone" type="{urn:ebay:apis:eBLBaseComponents}PhoneNumberType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetMobileStatusRequestDetailsType", namespace = "urn:ebay:apis:eBLBaseComponents", propOrder = {
        "phone"
})
public class GetMobileStatusRequestDetailsType
{

    @XmlElement(name = "Phone", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected PhoneNumberType phone;

    /**
     * Gets the value of the phone property.
     *
     * @return possible object is
     *         {@link PhoneNumberType }
     */
    public PhoneNumberType getPhone()
    {
        return phone;
    }

    /**
     * Sets the value of the phone property.
     *
     * @param value allowed object is
     *              {@link PhoneNumberType }
     */
    public void setPhone(PhoneNumberType value)
    {
        this.phone = value;
    }

}
