package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for CalculatedShippingRateType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="CalculatedShippingRateType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="OriginatingPostalCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ShippingIrregular" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="CarrierDetails" type="{urn:ebay:apis:eBLBaseComponents}ShippingCarrierDetailsType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="ShippingPackage" type="{urn:ebay:apis:eBLBaseComponents}ShippingPackageCodeType" minOccurs="0"/>
 *         &lt;element name="WeightMajor" type="{urn:ebay:apis:CoreComponentTypes}MeasureType" minOccurs="0"/>
 *         &lt;element name="WeightMinor" type="{urn:ebay:apis:CoreComponentTypes}MeasureType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CalculatedShippingRateType", namespace = "urn:ebay:apis:eBLBaseComponents", propOrder = {
        "originatingPostalCode",
        "shippingIrregular",
        "carrierDetails",
        "shippingPackage",
        "weightMajor",
        "weightMinor"
})
public class CalculatedShippingRateType
{

    @XmlElement(name = "OriginatingPostalCode", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String originatingPostalCode;
    @XmlElement(name = "ShippingIrregular", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected Boolean shippingIrregular;
    @XmlElement(name = "CarrierDetails", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected List<ShippingCarrierDetailsType> carrierDetails;
    @XmlElement(name = "ShippingPackage", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected ShippingPackageCodeType shippingPackage;
    @XmlElement(name = "WeightMajor", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected MeasureType weightMajor;
    @XmlElement(name = "WeightMinor", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected MeasureType weightMinor;

    /**
     * Gets the value of the originatingPostalCode property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getOriginatingPostalCode()
    {
        return originatingPostalCode;
    }

    /**
     * Sets the value of the originatingPostalCode property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setOriginatingPostalCode(String value)
    {
        this.originatingPostalCode = value;
    }

    /**
     * Gets the value of the shippingIrregular property.
     *
     * @return possible object is
     *         {@link Boolean }
     */
    public Boolean isShippingIrregular()
    {
        return shippingIrregular;
    }

    /**
     * Sets the value of the shippingIrregular property.
     *
     * @param value allowed object is
     *              {@link Boolean }
     */
    public void setShippingIrregular(Boolean value)
    {
        this.shippingIrregular = value;
    }

    /**
     * Gets the value of the carrierDetails property.
     * <p/>
     * <p/>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the carrierDetails property.
     * <p/>
     * <p/>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCarrierDetails().add(newItem);
     * </pre>
     * <p/>
     * <p/>
     * <p/>
     * Objects of the following type(s) are allowed in the list
     * {@link ShippingCarrierDetailsType }
     */
    public List<ShippingCarrierDetailsType> getCarrierDetails()
    {
        if (carrierDetails == null)
        {
            carrierDetails = new ArrayList<ShippingCarrierDetailsType>();
        }
        return this.carrierDetails;
    }

    /**
     * Gets the value of the shippingPackage property.
     *
     * @return possible object is
     *         {@link ShippingPackageCodeType }
     */
    public ShippingPackageCodeType getShippingPackage()
    {
        return shippingPackage;
    }

    /**
     * Sets the value of the shippingPackage property.
     *
     * @param value allowed object is
     *              {@link ShippingPackageCodeType }
     */
    public void setShippingPackage(ShippingPackageCodeType value)
    {
        this.shippingPackage = value;
    }

    /**
     * Gets the value of the weightMajor property.
     *
     * @return possible object is
     *         {@link MeasureType }
     */
    public MeasureType getWeightMajor()
    {
        return weightMajor;
    }

    /**
     * Sets the value of the weightMajor property.
     *
     * @param value allowed object is
     *              {@link MeasureType }
     */
    public void setWeightMajor(MeasureType value)
    {
        this.weightMajor = value;
    }

    /**
     * Gets the value of the weightMinor property.
     *
     * @return possible object is
     *         {@link MeasureType }
     */
    public MeasureType getWeightMinor()
    {
        return weightMinor;
    }

    /**
     * Sets the value of the weightMinor property.
     *
     * @param value allowed object is
     *              {@link MeasureType }
     */
    public void setWeightMinor(MeasureType value)
    {
        this.weightMinor = value;
    }

}
