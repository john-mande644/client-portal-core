package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ShippingServiceCodeType.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;simpleType name="ShippingServiceCodeType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="UPSGround"/>
 *     &lt;enumeration value="UPS3rdDay"/>
 *     &lt;enumeration value="UPS2ndDay"/>
 *     &lt;enumeration value="UPSNextDay"/>
 *     &lt;enumeration value="USPSPriority"/>
 *     &lt;enumeration value="USPSParcel"/>
 *     &lt;enumeration value="USPSMedia"/>
 *     &lt;enumeration value="USPSFirstClass"/>
 *     &lt;enumeration value="ShippingMethodStandard"/>
 *     &lt;enumeration value="ShippingMethodExpress"/>
 *     &lt;enumeration value="ShippingMethodNextDay"/>
 *     &lt;enumeration value="USPSExpressMail"/>
 *     &lt;enumeration value="USPSGround"/>
 *     &lt;enumeration value="Download"/>
 *     &lt;enumeration value="WillCall_Or_Pickup"/>
 *     &lt;enumeration value="CustomCode"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 */
@XmlType(name = "ShippingServiceCodeType", namespace = "urn:ebay:apis:eBLBaseComponents")
@XmlEnum
public enum ShippingServiceCodeType
{

    @XmlEnumValue("UPSGround")
    UPS_GROUND("UPSGround"),
    @XmlEnumValue("UPS3rdDay")
    UPS_3_RD_DAY("UPS3rdDay"),
    @XmlEnumValue("UPS2ndDay")
    UPS_2_ND_DAY("UPS2ndDay"),
    @XmlEnumValue("UPSNextDay")
    UPS_NEXT_DAY("UPSNextDay"),
    @XmlEnumValue("USPSPriority")
    USPS_PRIORITY("USPSPriority"),
    @XmlEnumValue("USPSParcel")
    USPS_PARCEL("USPSParcel"),
    @XmlEnumValue("USPSMedia")
    USPS_MEDIA("USPSMedia"),
    @XmlEnumValue("USPSFirstClass")
    USPS_FIRST_CLASS("USPSFirstClass"),
    @XmlEnumValue("ShippingMethodStandard")
    SHIPPING_METHOD_STANDARD("ShippingMethodStandard"),
    @XmlEnumValue("ShippingMethodExpress")
    SHIPPING_METHOD_EXPRESS("ShippingMethodExpress"),
    @XmlEnumValue("ShippingMethodNextDay")
    SHIPPING_METHOD_NEXT_DAY("ShippingMethodNextDay"),
    @XmlEnumValue("USPSExpressMail")
    USPS_EXPRESS_MAIL("USPSExpressMail"),
    @XmlEnumValue("USPSGround")
    USPS_GROUND("USPSGround"),
    @XmlEnumValue("Download")
    DOWNLOAD("Download"),
    @XmlEnumValue("WillCall_Or_Pickup")
    WILL_CALL_OR_PICKUP("WillCall_Or_Pickup"),
    @XmlEnumValue("CustomCode")
    CUSTOM_CODE("CustomCode");
    private final String value;

    ShippingServiceCodeType(String v)
    {
        value = v;
    }

    public String value()
    {
        return value;
    }

    public static ShippingServiceCodeType fromValue(String v)
    {
        for (ShippingServiceCodeType c : ShippingServiceCodeType.values())
        {
            if (c.value.equals(v))
            {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
