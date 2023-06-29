
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for contactFieldMapping.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="contactFieldMapping">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="None"/>
 *     &lt;enumeration value="LastAgent"/>
 *     &lt;enumeration value="LastDisposition"/>
 *     &lt;enumeration value="LastSystemDisposition"/>
 *     &lt;enumeration value="LastAgentDisposition"/>
 *     &lt;enumeration value="LastDispositionDateTime"/>
 *     &lt;enumeration value="LastSystemDispositionDateTime"/>
 *     &lt;enumeration value="LastAgentDispositionDateTime"/>
 *     &lt;enumeration value="LastAttemptedNumber"/>
 *     &lt;enumeration value="LastAttemptedNumberN1N2N3"/>
 *     &lt;enumeration value="LastCampaign"/>
 *     &lt;enumeration value="AttemptsForLastCampaign"/>
 *     &lt;enumeration value="LastList"/>
 *     &lt;enumeration value="CreatedDateTime"/>
 *     &lt;enumeration value="LastModifiedDateTime"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "contactFieldMapping")
@XmlEnum
public enum ContactFieldMapping {

    @XmlEnumValue("None")
    NONE("None"),
    @XmlEnumValue("LastAgent")
    LAST_AGENT("LastAgent"),
    @XmlEnumValue("LastDisposition")
    LAST_DISPOSITION("LastDisposition"),
    @XmlEnumValue("LastSystemDisposition")
    LAST_SYSTEM_DISPOSITION("LastSystemDisposition"),
    @XmlEnumValue("LastAgentDisposition")
    LAST_AGENT_DISPOSITION("LastAgentDisposition"),
    @XmlEnumValue("LastDispositionDateTime")
    LAST_DISPOSITION_DATE_TIME("LastDispositionDateTime"),
    @XmlEnumValue("LastSystemDispositionDateTime")
    LAST_SYSTEM_DISPOSITION_DATE_TIME("LastSystemDispositionDateTime"),
    @XmlEnumValue("LastAgentDispositionDateTime")
    LAST_AGENT_DISPOSITION_DATE_TIME("LastAgentDispositionDateTime"),
    @XmlEnumValue("LastAttemptedNumber")
    LAST_ATTEMPTED_NUMBER("LastAttemptedNumber"),
    @XmlEnumValue("LastAttemptedNumberN1N2N3")
    LAST_ATTEMPTED_NUMBER_N_1_N_2_N_3("LastAttemptedNumberN1N2N3"),
    @XmlEnumValue("LastCampaign")
    LAST_CAMPAIGN("LastCampaign"),
    @XmlEnumValue("AttemptsForLastCampaign")
    ATTEMPTS_FOR_LAST_CAMPAIGN("AttemptsForLastCampaign"),
    @XmlEnumValue("LastList")
    LAST_LIST("LastList"),
    @XmlEnumValue("CreatedDateTime")
    CREATED_DATE_TIME("CreatedDateTime"),
    @XmlEnumValue("LastModifiedDateTime")
    LAST_MODIFIED_DATE_TIME("LastModifiedDateTime");
    private final String value;

    ContactFieldMapping(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ContactFieldMapping fromValue(String v) {
        for (ContactFieldMapping c: ContactFieldMapping.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
