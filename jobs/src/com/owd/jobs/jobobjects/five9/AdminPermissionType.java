
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for adminPermissionType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="adminPermissionType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="FullPermissions"/>
 *     &lt;enumeration value="ManageUsers"/>
 *     &lt;enumeration value="ManageSkills"/>
 *     &lt;enumeration value="ManageAgentGroups"/>
 *     &lt;enumeration value="ManageCampaignsStartStop"/>
 *     &lt;enumeration value="ManageCampaignsResetDispositions"/>
 *     &lt;enumeration value="ManageCampaignsResetListPosition"/>
 *     &lt;enumeration value="ManageCampaignsReset"/>
 *     &lt;enumeration value="ManageCampaignsProperties"/>
 *     &lt;enumeration value="ManageLists"/>
 *     &lt;enumeration value="ManageCRM"/>
 *     &lt;enumeration value="ManageDNC"/>
 *     &lt;enumeration value="EditIvr"/>
 *     &lt;enumeration value="EditProfiles"/>
 *     &lt;enumeration value="EditConnectors"/>
 *     &lt;enumeration value="EditDispositions"/>
 *     &lt;enumeration value="EditPrompts"/>
 *     &lt;enumeration value="EditReasonCodes"/>
 *     &lt;enumeration value="EditWorkflowRules"/>
 *     &lt;enumeration value="AccessConfigANI"/>
 *     &lt;enumeration value="EditCallAttachedData"/>
 *     &lt;enumeration value="EditTrustedIPAddresses"/>
 *     &lt;enumeration value="NICEEnabled"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "adminPermissionType")
@XmlEnum
public enum AdminPermissionType {

    @XmlEnumValue("FullPermissions")
    FULL_PERMISSIONS("FullPermissions"),
    @XmlEnumValue("ManageUsers")
    MANAGE_USERS("ManageUsers"),
    @XmlEnumValue("ManageSkills")
    MANAGE_SKILLS("ManageSkills"),
    @XmlEnumValue("ManageAgentGroups")
    MANAGE_AGENT_GROUPS("ManageAgentGroups"),
    @XmlEnumValue("ManageCampaignsStartStop")
    MANAGE_CAMPAIGNS_START_STOP("ManageCampaignsStartStop"),
    @XmlEnumValue("ManageCampaignsResetDispositions")
    MANAGE_CAMPAIGNS_RESET_DISPOSITIONS("ManageCampaignsResetDispositions"),
    @XmlEnumValue("ManageCampaignsResetListPosition")
    MANAGE_CAMPAIGNS_RESET_LIST_POSITION("ManageCampaignsResetListPosition"),
    @XmlEnumValue("ManageCampaignsReset")
    MANAGE_CAMPAIGNS_RESET("ManageCampaignsReset"),
    @XmlEnumValue("ManageCampaignsProperties")
    MANAGE_CAMPAIGNS_PROPERTIES("ManageCampaignsProperties"),
    @XmlEnumValue("ManageLists")
    MANAGE_LISTS("ManageLists"),
    @XmlEnumValue("ManageCRM")
    MANAGE_CRM("ManageCRM"),
    @XmlEnumValue("ManageDNC")
    MANAGE_DNC("ManageDNC"),
    @XmlEnumValue("EditIvr")
    EDIT_IVR("EditIvr"),
    @XmlEnumValue("EditProfiles")
    EDIT_PROFILES("EditProfiles"),
    @XmlEnumValue("EditConnectors")
    EDIT_CONNECTORS("EditConnectors"),
    @XmlEnumValue("EditDispositions")
    EDIT_DISPOSITIONS("EditDispositions"),
    @XmlEnumValue("EditPrompts")
    EDIT_PROMPTS("EditPrompts"),
    @XmlEnumValue("EditReasonCodes")
    EDIT_REASON_CODES("EditReasonCodes"),
    @XmlEnumValue("EditWorkflowRules")
    EDIT_WORKFLOW_RULES("EditWorkflowRules"),
    @XmlEnumValue("AccessConfigANI")
    ACCESS_CONFIG_ANI("AccessConfigANI"),
    @XmlEnumValue("EditCallAttachedData")
    EDIT_CALL_ATTACHED_DATA("EditCallAttachedData"),
    @XmlEnumValue("EditTrustedIPAddresses")
    EDIT_TRUSTED_IP_ADDRESSES("EditTrustedIPAddresses"),
    @XmlEnumValue("NICEEnabled")
    NICE_ENABLED("NICEEnabled");
    private final String value;

    AdminPermissionType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static AdminPermissionType fromValue(String v) {
        for (AdminPermissionType c: AdminPermissionType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
