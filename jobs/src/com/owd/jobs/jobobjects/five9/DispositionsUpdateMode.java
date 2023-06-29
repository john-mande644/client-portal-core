
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for dispositionsUpdateMode.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="dispositionsUpdateMode">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="UPDATE_ALL"/>
 *     &lt;enumeration value="UPDATE_IF_SOLE_CRM_MATCH"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "dispositionsUpdateMode")
@XmlEnum
public enum DispositionsUpdateMode {

    UPDATE_ALL,
    UPDATE_IF_SOLE_CRM_MATCH;

    public String value() {
        return name();
    }

    public static DispositionsUpdateMode fromValue(String v) {
        return valueOf(v);
    }

}
