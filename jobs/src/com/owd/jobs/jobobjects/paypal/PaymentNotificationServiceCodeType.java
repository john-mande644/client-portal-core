package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PaymentNotificationServiceCodeType.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;simpleType name="PaymentNotificationServiceCodeType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="eBayCN"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 */
@XmlType(name = "PaymentNotificationServiceCodeType", namespace = "urn:ebay:apis:eBLBaseComponents")
@XmlEnum
public enum PaymentNotificationServiceCodeType
{

    @XmlEnumValue("eBayCN")
    E_BAY_CN("eBayCN");
    private final String value;

    PaymentNotificationServiceCodeType(String v)
    {
        value = v;
    }

    public String value()
    {
        return value;
    }

    public static PaymentNotificationServiceCodeType fromValue(String v)
    {
        for (PaymentNotificationServiceCodeType c : PaymentNotificationServiceCodeType.values())
        {
            if (c.value.equals(v))
            {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
