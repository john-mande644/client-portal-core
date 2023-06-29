
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
 * <p>Java class for campaignProfileFilter complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="campaignProfileFilter">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="crmCriteria" type="{http://service.admin.ws.five9.com/v2/}campaignFilterCriterion" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="grouping" type="{http://service.admin.ws.five9.com/v2/}crmCriteriaGrouping" minOccurs="0"/>
 *         &lt;element name="orderByFields" type="{http://service.admin.ws.five9.com/v2/}orderByField" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "campaignProfileFilter", propOrder = {
    "crmCriteria",
    "grouping",
    "orderByFields"
})
public class CampaignProfileFilter {

    @XmlElement(nillable = true)
    protected List<CampaignFilterCriterion> crmCriteria;
    protected CrmCriteriaGrouping grouping;
    @XmlElement(nillable = true)
    protected List<OrderByField> orderByFields;

    /**
     * Gets the value of the crmCriteria property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the crmCriteria property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCrmCriteria().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CampaignFilterCriterion }
     * 
     * 
     */
    public List<CampaignFilterCriterion> getCrmCriteria() {
        if (crmCriteria == null) {
            crmCriteria = new ArrayList<CampaignFilterCriterion>();
        }
        return this.crmCriteria;
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
     * Gets the value of the orderByFields property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the orderByFields property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOrderByFields().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OrderByField }
     * 
     * 
     */
    public List<OrderByField> getOrderByFields() {
        if (orderByFields == null) {
            orderByFields = new ArrayList<OrderByField>();
        }
        return this.orderByFields;
    }

}
