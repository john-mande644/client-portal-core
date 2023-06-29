
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for contactField complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="contactField">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="displayAs" type="{http://service.admin.ws.five9.com/v2/}contactFieldDisplay" minOccurs="0"/>
 *         &lt;element name="mapTo" type="{http://service.admin.ws.five9.com/v2/}contactFieldMapping" minOccurs="0"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="restrictions" type="{http://service.admin.ws.five9.com/v2/}contactFieldRestriction" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="system" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="type" type="{http://service.admin.ws.five9.com/v2/}contactFieldType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "contactField", propOrder = {
    "displayAs",
    "mapTo",
    "name",
    "restrictions",
    "system",
    "type"
})
public class ContactField {

    protected ContactFieldDisplay displayAs;
    protected ContactFieldMapping mapTo;
    protected String name;
    @XmlElement(nillable = true)
    protected List<ContactFieldRestriction> restrictions;
    protected Boolean system;
    protected ContactFieldType type;

    /**
     * Gets the value of the displayAs property.
     * 
     * @return
     *     possible object is
     *     {@link ContactFieldDisplay }
     *     
     */
    public ContactFieldDisplay getDisplayAs() {
        return displayAs;
    }

    /**
     * Sets the value of the displayAs property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContactFieldDisplay }
     *     
     */
    public void setDisplayAs(ContactFieldDisplay value) {
        this.displayAs = value;
    }

    /**
     * Gets the value of the mapTo property.
     * 
     * @return
     *     possible object is
     *     {@link ContactFieldMapping }
     *     
     */
    public ContactFieldMapping getMapTo() {
        return mapTo;
    }

    /**
     * Sets the value of the mapTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContactFieldMapping }
     *     
     */
    public void setMapTo(ContactFieldMapping value) {
        this.mapTo = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the restrictions property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the restrictions property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRestrictions().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ContactFieldRestriction }
     * 
     * 
     */
    public List<ContactFieldRestriction> getRestrictions() {
        if (restrictions == null) {
            restrictions = new ArrayList<ContactFieldRestriction>();
        }
        return this.restrictions;
    }

    /**
     * Gets the value of the system property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isSystem() {
        return system;
    }

    /**
     * Sets the value of the system property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSystem(Boolean value) {
        this.system = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link ContactFieldType }
     *     
     */
    public ContactFieldType getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContactFieldType }
     *     
     */
    public void setType(ContactFieldType value) {
        this.type = value;
    }

}
