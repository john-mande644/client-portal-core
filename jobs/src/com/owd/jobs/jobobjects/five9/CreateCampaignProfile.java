
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for createCampaignProfile complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="createCampaignProfile">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="campaignProfile" type="{http://service.admin.ws.five9.com/v2/}campaignProfileInfo" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "createCampaignProfile", propOrder = {
    "campaignProfile"
})
public class CreateCampaignProfile {

    protected CampaignProfileInfo campaignProfile;

    /**
     * Gets the value of the campaignProfile property.
     * 
     * @return
     *     possible object is
     *     {@link CampaignProfileInfo }
     *     
     */
    public CampaignProfileInfo getCampaignProfile() {
        return campaignProfile;
    }

    /**
     * Sets the value of the campaignProfile property.
     * 
     * @param value
     *     allowed object is
     *     {@link CampaignProfileInfo }
     *     
     */
    public void setCampaignProfile(CampaignProfileInfo value) {
        this.campaignProfile = value;
    }

}
