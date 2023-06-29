package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ButtonTypeType.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;simpleType name="ButtonTypeType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="BUYNOW"/>
 *     &lt;enumeration value="CART"/>
 *     &lt;enumeration value="GIFTCERTIFICATE"/>
 *     &lt;enumeration value="SUBSCRIBE"/>
 *     &lt;enumeration value="DONATE"/>
 *     &lt;enumeration value="UNSUBSCRIBE"/>
 *     &lt;enumeration value="VIEWCART"/>
 *     &lt;enumeration value="PAYMENTPLAN"/>
 *     &lt;enumeration value="AUTOBILLING"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 */
@XmlType(name = "ButtonTypeType", namespace = "urn:ebay:apis:eBLBaseComponents")
@XmlEnum
public enum ButtonTypeType
{

    BUYNOW,
    CART,
    GIFTCERTIFICATE,
    SUBSCRIBE,
    DONATE,
    UNSUBSCRIBE,
    VIEWCART,
    PAYMENTPLAN,
    AUTOBILLING;

    public String value()
    {
        return name();
    }

    public static ButtonTypeType fromValue(String v)
    {
        return valueOf(v);
    }

}
