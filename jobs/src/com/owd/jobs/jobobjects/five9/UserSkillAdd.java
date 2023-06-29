
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for userSkillAdd complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="userSkillAdd">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="userSkill" type="{http://service.admin.ws.five9.com/v2/}userSkill" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "userSkillAdd", propOrder = {
    "userSkill"
})
public class UserSkillAdd {

    protected UserSkill userSkill;

    /**
     * Gets the value of the userSkill property.
     * 
     * @return
     *     possible object is
     *     {@link UserSkill }
     *     
     */
    public UserSkill getUserSkill() {
        return userSkill;
    }

    /**
     * Sets the value of the userSkill property.
     * 
     * @param value
     *     allowed object is
     *     {@link UserSkill }
     *     
     */
    public void setUserSkill(UserSkill value) {
        this.userSkill = value;
    }

}
