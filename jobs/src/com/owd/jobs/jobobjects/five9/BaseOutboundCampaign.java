
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for baseOutboundCampaign complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="baseOutboundCampaign">
 *   &lt;complexContent>
 *     &lt;extension base="{http://service.admin.ws.five9.com/v2/}generalCampaign">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="analyzeLevel" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="CRMRedialTimeout" type="{http://service.admin.ws.five9.com/v2/}timer" minOccurs="0"/>
 *         &lt;element name="dialingByStateRules" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="dnisAsAni" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="enableListDialingRatios" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="listDialingMode" type="{http://service.admin.ws.five9.com/v2/}listDialingMode" minOccurs="0"/>
 *         &lt;element name="noOutOfNumbersAlert" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "baseOutboundCampaign", propOrder = {
    "analyzeLevel",
    "crmRedialTimeout",
    "dialingByStateRules",
    "dnisAsAni",
    "enableListDialingRatios",
    "listDialingMode",
    "noOutOfNumbersAlert"
})
@XmlSeeAlso({
    AutodialCampaign.class,
    OutboundCampaign.class
})
public abstract class BaseOutboundCampaign
    extends GeneralCampaign
{

    protected Integer analyzeLevel;
    @XmlElement(name = "CRMRedialTimeout")
    protected Timer crmRedialTimeout;
    protected Boolean dialingByStateRules;
    protected Boolean dnisAsAni;
    protected Boolean enableListDialingRatios;
    protected ListDialingMode listDialingMode;
    protected Boolean noOutOfNumbersAlert;

    /**
     * Gets the value of the analyzeLevel property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAnalyzeLevel() {
        return analyzeLevel;
    }

    /**
     * Sets the value of the analyzeLevel property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAnalyzeLevel(Integer value) {
        this.analyzeLevel = value;
    }

    /**
     * Gets the value of the crmRedialTimeout property.
     * 
     * @return
     *     possible object is
     *     {@link Timer }
     *     
     */
    public Timer getCRMRedialTimeout() {
        return crmRedialTimeout;
    }

    /**
     * Sets the value of the crmRedialTimeout property.
     * 
     * @param value
     *     allowed object is
     *     {@link Timer }
     *     
     */
    public void setCRMRedialTimeout(Timer value) {
        this.crmRedialTimeout = value;
    }

    /**
     * Gets the value of the dialingByStateRules property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isDialingByStateRules() {
        return dialingByStateRules;
    }

    /**
     * Sets the value of the dialingByStateRules property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDialingByStateRules(Boolean value) {
        this.dialingByStateRules = value;
    }

    /**
     * Gets the value of the dnisAsAni property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isDnisAsAni() {
        return dnisAsAni;
    }

    /**
     * Sets the value of the dnisAsAni property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDnisAsAni(Boolean value) {
        this.dnisAsAni = value;
    }

    /**
     * Gets the value of the enableListDialingRatios property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isEnableListDialingRatios() {
        return enableListDialingRatios;
    }

    /**
     * Sets the value of the enableListDialingRatios property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setEnableListDialingRatios(Boolean value) {
        this.enableListDialingRatios = value;
    }

    /**
     * Gets the value of the listDialingMode property.
     * 
     * @return
     *     possible object is
     *     {@link ListDialingMode }
     *     
     */
    public ListDialingMode getListDialingMode() {
        return listDialingMode;
    }

    /**
     * Sets the value of the listDialingMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListDialingMode }
     *     
     */
    public void setListDialingMode(ListDialingMode value) {
        this.listDialingMode = value;
    }

    /**
     * Gets the value of the noOutOfNumbersAlert property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isNoOutOfNumbersAlert() {
        return noOutOfNumbersAlert;
    }

    /**
     * Sets the value of the noOutOfNumbersAlert property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setNoOutOfNumbersAlert(Boolean value) {
        this.noOutOfNumbersAlert = value;
    }

}
