package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SalesVenueType.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;simpleType name="SalesVenueType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="Venue-Unspecified"/>
 *     &lt;enumeration value="eBay"/>
 *     &lt;enumeration value="AnotherMarketPlace"/>
 *     &lt;enumeration value="OwnWebsite"/>
 *     &lt;enumeration value="Other"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 */
@XmlType(name = "SalesVenueType", namespace = "urn:ebay:apis:eBLBaseComponents")
@XmlEnum
public enum SalesVenueType
{

    @XmlEnumValue("Venue-Unspecified")
    VENUE_UNSPECIFIED("Venue-Unspecified"),
    @XmlEnumValue("eBay")
    E_BAY("eBay"),
    @XmlEnumValue("AnotherMarketPlace")
    ANOTHER_MARKET_PLACE("AnotherMarketPlace"),
    @XmlEnumValue("OwnWebsite")
    OWN_WEBSITE("OwnWebsite"),
    @XmlEnumValue("Other")
    OTHER("Other");
    private final String value;

    SalesVenueType(String v)
    {
        value = v;
    }

    public String value()
    {
        return value;
    }

    public static SalesVenueType fromValue(String v)
    {
        for (SalesVenueType c : SalesVenueType.values())
        {
            if (c.value.equals(v))
            {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
