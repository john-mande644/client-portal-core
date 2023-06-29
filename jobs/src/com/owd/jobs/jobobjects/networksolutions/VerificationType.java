
package com.owd.jobs.jobobjects.networksolutions;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VerificationType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VerificationType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="Card" type="{urn:networksolutions:apis}VerificationCodeType" minOccurs="0"/>
 *         &lt;element name="PostalCode" type="{urn:networksolutions:apis}VerificationCodeType" minOccurs="0"/>
 *         &lt;element name="Street" type="{urn:networksolutions:apis}VerificationCodeType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VerificationType", propOrder = {
    "card",
    "postalCode",
    "street"
})
public class VerificationType {

    @XmlElement(name = "Card")
    protected VerificationCodeType card;
    @XmlElement(name = "PostalCode")
    protected VerificationCodeType postalCode;
    @XmlElement(name = "Street")
    protected VerificationCodeType street;

    /**
     * Gets the value of the card property.
     * 
     * @return
     *     possible object is
     *     {@link VerificationCodeType }
     *     
     */
    public VerificationCodeType getCard() {
        return card;
    }

    /**
     * Sets the value of the card property.
     * 
     * @param value
     *     allowed object is
     *     {@link VerificationCodeType }
     *     
     */
    public void setCard(VerificationCodeType value) {
        this.card = value;
    }

    /**
     * Gets the value of the postalCode property.
     * 
     * @return
     *     possible object is
     *     {@link VerificationCodeType }
     *     
     */
    public VerificationCodeType getPostalCode() {
        return postalCode;
    }

    /**
     * Sets the value of the postalCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link VerificationCodeType }
     *     
     */
    public void setPostalCode(VerificationCodeType value) {
        this.postalCode = value;
    }

    /**
     * Gets the value of the street property.
     * 
     * @return
     *     possible object is
     *     {@link VerificationCodeType }
     *     
     */
    public VerificationCodeType getStreet() {
        return street;
    }

    /**
     * Sets the value of the street property.
     * 
     * @param value
     *     allowed object is
     *     {@link VerificationCodeType }
     *     
     */
    public void setStreet(VerificationCodeType value) {
        this.street = value;
    }

}
