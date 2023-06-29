
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for agentState.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="agentState">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="AVAILABLE_FOR_CALLS"/>
 *     &lt;enumeration value="AVAILABLE_FOR_VMS"/>
 *     &lt;enumeration value="LOGIN"/>
 *     &lt;enumeration value="LOGOUT"/>
 *     &lt;enumeration value="NOT_AVAILABLE_FOR_CALLS"/>
 *     &lt;enumeration value="NOT_AVAILABLE_FOR_VMS"/>
 *     &lt;enumeration value="NOT_READY"/>
 *     &lt;enumeration value="READY"/>
 *     &lt;enumeration value="VM_IN_PROGRESS"/>
 *     &lt;enumeration value="VM_NOT_AVAILABLE"/>
 *     &lt;enumeration value="WAITING"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "agentState")
@XmlEnum
public enum AgentState {

    AVAILABLE_FOR_CALLS,
    AVAILABLE_FOR_VMS,
    LOGIN,
    LOGOUT,
    NOT_AVAILABLE_FOR_CALLS,
    NOT_AVAILABLE_FOR_VMS,
    NOT_READY,
    READY,
    VM_IN_PROGRESS,
    VM_NOT_AVAILABLE,
    WAITING;

    public String value() {
        return name();
    }

    public static AgentState fromValue(String v) {
        return valueOf(v);
    }

}
