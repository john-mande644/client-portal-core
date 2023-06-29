
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for keyPerfomanceIndicators complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="keyPerfomanceIndicators">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="minTimeOfResponse" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="speedOfAnswer" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "keyPerfomanceIndicators", propOrder = {
    "minTimeOfResponse",
    "speedOfAnswer"
})
public class KeyPerfomanceIndicators {

    protected Integer minTimeOfResponse;
    protected Integer speedOfAnswer;

    /**
     * Gets the value of the minTimeOfResponse property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMinTimeOfResponse() {
        return minTimeOfResponse;
    }

    /**
     * Sets the value of the minTimeOfResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMinTimeOfResponse(Integer value) {
        this.minTimeOfResponse = value;
    }

    /**
     * Gets the value of the speedOfAnswer property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getSpeedOfAnswer() {
        return speedOfAnswer;
    }

    /**
     * Sets the value of the speedOfAnswer property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSpeedOfAnswer(Integer value) {
        this.speedOfAnswer = value;
    }

}
