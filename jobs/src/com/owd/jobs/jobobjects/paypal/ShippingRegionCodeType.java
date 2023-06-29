package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ShippingRegionCodeType.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;simpleType name="ShippingRegionCodeType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="Africa"/>
 *     &lt;enumeration value="Asia"/>
 *     &lt;enumeration value="Caribbean"/>
 *     &lt;enumeration value="Europe"/>
 *     &lt;enumeration value="LatinAmerica"/>
 *     &lt;enumeration value="MiddleEast"/>
 *     &lt;enumeration value="NorthAmerica"/>
 *     &lt;enumeration value="Oceania"/>
 *     &lt;enumeration value="SouthAmerica"/>
 *     &lt;enumeration value="CustomCode"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 */
@XmlType(name = "ShippingRegionCodeType", namespace = "urn:ebay:apis:eBLBaseComponents")
@XmlEnum
public enum ShippingRegionCodeType
{

    @XmlEnumValue("Africa")
    AFRICA("Africa"),
    @XmlEnumValue("Asia")
    ASIA("Asia"),
    @XmlEnumValue("Caribbean")
    CARIBBEAN("Caribbean"),
    @XmlEnumValue("Europe")
    EUROPE("Europe"),
    @XmlEnumValue("LatinAmerica")
    LATIN_AMERICA("LatinAmerica"),
    @XmlEnumValue("MiddleEast")
    MIDDLE_EAST("MiddleEast"),
    @XmlEnumValue("NorthAmerica")
    NORTH_AMERICA("NorthAmerica"),
    @XmlEnumValue("Oceania")
    OCEANIA("Oceania"),
    @XmlEnumValue("SouthAmerica")
    SOUTH_AMERICA("SouthAmerica"),
    @XmlEnumValue("CustomCode")
    CUSTOM_CODE("CustomCode");
    private final String value;

    ShippingRegionCodeType(String v)
    {
        value = v;
    }

    public String value()
    {
        return value;
    }

    public static ShippingRegionCodeType fromValue(String v)
    {
        for (ShippingRegionCodeType c : ShippingRegionCodeType.values())
        {
            if (c.value.equals(v))
            {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
