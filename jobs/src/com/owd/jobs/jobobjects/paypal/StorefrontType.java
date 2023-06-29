package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.*;


/**
 * Contains the eBay Stores-specific item attributes
 * department number and store location. StorefrontInfo
 * is shown for any item that belongs to an eBay Store
 * owner, regardless of whether it is fixed price or
 * auction type. Returned as null for international
 * fixed price items.
 * <p/>
 * <p/>
 * <p>Java class for StorefrontType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="StorefrontType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element ref="{urn:ebay:apis:eBLBaseComponents}StoreCategoryID"/>
 *         &lt;element ref="{urn:ebay:apis:eBLBaseComponents}StoreURL" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StorefrontType", namespace = "urn:ebay:apis:eBLBaseComponents", propOrder = {
        "storeCategoryID",
        "storeURL"
})
public class StorefrontType
{

    @XmlElement(name = "StoreCategoryID", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected int storeCategoryID;
    @XmlElement(name = "StoreURL", namespace = "urn:ebay:apis:eBLBaseComponents")
    @XmlSchemaType(name = "anyURI")
    protected String storeURL;

    /**
     * assumed this type is specific to add/get/revise item, then each StorefrontType nust have category id, for store details this node makes no sense to use
     */
    public int getStoreCategoryID()
    {
        return storeCategoryID;
    }

    /**
     * assumed this type is specific to add/get/revise item, then each StorefrontType nust have category id, for store details this node makes no sense to use
     */
    public void setStoreCategoryID(int value)
    {
        this.storeCategoryID = value;
    }

    /**
     * in case or revise item for example - to change store category (department) you do not need to change store URL, so it will notbe in request
     *
     * @return possible object is
     *         {@link String }
     */
    public String getStoreURL()
    {
        return storeURL;
    }

    /**
     * in case or revise item for example - to change store category (department) you do not need to change store URL, so it will notbe in request
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setStoreURL(String value)
    {
        this.storeURL = value;
    }

}
