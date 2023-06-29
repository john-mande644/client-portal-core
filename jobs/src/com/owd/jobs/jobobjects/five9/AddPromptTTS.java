
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for addPromptTTS complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="addPromptTTS">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="prompt" type="{http://service.admin.ws.five9.com/v2/}promptInfo" minOccurs="0"/>
 *         &lt;element name="ttsInfo" type="{http://service.admin.ws.five9.com/v2/}ttsInfo" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "addPromptTTS", propOrder = {
    "prompt",
    "ttsInfo"
})
public class AddPromptTTS {

    protected PromptInfo prompt;
    protected TtsInfo ttsInfo;

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
     * Gets the value of the ttsInfo property.
     * 
     * @return
     *     possible object is
     *     {@link TtsInfo }
     *     
     */
    public TtsInfo getTtsInfo() {
        return ttsInfo;
    }

    /**
     * Sets the value of the ttsInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link TtsInfo }
     *     
     */
    public void setTtsInfo(TtsInfo value) {
        this.ttsInfo = value;
    }

}
