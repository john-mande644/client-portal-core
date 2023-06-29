
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for agentAvailability.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="agentAvailability">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="ReadyToReceiveCalls"/>
 *     &lt;enumeration value="ReadyToReceiveCallsOrBusy"/>
 *     &lt;enumeration value="LoggedIn"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "agentAvailability")
@XmlEnum
public enum AgentAvailability {

    @XmlEnumValue("ReadyToReceiveCalls")
    READY_TO_RECEIVE_CALLS("ReadyToReceiveCalls"),
    @XmlEnumValue("ReadyToReceiveCallsOrBusy")
    READY_TO_RECEIVE_CALLS_OR_BUSY("ReadyToReceiveCallsOrBusy"),
    @XmlEnumValue("LoggedIn")
    LOGGED_IN("LoggedIn");
    private final String value;

    AgentAvailability(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static AgentAvailability fromValue(String v) {
        for (AgentAvailability c: AgentAvailability.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
