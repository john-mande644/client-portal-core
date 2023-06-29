
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
 * <p>Java class for agentAuditReportCriteria complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="agentAuditReportCriteria">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="agentStates" type="{http://service.admin.ws.five9.com/v2/}agentState" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="agents" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="logoutReasonCodes" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="notReadyReasonCodes" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "agentAuditReportCriteria", propOrder = {
    "agentStates",
    "agents",
    "logoutReasonCodes",
    "notReadyReasonCodes"
})
public class AgentAuditReportCriteria {

    @XmlElement(nillable = true)
    protected List<AgentState> agentStates;
    @XmlElement(nillable = true)
    protected List<String> agents;
    @XmlElement(nillable = true)
    protected List<String> logoutReasonCodes;
    @XmlElement(nillable = true)
    protected List<String> notReadyReasonCodes;

    /**
     * Gets the value of the agentStates property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the agentStates property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAgentStates().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AgentState }
     * 
     * 
     */
    public List<AgentState> getAgentStates() {
        if (agentStates == null) {
            agentStates = new ArrayList<AgentState>();
        }
        return this.agentStates;
    }

    /**
     * Gets the value of the agents property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the agents property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAgents().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getAgents() {
        if (agents == null) {
            agents = new ArrayList<String>();
        }
        return this.agents;
    }

    /**
     * Gets the value of the logoutReasonCodes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the logoutReasonCodes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLogoutReasonCodes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getLogoutReasonCodes() {
        if (logoutReasonCodes == null) {
            logoutReasonCodes = new ArrayList<String>();
        }
        return this.logoutReasonCodes;
    }

    /**
     * Gets the value of the notReadyReasonCodes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the notReadyReasonCodes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNotReadyReasonCodes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getNotReadyReasonCodes() {
        if (notReadyReasonCodes == null) {
            notReadyReasonCodes = new ArrayList<String>();
        }
        return this.notReadyReasonCodes;
    }

}
