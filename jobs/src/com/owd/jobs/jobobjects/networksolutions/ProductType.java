
package com.owd.jobs.jobobjects.networksolutions;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for ProductType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProductType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="AverageRating" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="CategorySpecial" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="CreateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Enabled" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="FreeShipping" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="FullDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="HomePageSpecial" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="InStockMessage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MaxOrderQty" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="ManufacturerPartNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MinOrderQty" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NonTaxable" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="AdminNotes" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OutStockMessage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PageUrl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ParentProduct" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="PartNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PreventPurchase" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="ProductClass" type="{urn:networksolutions:apis}ProductCodeType" minOccurs="0"/>
 *         &lt;element name="QtyInStock" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="SortOrder" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="UpdateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="DownloadInformation" type="{urn:networksolutions:apis}DownloadProductType" minOccurs="0"/>
 *         &lt;element name="Manufacturer" type="{urn:networksolutions:apis}ManufacturerType" minOccurs="0"/>
 *         &lt;element name="Parent" type="{urn:networksolutions:apis}ProductType" minOccurs="0"/>
 *         &lt;element name="Price" type="{urn:networksolutions:apis}ProductPriceType" minOccurs="0"/>
 *         &lt;element name="SearchInformation" type="{urn:networksolutions:apis}SearchInformationType" minOccurs="0"/>
 *         &lt;element name="Warehouse" type="{urn:networksolutions:apis}WarehouseType" minOccurs="0"/>
 *         &lt;element name="Weight" type="{urn:networksolutions:apis}WeightType" minOccurs="0"/>
 *         &lt;element name="ShippingOption" type="{urn:networksolutions:apis}ShippingOptionCodeType" minOccurs="0"/>
 *         &lt;element name="ShippingLabel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="QtyUnlimited" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="CategoryList" type="{urn:networksolutions:apis}CategoryType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="ImageList" type="{urn:networksolutions:apis}ProductImageType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="QuestionList" type="{urn:networksolutions:apis}QuestionType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="RelatedProductList" type="{urn:networksolutions:apis}ProductType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="SelectedVariationList" type="{urn:networksolutions:apis}SelectedVariationType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="VariationList" type="{urn:networksolutions:apis}VariationGroupType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="GiftCertificateInformation" type="{urn:networksolutions:apis}GiftCertificateProductType" minOccurs="0"/>
 *         &lt;element name="QtyReorder" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="ProductAttributeList" type="{urn:networksolutions:apis}AttributeType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="ProductId" type="{http://www.w3.org/2001/XMLSchema}long" />
 *       &lt;attribute name="Disassociate" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProductType", propOrder = {
    "averageRating",
    "categorySpecial",
    "createTime",
    "description",
    "enabled",
    "freeShipping",
    "fullDescription",
    "homePageSpecial",
    "inStockMessage",
    "maxOrderQty",
    "manufacturerPartNumber",
    "minOrderQty",
    "name",
    "nonTaxable",
    "adminNotes",
    "outStockMessage",
    "pageUrl",
    "parentProduct",
    "partNumber",
    "preventPurchase",
    "productClass",
    "qtyInStock",
    "sortOrder",
    "updateTime",
    "downloadInformation",
    "manufacturer",
    "parent",
    "price",
    "searchInformation",
    "warehouse",
    "weight",
    "shippingOption",
    "shippingLabel",
    "qtyUnlimited",
    "categoryList",
    "imageList",
    "questionList",
    "relatedProductList",
    "selectedVariationList",
    "variationList",
    "giftCertificateInformation",
    "qtyReorder",
    "productAttributeList"
})
public class ProductType {

