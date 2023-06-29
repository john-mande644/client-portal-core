
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for outboundCampaign complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="outboundCampaign">
 *   &lt;complexContent>
 *     &lt;extension base="{http://service.admin.ws.five9.com/v2/}baseOutboundCampaign">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="actionOnAnswerMachine" type="{http://service.admin.ws.five9.com/v2/}campaignDialingAction" minOccurs="0"/>
 *         &lt;element name="actionOnQueueExpiration" type="{http://service.admin.ws.five9.com/v2/}campaignDialingAction" minOccurs="0"/>
 *         &lt;element name="callAnalysisMode" type="{http://service.admin.ws.five9.com/v2/}callAnalysisMode" minOccurs="0"/>
 *         &lt;element name="callsAgentRatio" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="dialNumberOnTimeout" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="dialingMode" type="{http://service.admin.ws.five9.com/v2/}campaignDialingMode" minOccurs="0"/>
 *         &lt;element name="distributionAlgorithm" type="{http://service.admin.ws.five9.com/v2/}distributionAlgorithm" minOccurs="0"/>
 *         &lt;element name="distributionTimeFrame" type="{http://service.admin.ws.five9.com/v2/}distributionTimeFrame" minOccurs="0"/>
 *         &lt;element name="limitPreviewTime" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="maxDroppedCallsPercentage" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/>
 *         &lt;element name="maxPreviewTime" type="{http://service.admin.ws.five9.com/v2/}timer" minOccurs="0"/>
 *         &lt;element name="maxQueueTime" type="{http://service.admin.ws.five9.com/v2/}timer" minOccurs="0"/>
 *         &lt;element name="monitorDroppedCalls" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "outboundCampaign", propOrder = {
    "actionOnAnswerMachine",
    "actionOnQueueExpiration",
    "callAnalysisMode",
    "callsAgentRatio",
    "dialNumberOnTimeout",
    "dialingMode",
    "distributionAlgorithm",
    "distributionTimeFrame",
    "limitPreviewTime",
    "maxDroppedCallsPercentage",
    "maxPreviewTime",
    "maxQueueTime",
    "monitorDroppedCalls"
})
public class OutboundCampaign
    extends BaseOutboundCampaign
{

    protected CampaignDialingAction actionOnAnswerMachine;
    protected CampaignDialingAction actionOnQueueExpiration;
    protected CallAnalysisMode callAnalysisMode;
    protected Double callsAgentRatio;
    protected Boolean dialNumberOnTimeout;
    protected CampaignDialingMode dialingMode;
    protected DistributionAlgorithm distributionAlgorithm;
    protected DistributionTimeFrame distributionTimeFrame;
    protected Boolean limitPreviewTime;
    protected Float maxDroppedCallsPercentage;
    protected Timer maxPreviewTime;
    protected Timer maxQueueTime;
    protected Boolean monitorDroppedCalls;

    /**
     * Gets the value of the actionOnAnswerMachine property.
     * 
     * @return
     *     possible object is
     *     {@link CampaignDialingAction }
     *     
     */
    public CampaignDialingAction getActionOnAnswerMachine() {
        return actionOnAnswerMachine;
    }

    /**
     * Sets the value of the actionOnAnswerMachine property.
     * 
     * @param value
     *     allowed object is
     *     {@link CampaignDialingAction }
     *     
     */
    public void setActionOnAnswerMachine(CampaignDialingAction value) {
        this.actionOnAnswerMachine = value;
    }

    /**
     * Gets the value of the actionOnQueueExpiration property.
     * 
     * @return
     *     possible object is
     *     {@link CampaignDialingAction }
     *     
     */
    public CampaignDialingAction getActionOnQueueExpiration() {
        return actionOnQueueExpiration;
    }

    /**
     * Sets the value of the actionOnQueueExpiration property.
     * 
     * @param value
     *     allowed object is
     *     {@link CampaignDialingAction }
     *     
     */
    public void setActionOnQueueExpiration(CampaignDialingAction value) {
        this.actionOnQueueExpiration = value;
    }

    /**
     * Gets the value of the callAnalysisMode property.
     * 
     * @return
     *     possible object is
     *     {@link CallAnalysisMode }
     *     
     */
    public CallAnalysisMode getCallAnalysisMode() {
        return callAnalysisMode;
    }

    /**
     * Sets the value of the callAnalysisMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link CallAnalysisMode }
     *     
     */
    public void setCallAnalysisMode(CallAnalysisMode value) {
        this.callAnalysisMode = value;
    }

    /**
     * Gets the value of the callsAgentRatio property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getCallsAgentRatio() {
        return callsAgentRatio;
    }

    /**
     * Sets the value of the callsAgentRatio property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setCallsAgentRatio(Double value) {
        this.callsAgentRatio = value;
    }

    /**
     * Gets the value of the dialNumberOnTimeout property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isDialNumberOnTimeout() {
        return dialNumberOnTimeout;
    }

    /**
     * Sets the value of the dialNumberOnTimeout property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDialNumberOnTimeout(Boolean value) {
        this.dialNumberOnTimeout = value;
    }

    /**
     * Gets the value of the dialingMode property.
     * 
     * @return
     *     possible object is
     *     {@link CampaignDialingMode }
     *     
     */
    public CampaignDialingMode getDialingMode() {
        return dialingMode;
    }

    /**
     * Sets the value of the dialingMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link CampaignDialingMode }
     *     
     */
    public void setDialingMode(CampaignDialingMode value) {
        this.dialingMode = value;
    }

    /**
     * Gets the value of the distributionAlgorithm property.
     * 
     * @return
     *     possible object is
     *     {@link DistributionAlgorithm }
     *     
     */
    public DistributionAlgorithm getDistributionAlgorithm() {
        return distributionAlgorithm;
    }

    /**
     * Sets the value of the distributionAlgorithm property.
     * 
     * @param value
     *     allowed object is
     *     {@link DistributionAlgorithm }
     *     
     */
    public void setDistributionAlgorithm(DistributionAlgorithm value) {
        this.distributionAlgorithm = value;
    }

    /**
     * Gets the value of the distributionTimeFrame property.
     * 
     * @return
     *     possible object is
     *     {@link DistributionTimeFrame }
     *     
     */
    public DistributionTimeFrame getDistributionTimeFrame() {
        return distributionTimeFrame;
    }

    /**
     * Sets the value of the distributionTimeFrame property.
     * 
     * @param value
     *     allowed object is
     *     {@link DistributionTimeFrame }
     *     
     */
    public void setDistributionTimeFrame(DistributionTimeFrame value) {
        this.distributionTimeFrame = value;
    }

    /**
     * Gets the value of the limitPreviewTime property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isLimitPreviewTime() {
        return limitPreviewTime;
    }

    /**
     * Sets the value of the limitPreviewTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setLimitPreviewTime(Boolean value) {
        this.limitPreviewTime = value;
    }

    /**
     * Gets the value of the maxDroppedCallsPercentage property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getMaxDroppedCallsPercentage() {
        return maxDroppedCallsPercentage;
    }

    /**
     * Sets the value of the maxDroppedCallsPercentage property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setMaxDroppedCallsPercentage(Float value) {
        this.maxDroppedCallsPercentage = value;
    }

    /**
     * Gets the value of the maxPreviewTime property.
     * 
     * @return
     *     possible object is
     *     {@link Timer }
     *     
     */
    public Timer getMaxPreviewTime() {
        return maxPreviewTime;
    }

    /**
     * Sets the value of the maxPreviewTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link Timer }
     *     
     */
    public void setMaxPreviewTime(Timer value) {
        this.maxPreviewTime = value;
    }

    /**
     * Gets the value of the maxQueueTime property.
     * 
     * @return
     *     possible object is
     *     {@link Timer }
     *     
     */
    public Timer getMaxQueueTime() {
        return maxQueueTime;
    }

    /**
     * Sets the value of the maxQueueTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link Timer }
     *     
     */
    public void setMaxQueueTime(Timer value) {
        this.maxQueueTime = value;
    }

    /**
     * Gets the value of the monitorDroppedCalls property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isMonitorDroppedCalls() {
        return monitorDroppedCalls;
    }

    /**
     * Sets the value of the monitorDroppedCalls property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setMonitorDroppedCalls(Boolean value) {
        this.monitorDroppedCalls = value;
    }

}
