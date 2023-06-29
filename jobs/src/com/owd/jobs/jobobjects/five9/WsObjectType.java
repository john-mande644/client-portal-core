
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for wsObjectType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="wsObjectType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="AgentGroup"/>
 *     &lt;enumeration value="Campaign"/>
 *     &lt;enumeration value="CampaignProfile"/>
 *     &lt;enumeration value="CrmField"/>
 *     &lt;enumeration value="Disposition"/>
 *     &lt;enumeration value="List"/>
 *     &lt;enumeration value="Prompt"/>
 *     &lt;enumeration value="ReasonCode"/>
 *     &lt;enumeration value="Skill"/>
 *     &lt;enumeration value="User"/>
 *     &lt;enumeration value="UserProfile"/>
 *     &lt;enumeration value="IvrScript"/>
 *     &lt;enumeration value="CallVariableGroup"/>
 *     &lt;enumeration value="CallVariable"/>
 *     &lt;enumeration value="Connector"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "wsObjectType")
@XmlEnum
public enum WsObjectType {

    @XmlEnumValue("AgentGroup")
    AGENT_GROUP("AgentGroup"),
    @XmlEnumValue("Campaign")
    CAMPAIGN("Campaign"),
    @XmlEnumValue("CampaignProfile")
    CAMPAIGN_PROFILE("CampaignProfile"),
    @XmlEnumValue("CrmField")
    CRM_FIELD("CrmField"),
    @XmlEnumValue("Disposition")
    DISPOSITION("Disposition"),
    @XmlEnumValue("List")
    LIST("List"),
    @XmlEnumValue("Prompt")
    PROMPT("Prompt"),
    @XmlEnumValue("ReasonCode")
    REASON_CODE("ReasonCode"),
    @XmlEnumValue("Skill")
    SKILL("Skill"),
    @XmlEnumValue("User")
    USER("User"),
    @XmlEnumValue("UserProfile")
    USER_PROFILE("UserProfile"),
    @XmlEnumValue("IvrScript")
    IVR_SCRIPT("IvrScript"),
    @XmlEnumValue("CallVariableGroup")
    CALL_VARIABLE_GROUP("CallVariableGroup"),
    @XmlEnumValue("CallVariable")
    CALL_VARIABLE("CallVariable"),
    @XmlEnumValue("Connector")
    CONNECTOR("Connector");
    private final String value;

    WsObjectType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static WsObjectType fromValue(String v) {
        for (WsObjectType c: WsObjectType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
