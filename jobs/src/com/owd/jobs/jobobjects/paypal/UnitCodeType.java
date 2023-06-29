package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for UnitCodeType.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;simpleType name="UnitCodeType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="kg"/>
 *     &lt;enumeration value="lbs"/>
 *     &lt;enumeration value="oz"/>
 *     &lt;enumeration value="cm"/>
 *     &lt;enumeration value="inches"/>
 *     &lt;enumeration value="ft"/>
 *     &lt;enumeration value="CustomCode"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 */
@XmlType(name = "UnitCodeType", namespace = "urn:ebay:apis:eBLBaseComponents")
@XmlEnum
public enum UnitCodeType
{

    @XmlEnumValue("kg")
    KG("kg"),
    @XmlEnumValue("lbs")
    LBS("lbs"),
    @XmlEnumValue("oz")
    OZ("oz"),
    @XmlEnumValue("cm")
    CM("cm"),
    @XmlEnumValue("inches")
    INCHES("inches"),
    @XmlEnumValue("ft")
    FT("ft"),
    @XmlEnumValue("CustomCode")
    CUSTOM_CODE("CustomCode");
    private final String value;

    UnitCodeType(String v)
    {
        value = v;
    }

    public String value()
    {
        return value;
    }

    public static UnitCodeType fromValue(String v)
    {
        for (UnitCodeType c : UnitCodeType.values())
        {
            if (c.value.equals(v))
            {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
