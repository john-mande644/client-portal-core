
package com.owd.jobs.jobobjects.networksolutions;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for ProductPriceType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProductPriceType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="BasePrice" type="{urn:networksolutions:apis}AmountType" minOccurs="0"/>
 *         &lt;element name="Cost" type="{urn:networksolutions:apis}AmountType" minOccurs="0"/>
 *         &lt;element name="UnitPrice" type="{urn:networksolutions:apis}AmountType" minOccurs="0"/>
 *         &lt;element name="Handling" type="{urn:networksolutions:apis}AmountType" minOccurs="0"/>
 *         &lt;element name="MaxUnitPrice" type="{urn:networksolutions:apis}AmountType" minOccurs="0"/>
 *         &lt;element name="MinUnitPrice" type="{urn:networksolutions:apis}AmountType" minOccurs="0"/>
 *         &lt;element name="Msrp" type="{urn:networksolutions:apis}AmountType" minOccurs="0"/>
 *         &lt;element name="Message" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PriceLevelList" type="{urn:networksolutions:apis}PriceLevelType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProductPriceType", propOrder = {
    "basePrice",
    "cost",
    "unitPrice",
    "handling",
    "maxUnitPrice",
    "minUnitPrice",
    "msrp",
    "message",
    "priceLevelList"
})
public class ProductPriceType {

    @XmlElement(name = "BasePrice")
    protected AmountType basePrice;
    @XmlElement(name = "Cost")
    protected AmountType cost;
    @XmlElement(name = "UnitPrice")
    protected AmountType unitPrice;
    @XmlElement(name = "Handling")
    protected AmountType handling;
    @XmlElement(name = "MaxUnitPrice")
    protected AmountType maxUnitPrice;
    @XmlElement(name = "MinUnitPrice")
    protected AmountType minUnitPrice;
    @XmlElement(name = "Msrp")
    protected AmountType msrp;
    @XmlElement(name = "Message")
    protected String message;
    @XmlElement(name = "PriceLevelList")
    protected List<PriceLevelType> priceLevelList;

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
     * Gets the value of the cost property.
     * 
     * @return
     *     possible object is
     *     {@link AmountType }
     *     
     */
    public AmountType getCost() {
        return cost;
    }

    /**
     * Sets the value of the cost property.
     * 
     * @param value
     *     allowed object is
     *     {@link AmountType }
     *     
     */
    public void setCost(AmountType value) {
        this.cost = value;
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
     * Gets the value of the handling property.
     * 
     * @return
     *     possible object is
     *     {@link AmountType }
     *     
     */
    public AmountType getHandling() {
        return handling;
    }

    /**
     * Sets the value of the handling property.
     * 
     * @param value
     *     allowed object is
     *     {@link AmountType }
     *     
     */
    public void setHandling(AmountType value) {
        this.handling = value;
    }

    /**
     * Gets the value of the maxUnitPrice property.
     * 
     * @return
     *     possible object is
     *     {@link AmountType }
     *     
     */
    public AmountType getMaxUnitPrice() {
        return maxUnitPrice;
    }

    /**
     * Sets the value of the maxUnitPrice property.
     * 
     * @param value
     *     allowed object is
     *     {@link AmountType }
     *     
     */
    public void setMaxUnitPrice(AmountType value) {
        this.maxUnitPrice = value;
    }

    /**
     * Gets the value of the minUnitPrice property.
     * 
     * @return
     *     possible object is
     *     {@link AmountType }
     *     
     */
    public AmountType getMinUnitPrice() {
        return minUnitPrice;
    }

    /**
     * Sets the value of the minUnitPrice property.
     * 
     * @param value
     *     allowed object is
     *     {@link AmountType }
     *     
     */
    public void setMinUnitPrice(AmountType value) {
        this.minUnitPrice = value;
    }

    /**
     * Gets the value of the msrp property.
     * 
     * @return
     *     possible object is
     *     {@link AmountType }
     *     
     */
    public AmountType getMsrp() {
        return msrp;
    }

    /**
     * Sets the value of the msrp property.
     * 
     * @param value
     *     allowed object is
     *     {@link AmountType }
     *     
     */
    public void setMsrp(AmountType value) {
        this.msrp = value;
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
     * Gets the value of the priceLevelList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the priceLevelList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPriceLevelList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PriceLevelType }
     * 
     * 
     */
    public List<PriceLevelType> getPriceLevelList() {
        if (priceLevelList == null) {
            priceLevelList = new ArrayList<PriceLevelType>();
        }
        return this.priceLevelList;
    }

}
