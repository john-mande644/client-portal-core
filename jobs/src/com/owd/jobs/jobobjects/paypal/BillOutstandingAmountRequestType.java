package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BillOutstandingAmountRequestType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="BillOutstandingAmountRequestType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:ebay:apis:eBLBaseComponents}AbstractRequestType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element ref="{urn:ebay:apis:eBLBaseComponents}BillOutstandingAmountRequestDetails"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BillOutstandingAmountRequestType", propOrder = {
        "billOutstandingAmountRequestDetails"
})
public class BillOutstandingAmountRequestType
        extends AbstractRequestType
{

    @XmlElement(name = "BillOutstandingAmountRequestDetails", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected BillOutstandingAmountRequestDetailsType billOutstandingAmountRequestDetails;

    /**
     * Gets the value of the billOutstandingAmountRequestDetails property.
     *
     * @return possible object is
     *         {@link BillOutstandingAmountRequestDetailsType }
     */
    public BillOutstandingAmountRequestDetailsType getBillOutstandingAmountRequestDetails()
    {
        return billOutstandingAmountRequestDetails;
    }

    /**
     * Sets the value of the billOutstandingAmountRequestDetails property.
     *
     * @param value allowed object is
     *              {@link BillOutstandingAmountRequestDetailsType }
     */
    public void setBillOutstandingAmountRequestDetails(BillOutstandingAmountRequestDetailsType value)
    {
        this.billOutstandingAmountRequestDetails = value;
    }

}
