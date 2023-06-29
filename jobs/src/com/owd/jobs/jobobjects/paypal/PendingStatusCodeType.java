package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PendingStatusCodeType.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;simpleType name="PendingStatusCodeType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="none"/>
 *     &lt;enumeration value="echeck"/>
 *     &lt;enumeration value="intl"/>
 *     &lt;enumeration value="verify"/>
 *     &lt;enumeration value="address"/>
 *     &lt;enumeration value="unilateral"/>
 *     &lt;enumeration value="other"/>
 *     &lt;enumeration value="upgrade"/>
 *     &lt;enumeration value="multi-currency"/>
 *     &lt;enumeration value="authorization"/>
 *     &lt;enumeration value="order"/>
 *     &lt;enumeration value="payment-review"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 */
@XmlType(name = "PendingStatusCodeType", namespace = "urn:ebay:apis:eBLBaseComponents")
@XmlEnum
public enum PendingStatusCodeType
{

    @XmlEnumValue("none")
    NONE("none"),
    @XmlEnumValue("echeck")
    ECHECK("echeck"),
    @XmlEnumValue("intl")
    INTL("intl"),
    @XmlEnumValue("verify")
    VERIFY("verify"),
    @XmlEnumValue("address")
    ADDRESS("address"),
    @XmlEnumValue("unilateral")
    UNILATERAL("unilateral"),
    @XmlEnumValue("other")
    OTHER("other"),
    @XmlEnumValue("upgrade")
    UPGRADE("upgrade"),
    @XmlEnumValue("multi-currency")
    MULTI_CURRENCY("multi-currency"),
    @XmlEnumValue("authorization")
    AUTHORIZATION("authorization"),
    @XmlEnumValue("order")
    ORDER("order"),
    @XmlEnumValue("payment-review")
    PAYMENT_REVIEW("payment-review");
    private final String value;

    PendingStatusCodeType(String v)
    {
        value = v;
    }

    public String value()
    {
        return value;
    }

    public static PendingStatusCodeType fromValue(String v)
    {
        for (PendingStatusCodeType c : PendingStatusCodeType.values())
        {
            if (c.value.equals(v))
            {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
