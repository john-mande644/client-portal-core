package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PromotionItemPriceTypeCodeType.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;simpleType name="PromotionItemPriceTypeCodeType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="AuctionPrice"/>
 *     &lt;enumeration value="BuyItNowPrice"/>
 *     &lt;enumeration value="CustomCode"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 */
@XmlType(name = "PromotionItemPriceTypeCodeType", namespace = "urn:ebay:apis:eBLBaseComponents")
@XmlEnum
public enum PromotionItemPriceTypeCodeType
{

    @XmlEnumValue("AuctionPrice")
    AUCTION_PRICE("AuctionPrice"),
    @XmlEnumValue("BuyItNowPrice")
    BUY_IT_NOW_PRICE("BuyItNowPrice"),
    @XmlEnumValue("CustomCode")
    CUSTOM_CODE("CustomCode");
    private final String value;

    PromotionItemPriceTypeCodeType(String v)
    {
        value = v;
    }

    public String value()
    {
        return value;
    }

    public static PromotionItemPriceTypeCodeType fromValue(String v)
    {
        for (PromotionItemPriceTypeCodeType c : PromotionItemPriceTypeCodeType.values())
        {
            if (c.value.equals(v))
            {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
