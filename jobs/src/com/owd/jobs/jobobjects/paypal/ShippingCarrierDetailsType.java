package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ShippingCarrierDetailsType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="ShippingCarrierDetailsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="CarrierShippingFee" type="{urn:ebay:apis:CoreComponentTypes}AmountType" minOccurs="0"/>
 *         &lt;element ref="{urn:ebay:apis:eBLBaseComponents}InsuranceFee" minOccurs="0"/>
 *         &lt;element ref="{urn:ebay:apis:eBLBaseComponents}InsuranceOption" minOccurs="0"/>
 *         &lt;element name="PackagingHandlingCosts" type="{urn:ebay:apis:CoreComponentTypes}AmountType" minOccurs="0"/>
 *         &lt;element name="ShippingRateErrorMessage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element ref="{urn:ebay:apis:eBLBaseComponents}ShippingService"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ShippingCarrierDetailsType", namespace = "urn:ebay:apis:eBLBaseComponents", propOrder = {
        "carrierShippingFee",
        "insuranceFee",
        "insuranceOption",
        "packagingHandlingCosts",
        "shippingRateErrorMessage",
        "shippingService"
})
public class ShippingCarrierDetailsType
{

    @XmlElement(name = "CarrierShippingFee", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected AmountType carrierShippingFee;
    @XmlElement(name = "InsuranceFee", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected AmountType insuranceFee;
    @XmlElement(name = "InsuranceOption", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected InsuranceOptionCodeType insuranceOption;
    @XmlElement(name = "PackagingHandlingCosts", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected AmountType packagingHandlingCosts;
    @XmlElement(name = "ShippingRateErrorMessage", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String shippingRateErrorMessage;
    @XmlElement(name = "ShippingService", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected ShippingServiceCodeType shippingService;

    /**
     * Gets the value of the carrierShippingFee property.
     *
     * @return possible object is
     *         {@link AmountType }
     */
    public AmountType getCarrierShippingFee()
    {
        return carrierShippingFee;
    }

    /**
     * Sets the value of the carrierShippingFee property.
     *
     * @param value allowed object is
     *              {@link AmountType }
     */
    public void setCarrierShippingFee(AmountType value)
    {
        this.carrierShippingFee = value;
    }

    /**
     * Gets the value of the insuranceFee property.
     *
     * @return possible object is
     *         {@link AmountType }
     */
    public AmountType getInsuranceFee()
    {
        return insuranceFee;
    }

    /**
     * Sets the value of the insuranceFee property.
     *
     * @param value allowed object is
     *              {@link AmountType }
     */
    public void setInsuranceFee(AmountType value)
    {
        this.insuranceFee = value;
    }

    /**
     * Gets the value of the insuranceOption property.
     *
     * @return possible object is
     *         {@link InsuranceOptionCodeType }
     */
    public InsuranceOptionCodeType getInsuranceOption()
    {
        return insuranceOption;
    }

    /**
     * Sets the value of the insuranceOption property.
     *
     * @param value allowed object is
     *              {@link InsuranceOptionCodeType }
     */
    public void setInsuranceOption(InsuranceOptionCodeType value)
    {
        this.insuranceOption = value;
    }

    /**
     * Gets the value of the packagingHandlingCosts property.
     *
     * @return possible object is
     *         {@link AmountType }
     */
    public AmountType getPackagingHandlingCosts()
    {
        return packagingHandlingCosts;
    }

    /**
     * Sets the value of the packagingHandlingCosts property.
     *
     * @param value allowed object is
     *              {@link AmountType }
     */
    public void setPackagingHandlingCosts(AmountType value)
    {
        this.packagingHandlingCosts = value;
    }

    /**
     * Gets the value of the shippingRateErrorMessage property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getShippingRateErrorMessage()
    {
        return shippingRateErrorMessage;
    }

    /**
     * Sets the value of the shippingRateErrorMessage property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setShippingRateErrorMessage(String value)
    {
        this.shippingRateErrorMessage = value;
    }

    /**
     * is unique identified of shipping carrier, without this element the whole node makes no sence
     *
     * @return possible object is
     *         {@link ShippingServiceCodeType }
     */
    public ShippingServiceCodeType getShippingService()
    {
        return shippingService;
    }

    /**
     * is unique identified of shipping carrier, without this element the whole node makes no sence
     *
     * @param value allowed object is
     *              {@link ShippingServiceCodeType }
     */
    public void setShippingService(ShippingServiceCodeType value)
    {
        this.shippingService = value;
    }

}
