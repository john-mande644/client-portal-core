
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for modifyAgentGroup complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="modifyAgentGroup">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="group" type="{http://service.admin.ws.five9.com/v2/}agentGroup" minOccurs="0"/>
 *         &lt;element name="addAgents" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="removeAgents" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "modifyAgentGroup", propOrder = {
    "group",
    "addAgents",
    "removeAgents"
})
public class ModifyAgentGroup {

    protected AgentGroup group;
    protected List<String> addAgents;
    protected List<String> removeAgents;

    /**
     * Gets the value of the group property.
     * 
     * @return
     *     possible object is
     *     {@link AgentGroup }
     *     
     */
    public AgentGroup getGroup() {
        return group;
    }

    /**
     * Sets the value of the group property.
     * 
     * @param value
     *     allowed object is
     *     {@link AgentGroup }
     *     
     */
    public void setGroup(AgentGroup value) {
        this.group = value;
    }

    /**
     * Gets the value of the addAgents property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the addAgents property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAddAgents().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getAddAgents() {
        if (addAgents == null) {
            addAgents = new ArrayList<String>();
        }
        return this.addAgents;
    }

    /**
     * Gets the value of the removeAgents property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the removeAgents property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRemoveAgents().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getRemoveAgents() {
        if (removeAgents == null) {
            removeAgents = new ArrayList<String>();
        }
        return this.removeAgents;
    }

}
