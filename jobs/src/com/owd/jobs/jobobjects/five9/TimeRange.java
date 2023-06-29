
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for timeRange complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="timeRange">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="startHour" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="startMinute" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="stopHour" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="stopMinute" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "timeRange", propOrder = {
    "startHour",
    "startMinute",
    "stopHour",
    "stopMinute"
})
public class TimeRange {

    protected int startHour;
    protected int startMinute;
    protected int stopHour;
    protected int stopMinute;

    /**
     * Gets the value of the startHour property.
     * 
     */
    public int getStartHour() {
        return startHour;
    }

    /**
     * Sets the value of the startHour property.
     * 
     */
    public void setStartHour(int value) {
        this.startHour = value;
    }

    /**
     * Gets the value of the startMinute property.
     * 
     */
    public int getStartMinute() {
        return startMinute;
    }

    /**
     * Sets the value of the startMinute property.
     * 
     */
    public void setStartMinute(int value) {
        this.startMinute = value;
    }

    /**
     * Gets the value of the stopHour property.
     * 
     */
    public int getStopHour() {
        return stopHour;
    }

    /**
     * Sets the value of the stopHour property.
     * 
     */
    public void setStopHour(int value) {
        this.stopHour = value;
    }

    /**
     * Gets the value of the stopMinute property.
     * 
     */
    public int getStopMinute() {
        return stopMinute;
    }

    /**
     * Sets the value of the stopMinute property.
     * 
     */
    public void setStopMinute(int value) {
        this.stopMinute = value;
    }

}
