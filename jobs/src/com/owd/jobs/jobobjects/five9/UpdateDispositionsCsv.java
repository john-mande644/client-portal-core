
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for updateDispositionsCsv complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="updateDispositionsCsv">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="campaignName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DispositionsUpdateSettings" type="{http://service.admin.ws.five9.com/v2/}dispositionsUpdateSettings" minOccurs="0"/>
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
@XmlType(name = "updateDispositionsCsv", propOrder = {
    "campaignName",
    "dispositionsUpdateSettings",
    "csvData"
})
public class UpdateDispositionsCsv {

    protected String campaignName;
    @XmlElement(name = "DispositionsUpdateSettings")
    protected DispositionsUpdateSettings dispositionsUpdateSettings;
    protected String csvData;

    /**
     * Gets the value of the campaignName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCampaignName() {
        return campaignName;
    }

    /**
     * Sets the value of the campaignName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCampaignName(String value) {
        this.campaignName = value;
    }

    /**
     * Gets the value of the dispositionsUpdateSettings property.
     * 
     * @return
     *     possible object is
     *     {@link DispositionsUpdateSettings }
     *     
     */
    public DispositionsUpdateSettings getDispositionsUpdateSettings() {
        return dispositionsUpdateSettings;
    }

    /**
     * Sets the value of the dispositionsUpdateSettings property.
     * 
     * @param value
     *     allowed object is
     *     {@link DispositionsUpdateSettings }
     *     
     */
    public void setDispositionsUpdateSettings(DispositionsUpdateSettings value) {
        this.dispositionsUpdateSettings = value;
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
