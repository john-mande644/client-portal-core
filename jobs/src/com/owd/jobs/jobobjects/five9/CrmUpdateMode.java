
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for crmUpdateMode.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="crmUpdateMode">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="UPDATE_FIRST"/>
 *     &lt;enumeration value="UPDATE_ALL"/>
 *     &lt;enumeration value="UPDATE_SOLE_MATCHES"/>
 *     &lt;enumeration value="DONT_UPDATE"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "crmUpdateMode")
@XmlEnum
public enum CrmUpdateMode {

    UPDATE_FIRST,
    UPDATE_ALL,
    UPDATE_SOLE_MATCHES,
    DONT_UPDATE;

    public String value() {
        return name();
    }

    public static CrmUpdateMode fromValue(String v) {
        return valueOf(v);
    }

}
