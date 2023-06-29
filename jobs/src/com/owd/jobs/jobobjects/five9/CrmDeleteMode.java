
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for crmDeleteMode.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="crmDeleteMode">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="DELETE_ALL"/>
 *     &lt;enumeration value="DELETE_SOLE_MATCHES"/>
 *     &lt;enumeration value="DELETE_EXCEPT_FIRST"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "crmDeleteMode")
@XmlEnum
public enum CrmDeleteMode {

    DELETE_ALL,
    DELETE_SOLE_MATCHES,
    DELETE_EXCEPT_FIRST;

    public String value() {
        return name();
    }

    public static CrmDeleteMode fromValue(String v) {
        return valueOf(v);
    }

}
