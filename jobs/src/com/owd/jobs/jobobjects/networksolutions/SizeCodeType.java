
package com.owd.jobs.jobobjects.networksolutions;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SizeCodeType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="SizeCodeType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="Small"/>
 *     &lt;enumeration value="Medium"/>
 *     &lt;enumeration value="Large"/>
 *     &lt;enumeration value="Custom"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "SizeCodeType")
@XmlEnum
public enum SizeCodeType {

    @XmlEnumValue("Small")
    SMALL("Small"),
    @XmlEnumValue("Medium")
    MEDIUM("Medium"),
    @XmlEnumValue("Large")
    LARGE("Large"),
    @XmlEnumValue("Custom")
    CUSTOM("Custom");
    private final String value;

    SizeCodeType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static SizeCodeType fromValue(String v) {
        for (SizeCodeType c: SizeCodeType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
