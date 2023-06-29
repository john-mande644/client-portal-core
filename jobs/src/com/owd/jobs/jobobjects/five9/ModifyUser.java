
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for modifyUser complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="modifyUser">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="userGeneralInfo" type="{http://service.admin.ws.five9.com/v2/}userGeneralInfo" minOccurs="0"/>
 *         &lt;element name="rolesToSet" type="{http://service.admin.ws.five9.com/v2/}userRoles" minOccurs="0"/>
 *         &lt;element name="rolesToRemove" type="{http://service.admin.ws.five9.com/v2/}userRoleType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "modifyUser", propOrder = {
    "userGeneralInfo",
    "rolesToSet",
    "rolesToRemove"
})
public class ModifyUser {

    protected UserGeneralInfo userGeneralInfo;
    protected UserRoles rolesToSet;
    protected List<UserRoleType> rolesToRemove;

    /**
     * Gets the value of the userGeneralInfo property.
     * 
     * @return
     *     possible object is
     *     {@link UserGeneralInfo }
     *     
     */
    public UserGeneralInfo getUserGeneralInfo() {
        return userGeneralInfo;
    }

    /**
     * Sets the value of the userGeneralInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link UserGeneralInfo }
     *     
     */
    public void setUserGeneralInfo(UserGeneralInfo value) {
        this.userGeneralInfo = value;
    }

    /**
     * Gets the value of the rolesToSet property.
     * 
     * @return
     *     possible object is
     *     {@link UserRoles }
     *     
     */
    public UserRoles getRolesToSet() {
        return rolesToSet;
    }

    /**
     * Sets the value of the rolesToSet property.
     * 
     * @param value
     *     allowed object is
     *     {@link UserRoles }
     *     
     */
    public void setRolesToSet(UserRoles value) {
        this.rolesToSet = value;
    }

    /**
     * Gets the value of the rolesToRemove property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the rolesToRemove property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRolesToRemove().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link UserRoleType }
     * 
     * 
     */
    public List<UserRoleType> getRolesToRemove() {
        if (rolesToRemove == null) {
            rolesToRemove = new ArrayList<UserRoleType>();
        }
        return this.rolesToRemove;
    }

}
