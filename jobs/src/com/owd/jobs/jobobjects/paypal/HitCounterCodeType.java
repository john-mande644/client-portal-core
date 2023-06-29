package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for HitCounterCodeType.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;simpleType name="HitCounterCodeType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="NoHitCounter"/>
 *     &lt;enumeration value="HonestyStyle"/>
 *     &lt;enumeration value="GreenLED"/>
 *     &lt;enumeration value="Hidden"/>
 *     &lt;enumeration value="CustomCode"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 */
@XmlType(name = "HitCounterCodeType", namespace = "urn:ebay:apis:eBLBaseComponents")
@XmlEnum
public enum HitCounterCodeType
{

    @XmlEnumValue("NoHitCounter")
    NO_HIT_COUNTER("NoHitCounter"),
    @XmlEnumValue("HonestyStyle")
    HONESTY_STYLE("HonestyStyle"),
    @XmlEnumValue("GreenLED")
    GREEN_LED("GreenLED"),
    @XmlEnumValue("Hidden")
    HIDDEN("Hidden"),
    @XmlEnumValue("CustomCode")
    CUSTOM_CODE("CustomCode");
    private final String value;

    HitCounterCodeType(String v)
    {
        value = v;
    }

    public String value()
    {
        return value;
    }

    public static HitCounterCodeType fromValue(String v)
    {
        for (HitCounterCodeType c : HitCounterCodeType.values())
        {
            if (c.value.equals(v))
            {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
