
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for disposition complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="disposition">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="agentMustCompleteWorksheet" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="agentMustConfirm" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="resetAttemptsCounter" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="sendEmailNotification" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="sendIMNotification" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="trackAsFirstCallResolution" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="type" type="{http://service.admin.ws.five9.com/v2/}customDispositionType" minOccurs="0"/>
 *         &lt;element name="typeParameters" type="{http://service.admin.ws.five9.com/v2/}dispositionTypeParams" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "disposition", propOrder = {
    "agentMustCompleteWorksheet",
    "agentMustConfirm",
    "description",
    "name",
    "resetAttemptsCounter",
    "sendEmailNotification",
    "sendIMNotification",
    "trackAsFirstCallResolution",
    "type",
    "typeParameters"
})
public class Disposition {

    protected Boolean agentMustCompleteWorksheet;
    protected Boolean agentMustConfirm;
    protected String description;
    protected String name;
    protected Boolean resetAttemptsCounter;
    protected Boolean sendEmailNotification;
    protected Boolean sendIMNotification;
    protected Boolean trackAsFirstCallResolution;
    protected CustomDispositionType type;
    protected DispositionTypeParams typeParameters;

    /**
     * Gets the value of the agentMustCompleteWorksheet property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isAgentMustCompleteWorksheet() {
        return agentMustCompleteWorksheet;
    }

    /**
     * Sets the value of the agentMustCompleteWorksheet property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setAgentMustCompleteWorksheet(Boolean value) {
        this.agentMustCompleteWorksheet = value;
    }

    /**
     * Gets the value of the agentMustConfirm property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isAgentMustConfirm() {
        return agentMustConfirm;
    }

    /**
     * Sets the value of the agentMustConfirm property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setAgentMustConfirm(Boolean value) {
        this.agentMustConfirm = value;
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
     * Gets the value of the resetAttemptsCounter property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isResetAttemptsCounter() {
        return resetAttemptsCounter;
    }

    /**
     * Sets the value of the resetAttemptsCounter property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setResetAttemptsCounter(Boolean value) {
        this.resetAttemptsCounter = value;
    }

    /**
     * Gets the value of the sendEmailNotification property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isSendEmailNotification() {
        return sendEmailNotification;
    }

    /**
     * Sets the value of the sendEmailNotification property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSendEmailNotification(Boolean value) {
        this.sendEmailNotification = value;
    }

    /**
     * Gets the value of the sendIMNotification property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isSendIMNotification() {
        return sendIMNotification;
    }

    /**
     * Sets the value of the sendIMNotification property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSendIMNotification(Boolean value) {
        this.sendIMNotification = value;
    }

    /**
     * Gets the value of the trackAsFirstCallResolution property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isTrackAsFirstCallResolution() {
        return trackAsFirstCallResolution;
    }

    /**
     * Sets the value of the trackAsFirstCallResolution property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setTrackAsFirstCallResolution(Boolean value) {
        this.trackAsFirstCallResolution = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link CustomDispositionType }
     *     
     */
    public CustomDispositionType getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomDispositionType }
     *     
     */
    public void setType(CustomDispositionType value) {
        this.type = value;
    }

    /**
     * Gets the value of the typeParameters property.
     * 
     * @return
     *     possible object is
     *     {@link DispositionTypeParams }
     *     
     */
    public DispositionTypeParams getTypeParameters() {
        return typeParameters;
    }

    /**
     * Sets the value of the typeParameters property.
     * 
     * @param value
     *     allowed object is
     *     {@link DispositionTypeParams }
     *     
     */
    public void setTypeParameters(DispositionTypeParams value) {
        this.typeParameters = value;
    }

}
