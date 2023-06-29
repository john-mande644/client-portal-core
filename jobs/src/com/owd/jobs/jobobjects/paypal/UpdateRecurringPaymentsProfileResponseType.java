package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for UpdateRecurringPaymentsProfileResponseType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="UpdateRecurringPaymentsProfileResponseType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:ebay:apis:eBLBaseComponents}AbstractResponseType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element ref="{urn:ebay:apis:eBLBaseComponents}UpdateRecurringPaymentsProfileResponseDetails"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UpdateRecurringPaymentsProfileResponseType", propOrder = {
        "updateRecurringPaymentsProfileResponseDetails"
})
public class UpdateRecurringPaymentsProfileResponseType
        extends AbstractResponseType
{

    @XmlElement(name = "UpdateRecurringPaymentsProfileResponseDetails", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected UpdateRecurringPaymentsProfileResponseDetailsType updateRecurringPaymentsProfileResponseDetails;

    /**
     * Gets the value of the updateRecurringPaymentsProfileResponseDetails property.
     *
     * @return possible object is
     *         {@link UpdateRecurringPaymentsProfileResponseDetailsType }
     */
    public UpdateRecurringPaymentsProfileResponseDetailsType getUpdateRecurringPaymentsProfileResponseDetails()
    {
        return updateRecurringPaymentsProfileResponseDetails;
    }

    /**
     * Sets the value of the updateRecurringPaymentsProfileResponseDetails property.
     *
     * @param value allowed object is
     *              {@link UpdateRecurringPaymentsProfileResponseDetailsType }
     */
    public void setUpdateRecurringPaymentsProfileResponseDetails(UpdateRecurringPaymentsProfileResponseDetailsType value)
    {
        this.updateRecurringPaymentsProfileResponseDetails = value;
    }

}
