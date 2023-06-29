
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for agentProductivity complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="agentProductivity">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="longACWTime" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="longCallDuration" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="longHoldDuration" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="longParkDuration" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="shortACWTime" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="shortCallDuration" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "agentProductivity", propOrder = {
    "longACWTime",
    "longCallDuration",
    "longHoldDuration",
    "longParkDuration",
    "shortACWTime",
    "shortCallDuration"
})
public class AgentProductivity {

    protected Integer longACWTime;
    protected Integer longCallDuration;
    protected Integer longHoldDuration;
    protected Integer longParkDuration;
    protected Integer shortACWTime;
    protected Integer shortCallDuration;

    /**
     * Gets the value of the longACWTime property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getLongACWTime() {
        return longACWTime;
    }

    /**
     * Sets the value of the longACWTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setLongACWTime(Integer value) {
        this.longACWTime = value;
    }

    /**
     * Gets the value of the longCallDuration property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getLongCallDuration() {
        return longCallDuration;
    }

    /**
     * Sets the value of the longCallDuration property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setLongCallDuration(Integer value) {
        this.longCallDuration = value;
    }

    /**
     * Gets the value of the longHoldDuration property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getLongHoldDuration() {
        return longHoldDuration;
    }

    /**
     * Sets the value of the longHoldDuration property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setLongHoldDuration(Integer value) {
        this.longHoldDuration = value;
    }

    /**
     * Gets the value of the longParkDuration property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getLongParkDuration() {
        return longParkDuration;
    }

    /**
     * Sets the value of the longParkDuration property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setLongParkDuration(Integer value) {
        this.longParkDuration = value;
    }

    /**
     * Gets the value of the shortACWTime property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getShortACWTime() {
        return shortACWTime;
    }

    /**
     * Sets the value of the shortACWTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setShortACWTime(Integer value) {
        this.shortACWTime = value;
    }

    /**
     * Gets the value of the shortCallDuration property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getShortCallDuration() {
        return shortCallDuration;
    }

    /**
     * Sets the value of the shortCallDuration property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setShortCallDuration(Integer value) {
        this.shortCallDuration = value;
    }

}
