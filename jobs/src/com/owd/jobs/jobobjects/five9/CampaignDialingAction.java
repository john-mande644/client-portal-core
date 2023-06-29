
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for campaignDialingAction complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="campaignDialingAction">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="actionArgument" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="actionType" type="{http://service.admin.ws.five9.com/v2/}campaignDialingActionType" minOccurs="0"/>
 *         &lt;element name="maxWaitTime" type="{http://service.admin.ws.five9.com/v2/}timer" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "campaignDialingAction", propOrder = {
    "actionArgument",
    "actionType",
    "maxWaitTime"
})
public class CampaignDialingAction {

    protected String actionArgument;
    protected CampaignDialingActionType actionType;
    protected Timer maxWaitTime;

    /**
     * Gets the value of the actionArgument property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getActionArgument() {
        return actionArgument;
    }

    /**
     * Sets the value of the actionArgument property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setActionArgument(String value) {
        this.actionArgument = value;
    }

    /**
     * Gets the value of the actionType property.
     * 
     * @return
     *     possible object is
     *     {@link CampaignDialingActionType }
     *     
     */
    public CampaignDialingActionType getActionType() {
        return actionType;
    }

    /**
     * Sets the value of the actionType property.
     * 
     * @param value
     *     allowed object is
     *     {@link CampaignDialingActionType }
     *     
     */
    public void setActionType(CampaignDialingActionType value) {
        this.actionType = value;
    }

    /**
     * Gets the value of the maxWaitTime property.
     * 
     * @return
     *     possible object is
     *     {@link Timer }
     *     
     */
    public Timer getMaxWaitTime() {
        return maxWaitTime;
    }

    /**
     * Sets the value of the maxWaitTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link Timer }
     *     
     */
    public void setMaxWaitTime(Timer value) {
        this.maxWaitTime = value;
    }

}
