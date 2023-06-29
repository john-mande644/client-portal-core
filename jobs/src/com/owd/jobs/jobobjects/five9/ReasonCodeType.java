
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for reasonCodeType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="reasonCodeType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="NotReady"/>
 *     &lt;enumeration value="Logout"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "reasonCodeType")
@XmlEnum
public enum ReasonCodeType {

    @XmlEnumValue("NotReady")
    NOT_READY("NotReady"),
    @XmlEnumValue("Logout")
    LOGOUT("Logout");
    private final String value;

    ReasonCodeType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ReasonCodeType fromValue(String v) {
        for (ReasonCodeType c: ReasonCodeType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
