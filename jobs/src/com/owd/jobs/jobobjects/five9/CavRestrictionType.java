
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for cavRestrictionType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="cavRestrictionType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="MinValue"/>
 *     &lt;enumeration value="MaxValue"/>
 *     &lt;enumeration value="Regexp"/>
 *     &lt;enumeration value="Required"/>
 *     &lt;enumeration value="Set"/>
 *     &lt;enumeration value="Multiset"/>
 *     &lt;enumeration value="Precision"/>
 *     &lt;enumeration value="Scale"/>
 *     &lt;enumeration value="TimeFormat"/>
 *     &lt;enumeration value="DateFormat"/>
 *     &lt;enumeration value="TimePeriodFormat"/>
 *     &lt;enumeration value="CurrencyType"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "cavRestrictionType")
@XmlEnum
public enum CavRestrictionType {

    @XmlEnumValue("MinValue")
    MIN_VALUE("MinValue"),
    @XmlEnumValue("MaxValue")
    MAX_VALUE("MaxValue"),
    @XmlEnumValue("Regexp")
    REGEXP("Regexp"),
    @XmlEnumValue("Required")
    REQUIRED("Required"),
    @XmlEnumValue("Set")
    SET("Set"),
    @XmlEnumValue("Multiset")
    MULTISET("Multiset"),
    @XmlEnumValue("Precision")
    PRECISION("Precision"),
    @XmlEnumValue("Scale")
    SCALE("Scale"),
    @XmlEnumValue("TimeFormat")
    TIME_FORMAT("TimeFormat"),
    @XmlEnumValue("DateFormat")
    DATE_FORMAT("DateFormat"),
    @XmlEnumValue("TimePeriodFormat")
    TIME_PERIOD_FORMAT("TimePeriodFormat"),
    @XmlEnumValue("CurrencyType")
    CURRENCY_TYPE("CurrencyType");
    private final String value;

    CavRestrictionType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static CavRestrictionType fromValue(String v) {
        for (CavRestrictionType c: CavRestrictionType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
