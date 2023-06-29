
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for modifyCampaignProfileDispositions complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="modifyCampaignProfileDispositions">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="profileName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="addDispositionCounts" type="{http://service.admin.ws.five9.com/v2/}dispositionCount" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="removeDispositionCounts" type="{http://service.admin.ws.five9.com/v2/}dispositionCount" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "modifyCampaignProfileDispositions", propOrder = {
    "profileName",
    "addDispositionCounts",
    "removeDispositionCounts"
})
public class ModifyCampaignProfileDispositions {

    protected String profileName;
    protected List<DispositionCount> addDispositionCounts;
    protected List<DispositionCount> removeDispositionCounts;

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
     * Gets the value of the addDispositionCounts property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the addDispositionCounts property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAddDispositionCounts().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DispositionCount }
     * 
     * 
     */
    public List<DispositionCount> getAddDispositionCounts() {
        if (addDispositionCounts == null) {
            addDispositionCounts = new ArrayList<DispositionCount>();
        }
        return this.addDispositionCounts;
    }

    /**
     * Gets the value of the removeDispositionCounts property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the removeDispositionCounts property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRemoveDispositionCounts().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DispositionCount }
     * 
     * 
     */
    public List<DispositionCount> getRemoveDispositionCounts() {
        if (removeDispositionCounts == null) {
            removeDispositionCounts = new ArrayList<DispositionCount>();
        }
        return this.removeDispositionCounts;
    }

}
