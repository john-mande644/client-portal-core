
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for callNowMode.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="callNowMode">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="NONE"/>
 *     &lt;enumeration value="NEW_CRM_ONLY"/>
 *     &lt;enumeration value="NEW_LIST_ONLY"/>
 *     &lt;enumeration value="ANY"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "callNowMode")
@XmlEnum
public enum CallNowMode {

    NONE,
    NEW_CRM_ONLY,
    NEW_LIST_ONLY,
    ANY;

    public String value() {
        return name();
    }

    public static CallNowMode fromValue(String v) {
        return valueOf(v);
    }

}
