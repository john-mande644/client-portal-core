package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SetCustomerBillingAgreementRequestType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="SetCustomerBillingAgreementRequestType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:ebay:apis:eBLBaseComponents}AbstractRequestType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element ref="{urn:ebay:apis:eBLBaseComponents}SetCustomerBillingAgreementRequestDetails"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SetCustomerBillingAgreementRequestType", propOrder = {
        "setCustomerBillingAgreementRequestDetails"
})
public class SetCustomerBillingAgreementRequestType
        extends AbstractRequestType
{

    @XmlElement(name = "SetCustomerBillingAgreementRequestDetails", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected SetCustomerBillingAgreementRequestDetailsType setCustomerBillingAgreementRequestDetails;

    /**
     * Gets the value of the setCustomerBillingAgreementRequestDetails property.
     *
     * @return possible object is
     *         {@link SetCustomerBillingAgreementRequestDetailsType }
     */
    public SetCustomerBillingAgreementRequestDetailsType getSetCustomerBillingAgreementRequestDetails()
    {
        return setCustomerBillingAgreementRequestDetails;
    }

    /**
     * Sets the value of the setCustomerBillingAgreementRequestDetails property.
     *
     * @param value allowed object is
     *              {@link SetCustomerBillingAgreementRequestDetailsType }
     */
    public void setSetCustomerBillingAgreementRequestDetails(SetCustomerBillingAgreementRequestDetailsType value)
    {
        this.setCustomerBillingAgreementRequestDetails = value;
    }

}
