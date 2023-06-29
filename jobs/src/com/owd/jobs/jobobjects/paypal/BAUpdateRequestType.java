package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BAUpdateRequestType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="BAUpdateRequestType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:ebay:apis:eBLBaseComponents}AbstractRequestType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="ReferenceID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="BillingAgreementDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BillingAgreementStatus" type="{urn:ebay:apis:eBLBaseComponents}MerchantPullStatusCodeType" minOccurs="0"/>
 *         &lt;element name="BillingAgreementCustom" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BAUpdateRequestType", propOrder = {
        "referenceID",
        "billingAgreementDescription",
        "billingAgreementStatus",
        "billingAgreementCustom"
})
public class BAUpdateRequestType
        extends AbstractRequestType
{

    @XmlElement(name = "ReferenceID", required = true)
    protected String referenceID;
    @XmlElement(name = "BillingAgreementDescription")
    protected String billingAgreementDescription;
    @XmlElement(name = "BillingAgreementStatus")
    protected MerchantPullStatusCodeType billingAgreementStatus;
    @XmlElement(name = "BillingAgreementCustom")
    protected String billingAgreementCustom;

    /**
     * Gets the value of the referenceID property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getReferenceID()
    {
        return referenceID;
    }

    /**
     * Sets the value of the referenceID property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setReferenceID(String value)
    {
        this.referenceID = value;
    }

    /**
     * Gets the value of the billingAgreementDescription property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getBillingAgreementDescription()
    {
        return billingAgreementDescription;
    }

    /**
     * Sets the value of the billingAgreementDescription property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setBillingAgreementDescription(String value)
    {
        this.billingAgreementDescription = value;
    }

    /**
     * Gets the value of the billingAgreementStatus property.
     *
     * @return possible object is
     *         {@link MerchantPullStatusCodeType }
     */
    public MerchantPullStatusCodeType getBillingAgreementStatus()
    {
        return billingAgreementStatus;
    }

    /**
     * Sets the value of the billingAgreementStatus property.
     *
     * @param value allowed object is
     *              {@link MerchantPullStatusCodeType }
     */
    public void setBillingAgreementStatus(MerchantPullStatusCodeType value)
    {
        this.billingAgreementStatus = value;
    }

    /**
     * Gets the value of the billingAgreementCustom property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getBillingAgreementCustom()
    {
        return billingAgreementCustom;
    }

    /**
     * Sets the value of the billingAgreementCustom property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setBillingAgreementCustom(String value)
    {
        this.billingAgreementCustom = value;
    }

}
