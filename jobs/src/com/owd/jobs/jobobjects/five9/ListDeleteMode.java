
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for listDeleteMode.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="listDeleteMode">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="DELETE_ALL"/>
 *     &lt;enumeration value="DELETE_IF_SOLE_CRM_MATCH"/>
 *     &lt;enumeration value="DELETE_EXCEPT_FIRST"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "listDeleteMode")
@XmlEnum
public enum ListDeleteMode {

    DELETE_ALL,
    DELETE_IF_SOLE_CRM_MATCH,
    DELETE_EXCEPT_FIRST;

    public String value() {
        return name();
    }

    public static ListDeleteMode fromValue(String v) {
        return valueOf(v);
    }

}
