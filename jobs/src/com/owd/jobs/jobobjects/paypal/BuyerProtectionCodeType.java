package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BuyerProtectionCodeType.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;simpleType name="BuyerProtectionCodeType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="ItemIneligible"/>
 *     &lt;enumeration value="ItemEligible"/>
 *     &lt;enumeration value="ItemMarkedIneligible"/>
 *     &lt;enumeration value="ItemMarkedEligible"/>
 *     &lt;enumeration value="CustomCode"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 */
@XmlType(name = "BuyerProtectionCodeType", namespace = "urn:ebay:apis:eBLBaseComponents")
@XmlEnum
public enum BuyerProtectionCodeType
{

    @XmlEnumValue("ItemIneligible")
    ITEM_INELIGIBLE("ItemIneligible"),
    @XmlEnumValue("ItemEligible")
    ITEM_ELIGIBLE("ItemEligible"),
    @XmlEnumValue("ItemMarkedIneligible")
    ITEM_MARKED_INELIGIBLE("ItemMarkedIneligible"),
    @XmlEnumValue("ItemMarkedEligible")
    ITEM_MARKED_ELIGIBLE("ItemMarkedEligible"),
    @XmlEnumValue("CustomCode")
    CUSTOM_CODE("CustomCode");
    private final String value;

    BuyerProtectionCodeType(String v)
    {
        value = v;
    }

    public String value()
    {
        return value;
    }

    public static BuyerProtectionCodeType fromValue(String v)
    {
        for (BuyerProtectionCodeType c : BuyerProtectionCodeType.values())
        {
            if (c.value.equals(v))
            {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
