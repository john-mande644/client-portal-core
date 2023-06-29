
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for campaignType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="campaignType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="OUTBOUND"/>
 *     &lt;enumeration value="AUTODIAL"/>
 *     &lt;enumeration value="INBOUND"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "campaignType")
@XmlEnum
public enum CampaignType {

    OUTBOUND,
    AUTODIAL,
    INBOUND;

    public String value() {
        return name();
    }

    public static CampaignType fromValue(String v) {
        return valueOf(v);
    }

}
