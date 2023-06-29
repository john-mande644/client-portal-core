package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.*;


/**
 * <p>Java class for DoExpressCheckoutPaymentResponseType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="DoExpressCheckoutPaymentResponseType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:ebay:apis:eBLBaseComponents}AbstractResponseType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element ref="{urn:ebay:apis:eBLBaseComponents}DoExpressCheckoutPaymentResponseDetails"/>
 *         &lt;element name="FMFDetails" type="{urn:ebay:apis:eBLBaseComponents}FMFDetailsType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DoExpressCheckoutPaymentResponseType", propOrder = {
        "doExpressCheckoutPaymentResponseDetails",
        "fmfDetails"
})
@XmlSeeAlso({
        DoUATPExpressCheckoutPaymentResponseType.class
})
public class DoExpressCheckoutPaymentResponseType
        extends AbstractResponseType
{

    @XmlElement(name = "DoExpressCheckoutPaymentResponseDetails", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected DoExpressCheckoutPaymentResponseDetailsType doExpressCheckoutPaymentResponseDetails;
    @XmlElement(name = "FMFDetails")
    protected FMFDetailsType fmfDetails;

    /**
     * Gets the value of the doExpressCheckoutPaymentResponseDetails property.
     *
     * @return possible object is
     *         {@link DoExpressCheckoutPaymentResponseDetailsType }
     */
    public DoExpressCheckoutPaymentResponseDetailsType getDoExpressCheckoutPaymentResponseDetails()
    {
        return doExpressCheckoutPaymentResponseDetails;
    }

    /**
     * Sets the value of the doExpressCheckoutPaymentResponseDetails property.
     *
     * @param value allowed object is
     *              {@link DoExpressCheckoutPaymentResponseDetailsType }
     */
    public void setDoExpressCheckoutPaymentResponseDetails(DoExpressCheckoutPaymentResponseDetailsType value)
    {
        this.doExpressCheckoutPaymentResponseDetails = value;
    }

    /**
     * Gets the value of the fmfDetails property.
     *
     * @return possible object is
     *         {@link FMFDetailsType }
     */
    public FMFDetailsType getFMFDetails()
    {
        return fmfDetails;
    }

    /**
     * Sets the value of the fmfDetails property.
     *
     * @param value allowed object is
     *              {@link FMFDetailsType }
     */
    public void setFMFDetails(FMFDetailsType value)
    {
        this.fmfDetails = value;
    }

}
