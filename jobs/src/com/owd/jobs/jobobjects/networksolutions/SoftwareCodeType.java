
package com.owd.jobs.jobobjects.networksolutions;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SoftwareCodeType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="SoftwareCodeType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="Professional"/>
 *     &lt;enumeration value="Basic"/>
 *     &lt;enumeration value="Custom"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "SoftwareCodeType")
@XmlEnum
public enum SoftwareCodeType {

    @XmlEnumValue("Professional")
    PROFESSIONAL("Professional"),
    @XmlEnumValue("Basic")
    BASIC("Basic"),
    @XmlEnumValue("Custom")
    CUSTOM("Custom");
    private final String value;

    SoftwareCodeType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static SoftwareCodeType fromValue(String v) {
        for (SoftwareCodeType c: SoftwareCodeType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
