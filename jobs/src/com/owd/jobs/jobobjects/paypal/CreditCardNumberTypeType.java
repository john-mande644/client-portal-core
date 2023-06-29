package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CreditCardNumberTypeType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="CreditCardNumberTypeType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="CreditCardType" type="{urn:ebay:apis:eBLBaseComponents}CreditCardTypeType" minOccurs="0"/>
 *         &lt;element name="CreditCardNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CreditCardNumberTypeType", namespace = "urn:ebay:apis:eBLBaseComponents", propOrder = {
        "creditCardType",
        "creditCardNumber"
})
public class CreditCardNumberTypeType
{

    @XmlElement(name = "CreditCardType", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected CreditCardTypeType creditCardType;
    @XmlElement(name = "CreditCardNumber", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String creditCardNumber;

    /**
     * Gets the value of the creditCardType property.
     *
     * @return possible object is
     *         {@link CreditCardTypeType }
     */
    public CreditCardTypeType getCreditCardType()
    {
        return creditCardType;
    }

    /**
     * Sets the value of the creditCardType property.
     *
     * @param value allowed object is
     *              {@link CreditCardTypeType }
     */
    public void setCreditCardType(CreditCardTypeType value)
    {
        this.creditCardType = value;
    }

    /**
     * Gets the value of the creditCardNumber property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getCreditCardNumber()
    {
        return creditCardNumber;
    }

    /**
     * Sets the value of the creditCardNumber property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setCreditCardNumber(String value)
    {
        this.creditCardNumber = value;
    }

}
