package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PaymentType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="PaymentType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="PaymentMeans" type="{urn:ebay:apis:eBLBaseComponents}PaymentMeansType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PaymentType", namespace = "urn:ebay:apis:eBLBaseComponents", propOrder = {
        "paymentMeans"
})
public class PaymentType
{

    @XmlElement(name = "PaymentMeans", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected PaymentMeansType paymentMeans;

    /**
     * Gets the value of the paymentMeans property.
     *
     * @return possible object is
     *         {@link PaymentMeansType }
     */
    public PaymentMeansType getPaymentMeans()
    {
        return paymentMeans;
    }

    /**
     * Sets the value of the paymentMeans property.
     *
     * @param value allowed object is
     *              {@link PaymentMeansType }
     */
    public void setPaymentMeans(PaymentMeansType value)
    {
        this.paymentMeans = value;
    }

}
