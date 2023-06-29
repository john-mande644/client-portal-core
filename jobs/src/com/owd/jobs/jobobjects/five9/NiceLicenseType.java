
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for niceLicenseType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="niceLicenseType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="USER"/>
 *     &lt;enumeration value="SCREEN_RECORDED_AGENT"/>
 *     &lt;enumeration value="IEX_SCHEDULED_AGENT"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "niceLicenseType")
@XmlEnum
public enum NiceLicenseType {

    USER,
    SCREEN_RECORDED_AGENT,
    IEX_SCHEDULED_AGENT;

    public String value() {
        return name();
    }

    public static NiceLicenseType fromValue(String v) {
        return valueOf(v);
    }

}
