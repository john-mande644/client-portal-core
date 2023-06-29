
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ttsInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ttsInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="language" type="{http://service.admin.ws.five9.com/v2/}language" minOccurs="0"/>
 *         &lt;element name="sayAs" type="{http://service.admin.ws.five9.com/v2/}sayAs" minOccurs="0"/>
 *         &lt;element name="sayAsFormat" type="{http://service.admin.ws.five9.com/v2/}sayAsFormat" minOccurs="0"/>
 *         &lt;element name="text" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ttsInfo", propOrder = {
    "language",
    "sayAs",
    "sayAsFormat",
    "text"
})
public class TtsInfo {

    protected Language language;
    protected SayAs sayAs;
    protected SayAsFormat sayAsFormat;
    protected String text;

    /**
     * Gets the value of the language property.
     * 
     * @return
     *     possible object is
     *     {@link Language }
     *     
     */
    public Language getLanguage() {
        return language;
    }

    /**
     * Sets the value of the language property.
     * 
     * @param value
     *     allowed object is
     *     {@link Language }
     *     
     */
    public void setLanguage(Language value) {
        this.language = value;
    }

    /**
     * Gets the value of the sayAs property.
     * 
     * @return
     *     possible object is
     *     {@link SayAs }
     *     
     */
    public SayAs getSayAs() {
        return sayAs;
    }

    /**
     * Sets the value of the sayAs property.
     * 
     * @param value
     *     allowed object is
     *     {@link SayAs }
     *     
     */
    public void setSayAs(SayAs value) {
        this.sayAs = value;
    }

    /**
     * Gets the value of the sayAsFormat property.
     * 
     * @return
     *     possible object is
     *     {@link SayAsFormat }
     *     
     */
    public SayAsFormat getSayAsFormat() {
        return sayAsFormat;
    }

    /**
     * Sets the value of the sayAsFormat property.
     * 
     * @param value
     *     allowed object is
     *     {@link SayAsFormat }
     *     
     */
    public void setSayAsFormat(SayAsFormat value) {
        this.sayAsFormat = value;
    }

    /**
     * Gets the value of the text property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the value of the text property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setText(String value) {
        this.text = value;
    }

}
