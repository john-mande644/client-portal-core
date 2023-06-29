package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SellerLevelCodeType.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;simpleType name="SellerLevelCodeType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="Bronze"/>
 *     &lt;enumeration value="Silver"/>
 *     &lt;enumeration value="Gold"/>
 *     &lt;enumeration value="Platinum"/>
 *     &lt;enumeration value="Titanium"/>
 *     &lt;enumeration value="None"/>
 *     &lt;enumeration value="CustomCode"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 */
@XmlType(name = "SellerLevelCodeType", namespace = "urn:ebay:apis:eBLBaseComponents")
@XmlEnum
public enum SellerLevelCodeType
{

    @XmlEnumValue("Bronze")
    BRONZE("Bronze"),
    @XmlEnumValue("Silver")
    SILVER("Silver"),
    @XmlEnumValue("Gold")
    GOLD("Gold"),
    @XmlEnumValue("Platinum")
    PLATINUM("Platinum"),
    @XmlEnumValue("Titanium")
    TITANIUM("Titanium"),
    @XmlEnumValue("None")
    NONE("None"),
    @XmlEnumValue("CustomCode")
    CUSTOM_CODE("CustomCode");
    private final String value;

    SellerLevelCodeType(String v)
    {
        value = v;
    }

    public String value()
    {
        return value;
    }

    public static SellerLevelCodeType fromValue(String v)
    {
        for (SellerLevelCodeType c : SellerLevelCodeType.values())
        {
            if (c.value.equals(v))
            {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
