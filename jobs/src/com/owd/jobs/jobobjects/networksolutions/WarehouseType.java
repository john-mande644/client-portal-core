
package com.owd.jobs.jobobjects.networksolutions;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.*;


/**
 * <p>Java class for WarehouseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="WarehouseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EmailAddress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EmailHeader" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EmailPricing" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="EmailProducts" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="EmailShipping" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="Enabled" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="Notes" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="WarehouseId" type="{http://www.w3.org/2001/XMLSchema}long" />
 *       &lt;attribute name="Disassociate" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "WarehouseType", propOrder = {
    "name",
    "emailAddress",
    "emailHeader",
    "emailPricing",
    "emailProducts",
    "emailShipping",
    "enabled",
    "notes"
})
public class WarehouseType {

    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "EmailAddress")
    protected String emailAddress;
    @XmlElement(name = "EmailHeader")
    protected String emailHeader;
    @XmlElement(name = "EmailPricing")
    protected Boolean emailPricing;
    @XmlElement(name = "EmailProducts")
    protected Boolean emailProducts;
    @XmlElement(name = "EmailShipping")
    protected Boolean emailShipping;
    @XmlElement(name = "Enabled")
    protected Boolean enabled;
    @XmlElement(name = "Notes")
    protected String notes;
    @XmlAttribute(name = "WarehouseId")
    protected Long warehouseId;
    @XmlAttribute(name = "Disassociate")
    protected Boolean disassociate;

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
     * Gets the value of the emailAddress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     * Sets the value of the emailAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmailAddress(String value) {
        this.emailAddress = value;
    }

    /**
     * Gets the value of the emailHeader property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmailHeader() {
        return emailHeader;
    }

    /**
     * Sets the value of the emailHeader property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmailHeader(String value) {
        this.emailHeader = value;
    }

    /**
     * Gets the value of the emailPricing property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isEmailPricing() {
        return emailPricing;
    }

    /**
     * Sets the value of the emailPricing property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setEmailPricing(Boolean value) {
        this.emailPricing = value;
    }

    /**
     * Gets the value of the emailProducts property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isEmailProducts() {
        return emailProducts;
    }

    /**
     * Sets the value of the emailProducts property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setEmailProducts(Boolean value) {
        this.emailProducts = value;
    }

    /**
     * Gets the value of the emailShipping property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isEmailShipping() {
        return emailShipping;
    }

    /**
     * Sets the value of the emailShipping property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setEmailShipping(Boolean value) {
        this.emailShipping = value;
    }

    /**
     * Gets the value of the enabled property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isEnabled() {
        return enabled;
    }

    /**
     * Sets the value of the enabled property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setEnabled(Boolean value) {
        this.enabled = value;
    }

    /**
     * Gets the value of the notes property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNotes() {
        return notes;
    }

    /**
     * Sets the value of the notes property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNotes(String value) {
        this.notes = value;
    }

    /**
     * Gets the value of the warehouseId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getWarehouseId() {
        return warehouseId;
    }

    /**
     * Sets the value of the warehouseId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setWarehouseId(Long value) {
        this.warehouseId = value;
    }

    /**
     * Gets the value of the disassociate property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isDisassociate() {
        return disassociate;
    }

    /**
     * Sets the value of the disassociate property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDisassociate(Boolean value) {
        this.disassociate = value;
    }

}
