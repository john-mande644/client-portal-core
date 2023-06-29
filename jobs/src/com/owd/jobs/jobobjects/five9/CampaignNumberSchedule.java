
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for campaignNumberSchedule complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="campaignNumberSchedule">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="number" type="{http://service.admin.ws.five9.com/v2/}campaignDialNumber" minOccurs="0"/>
 *         &lt;element name="startTime" type="{http://service.admin.ws.five9.com/v2/}timer" minOccurs="0"/>
 *         &lt;element name="stopTime" type="{http://service.admin.ws.five9.com/v2/}timer" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "campaignNumberSchedule", propOrder = {
    "number",
    "startTime",
    "stopTime"
})
public class CampaignNumberSchedule {

    protected CampaignDialNumber number;
    protected Timer startTime;
    protected Timer stopTime;

    /**
     * Gets the value of the number property.
     * 
     * @return
     *     possible object is
     *     {@link CampaignDialNumber }
     *     
     */
    public CampaignDialNumber getNumber() {
        return number;
    }

    /**
     * Sets the value of the number property.
     * 
     * @param value
     *     allowed object is
     *     {@link CampaignDialNumber }
     *     
     */
    public void setNumber(CampaignDialNumber value) {
        this.number = value;
    }

    /**
     * Gets the value of the startTime property.
     * 
     * @return
     *     possible object is
     *     {@link Timer }
     *     
     */
    public Timer getStartTime() {
        return startTime;
    }

    /**
     * Sets the value of the startTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link Timer }
     *     
     */
    public void setStartTime(Timer value) {
        this.startTime = value;
    }

    /**
     * Gets the value of the stopTime property.
     * 
     * @return
     *     possible object is
     *     {@link Timer }
     *     
     */
    public Timer getStopTime() {
        return stopTime;
    }

    /**
     * Sets the value of the stopTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link Timer }
     *     
     */
    public void setStopTime(Timer value) {
        this.stopTime = value;
    }

}
