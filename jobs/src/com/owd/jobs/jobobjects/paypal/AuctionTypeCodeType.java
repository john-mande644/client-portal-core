package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AuctionTypeCodeType.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;simpleType name="AuctionTypeCodeType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="Unknown"/>
 *     &lt;enumeration value="Chinese"/>
 *     &lt;enumeration value="Dutch"/>
 *     &lt;enumeration value="Live"/>
 *     &lt;enumeration value="Ad type"/>
 *     &lt;enumeration value="Stores Fixed-price"/>
 *     &lt;enumeration value="Personal Offer"/>
 *     &lt;enumeration value="Fixed Price Item"/>
 *     &lt;enumeration value="CustomCode"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 */
@XmlType(name = "AuctionTypeCodeType", namespace = "urn:ebay:apis:eBLBaseComponents")
@XmlEnum
public enum AuctionTypeCodeType
{

    @XmlEnumValue("Unknown")
    UNKNOWN("Unknown"),
    @XmlEnumValue("Chinese")
    CHINESE("Chinese"),
    @XmlEnumValue("Dutch")
    DUTCH("Dutch"),
    @XmlEnumValue("Live")
    LIVE("Live"),
    @XmlEnumValue("Ad type")
    AD_TYPE("Ad type"),
    @XmlEnumValue("Stores Fixed-price")
    STORES_FIXED_PRICE("Stores Fixed-price"),
    @XmlEnumValue("Personal Offer")
    PERSONAL_OFFER("Personal Offer"),
    @XmlEnumValue("Fixed Price Item")
    FIXED_PRICE_ITEM("Fixed Price Item"),
    @XmlEnumValue("CustomCode")
    CUSTOM_CODE("CustomCode");
    private final String value;

    AuctionTypeCodeType(String v)
    {
        value = v;
    }

    public String value()
    {
        return value;
    }

    public static AuctionTypeCodeType fromValue(String v)
    {
        for (AuctionTypeCodeType c : AuctionTypeCodeType.values())
        {
            if (c.value.equals(v))
            {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
