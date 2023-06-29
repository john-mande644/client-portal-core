
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for updateDispositionsFtp complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="updateDispositionsFtp">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="campaignName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="updateSettings" type="{http://service.admin.ws.five9.com/v2/}dispositionsUpdateSettings" minOccurs="0"/>
 *         &lt;element name="ftpSettings" type="{http://service.admin.ws.five9.com/v2/}ftpImportSettings" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "updateDispositionsFtp", propOrder = {
    "campaignName",
    "updateSettings",
    "ftpSettings"
})
public class UpdateDispositionsFtp {

    protected String campaignName;
    protected DispositionsUpdateSettings updateSettings;
    protected FtpImportSettings ftpSettings;

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
     * Gets the value of the updateSettings property.
     * 
     * @return
     *     possible object is
     *     {@link DispositionsUpdateSettings }
     *     
     */
    public DispositionsUpdateSettings getUpdateSettings() {
        return updateSettings;
    }

    /**
     * Sets the value of the updateSettings property.
     * 
     * @param value
     *     allowed object is
     *     {@link DispositionsUpdateSettings }
     *     
     */
    public void setUpdateSettings(DispositionsUpdateSettings value) {
        this.updateSettings = value;
    }

    /**
     * Gets the value of the ftpSettings property.
     * 
     * @return
     *     possible object is
     *     {@link FtpImportSettings }
     *     
     */
    public FtpImportSettings getFtpSettings() {
        return ftpSettings;
    }

    /**
     * Sets the value of the ftpSettings property.
     * 
     * @param value
     *     allowed object is
     *     {@link FtpImportSettings }
     *     
     */
    public void setFtpSettings(FtpImportSettings value) {
        this.ftpSettings = value;
    }

}
