
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.activation.DataHandler;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlMimeType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for setSkillVoicemailGreeting complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="setSkillVoicemailGreeting">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="skillName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "setSkillVoicemailGreeting", propOrder = {
    "skillName",
    "wavFile"
})
public class SetSkillVoicemailGreeting {

    protected String skillName;
    @XmlMimeType("application/octet-stream")
    protected DataHandler wavFile;

    /**
     * Gets the value of the skillName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSkillName() {
        return skillName;
    }

    /**
     * Sets the value of the skillName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSkillName(String value) {
        this.skillName = value;
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
