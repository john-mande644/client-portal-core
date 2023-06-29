package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * Container for data on the primary category of listing.
 * <p/>
 * <p/>
 * <p>Java class for CategoryType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="CategoryType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="AutoPayEnabled" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="B2BVATEnabled" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="CatalogEnabled" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="CategoryID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CategoryLevel" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="CategoryName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CategoryParentID" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="CategoryParentName" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="CSIDList" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="Expired" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="IntlAutosFixedCat" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="LeafCategory" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="Virtual" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CategoryType", namespace = "urn:ebay:apis:eBLBaseComponents", propOrder = {
        "autoPayEnabled",
        "b2BVATEnabled",
        "catalogEnabled",
        "categoryID",
        "categoryLevel",
        "categoryName",
        "categoryParentID",
        "categoryParentName",
        "csidList",
        "expired",
        "intlAutosFixedCat",
        "leafCategory",
        "virtual"
})
public class CategoryType
{

    @XmlElement(name = "AutoPayEnabled", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected Boolean autoPayEnabled;
    @XmlElement(name = "B2BVATEnabled", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected Boolean b2BVATEnabled;
    @XmlElement(name = "CatalogEnabled", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected Boolean catalogEnabled;
    @XmlElement(name = "CategoryID", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected String categoryID;
    @XmlElement(name = "CategoryLevel", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected Integer categoryLevel;
    @XmlElement(name = "CategoryName", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String categoryName;
    @XmlElement(name = "CategoryParentID", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected List<String> categoryParentID;
    @XmlElement(name = "CategoryParentName", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected List<String> categoryParentName;
    @XmlElement(name = "CSIDList", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected List<String> csidList;
    @XmlElement(name = "Expired", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected Boolean expired;
    @XmlElement(name = "IntlAutosFixedCat", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected Boolean intlAutosFixedCat;
    @XmlElement(name = "LeafCategory", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected Boolean leafCategory;
    @XmlElement(name = "Virtual", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected Boolean virtual;

    /**
     * Gets the value of the autoPayEnabled property.
     *
     * @return possible object is
     *         {@link Boolean }
     */
    public Boolean isAutoPayEnabled()
    {
        return autoPayEnabled;
    }

    /**
     * Sets the value of the autoPayEnabled property.
     *
     * @param value allowed object is
     *              {@link Boolean }
     */
    public void setAutoPayEnabled(Boolean value)
    {
        this.autoPayEnabled = value;
    }

    /**
     * Gets the value of the b2BVATEnabled property.
     *
     * @return possible object is
     *         {@link Boolean }
     */
    public Boolean isB2BVATEnabled()
    {
        return b2BVATEnabled;
    }

    /**
     * Sets the value of the b2BVATEnabled property.
     *
     * @param value allowed object is
     *              {@link Boolean }
     */
    public void setB2BVATEnabled(Boolean value)
    {
        this.b2BVATEnabled = value;
    }

    /**
     * Gets the value of the catalogEnabled property.
     *
     * @return possible object is
     *         {@link Boolean }
     */
    public Boolean isCatalogEnabled()
    {
        return catalogEnabled;
    }

    /**
     * Sets the value of the catalogEnabled property.
     *
     * @param value allowed object is
     *              {@link Boolean }
     */
    public void setCatalogEnabled(Boolean value)
    {
        this.catalogEnabled = value;
    }

    /**
     * Gets the value of the categoryID property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getCategoryID()
    {
        return categoryID;
    }

    /**
     * Sets the value of the categoryID property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setCategoryID(String value)
    {
        this.categoryID = value;
    }

    /**
     * Gets the value of the categoryLevel property.
     *
     * @return possible object is
     *         {@link Integer }
     */
    public Integer getCategoryLevel()
    {
        return categoryLevel;
    }

    /**
     * Sets the value of the categoryLevel property.
     *
     * @param value allowed object is
     *              {@link Integer }
     */
    public void setCategoryLevel(Integer value)
    {
        this.categoryLevel = value;
    }

    /**
     * Gets the value of the categoryName property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getCategoryName()
    {
        return categoryName;
    }

    /**
     * Sets the value of the categoryName property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setCategoryName(String value)
    {
        this.categoryName = value;
    }

    /**
     * Gets the value of the categoryParentID property.
     * <p/>
     * <p/>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the categoryParentID property.
     * <p/>
     * <p/>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCategoryParentID().add(newItem);
     * </pre>
     * <p/>
     * <p/>
     * <p/>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     */
    public List<String> getCategoryParentID()
    {
        if (categoryParentID == null)
        {
            categoryParentID = new ArrayList<String>();
        }
        return this.categoryParentID;
    }

    /**
     * Gets the value of the categoryParentName property.
     * <p/>
     * <p/>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the categoryParentName property.
     * <p/>
     * <p/>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCategoryParentName().add(newItem);
     * </pre>
     * <p/>
     * <p/>
     * <p/>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     */
    public List<String> getCategoryParentName()
    {
        if (categoryParentName == null)
        {
            categoryParentName = new ArrayList<String>();
        }
        return this.categoryParentName;
    }

    /**
     * Gets the value of the csidList property.
     * <p/>
     * <p/>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the csidList property.
     * <p/>
     * <p/>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCSIDList().add(newItem);
     * </pre>
     * <p/>
     * <p/>
     * <p/>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     */
    public List<String> getCSIDList()
    {
        if (csidList == null)
        {
            csidList = new ArrayList<String>();
        }
        return this.csidList;
    }

    /**
     * Gets the value of the expired property.
     *
     * @return possible object is
     *         {@link Boolean }
     */
    public Boolean isExpired()
    {
        return expired;
    }

    /**
     * Sets the value of the expired property.
     *
     * @param value allowed object is
     *              {@link Boolean }
     */
    public void setExpired(Boolean value)
    {
        this.expired = value;
    }

    /**
     * Gets the value of the intlAutosFixedCat property.
     *
     * @return possible object is
     *         {@link Boolean }
     */
    public Boolean isIntlAutosFixedCat()
    {
        return intlAutosFixedCat;
    }

    /**
     * Sets the value of the intlAutosFixedCat property.
     *
     * @param value allowed object is
     *              {@link Boolean }
     */
    public void setIntlAutosFixedCat(Boolean value)
    {
        this.intlAutosFixedCat = value;
    }

    /**
     * Gets the value of the leafCategory property.
     *
     * @return possible object is
     *         {@link Boolean }
     */
    public Boolean isLeafCategory()
    {
        return leafCategory;
    }

    /**
     * Sets the value of the leafCategory property.
     *
     * @param value allowed object is
     *              {@link Boolean }
     */
    public void setLeafCategory(Boolean value)
    {
        this.leafCategory = value;
    }

    /**
     * Gets the value of the virtual property.
     *
     * @return possible object is
     *         {@link Boolean }
     */
    public Boolean isVirtual()
    {
        return virtual;
    }

    /**
     * Sets the value of the virtual property.
     *
     * @param value allowed object is
     *              {@link Boolean }
     */
    public void setVirtual(Boolean value)
    {
        this.virtual = value;
    }

}
