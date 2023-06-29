
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
 * <p>Java class for agentRole complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="agentRole">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="alwaysRecorded" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="attachVmToEmail" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="permissions" type="{http://service.admin.ws.five9.com/v2/}agentPermission" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="sendEmailOnVm" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "agentRole", propOrder = {
    "alwaysRecorded",
    "attachVmToEmail",
    "permissions",
    "sendEmailOnVm"
})
public class AgentRole {

    protected boolean alwaysRecorded;
    protected boolean attachVmToEmail;
    @XmlElement(nillable = true)
    protected List<AgentPermission> permissions;
    protected boolean sendEmailOnVm;

    /**
     * Gets the value of the alwaysRecorded property.
     * 
     */
    public boolean isAlwaysRecorded() {
        return alwaysRecorded;
    }

    /**
     * Sets the value of the alwaysRecorded property.
     * 
     */
    public void setAlwaysRecorded(boolean value) {
        this.alwaysRecorded = value;
    }

    /**
     * Gets the value of the attachVmToEmail property.
     * 
     */
    public boolean isAttachVmToEmail() {
        return attachVmToEmail;
    }

    /**
     * Sets the value of the attachVmToEmail property.
     * 
     */
    public void setAttachVmToEmail(boolean value) {
        this.attachVmToEmail = value;
    }

    /**
     * Gets the value of the permissions property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the permissions property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPermissions().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AgentPermission }
     * 
     * 
     */
    public List<AgentPermission> getPermissions() {
        if (permissions == null) {
            permissions = new ArrayList<AgentPermission>();
        }
        return this.permissions;
    }

    /**
     * Gets the value of the sendEmailOnVm property.
     * 
     */
    public boolean isSendEmailOnVm() {
        return sendEmailOnVm;
    }

    /**
     * Sets the value of the sendEmailOnVm property.
     * 
     */
    public void setSendEmailOnVm(boolean value) {
        this.sendEmailOnVm = value;
    }

}
