
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for webConnectorTriggerType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="webConnectorTriggerType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="OnCallAccepted"/>
 *     &lt;enumeration value="OnCallDisconnected"/>
 *     &lt;enumeration value="ManuallyStarted"/>
 *     &lt;enumeration value="ManuallyStartedAllowDuringPreviews"/>
 *     &lt;enumeration value="OnPreview"/>
 *     &lt;enumeration value="OnContactSelection"/>
 *     &lt;enumeration value="OnWarmTransferInitiation"/>
 *     &lt;enumeration value="OnCallDispositioned"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "webConnectorTriggerType")
@XmlEnum
public enum WebConnectorTriggerType {

    @XmlEnumValue("OnCallAccepted")
    ON_CALL_ACCEPTED("OnCallAccepted"),
    @XmlEnumValue("OnCallDisconnected")
    ON_CALL_DISCONNECTED("OnCallDisconnected"),
    @XmlEnumValue("ManuallyStarted")
    MANUALLY_STARTED("ManuallyStarted"),
    @XmlEnumValue("ManuallyStartedAllowDuringPreviews")
    MANUALLY_STARTED_ALLOW_DURING_PREVIEWS("ManuallyStartedAllowDuringPreviews"),
    @XmlEnumValue("OnPreview")
    ON_PREVIEW("OnPreview"),
    @XmlEnumValue("OnContactSelection")
    ON_CONTACT_SELECTION("OnContactSelection"),
    @XmlEnumValue("OnWarmTransferInitiation")
    ON_WARM_TRANSFER_INITIATION("OnWarmTransferInitiation"),
    @XmlEnumValue("OnCallDispositioned")
    ON_CALL_DISPOSITIONED("OnCallDispositioned");
    private final String value;

    WebConnectorTriggerType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static WebConnectorTriggerType fromValue(String v) {
        for (WebConnectorTriggerType c: WebConnectorTriggerType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
