package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * BusinessInfoType
 * <p/>
 * <p/>
 * <p>Java class for BusinessInfoType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="BusinessInfoType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="Type" type="{urn:ebay:apis:eBLBaseComponents}BusinessTypeType" minOccurs="0"/>
 *         &lt;element name="Name" type="{urn:ebay:apis:eBLBaseComponents}NameType" minOccurs="0"/>
 *         &lt;element name="Address" type="{urn:ebay:apis:eBLBaseComponents}AddressType" minOccurs="0"/>
 *         &lt;element name="WorkPhone" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Category" type="{urn:ebay:apis:eBLBaseComponents}BusinessCategoryType" minOccurs="0"/>
 *         &lt;element name="SubCategory" type="{urn:ebay:apis:eBLBaseComponents}BusinessSubCategoryType" minOccurs="0"/>
 *         &lt;element name="AveragePrice" type="{urn:ebay:apis:eBLBaseComponents}AverageTransactionPriceType" minOccurs="0"/>
 *         &lt;element name="AverageMonthlyVolume" type="{urn:ebay:apis:eBLBaseComponents}AverageMonthlyVolumeType" minOccurs="0"/>
 *         &lt;element name="SalesVenue" type="{urn:ebay:apis:eBLBaseComponents}SalesVenueType" minOccurs="0"/>
 *         &lt;element name="Website" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RevenueFromOnlineSales" type="{urn:ebay:apis:eBLBaseComponents}PercentageRevenueFromOnlineSalesType" minOccurs="0"/>
 *         &lt;element name="BusinessEstablished" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="CustomerServiceEmail" type="{urn:ebay:apis:eBLBaseComponents}EmailAddressType" minOccurs="0"/>
 *         &lt;element name="CustomerServicePhone" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BusinessInfoType", namespace = "urn:ebay:apis:eBLBaseComponents", propOrder = {
        "type",
        "name",
        "address",
        "workPhone",
        "category",
        "subCategory",
        "averagePrice",
        "averageMonthlyVolume",
        "salesVenue",
        "website",
        "revenueFromOnlineSales",
        "businessEstablished",
        "customerServiceEmail",
        "customerServicePhone"
})
public class BusinessInfoType
{

