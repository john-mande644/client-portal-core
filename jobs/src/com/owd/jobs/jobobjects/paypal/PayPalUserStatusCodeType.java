package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PayPalUserStatusCodeType.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;simpleType name="PayPalUserStatusCodeType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="verified"/>
 *     &lt;enumeration value="unverified"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 */
@XmlType(name = "PayPalUserStatusCodeType", namespace = "urn:ebay:apis:eBLBaseComponents")
@XmlEnum
public enum PayPalUserStatusCodeType
{

    @XmlEnumValue("verified")
    VERIFIED("verified"),
    @XmlEnumValue("unverified")
    UNVERIFIED("unverified");
    private final String value;

    PayPalUserStatusCodeType(String v)
    {
        value = v;
    }

    public String value()
    {
        return value;
    }

    public static PayPalUserStatusCodeType fromValue(String v)
    {
        for (PayPalUserStatusCodeType c : PayPalUserStatusCodeType.values())
        {
            if (c.value.equals(v))
            {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
