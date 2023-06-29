package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BuyNowTextType.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;simpleType name="BuyNowTextType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="BUYNOW"/>
 *     &lt;enumeration value="PAYNOW"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 */
@XmlType(name = "BuyNowTextType", namespace = "urn:ebay:apis:eBLBaseComponents")
@XmlEnum
public enum BuyNowTextType
{

    BUYNOW,
    PAYNOW;

    public String value()
    {
        return name();
    }

    public static BuyNowTextType fromValue(String v)
    {
        return valueOf(v);
    }

}
