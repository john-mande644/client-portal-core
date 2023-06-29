
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for distributionAlgorithm.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="distributionAlgorithm">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="LongestReadyTime"/>
 *     &lt;enumeration value="LongestReadyTimeExcludeMC"/>
 *     &lt;enumeration value="RoundRobin"/>
 *     &lt;enumeration value="MinCallsHandled"/>
 *     &lt;enumeration value="MinHandleTime"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "distributionAlgorithm")
@XmlEnum
public enum DistributionAlgorithm {

    @XmlEnumValue("LongestReadyTime")
    LONGEST_READY_TIME("LongestReadyTime"),
    @XmlEnumValue("LongestReadyTimeExcludeMC")
    LONGEST_READY_TIME_EXCLUDE_MC("LongestReadyTimeExcludeMC"),
    @XmlEnumValue("RoundRobin")
    ROUND_ROBIN("RoundRobin"),
    @XmlEnumValue("MinCallsHandled")
    MIN_CALLS_HANDLED("MinCallsHandled"),
    @XmlEnumValue("MinHandleTime")
    MIN_HANDLE_TIME("MinHandleTime");
    private final String value;

    DistributionAlgorithm(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static DistributionAlgorithm fromValue(String v) {
        for (DistributionAlgorithm c: DistributionAlgorithm.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
