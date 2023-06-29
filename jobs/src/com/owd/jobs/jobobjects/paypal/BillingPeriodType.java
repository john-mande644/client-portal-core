package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BillingPeriodType.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;simpleType name="BillingPeriodType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="NoBillingPeriodType"/>
 *     &lt;enumeration value="Day"/>
 *     &lt;enumeration value="Week"/>
 *     &lt;enumeration value="SemiMonth"/>
 *     &lt;enumeration value="Month"/>
 *     &lt;enumeration value="Year"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 */
@XmlType(name = "BillingPeriodType", namespace = "urn:ebay:apis:eBLBaseComponents")
@XmlEnum
public enum BillingPeriodType
{

    @XmlEnumValue("NoBillingPeriodType")
    NO_BILLING_PERIOD_TYPE("NoBillingPeriodType"),
    @XmlEnumValue("Day")
    DAY("Day"),
    @XmlEnumValue("Week")
    WEEK("Week"),
    @XmlEnumValue("SemiMonth")
    SEMI_MONTH("SemiMonth"),
    @XmlEnumValue("Month")
    MONTH("Month"),
    @XmlEnumValue("Year")
    YEAR("Year");
    private final String value;

    BillingPeriodType(String v)
    {
        value = v;
    }

    public String value()
    {
        return value;
    }

    public static BillingPeriodType fromValue(String v)
    {
        for (BillingPeriodType c : BillingPeriodType.values())
        {
            if (c.value.equals(v))
            {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
