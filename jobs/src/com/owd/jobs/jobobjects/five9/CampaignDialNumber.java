
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for campaignDialNumber.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="campaignDialNumber">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="Primary"/>
 *     &lt;enumeration value="Alt1"/>
 *     &lt;enumeration value="Alt2"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "campaignDialNumber")
@XmlEnum
public enum CampaignDialNumber {

    @XmlEnumValue("Primary")
    PRIMARY("Primary"),
    @XmlEnumValue("Alt1")
    ALT_1("Alt1"),
    @XmlEnumValue("Alt2")
    ALT_2("Alt2");
    private final String value;

    CampaignDialNumber(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static CampaignDialNumber fromValue(String v) {
        for (CampaignDialNumber c: CampaignDialNumber.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
