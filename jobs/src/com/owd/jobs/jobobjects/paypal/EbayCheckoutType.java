package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EbayCheckoutType.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;simpleType name="EbayCheckoutType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="none"/>
 *     &lt;enumeration value="Auction"/>
 *     &lt;enumeration value="BuyItNow"/>
 *     &lt;enumeration value="FixedPriceItem"/>
 *     &lt;enumeration value="Autopay"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 */
@XmlType(name = "EbayCheckoutType", namespace = "urn:ebay:apis:eBLBaseComponents")
@XmlEnum
public enum EbayCheckoutType
{

    @XmlEnumValue("none")
    NONE("none"),
    @XmlEnumValue("Auction")
    AUCTION("Auction"),
    @XmlEnumValue("BuyItNow")
    BUY_IT_NOW("BuyItNow"),
    @XmlEnumValue("FixedPriceItem")
    FIXED_PRICE_ITEM("FixedPriceItem"),
    @XmlEnumValue("Autopay")
    AUTOPAY("Autopay");
    private final String value;

    EbayCheckoutType(String v)
    {
        value = v;
    }

    public String value()
    {
        return value;
    }

    public static EbayCheckoutType fromValue(String v)
    {
        for (EbayCheckoutType c : EbayCheckoutType.values())
        {
            if (c.value.equals(v))
            {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
