
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for modifyCampaignProfileFilterOrder complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="modifyCampaignProfileFilterOrder">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="campaignProfile" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="addOrderByField" type="{http://service.admin.ws.five9.com/v2/}orderByField" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="removeOrderByField" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "modifyCampaignProfileFilterOrder", propOrder = {
    "campaignProfile",
    "addOrderByField",
    "removeOrderByField"
})
public class ModifyCampaignProfileFilterOrder {

    protected String campaignProfile;
    protected List<OrderByField> addOrderByField;
    protected List<String> removeOrderByField;

    /**
     * Gets the value of the campaignProfile property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCampaignProfile() {
        return campaignProfile;
    }

    /**
     * Sets the value of the campaignProfile property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCampaignProfile(String value) {
        this.campaignProfile = value;
    }

    /**
     * Gets the value of the addOrderByField property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the addOrderByField property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAddOrderByField().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OrderByField }
     * 
     * 
     */
    public List<OrderByField> getAddOrderByField() {
        if (addOrderByField == null) {
            addOrderByField = new ArrayList<OrderByField>();
        }
        return this.addOrderByField;
    }

    /**
     * Gets the value of the removeOrderByField property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the removeOrderByField property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRemoveOrderByField().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getRemoveOrderByField() {
        if (removeOrderByField == null) {
            removeOrderByField = new ArrayList<String>();
        }
        return this.removeOrderByField;
    }

}
