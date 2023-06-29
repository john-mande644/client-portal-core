
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for updateContacts complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="updateContacts">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="crmUpdateSettings" type="{http://service.admin.ws.five9.com/v2/}crmUpdateSettings" minOccurs="0"/>
 *         &lt;element name="importData" type="{http://service.admin.ws.five9.com/v2/}importData" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "updateContacts", propOrder = {
    "crmUpdateSettings",
    "importData"
})
public class UpdateContacts {

    protected CrmUpdateSettings crmUpdateSettings;
    protected ImportData importData;

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
     * Gets the value of the importData property.
     * 
     * @return
     *     possible object is
     *     {@link ImportData }
     *     
     */
    public ImportData getImportData() {
        return importData;
    }

    /**
     * Sets the value of the importData property.
     * 
     * @param value
     *     allowed object is
     *     {@link ImportData }
     *     
     */
    public void setImportData(ImportData value) {
        this.importData = value;
    }

}
