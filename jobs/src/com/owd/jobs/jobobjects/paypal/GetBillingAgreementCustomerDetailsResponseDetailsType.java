package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GetBillingAgreementCustomerDetailsResponseDetailsType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="GetBillingAgreementCustomerDetailsResponseDetailsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="PayerInfo" type="{urn:ebay:apis:eBLBaseComponents}PayerInfoType"/>
 *         &lt;element name="BillingAddress" type="{urn:ebay:apis:eBLBaseComponents}AddressType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetBillingAgreementCustomerDetailsResponseDetailsType", namespace = "urn:ebay:apis:eBLBaseComponents", propOrder = {
        "payerInfo",
        "billingAddress"
})
public class GetBillingAgreementCustomerDetailsResponseDetailsType
{

    @XmlElement(name = "PayerInfo", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected PayerInfoType payerInfo;
    @XmlElement(name = "BillingAddress", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected AddressType billingAddress;

    /**
     * Gets the value of the payerInfo property.
     *
     * @return possible object is
     *         {@link PayerInfoType }
     */
    public PayerInfoType getPayerInfo()
    {
        return payerInfo;
    }

    /**
     * Sets the value of the payerInfo property.
     *
     * @param value allowed object is
     *              {@link PayerInfoType }
     */
    public void setPayerInfo(PayerInfoType value)
    {
        this.payerInfo = value;
    }

    /**
     * Gets the value of the billingAddress property.
     *
     * @return possible object is
     *         {@link AddressType }
     */
    public AddressType getBillingAddress()
    {
        return billingAddress;
    }

    /**
     * Sets the value of the billingAddress property.
     *
     * @param value allowed object is
     *              {@link AddressType }
     */
    public void setBillingAddress(AddressType value)
    {
        this.billingAddress = value;
    }

}
