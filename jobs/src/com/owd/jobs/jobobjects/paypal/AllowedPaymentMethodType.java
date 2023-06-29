package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AllowedPaymentMethodType.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;simpleType name="AllowedPaymentMethodType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="Default"/>
 *     &lt;enumeration value="InstantPaymentOnly"/>
 *     &lt;enumeration value="AnyFundingSource"/>
 *     &lt;enumeration value="InstantFundingSource"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 */
@XmlType(name = "AllowedPaymentMethodType", namespace = "urn:ebay:apis:eBLBaseComponents")
@XmlEnum
public enum AllowedPaymentMethodType
{

    @XmlEnumValue("Default")
    DEFAULT("Default"),
    @XmlEnumValue("InstantPaymentOnly")
    INSTANT_PAYMENT_ONLY("InstantPaymentOnly"),
    @XmlEnumValue("AnyFundingSource")
    ANY_FUNDING_SOURCE("AnyFundingSource"),
    @XmlEnumValue("InstantFundingSource")
    INSTANT_FUNDING_SOURCE("InstantFundingSource");
    private final String value;

    AllowedPaymentMethodType(String v)
    {
        value = v;
    }

    public String value()
    {
        return value;
    }

    public static AllowedPaymentMethodType fromValue(String v)
    {
        for (AllowedPaymentMethodType c : AllowedPaymentMethodType.values())
        {
            if (c.value.equals(v))
            {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
