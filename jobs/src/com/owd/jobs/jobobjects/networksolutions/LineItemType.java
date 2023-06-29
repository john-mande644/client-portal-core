
package com.owd.jobs.jobobjects.networksolutions;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for LineItemType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="LineItemType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="FreeShipping" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NonTaxable" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="PartNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ProductId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="ProductClass" type="{urn:networksolutions:apis}ProductCodeType" minOccurs="0"/>
 *         &lt;element name="QtySold" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="Weight" type="{urn:networksolutions:apis}WeightType" minOccurs="0"/>
 *         &lt;element name="UnitPrice" type="{urn:networksolutions:apis}AmountType" minOccurs="0"/>
 *         &lt;element name="ShippingOption" type="{urn:networksolutions:apis}ShippingOptionCodeType" minOccurs="0"/>
 *         &lt;element name="QuestionList" type="{urn:networksolutions:apis}QuestionType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="SelectedVariationList" type="{urn:networksolutions:apis}SelectedVariationType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="LineItemId" type="{http://www.w3.org/2001/XMLSchema}long" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LineItemType", propOrder = {
    "freeShipping",
    "name",
    "nonTaxable",
    "partNumber",
    "productId",
    "productClass",
    "qtySold",
    "weight",
    "unitPrice",
    "shippingOption",
    "questionList",
    "selectedVariationList"
})
public class LineItemType {

    @XmlElement(name = "FreeShipping")
    protected Boolean freeShipping;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "NonTaxable")
    protected Boolean nonTaxable;
    @XmlElement(name = "PartNumber")
    protected String partNumber;
    @XmlElement(name = "ProductId")
    protected Long productId;
    @XmlElement(name = "ProductClass")
    protected ProductCodeType productClass;
    @XmlElement(name = "QtySold")
    protected Integer qtySold;
    @XmlElement(name = "Weight")
    protected WeightType weight;
    @XmlElement(name = "UnitPrice")
    protected AmountType unitPrice;
    @XmlElement(name = "ShippingOption")
    protected ShippingOptionCodeType shippingOption;
    @XmlElement(name = "QuestionList")
    protected List<QuestionType> questionList;
    @XmlElement(name = "SelectedVariationList")
    protected List<SelectedVariationType> selectedVariationList;
    @XmlAttribute(name = "LineItemId")
    protected Long lineItemId;

    /**
     * Gets the value of the freeShipping property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isFreeShipping() {
        return freeShipping;
    }

    /**
     * Sets the value of the freeShipping property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setFreeShipping(Boolean value) {
        this.freeShipping = value;
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
     * Gets the value of the nonTaxable property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isNonTaxable() {
        return nonTaxable;
    }

    /**
     * Sets the value of the nonTaxable property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setNonTaxable(Boolean value) {
        this.nonTaxable = value;
    }

    /**
     * Gets the value of the partNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPartNumber() {
        return partNumber;
    }

    /**
     * Sets the value of the partNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPartNumber(String value) {
        this.partNumber = value;
    }

    /**
     * Gets the value of the productId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getProductId() {
        return productId;
    }

    /**
     * Sets the value of the productId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setProductId(Long value) {
        this.productId = value;
    }

    /**
     * Gets the value of the productClass property.
     * 
     * @return
     *     possible object is
     *     {@link ProductCodeType }
     *     
     */
    public ProductCodeType getProductClass() {
        return productClass;
    }

    /**
     * Sets the value of the productClass property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProductCodeType }
     *     
     */
    public void setProductClass(ProductCodeType value) {
        this.productClass = value;
    }

    /**
     * Gets the value of the qtySold property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getQtySold() {
        return qtySold;
    }

    /**
     * Sets the value of the qtySold property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setQtySold(Integer value) {
        this.qtySold = value;
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
     * Gets the value of the shippingOption property.
     * 
     * @return
     *     possible object is
     *     {@link ShippingOptionCodeType }
     *     
     */
    public ShippingOptionCodeType getShippingOption() {
        return shippingOption;
    }

    /**
     * Sets the value of the shippingOption property.
     * 
     * @param value
     *     allowed object is
     *     {@link ShippingOptionCodeType }
     *     
     */
    public void setShippingOption(ShippingOptionCodeType value) {
        this.shippingOption = value;
    }

    /**
     * Gets the value of the questionList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the questionList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getQuestionList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link QuestionType }
     * 
     * 
     */
    public List<QuestionType> getQuestionList() {
        if (questionList == null) {
            questionList = new ArrayList<QuestionType>();
        }
        return this.questionList;
    }

    /**
     * Gets the value of the selectedVariationList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the selectedVariationList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSelectedVariationList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SelectedVariationType }
     * 
     * 
     */
    public List<SelectedVariationType> getSelectedVariationList() {
        if (selectedVariationList == null) {
            selectedVariationList = new ArrayList<SelectedVariationType>();
        }
        return this.selectedVariationList;
    }

    /**
     * Gets the value of the lineItemId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getLineItemId() {
        return lineItemId;
    }

    /**
     * Sets the value of the lineItemId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setLineItemId(Long value) {
        this.lineItemId = value;
    }

}
