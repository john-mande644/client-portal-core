
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for sayAsFormat.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="sayAsFormat">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="NoFormat"/>
 *     &lt;enumeration value="Default"/>
 *     &lt;enumeration value="Date_MDY"/>
 *     &lt;enumeration value="Date_DMY"/>
 *     &lt;enumeration value="Date_YMD"/>
 *     &lt;enumeration value="Date_YM"/>
 *     &lt;enumeration value="Date_MY"/>
 *     &lt;enumeration value="Date_DM"/>
 *     &lt;enumeration value="Date_MD"/>
 *     &lt;enumeration value="Date_Y"/>
 *     &lt;enumeration value="Date_M"/>
 *     &lt;enumeration value="Date_D"/>
 *     &lt;enumeration value="Duration_HMS"/>
 *     &lt;enumeration value="Duration_HM"/>
 *     &lt;enumeration value="Duration_MS"/>
 *     &lt;enumeration value="Duration_H"/>
 *     &lt;enumeration value="Duration_M"/>
 *     &lt;enumeration value="Duration_S"/>
 *     &lt;enumeration value="Net_URI"/>
 *     &lt;enumeration value="Net_EMAIL"/>
 *     &lt;enumeration value="Time_HMS"/>
 *     &lt;enumeration value="Time_HM"/>
 *     &lt;enumeration value="Time_H"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "sayAsFormat")
@XmlEnum
public enum SayAsFormat {

    @XmlEnumValue("NoFormat")
    NO_FORMAT("NoFormat"),
    @XmlEnumValue("Default")
    DEFAULT("Default"),
    @XmlEnumValue("Date_MDY")
    DATE_MDY("Date_MDY"),
    @XmlEnumValue("Date_DMY")
    DATE_DMY("Date_DMY"),
    @XmlEnumValue("Date_YMD")
    DATE_YMD("Date_YMD"),
    @XmlEnumValue("Date_YM")
    DATE_YM("Date_YM"),
    @XmlEnumValue("Date_MY")
    DATE_MY("Date_MY"),
    @XmlEnumValue("Date_DM")
    DATE_DM("Date_DM"),
    @XmlEnumValue("Date_MD")
    DATE_MD("Date_MD"),
    @XmlEnumValue("Date_Y")
    DATE_Y("Date_Y"),
    @XmlEnumValue("Date_M")
    DATE_M("Date_M"),
    @XmlEnumValue("Date_D")
    DATE_D("Date_D"),
    @XmlEnumValue("Duration_HMS")
    DURATION_HMS("Duration_HMS"),
    @XmlEnumValue("Duration_HM")
    DURATION_HM("Duration_HM"),
    @XmlEnumValue("Duration_MS")
    DURATION_MS("Duration_MS"),
    @XmlEnumValue("Duration_H")
    DURATION_H("Duration_H"),
    @XmlEnumValue("Duration_M")
    DURATION_M("Duration_M"),
    @XmlEnumValue("Duration_S")
    DURATION_S("Duration_S"),
    @XmlEnumValue("Net_URI")
    NET_URI("Net_URI"),
    @XmlEnumValue("Net_EMAIL")
    NET_EMAIL("Net_EMAIL"),
    @XmlEnumValue("Time_HMS")
    TIME_HMS("Time_HMS"),
    @XmlEnumValue("Time_HM")
    TIME_HM("Time_HM"),
    @XmlEnumValue("Time_H")
    TIME_H("Time_H");
    private final String value;

    SayAsFormat(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static SayAsFormat fromValue(String v) {
        for (SayAsFormat c: SayAsFormat.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
