
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for listDialingMode.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="listDialingMode">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="VERTICAL_DIALING"/>
 *     &lt;enumeration value="LIST_PENETRATION"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "listDialingMode")
@XmlEnum
public enum ListDialingMode {

    VERTICAL_DIALING,
    LIST_PENETRATION;

    public String value() {
        return name();
    }

    public static ListDialingMode fromValue(String v) {
        return valueOf(v);
    }

}
