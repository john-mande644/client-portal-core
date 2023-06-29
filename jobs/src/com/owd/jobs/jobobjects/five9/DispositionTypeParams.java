
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for dispositionTypeParams complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="dispositionTypeParams">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="allowChangeTimer" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="attempts" type="{http://www.w3.org/2001/XMLSchema}byte" minOccurs="0"/>
 *         &lt;element name="timer" type="{http://service.admin.ws.five9.com/v2/}timer" minOccurs="0"/>
 *         &lt;element name="useTimer" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dispositionTypeParams", propOrder = {
    "allowChangeTimer",
    "attempts",
    "timer",
    "useTimer"
})
public class DispositionTypeParams {

    protected Boolean allowChangeTimer;
    protected Byte attempts;
    protected Timer timer;
    protected Boolean useTimer;

    /**
     * Gets the value of the allowChangeTimer property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isAllowChangeTimer() {
        return allowChangeTimer;
    }

    /**
     * Sets the value of the allowChangeTimer property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setAllowChangeTimer(Boolean value) {
        this.allowChangeTimer = value;
    }

    /**
     * Gets the value of the attempts property.
     * 
     * @return
     *     possible object is
     *     {@link Byte }
     *     
     */
    public Byte getAttempts() {
        return attempts;
    }

    /**
     * Sets the value of the attempts property.
     * 
     * @param value
     *     allowed object is
     *     {@link Byte }
     *     
     */
    public void setAttempts(Byte value) {
        this.attempts = value;
    }

    /**
     * Gets the value of the timer property.
     * 
     * @return
     *     possible object is
     *     {@link Timer }
     *     
     */
    public Timer getTimer() {
        return timer;
    }

    /**
     * Sets the value of the timer property.
     * 
     * @param value
     *     allowed object is
     *     {@link Timer }
     *     
     */
    public void setTimer(Timer value) {
        this.timer = value;
    }

    /**
     * Gets the value of the useTimer property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isUseTimer() {
        return useTimer;
    }

    /**
     * Sets the value of the useTimer property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setUseTimer(Boolean value) {
        this.useTimer = value;
    }

}
