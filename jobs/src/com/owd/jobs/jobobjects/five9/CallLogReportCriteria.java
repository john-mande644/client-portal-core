
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
 * <p>Java class for callLogReportCriteria complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="callLogReportCriteria">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="ANI" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="agents" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="callTypes" type="{http://service.admin.ws.five9.com/v2/}callType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="campaigns" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="DNIS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dispositions" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="lists" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="skillGroups" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "callLogReportCriteria", propOrder = {
    "ani",
    "agents",
    "callTypes",
    "campaigns",
    "dnis",
    "dispositions",
    "lists",
    "skillGroups"
})
public class CallLogReportCriteria {

    @XmlElement(name = "ANI")
    protected String ani;
    @XmlElement(nillable = true)
    protected List<String> agents;
    @XmlElement(nillable = true)
    protected List<CallType> callTypes;
    @XmlElement(nillable = true)
    protected List<String> campaigns;
    @XmlElement(name = "DNIS")
    protected String dnis;
    @XmlElement(nillable = true)
    protected List<String> dispositions;
    @XmlElement(nillable = true)
    protected List<String> lists;
    @XmlElement(nillable = true)
    protected List<String> skillGroups;

    /**
     * Gets the value of the ani property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getANI() {
        return ani;
    }

    /**
     * Sets the value of the ani property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setANI(String value) {
        this.ani = value;
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
     * Gets the value of the callTypes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the callTypes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCallTypes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CallType }
     * 
     * 
     */
    public List<CallType> getCallTypes() {
        if (callTypes == null) {
            callTypes = new ArrayList<CallType>();
        }
        return this.callTypes;
    }

    /**
     * Gets the value of the campaigns property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the campaigns property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCampaigns().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getCampaigns() {
        if (campaigns == null) {
            campaigns = new ArrayList<String>();
        }
        return this.campaigns;
    }

    /**
     * Gets the value of the dnis property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDNIS() {
        return dnis;
    }

    /**
     * Sets the value of the dnis property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDNIS(String value) {
        this.dnis = value;
    }

    /**
     * Gets the value of the dispositions property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dispositions property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDispositions().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getDispositions() {
        if (dispositions == null) {
            dispositions = new ArrayList<String>();
        }
        return this.dispositions;
    }

    /**
     * Gets the value of the lists property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the lists property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLists().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getLists() {
        if (lists == null) {
            lists = new ArrayList<String>();
        }
        return this.lists;
    }

    /**
     * Gets the value of the skillGroups property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the skillGroups property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSkillGroups().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getSkillGroups() {
        if (skillGroups == null) {
            skillGroups = new ArrayList<String>();
        }
        return this.skillGroups;
    }

}
