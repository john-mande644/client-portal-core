
package com.owd.connectship.soap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Represents the result of a document output operation.
 * 
 * <p>Java class for DocumentResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DocumentResult">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="code" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="message" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="sourceItem" type="{urn:connectship-com:ampcore}PrintItem" minOccurs="0"/>
 *         &lt;element name="output" type="{urn:connectship-com:ampcore}DocumentOutput" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DocumentResult", propOrder = {
    "code",
    "message",
    "sourceItem",
    "output"
})
public class DocumentResult {

    protected int code;
    @XmlElement(required = true)
    protected String message;
    protected PrintItem sourceItem;
    protected DocumentOutput output;

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
     * Gets the value of the sourceItem property.
     * 
     * @return
     *     possible object is
     *     {@link PrintItem }
     *     
     */
    public PrintItem getSourceItem() {
        return sourceItem;
    }

    /**
     * Sets the value of the sourceItem property.
     * 
     * @param value
     *     allowed object is
     *     {@link PrintItem }
     *     
     */
    public void setSourceItem(PrintItem value) {
        this.sourceItem = value;
    }

    /**
     * Gets the value of the output property.
     * 
     * @return
     *     possible object is
     *     {@link DocumentOutput }
     *     
     */
    public DocumentOutput getOutput() {
        return output;
    }

    /**
     * Sets the value of the output property.
     * 
     * @param value
     *     allowed object is
     *     {@link DocumentOutput }
     *     
     */
    public void setOutput(DocumentOutput value) {
        this.output = value;
    }

}
