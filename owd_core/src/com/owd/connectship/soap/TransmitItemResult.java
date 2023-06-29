
package com.owd.connectship.soap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Represents the result of a single transmit operation.
 * 
 * <p>Java class for TransmitItemResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TransmitItemResult">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="code" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="message" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="transmitItem" type="{urn:connectship-com:ampcore}Identity"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TransmitItemResult", propOrder = {
    "code",
    "message",
    "transmitItem"
})
public class TransmitItemResult {

    protected int code;
    @XmlElement(required = true)
    protected String message;
    @XmlElement(required = true)
    protected Identity transmitItem;

    /**
     * Gets the value of the code property.
     * 
     */
    public int getCode() {
        return code;
    }

    /**
     * Sets the value of the code property.
     * 
     */
    public void setCode(int value) {
        this.code = value;
    }

    /**
     * Gets the value of the message property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the value of the message property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessage(String value) {
        this.message = value;
    }

    /**
     * Gets the value of the transmitItem property.
     * 
     * @return
     *     possible object is
     *     {@link Identity }
     *     
     */
    public Identity getTransmitItem() {
        return transmitItem;
    }

    /**
     * Sets the value of the transmitItem property.
     * 
     * @param value
     *     allowed object is
     *     {@link Identity }
     *     
     */
    public void setTransmitItem(Identity value) {
        this.transmitItem = value;
    }

}
