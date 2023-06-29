
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
 * <p>Java class for InvoiceType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InvoiceType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="Subtotal" type="{urn:networksolutions:apis}AmountType" minOccurs="0"/>
 *         &lt;element name="Handling" type="{urn:networksolutions:apis}AmountType" minOccurs="0"/>
 *         &lt;element name="Shipping" type="{urn:networksolutions:apis}AmountType" minOccurs="0"/>
 *         &lt;element name="Tax" type="{urn:networksolutions:apis}TaxAppliedType" minOccurs="0"/>
 *         &lt;element name="Surcharge" type="{urn:networksolutions:apis}AmountType" minOccurs="0"/>
 *         &lt;element name="Total" type="{urn:networksolutions:apis}AmountType" minOccurs="0"/>
 *         &lt;element name="Discount" type="{urn:networksolutions:apis}AmountType" minOccurs="0"/>
 *         &lt;element name="GiftCertificate" type="{urn:networksolutions:apis}AmountType" minOccurs="0"/>
 *         &lt;element name="CombineHandling" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="GiftCertificateCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Weight" type="{urn:networksolutions:apis}WeightType" minOccurs="0"/>
 *         &lt;element name="LineItemList" type="{urn:networksolutions:apis}LineItemType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="DiscountCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OrderDiscountList" type="{urn:networksolutions:apis}DiscountType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InvoiceType", propOrder = {
    "subtotal",
    "handling",
    "shipping",
    "tax",
    "surcharge",
    "total",
    "discount",
    "giftCertificate",
    "combineHandling",
    "giftCertificateCode",
    "weight",
    "lineItemList",
    "discountCode",
    "orderDiscountList"
})
public class InvoiceType {

    @XmlElement(name = "Subtotal")
    protected AmountType subtotal;
    @XmlElement(name = "Handling")
    protected AmountType handling;
    @XmlElement(name = "Shipping")
    protected AmountType shipping;
    @XmlElement(name = "Tax")
    protected TaxAppliedType tax;
    @XmlElement(name = "Surcharge")
    protected AmountType surcharge;
    @XmlElement(name = "Total")
    protected AmountType total;
    @XmlElement(name = "Discount")
    protected AmountType discount;
    @XmlElement(name = "GiftCertificate")
    protected AmountType giftCertificate;
    @XmlElement(name = "CombineHandling")
    protected Boolean combineHandling;
    @XmlElement(name = "GiftCertificateCode")
    protected String giftCertificateCode;
    @XmlElement(name = "Weight")
    protected WeightType weight;
    @XmlElement(name = "LineItemList")
    protected List<LineItemType> lineItemList;
    @XmlElement(name = "DiscountCode")
    protected String discountCode;
    @XmlElement(name = "OrderDiscountList")
    protected List<DiscountType> orderDiscountList;

    /**
     * Gets the value of the subtotal property.
     * 
     * @return
     *     possible object is
     *     {@link AmountType }
     *     
     */
    public AmountType getSubtotal() {
        return subtotal;
    }

    /**
     * Sets the value of the subtotal property.
     * 
     * @param value
     *     allowed object is
     *     {@link AmountType }
     *     
     */
    public void setSubtotal(AmountType value) {
        this.subtotal = value;
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
     * Gets the value of the shipping property.
     * 
     * @return
     *     possible object is
     *     {@link AmountType }
     *     
     */
    public AmountType getShipping() {
        return shipping;
    }

    /**
     * Sets the value of the shipping property.
     * 
     * @param value
     *     allowed object is
     *     {@link AmountType }
     *     
     */
    public void setShipping(AmountType value) {
        this.shipping = value;
    }

    /**
     * Gets the value of the tax property.
     * 
     * @return
     *     possible object is
     *     {@link TaxAppliedType }
     *     
     */
    public TaxAppliedType getTax() {
        return tax;
    }

    /**
     * Sets the value of the tax property.
     * 
     * @param value
     *     allowed object is
     *     {@link TaxAppliedType }
     *     
     */
    public void setTax(TaxAppliedType value) {
        this.tax = value;
    }

    /**
     * Gets the value of the surcharge property.
     * 
     * @return
     *     possible object is
     *     {@link AmountType }
     *     
     */
    public AmountType getSurcharge() {
        return surcharge;
    }

    /**
     * Sets the value of the surcharge property.
     * 
     * @param value
     *     allowed object is
     *     {@link AmountType }
     *     
     */
    public void setSurcharge(AmountType value) {
        this.surcharge = value;
    }

    /**
     * Gets the value of the total property.
     * 
     * @return
     *     possible object is
     *     {@link AmountType }
     *     
     */
    public AmountType getTotal() {
        return total;
    }

    /**
     * Sets the value of the total property.
     * 
     * @param value
     *     allowed object is
     *     {@link AmountType }
     *     
     */
    public void setTotal(AmountType value) {
        this.total = value;
    }

    /**
     * Gets the value of the discount property.
     * 
     * @return
     *     possible object is
     *     {@link AmountType }
     *     
     */
    public AmountType getDiscount() {
        return discount;
    }

    /**
     * Sets the value of the discount property.
     * 
     * @param value
     *     allowed object is
     *     {@link AmountType }
     *     
     */
    public void setDiscount(AmountType value) {
        this.discount = value;
    }

    /**
     * Gets the value of the giftCertificate property.
     * 
     * @return
     *     possible object is
     *     {@link AmountType }
     *     
     */
    public AmountType getGiftCertificate() {
        return giftCertificate;
    }

    /**
     * Sets the value of the giftCertificate property.
     * 
     * @param value
     *     allowed object is
     *     {@link AmountType }
     *     
     */
    public void setGiftCertificate(AmountType value) {
        this.giftCertificate = value;
    }

    /**
     * Gets the value of the combineHandling property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isCombineHandling() {
        return combineHandling;
    }

    /**
     * Sets the value of the combineHandling property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setCombineHandling(Boolean value) {
        this.combineHandling = value;
    }

    /**
     * Gets the value of the giftCertificateCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGiftCertificateCode() {
        return giftCertificateCode;
    }

    /**
     * Sets the value of the giftCertificateCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGiftCertificateCode(String value) {
        this.giftCertificateCode = value;
    }

    /**
     * Gets the value of the weight property.
     * 
     * @return
     *     possible object is
     *     {@link WeightType }
     *     
     */
    public WeightType getWeight() {
        return weight;
    }

    /**
     * Sets the value of the weight property.
     * 
     * @param value
     *     allowed object is
     *     {@link WeightType }
     *     
     */
    public void setWeight(WeightType value) {
        this.weight = value;
    }

    /**
     * Gets the value of the lineItemList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the lineItemList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLineItemList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LineItemType }
     * 
     * 
     */
    public List<LineItemType> getLineItemList() {
        if (lineItemList == null) {
            lineItemList = new ArrayList<LineItemType>();
        }
        return this.lineItemList;
    }

    /**
     * Gets the value of the discountCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDiscountCode() {
        return discountCode;
    }

    /**
     * Sets the value of the discountCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDiscountCode(String value) {
        this.discountCode = value;
    }

    /**
     * Gets the value of the orderDiscountList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the orderDiscountList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOrderDiscountList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DiscountType }
     * 
     * 
     */
    public List<DiscountType> getOrderDiscountList() {
        if (orderDiscountList == null) {
            orderDiscountList = new ArrayList<DiscountType>();
        }
        return this.orderDiscountList;
    }

}
