package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Details of Risk Filter.
 * <p/>
 * <p/>
 * <p>Java class for RiskFilterDetailsType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="RiskFilterDetailsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="Id" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RiskFilterDetailsType", namespace = "urn:ebay:apis:eBLBaseComponents", propOrder = {
        "id",
        "name",
        "description"
})
public class RiskFilterDetailsType
{

    @XmlElement(name = "Id", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected int id;
    @XmlElement(name = "Name", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected String name;
    @XmlElement(name = "Description", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected String description;

    /**
     * Gets the value of the id property.
     */
    public int getId()
    {
        return id;
    }

    /**
     * Sets the value of the id property.
     */
    public void setId(int value)
    {
        this.id = value;
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
     * Gets the value of the description property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * Sets the value of the description property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setDescription(String value)
    {
        this.description = value;
    }

}
