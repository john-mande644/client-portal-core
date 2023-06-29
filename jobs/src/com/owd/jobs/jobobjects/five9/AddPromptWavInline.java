
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.activation.DataHandler;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlMimeType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for addPromptWavInline complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="addPromptWavInline">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="prompt" type="{http://service.admin.ws.five9.com/v2/}promptInfo" minOccurs="0"/>
 *         &lt;element name="wavFile" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "addPromptWavInline", propOrder = {
    "prompt",
    "wavFile"
})
public class AddPromptWavInline {

    protected PromptInfo prompt;
    @XmlMimeType("application/octet-stream")
    protected DataHandler wavFile;

    /**
     * Gets the value of the prompt property.
     * 
     * @return
     *     possible object is
     *     {@link PromptInfo }
     *     
     */
    public PromptInfo getPrompt() {
        return prompt;
    }

    /**
     * Sets the value of the prompt property.
     * 
     * @param value
     *     allowed object is
     *     {@link PromptInfo }
     *     
     */
    public void setPrompt(PromptInfo value) {
        this.prompt = value;
    }

    /**
     * Gets the value of the wavFile property.
     * 
     * @return
     *     possible object is
     *     {@link DataHandler }
     *     
     */
    public DataHandler getWavFile() {
        return wavFile;
    }

    /**
     * Sets the value of the wavFile property.
     * 
     * @param value
     *     allowed object is
     *     {@link DataHandler }
     *     
     */
    public void setWavFile(DataHandler value) {
        this.wavFile = value;
    }

}
