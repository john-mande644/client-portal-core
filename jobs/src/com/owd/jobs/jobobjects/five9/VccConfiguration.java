
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for vccConfiguration complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="vccConfiguration">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="agentProductivity" type="{http://service.admin.ws.five9.com/v2/}agentProductivity" minOccurs="0"/>
 *         &lt;element name="emailProperties" type="{http://service.admin.ws.five9.com/v2/}emailNotifications" minOccurs="0"/>
 *         &lt;element name="keyPerfomanceIndicators" type="{http://service.admin.ws.five9.com/v2/}keyPerfomanceIndicators" minOccurs="0"/>
 *         &lt;element name="miscOptions" type="{http://service.admin.ws.five9.com/v2/}miscVccOptions" minOccurs="0"/>
 *         &lt;element name="passwordPolicies" type="{http://service.admin.ws.five9.com/v2/}passwordPolicies" minOccurs="0"/>
 *         &lt;element name="recordingsServer" type="{http://service.admin.ws.five9.com/v2/}remoteHostLoginSettings" minOccurs="0"/>
 *         &lt;element name="reportsServer" type="{http://service.admin.ws.five9.com/v2/}remoteHostLoginSettings" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "vccConfiguration", propOrder = {
    "agentProductivity",
    "emailProperties",
    "keyPerfomanceIndicators",
    "miscOptions",
    "passwordPolicies",
    "recordingsServer",
    "reportsServer"
})
public class VccConfiguration {

    protected AgentProductivity agentProductivity;
    protected EmailNotifications emailProperties;
    protected KeyPerfomanceIndicators keyPerfomanceIndicators;
    protected MiscVccOptions miscOptions;
    protected PasswordPolicies passwordPolicies;
    protected RemoteHostLoginSettings recordingsServer;
    protected RemoteHostLoginSettings reportsServer;

    /**
     * Gets the value of the agentProductivity property.
     * 
     * @return
     *     possible object is
     *     {@link AgentProductivity }
     *     
     */
    public AgentProductivity getAgentProductivity() {
        return agentProductivity;
    }

    /**
     * Sets the value of the agentProductivity property.
     * 
     * @param value
     *     allowed object is
     *     {@link AgentProductivity }
     *     
     */
    public void setAgentProductivity(AgentProductivity value) {
        this.agentProductivity = value;
    }

    /**
     * Gets the value of the emailProperties property.
     * 
     * @return
     *     possible object is
     *     {@link EmailNotifications }
     *     
     */
    public EmailNotifications getEmailProperties() {
        return emailProperties;
    }

    /**
     * Sets the value of the emailProperties property.
     * 
     * @param value
     *     allowed object is
     *     {@link EmailNotifications }
     *     
     */
    public void setEmailProperties(EmailNotifications value) {
        this.emailProperties = value;
    }

    /**
     * Gets the value of the keyPerfomanceIndicators property.
     * 
     * @return
     *     possible object is
     *     {@link KeyPerfomanceIndicators }
     *     
     */
    public KeyPerfomanceIndicators getKeyPerfomanceIndicators() {
        return keyPerfomanceIndicators;
    }

    /**
     * Sets the value of the keyPerfomanceIndicators property.
     * 
     * @param value
     *     allowed object is
     *     {@link KeyPerfomanceIndicators }
     *     
     */
    public void setKeyPerfomanceIndicators(KeyPerfomanceIndicators value) {
        this.keyPerfomanceIndicators = value;
    }

    /**
     * Gets the value of the miscOptions property.
     * 
     * @return
     *     possible object is
     *     {@link MiscVccOptions }
     *     
     */
    public MiscVccOptions getMiscOptions() {
        return miscOptions;
    }

    /**
     * Sets the value of the miscOptions property.
     * 
     * @param value
     *     allowed object is
     *     {@link MiscVccOptions }
     *     
     */
    public void setMiscOptions(MiscVccOptions value) {
        this.miscOptions = value;
    }

    /**
     * Gets the value of the passwordPolicies property.
     * 
     * @return
     *     possible object is
     *     {@link PasswordPolicies }
     *     
     */
    public PasswordPolicies getPasswordPolicies() {
        return passwordPolicies;
    }

    /**
     * Sets the value of the passwordPolicies property.
     * 
     * @param value
     *     allowed object is
     *     {@link PasswordPolicies }
     *     
     */
    public void setPasswordPolicies(PasswordPolicies value) {
        this.passwordPolicies = value;
    }

    /**
     * Gets the value of the recordingsServer property.
     * 
     * @return
     *     possible object is
     *     {@link RemoteHostLoginSettings }
     *     
     */
    public RemoteHostLoginSettings getRecordingsServer() {
        return recordingsServer;
    }

    /**
     * Sets the value of the recordingsServer property.
     * 
     * @param value
     *     allowed object is
     *     {@link RemoteHostLoginSettings }
     *     
     */
    public void setRecordingsServer(RemoteHostLoginSettings value) {
        this.recordingsServer = value;
    }

    /**
     * Gets the value of the reportsServer property.
     * 
     * @return
     *     possible object is
     *     {@link RemoteHostLoginSettings }
     *     
     */
    public RemoteHostLoginSettings getReportsServer() {
        return reportsServer;
    }

    /**
     * Sets the value of the reportsServer property.
     * 
     * @param value
     *     allowed object is
     *     {@link RemoteHostLoginSettings }
     *     
     */
    public void setReportsServer(RemoteHostLoginSettings value) {
        this.reportsServer = value;
    }

}
