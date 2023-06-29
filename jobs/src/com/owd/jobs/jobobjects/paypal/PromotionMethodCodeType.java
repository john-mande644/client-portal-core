package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PromotionMethodCodeType.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;simpleType name="PromotionMethodCodeType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="CrossSell"/>
 *     &lt;enumeration value="UpSell"/>
 *     &lt;enumeration value="CustomCode"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 */
@XmlType(name = "PromotionMethodCodeType", namespace = "urn:ebay:apis:eBLBaseComponents")
@XmlEnum
public enum PromotionMethodCodeType
{

    @XmlEnumValue("CrossSell")
    CROSS_SELL("CrossSell"),
    @XmlEnumValue("UpSell")
    UP_SELL("UpSell"),
    @XmlEnumValue("CustomCode")
    CUSTOM_CODE("CustomCode");
    private final String value;

    PromotionMethodCodeType(String v)
    {
        value = v;
    }

    public String value()
    {
        return value;
    }

    public static PromotionMethodCodeType fromValue(String v)
    {
        for (PromotionMethodCodeType c : PromotionMethodCodeType.values())
        {
            if (c.value.equals(v))
            {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
