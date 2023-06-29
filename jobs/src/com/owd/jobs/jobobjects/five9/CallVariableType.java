
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for callVariableType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="callVariableType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="STRING"/>
 *     &lt;enumeration value="NUMBER"/>
 *     &lt;enumeration value="DATE"/>
 *     &lt;enumeration value="TIME"/>
 *     &lt;enumeration value="DATE_TIME"/>
 *     &lt;enumeration value="CURRENCY"/>
 *     &lt;enumeration value="BOOLEAN"/>
 *     &lt;enumeration value="PERCENT"/>
 *     &lt;enumeration value="EMAIL"/>
 *     &lt;enumeration value="URL"/>
 *     &lt;enumeration value="PHONE"/>
 *     &lt;enumeration value="TIME_PERIOD"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "callVariableType")
@XmlEnum
public enum CallVariableType {

    STRING,
    NUMBER,
    DATE,
    TIME,
    DATE_TIME,
    CURRENCY,
    BOOLEAN,
    PERCENT,
    EMAIL,
    URL,
    PHONE,
    TIME_PERIOD;

    public String value() {
        return name();
    }

    public static CallVariableType fromValue(String v) {
        return valueOf(v);
    }

}
