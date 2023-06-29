package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GetBillingAgreementCustomerDetailsResponseType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="GetBillingAgreementCustomerDetailsResponseType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:ebay:apis:eBLBaseComponents}AbstractResponseType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element ref="{urn:ebay:apis:eBLBaseComponents}GetBillingAgreementCustomerDetailsResponseDetails"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetBillingAgreementCustomerDetailsResponseType", propOrder = {
        "getBillingAgreementCustomerDetailsResponseDetails"
})
public class GetBillingAgreementCustomerDetailsResponseType
        extends AbstractResponseType
{

    @XmlElement(name = "GetBillingAgreementCustomerDetailsResponseDetails", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected GetBillingAgreementCustomerDetailsResponseDetailsType getBillingAgreementCustomerDetailsResponseDetails;

    /**
     * Gets the value of the getBillingAgreementCustomerDetailsResponseDetails property.
     *
     * @return possible object is
     *         {@link GetBillingAgreementCustomerDetailsResponseDetailsType }
     */
    public GetBillingAgreementCustomerDetailsResponseDetailsType getGetBillingAgreementCustomerDetailsResponseDetails()
    {
        return getBillingAgreementCustomerDetailsResponseDetails;
    }

    /**
     * Sets the value of the getBillingAgreementCustomerDetailsResponseDetails property.
     *
     * @param value allowed object is
     *              {@link GetBillingAgreementCustomerDetailsResponseDetailsType }
     */
    public void setGetBillingAgreementCustomerDetailsResponseDetails(GetBillingAgreementCustomerDetailsResponseDetailsType value)
    {
        this.getBillingAgreementCustomerDetailsResponseDetails = value;
    }

}
