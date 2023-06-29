
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for createAutodialCampaign complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="createAutodialCampaign">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="campaign" type="{http://service.admin.ws.five9.com/v2/}autodialCampaign" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "createAutodialCampaign", propOrder = {
    "campaign"
})
public class CreateAutodialCampaign {

    protected AutodialCampaign campaign;

    /**
     * Gets the value of the campaign property.
     * 
     * @return
     *     possible object is
     *     {@link AutodialCampaign }
     *     
     */
    public AutodialCampaign getCampaign() {
        return campaign;
    }

    /**
     * Sets the value of the campaign property.
     * 
     * @param value
     *     allowed object is
     *     {@link AutodialCampaign }
     *     
     */
    public void setCampaign(AutodialCampaign value) {
        this.campaign = value;
    }

}
