
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for contactFieldDisplay.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="contactFieldDisplay">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="Short"/>
 *     &lt;enumeration value="Long"/>
 *     &lt;enumeration value="Invisible"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "contactFieldDisplay")
@XmlEnum
public enum ContactFieldDisplay {

    @XmlEnumValue("Short")
    SHORT("Short"),
    @XmlEnumValue("Long")
    LONG("Long"),
    @XmlEnumValue("Invisible")
    INVISIBLE("Invisible");
    private final String value;

    ContactFieldDisplay(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ContactFieldDisplay fromValue(String v) {
        for (ContactFieldDisplay c: ContactFieldDisplay.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
