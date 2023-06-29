package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ListingEnhancementsCodeType.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;simpleType name="ListingEnhancementsCodeType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="Border"/>
 *     &lt;enumeration value="BoldTitle"/>
 *     &lt;enumeration value="Featured"/>
 *     &lt;enumeration value="Highlight"/>
 *     &lt;enumeration value="HomePageFeatured"/>
 *     &lt;enumeration value="CustomCode"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 */
@XmlType(name = "ListingEnhancementsCodeType", namespace = "urn:ebay:apis:eBLBaseComponents")
@XmlEnum
public enum ListingEnhancementsCodeType
{

    @XmlEnumValue("Border")
    BORDER("Border"),
    @XmlEnumValue("BoldTitle")
    BOLD_TITLE("BoldTitle"),
    @XmlEnumValue("Featured")
    FEATURED("Featured"),
    @XmlEnumValue("Highlight")
    HIGHLIGHT("Highlight"),
    @XmlEnumValue("HomePageFeatured")
    HOME_PAGE_FEATURED("HomePageFeatured"),
    @XmlEnumValue("CustomCode")
    CUSTOM_CODE("CustomCode");
    private final String value;

    ListingEnhancementsCodeType(String v)
    {
        value = v;
    }

    public String value()
    {
        return value;
    }

    public static ListingEnhancementsCodeType fromValue(String v)
    {
        for (ListingEnhancementsCodeType c : ListingEnhancementsCodeType.values())
        {
            if (c.value.equals(v))
            {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
