package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for UserWithdrawalLimitTypeType.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;simpleType name="UserWithdrawalLimitTypeType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="Unknown"/>
 *     &lt;enumeration value="Limited"/>
 *     &lt;enumeration value="Unlimited"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 */
@XmlType(name = "UserWithdrawalLimitTypeType", namespace = "urn:ebay:apis:eBLBaseComponents")
@XmlEnum
public enum UserWithdrawalLimitTypeType
{

    @XmlEnumValue("Unknown")
    UNKNOWN("Unknown"),
    @XmlEnumValue("Limited")
    LIMITED("Limited"),
    @XmlEnumValue("Unlimited")
    UNLIMITED("Unlimited");
    private final String value;

    UserWithdrawalLimitTypeType(String v)
    {
        value = v;
    }

    public String value()
    {
        return value;
    }

    public static UserWithdrawalLimitTypeType fromValue(String v)
    {
        for (UserWithdrawalLimitTypeType c : UserWithdrawalLimitTypeType.values())
        {
            if (c.value.equals(v))
            {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
