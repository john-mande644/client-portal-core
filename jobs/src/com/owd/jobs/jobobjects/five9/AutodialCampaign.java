
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for autodialCampaign complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="autodialCampaign">
 *   &lt;complexContent>
 *     &lt;extension base="{http://service.admin.ws.five9.com/v2/}baseOutboundCampaign">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="agentAvailability" type="{http://service.admin.ws.five9.com/v2/}agentAvailability" minOccurs="0"/>
 *         &lt;element name="agentSkillName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="defaultIvrSchedule" type="{http://service.admin.ws.five9.com/v2/}ivrScriptSchedule" minOccurs="0"/>
 *         &lt;element name="dialIfAgentsAvailable" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="maxNumOfLines" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "autodialCampaign", propOrder = {
    "agentAvailability",
    "agentSkillName",
    "defaultIvrSchedule",
    "dialIfAgentsAvailable",
    "maxNumOfLines"
})
public class AutodialCampaign
    extends BaseOutboundCampaign
{

    protected AgentAvailability agentAvailability;
    protected String agentSkillName;
    protected IvrScriptSchedule defaultIvrSchedule;
    protected Boolean dialIfAgentsAvailable;
    protected Integer maxNumOfLines;

    /**
     * Gets the value of the agentAvailability property.
     * 
     * @return
     *     possible object is
     *     {@link AgentAvailability }
     *     
     */
    public AgentAvailability getAgentAvailability() {
        return agentAvailability;
    }

    /**
     * Sets the value of the agentAvailability property.
     * 
     * @param value
     *     allowed object is
     *     {@link AgentAvailability }
     *     
     */
    public void setAgentAvailability(AgentAvailability value) {
        this.agentAvailability = value;
    }

    /**
     * Gets the value of the agentSkillName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAgentSkillName() {
        return agentSkillName;
    }

    /**
     * Sets the value of the agentSkillName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAgentSkillName(String value) {
        this.agentSkillName = value;
    }

    /**
     * Gets the value of the defaultIvrSchedule property.
     * 
     * @return
     *     possible object is
     *     {@link IvrScriptSchedule }
     *     
     */
    public IvrScriptSchedule getDefaultIvrSchedule() {
        return defaultIvrSchedule;
    }

    /**
     * Sets the value of the defaultIvrSchedule property.
     * 
     * @param value
     *     allowed object is
     *     {@link IvrScriptSchedule }
     *     
     */
    public void setDefaultIvrSchedule(IvrScriptSchedule value) {
        this.defaultIvrSchedule = value;
    }

    /**
     * Gets the value of the dialIfAgentsAvailable property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isDialIfAgentsAvailable() {
        return dialIfAgentsAvailable;
    }

    /**
     * Sets the value of the dialIfAgentsAvailable property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDialIfAgentsAvailable(Boolean value) {
        this.dialIfAgentsAvailable = value;
    }

    /**
     * Gets the value of the maxNumOfLines property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMaxNumOfLines() {
        return maxNumOfLines;
    }

    /**
     * Sets the value of the maxNumOfLines property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMaxNumOfLines(Integer value) {
        this.maxNumOfLines = value;
    }

}
