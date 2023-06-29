package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ShippingOptionCodeType.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;simpleType name="ShippingOptionCodeType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="SiteOnly"/>
 *     &lt;enumeration value="WorldWide"/>
 *     &lt;enumeration value="SitePlusRegions"/>
 *     &lt;enumeration value="WillNotShip"/>
 *     &lt;enumeration value="CustomCode"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 */
@XmlType(name = "ShippingOptionCodeType", namespace = "urn:ebay:apis:eBLBaseComponents")
@XmlEnum
public enum ShippingOptionCodeType
{

    @XmlEnumValue("SiteOnly")
    SITE_ONLY("SiteOnly"),
    @XmlEnumValue("WorldWide")
    WORLD_WIDE("WorldWide"),
    @XmlEnumValue("SitePlusRegions")
    SITE_PLUS_REGIONS("SitePlusRegions"),
    @XmlEnumValue("WillNotShip")
    WILL_NOT_SHIP("WillNotShip"),
    @XmlEnumValue("CustomCode")
    CUSTOM_CODE("CustomCode");
    private final String value;

    ShippingOptionCodeType(String v)
    {
        value = v;
    }

    public String value()
    {
        return value;
    }

    public static ShippingOptionCodeType fromValue(String v)
    {
        for (ShippingOptionCodeType c : ShippingOptionCodeType.values())
        {
            if (c.value.equals(v))
            {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
