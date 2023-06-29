
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for passwordPolicyEntries.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="passwordPolicyEntries">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="MIN_LENGTH"/>
 *     &lt;enumeration value="SPECIAL_SMBL"/>
 *     &lt;enumeration value="CAPITAL_SMBL"/>
 *     &lt;enumeration value="DIGIT"/>
 *     &lt;enumeration value="LOCKOUT"/>
 *     &lt;enumeration value="ADMINLOCKOUT"/>
 *     &lt;enumeration value="REUSELIFE"/>
 *     &lt;enumeration value="TIMELIFE"/>
 *     &lt;enumeration value="QUESTCANTCONTAINPWD"/>
 *     &lt;enumeration value="CANRESETPASSWORD"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "passwordPolicyEntries")
@XmlEnum
public enum PasswordPolicyEntries {

    MIN_LENGTH,
    SPECIAL_SMBL,
    CAPITAL_SMBL,
    DIGIT,
    LOCKOUT,
    ADMINLOCKOUT,
    REUSELIFE,
    TIMELIFE,
    QUESTCANTCONTAINPWD,
    CANRESETPASSWORD;

    public String value() {
        return name();
    }

    public static PasswordPolicyEntries fromValue(String v) {
        return valueOf(v);
    }

}
