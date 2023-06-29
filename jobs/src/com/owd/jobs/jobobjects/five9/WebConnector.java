
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for webConnector complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="webConnector">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="addWorksheet" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="agentApplication" type="{http://service.admin.ws.five9.com/v2/}webConnectorAgentAppType" minOccurs="0"/>
 *         &lt;element name="clearTriggerDispositions" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="constants" type="{http://service.admin.ws.five9.com/v2/}keyValuePair" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="ctiWebServices" type="{http://service.admin.ws.five9.com/v2/}webConnectorCTIWebServicesType" minOccurs="0"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="executeInBrowser" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="postConstants" type="{http://service.admin.ws.five9.com/v2/}keyValuePair" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="postMethod" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="postVariables" type="{http://service.admin.ws.five9.com/v2/}keyValuePair" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="startPageText" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="trigger" type="{http://service.admin.ws.five9.com/v2/}webConnectorTriggerType" minOccurs="0"/>
 *         &lt;element name="triggerDispositions" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="url" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="variables" type="{http://service.admin.ws.five9.com/v2/}keyValuePair" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "webConnector", propOrder = {
    "addWorksheet",
    "agentApplication",
    "clearTriggerDispositions",
    "constants",
    "ctiWebServices",
    "description",
    "executeInBrowser",
    "name",
    "postConstants",
    "postMethod",
    "postVariables",
    "startPageText",
    "trigger",
    "triggerDispositions",
    "url",
    "variables"
})
public class WebConnector {

    protected Boolean addWorksheet;
    protected WebConnectorAgentAppType agentApplication;
    protected Boolean clearTriggerDispositions;
    protected List<KeyValuePair> constants;
    protected WebConnectorCTIWebServicesType ctiWebServices;
    protected String description;
    protected Boolean executeInBrowser;
    protected String name;
    protected List<KeyValuePair> postConstants;
    protected Boolean postMethod;
    protected List<KeyValuePair> postVariables;
    protected String startPageText;
    protected WebConnectorTriggerType trigger;
    @XmlElement(nillable = true)
    protected List<String> triggerDispositions;
    protected String url;
    protected List<KeyValuePair> variables;

    /**
     * Gets the value of the addWorksheet property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isAddWorksheet() {
        return addWorksheet;
    }

    /**
     * Sets the value of the addWorksheet property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setAddWorksheet(Boolean value) {
        this.addWorksheet = value;
    }

    /**
     * Gets the value of the agentApplication property.
     * 
     * @return
     *     possible object is
     *     {@link WebConnectorAgentAppType }
     *     
     */
    public WebConnectorAgentAppType getAgentApplication() {
        return agentApplication;
    }

    /**
     * Sets the value of the agentApplication property.
     * 
     * @param value
     *     allowed object is
     *     {@link WebConnectorAgentAppType }
     *     
     */
    public void setAgentApplication(WebConnectorAgentAppType value) {
        this.agentApplication = value;
    }

    /**
     * Gets the value of the clearTriggerDispositions property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isClearTriggerDispositions() {
        return clearTriggerDispositions;
    }

    /**
     * Sets the value of the clearTriggerDispositions property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setClearTriggerDispositions(Boolean value) {
        this.clearTriggerDispositions = value;
    }

    /**
     * Gets the value of the constants property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the constants property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getConstants().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link KeyValuePair }
     * 
     * 
     */
    public List<KeyValuePair> getConstants() {
        if (constants == null) {
            constants = new ArrayList<KeyValuePair>();
        }
        return this.constants;
    }

    /**
     * Gets the value of the ctiWebServices property.
     * 
     * @return
     *     possible object is
     *     {@link WebConnectorCTIWebServicesType }
     *     
     */
    public WebConnectorCTIWebServicesType getCtiWebServices() {
        return ctiWebServices;
    }

    /**
     * Sets the value of the ctiWebServices property.
     * 
     * @param value
     *     allowed object is
     *     {@link WebConnectorCTIWebServicesType }
     *     
     */
    public void setCtiWebServices(WebConnectorCTIWebServicesType value) {
        this.ctiWebServices = value;
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
     * Gets the value of the executeInBrowser property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isExecuteInBrowser() {
        return executeInBrowser;
    }

    /**
     * Sets the value of the executeInBrowser property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setExecuteInBrowser(Boolean value) {
        this.executeInBrowser = value;
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
     * Gets the value of the postConstants property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the postConstants property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPostConstants().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link KeyValuePair }
     * 
     * 
     */
    public List<KeyValuePair> getPostConstants() {
        if (postConstants == null) {
            postConstants = new ArrayList<KeyValuePair>();
        }
        return this.postConstants;
    }

    /**
     * Gets the value of the postMethod property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isPostMethod() {
        return postMethod;
    }

    /**
     * Sets the value of the postMethod property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setPostMethod(Boolean value) {
        this.postMethod = value;
    }

    /**
     * Gets the value of the postVariables property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the postVariables property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPostVariables().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link KeyValuePair }
     * 
     * 
     */
    public List<KeyValuePair> getPostVariables() {
        if (postVariables == null) {
            postVariables = new ArrayList<KeyValuePair>();
        }
        return this.postVariables;
    }

    /**
     * Gets the value of the startPageText property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStartPageText() {
        return startPageText;
    }

    /**
     * Sets the value of the startPageText property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStartPageText(String value) {
        this.startPageText = value;
    }

    /**
     * Gets the value of the trigger property.
     * 
     * @return
     *     possible object is
     *     {@link WebConnectorTriggerType }
     *     
     */
    public WebConnectorTriggerType getTrigger() {
        return trigger;
    }

    /**
     * Sets the value of the trigger property.
     * 
     * @param value
     *     allowed object is
     *     {@link WebConnectorTriggerType }
     *     
     */
    public void setTrigger(WebConnectorTriggerType value) {
        this.trigger = value;
    }

    /**
     * Gets the value of the triggerDispositions property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the triggerDispositions property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTriggerDispositions().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getTriggerDispositions() {
        if (triggerDispositions == null) {
            triggerDispositions = new ArrayList<String>();
        }
        return this.triggerDispositions;
    }

    /**
     * Gets the value of the url property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets the value of the url property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUrl(String value) {
        this.url = value;
    }

    /**
     * Gets the value of the variables property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the variables property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getVariables().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link KeyValuePair }
     * 
     * 
     */
    public List<KeyValuePair> getVariables() {
        if (variables == null) {
            variables = new ArrayList<KeyValuePair>();
        }
        return this.variables;
    }

}
