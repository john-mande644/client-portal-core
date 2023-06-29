package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for UserSelectedFundingSourceType.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;simpleType name="UserSelectedFundingSourceType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="ELV"/>
 *     &lt;enumeration value="CreditCard"/>
 *     &lt;enumeration value="ChinaUnionPay"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 */
@XmlType(name = "UserSelectedFundingSourceType", namespace = "urn:ebay:apis:eBLBaseComponents")
@XmlEnum
public enum UserSelectedFundingSourceType
{

    ELV("ELV"),
    @XmlEnumValue("CreditCard")
    CREDIT_CARD("CreditCard"),
    @XmlEnumValue("ChinaUnionPay")
    CHINA_UNION_PAY("ChinaUnionPay");
    private final String value;

    UserSelectedFundingSourceType(String v)
    {
        value = v;
    }

    public String value()
    {
        return value;
    }

    public static UserSelectedFundingSourceType fromValue(String v)
    {
        for (UserSelectedFundingSourceType c : UserSelectedFundingSourceType.values())
        {
            if (c.value.equals(v))
            {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
