
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for deleteFromContactsCsv complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="deleteFromContactsCsv">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="crmDeleteSettings" type="{http://service.admin.ws.five9.com/v2/}crmDeleteSettings" minOccurs="0"/>
 *         &lt;element name="csvData" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "deleteFromContactsCsv", propOrder = {
    "crmDeleteSettings",
    "csvData"
})
public class DeleteFromContactsCsv {

    protected CrmDeleteSettings crmDeleteSettings;
    protected String csvData;

    /**
     * Gets the value of the crmDeleteSettings property.
     * 
     * @return
     *     possible object is
     *     {@link CrmDeleteSettings }
     *     
     */
    public CrmDeleteSettings getCrmDeleteSettings() {
        return crmDeleteSettings;
    }

    /**
     * Sets the value of the crmDeleteSettings property.
     * 
     * @param value
     *     allowed object is
     *     {@link CrmDeleteSettings }
     *     
     */
    public void setCrmDeleteSettings(CrmDeleteSettings value) {
        this.crmDeleteSettings = value;
    }

    /**
     * Gets the value of the csvData property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCsvData() {
        return csvData;
    }

    /**
     * Sets the value of the csvData property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCsvData(String value) {
        this.csvData = value;
    }

}
