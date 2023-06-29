
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for WrongPromptTypeException complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="WrongPromptTypeException">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="actualType" type="{http://service.admin.ws.five9.com/v2/}promptType" minOccurs="0"/>
 *         &lt;element name="desiredType" type="{http://service.admin.ws.five9.com/v2/}promptType" minOccurs="0"/>
 *         &lt;element name="message" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="promptName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "WrongPromptTypeException", propOrder = {
    "actualType",
    "desiredType",
    "message",
    "promptName"
})
public class WrongPromptTypeException {

    protected PromptType actualType;
    protected PromptType desiredType;
    protected String message;
    protected String promptName;

    /**
     * Gets the value of the actualType property.
     * 
     * @return
     *     possible object is
     *     {@link PromptType }
     *     
     */
    public PromptType getActualType() {
        return actualType;
    }

    /**
     * Sets the value of the actualType property.
     * 
     * @param value
     *     allowed object is
     *     {@link PromptType }
     *     
     */
    public void setActualType(PromptType value) {
        this.actualType = value;
    }

    /**
     * Gets the value of the desiredType property.
     * 
     * @return
     *     possible object is
     *     {@link PromptType }
     *     
     */
    public PromptType getDesiredType() {
        return desiredType;
    }

    /**
     * Sets the value of the desiredType property.
     * 
     * @param value
     *     allowed object is
     *     {@link PromptType }
     *     
     */
    public void setDesiredType(PromptType value) {
        this.desiredType = value;
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
     * Gets the value of the promptName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPromptName() {
        return promptName;
    }

    /**
     * Sets the value of the promptName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPromptName(String value) {
        this.promptName = value;
    }

}
