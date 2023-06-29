package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ShippingPackageCodeType.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;simpleType name="ShippingPackageCodeType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="None"/>
 *     &lt;enumeration value="Letter"/>
 *     &lt;enumeration value="LargeEnvelope"/>
 *     &lt;enumeration value="USPSLargePack"/>
 *     &lt;enumeration value="VeryLargePack"/>
 *     &lt;enumeration value="UPSLetter"/>
 *     &lt;enumeration value="USPSFlatRateEnvelope"/>
 *     &lt;enumeration value="PackageThickEnvelope"/>
 *     &lt;enumeration value="CustomCode"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 */
@XmlType(name = "ShippingPackageCodeType", namespace = "urn:ebay:apis:eBLBaseComponents")
@XmlEnum
public enum ShippingPackageCodeType
{

    @XmlEnumValue("None")
    NONE("None"),
    @XmlEnumValue("Letter")
    LETTER("Letter"),
    @XmlEnumValue("LargeEnvelope")
    LARGE_ENVELOPE("LargeEnvelope"),
    @XmlEnumValue("USPSLargePack")
    USPS_LARGE_PACK("USPSLargePack"),
    @XmlEnumValue("VeryLargePack")
    VERY_LARGE_PACK("VeryLargePack"),
    @XmlEnumValue("UPSLetter")
    UPS_LETTER("UPSLetter"),
    @XmlEnumValue("USPSFlatRateEnvelope")
    USPS_FLAT_RATE_ENVELOPE("USPSFlatRateEnvelope"),
    @XmlEnumValue("PackageThickEnvelope")
    PACKAGE_THICK_ENVELOPE("PackageThickEnvelope"),
    @XmlEnumValue("CustomCode")
    CUSTOM_CODE("CustomCode");
    private final String value;

    ShippingPackageCodeType(String v)
    {
        value = v;
    }

    public String value()
    {
        return value;
    }

    public static ShippingPackageCodeType fromValue(String v)
    {
        for (ShippingPackageCodeType c : ShippingPackageCodeType.values())
        {
            if (c.value.equals(v))
            {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
