
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for callAnalysisMode.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="callAnalysisMode">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="NO_ANALYSIS"/>
 *     &lt;enumeration value="FAX_ONLY"/>
 *     &lt;enumeration value="FAX_AND_ANSWERING_MACHINE"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "callAnalysisMode")
@XmlEnum
public enum CallAnalysisMode {

    NO_ANALYSIS,
    FAX_ONLY,
    FAX_AND_ANSWERING_MACHINE;

    public String value() {
        return name();
    }

    public static CallAnalysisMode fromValue(String v) {
        return valueOf(v);
    }

}
