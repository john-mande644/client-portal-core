
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for generalCampaign complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="generalCampaign">
 *   &lt;complexContent>
 *     &lt;extension base="{http://service.admin.ws.five9.com/v2/}campaign">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="autoRecord" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="callWrapup" type="{http://service.admin.ws.five9.com/v2/}campaignCallWrapup" minOccurs="0"/>
 *         &lt;element name="ftpHost" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ftpPassword" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ftpUser" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="recordingNameAsSid" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="useFtp" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "generalCampaign", propOrder = {
    "autoRecord",
    "callWrapup",
    "ftpHost",
    "ftpPassword",
    "ftpUser",
    "recordingNameAsSid",
    "useFtp"
})
@XmlSeeAlso({
    BaseOutboundCampaign.class,
    InboundCampaign.class
})
public class GeneralCampaign
    extends Campaign
{

    protected Boolean autoRecord;
    protected CampaignCallWrapup callWrapup;
    protected String ftpHost;
    protected String ftpPassword;
    protected String ftpUser;
    protected Boolean recordingNameAsSid;
    protected Boolean useFtp;

    /**
     * Gets the value of the autoRecord property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isAutoRecord() {
        return autoRecord;
    }

    /**
     * Sets the value of the autoRecord property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setAutoRecord(Boolean value) {
        this.autoRecord = value;
    }

    /**
     * Gets the value of the callWrapup property.
     * 
     * @return
     *     possible object is
     *     {@link CampaignCallWrapup }
     *     
     */
    public CampaignCallWrapup getCallWrapup() {
        return callWrapup;
    }

    /**
     * Sets the value of the callWrapup property.
     * 
     * @param value
     *     allowed object is
     *     {@link CampaignCallWrapup }
     *     
     */
    public void setCallWrapup(CampaignCallWrapup value) {
        this.callWrapup = value;
    }

    /**
     * Gets the value of the ftpHost property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFtpHost() {
        return ftpHost;
    }

    /**
     * Sets the value of the ftpHost property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFtpHost(String value) {
        this.ftpHost = value;
    }

    /**
     * Gets the value of the ftpPassword property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFtpPassword() {
        return ftpPassword;
    }

    /**
     * Sets the value of the ftpPassword property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFtpPassword(String value) {
        this.ftpPassword = value;
    }

    /**
     * Gets the value of the ftpUser property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFtpUser() {
        return ftpUser;
    }

    /**
     * Sets the value of the ftpUser property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFtpUser(String value) {
        this.ftpUser = value;
    }

    /**
     * Gets the value of the recordingNameAsSid property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isRecordingNameAsSid() {
        return recordingNameAsSid;
    }

    /**
     * Sets the value of the recordingNameAsSid property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setRecordingNameAsSid(Boolean value) {
        this.recordingNameAsSid = value;
    }

    /**
     * Gets the value of the useFtp property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isUseFtp() {
        return useFtp;
    }

    /**
     * Sets the value of the useFtp property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setUseFtp(Boolean value) {
        this.useFtp = value;
    }

}
