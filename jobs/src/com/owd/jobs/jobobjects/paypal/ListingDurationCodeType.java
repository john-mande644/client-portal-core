package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ListingDurationCodeType.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;simpleType name="ListingDurationCodeType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="Days_1"/>
 *     &lt;enumeration value="Days_3"/>
 *     &lt;enumeration value="Days_5"/>
 *     &lt;enumeration value="Days_7"/>
 *     &lt;enumeration value="Days_10"/>
 *     &lt;enumeration value="Days_30"/>
 *     &lt;enumeration value="Days_60"/>
 *     &lt;enumeration value="Days_90"/>
 *     &lt;enumeration value="Days_120"/>
 *     &lt;enumeration value="GTC"/>
 *     &lt;enumeration value="CustomCode"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 */
@XmlType(name = "ListingDurationCodeType", namespace = "urn:ebay:apis:eBLBaseComponents")
@XmlEnum
public enum ListingDurationCodeType
{

    @XmlEnumValue("Days_1")
    DAYS_1("Days_1"),
    @XmlEnumValue("Days_3")
    DAYS_3("Days_3"),
    @XmlEnumValue("Days_5")
    DAYS_5("Days_5"),
    @XmlEnumValue("Days_7")
    DAYS_7("Days_7"),
    @XmlEnumValue("Days_10")
    DAYS_10("Days_10"),
    @XmlEnumValue("Days_30")
    DAYS_30("Days_30"),
    @XmlEnumValue("Days_60")
    DAYS_60("Days_60"),
    @XmlEnumValue("Days_90")
    DAYS_90("Days_90"),
    @XmlEnumValue("Days_120")
    DAYS_120("Days_120"),
    GTC("GTC"),
    @XmlEnumValue("CustomCode")
    CUSTOM_CODE("CustomCode");
    private final String value;

    ListingDurationCodeType(String v)
    {
        value = v;
    }

    public String value()
    {
        return value;
    }

    public static ListingDurationCodeType fromValue(String v)
    {
        for (ListingDurationCodeType c : ListingDurationCodeType.values())
        {
            if (c.value.equals(v))
            {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
