
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for campaignProfileInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="campaignProfileInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="ANI" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dialingSchedule" type="{http://service.admin.ws.five9.com/v2/}campaignDialingSchedule" minOccurs="0"/>
 *         &lt;element name="dialingTimeout" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="initialCallPriority" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="maxCharges" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numberOfAttempts" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "campaignProfileInfo", propOrder = {
    "ani",
    "description",
    "dialingSchedule",
    "dialingTimeout",
    "initialCallPriority",
    "maxCharges",
    "name",
    "numberOfAttempts"
})
public class CampaignProfileInfo {

    @XmlElement(name = "ANI")
    protected String ani;
    protected String description;
    protected CampaignDialingSchedule dialingSchedule;
    protected Integer dialingTimeout;
    protected Integer initialCallPriority;
    protected Integer maxCharges;
    protected String name;
    protected Integer numberOfAttempts;

    /**
     * Gets the value of the ani property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getANI() {
        return ani;
    }

    /**
     * Sets the value of the ani property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setANI(String value) {
        this.ani = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the dialingSchedule property.
     * 
     * @return
     *     possible object is
     *     {@link CampaignDialingSchedule }
     *     
     */
    public CampaignDialingSchedule getDialingSchedule() {
        return dialingSchedule;
    }

    /**
     * Sets the value of the dialingSchedule property.
     * 
     * @param value
     *     allowed object is
     *     {@link CampaignDialingSchedule }
     *     
     */
    public void setDialingSchedule(CampaignDialingSchedule value) {
        this.dialingSchedule = value;
    }

    /**
     * Gets the value of the dialingTimeout property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getDialingTimeout() {
        return dialingTimeout;
    }

    /**
     * Sets the value of the dialingTimeout property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setDialingTimeout(Integer value) {
        this.dialingTimeout = value;
    }

    /**
     * Gets the value of the initialCallPriority property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getInitialCallPriority() {
        return initialCallPriority;
    }

    /**
     * Sets the value of the initialCallPriority property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setInitialCallPriority(Integer value) {
        this.initialCallPriority = value;
    }

    /**
     * Gets the value of the maxCharges property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMaxCharges() {
        return maxCharges;
    }

    /**
     * Sets the value of the maxCharges property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMaxCharges(Integer value) {
        this.maxCharges = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the numberOfAttempts property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumberOfAttempts() {
        return numberOfAttempts;
    }

    /**
     * Sets the value of the numberOfAttempts property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumberOfAttempts(Integer value) {
        this.numberOfAttempts = value;
    }

}
