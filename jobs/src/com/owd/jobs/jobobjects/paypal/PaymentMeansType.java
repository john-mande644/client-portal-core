package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PaymentMeansType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="PaymentMeansType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="TypeCodeID" type="{urn:ebay:apis:eBLBaseComponents}SellerPaymentMethodCodeType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PaymentMeansType", namespace = "urn:ebay:apis:eBLBaseComponents", propOrder = {
        "typeCodeID"
})
public class PaymentMeansType
{

    @XmlElement(name = "TypeCodeID", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected SellerPaymentMethodCodeType typeCodeID;

    /**
     * Gets the value of the typeCodeID property.
     *
     * @return possible object is
     *         {@link SellerPaymentMethodCodeType }
     */
    public SellerPaymentMethodCodeType getTypeCodeID()
    {
        return typeCodeID;
    }

    /**
     * Sets the value of the typeCodeID property.
     *
     * @param value allowed object is
     *              {@link SellerPaymentMethodCodeType }
     */
    public void setTypeCodeID(SellerPaymentMethodCodeType value)
    {
        this.typeCodeID = value;
    }

}