    @XmlElement(name = "Type", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected BusinessTypeType type;
    @XmlElement(name = "Name", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String name;
    @XmlElement(name = "Address", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected AddressType address;
    @XmlElement(name = "WorkPhone", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String workPhone;
    @XmlElement(name = "Category", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected BusinessCategoryType category;
    @XmlElement(name = "SubCategory", namespace = "urn:ebay:apis:eBLBaseComponents")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String subCategory;
    @XmlElement(name = "AveragePrice", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected AverageTransactionPriceType averagePrice;
    @XmlElement(name = "AverageMonthlyVolume", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected AverageMonthlyVolumeType averageMonthlyVolume;
    @XmlElement(name = "SalesVenue", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected SalesVenueType salesVenue;
    @XmlElement(name = "Website", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String website;
    @XmlElement(name = "RevenueFromOnlineSales", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected PercentageRevenueFromOnlineSalesType revenueFromOnlineSales;
    @XmlElement(name = "BusinessEstablished", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected XMLGregorianCalendar businessEstablished;
    @XmlElement(name = "CustomerServiceEmail", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String customerServiceEmail;
    @XmlElement(name = "CustomerServicePhone", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String customerServicePhone;

    /**
     * Gets the value of the type property.
     *
     * @return possible object is
     *         {@link BusinessTypeType }
     */
    public BusinessTypeType getType()
    {
        return type;
    }

    /**
     * Sets the value of the type property.
     *
     * @param value allowed object is
     *              {@link BusinessTypeType }
     */
    public void setType(BusinessTypeType value)
    {
        this.type = value;
    }

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
     * Gets the value of the workPhone property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getWorkPhone()
    {
        return workPhone;
    }

    /**
     * Sets the value of the workPhone property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setWorkPhone(String value)
    {
        this.workPhone = value;
    }

    /**
     * Gets the value of the category property.
     *
     * @return possible object is
     *         {@link BusinessCategoryType }
     */
    public BusinessCategoryType getCategory()
    {
        return category;
    }

    /**
     * Sets the value of the category property.
     *
     * @param value allowed object is
     *              {@link BusinessCategoryType }
     */
    public void setCategory(BusinessCategoryType value)
    {
        this.category = value;
    }

    /**
     * Gets the value of the subCategory property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getSubCategory()
    {
        return subCategory;
    }

    /**
     * Sets the value of the subCategory property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setSubCategory(String value)
    {
        this.subCategory = value;
    }

    /**
     * Gets the value of the averagePrice property.
     *
     * @return possible object is
     *         {@link AverageTransactionPriceType }
     */
    public AverageTransactionPriceType getAveragePrice()
    {
        return averagePrice;
    }

    /**
     * Sets the value of the averagePrice property.
     *
     * @param value allowed object is
     *              {@link AverageTransactionPriceType }
     */
    public void setAveragePrice(AverageTransactionPriceType value)
    {
        this.averagePrice = value;
    }

    /**
     * Gets the value of the averageMonthlyVolume property.
     *
     * @return possible object is
     *         {@link AverageMonthlyVolumeType }
     */
    public AverageMonthlyVolumeType getAverageMonthlyVolume()
    {
        return averageMonthlyVolume;
    }

    /**
     * Sets the value of the averageMonthlyVolume property.
     *
     * @param value allowed object is
     *              {@link AverageMonthlyVolumeType }
     */
    public void setAverageMonthlyVolume(AverageMonthlyVolumeType value)
    {
        this.averageMonthlyVolume = value;
    }

    /**
     * Gets the value of the salesVenue property.
     *
     * @return possible object is
     *         {@link SalesVenueType }
     */
    public SalesVenueType getSalesVenue()
    {
        return salesVenue;
    }

    /**
     * Sets the value of the salesVenue property.
     *
     * @param value allowed object is
     *              {@link SalesVenueType }
     */
    public void setSalesVenue(SalesVenueType value)
    {
        this.salesVenue = value;
    }

    /**
     * Gets the value of the website property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getWebsite()
    {
        return website;
    }

    /**
     * Sets the value of the website property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setWebsite(String value)
    {
        this.website = value;
    }

    /**
     * Gets the value of the revenueFromOnlineSales property.
     *
     * @return possible object is
     *         {@link PercentageRevenueFromOnlineSalesType }
     */
    public PercentageRevenueFromOnlineSalesType getRevenueFromOnlineSales()
    {
        return revenueFromOnlineSales;
    }

    /**
     * Sets the value of the revenueFromOnlineSales property.
     *
     * @param value allowed object is
     *              {@link PercentageRevenueFromOnlineSalesType }
     */
    public void setRevenueFromOnlineSales(PercentageRevenueFromOnlineSalesType value)
    {
        this.revenueFromOnlineSales = value;
    }

    /**
     * Gets the value of the businessEstablished property.
     *
     * @return possible object is
     *         {@link XMLGregorianCalendar }
     */
    public XMLGregorianCalendar getBusinessEstablished()
    {
        return businessEstablished;
    }

    /**
     * Sets the value of the businessEstablished property.
     *
     * @param value allowed object is
     *              {@link XMLGregorianCalendar }
     */
    public void setBusinessEstablished(XMLGregorianCalendar value)
    {
        this.businessEstablished = value;
    }

    /**
     * Gets the value of the customerServiceEmail property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getCustomerServiceEmail()
    {
        return customerServiceEmail;
    }

    /**
     * Sets the value of the customerServiceEmail property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setCustomerServiceEmail(String value)
    {
        this.customerServiceEmail = value;
    }

    /**
     * Gets the value of the customerServicePhone property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getCustomerServicePhone()
    {
        return customerServicePhone;
    }

    /**
     * Sets the value of the customerServicePhone property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setCustomerServicePhone(String value)
    {
        this.customerServicePhone = value;
    }

}
