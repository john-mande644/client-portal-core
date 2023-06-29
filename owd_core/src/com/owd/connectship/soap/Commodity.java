
package com.owd.connectship.soap;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


/**
 * Elements that represent a commodity item in a shipment.
 * 
 * <p>Java class for Commodity complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Commodity">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;all>
 *         &lt;element name="certOfOriginMethod" type="{urn:connectship-com:ampcore}enumItem" minOccurs="0"/>
 *         &lt;element name="commodityCondition" type="{urn:connectship-com:ampcore}enumItem" minOccurs="0"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="eccn" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="exportQuantity1" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="exportQuantity2" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="exportQuantityUnitMeasure1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="exportQuantityUnitMeasure2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="harmonizedCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="licenseExpirationDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="licenseNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="licenseType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="manufacturerId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="naftaPreferenceCriterion" type="{urn:connectship-com:ampcore}enumItem" minOccurs="0"/>
 *         &lt;element name="naftaProducer" type="{urn:connectship-com:ampcore}enumItem" minOccurs="0"/>
 *         &lt;element name="naftaRvcAvgEndDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="naftaRvcAvgStartDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="naftaRvcMethod" type="{urn:connectship-com:ampcore}enumItem" minOccurs="0"/>
 *         &lt;element name="origin" type="{urn:connectship-com:ampcore}NameAddress" minOccurs="0"/>
 *         &lt;element name="originCountry" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="originTaxId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="productCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="quantity" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="quantityUnitMeasure" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="restrictedArticleType" type="{urn:connectship-com:ampcore}enumList" minOccurs="0"/>
 *         &lt;element name="sedMethod" type="{urn:connectship-com:ampcore}enumItem" minOccurs="0"/>
 *         &lt;element name="unitValue" type="{urn:connectship-com:ampcore}Money" minOccurs="0"/>
 *         &lt;element name="unitWeight" type="{urn:connectship-com:ampcore}Weight" minOccurs="0"/>
 *         &lt;element name="userData1" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *         &lt;element name="userData2" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *         &lt;element name="userData3" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *         &lt;element name="userData4" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *         &lt;element name="userData5" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Commodity", propOrder = {

})
public class Commodity {

    protected String certOfOriginMethod;
    protected String commodityCondition;
    protected String description;
    protected String eccn;
    protected BigDecimal exportQuantity1;
    protected BigDecimal exportQuantity2;
    protected String exportQuantityUnitMeasure1;
    protected String exportQuantityUnitMeasure2;
    protected String harmonizedCode;
    @XmlElement(defaultValue = "0001-01-01")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar licenseExpirationDate;
    protected String licenseNumber;
    protected String licenseType;
    protected String manufacturerId;
    protected String naftaPreferenceCriterion;
    protected String naftaProducer;
    @XmlElement(defaultValue = "0001-01-01")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar naftaRvcAvgEndDate;
    @XmlElement(defaultValue = "0001-01-01")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar naftaRvcAvgStartDate;
    protected String naftaRvcMethod;
    protected NameAddress origin;
    protected String originCountry;
    protected String originTaxId;
    protected String productCode;
    protected Integer quantity;
    protected String quantityUnitMeasure;
    @XmlList
    protected List<String> restrictedArticleType;
    protected String sedMethod;
    protected Money unitValue;
    protected Weight unitWeight;
    protected Object userData1;
    protected Object userData2;
    protected Object userData3;
    protected Object userData4;
    protected Object userData5;

    /**
     * Gets the value of the certOfOriginMethod property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCertOfOriginMethod() {
        return certOfOriginMethod;
    }

    /**
     * Sets the value of the certOfOriginMethod property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCertOfOriginMethod(String value) {
        this.certOfOriginMethod = value;
    }

    /**
     * Gets the value of the commodityCondition property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCommodityCondition() {
        return commodityCondition;
    }

    /**
     * Sets the value of the commodityCondition property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCommodityCondition(String value) {
        this.commodityCondition = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the eccn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEccn() {
        return eccn;
    }

    /**
     * Sets the value of the eccn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEccn(String value) {
        this.eccn = value;
    }

    /**
     * Gets the value of the exportQuantity1 property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getExportQuantity1() {
        return exportQuantity1;
    }

    /**
     * Sets the value of the exportQuantity1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setExportQuantity1(BigDecimal value) {
        this.exportQuantity1 = value;
    }

    /**
     * Gets the value of the exportQuantity2 property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getExportQuantity2() {
        return exportQuantity2;
    }

    /**
     * Sets the value of the exportQuantity2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setExportQuantity2(BigDecimal value) {
        this.exportQuantity2 = value;
    }

    /**
     * Gets the value of the exportQuantityUnitMeasure1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExportQuantityUnitMeasure1() {
        return exportQuantityUnitMeasure1;
    }

    /**
     * Sets the value of the exportQuantityUnitMeasure1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExportQuantityUnitMeasure1(String value) {
        this.exportQuantityUnitMeasure1 = value;
    }

    /**
     * Gets the value of the exportQuantityUnitMeasure2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExportQuantityUnitMeasure2() {
        return exportQuantityUnitMeasure2;
    }

    /**
     * Sets the value of the exportQuantityUnitMeasure2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExportQuantityUnitMeasure2(String value) {
        this.exportQuantityUnitMeasure2 = value;
    }

    /**
     * Gets the value of the harmonizedCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHarmonizedCode() {
        return harmonizedCode;
    }

    /**
     * Sets the value of the harmonizedCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHarmonizedCode(String value) {
        this.harmonizedCode = value;
    }

    /**
     * Gets the value of the licenseExpirationDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLicenseExpirationDate() {
        return licenseExpirationDate;
    }

    /**
     * Sets the value of the licenseExpirationDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLicenseExpirationDate(XMLGregorianCalendar value) {
        this.licenseExpirationDate = value;
    }

    /**
     * Gets the value of the licenseNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLicenseNumber() {
        return licenseNumber;
    }

    /**
     * Sets the value of the licenseNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLicenseNumber(String value) {
        this.licenseNumber = value;
    }

    /**
     * Gets the value of the licenseType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLicenseType() {
        return licenseType;
    }

    /**
     * Sets the value of the licenseType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLicenseType(String value) {
        this.licenseType = value;
    }

    /**
     * Gets the value of the manufacturerId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getManufacturerId() {
        return manufacturerId;
    }

    /**
     * Sets the value of the manufacturerId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setManufacturerId(String value) {
        this.manufacturerId = value;
    }

    /**
     * Gets the value of the naftaPreferenceCriterion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNaftaPreferenceCriterion() {
        return naftaPreferenceCriterion;
    }

    /**
     * Sets the value of the naftaPreferenceCriterion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNaftaPreferenceCriterion(String value) {
        this.naftaPreferenceCriterion = value;
    }

    /**
     * Gets the value of the naftaProducer property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNaftaProducer() {
        return naftaProducer;
    }

    /**
     * Sets the value of the naftaProducer property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNaftaProducer(String value) {
        this.naftaProducer = value;
    }

    /**
     * Gets the value of the naftaRvcAvgEndDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getNaftaRvcAvgEndDate() {
        return naftaRvcAvgEndDate;
    }

    /**
     * Sets the value of the naftaRvcAvgEndDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setNaftaRvcAvgEndDate(XMLGregorianCalendar value) {
        this.naftaRvcAvgEndDate = value;
    }

    /**
     * Gets the value of the naftaRvcAvgStartDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getNaftaRvcAvgStartDate() {
        return naftaRvcAvgStartDate;
    }

    /**
     * Sets the value of the naftaRvcAvgStartDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setNaftaRvcAvgStartDate(XMLGregorianCalendar value) {
        this.naftaRvcAvgStartDate = value;
    }

    /**
     * Gets the value of the naftaRvcMethod property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNaftaRvcMethod() {
        return naftaRvcMethod;
    }

    /**
     * Sets the value of the naftaRvcMethod property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNaftaRvcMethod(String value) {
        this.naftaRvcMethod = value;
    }

    /**
     * Gets the value of the origin property.
     * 
     * @return
     *     possible object is
     *     {@link NameAddress }
     *     
     */
    public NameAddress getOrigin() {
        return origin;
    }

    /**
     * Sets the value of the origin property.
     * 
     * @param value
     *     allowed object is
     *     {@link NameAddress }
     *     
     */
    public void setOrigin(NameAddress value) {
        this.origin = value;
    }

    /**
     * Gets the value of the originCountry property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOriginCountry() {
        return originCountry;
    }

    /**
     * Sets the value of the originCountry property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOriginCountry(String value) {
        this.originCountry = value;
    }

    /**
     * Gets the value of the originTaxId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOriginTaxId() {
        return originTaxId;
    }

    /**
     * Sets the value of the originTaxId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOriginTaxId(String value) {
        this.originTaxId = value;
    }

    /**
     * Gets the value of the productCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProductCode() {
        return productCode;
    }

    /**
     * Sets the value of the productCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProductCode(String value) {
        this.productCode = value;
    }

    /**
     * Gets the value of the quantity property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * Sets the value of the quantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setQuantity(Integer value) {
        this.quantity = value;
    }

    /**
     * Gets the value of the quantityUnitMeasure property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQuantityUnitMeasure() {
        return quantityUnitMeasure;
    }

    /**
     * Sets the value of the quantityUnitMeasure property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQuantityUnitMeasure(String value) {
        this.quantityUnitMeasure = value;
    }

    /**
     * Gets the value of the restrictedArticleType property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the restrictedArticleType property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRestrictedArticleType().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getRestrictedArticleType() {
        if (restrictedArticleType == null) {
            restrictedArticleType = new ArrayList<String>();
        }
        return this.restrictedArticleType;
    }

    /**
     * Gets the value of the sedMethod property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSedMethod() {
        return sedMethod;
    }

    /**
     * Sets the value of the sedMethod property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSedMethod(String value) {
        this.sedMethod = value;
    }

    /**
     * Gets the value of the unitValue property.
     * 
     * @return
     *     possible object is
     *     {@link Money }
     *     
     */
    public Money getUnitValue() {
        return unitValue;
    }

    /**
     * Sets the value of the unitValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link Money }
     *     
     */
    public void setUnitValue(Money value) {
        this.unitValue = value;
    }

    /**
     * Gets the value of the unitWeight property.
     * 
     * @return
     *     possible object is
     *     {@link Weight }
     *     
     */
    public Weight getUnitWeight() {
        return unitWeight;
    }

    /**
     * Sets the value of the unitWeight property.
     * 
     * @param value
     *     allowed object is
     *     {@link Weight }
     *     
     */
    public void setUnitWeight(Weight value) {
        this.unitWeight = value;
    }

    /**
     * Gets the value of the userData1 property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getUserData1() {
        return userData1;
    }

    /**
     * Sets the value of the userData1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setUserData1(Object value) {
        this.userData1 = value;
    }

    /**
     * Gets the value of the userData2 property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getUserData2() {
        return userData2;
    }

    /**
     * Sets the value of the userData2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setUserData2(Object value) {
        this.userData2 = value;
    }

    /**
     * Gets the value of the userData3 property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getUserData3() {
        return userData3;
    }

    /**
     * Sets the value of the userData3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setUserData3(Object value) {
        this.userData3 = value;
    }

    /**
     * Gets the value of the userData4 property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getUserData4() {
        return userData4;
    }

    /**
     * Sets the value of the userData4 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setUserData4(Object value) {
        this.userData4 = value;
    }

    /**
     * Gets the value of the userData5 property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getUserData5() {
        return userData5;
    }

    /**
     * Sets the value of the userData5 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setUserData5(Object value) {
        this.userData5 = value;
    }

}
