
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for supervisorPermissionType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="supervisorPermissionType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="Users"/>
 *     &lt;enumeration value="Agents"/>
 *     &lt;enumeration value="CallMonitoring"/>
 *     &lt;enumeration value="Stations"/>
 *     &lt;enumeration value="ChatSessions"/>
 *     &lt;enumeration value="Campaigns"/>
 *     &lt;enumeration value="CampaignManagement"/>
 *     &lt;enumeration value="AllSkills"/>
 *     &lt;enumeration value="BillingInfo"/>
 *     &lt;enumeration value="BargeInMonitor"/>
 *     &lt;enumeration value="WhisperMonitor"/>
 *     &lt;enumeration value="ViewDataForAllAgentGroups"/>
 *     &lt;enumeration value="ReviewVoiceRecordings"/>
 *     &lt;enumeration value="EditAgentSkills"/>
 *     &lt;enumeration value="NICEEnabled"/>
 *     &lt;enumeration value="CanAccessDashboardMenu"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "supervisorPermissionType")
@XmlEnum
public enum SupervisorPermissionType {

    @XmlEnumValue("Users")
    USERS("Users"),
    @XmlEnumValue("Agents")
    AGENTS("Agents"),
    @XmlEnumValue("CallMonitoring")
    CALL_MONITORING("CallMonitoring"),
    @XmlEnumValue("Stations")
    STATIONS("Stations"),
    @XmlEnumValue("ChatSessions")
    CHAT_SESSIONS("ChatSessions"),
    @XmlEnumValue("Campaigns")
    CAMPAIGNS("Campaigns"),
    @XmlEnumValue("CampaignManagement")
    CAMPAIGN_MANAGEMENT("CampaignManagement"),
    @XmlEnumValue("AllSkills")
    ALL_SKILLS("AllSkills"),
    @XmlEnumValue("BillingInfo")
    BILLING_INFO("BillingInfo"),
    @XmlEnumValue("BargeInMonitor")
    BARGE_IN_MONITOR("BargeInMonitor"),
    @XmlEnumValue("WhisperMonitor")
    WHISPER_MONITOR("WhisperMonitor"),
    @XmlEnumValue("ViewDataForAllAgentGroups")
    VIEW_DATA_FOR_ALL_AGENT_GROUPS("ViewDataForAllAgentGroups"),
    @XmlEnumValue("ReviewVoiceRecordings")
    REVIEW_VOICE_RECORDINGS("ReviewVoiceRecordings"),
    @XmlEnumValue("EditAgentSkills")
    EDIT_AGENT_SKILLS("EditAgentSkills"),
    @XmlEnumValue("NICEEnabled")
    NICE_ENABLED("NICEEnabled"),
    @XmlEnumValue("CanAccessDashboardMenu")
    CAN_ACCESS_DASHBOARD_MENU("CanAccessDashboardMenu");
    private final String value;

    SupervisorPermissionType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static SupervisorPermissionType fromValue(String v) {
        for (SupervisorPermissionType c: SupervisorPermissionType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
