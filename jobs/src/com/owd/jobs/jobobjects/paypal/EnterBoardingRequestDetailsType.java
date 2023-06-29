package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EnterBoardingRequestDetailsType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="EnterBoardingRequestDetailsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="ProgramCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ProductList" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="PartnerCustom" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ImageUrl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MarketingCategory" type="{urn:ebay:apis:eBLBaseComponents}MarketingCategoryType" minOccurs="0"/>
 *         &lt;element name="BusinessInfo" type="{urn:ebay:apis:eBLBaseComponents}BusinessInfoType" minOccurs="0"/>
 *         &lt;element name="OwnerInfo" type="{urn:ebay:apis:eBLBaseComponents}BusinessOwnerInfoType" minOccurs="0"/>
 *         &lt;element name="BankAccount" type="{urn:ebay:apis:eBLBaseComponents}BankAccountDetailsType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EnterBoardingRequestDetailsType", namespace = "urn:ebay:apis:eBLBaseComponents", propOrder = {
        "programCode",
        "productList",
        "partnerCustom",
        "imageUrl",
        "marketingCategory",
        "businessInfo",
        "ownerInfo",
        "bankAccount"
})
public class EnterBoardingRequestDetailsType
{

    @XmlElement(name = "ProgramCode", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected String programCode;
    @XmlElement(name = "ProductList", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected String productList;
    @XmlElement(name = "PartnerCustom", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String partnerCustom;
    @XmlElement(name = "ImageUrl", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String imageUrl;
    @XmlElement(name = "MarketingCategory", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected MarketingCategoryType marketingCategory;
    @XmlElement(name = "BusinessInfo", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected BusinessInfoType businessInfo;
    @XmlElement(name = "OwnerInfo", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected BusinessOwnerInfoType ownerInfo;
    @XmlElement(name = "BankAccount", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected BankAccountDetailsType bankAccount;

    /**
     * Gets the value of the programCode property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getProgramCode()
    {
        return programCode;
    }

    /**
     * Sets the value of the programCode property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setProgramCode(String value)
    {
        this.programCode = value;
    }

    /**
     * Gets the value of the productList property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getProductList()
    {
        return productList;
    }

    /**
     * Sets the value of the productList property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setProductList(String value)
    {
        this.productList = value;
    }

    /**
     * Gets the value of the partnerCustom property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getPartnerCustom()
    {
        return partnerCustom;
    }

    /**
     * Sets the value of the partnerCustom property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setPartnerCustom(String value)
    {
        this.partnerCustom = value;
    }

    /**
     * Gets the value of the imageUrl property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getImageUrl()
    {
        return imageUrl;
    }

    /**
     * Sets the value of the imageUrl property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setImageUrl(String value)
    {
        this.imageUrl = value;
    }

    /**
     * Gets the value of the marketingCategory property.
     *
     * @return possible object is
     *         {@link MarketingCategoryType }
     */
    public MarketingCategoryType getMarketingCategory()
    {
        return marketingCategory;
    }

    /**
     * Sets the value of the marketingCategory property.
     *
     * @param value allowed object is
     *              {@link MarketingCategoryType }
     */
    public void setMarketingCategory(MarketingCategoryType value)
    {
        this.marketingCategory = value;
    }

    /**
     * Gets the value of the businessInfo property.
     *
     * @return possible object is
     *         {@link BusinessInfoType }
     */
    public BusinessInfoType getBusinessInfo()
    {
        return businessInfo;
    }

    /**
     * Sets the value of the businessInfo property.
     *
     * @param value allowed object is
     *              {@link BusinessInfoType }
     */
    public void setBusinessInfo(BusinessInfoType value)
    {
        this.businessInfo = value;
    }

    /**
     * Gets the value of the ownerInfo property.
     *
     * @return possible object is
     *         {@link BusinessOwnerInfoType }
     */
    public BusinessOwnerInfoType getOwnerInfo()
    {
        return ownerInfo;
    }

    /**
     * Sets the value of the ownerInfo property.
     *
     * @param value allowed object is
     *              {@link BusinessOwnerInfoType }
     */
    public void setOwnerInfo(BusinessOwnerInfoType value)
    {
        this.ownerInfo = value;
    }

    /**
     * Gets the value of the bankAccount property.
     *
     * @return possible object is
     *         {@link BankAccountDetailsType }
     */
    public BankAccountDetailsType getBankAccount()
    {
        return bankAccount;
    }

    /**
     * Sets the value of the bankAccount property.
     *
     * @param value allowed object is
     *              {@link BankAccountDetailsType }
     */
    public void setBankAccount(BankAccountDetailsType value)
    {
        this.bankAccount = value;
    }

}
