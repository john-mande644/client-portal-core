
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for updateCrmRecord complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="updateCrmRecord">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="crmUpdateSettings" type="{http://service.admin.ws.five9.com/v2/}crmUpdateSettings" minOccurs="0"/>
 *         &lt;element name="record" type="{http://service.admin.ws.five9.com/v2/}recordData" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "updateCrmRecord", propOrder = {
    "crmUpdateSettings",
    "record"
})
public class UpdateCrmRecord {

    protected CrmUpdateSettings crmUpdateSettings;
    protected RecordData record;

    /**
     * Gets the value of the crmUpdateSettings property.
     * 
     * @return
     *     possible object is
     *     {@link CrmUpdateSettings }
     *     
     */
    public CrmUpdateSettings getCrmUpdateSettings() {
        return crmUpdateSettings;
    }

    /**
     * Sets the value of the crmUpdateSettings property.
     * 
     * @param value
     *     allowed object is
     *     {@link CrmUpdateSettings }
     *     
     */
    public void setCrmUpdateSettings(CrmUpdateSettings value) {
        this.crmUpdateSettings = value;
    }

    /**
     * Gets the value of the record property.
     * 
     * @return
     *     possible object is
     *     {@link RecordData }
     *     
     */
    public RecordData getRecord() {
        return record;
    }

    /**
     * Sets the value of the record property.
     * 
     * @param value
     *     allowed object is
     *     {@link RecordData }
     *     
     */
    public void setRecord(RecordData value) {
        this.record = value;
    }

}
