
package com.owd.jobs.jobobjects.networksolutions;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for StatusCodeType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="StatusCodeType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="Failure"/>
 *     &lt;enumeration value="Success"/>
 *     &lt;enumeration value="PartialFailure"/>
 *     &lt;enumeration value="Custom"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "StatusCodeType")
@XmlEnum
public enum StatusCodeType {

    @XmlEnumValue("Failure")
    FAILURE("Failure"),
    @XmlEnumValue("Success")
    SUCCESS("Success"),
    @XmlEnumValue("PartialFailure")
    PARTIAL_FAILURE("PartialFailure"),
    @XmlEnumValue("Custom")
    CUSTOM("Custom");
    private final String value;

    StatusCodeType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static StatusCodeType fromValue(String v) {
        for (StatusCodeType c: StatusCodeType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
