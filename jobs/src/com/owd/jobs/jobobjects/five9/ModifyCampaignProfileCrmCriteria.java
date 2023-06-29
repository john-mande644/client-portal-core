
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for modifyCampaignProfileCrmCriteria complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="modifyCampaignProfileCrmCriteria">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="profileName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="grouping" type="{http://service.admin.ws.five9.com/v2/}crmCriteriaGrouping" minOccurs="0"/>
 *         &lt;element name="addCriteria" type="{http://service.admin.ws.five9.com/v2/}campaignFilterCriterion" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="removeCriteria" type="{http://service.admin.ws.five9.com/v2/}campaignFilterCriterion" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "modifyCampaignProfileCrmCriteria", propOrder = {
    "profileName",
    "grouping",
    "addCriteria",
    "removeCriteria"
})
public class ModifyCampaignProfileCrmCriteria {

    protected String profileName;
    protected CrmCriteriaGrouping grouping;
    protected List<CampaignFilterCriterion> addCriteria;
    protected List<CampaignFilterCriterion> removeCriteria;

    /**
     * Gets the value of the profileName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProfileName() {
        return profileName;
    }

    /**
     * Sets the value of the profileName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProfileName(String value) {
        this.profileName = value;
    }

    /**
     * Gets the value of the grouping property.
     * 
     * @return
     *     possible object is
     *     {@link CrmCriteriaGrouping }
     *     
     */
    public CrmCriteriaGrouping getGrouping() {
        return grouping;
    }

    /**
     * Sets the value of the grouping property.
     * 
     * @param value
     *     allowed object is
     *     {@link CrmCriteriaGrouping }
     *     
     */
    public void setGrouping(CrmCriteriaGrouping value) {
        this.grouping = value;
    }

    /**
     * Gets the value of the addCriteria property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the addCriteria property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAddCriteria().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CampaignFilterCriterion }
     * 
     * 
     */
    public List<CampaignFilterCriterion> getAddCriteria() {
        if (addCriteria == null) {
            addCriteria = new ArrayList<CampaignFilterCriterion>();
        }
        return this.addCriteria;
    }

    /**
     * Gets the value of the removeCriteria property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the removeCriteria property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRemoveCriteria().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CampaignFilterCriterion }
     * 
     * 
     */
    public List<CampaignFilterCriterion> getRemoveCriteria() {
        if (removeCriteria == null) {
            removeCriteria = new ArrayList<CampaignFilterCriterion>();
        }
        return this.removeCriteria;
    }

}
