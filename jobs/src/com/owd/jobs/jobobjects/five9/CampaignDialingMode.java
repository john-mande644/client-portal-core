
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for campaignDialingMode.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="campaignDialingMode">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="PREDICTIVE"/>
 *     &lt;enumeration value="PROGRESSIVE"/>
 *     &lt;enumeration value="PREVIEW"/>
 *     &lt;enumeration value="POWER"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "campaignDialingMode")
@XmlEnum
public enum CampaignDialingMode {

    PREDICTIVE,
    PROGRESSIVE,
    PREVIEW,
    POWER;

    public String value() {
        return name();
    }

    public static CampaignDialingMode fromValue(String v) {
        return valueOf(v);
    }

}
