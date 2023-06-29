
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for distributionTimeFrame.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="distributionTimeFrame">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="minutes15"/>
 *     &lt;enumeration value="minutes30"/>
 *     &lt;enumeration value="minutes60"/>
 *     &lt;enumeration value="hours8"/>
 *     &lt;enumeration value="hours24"/>
 *     &lt;enumeration value="thisDay"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "distributionTimeFrame")
@XmlEnum
public enum DistributionTimeFrame {

    @XmlEnumValue("minutes15")
    MINUTES_15("minutes15"),
    @XmlEnumValue("minutes30")
    MINUTES_30("minutes30"),
    @XmlEnumValue("minutes60")
    MINUTES_60("minutes60"),
    @XmlEnumValue("hours8")
    HOURS_8("hours8"),
    @XmlEnumValue("hours24")
    HOURS_24("hours24"),
    @XmlEnumValue("thisDay")
    THIS_DAY("thisDay");
    private final String value;

    DistributionTimeFrame(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static DistributionTimeFrame fromValue(String v) {
        for (DistributionTimeFrame c: DistributionTimeFrame.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
