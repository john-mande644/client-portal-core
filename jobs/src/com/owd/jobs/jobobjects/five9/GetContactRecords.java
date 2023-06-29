
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getContactRecords complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getContactRecords">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="lookupCriteria" type="{http://service.admin.ws.five9.com/v2/}crmLookupCriteria" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getContactRecords", propOrder = {
    "lookupCriteria"
})
public class GetContactRecords {

    protected CrmLookupCriteria lookupCriteria;

    /**
     * Gets the value of the lookupCriteria property.
     * 
     * @return
     *     possible object is
     *     {@link CrmLookupCriteria }
     *     
     */
    public CrmLookupCriteria getLookupCriteria() {
        return lookupCriteria;
    }

    /**
     * Sets the value of the lookupCriteria property.
     * 
     * @param value
     *     allowed object is
     *     {@link CrmLookupCriteria }
     *     
     */
    public void setLookupCriteria(CrmLookupCriteria value) {
        this.lookupCriteria = value;
    }

}
