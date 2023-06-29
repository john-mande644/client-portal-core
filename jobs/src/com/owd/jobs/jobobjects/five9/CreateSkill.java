
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for createSkill complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="createSkill">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="skillInfo" type="{http://service.admin.ws.five9.com/v2/}skillInfo" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "createSkill", propOrder = {
    "skillInfo"
})
public class CreateSkill {

    protected SkillInfo skillInfo;

    /**
     * Gets the value of the skillInfo property.
     * 
     * @return
     *     possible object is
     *     {@link SkillInfo }
     *     
     */
    public SkillInfo getSkillInfo() {
        return skillInfo;
    }

    /**
     * Sets the value of the skillInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link SkillInfo }
     *     
     */
    public void setSkillInfo(SkillInfo value) {
        this.skillInfo = value;
    }

}
