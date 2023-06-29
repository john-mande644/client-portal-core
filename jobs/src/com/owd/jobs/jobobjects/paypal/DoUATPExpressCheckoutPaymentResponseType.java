package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DoUATPExpressCheckoutPaymentResponseType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="DoUATPExpressCheckoutPaymentResponseType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:ebay:api:PayPalAPI}DoExpressCheckoutPaymentResponseType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element ref="{urn:ebay:apis:eBLBaseComponents}UATPDetails"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DoUATPExpressCheckoutPaymentResponseType", propOrder = {
        "uatpDetails"
})
public class DoUATPExpressCheckoutPaymentResponseType
        extends DoExpressCheckoutPaymentResponseType
{

    @XmlElement(name = "UATPDetails", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected UATPDetailsType uatpDetails;

    /**
     * Gets the value of the uatpDetails property.
     *
     * @return possible object is
     *         {@link UATPDetailsType }
     */
    public UATPDetailsType getUATPDetails()
    {
        return uatpDetails;
    }

    /**
     * Sets the value of the uatpDetails property.
     *
     * @param value allowed object is
     *              {@link UATPDetailsType }
     */
    public void setUATPDetails(UATPDetailsType value)
    {
        this.uatpDetails = value;
    }

}
