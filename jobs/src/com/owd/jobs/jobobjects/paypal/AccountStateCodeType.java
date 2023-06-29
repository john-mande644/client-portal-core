package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AccountStateCodeType.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;simpleType name="AccountStateCodeType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="Active"/>
 *     &lt;enumeration value="Pending"/>
 *     &lt;enumeration value="Inactive"/>
 *     &lt;enumeration value="CustomCode"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 */
@XmlType(name = "AccountStateCodeType", namespace = "urn:ebay:apis:eBLBaseComponents")
@XmlEnum
public enum AccountStateCodeType
{

    @XmlEnumValue("Active")
    ACTIVE("Active"),
    @XmlEnumValue("Pending")
    PENDING("Pending"),
    @XmlEnumValue("Inactive")
    INACTIVE("Inactive"),
    @XmlEnumValue("CustomCode")
    CUSTOM_CODE("CustomCode");
    private final String value;

    AccountStateCodeType(String v)
    {
        value = v;
    }

    public String value()
    {
        return value;
    }

    public static AccountStateCodeType fromValue(String v)
    {
        for (AccountStateCodeType c : AccountStateCodeType.values())
        {
            if (c.value.equals(v))
            {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
