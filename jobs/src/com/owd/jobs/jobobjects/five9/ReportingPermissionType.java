
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for reportingPermissionType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="reportingPermissionType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="CanScheduleReportsViaFtp"/>
 *     &lt;enumeration value="CanAccessRecordingsColumn"/>
 *     &lt;enumeration value="NICEEnabled"/>
 *     &lt;enumeration value="CanViewStandardReports"/>
 *     &lt;enumeration value="CanViewCustomReports"/>
 *     &lt;enumeration value="CanViewScheduledReports"/>
 *     &lt;enumeration value="CanViewRecentReports"/>
 *     &lt;enumeration value="CanViewRelease7Reports"/>
 *     &lt;enumeration value="CanViewCannedReports"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "reportingPermissionType")
@XmlEnum
public enum ReportingPermissionType {

    @XmlEnumValue("CanScheduleReportsViaFtp")
    CAN_SCHEDULE_REPORTS_VIA_FTP("CanScheduleReportsViaFtp"),
    @XmlEnumValue("CanAccessRecordingsColumn")
    CAN_ACCESS_RECORDINGS_COLUMN("CanAccessRecordingsColumn"),
    @XmlEnumValue("NICEEnabled")
    NICE_ENABLED("NICEEnabled"),
    @XmlEnumValue("CanViewStandardReports")
    CAN_VIEW_STANDARD_REPORTS("CanViewStandardReports"),
    @XmlEnumValue("CanViewCustomReports")
    CAN_VIEW_CUSTOM_REPORTS("CanViewCustomReports"),
    @XmlEnumValue("CanViewScheduledReports")
    CAN_VIEW_SCHEDULED_REPORTS("CanViewScheduledReports"),
    @XmlEnumValue("CanViewRecentReports")
    CAN_VIEW_RECENT_REPORTS("CanViewRecentReports"),
    @XmlEnumValue("CanViewRelease7Reports")
    CAN_VIEW_RELEASE_7_REPORTS("CanViewRelease7Reports"),
    @XmlEnumValue("CanViewCannedReports")
    CAN_VIEW_CANNED_REPORTS("CanViewCannedReports");
    private final String value;

    ReportingPermissionType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ReportingPermissionType fromValue(String v) {
        for (ReportingPermissionType c: ReportingPermissionType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
