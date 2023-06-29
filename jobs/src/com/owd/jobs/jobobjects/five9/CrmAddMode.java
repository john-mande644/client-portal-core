
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for crmAddMode.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="crmAddMode">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="ADD_NEW"/>
 *     &lt;enumeration value="DONT_ADD"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "crmAddMode")
@XmlEnum
public enum CrmAddMode {

    ADD_NEW,
    DONT_ADD;

    public String value() {
        return name();
    }

    public static CrmAddMode fromValue(String v) {
        return valueOf(v);
    }

}
