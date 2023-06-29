
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for dialingRule complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="dialingRule">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="applyToManualCalls" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="contactText" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dateRange" type="{http://service.admin.ws.five9.com/v2/}dateRange" minOccurs="0"/>
 *         &lt;element name="fixedTimeZone" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="state" type="{http://service.admin.ws.five9.com/v2/}stateProvince" minOccurs="0"/>
 *         &lt;element name="timeRange" type="{http://service.admin.ws.five9.com/v2/}timeRange" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dialingRule", propOrder = {
    "applyToManualCalls",
    "contactText",
    "dateRange",
    "fixedTimeZone",
    "name",
    "state",
    "timeRange"
})
public class DialingRule {

    protected Boolean applyToManualCalls;
    protected String contactText;
    protected DateRange dateRange;
    protected String fixedTimeZone;
    protected String name;
    protected StateProvince state;
    protected TimeRange timeRange;

    /**
     * Gets the value of the applyToManualCalls property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isApplyToManualCalls() {
        return applyToManualCalls;
    }

    /**
     * Sets the value of the applyToManualCalls property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setApplyToManualCalls(Boolean value) {
        this.applyToManualCalls = value;
    }

    /**
     * Gets the value of the contactText property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContactText() {
        return contactText;
    }

    /**
     * Sets the value of the contactText property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContactText(String value) {
        this.contactText = value;
    }

    /**
     * Gets the value of the dateRange property.
     * 
     * @return
     *     possible object is
     *     {@link DateRange }
     *     
     */
    public DateRange getDateRange() {
        return dateRange;
    }

    /**
     * Sets the value of the dateRange property.
     * 
     * @param value
     *     allowed object is
     *     {@link DateRange }
     *     
     */
    public void setDateRange(DateRange value) {
        this.dateRange = value;
    }

    /**
     * Gets the value of the fixedTimeZone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFixedTimeZone() {
        return fixedTimeZone;
    }

    /**
     * Sets the value of the fixedTimeZone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFixedTimeZone(String value) {
        this.fixedTimeZone = value;
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
     * Gets the value of the state property.
     * 
     * @return
     *     possible object is
     *     {@link StateProvince }
     *     
     */
    public StateProvince getState() {
        return state;
    }

    /**
     * Sets the value of the state property.
     * 
     * @param value
     *     allowed object is
     *     {@link StateProvince }
     *     
     */
    public void setState(StateProvince value) {
        this.state = value;
    }

    /**
     * Gets the value of the timeRange property.
     * 
     * @return
     *     possible object is
     *     {@link TimeRange }
     *     
     */
    public TimeRange getTimeRange() {
        return timeRange;
    }

    /**
     * Sets the value of the timeRange property.
     * 
     * @param value
     *     allowed object is
     *     {@link TimeRange }
     *     
     */
    public void setTimeRange(TimeRange value) {
        this.timeRange = value;
    }

}
