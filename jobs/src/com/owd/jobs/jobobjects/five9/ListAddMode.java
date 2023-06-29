
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for listAddMode.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="listAddMode">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="ADD_FIRST"/>
 *     &lt;enumeration value="ADD_ALL"/>
 *     &lt;enumeration value="ADD_IF_SOLE_CRM_MATCH"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "listAddMode")
@XmlEnum
public enum ListAddMode {

    ADD_FIRST,
    ADD_ALL,
    ADD_IF_SOLE_CRM_MATCH;

    public String value() {
        return name();
    }

    public static ListAddMode fromValue(String v) {
        return valueOf(v);
    }

}
