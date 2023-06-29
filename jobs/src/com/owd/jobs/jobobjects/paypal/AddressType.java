package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AddressType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="AddressType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Street1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Street2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CityName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="StateOrProvince" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element ref="{urn:ebay:apis:eBLBaseComponents}Country" minOccurs="0"/>
 *         &lt;element ref="{urn:ebay:apis:eBLBaseComponents}CountryName" minOccurs="0"/>
 *         &lt;element name="Phone" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PostalCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AddressID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AddressOwner" type="{urn:ebay:apis:eBLBaseComponents}AddressOwnerCodeType" minOccurs="0"/>
 *         &lt;element name="ExternalAddressID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="InternationalName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="InternationalStateAndCity" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="InternationalStreet" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AddressStatus" type="{urn:ebay:apis:eBLBaseComponents}AddressStatusCodeType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AddressType", namespace = "urn:ebay:apis:eBLBaseComponents", propOrder = {
        "name",
        "street1",
        "street2",
        "cityName",
        "stateOrProvince",
        "country",
        "countryName",
        "phone",
        "postalCode",
        "addressID",
        "addressOwner",
        "externalAddressID",
        "internationalName",
        "internationalStateAndCity",
        "internationalStreet",
        "addressStatus"
})
public class AddressType
{

    @XmlElement(name = "Name", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String name;
    @XmlElement(name = "Street1", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String street1;
    @XmlElement(name = "Street2", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String street2;
    @XmlElement(name = "CityName", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String cityName;
    @XmlElement(name = "StateOrProvince", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String stateOrProvince;
    @XmlElement(name = "Country", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected CountryCodeType country;
    @XmlElement(name = "CountryName", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String countryName;
    @XmlElement(name = "Phone", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String phone;
    @XmlElement(name = "PostalCode", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String postalCode;
    @XmlElement(name = "AddressID", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String addressID;
    @XmlElement(name = "AddressOwner", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected AddressOwnerCodeType addressOwner;
    @XmlElement(name = "ExternalAddressID", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String externalAddressID;
    @XmlElement(name = "InternationalName", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String internationalName;
    @XmlElement(name = "InternationalStateAndCity", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String internationalStateAndCity;
    @XmlElement(name = "InternationalStreet", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String internationalStreet;
    @XmlElement(name = "AddressStatus", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected AddressStatusCodeType addressStatus;

    /**
     * Gets the value of the name property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getName()
    {
        return name;
    }

    /**
     * Sets the value of the name property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setName(String value)
    {
        this.name = value;
    }

    /**
     * Gets the value of the street1 property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getStreet1()
    {
        return street1;
    }

    /**
     * Sets the value of the street1 property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setStreet1(String value)
    {
        this.street1 = value;
    }

    /**
     * Gets the value of the street2 property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getStreet2()
    {
        return street2;
    }

    /**
     * Sets the value of the street2 property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setStreet2(String value)
    {
        this.street2 = value;
    }

    /**
     * Gets the value of the cityName property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getCityName()
    {
        return cityName;
    }

    /**
     * Sets the value of the cityName property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setCityName(String value)
    {
        this.cityName = value;
    }

    /**
     * Gets the value of the stateOrProvince property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getStateOrProvince()
    {
        return stateOrProvince;
    }

    /**
     * Sets the value of the stateOrProvince property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setStateOrProvince(String value)
    {
        this.stateOrProvince = value;
    }

    /**
     * ISO 3166 standard country code
     * <p/>
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;br xmlns="http://www.w3.org/2001/XMLSchema"/&gt;
     * </pre>
     * Character limit: Two single-byte characters.
     *
     * @return possible object is
     *         {@link CountryCodeType }
     */
    public CountryCodeType getCountry()
    {
        return country;
    }

    /**
     * ISO 3166 standard country code
     * <p/>
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;br xmlns="http://www.w3.org/2001/XMLSchema"/&gt;
     * </pre>
     * Character limit: Two single-byte characters.
     *
     * @param value allowed object is
     *              {@link CountryCodeType }
     */
    public void setCountry(CountryCodeType value)
    {
        this.country = value;
    }

    /**
     * IMPORTANT: Do not set this element for SetExpressCheckout, DoExpressCheckoutPayment, DoDirectPayment, CreateRecurringPaymentsProfile or UpdateRecurringPaymentsProfile.
     * <p/>
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;br xmlns="http://www.w3.org/2001/XMLSchema"/&gt;
     * </pre>
     * <p/>
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;br xmlns="http://www.w3.org/2001/XMLSchema"/&gt;
     * </pre>
     * <p/>
     * This element should only be used in response elements and typically
     * should not be used in creating request messages which specify the
     * name of a country using the Country element (which refers to a
     * 2-letter country code).
     *
     * @return possible object is
     *         {@link String }
     */
    public String getCountryName()
    {
        return countryName;
    }

    /**
     * IMPORTANT: Do not set this element for SetExpressCheckout, DoExpressCheckoutPayment, DoDirectPayment, CreateRecurringPaymentsProfile or UpdateRecurringPaymentsProfile.
     * <p/>
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;br xmlns="http://www.w3.org/2001/XMLSchema"/&gt;
     * </pre>
     * <p/>
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;br xmlns="http://www.w3.org/2001/XMLSchema"/&gt;
     * </pre>
     * <p/>
     * This element should only be used in response elements and typically
     * should not be used in creating request messages which specify the
     * name of a country using the Country element (which refers to a
     * 2-letter country code).
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setCountryName(String value)
    {
        this.countryName = value;
    }

    /**
     * Gets the value of the phone property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getPhone()
    {
        return phone;
    }

    /**
     * Sets the value of the phone property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setPhone(String value)
    {
        this.phone = value;
    }

    /**
     * Gets the value of the postalCode property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getPostalCode()
    {
        return postalCode;
    }

    /**
     * Sets the value of the postalCode property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setPostalCode(String value)
    {
        this.postalCode = value;
    }

    /**
     * Gets the value of the addressID property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getAddressID()
    {
        return addressID;
    }

    /**
     * Sets the value of the addressID property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setAddressID(String value)
    {
        this.addressID = value;
    }

    /**
     * Gets the value of the addressOwner property.
     *
     * @return possible object is
     *         {@link AddressOwnerCodeType }
     */
    public AddressOwnerCodeType getAddressOwner()
    {
        return addressOwner;
    }

    /**
     * Sets the value of the addressOwner property.
     *
     * @param value allowed object is
     *              {@link AddressOwnerCodeType }
     */
    public void setAddressOwner(AddressOwnerCodeType value)
    {
        this.addressOwner = value;
    }

    /**
     * Gets the value of the externalAddressID property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getExternalAddressID()
    {
        return externalAddressID;
    }

    /**
     * Sets the value of the externalAddressID property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setExternalAddressID(String value)
    {
        this.externalAddressID = value;
    }

    /**
     * Gets the value of the internationalName property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getInternationalName()
    {
        return internationalName;
    }

    /**
     * Sets the value of the internationalName property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setInternationalName(String value)
    {
        this.internationalName = value;
    }

    /**
     * Gets the value of the internationalStateAndCity property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getInternationalStateAndCity()
    {
        return internationalStateAndCity;
    }

    /**
     * Sets the value of the internationalStateAndCity property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setInternationalStateAndCity(String value)
    {
        this.internationalStateAndCity = value;
    }

    /**
     * Gets the value of the internationalStreet property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getInternationalStreet()
    {
        return internationalStreet;
    }

    /**
     * Sets the value of the internationalStreet property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setInternationalStreet(String value)
    {
        this.internationalStreet = value;
    }

    /**
     * Gets the value of the addressStatus property.
     *
     * @return possible object is
     *         {@link AddressStatusCodeType }
     */
    public AddressStatusCodeType getAddressStatus()
    {
        return addressStatus;
    }

    /**
     * Sets the value of the addressStatus property.
     *
     * @param value allowed object is
     *              {@link AddressStatusCodeType }
     */
    public void setAddressStatus(AddressStatusCodeType value)
    {
        this.addressStatus = value;
    }

}
