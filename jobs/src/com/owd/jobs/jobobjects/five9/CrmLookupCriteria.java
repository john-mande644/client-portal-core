
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
 * <p>Java class for crmLookupCriteria complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="crmLookupCriteria">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="contactIdField" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="criteria" type="{http://service.admin.ws.five9.com/v2/}crmFieldCriterion" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "crmLookupCriteria", propOrder = {
    "contactIdField",
    "criteria"
})
public class CrmLookupCriteria {

    protected String contactIdField;
    @XmlElement(nillable = true)
    protected List<CrmFieldCriterion> criteria;

    /**
     * Gets the value of the contactIdField property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContactIdField() {
        return contactIdField;
    }

    /**
     * Sets the value of the contactIdField property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContactIdField(String value) {
        this.contactIdField = value;
    }

    /**
     * Gets the value of the criteria property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the criteria property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCriteria().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CrmFieldCriterion }
     * 
     * 
     */
    public List<CrmFieldCriterion> getCriteria() {
        if (criteria == null) {
            criteria = new ArrayList<CrmFieldCriterion>();
        }
        return this.criteria;
    }

}
