package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * PayerInfoType
 * Payer information
 * <p/>
 * <p/>
 * <p>Java class for PayerInfoType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="PayerInfoType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="Payer" type="{urn:ebay:apis:eBLBaseComponents}EmailAddressType" minOccurs="0"/>
 *         &lt;element name="PayerID" type="{urn:ebay:apis:eBLBaseComponents}UserIDType" minOccurs="0"/>
 *         &lt;element name="PayerStatus" type="{urn:ebay:apis:eBLBaseComponents}PayPalUserStatusCodeType" minOccurs="0"/>
 *         &lt;element name="PayerName" type="{urn:ebay:apis:eBLBaseComponents}PersonNameType"/>
 *         &lt;element name="PayerCountry" type="{urn:ebay:apis:eBLBaseComponents}CountryCodeType" minOccurs="0"/>
 *         &lt;element name="PayerBusiness" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Address" type="{urn:ebay:apis:eBLBaseComponents}AddressType" minOccurs="0"/>
 *         &lt;element name="ContactPhone" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PayerInfoType", namespace = "urn:ebay:apis:eBLBaseComponents", propOrder = {
        "payer",
        "payerID",
        "payerStatus",
        "payerName",
        "payerCountry",
        "payerBusiness",
        "address",
        "contactPhone"
})
public class PayerInfoType
{

    @XmlElement(name = "Payer", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String payer;
    @XmlElement(name = "PayerID", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String payerID;
    @XmlElement(name = "PayerStatus", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected PayPalUserStatusCodeType payerStatus;
    @XmlElement(name = "PayerName", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected PersonNameType payerName;
    @XmlElement(name = "PayerCountry", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected CountryCodeType payerCountry;
    @XmlElement(name = "PayerBusiness", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String payerBusiness;
    @XmlElement(name = "Address", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected AddressType address;
    @XmlElement(name = "ContactPhone", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String contactPhone;

    /**
     * Gets the value of the payer property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getPayer()
    {
        return payer;
    }

    /**
     * Sets the value of the payer property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setPayer(String value)
    {
        this.payer = value;
    }

    /**
     * Gets the value of the payerID property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getPayerID()
    {
        return payerID;
    }

    /**
     * Sets the value of the payerID property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setPayerID(String value)
    {
        this.payerID = value;
    }

    /**
     * Gets the value of the payerStatus property.
     *
     * @return possible object is
     *         {@link PayPalUserStatusCodeType }
     */
    public PayPalUserStatusCodeType getPayerStatus()
    {
        return payerStatus;
    }

    /**
     * Sets the value of the payerStatus property.
     *
     * @param value allowed object is
     *              {@link PayPalUserStatusCodeType }
     */
    public void setPayerStatus(PayPalUserStatusCodeType value)
    {
        this.payerStatus = value;
    }

    /**
     * Gets the value of the payerName property.
     *
     * @return possible object is
     *         {@link PersonNameType }
     */
    public PersonNameType getPayerName()
    {
        return payerName;
    }

    /**
     * Sets the value of the payerName property.
     *
     * @param value allowed object is
     *              {@link PersonNameType }
     */
    public void setPayerName(PersonNameType value)
    {
        this.payerName = value;
    }

    /**
     * Gets the value of the payerCountry property.
     *
     * @return possible object is
     *         {@link CountryCodeType }
     */
    public CountryCodeType getPayerCountry()
    {
        return payerCountry;
    }

    /**
     * Sets the value of the payerCountry property.
     *
     * @param value allowed object is
     *              {@link CountryCodeType }
     */
    public void setPayerCountry(CountryCodeType value)
    {
        this.payerCountry = value;
    }

    /**
     * Gets the value of the payerBusiness property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getPayerBusiness()
    {
        return payerBusiness;
    }

    /**
     * Sets the value of the payerBusiness property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setPayerBusiness(String value)
    {
        this.payerBusiness = value;
    }

    /**
     * Gets the value of the address property.
     *
     * @return possible object is
     *         {@link AddressType }
     */
    public AddressType getAddress()
    {
        return address;
    }

    /**
     * Sets the value of the address property.
     *
     * @param value allowed object is
     *              {@link AddressType }
     */
    public void setAddress(AddressType value)
    {
        this.address = value;
    }

    /**
     * Gets the value of the contactPhone property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getContactPhone()
    {
        return contactPhone;
    }

    /**
     * Sets the value of the contactPhone property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setContactPhone(String value)
    {
        this.contactPhone = value;
    }

}
