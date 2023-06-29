
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for agentPermissionType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="agentPermissionType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="MakeTransfer"/>
 *     &lt;enumeration value="ReceiveTransfer"/>
 *     &lt;enumeration value="CreateConference"/>
 *     &lt;enumeration value="MakeCall"/>
 *     &lt;enumeration value="MakeInternalCall"/>
 *     &lt;enumeration value="ProcessVoiceMail"/>
 *     &lt;enumeration value="DeleteVoiceMail"/>
 *     &lt;enumeration value="TransferVoiceMail"/>
 *     &lt;enumeration value="MakeRecordings"/>
 *     &lt;enumeration value="SendMessages"/>
 *     &lt;enumeration value="CreateChatSessions"/>
 *     &lt;enumeration value="TrainingMode"/>
 *     &lt;enumeration value="CannotRemoveCRM"/>
 *     &lt;enumeration value="CannotEditSession"/>
 *     &lt;enumeration value="CallForwarding"/>
 *     &lt;enumeration value="AddingToDNC"/>
 *     &lt;enumeration value="DialManuallyDNC"/>
 *     &lt;enumeration value="CreateCallbacks"/>
 *     &lt;enumeration value="PlayAudioFiles"/>
 *     &lt;enumeration value="SkipCrmInPreviewDialMode"/>
 *     &lt;enumeration value="ManageAvailabilityBySkill"/>
 *     &lt;enumeration value="BrowseWebInEmbeddedBrowser"/>
 *     &lt;enumeration value="ChangePreviewPreferences"/>
 *     &lt;enumeration value="CanWrapCall"/>
 *     &lt;enumeration value="CanPlaceCallOnHold"/>
 *     &lt;enumeration value="CanParkCall"/>
 *     &lt;enumeration value="CanRejectCalls"/>
 *     &lt;enumeration value="CanConfigureAutoAnswer"/>
 *     &lt;enumeration value="NICEEnabled"/>
 *     &lt;enumeration value="ScreenRecording"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "agentPermissionType")
@XmlEnum
public enum AgentPermissionType {

    @XmlEnumValue("MakeTransfer")
    MAKE_TRANSFER("MakeTransfer"),
    @XmlEnumValue("ReceiveTransfer")
    RECEIVE_TRANSFER("ReceiveTransfer"),
    @XmlEnumValue("CreateConference")
    CREATE_CONFERENCE("CreateConference"),
    @XmlEnumValue("MakeCall")
    MAKE_CALL("MakeCall"),
    @XmlEnumValue("MakeInternalCall")
    MAKE_INTERNAL_CALL("MakeInternalCall"),
    @XmlEnumValue("ProcessVoiceMail")
    PROCESS_VOICE_MAIL("ProcessVoiceMail"),
    @XmlEnumValue("DeleteVoiceMail")
    DELETE_VOICE_MAIL("DeleteVoiceMail"),
    @XmlEnumValue("TransferVoiceMail")
    TRANSFER_VOICE_MAIL("TransferVoiceMail"),
    @XmlEnumValue("MakeRecordings")
    MAKE_RECORDINGS("MakeRecordings"),
    @XmlEnumValue("SendMessages")
    SEND_MESSAGES("SendMessages"),
    @XmlEnumValue("CreateChatSessions")
    CREATE_CHAT_SESSIONS("CreateChatSessions"),
    @XmlEnumValue("TrainingMode")
    TRAINING_MODE("TrainingMode"),
    @XmlEnumValue("CannotRemoveCRM")
    CANNOT_REMOVE_CRM("CannotRemoveCRM"),
    @XmlEnumValue("CannotEditSession")
    CANNOT_EDIT_SESSION("CannotEditSession"),
    @XmlEnumValue("CallForwarding")
    CALL_FORWARDING("CallForwarding"),
    @XmlEnumValue("AddingToDNC")
    ADDING_TO_DNC("AddingToDNC"),
    @XmlEnumValue("DialManuallyDNC")
    DIAL_MANUALLY_DNC("DialManuallyDNC"),
    @XmlEnumValue("CreateCallbacks")
    CREATE_CALLBACKS("CreateCallbacks"),
    @XmlEnumValue("PlayAudioFiles")
    PLAY_AUDIO_FILES("PlayAudioFiles"),
    @XmlEnumValue("SkipCrmInPreviewDialMode")
    SKIP_CRM_IN_PREVIEW_DIAL_MODE("SkipCrmInPreviewDialMode"),
    @XmlEnumValue("ManageAvailabilityBySkill")
    MANAGE_AVAILABILITY_BY_SKILL("ManageAvailabilityBySkill"),
    @XmlEnumValue("BrowseWebInEmbeddedBrowser")
    BROWSE_WEB_IN_EMBEDDED_BROWSER("BrowseWebInEmbeddedBrowser"),
    @XmlEnumValue("ChangePreviewPreferences")
    CHANGE_PREVIEW_PREFERENCES("ChangePreviewPreferences"),
    @XmlEnumValue("CanWrapCall")
    CAN_WRAP_CALL("CanWrapCall"),
    @XmlEnumValue("CanPlaceCallOnHold")
    CAN_PLACE_CALL_ON_HOLD("CanPlaceCallOnHold"),
    @XmlEnumValue("CanParkCall")
    CAN_PARK_CALL("CanParkCall"),
    @XmlEnumValue("CanRejectCalls")
    CAN_REJECT_CALLS("CanRejectCalls"),
    @XmlEnumValue("CanConfigureAutoAnswer")
    CAN_CONFIGURE_AUTO_ANSWER("CanConfigureAutoAnswer"),
    @XmlEnumValue("NICEEnabled")
    NICE_ENABLED("NICEEnabled"),
    @XmlEnumValue("ScreenRecording")
    SCREEN_RECORDING("ScreenRecording");
    private final String value;

    AgentPermissionType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static AgentPermissionType fromValue(String v) {
        for (AgentPermissionType c: AgentPermissionType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
