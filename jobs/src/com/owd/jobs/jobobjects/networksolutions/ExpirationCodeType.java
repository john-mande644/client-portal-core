
package com.owd.jobs.jobobjects.networksolutions;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ExpirationCodeType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ExpirationCodeType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="Day"/>
 *     &lt;enumeration value="Month"/>
 *     &lt;enumeration value="Never"/>
 *     &lt;enumeration value="Week"/>
 *     &lt;enumeration value="Year"/>
 *     &lt;enumeration value="Custom"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ExpirationCodeType")
@XmlEnum
public enum ExpirationCodeType {

    @XmlEnumValue("Day")
    DAY("Day"),
    @XmlEnumValue("Month")
    MONTH("Month"),
    @XmlEnumValue("Never")
    NEVER("Never"),
    @XmlEnumValue("Week")
    WEEK("Week"),
    @XmlEnumValue("Year")
    YEAR("Year"),
    @XmlEnumValue("Custom")
    CUSTOM("Custom");
    private final String value;

    ExpirationCodeType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ExpirationCodeType fromValue(String v) {
        for (ExpirationCodeType c: ExpirationCodeType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
