package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Contains information required To list a business item.
 * BusinessSeller - only for add item, the RestrictedToBusiness and VATPercent for both get and add,
 * for revise all must be optional
 * <p/>
 * <p/>
 * <p>Java class for VATDetailsType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="VATDetailsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="BusinessSeller" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="RestrictedToBusiness" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="VATPercent" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VATDetailsType", namespace = "urn:ebay:apis:eBLBaseComponents", propOrder = {
        "businessSeller",
        "restrictedToBusiness",
        "vatPercent"
})
public class VATDetailsType
{

    @XmlElement(name = "BusinessSeller", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected Boolean businessSeller;
    @XmlElement(name = "RestrictedToBusiness", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected Boolean restrictedToBusiness;
    @XmlElement(name = "VATPercent", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected Float vatPercent;

    /**
     * Gets the value of the businessSeller property.
     *
     * @return possible object is
     *         {@link Boolean }
     */
    public Boolean isBusinessSeller()
    {
        return businessSeller;
    }

    /**
     * Sets the value of the businessSeller property.
     *
     * @param value allowed object is
     *              {@link Boolean }
     */
    public void setBusinessSeller(Boolean value)
    {
        this.businessSeller = value;
    }

    /**
     * Gets the value of the restrictedToBusiness property.
     *
     * @return possible object is
     *         {@link Boolean }
     */
    public Boolean isRestrictedToBusiness()
    {
        return restrictedToBusiness;
    }

    /**
     * Sets the value of the restrictedToBusiness property.
     *
     * @param value allowed object is
     *              {@link Boolean }
     */
    public void setRestrictedToBusiness(Boolean value)
    {
        this.restrictedToBusiness = value;
    }

    /**
     * Gets the value of the vatPercent property.
     *
     * @return possible object is
     *         {@link Float }
     */
    public Float getVATPercent()
    {
        return vatPercent;
    }

    /**
     * Sets the value of the vatPercent property.
     *
     * @param value allowed object is
     *              {@link Float }
     */
    public void setVATPercent(Float value)
    {
        this.vatPercent = value;
    }

}
