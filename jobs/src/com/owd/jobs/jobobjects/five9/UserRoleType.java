
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for userRoleType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="userRoleType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="DomainAdmin"/>
 *     &lt;enumeration value="Agent"/>
 *     &lt;enumeration value="Supervisor"/>
 *     &lt;enumeration value="Reporting"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "userRoleType")
@XmlEnum
public enum UserRoleType {

    @XmlEnumValue("DomainAdmin")
    DOMAIN_ADMIN("DomainAdmin"),
    @XmlEnumValue("Agent")
    AGENT("Agent"),
    @XmlEnumValue("Supervisor")
    SUPERVISOR("Supervisor"),
    @XmlEnumValue("Reporting")
    REPORTING("Reporting");
    private final String value;

    UserRoleType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static UserRoleType fromValue(String v) {
        for (UserRoleType c: UserRoleType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
