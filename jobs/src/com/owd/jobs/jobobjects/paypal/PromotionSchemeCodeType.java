package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PromotionSchemeCodeType.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;simpleType name="PromotionSchemeCodeType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="ItemToItem"/>
 *     &lt;enumeration value="ItemToStoreCat"/>
 *     &lt;enumeration value="StoreToStoreCat"/>
 *     &lt;enumeration value="CustomCode"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 */
@XmlType(name = "PromotionSchemeCodeType", namespace = "urn:ebay:apis:eBLBaseComponents")
@XmlEnum
public enum PromotionSchemeCodeType
{

    @XmlEnumValue("ItemToItem")
    ITEM_TO_ITEM("ItemToItem"),
    @XmlEnumValue("ItemToStoreCat")
    ITEM_TO_STORE_CAT("ItemToStoreCat"),
    @XmlEnumValue("StoreToStoreCat")
    STORE_TO_STORE_CAT("StoreToStoreCat"),
    @XmlEnumValue("CustomCode")
    CUSTOM_CODE("CustomCode");
    private final String value;

    PromotionSchemeCodeType(String v)
    {
        value = v;
    }

    public String value()
    {
        return value;
    }

    public static PromotionSchemeCodeType fromValue(String v)
    {
        for (PromotionSchemeCodeType c : PromotionSchemeCodeType.values())
        {
            if (c.value.equals(v))
            {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
