
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for campaignDialingOrder.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="campaignDialingOrder">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="PrimaryAlt1Alt2"/>
 *     &lt;enumeration value="PrimaryAlt2Alt1"/>
 *     &lt;enumeration value="Alt1PrimaryAlt2"/>
 *     &lt;enumeration value="Alt1Alt2Primary"/>
 *     &lt;enumeration value="Alt2PrimaryAlt1"/>
 *     &lt;enumeration value="Alt2Alt1Primary"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "campaignDialingOrder")
@XmlEnum
public enum CampaignDialingOrder {

    @XmlEnumValue("PrimaryAlt1Alt2")
    PRIMARY_ALT_1_ALT_2("PrimaryAlt1Alt2"),
    @XmlEnumValue("PrimaryAlt2Alt1")
    PRIMARY_ALT_2_ALT_1("PrimaryAlt2Alt1"),
    @XmlEnumValue("Alt1PrimaryAlt2")
    ALT_1_PRIMARY_ALT_2("Alt1PrimaryAlt2"),
    @XmlEnumValue("Alt1Alt2Primary")
    ALT_1_ALT_2_PRIMARY("Alt1Alt2Primary"),
    @XmlEnumValue("Alt2PrimaryAlt1")
    ALT_2_PRIMARY_ALT_1("Alt2PrimaryAlt1"),
    @XmlEnumValue("Alt2Alt1Primary")
    ALT_2_ALT_1_PRIMARY("Alt2Alt1Primary");
    private final String value;

    CampaignDialingOrder(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static CampaignDialingOrder fromValue(String v) {
        for (CampaignDialingOrder c: CampaignDialingOrder.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
