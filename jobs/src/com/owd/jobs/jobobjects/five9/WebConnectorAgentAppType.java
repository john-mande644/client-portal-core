
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for webConnectorAgentAppType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="webConnectorAgentAppType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="EmbeddedBrowser"/>
 *     &lt;enumeration value="ExternalBrowser"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "webConnectorAgentAppType")
@XmlEnum
public enum WebConnectorAgentAppType {

    @XmlEnumValue("EmbeddedBrowser")
    EMBEDDED_BROWSER("EmbeddedBrowser"),
    @XmlEnumValue("ExternalBrowser")
    EXTERNAL_BROWSER("ExternalBrowser");
    private final String value;

    WebConnectorAgentAppType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static WebConnectorAgentAppType fromValue(String v) {
        for (WebConnectorAgentAppType c: WebConnectorAgentAppType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
