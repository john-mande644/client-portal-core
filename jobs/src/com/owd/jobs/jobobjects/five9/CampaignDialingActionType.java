
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for campaignDialingActionType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="campaignDialingActionType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="DROP_CALL"/>
 *     &lt;enumeration value="PLAY_PROMPT"/>
 *     &lt;enumeration value="START_IVR_SCRIPT"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "campaignDialingActionType")
@XmlEnum
public enum CampaignDialingActionType {

    DROP_CALL,
    PLAY_PROMPT,
    START_IVR_SCRIPT;

    public String value() {
        return name();
    }

    public static CampaignDialingActionType fromValue(String v) {
        return valueOf(v);
    }

}
