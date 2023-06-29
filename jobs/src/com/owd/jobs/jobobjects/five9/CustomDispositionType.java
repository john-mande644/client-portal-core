
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for customDispositionType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="customDispositionType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="FinalDisp"/>
 *     &lt;enumeration value="FinalApplyToCampaigns"/>
 *     &lt;enumeration value="AddActiveNumber"/>
 *     &lt;enumeration value="AddAndFinalize"/>
 *     &lt;enumeration value="AddAllNumbers"/>
 *     &lt;enumeration value="DoNotDial"/>
 *     &lt;enumeration value="RedialNumber"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "customDispositionType")
@XmlEnum
public enum CustomDispositionType {

    @XmlEnumValue("FinalDisp")
    FINAL_DISP("FinalDisp"),
    @XmlEnumValue("FinalApplyToCampaigns")
    FINAL_APPLY_TO_CAMPAIGNS("FinalApplyToCampaigns"),
    @XmlEnumValue("AddActiveNumber")
    ADD_ACTIVE_NUMBER("AddActiveNumber"),
    @XmlEnumValue("AddAndFinalize")
    ADD_AND_FINALIZE("AddAndFinalize"),
    @XmlEnumValue("AddAllNumbers")
    ADD_ALL_NUMBERS("AddAllNumbers"),
    @XmlEnumValue("DoNotDial")
    DO_NOT_DIAL("DoNotDial"),
    @XmlEnumValue("RedialNumber")
    REDIAL_NUMBER("RedialNumber");
    private final String value;

    CustomDispositionType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static CustomDispositionType fromValue(String v) {
        for (CustomDispositionType c: CustomDispositionType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
