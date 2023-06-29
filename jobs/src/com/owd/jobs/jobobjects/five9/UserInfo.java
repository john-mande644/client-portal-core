
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
 * <p>Java class for userInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="userInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="agentGroups" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="cannedReports" type="{http://service.admin.ws.five9.com/v2/}cannedReport" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="generalInfo" type="{http://service.admin.ws.five9.com/v2/}userGeneralInfo" minOccurs="0"/>
 *         &lt;element name="roles" type="{http://service.admin.ws.five9.com/v2/}userRoles" minOccurs="0"/>
 *         &lt;element name="skills" type="{http://service.admin.ws.five9.com/v2/}userSkill" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "userInfo", propOrder = {
    "agentGroups",
    "cannedReports",
    "generalInfo",
    "roles",
    "skills"
})
public class UserInfo {

    @XmlElement(nillable = true)
    protected List<String> agentGroups;
    @XmlElement(nillable = true)
    protected List<CannedReport> cannedReports;
    protected UserGeneralInfo generalInfo;
    protected UserRoles roles;
    @XmlElement(nillable = true)
    protected List<UserSkill> skills;

    /**
     * Gets the value of the agentGroups property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the agentGroups property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAgentGroups().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getAgentGroups() {
        if (agentGroups == null) {
            agentGroups = new ArrayList<String>();
        }
        return this.agentGroups;
    }

    /**
     * Gets the value of the cannedReports property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the cannedReports property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCannedReports().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CannedReport }
     * 
     * 
     */
    public List<CannedReport> getCannedReports() {
        if (cannedReports == null) {
            cannedReports = new ArrayList<CannedReport>();
        }
        return this.cannedReports;
    }

    /**
     * Gets the value of the generalInfo property.
     * 
     * @return
     *     possible object is
     *     {@link UserGeneralInfo }
     *     
     */
    public UserGeneralInfo getGeneralInfo() {
        return generalInfo;
    }

    /**
     * Sets the value of the generalInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link UserGeneralInfo }
     *     
     */
    public void setGeneralInfo(UserGeneralInfo value) {
        this.generalInfo = value;
    }

    /**
     * Gets the value of the roles property.
     * 
     * @return
     *     possible object is
     *     {@link UserRoles }
     *     
     */
    public UserRoles getRoles() {
        return roles;
    }

    /**
     * Sets the value of the roles property.
     * 
     * @param value
     *     allowed object is
     *     {@link UserRoles }
     *     
     */
    public void setRoles(UserRoles value) {
        this.roles = value;
    }

    /**
     * Gets the value of the skills property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the skills property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSkills().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link UserSkill }
     * 
     * 
     */
    public List<UserSkill> getSkills() {
        if (skills == null) {
            skills = new ArrayList<UserSkill>();
        }
        return this.skills;
    }

}
