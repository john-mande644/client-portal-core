
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for callType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="callType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="AGENT"/>
 *     &lt;enumeration value="AUTODIAL"/>
 *     &lt;enumeration value="INBOUND"/>
 *     &lt;enumeration value="INBOUND_VOICEMAIL"/>
 *     &lt;enumeration value="INTERNAL"/>
 *     &lt;enumeration value="INTERNAL_VOICEMAIL"/>
 *     &lt;enumeration value="OUTBOUND"/>
 *     &lt;enumeration value="OUTBOUND_PREVIEW"/>
 *     &lt;enumeration value="OUTBOUND_VOICEMAIL"/>
 *     &lt;enumeration value="TEST"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "callType")
@XmlEnum
public enum CallType {

    AGENT,
    AUTODIAL,
    INBOUND,
    INBOUND_VOICEMAIL,
    INTERNAL,
    INTERNAL_VOICEMAIL,
    OUTBOUND,
    OUTBOUND_PREVIEW,
    OUTBOUND_VOICEMAIL,
    TEST;

    public String value() {
        return name();
    }

    public static CallType fromValue(String v) {
        return valueOf(v);
    }

}
