
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for miscVccOptions complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="miscVccOptions">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="defaultCampaign" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="enableReasonCodes" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="internalCallTimeout" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="maySelectCampaign" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="maySelectNone" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="showDialAttempts" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="voicemailTimeout" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "miscVccOptions", propOrder = {
    "defaultCampaign",
    "enableReasonCodes",
    "internalCallTimeout",
    "maySelectCampaign",
    "maySelectNone",
    "showDialAttempts",
    "voicemailTimeout"
})
public class MiscVccOptions {

    protected String defaultCampaign;
    protected Boolean enableReasonCodes;
    protected Integer internalCallTimeout;
    protected Boolean maySelectCampaign;
    protected Boolean maySelectNone;
    protected Boolean showDialAttempts;
    protected Integer voicemailTimeout;

    /**
     * Gets the value of the defaultCampaign property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDefaultCampaign() {
        return defaultCampaign;
    }

    /**
     * Sets the value of the defaultCampaign property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDefaultCampaign(String value) {
        this.defaultCampaign = value;
    }

    /**
     * Gets the value of the enableReasonCodes property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isEnableReasonCodes() {
        return enableReasonCodes;
    }

    /**
     * Sets the value of the enableReasonCodes property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setEnableReasonCodes(Boolean value) {
        this.enableReasonCodes = value;
    }

    /**
     * Gets the value of the internalCallTimeout property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getInternalCallTimeout() {
        return internalCallTimeout;
    }

    /**
     * Sets the value of the internalCallTimeout property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setInternalCallTimeout(Integer value) {
        this.internalCallTimeout = value;
    }

    /**
     * Gets the value of the maySelectCampaign property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isMaySelectCampaign() {
        return maySelectCampaign;
    }

    /**
     * Sets the value of the maySelectCampaign property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setMaySelectCampaign(Boolean value) {
        this.maySelectCampaign = value;
    }

    /**
     * Gets the value of the maySelectNone property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isMaySelectNone() {
        return maySelectNone;
    }

    /**
     * Sets the value of the maySelectNone property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setMaySelectNone(Boolean value) {
        this.maySelectNone = value;
    }

    /**
     * Gets the value of the showDialAttempts property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isShowDialAttempts() {
        return showDialAttempts;
    }

    /**
     * Sets the value of the showDialAttempts property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setShowDialAttempts(Boolean value) {
        this.showDialAttempts = value;
    }

    /**
     * Gets the value of the voicemailTimeout property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getVoicemailTimeout() {
        return voicemailTimeout;
    }

    /**
     * Sets the value of the voicemailTimeout property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setVoicemailTimeout(Integer value) {
        this.voicemailTimeout = value;
    }

}
