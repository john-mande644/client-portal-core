
package com.owd.jobs.jobobjects.networksolutions;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.*;


/**
 * <p>Java class for PriceLevelType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PriceLevelType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BasePrice" type="{urn:networksolutions:apis}AmountType" minOccurs="0"/>
 *         &lt;element name="UnitPrice" type="{urn:networksolutions:apis}AmountType" minOccurs="0"/>
 *         &lt;element name="Message" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PreventPurchase" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="PriceLevelId" type="{http://www.w3.org/2001/XMLSchema}long" />
 *       &lt;attribute name="Disassociate" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PriceLevelType", propOrder = {
    "name",
    "basePrice",
    "unitPrice",
    "message",
    "preventPurchase"
})
public class PriceLevelType {

    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "BasePrice")
    protected AmountType basePrice;
    @XmlElement(name = "UnitPrice")
    protected AmountType unitPrice;
    @XmlElement(name = "Message")
    protected String message;
    @XmlElement(name = "PreventPurchase")
    protected Boolean preventPurchase;
    @XmlAttribute(name = "PriceLevelId")
    protected Long priceLevelId;
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
     * Gets the value of the basePrice property.
     * 
     * @return
     *     possible object is
     *     {@link AmountType }
     *     
     */
    public AmountType getBasePrice() {
        return basePrice;
    }

    /**
     * Sets the value of the basePrice property.
     * 
     * @param value
     *     allowed object is
     *     {@link AmountType }
     *     
     */
    public void setBasePrice(AmountType value) {
        this.basePrice = value;
    }

    /**
     * Gets the value of the unitPrice property.
     * 
     * @return
     *     possible object is
     *     {@link AmountType }
     *     
     */
    public AmountType getUnitPrice() {
        return unitPrice;
    }

    /**
     * Sets the value of the unitPrice property.
     * 
     * @param value
     *     allowed object is
     *     {@link AmountType }
     *     
     */
    public void setUnitPrice(AmountType value) {
        this.unitPrice = value;
    }

    /**
     * Gets the value of the message property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the value of the message property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessage(String value) {
        this.message = value;
    }

    /**
     * Gets the value of the preventPurchase property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isPreventPurchase() {
        return preventPurchase;
    }

    /**
     * Sets the value of the preventPurchase property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setPreventPurchase(Boolean value) {
        this.preventPurchase = value;
    }

    /**
     * Gets the value of the priceLevelId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getPriceLevelId() {
        return priceLevelId;
    }

    /**
     * Sets the value of the priceLevelId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setPriceLevelId(Long value) {
        this.priceLevelId = value;
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