    @XmlElement(name = "AverageRating")
    protected BigDecimal averageRating;
    @XmlElement(name = "CategorySpecial")
    protected Boolean categorySpecial;
    @XmlElement(name = "CreateTime")
    protected XMLGregorianCalendar createTime;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "Enabled")
    protected Boolean enabled;
    @XmlElement(name = "FreeShipping")
    protected Boolean freeShipping;
    @XmlElement(name = "FullDescription")
    protected String fullDescription;
    @XmlElement(name = "HomePageSpecial")
    protected Boolean homePageSpecial;
    @XmlElement(name = "InStockMessage")
    protected String inStockMessage;
    @XmlElement(name = "MaxOrderQty")
    protected Integer maxOrderQty;
    @XmlElement(name = "ManufacturerPartNumber")
    protected String manufacturerPartNumber;
    @XmlElement(name = "MinOrderQty")
    protected Integer minOrderQty;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "NonTaxable")
    protected Boolean nonTaxable;
    @XmlElement(name = "AdminNotes")
    protected String adminNotes;
    @XmlElement(name = "OutStockMessage")
    protected String outStockMessage;
    @XmlElement(name = "PageUrl")
    protected String pageUrl;
    @XmlElement(name = "ParentProduct")
    protected Boolean parentProduct;
    @XmlElement(name = "PartNumber")
    protected String partNumber;
    @XmlElement(name = "PreventPurchase")
    protected Boolean preventPurchase;
    @XmlElement(name = "ProductClass")
    protected ProductCodeType productClass;
    @XmlElement(name = "QtyInStock")
    protected Integer qtyInStock;
    @XmlElement(name = "SortOrder")
    protected Integer sortOrder;
    @XmlElement(name = "UpdateTime")
    protected XMLGregorianCalendar updateTime;
    @XmlElement(name = "DownloadInformation")
    protected DownloadProductType downloadInformation;
    @XmlElement(name = "Manufacturer")
    protected ManufacturerType manufacturer;
    @XmlElement(name = "Parent")
    protected ProductType parent;
    @XmlElement(name = "Price")
    protected ProductPriceType price;
    @XmlElement(name = "SearchInformation")
    protected SearchInformationType searchInformation;
    @XmlElement(name = "Warehouse")
    protected WarehouseType warehouse;
    @XmlElement(name = "Weight")
    protected WeightType weight;
    @XmlElement(name = "ShippingOption")
    protected ShippingOptionCodeType shippingOption;
    @XmlElement(name = "ShippingLabel")
    protected String shippingLabel;
    @XmlElement(name = "QtyUnlimited")
    protected Boolean qtyUnlimited;
    @XmlElement(name = "CategoryList")
    protected List<CategoryType> categoryList;
    @XmlElement(name = "ImageList")
    protected List<ProductImageType> imageList;
    @XmlElement(name = "QuestionList")
    protected List<QuestionType> questionList;
    @XmlElement(name = "RelatedProductList")
    protected List<ProductType> relatedProductList;
    @XmlElement(name = "SelectedVariationList")
    protected List<SelectedVariationType> selectedVariationList;
    @XmlElement(name = "VariationList")
    protected List<VariationGroupType> variationList;
    @XmlElement(name = "GiftCertificateInformation")
    protected GiftCertificateProductType giftCertificateInformation;
    @XmlElement(name = "QtyReorder")
    protected Integer qtyReorder;
    @XmlElement(name = "ProductAttributeList")
    protected List<AttributeType> productAttributeList;
    @XmlAttribute(name = "ProductId")
    protected Long productId;
    @XmlAttribute(name = "Disassociate")
    protected Boolean disassociate;

    /**
     * Gets the value of the averageRating property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAverageRating() {
        return averageRating;
    }

    /**
     * Sets the value of the averageRating property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAverageRating(BigDecimal value) {
        this.averageRating = value;
    }

    /**
     * Gets the value of the categorySpecial property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isCategorySpecial() {
        return categorySpecial;
    }

    /**
     * Sets the value of the categorySpecial property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setCategorySpecial(Boolean value) {
        this.categorySpecial = value;
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
     * Gets the value of the fullDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFullDescription() {
        return fullDescription;
    }

    /**
     * Sets the value of the fullDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFullDescription(String value) {
        this.fullDescription = value;
    }

    /**
     * Gets the value of the homePageSpecial property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isHomePageSpecial() {
        return homePageSpecial;
    }

    /**
     * Sets the value of the homePageSpecial property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setHomePageSpecial(Boolean value) {
        this.homePageSpecial = value;
    }

    /**
     * Gets the value of the inStockMessage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInStockMessage() {
        return inStockMessage;
    }

    /**
     * Sets the value of the inStockMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInStockMessage(String value) {
        this.inStockMessage = value;
    }

    /**
     * Gets the value of the maxOrderQty property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMaxOrderQty() {
        return maxOrderQty;
    }

    /**
     * Sets the value of the maxOrderQty property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMaxOrderQty(Integer value) {
        this.maxOrderQty = value;
    }

    /**
     * Gets the value of the manufacturerPartNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getManufacturerPartNumber() {
        return manufacturerPartNumber;
    }

    /**
     * Sets the value of the manufacturerPartNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setManufacturerPartNumber(String value) {
        this.manufacturerPartNumber = value;
    }

    /**
     * Gets the value of the minOrderQty property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMinOrderQty() {
        return minOrderQty;
    }

    /**
     * Sets the value of the minOrderQty property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMinOrderQty(Integer value) {
        this.minOrderQty = value;
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
     * Gets the value of the adminNotes property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdminNotes() {
        return adminNotes;
    }

    /**
     * Sets the value of the adminNotes property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdminNotes(String value) {
        this.adminNotes = value;
    }

    /**
     * Gets the value of the outStockMessage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutStockMessage() {
        return outStockMessage;
    }

    /**
     * Sets the value of the outStockMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutStockMessage(String value) {
        this.outStockMessage = value;
    }

    /**
     * Gets the value of the pageUrl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPageUrl() {
        return pageUrl;
    }

    /**
     * Sets the value of the pageUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPageUrl(String value) {
        this.pageUrl = value;
    }

    /**
     * Gets the value of the parentProduct property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isParentProduct() {
        return parentProduct;
    }

    /**
     * Sets the value of the parentProduct property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setParentProduct(Boolean value) {
        this.parentProduct = value;
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
     * Gets the value of the qtyInStock property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getQtyInStock() {
        return qtyInStock;
    }

    /**
     * Sets the value of the qtyInStock property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setQtyInStock(Integer value) {
        this.qtyInStock = value;
    }

    /**
     * Gets the value of the sortOrder property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getSortOrder() {
        return sortOrder;
    }

    /**
     * Sets the value of the sortOrder property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSortOrder(Integer value) {
        this.sortOrder = value;
    }

    /**
     * Gets the value of the updateTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getUpdateTime() {
        return updateTime;
    }

    /**
     * Sets the value of the updateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setUpdateTime(XMLGregorianCalendar value) {
        this.updateTime = value;
    }

    /**
     * Gets the value of the downloadInformation property.
     * 
     * @return
     *     possible object is
     *     {@link DownloadProductType }
     *     
     */
    public DownloadProductType getDownloadInformation() {
        return downloadInformation;
    }

    /**
     * Sets the value of the downloadInformation property.
     * 
     * @param value
     *     allowed object is
     *     {@link DownloadProductType }
     *     
     */
    public void setDownloadInformation(DownloadProductType value) {
        this.downloadInformation = value;
    }

    /**
     * Gets the value of the manufacturer property.
     * 
     * @return
     *     possible object is
     *     {@link ManufacturerType }
     *     
     */
    public ManufacturerType getManufacturer() {
        return manufacturer;
    }

    /**
     * Sets the value of the manufacturer property.
     * 
     * @param value
     *     allowed object is
     *     {@link ManufacturerType }
     *     
     */
    public void setManufacturer(ManufacturerType value) {
        this.manufacturer = value;
    }

    /**
     * Gets the value of the parent property.
     * 
     * @return
     *     possible object is
     *     {@link ProductType }
     *     
     */
    public ProductType getParent() {
        return parent;
    }

    /**
     * Sets the value of the parent property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProductType }
     *     
     */
    public void setParent(ProductType value) {
        this.parent = value;
    }

    /**
     * Gets the value of the price property.
     * 
     * @return
     *     possible object is
     *     {@link ProductPriceType }
     *     
     */
    public ProductPriceType getPrice() {
        return price;
    }

    /**
     * Sets the value of the price property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProductPriceType }
     *     
     */
    public void setPrice(ProductPriceType value) {
        this.price = value;
    }

    /**
     * Gets the value of the searchInformation property.
     * 
     * @return
     *     possible object is
     *     {@link SearchInformationType }
     *     
     */
    public SearchInformationType getSearchInformation() {
        return searchInformation;
    }

    /**
     * Sets the value of the searchInformation property.
     * 
     * @param value
     *     allowed object is
     *     {@link SearchInformationType }
     *     
     */
    public void setSearchInformation(SearchInformationType value) {
        this.searchInformation = value;
    }

    /**
     * Gets the value of the warehouse property.
     * 
     * @return
     *     possible object is
     *     {@link WarehouseType }
     *     
     */
    public WarehouseType getWarehouse() {
        return warehouse;
    }

    /**
     * Sets the value of the warehouse property.
     * 
     * @param value
     *     allowed object is
     *     {@link WarehouseType }
     *     
     */
    public void setWarehouse(WarehouseType value) {
        this.warehouse = value;
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
     * Gets the value of the shippingLabel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShippingLabel() {
        return shippingLabel;
    }

    /**
     * Sets the value of the shippingLabel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShippingLabel(String value) {
        this.shippingLabel = value;
    }

    /**
     * Gets the value of the qtyUnlimited property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isQtyUnlimited() {
        return qtyUnlimited;
    }

    /**
     * Sets the value of the qtyUnlimited property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setQtyUnlimited(Boolean value) {
        this.qtyUnlimited = value;
    }

    /**
     * Gets the value of the categoryList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the categoryList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCategoryList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CategoryType }
     * 
     * 
     */
    public List<CategoryType> getCategoryList() {
        if (categoryList == null) {
            categoryList = new ArrayList<CategoryType>();
        }
        return this.categoryList;
    }

    /**
     * Gets the value of the imageList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the imageList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getImageList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ProductImageType }
     * 
     * 
     */
    public List<ProductImageType> getImageList() {
        if (imageList == null) {
            imageList = new ArrayList<ProductImageType>();
        }
        return this.imageList;
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
     * Gets the value of the relatedProductList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the relatedProductList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRelatedProductList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ProductType }
     * 
     * 
     */
    public List<ProductType> getRelatedProductList() {
        if (relatedProductList == null) {
            relatedProductList = new ArrayList<ProductType>();
        }
        return this.relatedProductList;
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
     * Gets the value of the variationList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the variationList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getVariationList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link VariationGroupType }
     * 
     * 
     */
    public List<VariationGroupType> getVariationList() {
        if (variationList == null) {
            variationList = new ArrayList<VariationGroupType>();
        }
        return this.variationList;
    }

    /**
     * Gets the value of the giftCertificateInformation property.
     * 
     * @return
     *     possible object is
     *     {@link GiftCertificateProductType }
     *     
     */
    public GiftCertificateProductType getGiftCertificateInformation() {
        return giftCertificateInformation;
    }

    /**
     * Sets the value of the giftCertificateInformation property.
     * 
     * @param value
     *     allowed object is
     *     {@link GiftCertificateProductType }
     *     
     */
    public void setGiftCertificateInformation(GiftCertificateProductType value) {
        this.giftCertificateInformation = value;
    }

    /**
     * Gets the value of the qtyReorder property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getQtyReorder() {
        return qtyReorder;
    }

    /**
     * Sets the value of the qtyReorder property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setQtyReorder(Integer value) {
        this.qtyReorder = value;
    }

    /**
     * Gets the value of the productAttributeList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the productAttributeList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProductAttributeList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AttributeType }
     * 
     * 
     */
    public List<AttributeType> getProductAttributeList() {
        if (productAttributeList == null) {
            productAttributeList = new ArrayList<AttributeType>();
        }
        return this.productAttributeList;
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
