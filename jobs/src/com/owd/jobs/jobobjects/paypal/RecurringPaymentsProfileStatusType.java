package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RecurringPaymentsProfileStatusType.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;simpleType name="RecurringPaymentsProfileStatusType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="ActiveProfile"/>
 *     &lt;enumeration value="PendingProfile"/>
 *     &lt;enumeration value="CancelledProfile"/>
 *     &lt;enumeration value="ExpiredProfile"/>
 *     &lt;enumeration value="SuspendedProfile"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 */
@XmlType(name = "RecurringPaymentsProfileStatusType", namespace = "urn:ebay:apis:eBLBaseComponents")
@XmlEnum
public enum RecurringPaymentsProfileStatusType
{

    @XmlEnumValue("ActiveProfile")
    ACTIVE_PROFILE("ActiveProfile"),
    @XmlEnumValue("PendingProfile")
    PENDING_PROFILE("PendingProfile"),
    @XmlEnumValue("CancelledProfile")
    CANCELLED_PROFILE("CancelledProfile"),
    @XmlEnumValue("ExpiredProfile")
    EXPIRED_PROFILE("ExpiredProfile"),
    @XmlEnumValue("SuspendedProfile")
    SUSPENDED_PROFILE("SuspendedProfile");
    private final String value;

    RecurringPaymentsProfileStatusType(String v)
    {
        value = v;
    }

    public String value()
    {
        return value;
    }

    public static RecurringPaymentsProfileStatusType fromValue(String v)
    {
        for (RecurringPaymentsProfileStatusType c : RecurringPaymentsProfileStatusType.values())
        {
            if (c.value.equals(v))
            {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
