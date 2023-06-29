package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * BusinessOwnerInfoType
 * <p/>
 * <p/>
 * <p>Java class for BusinessOwnerInfoType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="BusinessOwnerInfoType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="Owner" type="{urn:ebay:apis:eBLBaseComponents}PayerInfoType" minOccurs="0"/>
 *         &lt;element name="HomePhone" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MobilePhone" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SSN" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BusinessOwnerInfoType", namespace = "urn:ebay:apis:eBLBaseComponents", propOrder = {
        "owner",
        "homePhone",
        "mobilePhone",
        "ssn"
})
public class BusinessOwnerInfoType
{

    @XmlElement(name = "Owner", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected PayerInfoType owner;
    @XmlElement(name = "HomePhone", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String homePhone;
    @XmlElement(name = "MobilePhone", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String mobilePhone;
    @XmlElement(name = "SSN", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String ssn;

    /**
     * Gets the value of the owner property.
     *
     * @return possible object is
     *         {@link PayerInfoType }
     */
    public PayerInfoType getOwner()
    {
        return owner;
    }

    /**
     * Sets the value of the owner property.
     *
     * @param value allowed object is
     *              {@link PayerInfoType }
     */
    public void setOwner(PayerInfoType value)
    {
        this.owner = value;
    }

    /**
     * Gets the value of the homePhone property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getHomePhone()
    {
        return homePhone;
    }

    /**
     * Sets the value of the homePhone property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setHomePhone(String value)
    {
        this.homePhone = value;
    }

    /**
     * Gets the value of the mobilePhone property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getMobilePhone()
    {
        return mobilePhone;
    }

    /**
     * Sets the value of the mobilePhone property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setMobilePhone(String value)
    {
        this.mobilePhone = value;
    }

    /**
     * Gets the value of the ssn property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getSSN()
    {
        return ssn;
    }

    /**
     * Sets the value of the ssn property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setSSN(String value)
    {
        this.ssn = value;
    }

}
