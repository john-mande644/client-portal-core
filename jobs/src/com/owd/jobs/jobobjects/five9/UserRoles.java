
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for userRoles complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="userRoles">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="admin" type="{http://service.admin.ws.five9.com/v2/}adminRole" minOccurs="0"/>
 *         &lt;element name="agent" type="{http://service.admin.ws.five9.com/v2/}agentRole" minOccurs="0"/>
 *         &lt;element name="reporting" type="{http://service.admin.ws.five9.com/v2/}reportingRole" minOccurs="0"/>
 *         &lt;element name="supervisor" type="{http://service.admin.ws.five9.com/v2/}supervisorRole" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "userRoles", propOrder = {
    "admin",
    "agent",
    "reporting",
    "supervisor"
})
public class UserRoles {

    protected AdminRole admin;
    protected AgentRole agent;
    protected ReportingRole reporting;
    protected SupervisorRole supervisor;

    /**
     * Gets the value of the admin property.
     * 
     * @return
     *     possible object is
     *     {@link AdminRole }
     *     
     */
    public AdminRole getAdmin() {
        return admin;
    }

    /**
     * Sets the value of the admin property.
     * 
     * @param value
     *     allowed object is
     *     {@link AdminRole }
     *     
     */
    public void setAdmin(AdminRole value) {
        this.admin = value;
    }

    /**
     * Gets the value of the agent property.
     * 
     * @return
     *     possible object is
     *     {@link AgentRole }
     *     
     */
    public AgentRole getAgent() {
        return agent;
    }

    /**
     * Sets the value of the agent property.
     * 
     * @param value
     *     allowed object is
     *     {@link AgentRole }
     *     
     */
    public void setAgent(AgentRole value) {
        this.agent = value;
    }

    /**
     * Gets the value of the reporting property.
     * 
     * @return
     *     possible object is
     *     {@link ReportingRole }
     *     
     */
    public ReportingRole getReporting() {
        return reporting;
    }

    /**
     * Sets the value of the reporting property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReportingRole }
     *     
     */
    public void setReporting(ReportingRole value) {
        this.reporting = value;
    }

    /**
     * Gets the value of the supervisor property.
     * 
     * @return
     *     possible object is
     *     {@link SupervisorRole }
     *     
     */
    public SupervisorRole getSupervisor() {
        return supervisor;
    }

    /**
     * Sets the value of the supervisor property.
     * 
     * @param value
     *     allowed object is
     *     {@link SupervisorRole }
     *     
     */
    public void setSupervisor(SupervisorRole value) {
        this.supervisor = value;
    }

}
