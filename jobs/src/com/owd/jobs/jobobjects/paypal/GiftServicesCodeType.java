package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GiftServicesCodeType.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;simpleType name="GiftServicesCodeType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="GiftExpressShipping"/>
 *     &lt;enumeration value="GiftShipToRecipient"/>
 *     &lt;enumeration value="GiftWrap"/>
 *     &lt;enumeration value="CustomCode"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 */
@XmlType(name = "GiftServicesCodeType", namespace = "urn:ebay:apis:eBLBaseComponents")
@XmlEnum
public enum GiftServicesCodeType
{

    @XmlEnumValue("GiftExpressShipping")
    GIFT_EXPRESS_SHIPPING("GiftExpressShipping"),
    @XmlEnumValue("GiftShipToRecipient")
    GIFT_SHIP_TO_RECIPIENT("GiftShipToRecipient"),
    @XmlEnumValue("GiftWrap")
    GIFT_WRAP("GiftWrap"),
    @XmlEnumValue("CustomCode")
    CUSTOM_CODE("CustomCode");
    private final String value;

    GiftServicesCodeType(String v)
    {
        value = v;
    }

    public String value()
    {
        return value;
    }

    public static GiftServicesCodeType fromValue(String v)
    {
        for (GiftServicesCodeType c : GiftServicesCodeType.values())
        {
            if (c.value.equals(v))
            {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
