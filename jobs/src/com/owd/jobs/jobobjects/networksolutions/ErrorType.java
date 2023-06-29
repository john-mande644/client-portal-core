
package com.owd.jobs.jobobjects.networksolutions;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ErrorType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ErrorType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="Message" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Number" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="Severity" type="{urn:networksolutions:apis}SeverityCodeType" minOccurs="0"/>
 *         &lt;element name="FieldInfo" type="{urn:networksolutions:apis}FieldInfoType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ErrorType", propOrder = {
    "message",
    "number",
    "severity",
    "fieldInfo"
})
public class ErrorType {

    @XmlElement(name = "Message")
    protected String message;
    @XmlElement(name = "Number")
    protected Integer number;
    @XmlElement(name = "Severity")
    protected SeverityCodeType severity;
    @XmlElement(name = "FieldInfo")
    protected FieldInfoType fieldInfo;

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
     * Gets the value of the number property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumber() {
        return number;
    }

    /**
     * Sets the value of the number property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumber(Integer value) {
        this.number = value;
    }

    /**
     * Gets the value of the severity property.
     * 
     * @return
     *     possible object is
     *     {@link SeverityCodeType }
     *     
     */
    public SeverityCodeType getSeverity() {
        return severity;
    }

    /**
     * Sets the value of the severity property.
     * 
     * @param value
     *     allowed object is
     *     {@link SeverityCodeType }
     *     
     */
    public void setSeverity(SeverityCodeType value) {
        this.severity = value;
    }

    /**
     * Gets the value of the fieldInfo property.
     * 
     * @return
     *     possible object is
     *     {@link FieldInfoType }
     *     
     */
    public FieldInfoType getFieldInfo() {
        return fieldInfo;
    }

    /**
     * Sets the value of the fieldInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldInfoType }
     *     
     */
    public void setFieldInfo(FieldInfoType value) {
        this.fieldInfo = value;
    }

}
