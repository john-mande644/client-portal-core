
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for webConnectorCTIWebServicesType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="webConnectorCTIWebServicesType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="CurrentBrowserWindow"/>
 *     &lt;enumeration value="NewBrowserWindow"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "webConnectorCTIWebServicesType")
@XmlEnum
public enum WebConnectorCTIWebServicesType {

    @XmlEnumValue("CurrentBrowserWindow")
    CURRENT_BROWSER_WINDOW("CurrentBrowserWindow"),
    @XmlEnumValue("NewBrowserWindow")
    NEW_BROWSER_WINDOW("NewBrowserWindow");
    private final String value;

    WebConnectorCTIWebServicesType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static WebConnectorCTIWebServicesType fromValue(String v) {
        for (WebConnectorCTIWebServicesType c: WebConnectorCTIWebServicesType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
