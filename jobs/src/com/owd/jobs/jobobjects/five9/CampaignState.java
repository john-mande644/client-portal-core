
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for campaignState.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="campaignState">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="NOT_RUNNING"/>
 *     &lt;enumeration value="STARTING"/>
 *     &lt;enumeration value="RUNNING"/>
 *     &lt;enumeration value="STOPPING"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "campaignState")
@XmlEnum
public enum CampaignState {

    NOT_RUNNING,
    STARTING,
    RUNNING,
    STOPPING;

    public String value() {
        return name();
    }

    public static CampaignState fromValue(String v) {
        return valueOf(v);
    }

}
