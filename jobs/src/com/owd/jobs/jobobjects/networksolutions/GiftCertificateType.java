
package com.owd.jobs.jobobjects.networksolutions;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for GiftCertificateType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GiftCertificateType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="Adjustment" type="{urn:networksolutions:apis}AmountType" minOccurs="0"/>
 *         &lt;element name="CreateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="Expiration" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="GiftCertificateStatus" type="{urn:networksolutions:apis}GiftCertificateStatusCodeType" minOccurs="0"/>
 *         &lt;element name="Notes" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Original" type="{urn:networksolutions:apis}AmountType" minOccurs="0"/>
 *         &lt;element name="Remaining" type="{urn:networksolutions:apis}AmountType" minOccurs="0"/>
 *         &lt;element name="Order" type="{urn:networksolutions:apis}OrderType" minOccurs="0"/>
 *         &lt;element name="Product" type="{urn:networksolutions:apis}ProductType" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="Code" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GiftCertificateType", propOrder = {
    "adjustment",
    "createTime",
    "expiration",
    "giftCertificateStatus",
    "notes",
    "original",
    "remaining",
    "order",
    "product"
})
public class GiftCertificateType {

    @XmlElement(name = "Adjustment")
    protected AmountType adjustment;
    @XmlElement(name = "CreateTime")
    protected XMLGregorianCalendar createTime;
    @XmlElement(name = "Expiration")
    protected XMLGregorianCalendar expiration;
    @XmlElement(name = "GiftCertificateStatus")
    protected GiftCertificateStatusCodeType giftCertificateStatus;
    @XmlElement(name = "Notes")
    protected String notes;
    @XmlElement(name = "Original")
    protected AmountType original;
    @XmlElement(name = "Remaining")
    protected AmountType remaining;
    @XmlElement(name = "Order")
    protected OrderType order;
    @XmlElement(name = "Product")
    protected ProductType product;
    @XmlAttribute(name = "Code")
    protected String code;

    /**
     * Gets the value of the adjustment property.
     * 
     * @return
     *     possible object is
     *     {@link AmountType }
     *     
     */
    public AmountType getAdjustment() {
        return adjustment;
    }

    /**
     * Sets the value of the adjustment property.
     * 
     * @param value
     *     allowed object is
     *     {@link AmountType }
     *     
     */
    public void setAdjustment(AmountType value) {
        this.adjustment = value;
    }

    /**
     * Gets the value of the createTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCreateTime() {
        return createTime;
    }

    /**
     * Sets the value of the createTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCreateTime(XMLGregorianCalendar value) {
        this.createTime = value;
    }

    /**
     * Gets the value of the expiration property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getExpiration() {
        return expiration;
    }

    /**
     * Sets the value of the expiration property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setExpiration(XMLGregorianCalendar value) {
        this.expiration = value;
    }

    /**
     * Gets the value of the giftCertificateStatus property.
     * 
     * @return
     *     possible object is
     *     {@link GiftCertificateStatusCodeType }
     *     
     */
    public GiftCertificateStatusCodeType getGiftCertificateStatus() {
        return giftCertificateStatus;
    }

    /**
     * Sets the value of the giftCertificateStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link GiftCertificateStatusCodeType }
     *     
     */
    public void setGiftCertificateStatus(GiftCertificateStatusCodeType value) {
        this.giftCertificateStatus = value;
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
     * Gets the value of the original property.
     * 
     * @return
     *     possible object is
     *     {@link AmountType }
     *     
     */
    public AmountType getOriginal() {
        return original;
    }

    /**
     * Sets the value of the original property.
     * 
     * @param value
     *     allowed object is
     *     {@link AmountType }
     *     
     */
    public void setOriginal(AmountType value) {
        this.original = value;
    }

    /**
     * Gets the value of the remaining property.
     * 
     * @return
     *     possible object is
     *     {@link AmountType }
     *     
     */
    public AmountType getRemaining() {
        return remaining;
    }

    /**
     * Sets the value of the remaining property.
     * 
     * @param value
     *     allowed object is
     *     {@link AmountType }
     *     
     */
    public void setRemaining(AmountType value) {
        this.remaining = value;
    }

    /**
     * Gets the value of the order property.
     * 
     * @return
     *     possible object is
     *     {@link OrderType }
     *     
     */
    public OrderType getOrder() {
        return order;
    }

    /**
     * Sets the value of the order property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrderType }
     *     
     */
    public void setOrder(OrderType value) {
        this.order = value;
    }

    /**
     * Gets the value of the product property.
     * 
     * @return
     *     possible object is
     *     {@link ProductType }
     *     
     */
    public ProductType getProduct() {
        return product;
    }

    /**
     * Sets the value of the product property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProductType }
     *     
     */
    public void setProduct(ProductType value) {
        this.product = value;
    }

    /**
     * Gets the value of the code property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the value of the code property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCode(String value) {
        this.code = value;
    }

}
